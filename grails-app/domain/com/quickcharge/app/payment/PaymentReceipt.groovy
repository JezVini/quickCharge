package com.quickcharge.app.payment

import utils.entity.BaseEntity

class PaymentReceipt extends BaseEntity {

    String uniqueId = UUID.randomUUID().toString()
    Payment payment

    static constraints = {
    }
    
    static namedQueries = {
        query { Map search ->
            if (search.containsKey("paymentId")) {
                eq("payment.id", Long.valueOf(search.paymentId))
            }

            if (search.containsKey("column")) {
                projections {
                    property "${search.column}"
                }
            }

            if (search.containsKey("uniqueId")) {
                eq("uniqueId", search.uniqueId)
            }
        }
    }
}
