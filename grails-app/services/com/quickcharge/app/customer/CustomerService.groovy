package com.quickcharge.app.customer

import grails.gorm.transactions.Transactional
import grails.validation.ValidationException

@Transactional
class CustomerService {

    public Customer saveOrUpdate(Map params) {
        Customer validatedCustomer = validateSave(params)

        if (validatedCustomer.hasErrors()) {
            throw new ValidationException("Erro ao salvar conta", validatedCustomer.errors)
        }

        Long customerId = params.long("id")
        Customer customer = customerId ? Customer.get(params.long("id")) : new Customer()

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

        return customer.save(failOnError: true)
    }
    
    private Customer validateSave(Map params) {
        Customer validatedCustomer = new Customer()

        if (!params.name) {
            validatedCustomer.errors.reject("", null, "Nome não preenchido")
        }

        if (!params.email) {
            validatedCustomer.errors.reject("", null, "E-mail não preenchido")
        }

        if (!params.cpfCnpj) {
            validatedCustomer.errors.reject("", null, "CPF ou CNPJ não preenchido")
        }

        if (!params.phone) {
            validatedCustomer.errors.reject("", null, "Telefone não preenchido")
        }

        if (!params.state) {
            validatedCustomer.errors.reject("", null, "Estado não preenchido")
        }

        if (!params.city) {
            validatedCustomer.errors.reject("", null, "Cidade não preenchida")
        }

        if (!params.district) {
            validatedCustomer.errors.reject("", null, "Bairro não preenchido")
        }

        if (!params.number) {
            validatedCustomer.errors.reject("", null, "Número não preenchido")
        }

        if (!params.postalCode) {
            validatedCustomer.errors.reject("", null, "CEP não preenchido")
        }

        return validatedCustomer
    }
}