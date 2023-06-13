package com.quickcharge.app.customer

import utils.baseperson.BasePerson
import utils.baseperson.PersonType

class Customer extends BasePerson {
    
    static namedQueries = {
        query { Map search ->
            if (!search.containsKey("id")) {
                throw new RuntimeException("Customer.query(): o atributo [id] é obrigatório para executar a consulta.")
            }

            if (Boolean.valueOf(search.deletedOnly)) {
                eq("deleted", true)
            } else if (!Boolean.valueOf(search.includeDeleted)) {
                eq("deleted", false)
            }

            eq("id", Long.valueOf(search.id))
        }
    }
}