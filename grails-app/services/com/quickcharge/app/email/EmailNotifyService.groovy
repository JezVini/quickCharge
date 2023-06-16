package com.quickcharge.app.email

import com.quickcharge.app.customer.Customer
import grails.gorm.transactions.Transactional
import grails.plugin.springsecurity.SpringSecurityService
import utils.message.MessagePattern

@Transactional
class EmailNotifyService {

    static def mailService
    SpringSecurityService springSecurityService
    
    private Customer getCurrentCustomer() {
        return springSecurityService.currentUser().customer
    }
    
    void notifyPayerCreated() {
        Map email = [
            to     : getCurrentCustomer().email,
            subject: MessagePattern.createPayer(),
            text   : MessagePattern.createPayer()
        ]

        addEmail(email)
    }

    private void addEmail(Map email) {
        println(" << Email adicionado")
        EmailNotify emailNotify = new EmailNotify()
        emailNotify.setProperty(email)
        emailNotify.save(failOnError: true)
    }

    static void sendEmails() {
        List<EmailNotify> emailList = EmailNotify.query([wasSend: false]).list()
        if (!emailList) return

        while (emailList) {
            println(" >> Enviando email")
            EmailNotify email = emailList.pop()
            EmailNotifyService.mailService.sendMail {
                to = email.to
                subject = email.subject
                text = email.text
            }
        }
    }
}
