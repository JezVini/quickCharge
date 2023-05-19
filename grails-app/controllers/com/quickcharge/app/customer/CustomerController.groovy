package com.quickcharge.app.customer

class CustomerController {

    def customerService

    def create() {
        return [
            errorsList: params.errorsList
        ]
    }

    def save() {
        //trycatch
        Customer customer = customerService.save(params)
        if (customer.hasErrors()) {
            flash.message = "Dados inválidos, por favor corrija."

            List<String> errorsList = customer.errors.allErrors.defaultMessage

            redirect([
                action: "create",
                params: errorsList
                ])
            return
        }
        flash.message = "Registro criado com sucesso!"

        redirect(action: "create")
    }
}
