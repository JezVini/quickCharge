package com.quickcharge.app.customer

import grails.validation.ValidationException
import utils.controller.BaseController
import utils.message.MessageType

class CustomerController extends BaseController{

    def customerService

    def create() {
        return params
    }

    def edit() {
        Customer customer = Customer.get(params.long("id"))
        return [customer: customer]
    }

    def save() {
        try {
            customerService.save(params)
            flash.message = "Conta criada com sucesso"
            flash.type = MessageType.SUCCESS
        } catch (ValidationException validationException) {
            this.validateExceptionHandler(validationException)
        } catch (Exception exception) {
            flash.message = "Ocorreu um erro ao criar conta, contate o desenvolvimento"
            flash.type = MessageType.ERROR
            log.info("CustomerController.save >> Erro ao salvar conta com os parâmetros: [${params}]")
        } finally {
            redirect([
                action: "create",
                params: params
            ])
        }
    }

    def update() {
        try {
            customerService.update(params)
            flash.message = "Conta alterada com sucesso"
        } catch (ValidationException validationException) {
            this.validateExceptionHandler(validationException)
        } catch (Exception exception) {
            flash.message = "Ocorreu um erro, contate o desenvolvimento"
            log.info("CustomerController.save >> Erro ao alterar conta com os parâmetros: [${params}]")
        } finally {
            redirect([
                action: "edit",
                params: [id: params.id]
            ])
        }
    }
}