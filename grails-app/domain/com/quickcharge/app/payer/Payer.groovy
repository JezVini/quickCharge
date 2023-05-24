package com.quickcharge.app.payer

import com.quickcharge.app.customer.Customer
import utils.baseperson.BasePerson

class Payer extends BasePerson {
    
    Customer customer

    static namedQueries = {
        query { Map search ->
            if (Boolean.valueOf(search.deletedOnly)) {
                eq("deleted", true)
            } else if (!Boolean.valueOf(search.includeDeleted)) {
                eq("deleted", false)
            }
        }
    }
}