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

    def edit() {
        Customer customer = Customer.get(params.long("id"))
        return [
            customer: customer
        ]
    }

    def save() {
        try {
            customerService.save(params)
            flash.message = (!params.id) ? "Registro criado com sucesso" : "Cadastro editado com sucesso"
        } catch (ValidationException validationException) {
            flash.message = validationException.errors.allErrors.first().defaultMessage
        } catch (Exception exception) {
            flash.message = "Ocorreu um erro, contate o desenvolvimento"
        } finally {
            redirect([
                action: (!params.id) ? "create" : "edit",
                params: (!params.id) ? params : [id: params.id]
            ])
        }
    }
}
