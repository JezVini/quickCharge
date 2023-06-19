package com.quickcharge.app.payment

import grails.gorm.transactions.Transactional

@Transactional
class PaymentReceiptService {
    
    public PaymentReceipt createReceipt(Payment payment) {
        PaymentReceipt paymentReceipt = new PaymentReceipt()

        paymentReceipt.payment = payment

        return paymentReceipt.save(failOnError: true)
    }

}