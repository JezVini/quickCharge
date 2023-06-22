package com.quickcharge.app.payment

import grails.plugin.springsecurity.annotation.Secured
import utils.controller.BaseController
import utils.message.MessageType
import utils.payment.BillingType

class PaymentReceiptController extends BaseController {

    PaymentReceiptService paymentReceiptService

    @Secured("permitAll")
    def show() {
        try {
            PaymentReceipt receipt = PaymentReceipt.query([uniqueId: params.paymentReceiptUniqueId]).get()
            return [receipt: receipt]
        } catch (Exception exception) {
            flash.message = "Ocorreu um erro ao buscar comprovante de pagamento, contate o desenvolvimento"
            flash.type = MessageType.ERROR
            log.error("PaymentReceiptController.show >> Erro ao consultar comprovante com parâmetros: [${params}] [Mensagem de erro]: ${exception.message}")
        }
    }
}
