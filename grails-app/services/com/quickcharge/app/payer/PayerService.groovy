package com.quickcharge.app.payer

import com.quickcharge.app.customer.Customer
import grails.gorm.transactions.Transactional
import grails.validation.ValidationException
import utils.CpfCnpjUtils
import utils.Utils
import java.util.regex.Pattern

@Transactional
class PayerService {

    public Payer saveOrUpdate(Map parameterMap) {
        Payer validatedPayer = validateSave(parameterMap)
        
        if (validatedPayer.hasErrors()) {
            throw new ValidationException("Erro ao salvar pagador", validatedPayer.errors)
        }

        Map parsedParameterMap = parseParameterMap(parameterMap)
        Map sanitizedParsedParameterMap = sanitizeParameterMap(parsedParameterMap)
        
        Customer customer = Customer.query([id: sanitizedParsedParameterMap.customerId]).get()
        Payer payer
        if (sanitizedParsedParameterMap.id) {
            payer = Payer.query([id: sanitizedParsedParameterMap.id, customerId: sanitizedParsedParameterMap.customerId]).get()
        } else {
            payer = new Payer()
        }

        payer.customer = customer
        payer.properties[
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

        return payer.save(failOnError: true)
    }

    public Payer delete(Map params) {
        Payer validatedPayer = validateDelete(params)

        if (validatedPayer.hasErrors()) {
            throw new ValidationException("Erro ao remover pagador", validatedPayer.errors)
        }
        
        Payer payer = Payer.query([id: params.id, customerId: params.customerId]).get()
        payer.deleted = true
        
        return payer.save(failOnError: true)
    }
    
    private Payer validateDelete(Map params) {
        Payer validatedPayer = new Payer()

        if (!Payer.query([id: params.id, customerId: params.customerId]).get()) {
            validatedPayer.errors.rejectValue("id", "not.found")
        }
        
        return validatedPayer
    }
    
    private Payer validatePatternMatching(Map parameterMap) {
        final String DEFAULT_FIELD_INVALID_PATTERN = "default.field.invalid.pattern"

        Payer validatedPayer = new Payer()
        
        if (!CpfCnpjUtils.isCpfCnpjPatternMatch(parameterMap.cpfCnpj as String)) {
            validatedPayer.errors.rejectValue("cpfCnpj", "", DEFAULT_FIELD_INVALID_PATTERN)
        }

        if (!Utils.isPhonePatternMatch(parameterMap.phone as String)) {
            validatedPayer.errors.rejectValue("phone", "", DEFAULT_FIELD_INVALID_PATTERN)
        }

        if (!Utils.isPostalCodePatternMatch(parameterMap.postalCode as String)) {
            validatedPayer.errors.rejectValue("postalCode", "", DEFAULT_FIELD_INVALID_PATTERN)
        }

        if (!Utils.isStatePatternMatch(parameterMap.state as String)) {
            validatedPayer.errors.rejectValue("state", "invalid")
        }
        
        return validatedPayer
    }

    private Payer validateInvalidSpecials(Map parameterMap) {
        final Pattern INVALID_CHARACTERS_PATTERN = ~/(.*)\p{Punct}+(.*)/
        final String DEFAULT_FIELD_INVALID_SPECIAL_CHARACTERS = "default.field.invalid.special.characters"
        
        Payer validatedPayer = new Payer()
        
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
            validatedPayer.errors.rejectValue(field, "", DEFAULT_FIELD_INVALID_SPECIAL_CHARACTERS)
        }

        return validatedPayer
    }

    private Payer validateSave(Map parameterMap) {
        
        Payer patterMatchingPayer = validatePatternMatching(parameterMap)
        if (patterMatchingPayer.hasErrors()) return patterMatchingPayer

        Payer invalidSpecialsPayer = validateInvalidSpecials(parameterMap)
        if (invalidSpecialsPayer.hasErrors()) return invalidSpecialsPayer
        
        Payer validatedPayer = new Payer()
        
        if (!CpfCnpjUtils.validate(parameterMap.cpfCnpj as String)) {
            validatedPayer.errors.rejectValue("cpfCnpj", "invalid")
        }
        
        return validatedPayer
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
            "customerId"
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