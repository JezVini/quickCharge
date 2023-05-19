package com.quickcharge.app.customer

import grails.gorm.transactions.Transactional
import grails.validation.ValidationException

@Transactional
class CustomerService {

    public Customer save(Map params) {
        Customer customer = validateCustomer(params)

        if (customer.hasErrors()) {
            throw new ValidationException(null, customer.errors)
        }

        customer.properties[
            "name",
            "email",
            "cpfCnpj",
            "phone",
            "state",
            "city",
            "district",
            "number",
            "postalCode"
        ] = params

        customer.save(failOnError: true)

        if (customer.hasErrors()) {
            throw new ValidationException(null, customer.errors)
        }

        return customer
    }

    private Customer validateCustomer(Map params) {
        Customer customer = new Customer()

        if (!params.name) {
            customer.errors.reject("", null, "Nome não preenchido")
        }
        if (!params.email) {
            customer.errors.reject("", null, "E-mail não preenchido")
        }
        if (!params.cpfCnpj) {
            customer.errors.reject("", null, "CPF ou CNPJ não preenchido")
        }
        if (!params.phone) {
            customer.errors.reject("", null, "Telefone não preenchido")
        }
        if (!params.state) {
            customer.errors.reject("", null, "Estado não preenchido")
        }
        if (!params.city) {
            customer.errors.reject("", null, "Cidade não preenchida")
        }
        if (!params.district) {
            customer.errors.reject("", null, "Bairro não preenchido")
        }
        if (!params.number) {
            customer.errors.reject("", null, "Número não preenchido")
        }
        if (!params.postalCode) {
            customer.errors.reject("", null, "CEP não preenchido")
        }

        return customer
    }
}
