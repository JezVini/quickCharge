package com.quickcharge.app.customer

import grails.gorm.transactions.Transactional
import grails.validation.ValidationException
import org.apache.commons.validator.routines.EmailValidator
import utils.CpfCnpjUtils
import utils.Utils

import java.util.regex.Pattern

@Transactional
class CustomerService {

    public Customer saveOrUpdate(Map parameterMap) {
        Customer validatedCustomer = validateSave(parameterMap)

        if (validatedCustomer.hasErrors()) {
            throw new ValidationException("Erro ao salvar conta", validatedCustomer.errors)
        }

        Map sanitizedParameterMap = sanitizeParameterMap(parameterMap)
        
        Customer customer
        if (sanitizedParameterMap.id) {
            customer = Customer.query([id: sanitizedParameterMap.id]).get()
        } else {
            customer = new Customer()
        }

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
        ] = sanitizedParameterMap
        
        return customer.save(failOnError: true)
    }

    private Customer validateEmptyField(Map parameterMap) {
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
            if ((parameterMap[field.key] as String).trim()) continue
            validatedCustomer.errors.reject("", null, "O campo ${field.value} é obrigatório")
        }

        return validatedCustomer
    }

    private Customer validatePatternMatching(Map parameterMap) {
        Customer validatedCustomer = new Customer()

        if (!CpfCnpjUtils.isCpfCnpjPatternMatch(parameterMap.cpfCnpj as String)) {
            validatedCustomer.errors.reject("", null, "Padrão de CPF ou CNPJ inválido")
        }

        if (!Utils.isPhonePatternMatch(parameterMap.phone as String)) {
            validatedCustomer.errors.reject("", null, "Padrão de telefone inválido!")
        }

        if (!Utils.isPostalCodePatternMatch(parameterMap.postalCode as String)) {
            validatedCustomer.errors.reject("", null, "Padrão de CEP inválido")
        }

        if (!Utils.isStatePatternMatch(parameterMap.state as String)) {
            validatedCustomer.errors.reject("", null, "Estado deve ser uma sigla")
        }

        return validatedCustomer
    }

    private Customer validateInvalidSpecials(Map parameterMap) {
        Customer validatedCustomer = new Customer()

        Pattern INVALID_CHARACTERS_PATTERN = ~/(.*)\p{Punct}+(.*)/

        Map shouldNotHaveSpecialsFieldMap = [
            name: "nome",
            state: "estado",
            city: "cidade",
            district: "bairro",
            address: "rua",
            addressNumber: "número",
            addressComplement: "complemento"
        ]

        for (def field : shouldNotHaveSpecialsFieldMap) {
            if (!(parameterMap[field.key] as String).matches(INVALID_CHARACTERS_PATTERN)) continue
            validatedCustomer.errors.reject("", null, "O campo ${field.value} não aceita carecteres especiais")
        }

        return validatedCustomer
    }

    private Customer validateSave(Map parameterMap) {
        Customer emptyFieldCustomer = validateEmptyField(parameterMap)
        if (emptyFieldCustomer.hasErrors()) return emptyFieldCustomer

        Customer patterMatchingCustomer = validatePatternMatching(parameterMap)
        if (patterMatchingCustomer.hasErrors()) return patterMatchingCustomer

        Customer invalidSpecialsCustomer = validateInvalidSpecials(parameterMap)
        if (invalidSpecialsCustomer.hasErrors()) return invalidSpecialsCustomer

        Customer validatedCustomer = new Customer()

        if (!CpfCnpjUtils.validate(parameterMap.cpfCnpj as String)) {
            validatedCustomer.errors.reject("", null, "CPF ou CNPJ inválido")
        }

        if (!(new EmailValidator(false).isValid(parameterMap.email as String))) {
            validatedCustomer.errors.reject("", null, "Email inválido")
        }

        return validatedCustomer
    }

    private Map sanitizeParameterMap(Map parameterMap) {
        Map sanitizedParameterMap = [:]
        List<String> mustRemoveNonNumericsParameterList = ["cpfCnpj", "phone", "postalCode"]
        List<String> toSanitizeParsedParameterList = [
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
        ]

        for (def parameter : parameterMap) {
            if (!toSanitizeParsedParameterList.contains(parameter.key)) continue

            if (mustRemoveNonNumericsParameterList.contains(parameter.key)) {
                sanitizedParameterMap[parameter.key] = Utils.removeNonNumeric(parameter.value as String)
            } else {
                sanitizedParameterMap[parameter.key] = (parameter.value as String).trim()
            }
        }

        sanitizedParameterMap["id"] = parameterMap["id"]

        return sanitizedParameterMap
    }
}