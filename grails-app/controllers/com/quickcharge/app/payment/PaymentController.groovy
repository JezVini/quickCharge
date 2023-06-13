package com.quickcharge.app.payment

import com.quickcharge.app.payer.Payer
import grails.plugin.springsecurity.SpringSecurityService
import grails.validation.ValidationException
import utils.controller.BaseController
import utils.message.MessageType
import utils.payment.BillingType

class PaymentController extends BaseController{

    PaymentService paymentService
    SpringSecurityService springSecurityService

    def index() {
        try {
            Long customerId = Long.valueOf(springSecurityService.getCurrentUser().customer.id)
            
            return [
                paymentList: Payment.query([
                    customerId: customerId,
                    deletedOnly: params.deletedOnly,
                    includeDeleted: params.includeDeleted
                ]).list(),

                deletedOnly: params.deletedOnly,
                includeDeleted: params.includeDeleted
            ]
        } catch (Exception exception) {
            flash.message = "Ocorreu um erro ao buscar cobranças, contate o desenvolvimento"
            flash.type = MessageType.ERROR
            log.info("PaymentController.index >> Erro ao consultar cobranças com parâmetros: [${params}] [Mensagem de erro]: ${exception.message}")
        }
    }
    
    def create() {
        Long customerId = Long.valueOf(springSecurityService.getCurrentUser().customer.id)
        List<Payer> payerList = Payer.query([customerId: customerId]).list()
        return [payerList: payerList, billingType: BillingType]
    }
    
    def save() {
        try {
            Long customerId = Long.valueOf(springSecurityService.getCurrentUser().customer.id)
            paymentService.save(params, customerId)
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
            Long customerId = Long.valueOf(springSecurityService.getCurrentUser().customer.id)
            paymentService.delete(params, customerId)
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
    
    def restore() {
        try {
            Long customerId = Long.valueOf(springSecurityService.getCurrentUser().customer.id)
            paymentService.restore(params, customerId)
            flash.message = "Cobrança restaurada com sucesso"
            flash.type = MessageType.SUCCESS
        } catch (ValidationException validationException) {
            this.validateExceptionHandler(validationException)
        } catch (Exception exception) {
            flash.message = "Ocorreu um erro ao restaurar cobrança, contate o desenvolvimento"
            flash.type = MessageType.ERROR
            log.info("PaymentController.restore >> Erro ao restaurar cobrança com parâmetros: [${params}] [Mensagem de erro]: ${exception.message}")
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

    def receiveInCash() {
        try {
            paymentService.receiveInCash(params)
            flash.message = "Recebimento em dinheiro confirmado com sucesso"
            flash.type = MessageType.SUCCESS
        } catch (ValidationException validationException) {
            this.validateExceptionHandler(validationException)
        } catch (Exception exception) {
            flash.message = "Ocorreu um erro ao confirmar recebimento em dinheiro, contate o desenvolvimento"
            flash.type = MessageType.ERROR
            log.info("PaymentController.receivedInCash >> Erro ao confirmar recebimento em dinheiro com parâmetros: [${params}] [Mensagem de erro]: ${exception.message}")
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
