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
            payerService.save(params)
            flash.message = !params.id
                ? "Pagador criado com sucesso"
                : "Pagador editado com sucesso"
        } catch (ValidationException validationException) {
            flash.message = validationException.errors.allErrors.first().defaultMessage
        } catch (Exception exception) {
            flash.message = !params.id
                ? "Ocorreu um erro ao criar pagador, contate o desenvolvimento"
                : "Ocorreu um erro ao editar pagador, contate o desenvolvimento"
        } finally {
            redirect([
                action: !params.id ? "create" : "edit"
                params: !params.id ? params : [
                    id: params.id,
                    customerId: params.customerId
                ]
            ])
        }
    }

}
