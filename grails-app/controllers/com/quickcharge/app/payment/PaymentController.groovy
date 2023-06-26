package com.quickcharge.app.payment

import com.quickcharge.app.payer.Payer
import grails.gorm.PagedResultList
import grails.validation.ValidationException
import utils.controller.BaseController
import utils.message.MessageType 

class PaymentController extends BaseController {
    
    PaymentService paymentService

    def index() {
        try {
            Map parsedParams = [
                offset: getOffSet(),
                max: getMax(),
                deletedOnly: params.deletedOnly,
                includeDeleted: params.includeDeleted
            ]

            PagedResultList pagedResultList = paymentService.paginatedPayment(parsedParams, getCurrentCustomer())

            return parsedParams + [
                paymentList: pagedResultList,
                total: pagedResultList.getTotalCount()
            ]
        } catch (Exception exception) {
            flash.message = "Ocorreu um erro ao buscar cobranças, contate o desenvolvimento"
            flash.type = MessageType.ERROR
            log.info("PaymentController.index >> Erro ao consultar cobranças com parâmetros: [${params}] [Mensagem de erro]: ${exception.message}")
        }
    }
    
    def create() {
        return [payerList: Payer.query([customerId: getCurrentCustomer().id]).list()]
    }

    def save() {
        try {
            Payment payment = paymentService.save(params, getCurrentCustomer())
            flash.message = "Cobrança criada com sucesso"
            flash.type = MessageType.SUCCESS
            redirect([action: "edit", params: [id: payment.id]])
        } catch (ValidationException validationException) {
            this.validateExceptionHandler(validationException)
            redirect([action: "create", params: params])
        } catch (Exception exception) {
            flash.message = "Ocorreu um erro ao criar cobrança, contate o desenvolvimento"
            flash.type = MessageType.ERROR
            log.info("PaymentController.save >> Erro ao salvar cobrança com os parâmetros: [${params}] [Mensagem de erro]: ${exception.message}")
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
                    includeDeleted: params.includeDeleted,
                    offset: params.offset,
                    max: params.max
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
                    includeDeleted: params.includeDeleted,
                    offset: params.offset,
                    max: params.max
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
                    includeDeleted: params.includeDeleted,
                    offset: params.offset,
                    max: params.max
                ]
            ])
        }
    }

    def edit() {
        try {
            return [payment: Payment.getById(params.long("id"), getCurrentCustomer().id)]
        } catch (ValidationException validationException) {
            this.validateExceptionHandler(validationException) 
        } catch (Exception exception) {
            flash.message = "Ocorreu um erro ao buscar dados da cobrança, contate o desenvolvimento"
            flash.type = MessageType.ERROR
            log.error("PaymentController.edit >> Erro ao consultar cobrança com os parâmetros: [${params}] [Mensagem de erro]: ${exception.message}")
        }
    }

    def update() {
        try {
            paymentService.update(params, getCurrentCustomer())
            flash.message = "Cobrança alterada com sucesso"
            flash.type = MessageType.SUCCESS
        } catch (ValidationException validationException) {
            this.validateExceptionHandler(validationException)
        } catch (Exception exception) {
            flash.message = "Ocorreu um erro ao alterar cobrança, contate o desenvolvimento"
            flash.type = MessageType.ERROR
            log.info("PaymentController.update >> Erro ao alterar cobrança com os parâmetros: [${params}] [Mensagem de erro]: ${exception.message}")
        } finally {
            redirect([
                action: "edit",
                params: [id: params.id]
            ])
        }
    }
}
