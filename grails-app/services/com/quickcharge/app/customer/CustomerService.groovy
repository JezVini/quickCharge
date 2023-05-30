package com.quickcharge.app.customer

import grails.gorm.transactions.Transactional
import grails.validation.ValidationException
import org.apache.commons.validator.routines.EmailValidator
import utils.CpfCnpjUtils
import java.util.regex.Pattern

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

    private Customer validateEmptyField(Map params) {
        Customer validatedCustomer = new Customer()

        Map shouldNotBeEmptyFieldMap = [
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

        for (def field : shouldNotBeEmptyFieldMap) {
            if (params[field.key]) continue
            validatedCustomer.errors.reject("", null, "O campo ${field.value} é obrigatório")
        }

        return validatedCustomer
    }

    private Customer validatePatternMatching(Map params) {
        Customer validatedCustomer = new Customer()

        Pattern CPF_PATTERN = ~/\d{3}\.\d{3}\.\d{3}-\d{2}/
        Pattern CNPJ_PATTERN = ~/\d{2}\.\d{3}\.\d{3}\/\d{4}-{2}/
        if (!(params.cpfCnpj as String).matches(CPF_PATTERN) && !(params.cpfCnpj as String).matches(CNPJ_PATTERN)) {
            validatedCustomer.errors.reject("", null, "Padrão de CPF ou CNPJ inválido")
        }

        Pattern PHONE_PATTERN = ~/\(\d{2}\) 9?\d{4}-?\d{4}/
        if (!(params.phone as String).matches(PHONE_PATTERN)) {
            validatedCustomer.errors.reject("", null, "Padrão de telefone inválido!")
        }

        Pattern POSTAL_CODE_PATTERN = ~/\d{5}-\d{3}/
        if (!(params.postalCode as String).matches(POSTAL_CODE_PATTERN)) {
            validatedCustomer.errors.reject("", null, "Padrão de CEP inválido")
        }

        Pattern STATE_PATTERN = ~/[A-Z]{2}/
        if (!(params.state as String).matches(STATE_PATTERN)) {
            validatedCustomer.errors.reject("", null, "Estado deve ser uma sigla")
        }

        return validatedCustomer
    }

    private Customer validateInvalidSpecials(Map params) {
        Customer validatedCustomer = new Customer()

        Pattern INVALID_CHARACTERS_PATTERN = ~/(.*)\p{Punct}+(.*)/

        Map shouldNotHaveSpecialsFieldMap = [
            name: "nome",
            state: "estado",
            city: "cidade",
            district: "bairro",
            address: "rua",
            addressNumber: "número"
        ]

        for (def field : shouldNotHaveSpecialsFieldMap) {
            if (!(params[field.key] as String).matches(INVALID_CHARACTERS_PATTERN)) continue
            validatedCustomer.errors.reject("", null, "O campo ${field.value} não aceita carecteres especiais")
        }

        return validatedCustomer
    }

    private Customer validateSave(Map params) {
        Customer emptyFieldCustomer = validateEmptyField(params)
        if (emptyFieldCustomer.hasErrors()) return emptyFieldCustomer

        Customer patterMatchingCustomer = validatePatternMatching(params)
        if (patterMatchingCustomer.hasErrors()) return patterMatchingCustomer

        Customer invalidSpecialsCustomer = validateInvalidSpecials(params)
        if (invalidSpecialsCustomer.hasErrors()) return invalidSpecialsCustomer

        Customer validatedCustomer = new Customer()

        if (!CpfCnpjUtils.validate(params.cpfCnpj as String)) {
            validatedCustomer.errors.reject("", null, "CPF ou CNPJ inválido")
        }

        if (!(new EmailValidator(false).isValid(params.email as String))) {
            validatedCustomer.errors.reject("", null, "Email inválido")
        }

        return validatedCustomer
    }
}