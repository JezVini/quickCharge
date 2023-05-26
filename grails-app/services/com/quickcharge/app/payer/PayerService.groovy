package com.quickcharge.app.payer

import grails.gorm.transactions.Transactional
import grails.validation.ValidationException
import com.quickcharge.app.customer.Customer

@Transactional
class PayerService {

    public Payer get(Long id, Long customerId) {
        return Payer.findWhere(
            id: id,
            customer: Customer.get(customerId),
            deleted: false
        )
    }

    public List<Payer> getAllByCustomerId(Map params) {
        Long customerId = params.long("customerId")
        Customer customer = Customer.get(customerId)
        Payer validatePayer = new Payer()
    
        if (!customer) {
            validatePayer.errors.reject("", null, "Cliente inexistente")
            throw new ValidationException("Cliente inexistente", validatePayer.errors)
        }
        
        return Payer.findAllWhere(
            customer: customer,
            deleted: false
        )
    }
    
    
    
    public Payer saveOrUpdate(Map params) {
        Payer validatedPayer = validateSave(params)

        if (validatedPayer.hasErrors()) {
            throw new ValidationException("Erro ao salvar pagador", validatedPayer.errors)
        }

        Customer customer = Customer.get(params.customerId)
        Payer payer = params.id
            ? get(params.long("id"), params.long("customerId"))
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
            "number",
            "postalCode"
        ] = params

        return payer.save(failOnError: true)
    }

    public Payer validateSave(Map params) {
        Payer validatedPayer = new Payer()

        if (!params.customerId || !(Customer.get(params.customerId))) {
            validatedPayer.errors.reject("", null, "Cliente inexistente")
        }

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

        if (!params.state) {
            validatedPayer.errors.reject("", null, "O campo estado é obrigatório")
        }

        if (!params.city) {
            validatedPayer.errors.reject("", null, "O campo cidade é obrigatório")
        }

        if (!params.district) {
            validatedPayer.errors.reject("", null, "O campo bairro é obrigatório")
        }

        if (!params.number) {
            validatedPayer.errors.reject("", null, "O campo número é obrigatório")
        }

        if (!params.postalCode) {
            validatedPayer.errors.reject("", null, "O campo CEP é obrigatório")
        }

        return validatedPayer
    }
}