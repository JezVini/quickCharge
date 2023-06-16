package com.quickcharge.app.payment

import com.quickcharge.app.customer.Customer
import com.quickcharge.app.payer.Payer
import grails.gorm.transactions.Transactional
import grails.validation.ValidationException
import org.apache.commons.lang3.EnumUtils
import utils.payment.BillingType
import utils.payment.PaymentStatus

import java.text.SimpleDateFormat

@Transactional
class PaymentService {

    def save(Map parameterMap, Customer customer) {
        Payment validatedPayment = validateSave(parameterMap)
        
        if (validatedPayment.hasErrors()) {
            throw new ValidationException("Erro ao salvar cobrança", validatedPayment.errors)
        }
        
        Payment payment = new Payment()
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
    
    public Payment delete(Map parameterMap, Customer customer) {
        Map parameterQuery = [id: parameterMap.id, customerId: customer.id]
        Payment validatedPayment = validatePaymentStatus(parameterQuery)

        if (validatedPayment.hasErrors()) {
            throw new ValidationException("Erro ao remover cobrança", validatedPayment.errors)
        }

        Payment payment = Payment.getPayment(parameterQuery)
        payment.deleted = true

        return payment.save(failOnError: true)
    }

    public Payment restore(Map parameterMap, Customer customer) {
        Map parameterQuery = [id: parameterMap.id, customerId: customer.id, deletedOnly: true]

        Payment payment = Payment.getPayment(parameterQuery)
        payment.deleted = false

        return payment.save(failOnError: true)
    }
    
    public Payment receiveInCash(Map parameterMap, Customer customer) {
        Map parameterQuery = [id: parameterMap.id, customerId: customer.id]
        Payment validatedPayment = validatePaymentStatus(parameterQuery)

        if (validatedPayment.hasErrors()) {
            throw new ValidationException("Erro ao confirmar pagamento em dinheiro da cobrança", validatedPayment.errors)
        }

        Payment payment = Payment.getPayment(parameterQuery)
        payment.status = PaymentStatus.RECEIVED_IN_CASH
        payment.paymentDate = new Date()
        
        return payment.save(failOnError: true)
    } 
    
    private Payment validatePaymentStatus(Map parameterQuery) {
        Payment validatedPayment = new Payment()
        if (!Payment.getPayment(parameterQuery).status.canUpdate()) {
            validatedPayment.errors.rejectValue("status", "already.received")
        }
        
        return validatedPayment
    }

    public Payment update(Map parameterMap, Long customerId) {
        Payment validatedPayment = validateUpdate(parameterMap.dueDate as String)

        if (validatedPayment.hasErrors()) {
            throw new ValidationException("Erro ao salvar cobrança", validatedPayment.errors)
        }

        Long paymentId = parameterMap.long("id")
        Payment payment = Payment.getById(paymentId, customerId)

        Double value = parameterMap.double("value")
        Date dueDate = new SimpleDateFormat("yyyy-MM-dd").parse(parameterMap.dueDate as String)

        payment.value = value
        payment.dueDate = dueDate

        return payment.save(failOnError: true)
    }

    public Payment validateUpdate(String dueDate) {
        Payment validatedPayment = new Payment()

        SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy-MM-dd")
        Date dueDateValidate = simpleDate.parse(dueDate)
        if(dueDateValidate.before(simpleDate.parse(simpleDate.format(new Date())))) {
            validatedPayment.errors.rejectValue("dueDate", "past.date")
        }

        return validatedPayment
    }
}
