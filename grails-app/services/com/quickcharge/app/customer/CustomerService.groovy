package com.quickcharge.app.customer

import grails.gorm.transactions.Transactional
import grails.validation.ValidationException
import utils.CpfCnpjUtils
import utils.address.State
import utils.Utils
import utils.baseperson.PersonType

import java.util.regex.Pattern

@Transactional
class CustomerService {

    def userService
    def springSecurityService
    
    public Customer save(Map parameterMap) {
        Customer validatedCustomer = validateSave(parameterMap)

        if (validatedCustomer.hasErrors()) {
            throw new ValidationException("Erro ao salvar conta", validatedCustomer.errors)
        }

        Map sanitizedParameterMap = sanitizeParameterMap(parameterMap)
        Customer customer = new Customer()
        
        customer.email = sanitizedParameterMap.email
        setCustomerProperties(customer, sanitizedParameterMap)
        
        customer.save(failOnError: true)
        userService.save(customer, parameterMap.email, parameterMap.password)
        
        return customer
    }

    public Customer update(Map parameterMap) {
        Customer validatedCustomer = validateSave(parameterMap)

        if (validatedCustomer.hasErrors()) {
            throw new ValidationException("Erro ao salvar conta", validatedCustomer.errors)
        }

        Map sanitizedParameterMap = sanitizeParameterMap(parameterMap)
        Customer customer = springSecurityService.getCurrentUser().customer

        setCustomerProperties(customer, sanitizedParameterMap)

        return customer.save(failOnError: true)
    }

    private void setCustomerProperties(Customer customer, Map parameterMap) {
        customer.name = parameterMap.name
        customer.cpfCnpj = parameterMap.cpfCnpj
        customer.phone = parameterMap.phone
        customer.state = parameterMap.state
        customer.city = parameterMap.city
        customer.district = parameterMap.district
        customer.addressNumber = parameterMap.addressNumber
        customer.postalCode = parameterMap.postalCode
        customer.address = parameterMap.address
        customer.addressComplement = parameterMap.addressComplement
        customer.personType = CpfCnpjUtils.isCpf(parameterMap.cpfCnpj) ? PersonType.NATURAL : PersonType.LEGAL
    }
    
    private Customer validatePatternMatching(Map parameterMap) {
        final String DEFAULT_FIELD_INVALID_PATTERN = "default.field.invalid.pattern"

        Customer validatedCustomer = new Customer()

        if (!CpfCnpjUtils.isCpfCnpjPatternMatch(parameterMap.cpfCnpj as String)) {
            validatedCustomer.errors.rejectValue("cpfCnpj", "", DEFAULT_FIELD_INVALID_PATTERN)
        }

        if (!Utils.isPhonePatternMatch(parameterMap.phone as String)) {
            validatedCustomer.errors.rejectValue("phone", "", DEFAULT_FIELD_INVALID_PATTERN)
        }

        if (!Utils.isPostalCodePatternMatch(parameterMap.postalCode as String)) {
            validatedCustomer.errors.rejectValue("postalCode", "", DEFAULT_FIELD_INVALID_PATTERN)
        }

        if (!Utils.isStatePatternMatch(parameterMap.state as String)) {
            validatedCustomer.errors.rejectValue("state", "invalid")
        }

        return validatedCustomer
    }
  
    private Customer validateInvalidSpecials(Map parameterMap) {
        final Pattern INVALID_CHARACTERS_PATTERN = ~/(.*)\p{Punct}+(.*)/
        final String DEFAULT_FIELD_INVALID_SPECIAL_CHARACTERS = "default.field.invalid.special.characters"

        Customer validatedCustomer = new Customer()

        List<String> shouldNotHaveSpecialsFieldList = [
            "name",
            "state",
            "city",
            "district",
            "address",
            "addressNumber",
            "addressComplement"
        ]

        for (String field : shouldNotHaveSpecialsFieldList) {
            if (!(parameterMap[field] as String).matches(INVALID_CHARACTERS_PATTERN)) continue
            validatedCustomer.errors.rejectValue(field, "", DEFAULT_FIELD_INVALID_SPECIAL_CHARACTERS)
        }

        return validatedCustomer
    }

    private Customer validateSave(Map parameterMap) {

        Customer patterMatchingCustomer = validatePatternMatching(parameterMap)
        if (patterMatchingCustomer.hasErrors()) return patterMatchingCustomer

        Customer invalidSpecialsCustomer = validateInvalidSpecials(parameterMap)
        if (invalidSpecialsCustomer.hasErrors()) return invalidSpecialsCustomer

        Customer validatedCustomer = new Customer()

        if (!CpfCnpjUtils.validate(parameterMap.cpfCnpj as String)) {
            validatedCustomer.errors.reject("", null, "CPF ou CNPJ inválido")
        }
        
        if (!State.validate(parameterMap.state)) {
            validatedCustomer.errors.rejectValue("state", "invalid")
        }

        return validatedCustomer
    }

    private Map sanitizeParameterMap(Map parameterMap) {
        List<String> mustRemoveNonNumericsParameterList = ["cpfCnpj", "phone", "postalCode"]

        Map sanitizedParameterMap = [:]
        for (def parameter : parameterMap) {
            if (!(parameter.value instanceof String)) {
                sanitizedParameterMap[parameter.key] = parameter.value
                continue
            }

            if (mustRemoveNonNumericsParameterList.contains(parameter.key)) {
                sanitizedParameterMap[parameter.key] = Utils.removeNonNumeric(parameter.value as String)
                continue
            }

            sanitizedParameterMap[parameter.key] = (parameter.value as String).trim()
        }

        return sanitizedParameterMap
    }
}