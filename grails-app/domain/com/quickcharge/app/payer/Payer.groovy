package com.quickcharge.app.payer

import com.quickcharge.app.customer.Customer
import grails.validation.ValidationException
import utils.baseperson.BasePerson

class Payer extends BasePerson {
    Customer customer

    static namedQueries = {
        query { Map search ->
            if (!search.containsKey("customerId")) {
                throw new RuntimeException("Payer.query(): o atributo [customerId] é obrigatório para executar a consulta.")
            }
            
            if (Boolean.valueOf(search.deletedOnly)) {
                eq("deleted", true)
            } else if (!Boolean.valueOf(search.includeDeleted)) {
                eq("deleted", false)
            }
            
            eq("customer.id", Long.valueOf(search.customerId))
            
            if (search.containsKey("id")) {
                eq("id", Long.valueOf(search.id))
            }
        }
    }

    static Payer getPayer(Map parameterQuery) {
        Payer validatedPayer = new Payer()

        Payer payment = Payer.query(parameterQuery).get()
        if (payment) return payment

        validatedPayer.errors.rejectValue("id", "not.found")
        throw new ValidationException("Erro ao buscar pagador", validatedPayer.errors)
    }
}