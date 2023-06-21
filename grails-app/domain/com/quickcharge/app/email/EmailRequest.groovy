package com.quickcharge.app.email

import utils.email.EmailStatus
import utils.entity.BaseEntity

class EmailRequest extends BaseEntity {

    String emailTo
    String subject
    String text
    EmailStatus status = EmailStatus.PENDING
    String errorMessage
    
    static constraints = {
        emailTo email: true
        errorMessage nullable: true
    }
    
    static mapping = {
        text length: 5000
    }

    static namedQueries = {
        query { Map search ->
            if (!search.containsKey("status")) {
                throw new RuntimeException("EmailRequest.query(): o atributo [status] é obrigatório para executar a consulta.")
            }

            eq("status", search.status)
        }
    }
}
