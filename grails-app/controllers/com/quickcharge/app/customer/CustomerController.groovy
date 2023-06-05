package com.quickcharge.app.customer


import grails.plugin.springsecurity.annotation.Secured
import grails.validation.ValidationException
import utils.message.MessageType

class CustomerController {

    def customerService
    def springSecurityService

    @Secured(['permitAll'])
    def create() {
        return params
    }
    
    def edit() {
        Long customerId = Long.valueOf(springSecurityService.getCurrentUser().customer.id)
        Customer customer = Customer.query([id: customerId]).get()
        return [customer: customer]
    }

    @Secured(['permitAll'])
    def save() {
        try {
            customerService.save(params)
            flash.message = "Conta criada com sucesso"
            flash.type = MessageType.SUCCESS
        } catch (ValidationException validationException) {
            flash.message = validationException.errors.allErrors.first().defaultMessage
            flash.type = MessageType.WARNING
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
            flash.message = "Cadastro alterado com sucesso"
        } catch (ValidationException validationException) {
            flash.message = validationException.errors.allErrors.first().defaultMessage
        } catch (Exception exception) {
            flash.message = "Ocorreu um erro, contate o desenvolvimento"
            log.info("CustomerController.save >> Erro ao alterar cadastro com os parâmetros: [${params}]")
        } finally {
            redirect([
                action: "edit",
                params: [id: params.id]
            ])
        }
    }
}