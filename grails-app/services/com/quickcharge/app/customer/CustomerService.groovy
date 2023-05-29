package com.quickcharge.app.customer

import grails.gorm.transactions.Transactional
import grails.validation.ValidationException
import org.apache.commons.validator.routines.EmailValidator
import utils.CpfCnpjUtils

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
            "addressNumber",
            "postalCode",
            "address",
            "addressComplement"
        ] = params
        
        return customer.save(failOnError: true)
    }
    
    private Customer validateSave(Map params) {
        Customer validatedCustomer = new Customer()

        Map validationFields = [
            name: "nome",
            email: "e-mail",
            cpfCnpj: "CPF ou CNPJ",
            phone: "telefone",
            postalCode: "CEP",
            state: "estado",
            city: "cidade",
            district: "bairro",
            address: "rua",
            addressNumber: "número"
        ]

        validationFields.forEach { fieldKey, fieldName ->
            if (params[fieldKey]) return
            validatedCustomer.errors.reject("", null, "O campo ${fieldName} é obrigatório")
        }

        if (!(new EmailValidator(false).isValid(params.email as String))) {
            validatedCustomer.errors.reject("", null, "Email inválido")
        }
        
        if (!CpfCnpjUtils.validate(params.cpfCnpj as String)) {
            validatedCustomer.errors.reject("", null, "CPF ou CNPJ informado é inválido")
        }

        return validatedCustomer
    }
}