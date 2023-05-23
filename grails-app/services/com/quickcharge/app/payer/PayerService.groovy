package com.quickcharge.app.payer

import grails.gorm.transactions.Transactional
import grails.validation.ValidationException
import com.quickcharge.app.customer.Customer

@Transactional
class PayerService {

    public Boolean save(Map params) {
        Payer payer = validateSave(params)

        if (payer.hasErrors()) {
            throw new ValidationException("Erro ao salvar pagador", payer.errors)
        }

        payer.customer = Customer.get(params.customerId)
        payer.properties [
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

        println(payer.properties)

        return payer.save(failOnError: true)
    }

    public Payer get(Long id, Long customerId) {
        Customer customer = Customer.get(customerId)
        return Payer.findWhere(id: id, customer: customer, deleted: false)
    }

    public Payer validateSave(Map params) {
        Payer validatedPayer = params.id ? Payer.get(params.long("id")) : new Payer()

        if (!params.name) {
            payer.errors.reject("", null, "O campo nome é obrigatório")
        }

        if (!params.email) {
            payer.errors.reject("", null, "O campo e-mail é obrigatório")
        }

        if (!params.cpfCnpj) {
            payer.errors.reject("", null, "O campo CPF ou CNPJ é obrigatório")
        }

        if (!params.phone) {
            payer.errors.reject("", null, "O campo telefone é obrigatório")
        }

        if (!params.state) {
            payer.errors.reject("", null, "O campo estado é obrigatório")
        }

        if (!params.city) {
            payer.errors.reject("", null, "O campo cidade é obrigatório")
        }

        if (!params.district) {
            payer.errors.reject("", null, "O campo bairro é obrigatório")
        }

        if (!params.number) {
            payer.errors.reject("", null, "O campo número é obrigatório")
        }

        if (!params.postalCode) {
            payer.errors.reject("", null, "O campo CEP é obrigatório")
        }

        return validatedPayer
    }

}
