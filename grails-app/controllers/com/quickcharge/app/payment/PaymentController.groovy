package com.quickcharge.app.payment

import com.quickcharge.app.payer.Payer
import grails.plugin.springsecurity.SpringSecurityService
import grails.validation.ValidationException
import utils.message.MessageType
import utils.payment.BillingType

class PaymentController {

    PaymentService paymentService
    SpringSecurityService springSecurityService

    def create() {
        Long customerId = Long.valueOf(springSecurityService.getCurrentUser().customer.id)
        List<Payer> payerList = Payer.query([customerId: customerId]).list()
        return [payerList: payerList, billingType: BillingType]
    }

    def save() {
        try {
            paymentService.save(params)
            flash.message = "Cobrança criada com sucesso"
            flash.type = MessageType.SUCCESS
        } catch (ValidationException validationException) {
            flash.message = validationException.errors.allErrors.first().defaultMessage
            flash.type = MessageType.WARNING
        } catch (Exception exception) {
            flash.message = "Ocorreu um erro ao criar cobrança, contate o desenvolvimento"
            flash.type = MessageType.ERROR
            log.info("PaymentController.save >> Erro ao salvar cobrança com os parâmetros: [${params}] [Mensagem de erro]: ${exception.message}")
        } finally {
            redirect([action: "create", params: params])
        }
    }
}
