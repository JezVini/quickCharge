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
            flash.message = "Pagador criado com sucesso"
        } catch (ValidationException validationException) {
            flash.message = validationException.errors.allErrors.first().defaultMessage
        } catch (Exception exception) {
            flash.message = "Ocorreu um erro ao criar pagador, contate o desenvolvimento"
            log.info("PayerController.save >> Erro ao salvar pagador: ${params}")
        } finally {
            redirect([
                action: "create",
                params: params
            ])
        }
    }
}