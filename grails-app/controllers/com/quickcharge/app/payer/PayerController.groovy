package com.quickcharge.app.payer

import grails.validation.ValidationException
import utils.message.MessageType

class PayerController {

    PayerService payerService

    def create() {
        return params
    }

    def save() {
        try {
            payerService.save(params)
            flash.message = "Pagador criado com sucesso"
            flash.type = MessageType.SUCCESS
        } catch (ValidationException validationException) {
            flash.message = validationException.errors.allErrors.first().defaultMessage
            flash.type = MessageType.WARNING
        } catch (Exception exception) {
            flash.message = "Ocorreu um erro ao criar pagador, contate o desenvolvimento"
            flash.type = MessageType.ERROR
            log.info("PayerController.save >> Erro ao salvar pagador: ${params}")
        } finally {
            redirect([
                action: "create",
                params: params
            ])
        }
    }
}