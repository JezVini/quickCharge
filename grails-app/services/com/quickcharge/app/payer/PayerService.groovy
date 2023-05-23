package com.quickcharge.app.payer

import grails.gorm.transactions.Transactional
import grails.validation.ValidationException
import com.quickcharge.app.customer.Customer

@Transactional
class PayerService {

    public Payer save(Map params) {
        Payer payer = validatePayer(params)

        payer.properties["name", "email", "cpfCnpj", "phone", "state", "city", "district", "number", "postalCode"] = params
        payer.customer = Customer.get(params.customer)

        if (payer.hasErrors()) {
            throw new ValidationException("Erro ao salvar pagador", payer.errors)
        }

        payer.save(failOnError: true)

        return payer
    }

    public Payer validateSave(Map params) {
        Payer validatedPayer = new Payer()

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
