package com.quickcharge.app.payer

import grails.gorm.transactions.Transactional
import grails.validation.ValidationException
import com.quickcharge.app.customer.Customer
import org.apache.commons.validator.routines.EmailValidator
import utils.CpfCnpjUtils
import java.util.regex.Pattern

@Transactional
class PayerService {

    public Payer saveOrUpdate(Map params) {
        Payer validatedPayer = validateSave(params)

        if (validatedPayer.hasErrors()) {
            throw new ValidationException("Erro ao salvar pagador", validatedPayer.errors)
        }

        Customer customer = Customer.query([id: params.customerId]).get()
        Payer payer = params.id
            ? Payer.query([id: params.id, customerId: params.customerId]).get()
            : new Payer()

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
        ] = params

        return payer.save(failOnError: true)
    }

    private Payer validateEmptyField(Map params) {
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
            addressNumber: "número"
        ]

        for (def field : shouldNotBeEmptyFieldMap) {
            if ((params[field.key] as String).trim()) continue
            validatedPayer.errors.reject("", null, "O campo ${field.value} é obrigatório")
        }
        
        return validatedPayer
    }
    
    private Payer validatePatternMatching(Map params) {
        Payer validatedPayer = new Payer()
        
        Pattern CPF_PATTERN = ~/\d{3}\.\d{3}\.\d{3}-\d{2}/
        Pattern CNPJ_PATTERN = ~/\d{2}\.\d{3}\.\d{3}\/\d{4}-{2}/
        if (!(params.cpfCnpj as String).matches(CPF_PATTERN) && !(params.cpfCnpj as String).matches(CNPJ_PATTERN)) {
            validatedPayer.errors.reject("", null, "Padrão de CPF ou CNPJ inválido")
        }

        Pattern PHONE_PATTERN = ~/\(\d{2}\) 9?\d{4}-?\d{4}/
        if (!(params.phone as String).matches(PHONE_PATTERN)) {
            validatedPayer.errors.reject("", null, "Padrão de telefone inválido!")
        }

        Pattern POSTAL_CODE_PATTERN = ~/\d{5}-\d{3}/
        if (!(params.postalCode as String).matches(POSTAL_CODE_PATTERN)) {
            validatedPayer.errors.reject("", null, "Padrão de CEP inválido")
        }

        Pattern STATE_PATTERN = ~/[A-Z]{2}/
        if (!(params.state as String).matches(STATE_PATTERN)) {
            validatedPayer.errors.reject("", null, "Estado deve ser uma sigla")
        }
        
        return validatedPayer
    }

    private Payer validateInvalidSpecials(Map params) {
        Payer validatedPayer = new Payer()

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
            validatedPayer.errors.reject("", null, "O campo ${field.value} não aceita carecteres especiais")
        }

        return validatedPayer
    }

    private Payer validateSave(Map params) {
        Payer emptyFieldPayer = validateEmptyField(params)
        if (emptyFieldPayer.hasErrors()) return emptyFieldPayer

        Payer patterMatchingPayer = validatePatternMatching(params)
        if (patterMatchingPayer.hasErrors()) return patterMatchingPayer

        Payer invalidSpecialsPayer = validateInvalidSpecials(params)
        if (invalidSpecialsPayer.hasErrors()) return invalidSpecialsPayer
        
        Payer validatedPayer = new Payer()
        
        if (!CpfCnpjUtils.validate(params.cpfCnpj as String)) {
            validatedPayer.errors.reject("", null, "CPF ou CNPJ inválido")
        }
        
        if (!(new EmailValidator(false).isValid(params.email as String))) {
            validatedPayer.errors.reject("", null, "Email inválido")
        }

        if (!params.customerId || !(Customer.get(params.customerId))) {
            validatedPayer.errors.reject("", null, "Cliente inexistente")
        }
        
        return validatedPayer
    }
}