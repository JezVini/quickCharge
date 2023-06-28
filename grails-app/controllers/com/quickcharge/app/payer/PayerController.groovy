package com.quickcharge.app.payer

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
            return [payer: Payer.getById(params.long("id"), getCurrentCustomer().id)]
        } catch (Exception exception) {
            flash.message = "Ocorreu um erro ao buscar dados do cliente"
            flash.type = MessageType.ERROR
            log.error("PayerController.edit >> Erro ao consultar pagador com os parâmetros: [${params}] [Mensagem de erro]: ${exception.message}")
            throw new Exception()
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
            flash.message = "Ocorreu um erro ao buscar clientes"
            flash.type = MessageType.ERROR
            log.error("PayerController.index >> Erro ao consultar pagadores com parâmetros: [${params}] [Mensagem de erro]: ${exception.message}")
            throw new Exception()
        }
    }
    
    def delete() {
        try {
            payerService.delete(params, getCurrentCustomer())
            flash.message = "Cliente removido com sucesso"
            flash.type = MessageType.SUCCESS
        } catch (ValidationException validationException) {
            this.validateExceptionHandler(validationException)
        } catch (Exception exception) {
            flash.message = "Ocorreu um erro ao remover cliente"
            flash.type = MessageType.ERROR
            log.error("PayerController.delete >> Erro ao remover pagador com parâmetros: [${params}] [Mensagem de erro]: ${exception.message}")
            throw new Exception()
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
            payerService.restore(params, getCurrentCustomer())
            flash.message = "Cliente restaurado com sucesso"
            flash.type = MessageType.SUCCESS
        } catch (ValidationException validationException) {
            this.validateExceptionHandler(validationException)
        } catch (Exception exception) {
            flash.message = "Ocorreu um erro ao restaurar cliente"
            flash.type = MessageType.ERROR
            log.error("PayerController.restore >> Erro ao restaurar pagador com parâmetros: [${params}] [Mensagem de erro]: ${exception.message}")
            throw new Exception()
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
            Payer payer = payerService.save(params, getCurrentCustomer())
            flash.message = "Cliente criado com sucesso"
            flash.type = MessageType.SUCCESS
            redirect([action: "index", params: [id: payer.id]])
        } catch (ValidationException validationException) {
            this.validateExceptionHandler(validationException)
            redirect([action: "create", params: params])
        } catch (Exception exception) {
            flash.message = "Ocorreu um erro ao criar cliente"
            flash.type = MessageType.ERROR
            log.error("PayerController.save >> Erro ao salvar pagador com parâmetros: [${params}] [Mensagem de erro]: ${exception.message}")
            throw new Exception()
        }
    }
    
    def update() {
        try {
            payerService.update(params, getCurrentCustomer())
            flash.message = "Cliente alterado com sucesso"
            flash.type = MessageType.SUCCESS
        } catch (ValidationException validationException) {
            this.validateExceptionHandler(validationException)
        } catch (Exception exception) {
            flash.message = "Ocorreu um erro ao alterar cliente"
            flash.type = MessageType.ERROR
            log.error("PayerController.update >> Erro ao alterar pagador com parâmetros: [${params}] [Mensagem de erro]: ${exception.message}")
            throw new Exception()
        } finally {
            redirect([
                action: "edit",
                params: [id: params.id]
            ])
        }
    }
}