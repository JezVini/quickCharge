package com.quickcharge.app.customer

import grails.plugin.springsecurity.annotation.Secured
import grails.validation.ValidationException
import utils.controller.BaseController
import utils.message.MessageType

class CustomerController extends BaseController {

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
            flash.message = "Ocorreu um erro ao criar conta"
            flash.type = MessageType.ERROR
            log.error("CustomerController.save >> Erro ao salvar conta com os parâmetros: [${params}] [Mensagem de erro]: ${exception.message}")
            throw new Exception()
        }
    }

    def update() {
        try {
            customerService.update(params, getCurrentCustomer())
            flash.message = "Cadastro alterado com sucesso"
            flash.type = MessageType.SUCCESS
            redirect([controller: "login", action: "auth"])
        } catch (ValidationException validationException) {
            this.validateExceptionHandler(validationException)
            redirect([action: "edit"])
        } catch (Exception exception) {
            flash.message = "Ocorreu um erro ao alterar cadastro"
            flash.type = MessageType.ERROR
            log.error("CustomerController.save >> Erro ao alterar cadastro com os parâmetros: [${params}] [Mensagem de erro]: ${exception.message}")
            throw new Exception()
        }
    }
}