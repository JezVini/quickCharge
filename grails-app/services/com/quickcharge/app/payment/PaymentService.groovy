package com.quickcharge.app.payment

import com.quickcharge.app.customer.Customer
import com.quickcharge.app.payer.Payer
import grails.gorm.transactions.Transactional
import grails.plugin.springsecurity.SpringSecurityService
import grails.validation.ValidationException
import org.apache.commons.lang3.EnumUtils
import org.apache.commons.lang3.time.DateUtils
import utils.payment.BillingType

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
        
        Long customerId = Long.valueOf(springSecurityService.getCurrentUser().customer.id)
        Payer payer = Payer.query([id: parameterMap.payerId, customerId: customerId]).get()
        Customer customer = Customer.query([id: customerId]).get()
        BillingType billingType = BillingType[parameterMap.billingType as String]
        Double value = parameterMap.double("value")
        
        Date selectedDay = new SimpleDateFormat("yyyy-MM-dd").parse(parameterMap.dueDate as String) 
        Date dueDate = DateUtils.addDays(selectedDay, 1)
        
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
            validatedPayment.errors.rejectValue("", "", "Selecione um pagador")
        }
        
        if(!EnumUtils.isValidEnum(BillingType.class, parameterMap.billingType)) {
            validatedPayment.errors.rejectValue("", "", "Selecione uma forma de pagamento")
        }
        
        if(!parameterMap.dueDate) {
            validatedPayment.errors.rejectValue("", "", "Selecione uma data de vencimento")
        }
        
        return validatedPayment
    }
    
    public Payment delete(Map parameterMap) {
        Payment validatedPayment = validateDelete(parameterMap)

        if (validatedPayment.hasErrors()) {
            throw new ValidationException("Erro ao remover cobrança", validatedPayment.errors)
        }

        Payment payment = Payment.query([id: parameterMap.id, customerId: parameterMap.customerId]).get()
        payment.deleted = true

        return payment.save(failOnError: true)
    }
 
    private Payment validateDelete(Map parameterMap) {
        Payment validatedPayment = new Payment()

        if (!Payment.query([id: parameterMap.id, customerId: parameterMap.customerId]).get()) {
            validatedPayment.errors.rejectValue("id", "not.found", "Cobrança não encontrada")
        }

        return validatedPayment
    }
    
}
