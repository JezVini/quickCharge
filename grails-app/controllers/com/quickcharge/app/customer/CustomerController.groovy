package com.quickcharge.app.customer

import grails.plugin.springsecurity.annotation.Secured
import grails.validation.ValidationException
import utils.controller.BaseController
import utils.message.MessageType

class CustomerController extends BaseController{

    def customerService

    @Secured(['permitAll'])
    def create() {
        return params
    }

    def edit() {
        return [customer: getCurrentCustomer()]
    }

    @Secured(['permitAll'])
    def save() {
        try {
            customerService.save(params)
            flash.message = "Conta criada com sucesso"
            flash.type = MessageType.SUCCESS
            redirect([controller: "login", action: "auth"])
        } catch (ValidationException validationException) {
            this.validateExceptionHandler(validationException)
            redirect([action: "create", params: params])
        } catch (Exception exception) {
            flash.message = "Ocorreu um erro ao criar conta, contate o desenvolvimento"
            flash.type = MessageType.ERROR
            log.info("CustomerController.save >> Erro ao salvar conta com os parâmetros: [${params}]")
            redirect([action: "create", params: params])
        }
    }

    def update() {
        try {
            customerService.update(params, getCurrentCustomer())
            flash.message = "Cadastro alterado com sucesso"
            flash.type = MessageType.SUCCESS
        } catch (ValidationException validationException) {
            this.validateExceptionHandler(validationException)
        } catch (Exception exception) {
            flash.message = "Ocorreu um erro, contate o desenvolvimento"
            log.info("CustomerController.save >> Erro ao alterar cadastro com os parâmetros: [${params}]")
            flash.type = MessageType.ERROR
        } finally {
            redirect([action: "edit"])
        }
    }
}