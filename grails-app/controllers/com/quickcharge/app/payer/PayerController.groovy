package com.quickcharge.app.payer

import com.quickcharge.app.customer.Customer
import grails.validation.ValidationException

class PayerController {

    PayerService payerService

    def create() {
        return params
    }
    
    def edit() {
        Long id = params.long("id")
        Long customerId = params.long("customerId")
        
        try {
            if (!Customer.query([id: customerId]).get()) {
                flash.message = "Cliente inexistente"
                return [id: id, customerId: customerId]
            }

            Payer payer = Payer.query([id: id, customerId: customerId]).get()
            if (!payer) {
                flash.message = "Não foi possível buscar os dados do pagador"
                return [id: id, customerId: customerId]
            }   

            return [id: id, customerId: customerId, payer: payer]
        } catch (Exception exception) {
            flash.message = "Ocorreu um erro ao buscar dados do pagador, contate o desenvolvimento"
            log.info("PayerController.edit >> Erro ao consultar pagador com os parâmetros: [${params}] [Mensagem de erro]: ${exception.message}")
        }
        
        return [id: id, customerId: id]
    }
    
    def index () {
        try {
            Long customerId = params.long("customerId")
            
            if (!Customer.query([id: customerId]).get()) {
                flash.message = "Cliente inexistente"
                return [invalidCustomer: true]
            }
            
            return [payers: Payer.query(customerId: customerId).list()]
        } catch (Exception exception) {
            flash.message = "Ocorreu um erro ao buscar pagadores, contate o desenvolvimento"
            log.info("PayerController.index >> Erro ao consultar pagadores com parâmetros: [${params}] [Mensagem de erro]: ${exception.message}")
            return [:]
        }
    }
    
    def save() {
        try {
            payerService.saveOrUpdate(params)
            flash.message = "Pagador criado com sucesso"
        } catch (ValidationException validationException) {
            flash.message = validationException.errors.allErrors.first().defaultMessage
        } catch (Exception exception) {
            flash.message = "Ocorreu um erro ao criar pagador, contate o desenvolvimento"
            log.info("PayerController.save >> Erro ao salvar pagador com os parâmetros: [${params}] [Mensagem de erro]: ${exception.message}")
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