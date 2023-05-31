package com.quickcharge.app.payer

import com.quickcharge.app.customer.Customer
import grails.plugin.springsecurity.annotation.Secured
import grails.validation.ValidationException
import utils.message.MessageType

class PayerController {

    PayerService payerService

    @Secured(['ROLE_USER'])
    def create() {
        return params
    }

    @Secured(['ROLE_USER'])
    def edit() {
        try {
            Long id = params.long("id")
            Long customerId = params.long("customerId")
            Map parsedParams = [id: id, customerId: customerId]
            
            if (!Customer.query([id: customerId]).get()) {
                flash.message = "Cliente inexistente"
                return parsedParams
            }

            Payer payer = Payer.query([id: id, customerId: customerId]).get()
            if (!payer) {
                flash.message = "Não foi possível buscar os dados do pagador"
                return parsedParams
            }   

            return parsedParams + [payer: payer]
        } catch (Exception exception) {
            flash.message = "Ocorreu um erro ao buscar dados do pagador, contate o desenvolvimento"
            log.info("PayerController.edit >> Erro ao consultar pagador com os parâmetros: [${params}] [Mensagem de erro]: ${exception.message}")
        }
    }

    @Secured(['ROLE_USER'])
    def save() {
        try {
            payerService.saveOrUpdate(params)
            flash.message = "Pagador criado com sucesso"
            flash.type = MessageType.SUCCESS
        } catch (ValidationException validationException) {
            flash.message = validationException.errors.allErrors.first().defaultMessage
            flash.type = MessageType.WARNING
        } catch (Exception exception) {
            flash.message = "Ocorreu um erro ao criar pagador, contate o desenvolvimento"
            flash.type = MessageType.ERROR
            log.info("PayerController.save >> Erro ao salvar pagador com os parâmetros: [${params}] [Mensagem de erro]: ${exception.message}")
        } finally {
            redirect([action: "create", params: params])
        }
    }

    @Secured(['ROLE_USER'])
    def update() {
        try {
            payerService.saveOrUpdate(params)
            flash.message = "Pagador alterado com sucesso"
        } catch (ValidationException validationException) {
            flash.message = validationException.errors.allErrors.first().defaultMessage
        } catch (Exception exception) {
            flash.message = "Ocorreu um erro ao alterar pagador, contate o desenvolvimento"
            log.info("PayerController.update >> Erro ao alterar pagador com os parâmetros: [${params}] [Mensagem de erro]: ${exception.message}")
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