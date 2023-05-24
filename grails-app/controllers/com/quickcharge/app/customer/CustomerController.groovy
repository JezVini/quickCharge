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
            flash.message = "Conta criada com sucesso"
        } catch (ValidationException validationException) {
            flash.message = validationException.errors.allErrors.first().defaultMessage
        } catch (Exception exception) {
            flash.message = "Ocorreu um erro ao criar conta, contate o desenvolvimento"
            log.info("CustomerController.save >> Erro ao salvar conta com os parâmetros: [${params}]")
        } finally {
            redirect([
                action: "create",
                params: params
            ])
        }
    }
}
