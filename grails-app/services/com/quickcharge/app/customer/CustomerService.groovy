package com.quickcharge.app.customer

import grails.gorm.transactions.Transactional
import grails.validation.ValidationException
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

        Map parsedParameterMap = parseParameterMap(parameterMap)
        Map sanitizedParsedParameterMap = sanitizeParameterMap(parsedParameterMap)
        
        Customer customer
        if (sanitizedParsedParameterMap.id) {
            customer = Customer.query([id: sanitizedParsedParameterMap.id]).get()
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
        ] = sanitizedParsedParameterMap
        
        return customer.save(failOnError: true)
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

        return validatedCustomer
    }

    private Map parseParameterMap(Map parameterMap) {
        List<String> toParseParameterList = [
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
            "addressComplement",
            "id",
        ]

        Map parsedParameterMap = [:]
        for (String parameter : toParseParameterList) {
            parsedParameterMap[parameter] = parameterMap[parameter]
        }

        return parsedParameterMap
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