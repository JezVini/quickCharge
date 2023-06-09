package com.quickcharge.app.payer

import com.quickcharge.app.customer.Customer
import grails.plugin.springsecurity.SpringSecurityService
import grails.validation.ValidationException
import utils.controller.BaseController
import utils.message.MessageType

class PayerController extends BaseController {

    PayerService payerService
    SpringSecurityService springSecurityService
    
    def create() {
        return params
    }
    
    def edit() {
        try {
            Long id = params.long("id")
            Long customerId = Long.valueOf(springSecurityService.getCurrentUser().customer.id)
            Map parsedParams = [id: id, customerId: customerId]
            
            if (!springSecurityService.getCurrentUser().customer) {
                flash.message = "Cliente inexistente"
                flash.type = MessageType.WARNING
                return parsedParams
            }

            Payer payer = Payer.query([id: id, customerId: customerId]).get()
            if (!payer) {
                flash.message = "Não foi possível buscar os dados do pagador"
                flash.type = MessageType.WARNING
                return parsedParams
            }   

            return parsedParams + [payer: payer]
        } catch (Exception exception) {
            flash.message = "Ocorreu um erro ao buscar dados do pagador, contate o desenvolvimento"
            flash.type = MessageType.ERROR
            log.info("PayerController.edit >> Erro ao consultar pagador com os parâmetros: [${params}] [Mensagem de erro]: ${exception.message}")
        }
    }

    def index () {
        try {
            Long customerId = Long.valueOf(springSecurityService.getCurrentUser().customer.id)

            if (!springSecurityService.getCurrentUser().customer) {
                flash.message = "Cliente inexistente"
                flash.type = MessageType.WARNING
                return
            }
            
            return [
                payerList: Payer.query([
                    customerId: customerId,
                    deletedOnly: params.deletedOnly,
                    includeDeleted: params.includeDeleted
                ]).list(),
                
                customerId: customerId,
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
            Long customerId = Long.valueOf(springSecurityService.getCurrentUser().customer.id)
            redirect([
                action: "index",
                params: [
                    customerId: customerId,
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
            Long customerId = Long.valueOf(springSecurityService.getCurrentUser().customer.id)
            redirect([
                action: "index",
                params: [
                    customerId: customerId,
                    deletedOnly: params.deletedOnly,
                    includeDeleted: params.includeDeleted
                ]
            ])
        }
    }
    
    def save() {
        try {
            payerService.save(params)
            flash.message = "Pagador criado com sucesso"
            flash.type = MessageType.SUCCESS
        } catch (ValidationException validationException) {
            this.validateExceptionHandler(validationException)
        } catch (Exception exception) {
            flash.message = "Ocorreu um erro ao criar pagador, contate o desenvolvimento"
            flash.type = MessageType.ERROR
            log.info("PayerController.save >> Erro ao salvar pagador com os parâmetros: [${params}] [Mensagem de erro]: ${exception.message}")
        } finally {
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
            Long customerId = Long.valueOf(springSecurityService.getCurrentUser().customer.id)
            redirect([
                action: "edit",
                params: [
                    id: params.id,
                    customerId: customerId
                ]
            ])
        }
    }
}