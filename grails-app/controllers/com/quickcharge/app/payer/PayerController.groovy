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
            log.info("PayerController.save >> Erro ao salvar pagador: ${params}")
        } finally {
            redirect([
                action: "create",
                params: params
            ])
        }
    }

    def update() {
        try {
            payerService.saveOrUpdate(params)
            flash.message = "Pagador editado com sucesso"
        } catch (ValidationException validationException) {
            flash.message = validationException.errors.allErrors.first().defaultMessage
        } catch (Exception exception) {
            flash.message = "Ocorreu um erro ao editar pagador, contate o desenvolvimento"
            log.info("PayerController.save >> Erro ao editar pagador: ${params}")
        } finally {
            redirect([
                action: "edit",
                params: [
                    id: params.id,
                    customerId: params.customerId
                ]
            ])
        }
    }

}
