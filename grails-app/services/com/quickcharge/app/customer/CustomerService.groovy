package com.quickcharge.app.customer


import grails.gorm.transactions.Transactional
import grails.validation.ValidationException
import utils.CpfCnpjUtils

@Transactional
class CustomerService {
    
    def userService

    public Customer save(Map params) {
        Customer validatedCustomer = validateSaveOrUpdate(params, true)

        if (validatedCustomer.hasErrors()) {
            throw new ValidationException("Erro ao salvar conta", validatedCustomer.errors)
        }

        Customer customer = new Customer()

        customer.properties[
            "name",
            "email",
            "cpfCnpj",
            "phone",
            "state",
            "city",
            "district",
            "addressNumber",
            "postalCode",
            "address",
            "addressComplement"
        ] = params
        
        customer.save(failOnError: true)
        userService.save(customer, params.email, params.password)
        
        return customer
    }
    
    public Customer update(Map params) {
        Customer validatedCustomer = validateSaveOrUpdate(params)

        if (validatedCustomer.hasErrors()) {
            throw new ValidationException("Erro ao salvar conta", validatedCustomer.errors)
        }

        Customer customer = Customer.get(params.long("id"))

        customer.properties[
            "name",
            "cpfCnpj",
            "phone",
            "state",
            "city",
            "district",
            "addressNumber",
            "postalCode",
            "address",
            "addressComplement"
        ] = params

        customer.save(failOnError: true)

        return customer
    }
    
    private Customer validateSaveOrUpdate(Map params, Boolean isSave = false) {
        Customer validatedCustomer = new Customer()

        if (!params.name) {
            validatedCustomer.errors.reject("", null, "Nome não preenchido")
        }
        
        if (!params.email && isSave) {
            validatedCustomer.errors.reject("", null, "E-mail não preenchido")
        }

        if (!params.cpfCnpj) {
            validatedCustomer.errors.reject("", null, "CPF ou CNPJ não preenchido")
        }

        if (!params.phone) {
            validatedCustomer.errors.reject("", null, "Telefone não preenchido")
        }

        if (!params.postalCode) {
            validatedCustomer.errors.reject("", null, "CEP não preenchido")
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

        if (!params.address) {
            validatedCustomer.errors.reject("", null, "Rua não preenchida")
        }

        if (!params.addressNumber) {
            validatedCustomer.errors.reject("", null, "Número não preenchido")
        }

        if (!CpfCnpjUtils.validate(params.cpfCnpj)) {
            validatedCustomer.errors.reject("", null, "CPF ou CNPJ informado é inválido")
        }
        return validatedCustomer
    }
}