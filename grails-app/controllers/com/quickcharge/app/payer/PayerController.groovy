package com.quickcharge.app.payer

import grails.validation.ValidationException
import utils.controller.BaseController 

class PayerController extends BaseController {

    PayerService payerService
    
    def create() {
        return params
    }
    
    def edit() {
        try {
            return [payer: Payer.getById(params.long("id"), getCurrentCustomer().id)]
        } catch (Exception exception) {
            error("Ocorreu um erro ao buscar dados do cliente")
            log.error("PayerController.edit >> Erro ao consultar pagador com os parâmetros: [${params}] [Mensagem de erro]: ${exception.message}")
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
            error("Ocorreu um erro ao buscar clientes")
            log.error("PayerController.index >> Erro ao consultar pagadores com parâmetros: [${params}] [Mensagem de erro]: ${exception.message}")
        }
    }
    
    def delete() {
        try {
            payerService.delete(params, getCurrentCustomer())
            success("Cliente removido com sucesso")
        } catch (ValidationException validationException) {
            this.validateExceptionHandler(validationException)
        } catch (Exception exception) {
            error("Ocorreu um erro ao remover cliente")
            log.error("PayerController.delete >> Erro ao remover pagador com parâmetros: [${params}] [Mensagem de erro]: ${exception.message}")
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
            success("Cliente restaurado com sucesso")
        } catch (ValidationException validationException) {
            this.validateExceptionHandler(validationException)
        } catch (Exception exception) {
            error("Ocorreu um erro ao restaurar cliente")
            log.error("PayerController.restore >> Erro ao restaurar pagador com parâmetros: [${params}] [Mensagem de erro]: ${exception.message}")
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
            success("Cliente criado com sucesso")
            redirect([action: "index", params: [id: payer.id]])
        } catch (ValidationException validationException) {
            this.validateExceptionHandler(validationException)
            redirect([action: "create", params: params])
        } catch (Exception exception) {
            error("Ocorreu um erro ao criar cliente")
            log.error("PayerController.save >> Erro ao salvar pagador com parâmetros: [${params}] [Mensagem de erro]: ${exception.message}")
        }
    }
    
    def update() {
        try {
            payerService.update(params, getCurrentCustomer())
            success("Cliente alterado com sucesso")
        } catch (ValidationException validationException) {
            this.validateExceptionHandler(validationException)
        } catch (Exception exception) {
            error("Ocorreu um erro ao alterar cliente")
            log.error("PayerController.update >> Erro ao alterar pagador com parâmetros: [${params}] [Mensagem de erro]: ${exception.message}")
        } finally {
            redirect([
                action: "edit",
                params: [id: params.id]
            ])
        }
    }
}