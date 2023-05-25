package com.quickcharge.app.payer

import com.quickcharge.app.customer.Customer
import utils.baseperson.BasePerson

class Payer extends BasePerson {
    Customer customer

    static namedQueries = {
        query { Map search ->
            if (!search.containsKey("customer")) {
                throw new RuntimeException("Payer.query(): o atributo [customer] é obrigatório para executar a consulta.")
            }

            if (!search.containsKey("id")) {
                throw new RuntimeException("Payer.query(): o atributo [id] é obrigatório para executar a consulta.")
            }

            if (!search.containsKey("deletedOnly") && !search.containsKey("includeDeleted")) {
                throw new RuntimeException("Payer.query(): os atributos [deletedOnly] ou [includeDeleted] são obrigatórios para executar a consulta.")
            }

            if (Boolean.valueOf(search.deletedOnly)) {
                eq("deleted", true)
            } else if (!Boolean.valueOf(search.includeDeleted)) {
                eq("deleted", false)
            }

            eq("customer", search.customer)
            eq("id", search.id)
        }
    }
}