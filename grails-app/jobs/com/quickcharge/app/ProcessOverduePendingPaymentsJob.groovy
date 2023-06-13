package com.quickcharge.app

import com.quickcharge.app.payment.Payment
import com.quickcharge.app.payment.PaymentService
import utils.payment.PaymentStatus

class ProcessOverduePendingPaymentsJob {
    PaymentService paymentService
//        cron name: "processOverduePendingPayments", cronExpression: "0 9 26 * * ?"
    
    static triggers = {
        simple name: "processOverduePendingPaymentsTrigger", repeatInterval: 5000
    }

    def execute() {
            List<Payment> overduePendingPayments = Payment.queryOverduePendingPayments.list()
            paymentService.updateStatus(overduePendingPayments, PaymentStatus.OVERDUE)
    }
}
