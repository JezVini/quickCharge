package com.quickcharge.app.payment

import com.quickcharge.app.customer.Customer
import com.quickcharge.app.payer.Payer
import utils.entity.BaseEntity

class Payment extends BaseEntity {

    Payer payer
    Customer customer
    String billingType
    Double value
    String status
    Date dueDate
    Date paymentDate
    
    static constraints = {
    }
}