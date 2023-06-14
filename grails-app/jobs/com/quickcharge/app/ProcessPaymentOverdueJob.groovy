package com.quickcharge.app

import com.quickcharge.app.payment.PaymentService
import utils.payment.PaymentStatus

class ProcessPaymentOverdueJob {
    PaymentService paymentService

    static triggers = {
        cron name: "processOverduePendingPaymentsTrigger", cronExpression: "0 0 0 * * ?"
    }

    def execute() {
        paymentService.updatePendingPaymentStatus(PaymentStatus.OVERDUE)
    }
}
