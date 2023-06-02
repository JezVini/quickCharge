package com.quickcharge.app.payment

import com.quickcharge.app.customer.Customer
import com.quickcharge.app.payer.Payer
import utils.entity.BaseEntity
import utils.payment.BillingTypeEnum
import utils.payment.StatusEnum 

class Payment extends BaseEntity {

    Payer payer
    Customer customer
    BillingTypeEnum billingType
    Double value
    StatusEnum status
    Date dueDate
    Date paymentDate
    
    static constraints = {
        billingType blank: false
        value min: 0.01D
        status blank: false
        paymentDate nullable: true
    }
}
