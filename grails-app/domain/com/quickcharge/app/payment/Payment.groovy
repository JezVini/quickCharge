package com.quickcharge.app.payment

import com.quickcharge.app.customer.Customer
import com.quickcharge.app.payer.Payer
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
        dueDate validator: {val, obj, errors ->
            if(val.before(new Date())) {
                errors.rejectValue("dueDate", "past.date",)
            }
        }
    }
}
