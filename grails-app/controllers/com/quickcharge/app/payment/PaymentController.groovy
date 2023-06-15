package com.quickcharge.app.payment

import com.quickcharge.app.payer.Payer
import grails.validation.ValidationException
import utils.controller.BaseController
import utils.message.MessageType
import utils.payment.BillingType

class PaymentController extends BaseController{

    PaymentService paymentService
    
    def create() {
        List<Payer> payerList = Payer.query([customerId: getCurrentCustomer().id]).list()
        return [payerList: payerList, billingType: BillingType]
    }

    def save() {
        try {
            paymentService.save(params, getCurrentCustomer())
            flash.message = "Cobrança criada com sucesso"
            flash.type = MessageType.SUCCESS
        } catch (ValidationException validationException) {
            this.validateExceptionHandler(validationException)
        } catch (Exception exception) {
            flash.message = "Ocorreu um erro ao criar cobrança, contate o desenvolvimento"
            flash.type = MessageType.ERROR
            log.info("PaymentController.save >> Erro ao salvar cobrança com os parâmetros: [${params}] [Mensagem de erro]: ${exception.message}")
        } finally {
            redirect([action: "create", params: params])
        }
    }
}
