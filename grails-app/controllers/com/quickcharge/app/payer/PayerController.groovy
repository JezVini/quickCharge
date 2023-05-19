package com.quickcharge.app.payer

import grails.validation.ValidationException

class PayerController {

    PayerService payerService

    def create() {
        return params
    }

    def save() {
        try {
            payerService.save(params)
            flash.message = "Registro criado com sucesso"
        } catch (ValidationException validationException) {
            flash.message = validationException.errors.allErrors.first().defaultMessage
        } catch (Exception exception) {
            flash.message = "Ocorreu um erro, contate o desenvolvimento"
        } finally {
            redirect([
                action: "create",
                params: params
            ])
        }
    }

}
