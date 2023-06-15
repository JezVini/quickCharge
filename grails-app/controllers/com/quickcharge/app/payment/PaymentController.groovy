package com.quickcharge.app.payment

import com.quickcharge.app.payer.Payer
import grails.validation.ValidationException
import utils.controller.BaseController
import utils.message.MessageType
import utils.payment.BillingType
import utils.payment.PaymentStatus

class PaymentController extends BaseController{

    PaymentService paymentService

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
                includeDeleted: params.includeDeleted,
                paymentStatus: PaymentStatus
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
            Long customerId = Long.valueOf(springSecurityService.getCurrentUser().customer.id)
            paymentService.receiveInCash(params, customerId)
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

    def edit() {
        try {
            Long customerId = getCurrentCustomer().id
            Long id = params.long("id")
            Payment payment = Payment.query([customerId: customerId, id: id]).get()
            
            if (payment.status != PaymentStatus.PENDING) redirect(action: "index")
            
            return [payment: payment]
        } catch (Exception exception) {
            flash.message = "Ocorreu um erro ao buscar dados da cobrança, contate o desenvolvimento"
            flash.type = MessageType.ERROR
            log.info("PaymentController.edit >> Erro ao consultar cobrança com os parâmetros: [${params}] [Mensagem de erro]: ${exception.message}")
        }
    }

    def update() {
        try {
            Long customerId = getCurrentCustomer().id
            paymentService.update(params, customerId)
            flash.message = "Cobrança alterada com sucesso"
            flash.type = MessageType.SUCCESS
        } catch (ValidationException validationException) {
            this.validateExceptionHandler(validationException)
        } catch (Exception exception) {
            flash.message = "Ocorreu um erro ao alterar cobrança, contate o desenvolvimento"
            flash.type = MessageType.ERROR
            log.info("PaymentController.update >> Erro ao alterar cobrança com os parâmetros: [${params}] [Mensagem de erro]: ${exception.message}")
        } finally {
            redirect([action: "edit", params: params])
        }
    }
}
