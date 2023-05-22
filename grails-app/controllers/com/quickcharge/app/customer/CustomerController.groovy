package com.quickcharge.app.customer

import grails.validation.ValidationException

class CustomerController {

    def customerService

    def index() {
        List<Customer> customerList = customerService.list()
        return [
            customerList: customerList,
            customerCount: customerList.size()]
    }

    def create() {
        return params
    }

    def save() {
        try {
            //flash.type = MessageType.SUCESS
            customerService.save(params)
            flash.message = "Registro criado com sucesso"
        } catch (ValidationException validationException) {
            flash.message = validationException.errors.allErrors.first().defaultMessage
            //flash.type = MessageType.SUCESS
        } catch (Exception exception) {
            flash.message = "Ocorreu um erro, contate o desenvolvimento"
            //flash.type = MessageType.SUCESS
        } finally {
            redirect([
                action: "create",
                params: params
            ])
        }
    }
}
