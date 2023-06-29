package com.quickcharge.app.customer

import grails.plugin.springsecurity.annotation.Secured
import grails.validation.ValidationException
import utils.controller.BaseController 

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
            success("Conta criada com sucesso")
            redirect([controller: "login", action: "auth"])
        } catch (ValidationException validationException) {
            this.validateExceptionHandler(validationException)
            redirect([action: "create", params: params])
        } catch (Exception exception) {
            error("Ocorreu um erro ao criar conta")
            log.error("CustomerController.save >> Erro ao salvar conta com os parâmetros: [${params}] [Mensagem de erro]: ${exception.message}")
        }
    }

    def update() {
        try {
            customerService.update(params, getCurrentCustomer())
            success("Cadastro alterado com sucesso")
            redirect([controller: "login", action: "auth"])
        } catch (ValidationException validationException) {
            this.validateExceptionHandler(validationException)
            redirect([action: "edit"])
        } catch (Exception exception) {
            error("Ocorreu um erro ao alterar cadastro")
            log.error("CustomerController.save >> Erro ao alterar cadastro com os parâmetros: [${params}] [Mensagem de erro]: ${exception.message}")
        }
    }
}