package com.quickcharge.app.payment

import com.quickcharge.app.payer.Payer
import grails.validation.ValidationException
import utils.controller.BaseController
import utils.message.MessageType 

class PaymentController extends BaseController {
    
    PaymentService paymentService

    def index() {
        try {
            return [
                paymentList   : Payment.query([
                    customerId    : getCurrentCustomer().id,
                    deletedOnly   : params.deletedOnly,
                    includeDeleted: params.includeDeleted
                ]).list(),

                deletedOnly   : params.deletedOnly,
                includeDeleted: params.includeDeleted
            ]
        } catch (Exception exception) {
            flash.message = "Ocorreu um erro ao buscar cobranças"
            flash.type = MessageType.ERROR
            log.error("PaymentController.index >> Erro ao consultar cobranças com parâmetros: [${params}] [Mensagem de erro]: ${exception.message}")
            throw new Exception()
        }
    }

    def create() {
        return [payerList: Payer.query([customerId: getCurrentCustomer().id]).list()]
    }

    def save() {
        try {
            paymentService.save(params, getCurrentCustomer())
            flash.message = "Cobrança criada com sucesso"
            flash.type = MessageType.SUCCESS
            redirect([action: "index", params: params])
        } catch (ValidationException validationException) {
            this.validateExceptionHandler(validationException)
            redirect([action: "create", params: params])
        } catch (Exception exception) {
            flash.message = "Ocorreu um erro ao criar cobrança"
            flash.type = MessageType.ERROR
            log.error("PaymentController.save >> Erro ao salvar cobrança com os parâmetros: [${params}] [Mensagem de erro]: ${exception.message}")
            throw new Exception()
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
            flash.message = "Ocorreu um erro ao remover cobrança"
            flash.type = MessageType.ERROR
            log.error("PaymentController.delete >> Erro ao remover cobrança com parâmetros: [${params}] [Mensagem de erro]: ${exception.message}")
            throw new Exception()
        } finally {
            redirect([
                action: "index",
                params: [
                    deletedOnly   : params.deletedOnly,
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
            flash.message = "Ocorreu um erro ao restaurar cobrança"
            flash.type = MessageType.ERROR
            log.error("PaymentController.restore >> Erro ao restaurar cobrança com parâmetros: [${params}] [Mensagem de erro]: ${exception.message}")
            throw new Exception()
        } finally {
            redirect([
                action: "index",
                params: [
                    deletedOnly   : params.deletedOnly,
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
            flash.message = "Ocorreu um erro ao confirmar recebimento em dinheiro"
            flash.type = MessageType.ERROR
            log.error("PaymentController.receivedInCash >> Erro ao confirmar recebimento em dinheiro com parâmetros: [${params}] [Mensagem de erro]: ${exception.message}")
            throw new Exception()
        } finally {
            redirect([
                action: "index",
                params: [
                    deletedOnly   : params.deletedOnly,
                    includeDeleted: params.includeDeleted
                ]
            ])
        }
    }

    def edit() {
        try {
            return [payment: Payment.getById(params.long("id"), getCurrentCustomer().id)]
        } catch (Exception exception) {
            flash.message = "Ocorreu um erro ao buscar dados da cobrança"
            flash.type = MessageType.ERROR
            log.error("PaymentController.edit >> Erro ao consultar cobrança com os parâmetros: [${params}] [Mensagem de erro]: ${exception.message}")
            throw new Exception()
        }
    }

    def update() {
        try {
            paymentService.update(params, getCurrentCustomer())
            flash.message = "Cobrança alterada com sucesso"
            flash.type = MessageType.SUCCESS
            redirect([action: "index"])
        } catch (ValidationException validationException) {
            this.validateExceptionHandler(validationException)
            redirect([action: "edit", params: params])
        } catch (Exception exception) {
            flash.message = "Ocorreu um erro ao alterar cobrança"
            flash.type = MessageType.ERROR
            log.error("PaymentController.update >> Erro ao alterar cobrança com os parâmetros: [${params}] [Mensagem de erro]: ${exception.message}")
            throw new Exception()
        }
    }
}
