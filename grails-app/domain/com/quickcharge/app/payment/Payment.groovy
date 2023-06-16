package com.quickcharge.app.payment

import com.quickcharge.app.customer.Customer
import com.quickcharge.app.payer.Payer
import grails.validation.ValidationException
import utils.entity.BaseEntity
import utils.payment.BillingType
import utils.payment.PaymentStatus

class Payment extends BaseEntity {

    Payer payer
    Customer customer
    BillingType billingType
    Double value
    PaymentStatus status = PaymentStatus.PENDING
    Date dueDate
    Date paymentDate

    static constraints = {
        billingType blank: false
        value min: 0.01D
        status blank: false
        paymentDate nullable: true
    }

    static namedQueries = {
        query { Map search ->
            if (!search.containsKey("customerId")) {
                throw new RuntimeException("Payer.query(): o atributo [customerId] é obrigatório para executar a consulta.")
            }
            
            if (Boolean.valueOf(search.deletedOnly)) {
                eq("deleted", true)
            } else if (!Boolean.valueOf(search.includeDeleted)) {
                eq("deleted", false)
            }

            eq("customer.id", Long.valueOf(search.customerId))

            if (search.containsKey("id")) {
                eq("id", Long.valueOf(search.id))
            }
            
            if(search.containsKey("payerId")) {
                eq("payer.id", Long.valueOf(search.payerId))
            }
            
            if (search.containsKey("status")) {
                inList("status", search.status as List<PaymentStatus>)
            }
            
        }
    }

    static Payment getById(id, customerId) {
        Payment payment = Payment.query([id: id, customerId: customerId]).get()
        if (payment) return payment

        Payment validatedPayment = new Payment()
        validatedPayment.errors.rejectValue("id", "not.found")
        throw new ValidationException("Erro ao buscar cobrança", validatedPayment.errors)
    }
}