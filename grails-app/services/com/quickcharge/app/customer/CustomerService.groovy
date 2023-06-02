package com.quickcharge.app.customer

import grails.gorm.transactions.Transactional
import grails.validation.ValidationException
import org.apache.commons.validator.routines.EmailValidator
import utils.CpfCnpjUtils
import utils.Utils

import java.util.regex.Pattern

@Transactional
class CustomerService {

    public Customer save(Map parameterMap) {
        Customer validatedCustomer = validateSave(parameterMap)

        if (validatedCustomer.hasErrors()) {
            throw new ValidationException("Erro ao salvar conta", validatedCustomer.errors)
        }
        
        Map sanitizedParameterMap = sanitizeParameterMap(parameterMap)

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
        ] = sanitizedParameterMap
        
        return customer.save(failOnError: true)
    }

    public Customer update(Map parameterMap) {
        Customer customer = Customer.query([id: parameterMap.id]).get()
        
        Map changedFields = checkChangedFields(customer, parameterMap)

        Customer validatedCustomer = validateSave(changedFields)

        if (validatedCustomer.hasErrors()) {
            throw new ValidationException("Erro ao salvar conta", validatedCustomer.errors)
        }
        
        Map sanitizedParameterMap = sanitizeParameterMap(changedFields)
        
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
        ] = sanitizedParameterMap

        return customer.save(failOnError: true)
    }
 
    private Map checkChangedFields(Customer customer, Map parameterMap) {
        Map changedFields = [:]
    
        for (def parameter : parameterMap) {
            if (!customer.properties.keySet().contains(parameter.key)) continue
            
            if (customer.properties[parameter.key] != parameter.value) {
                changedFields.put(parameter.key, parameter.value)
            }
        }

        return changedFields
    }
    
    private Customer validateEmptyField(Map parameterMap) {
        Customer validatedCustomer = new Customer()

        for (def field : parameterMap) {
            if ((parameterMap[field.key] as String).trim()) continue
            validatedCustomer.errors.reject("", null, "O campo ${field.value} é obrigatório")
        }

        return validatedCustomer
    }

    private Customer validatePatternMatching(Map parameterMap) {
        Customer validatedCustomer = new Customer()
        if (parameterMap.containsKey("cpfCnpj")) {
            Pattern CPF_PATTERN = ~/\d{3}\.\d{3}\.\d{3}-\d{2}/
            Pattern CNPJ_PATTERN = ~/\d{2}\.\d{3}\.\d{3}\/\d{4}-{2}/
            if (!(parameterMap.cpfCnpj as String).matches(CPF_PATTERN) && !(parameterMap.cpfCnpj as String).matches(CNPJ_PATTERN)) {
                validatedCustomer.errors.reject("", null, "Padrão de CPF ou CNPJ inválido")
            }
        }

        if (parameterMap.containsKey("phone")) {
            Pattern PHONE_PATTERN = ~/\(\d{2}\) 9?\d{4}-?\d{4}/
            if (!(parameterMap.phone as String).matches(PHONE_PATTERN)) {
                validatedCustomer.errors.reject("", null, "Padrão de telefone inválido!")
            }
        }

        if (parameterMap.containsKey("postalCode")) {
            Pattern POSTAL_CODE_PATTERN = ~/\d{5}-\d{3}/
            if (!(parameterMap.postalCode as String).matches(POSTAL_CODE_PATTERN)) {
                validatedCustomer.errors.reject("", null, "Padrão de CEP inválido")
            }
        }

        if (parameterMap.containsKey("state")) {
            Pattern STATE_PATTERN = ~/[A-Z]{2}/
            if (!(parameterMap.state as String).matches(STATE_PATTERN)) {
                validatedCustomer.errors.reject("", null, "Estado deve ser uma sigla")
            }
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

        if (parameterMap.containsKey(shouldNotHaveSpecialsFieldMap.keySet())) {
            for (def field : shouldNotHaveSpecialsFieldMap) {
                if (!(parameterMap[field.key] as String).matches(INVALID_CHARACTERS_PATTERN)) continue
                validatedCustomer.errors.reject("", null, "O campo ${field.value} não aceita carecteres especiais")
            }
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

        if (parameterMap.containsKey("cpfCnpj")) {
            if (!CpfCnpjUtils.validate(parameterMap.cpfCnpj as String)) {
                validatedCustomer.errors.reject("", null, "CPF ou CNPJ inválido")
            }
        }

        if (parameterMap.containsKey("email")) {
            if (!(new EmailValidator(false).isValid(parameterMap.email as String))) {
                validatedCustomer.errors.reject("", null, "Email inválido")
            }
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

        return sanitizedParameterMap
    }
}