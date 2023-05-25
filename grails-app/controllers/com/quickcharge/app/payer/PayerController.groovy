package com.quickcharge.app.payer

import grails.validation.ValidationException

class PayerController {

    PayerService payerService

    def create() {
        return params
    }

    def edit() {
        try {
            Long id = params.long("id")
            Long customerId = params.long("customerId")
            Payer payer = payerService.returnPayerIfExistsWithCustomerId(id, customerId)
            
            return [id: id, customerId: customerId, payer: payer]
        } catch (ValidationException validationException) {
            flash.message = validationException.errors.allErrors.first().defaultMessage
        } catch (Exception exception) {
            flash.message = "Ocorreu um erro ao consular pagador, contate o desenvolvimento"
            log.info("PayerController.edit >> Erro ao consultar pagador com os parâmetros: [${params}]")
        }
        return [:]
    }

    def save() {
        try {
            payerService.saveOrUpdate(params)
            flash.message = "Pagador criado com sucesso"
        } catch (ValidationException validationException) {
            flash.message = validationException.errors.allErrors.first().defaultMessage
        } catch (Exception exception) {
            flash.message = "Ocorreu um erro ao criar pagador, contate o desenvolvimento"
            log.info("PayerController.save >> Erro ao salvar pagador com os parâmetros: [${params}]")
        } finally {
            redirect([action: "create", params: params])
        }
    }

    def update() {
        try {
            payerService.saveOrUpdate(params)
            flash.message = "Pagador alterado com sucesso"
        } catch (ValidationException validationException) {
            flash.message = validationException.errors.allErrors.first().defaultMessage
        } catch (Exception exception) {
            flash.message = "Ocorreu um erro ao alterar pagador, contate o desenvolvimento"
            log.info("PayerController.update >> Erro ao editar pagador com os parâmetros: [${params}]")
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