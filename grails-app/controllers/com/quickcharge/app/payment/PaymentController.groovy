package com.quickcharge.app.payment

import com.quickcharge.app.customer.Customer
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
            return [
                paymentList: Payment.query([
                    customerId: getCurrentCustomer().id,
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
    
    def restore() {
        try {
            paymentService.restore(params, getCurrentCustomer())
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
            paymentService.receiveInCash(params, getCurrentCustomer())
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
            Payment payment = Payment.query([customerId: customerId, id: id, includeDeleted: true]).get()

            if (payment.status != PaymentStatus.PENDING || payment.deleted) {
                redirect(action: "index")
            }

            return [payment: payment,  billingType: BillingType]
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
