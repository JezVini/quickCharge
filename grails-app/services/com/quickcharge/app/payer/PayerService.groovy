package com.quickcharge.app.payer

import com.quickcharge.app.customer.Customer
import grails.gorm.transactions.Transactional
import grails.validation.ValidationException
import utils.CpfCnpjUtils
import utils.Utils
import utils.baseperson.PersonType
import java.util.regex.Pattern
import utils.address.State

@Transactional
class PayerService {

    def springSecurityService
    
    public Payer save(Map parameterMap) {
        Payer validatedPayer = validateSave(parameterMap)

        if (validatedPayer.hasErrors()) {
            throw new ValidationException("Erro ao salvar pagador", validatedPayer.errors)
        }

        Map sanitizedParameterMap = sanitizeParameterMap(parameterMap)
        Customer customer = (springSecurityService.getCurrentUser().customer)
        
        Payer payer = new Payer()

        payer.customer = customer
        setPayerProperties(payer, sanitizedParameterMap)
        
        return payer.save(failOnError: true)
    }
    
    public Payer update(Map parameterMap) {
        Payer validatedPayer = validateSave(parameterMap)

        if (validatedPayer.hasErrors()) {
            throw new ValidationException("Erro ao salvar pagador", validatedPayer.errors)
        }

        Long customerId = Long.valueOf(springSecurityService.getCurrentUser().customer.id)
        Map sanitizedParameterMap = sanitizeParameterMap(parameterMap)
        Payer payer = Payer.query([id: sanitizedParameterMap.id, customerId: customerId]).get()

        setPayerProperties(payer, sanitizedParameterMap)

        return payer.save(failOnError: true)
    }

    private void setPayerProperties(Payer payer, Map parameterMap) {
        payer.name = parameterMap.name
        payer.email = parameterMap.email
        payer.cpfCnpj = parameterMap.cpfCnpj
        payer.phone = parameterMap.phone
        payer.state = parameterMap.state
        payer.city = parameterMap.city
        payer.district = parameterMap.district
        payer.addressNumber = parameterMap.addressNumber
        payer.postalCode = parameterMap.postalCode
        payer.address = parameterMap.address
        payer.addressComplement = parameterMap.addressComplement
        payer.personType = CpfCnpjUtils.isCpf(parameterMap.cpfCnpj) ? PersonType.NATURAL : PersonType.LEGAL
    }
    
    public Payer delete(Map parameterMap) {
        Payer validatedPayer = validateDelete(parameterMap)

        if (validatedPayer.hasErrors()) {
            throw new ValidationException("Erro ao remover pagador", validatedPayer.errors)
        }

        Long customerId = Long.valueOf(springSecurityService.getCurrentUser().customer.id)
        Payer payer = Payer.query([id: parameterMap.id, customerId: customerId]).get()
        payer.deleted = true
        
        return payer.save(failOnError: true)
    }
    
    public Payer restore(Map parameterMap) {
        Payer validatedPayer = validateRestore(parameterMap)

        if (validatedPayer.hasErrors()) {
            throw new ValidationException("Erro ao restaurar pagador", validatedPayer.errors)
        }

        Payer payer = Payer.query([id: parameterMap.id, customerId: parameterMap.customerId, deletedOnly: true]).get()
        payer.deleted = false

        return payer.save(failOnError: true)
    }

    private Payer validateRestore(Map parameterMap) {
        Payer validatedPayer = new Payer()

        Long customerId = Long.valueOf(springSecurityService.getCurrentUser().customer.id)
        if (!Payer.query([id: parameterMap.id, customerId: customerId, deletedOnly: true]).get()) {
            validatedPayer.errors.rejectValue("id", "not.found")
        }

        return validatedPayer
    }
    
    private Payer validateDelete(Map parameterMap) {
        Payer validatedPayer = new Payer()

        Long customerId = Long.valueOf(springSecurityService.getCurrentUser().customer.id)
        if (!Payer.query([id: parameterMap.id, customerId: customerId]).get()) {
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

        if (!State.validate(parameterMap.state)) {
            validatedPayer.errors.rejectValue("state", "invalid")
        }
        
        return validatedPayer
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