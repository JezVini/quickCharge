package com.quickcharge.app

import com.quickcharge.app.payment.Payment
import com.quickcharge.app.payment.PaymentService
import utils.payment.PaymentStatus

class DueDatePaymentJob {
    PaymentService paymentService

    static triggers = {
        cron name: "oncePerDayTrigger", cronExpression: "0 0 0 * * ?"
    }

    def execute() {
        List<Payment> overduePendingPayments = Payment.queryOverduePendingPayments.list()

        for (Payment payment : overduePendingPayments) {
            try {
                paymentService.updateStatus(payment, PaymentStatus.OVERDUE)
            } catch (Exception exception) {
                log.info("DueDatePaymentJob.execute >> Erro ao atualizar status da cobrança: [${payment}] [Mensagem de erro]: ${exception.message}")
            }
        }
    }
}
