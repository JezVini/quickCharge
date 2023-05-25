package com.quickcharge.app.customer

import utils.baseperson.BasePerson

class Customer extends BasePerson {

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