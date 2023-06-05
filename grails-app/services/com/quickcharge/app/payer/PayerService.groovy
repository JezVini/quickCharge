package com.quickcharge.app.payer

import grails.gorm.transactions.Transactional
import grails.validation.ValidationException
import com.quickcharge.app.customer.Customer
import utils.CpfCnpjUtils

@Transactional
class PayerService {

    def springSecurityService
    
    public Payer save(Map params) {
        Payer validatedPayer = validateSave(params)

        if (validatedPayer.hasErrors()) {
            throw new ValidationException("Erro ao salvar pagador", validatedPayer.errors)
        }

        Long customerId = Long.valueOf(springSecurityService.getCurrentUser().customer.id)
        Customer customer = Customer.query([id: customerId]).get()

        Payer payer = new Payer()

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

    public Payer update(Map params) {
        Payer validatedPayer = validateSave(params)

        if (validatedPayer.hasErrors()) {
            throw new ValidationException("Erro ao salvar pagador", validatedPayer.errors)
        }

        Long customerId = Long.valueOf(springSecurityService.getCurrentUser().customer.id)
        Customer customer = Customer.query([id: customerId]).get()
        
        Payer payer = Payer.query([id: params.id, customerId: customerId]).get()

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

    public Payer delete(Map params) {
        Payer validatedPayer = validateDelete(params)

        if (validatedPayer.hasErrors()) {
            throw new ValidationException("Erro ao remover pagador", validatedPayer.errors)
        }

        Long customerId = Long.valueOf(springSecurityService.getCurrentUser().customer.id)
        Payer payer = Payer.query([id: params.id, customerId: customerId]).get()
        payer.deleted = true
        
        return payer.save(failOnError: true)
    }
    
    private Payer validateDelete(Map params) {
        Payer validatedPayer = new Payer()

        Long customerId = Long.valueOf(springSecurityService.getCurrentUser().customer.id)
        if (!Payer.query([id: params.id, customerId: customerId]).get()) {
            validatedPayer.errors.reject("", null, "Não foi possível encontrar o pagador")
        }
        
        return validatedPayer
    }
    
    private Payer validateSave(Map params) {
        Payer validatedPayer = new Payer()

        if (!params.name) {
            validatedPayer.errors.reject("", null, "O campo nome é obrigatório")
        }

        if (!params.email) {
            validatedPayer.errors.reject("", null, "O campo e-mail é obrigatório")
        }

        if (!params.cpfCnpj) {
            validatedPayer.errors.reject("", null, "O campo CPF ou CNPJ é obrigatório")
        }

        if (!params.phone) {
            validatedPayer.errors.reject("", null, "O campo telefone é obrigatório")
        }

        if (!params.postalCode) {
            validatedPayer.errors.reject("", null, "O campo CEP é obrigatório")
        }

        if (!params.state) {
            validatedPayer.errors.reject("", null, "O campo estado é obrigatório")
        }

        if (!params.city) {
            validatedPayer.errors.reject("", null, "O campo cidade é obrigatório")
        }

        if (!params.district) {
            validatedPayer.errors.reject("", null, "O campo bairro é obrigatório")
        }

        if (!params.address) {
            validatedPayer.errors.reject("", null, "O campo rua rua é obrigatório")
        }

        if (!params.addressNumber) {
            validatedPayer.errors.reject("", null, "O campo número é obrigatório")
        }

        if (!CpfCnpjUtils.validate(params.cpfCnpj)) {
            validatedPayer.errors.reject("", null, "CPF ou CNPJ informado é inválido")
        }

        return validatedPayer
    }
}