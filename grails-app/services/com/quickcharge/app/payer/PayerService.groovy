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

        Map sanitizedParameterMap = sanitizeParams(parameterMap)
        
        Customer customer = Customer.query([id: sanitizedParameterMap.customerId]).get()
        if (sanitizedParameterMap.id) {
            Payer payer = Payer.query([id: sanitizedParameterMap.id, customerId: sanitizedParameterMap.customerId]).get()
        } else {
            Payer payer = new Payer()
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
        
        Pattern CPF_PATTERN = ~/\d{3}\.\d{3}\.\d{3}-\d{2}/
        Pattern CNPJ_PATTERN = ~/\d{2}\.\d{3}\.\d{3}\/\d{4}-{2}/
        if (!(parameterMap.cpfCnpj as String).matches(CPF_PATTERN) && !(parameterMap.cpfCnpj as String).matches(CNPJ_PATTERN)) {
            validatedPayer.errors.reject("", null, "Padrão de CPF ou CNPJ inválido")
        }

        Pattern PHONE_PATTERN = ~/\(\d{2}\) 9?\d{4}-?\d{4}/
        if (!(parameterMap.phone as String).matches(PHONE_PATTERN)) {
            validatedPayer.errors.reject("", null, "Padrão de telefone inválido!")
        }

        Pattern POSTAL_CODE_PATTERN = ~/\d{5}-\d{3}/
        if (!(parameterMap.postalCode as String).matches(POSTAL_CODE_PATTERN)) {
            validatedPayer.errors.reject("", null, "Padrão de CEP inválido")
        }

        Pattern STATE_PATTERN = ~/[A-Z]{2}/
        if (!(parameterMap.state as String).matches(STATE_PATTERN)) {
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
    
    private Map sanitizeParams(Map parameterMap) {
        Map sanitizedParameterMap = [:]
        List<String> mustRemoveNonNumericsFieldList = ["cpfCnpj", "phone", "postalCode"]
        List<String> requiredFieldList = [
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
            if (!requiredFieldList.contains(parameter.key)) continue
            
            if (mustRemoveNonNumericsFieldList.contains(parameter.key)) {
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