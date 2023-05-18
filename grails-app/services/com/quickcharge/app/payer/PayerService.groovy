package com.quickcharge.app.payer

import grails.gorm.transactions.Transactional

@Transactional
class PayerService {

    public Payer save(Map params) {
        Payer payer = validate(params)

        if (payer.hasErrors()) {
            return payer
        }

        payer.name = params.name
        payer.email = params.email
        payer.cpfCnpj = params.cpfCnpj
        payer.phone = params.phone
        payer.state = params.state
        payer.city = params.city
        payer.district = params.district
        payer.number = params.number
        payer.postalCode = params.postalCode

        payer.save()

        return payer
    }

    public Payer validate(Map params) {
        Payer payer = new Payer()

        if (!params.name){
            payer.errors.reject("", null, "Campo nome é obrigatório")
        }

        if (!params.email) {
            payer.errors.reject("", null, "Campo e-mail é obrigatório")
        }

        if (!params.cpfCnpj){
            payer.errors.reject("", null, "Campo CPF/CNPJ é obrigatório")
        }

        return payer
    }


}
