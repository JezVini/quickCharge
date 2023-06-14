package com.quickcharge.app.payment

import com.quickcharge.app.payer.Payer
import grails.validation.ValidationException
import utils.controller.BaseController
import utils.message.MessageType
import utils.payment.BillingType 

class PaymentController extends BaseController{

    PaymentService paymentService
    
    def index() {
        try {
            return [paymentList: Payment.query([customerId: getCurrentCustomer().id]).list()]
        } catch (Exception exception) {
            flash.message = "Ocorreu um erro ao buscar cobranças, contate o desenvolvimento"
            flash.type = MessageType.ERROR
            log.info("PaymentController.index >> Erro ao consultar cobranças com parâmetros: [${params}] [Mensagem de erro]: ${exception.message}")
        }
    }
    
    def create() {
        return [payerList: Payer.query([customerId: getCurrentCustomer().id]).list(), billingType: BillingType]
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
    
    def delete() {
        try {
            paymentService.delete(params, getCurrentCustomer())
            flash.message = "Cobrança removida com sucesso"
            flash.type = MessageType.SUCCESS
        } catch (ValidationException validationException) {
            this.validateExceptionHandler(validationException)
        } catch (Exception exception) {
            flash.message = "Ocorreu um erro ao remover cobrança, contate o desenvolvimento"
            flash.type = MessageType.ERROR
            log.info("PaymentController.delete >> Erro ao remover cobrança com parâmetros: [${params}] [Mensagem de erro]: ${exception.message}")
        } finally {
            redirect([
                action: "index",
                params: [
                    deletedOnly: params.deletedOnly,
                    includeDeleted: params.includeDeleted
                ]
            ])
        }
    }
}
