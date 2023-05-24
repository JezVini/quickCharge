package com.quickcharge.app.customer

import grails.validation.ValidationException

class CustomerController {

    def customerService

    def create() {
        return params
    }

    def edit() {
        Customer customer = Customer.get(params.long("id"))
        return [
            customer: customer
        ]
    }

    def save() {
        try {
            customerService.saveOrUpdate(params)
            flash.message = "Registro criado com sucesso"
        } catch (ValidationException validationException) {
            flash.message = validationException.errors.allErrors.first().defaultMessage
        } catch (Exception exception) {
            flash.message = "Ocorreu um erro, contate o desenvolvimento"
            log.info("CustomerController.save >> Erro ao salvar id ${params}")
        } finally {
            redirect([
                action: "create",
                params: params
            ])
        }
    }

    def update() {
        try {
            customerService.saveOrUpdate(params)
            flash.message = "Cadastro editado com sucesso"
        } catch (ValidationException validationException) {
            flash.message = validationException.errors.allErrors.first().defaultMessage
        } catch (Exception exception) {
            flash.message = "Ocorreu um erro, contate o desenvolvimento"
            log.info("CustomerController.save >> Erro ao editar id ${params.id}")
        } finally {
            redirect([
                action: "edit",
                params: [id: params.id]
            ])
        }
    }
}
