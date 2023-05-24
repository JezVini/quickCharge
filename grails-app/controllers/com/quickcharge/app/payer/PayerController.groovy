package com.quickcharge.app.payer

import grails.validation.ValidationException

class PayerController {

    PayerService payerService

    def create() {
        return params
    }

    def edit() {
        Long id = params.long("id")
        Long customerId = params.long("customerId")

        Payer payer = payerService.get(id, customerId)
        return [payer: payer]
    }

    def save() {
        try {
            payerService.saveOrUpdate(params)
            flash.message = "Pagador criado com sucesso"
        } catch (ValidationException validationException) {
            flash.message = validationException.errors.allErrors.first().defaultMessage
        } catch (Exception exception) {
            flash.message = "Ocorreu um erro ao criar pagador, contate o desenvolvimento"
        } finally {
            redirect([
                action: "create",
                params: params
            ])
        }
    }

}
