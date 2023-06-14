package com.quickcharge.app.payer

import com.quickcharge.app.customer.Customer
import grails.validation.ValidationException
import utils.controller.BaseController
import utils.message.MessageType

class PayerController extends BaseController {

    PayerService payerService
    
    def create() {
        return params
    }
    
    def edit() {
        try {
            return [payer: Payer.query([id: params.long("id"), customerId: getCurrentCustomer().id]).get()]
        } catch (Exception exception) {
            flash.message = "Ocorreu um erro ao buscar dados do pagador, contate o desenvolvimento"
            flash.type = MessageType.ERROR
            log.info("PayerController.edit >> Erro ao consultar pagador com os parâmetros: [${params}] [Mensagem de erro]: ${exception.message}")
        }
    }

    def index () {
        try {
            return [
                payerList: Payer.query([
                    customerId: getCurrentCustomer().id,
                    deletedOnly: params.deletedOnly,
                    includeDeleted: params.includeDeleted
                ]).list(),
                
                deletedOnly: params.deletedOnly,
                includeDeleted: params.includeDeleted
            ]
            
        } catch (Exception exception) {
            flash.message = "Ocorreu um erro ao buscar pagadores, contate o desenvolvimento"
            flash.type = MessageType.ERROR
            log.info("PayerController.index >> Erro ao consultar pagadores com parâmetros: [${params}] [Mensagem de erro]: ${exception.message}")
        }
    }
    
    def delete() {
        try {
            payerService.delete(params)
            flash.message = "Pagador removido com sucesso"
            flash.type = MessageType.SUCCESS
        } catch (ValidationException validationException) {
            this.validateExceptionHandler(validationException)
        } catch (Exception exception) {
            flash.message = "Ocorreu um erro ao remover pagador, contate o desenvolvimento"
            flash.type = MessageType.ERROR
            log.info("PayerController.delete >> Erro ao remover pagador com parâmetros: [${params}] [Mensagem de erro]: ${exception.message}")
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
            payerService.restore(params)
            flash.message = "Pagador restaurado com sucesso"
            flash.type = MessageType.SUCCESS
        } catch (ValidationException validationException) {
            this.validateExceptionHandler(validationException)
        } catch (Exception exception) {
            flash.message = "Ocorreu um erro ao restaurar pagador, contate o desenvolvimento"
            flash.type = MessageType.ERROR
            log.info("PayerController.restore >> Erro ao restaurar pagador com parâmetros: [${params}] [Mensagem de erro]: ${exception.message}")
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
    
    def save() {
        try {
            Payer payer = payerService.save(params)
            flash.message = "Pagador criado com sucesso"
            flash.type = MessageType.SUCCESS
            redirect([action: "edit", params: [id: payer.id]])
        } catch (ValidationException validationException) {
            this.validateExceptionHandler(validationException)
            redirect([action: "create", params: params])
        } catch (Exception exception) {
            flash.message = "Ocorreu um erro ao criar pagador, contate o desenvolvimento"
            flash.type = MessageType.ERROR
            log.info("PayerController.save >> Erro ao salvar pagador com os parâmetros: [${params}] [Mensagem de erro]: ${exception.message}")
            redirect([action: "create", params: params])
        }
    }
    
    def update() {
        try {
            payerService.update(params)
            flash.message = "Pagador alterado com sucesso"
            flash.type = MessageType.SUCCESS
        } catch (ValidationException validationException) {
            this.validateExceptionHandler(validationException)
        } catch (Exception exception) {
            flash.message = "Ocorreu um erro ao alterar pagador, contate o desenvolvimento"
            flash.type = MessageType.ERROR
            log.info("PayerController.update >> Erro ao alterar pagador com os parâmetros: [${params}] [Mensagem de erro]: ${exception.message}")
        } finally {
            redirect([
                action: "edit",
                params: [id: params.id]
            ])
        }
    }
}