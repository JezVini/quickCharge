package com.quickcharge.app.payer

import com.quickcharge.app.customer.Customer
import utils.baseperson.BasePerson

class Payer extends BasePerson {
    Customer customer

    static namedQueries = {
        query { Map search ->
            if (!search.containsKey("customerId")) {
                throw new RuntimeException("Payer.query(): o atributo [customerId] é obrigatório para executar a consulta.")
            }

            if (!search.containsKey("id")) {
                throw new RuntimeException("Payer.query(): o atributo [id] é obrigatório para executar a consulta.")
            }

            if (Boolean.valueOf(search.deletedOnly)) {
                eq("deleted", true)
            } else if (!Boolean.valueOf(search.includeDeleted)) {
                eq("deleted", false)
            }

            if (search.containsKey("customerId")) {
                eq("customer.id", search.customerId)
            }
            eq("id", search.id)
        }
    }
}