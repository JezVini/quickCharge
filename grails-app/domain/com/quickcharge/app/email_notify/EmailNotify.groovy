package com.quickcharge.app.email_notify

import utils.entity.BaseEntity

class EmailNotify extends BaseEntity{

    String to
    String subject
    String text
    Boolean wasSent = false
    Boolean errorToSend = false
    
    static constraints = {
        to email: true
        subject nullable: true
        text nullable: true
    }
}
