package com.quickcharge.app.payer

import grails.gorm.PagedResultList
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
            redirect(uri: request.getHeader('referer'))
        }
    }
    
    def index() {
        try {
            Map parsedParams = [
                offset: getOffSet(),
                max: getMax(),
                deletedOnly: params.deletedOnly,
                includeDeleted: params.includeDeleted
            ]

            PagedResultList pagedResultList = payerService.paginatedPayer(parsedParams, getCurrentCustomer())

            return parsedParams + [
                payerList: pagedResultList,
                total: pagedResultList.getTotalCount()
            ]
        } catch (Exception exception) {
            error("Ocorreu um erro ao buscar clientes")
            log.error("PayerController.index >> Erro ao consultar pagadores com parâmetros: [${params}] [Mensagem de erro]: ${exception.message}")
            redirect(uri: request.getHeader('referer'))
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
                    includeDeleted: params.includeDeleted,
                    offset: params.offset,
                    max: params.max
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
                    includeDeleted: params.includeDeleted,
                    offset: params.offset,
                    max: params.max
                ]
            ])
        }
    }
    
    def save() {
        try {
            Payer payer = payerService.save(params, getCurrentCustomer())
            success("Cliente criado com sucesso")
            redirect([action: "index"])
        } catch (ValidationException validationException) {
            this.validateExceptionHandler(validationException)
            redirect([action: "create", params: params])
        } catch (Exception exception) {
            error("Ocorreu um erro ao criar cliente")
            log.error("PayerController.save >> Erro ao salvar pagador com parâmetros: [${params}] [Mensagem de erro]: ${exception.message}")
            redirect([action: "create", params: params])
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