package com.quickcharge.app.customer

class CustomerController {

    def customerService

    def create() {
        return [:]
    }

    def save() {
        Customer customer = customerService.save(params)
        if (customer.hasErrors()) {
            redirect([
                action: "create"
                ])
            return
        }

        redirect(action: "create")
    }
}
