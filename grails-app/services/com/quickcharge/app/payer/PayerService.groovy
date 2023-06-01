package com.quickcharge.app.payer

import grails.gorm.transactions.Transactional
import grails.validation.ValidationException
import com.quickcharge.app.customer.Customer
import org.apache.commons.validator.routines.EmailValidator
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

        Map sanitizedParameterMap = sanitizeParameterMap(parameterMap)
        
        Customer customer = Customer.query([id: sanitizedParameterMap.customerId]).get()
        Payer payer
        if (sanitizedParameterMap.id) {
            payer = Payer.query([id: sanitizedParameterMap.id, customerId: sanitizedParameterMap.customerId]).get()
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
        ] = sanitizedParameterMap

        return payer.save(failOnError: true)
    }

    private Payer validateEmptyField(Map parameterMap) {
        Payer validatedPayer = new Payer()

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
            addressNumber: "número",
        ]

        for (def field : shouldNotBeEmptyFieldMap) {
            if ((parameterMap[field.key] as String).trim()) continue
            validatedPayer.errors.reject("", null, "O campo ${field.value} é obrigatório")
        }
        
        return validatedPayer
    }
    
    private Payer validatePatternMatching(Map parameterMap) {
        Payer validatedPayer = new Payer()
        
        if (!CpfCnpjUtils.isCpfCnpjPatternMatch(parameterMap.cpfCnpj as String)) {
            validatedPayer.errors.reject("", null, "Padrão de CPF ou CNPJ inválido")
        }

        if (!Utils.isPhonePatternMatch(parameterMap.phone as String)) {
            validatedPayer.errors.reject("", null, "Padrão de telefone inválido!")
        }
        
        if (!Utils.isPostalCodePatternMatch(parameterMap.postalCode as String)) {
            validatedPayer.errors.reject("", null, "Padrão de CEP inválido")
        }
        
        if (!Utils.isStatePatternMatch(parameterMap.state as String)) {
            validatedPayer.errors.reject("", null, "Estado deve ser uma sigla")
        }
        
        return validatedPayer
    }

    private Payer validateInvalidSpecials(Map parameterMap) {
        Payer validatedPayer = new Payer()

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
            validatedPayer.errors.reject("", null, "O campo ${field.value} não aceita carecteres especiais")
        }

        return validatedPayer
    }

    private Payer validateSave(Map parameterMap) {
        Payer emptyFieldPayer = validateEmptyField(parameterMap)
        if (emptyFieldPayer.hasErrors()) return emptyFieldPayer

        Payer patterMatchingPayer = validatePatternMatching(parameterMap)
        if (patterMatchingPayer.hasErrors()) return patterMatchingPayer

        Payer invalidSpecialsPayer = validateInvalidSpecials(parameterMap)
        if (invalidSpecialsPayer.hasErrors()) return invalidSpecialsPayer
        
        Payer validatedPayer = new Payer()
        
        if (!CpfCnpjUtils.validate(parameterMap.cpfCnpj as String)) {
            validatedPayer.errors.reject("", null, "CPF ou CNPJ inválido")
        }
        
        if (!(new EmailValidator(false).isValid(parameterMap.email as String))) {
            validatedPayer.errors.reject("", null, "Email inválido")
        }

        if (!parameterMap.customerId || !(Customer.get(parameterMap.customerId))) {
            validatedPayer.errors.reject("", null, "Cliente inexistente")
        }
        
        return validatedPayer
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
        
        sanitizedParameterMap["customerId"] = parameterMap["customerId"]
        sanitizedParameterMap["id"] = parameterMap["id"]
        
        return sanitizedParameterMap
    }
}