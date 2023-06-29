package com.quickcharge.app.payment

import com.quickcharge.app.payer.Payer
import grails.validation.ValidationException
import utils.controller.BaseController 

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
            error("Ocorreu um erro ao buscar cobranças")
            log.error("PaymentController.index >> Erro ao consultar cobranças com parâmetros: [${params}] [Mensagem de erro]: ${exception.message}")
        }
    }

    def create() {
        return [payerList: Payer.query([customerId: getCurrentCustomer().id]).list()]
    }

    def save() {
        try {
            paymentService.save(params, getCurrentCustomer())
            success("Cobrança criada com sucesso")
            redirect([action: "index", params: params])
        } catch (ValidationException validationException) {
            this.validateExceptionHandler(validationException)
            redirect([action: "create", params: params])
        } catch (Exception exception) {
            error("Ocorreu um erro ao criar cobrança")
            log.error("PaymentController.save >> Erro ao salvar cobrança com os parâmetros: [${params}] [Mensagem de erro]: ${exception.message}")
        }
    }

    def delete() {
        try {
            paymentService.delete(params, getCurrentCustomer())
            success("Cobrança removida com sucesso")
        } catch (ValidationException validationException) {
            this.validateExceptionHandler(validationException)
        } catch (Exception exception) {
            error("Ocorreu um erro ao remover cobrança")
            log.error("PaymentController.delete >> Erro ao remover cobrança com parâmetros: [${params}] [Mensagem de erro]: ${exception.message}")
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
            success("Cobrança restaurada com sucesso")
        } catch (ValidationException validationException) {
            this.validateExceptionHandler(validationException)
        } catch (Exception exception) {
            error("Ocorreu um erro ao restaurar cobrança")
            log.error("PaymentController.restore >> Erro ao restaurar cobrança com parâmetros: [${params}] [Mensagem de erro]: ${exception.message}")
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
            success("Recebimento em dinheiro confirmado com sucesso")
        } catch (ValidationException validationException) {
            this.validateExceptionHandler(validationException)
        } catch (Exception exception) {
            error("Ocorreu um erro ao confirmar recebimento em dinheiro")
            log.error("PaymentController.receivedInCash >> Erro ao confirmar recebimento em dinheiro com parâmetros: [${params}] [Mensagem de erro]: ${exception.message}")
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
            error("Ocorreu um erro ao buscar dados da cobrança")
            log.error("PaymentController.edit >> Erro ao consultar cobrança com os parâmetros: [${params}] [Mensagem de erro]: ${exception.message}")
        }
    }

    def update() {
        try {
            paymentService.update(params, getCurrentCustomer())
            success("Cobrança alterada com sucesso")
            redirect([action: "index"])
        } catch (ValidationException validationException) {
            this.validateExceptionHandler(validationException)
            redirect([action: "edit", params: params])
        } catch (Exception exception) {
            error("Ocorreu um erro ao alterar cobrança")
            log.error("PaymentController.update >> Erro ao alterar cobrança com os parâmetros: [${params}] [Mensagem de erro]: ${exception.message}")
        }
    }
}
