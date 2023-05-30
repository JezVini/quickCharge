package com.quickcharge.app.payer

import grails.gorm.transactions.Transactional
import grails.validation.ValidationException
import com.quickcharge.app.customer.Customer
import org.apache.commons.validator.routines.EmailValidator
import utils.CpfCnpjUtils

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

    private Payer validateSave(Map params) {
        Payer validatedPayer = new Payer()
        
        Map validationFields = [
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
        
        for (def field : validationFields) {
            if (params[field.key]) continue 
            validatedPayer.errors.reject("", null, "O campo ${field.value} é obrigatório")
        }
        
        if (!params.customerId || !(Customer.get(params.customerId))) {
            validatedPayer.errors.reject("", null, "Cliente inexistente")
        }

        if (!(new EmailValidator(false).isValid(params.email as String))) {
            validatedPayer.errors.reject("", null, "Email inválido")
        }
        
        if (!CpfCnpjUtils.validate(params.cpfCnpj as String)) {
            validatedPayer.errors.reject("", null, "CPF ou CNPJ informado é inválido")
        }
        
        Integer MIN_PHONE_LENGTH = 10
        Integer MAX_PHONE_LENGTH = 11
        Integer phoneLength = params.phone.length()
        if (phoneLength < MIN_PHONE_LENGTH || phoneLength > MAX_PHONE_LENGTH) {
            validatedPayer.errors.reject("", null, "Telefone inválido")
        }
        
        Integer POSTAL_CODE_LENGTH = 8
        if (params.postalCode.length() != POSTAL_CODE_LENGTH) {
            validatedPayer.errors.reject("", null, "CEP inválido")
        }
        
        Integer STATE_LENGTH = 2
        if (params.state.length() != STATE_LENGTH) {
            validatedPayer.errors.reject("", null, "Estado deve ser uma sigla")
        }
        
        return validatedPayer
    }
}