package com.quickcharge.app.email

import utils.entity.BaseEntity

class EmailNotify extends BaseEntity {

    String to
    String subject
    String text
    Boolean wasSent = false
    Boolean errorToSend = false
    
    static constraints = {
        to email: true
    }
    
    static namedQueries = {
        query { Map search ->
            if (!search.containsKey("wasSend")) {
                throw new RuntimeException("EmailNotify.query(): o atributo [wasSent] é obrigatório para executar a consulta.")
            }

            eq("wasSend", Boolean.valueOf(search.wasSent))
        }
    }
}
