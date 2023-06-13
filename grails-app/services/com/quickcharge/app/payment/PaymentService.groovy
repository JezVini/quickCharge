package com.quickcharge.app.payment

import com.quickcharge.app.customer.Customer
import com.quickcharge.app.payer.Payer
import grails.gorm.transactions.Transactional
import grails.plugin.springsecurity.SpringSecurityService
import grails.validation.ValidationException
import org.apache.commons.lang3.EnumUtils
import utils.payment.BillingType
import utils.payment.PaymentStatus

import java.text.SimpleDateFormat

@Transactional
class PaymentService {

    SpringSecurityService springSecurityService
    
    def save(Map parameterMap) {
        Payment validatedPayment = validateSave(parameterMap)
        
        if (validatedPayment.hasErrors()) {
            throw new ValidationException("Erro ao salvar cobrança", validatedPayment.errors)
        }
        
        Payment payment = new Payment()
        Customer customer = (springSecurityService.getCurrentUser().customer)
        Payer payer = Payer.query([id: parameterMap.payerId, customerId: customer.id]).get()
        BillingType billingType = BillingType[parameterMap.billingType as String] as BillingType
        Double value = parameterMap.double("value")
        Date dueDate = new SimpleDateFormat("yyyy-MM-dd").parse(parameterMap.dueDate as String) 
        
        payment.payer = payer
        payment.customer = customer
        payment.billingType = billingType
        payment.value = value
        payment.dueDate = dueDate
        
        payment.save(failOnError: true)        
    }
    
    public Payment validateSave(Map parameterMap) {
        Payment validatedPayment = new Payment()

        if(!parameterMap.payerId) {
            validatedPayment.errors.rejectValue("payer", "not.selected")
        }
        
        if(!EnumUtils.isValidEnum(BillingType.class, parameterMap.billingType as String)) {
            validatedPayment.errors.rejectValue("billingType", "not.selected")
        }
        
        if(!parameterMap.dueDate) {
            validatedPayment.errors.rejectValue("dueDate", "not.selected",)
            return validatedPayment
        }

        SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy-MM-dd")
        Date dueDate = simpleDate.parse(parameterMap.dueDate as String)
        if(dueDate.before(simpleDate.parse(simpleDate.format(new Date())))) {
            validatedPayment.errors.rejectValue("dueDate", "past.date")
        }
        
        return validatedPayment
    }
    
    public Payment delete(Map parameterMap) {
        Long customerId = Long.valueOf(springSecurityService.getCurrentUser().customer.id)
        Map parameterQuery = [id: parameterMap.id, customerId: customerId]
        Payment validatedPayment = validatePayment(parameterQuery)

        if (validatedPayment.hasErrors()) {
            throw new ValidationException("Erro ao remover cobrança", validatedPayment.errors)
        }

        Payment payment = Payment.query(parameterQuery).get()
        payment.deleted = true

        return payment.save(failOnError: true)
    }

    public Payment restore(Map parameterMap) {
        Long customerId = Long.valueOf(springSecurityService.getCurrentUser().customer.id)
        Map parameterQuery = [id: parameterMap.id, customerId: customerId, deletedOnly: true]
        Payment validatedPayment = validatePayment(parameterQuery)

        if (validatedPayment.hasErrors()) {
            throw new ValidationException("Erro ao restaurar cobrança", validatedPayment.errors)
        }
        
        Payment payment = Payment.query(parameterQuery).get()
        payment.deleted = false

        return payment.save(failOnError: true)
    }
    
    private Payment validatePayment(Map parameterQuery) {
        Payment validatedPayment = new Payment()
        if (!Payment.query(parameterQuery).get()) {
            validatedPayment.errors.rejectValue("id", "not.found")
        }

        return validatedPayment
    }
    
    public Payment receiveInCash(Map parameterMap) {
        Long customerId = Long.valueOf(springSecurityService.getCurrentUser().customer.id)
        Map parameterQuery = [id: parameterMap.id, customerId: customerId]
        Payment validatedPayment = validateReceiveInCash(parameterQuery)

        if (validatedPayment.hasErrors()) {
            throw new ValidationException("Erro ao confirmar pagamento em dinheiro da cobrança", validatedPayment.errors)
        }

        Payment payment = Payment.query(parameterQuery).get()
        payment.status = PaymentStatus.RECEIVED_IN_CASH
        
        return payment.save(failOnError: true)
    } 
    
    private validateReceiveInCash(Map parameterQuery) {
        Payment validatedPayment = validatePayment(parameterQuery)
        if (validatedPayment.hasErrors()) return validatedPayment
        
        if ((Payment.query(parameterQuery).get() as Payment).status != PaymentStatus.PENDING) {
            validatedPayment.errors.rejectValue("status", "not.pending")
        }
        
        return validatedPayment
    }
    
}
