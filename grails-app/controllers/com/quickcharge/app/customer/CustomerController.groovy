package com.quickcharge.app.customer

import grails.validation.ValidationException

class CustomerController {

    def customerService

    def create() {
        return params
    }

    def save() {
        try {
            customerService.save(params)
            flash.message = "Registro criado com sucesso"
        } catch (ValidationException validationException) {
            flash.message = validationException.errors.allErrors.first().defaultMessage
        } catch (Exception exception) {
            flash.message = "Ocorreu um erro, contate o desenvolvimento"
            log.info("CustomerController.save >> Erro ao salvar id ${params.id}")
        } finally {
            redirect([
                action: "create",
                params: params
            ])
        }
    }
}
