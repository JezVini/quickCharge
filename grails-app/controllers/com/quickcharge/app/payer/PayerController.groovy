package com.quickcharge.app.payer

import grails.validation.ValidationException

class PayerController {

    PayerService payerService

    def create() {
        return params
    }

    def edit() {
        Payer payer = payerService.get(
            params.long("id"),
            params.long("customerId")
        )
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
            log.info("PayerController.save >> Erro ao salvar pagador com parametros: ${params}")
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
            log.info("PayerController.update >> Erro ao editar pagador com parameros ${params}")
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