package com.quickcharge.app.email

import grails.gorm.transactions.Transactional
import utils.email.EmailStatus

@Transactional
class EmailRequestService {

    def mailService
    
    public void addEmail(Map email) {
        EmailRequest emailRequest = new EmailRequest()
        
        emailRequest.emailTo = email.to
        emailRequest.subject = email.subject
        emailRequest.text = email.text
        
        emailRequest.save(failOnErro: true)
    }
    
    public void sendPendingEmails() {
        List<EmailRequest> emailRequestList = EmailRequest.query([status: EmailStatus.PENDING]).list()
        
        for (EmailRequest emailRequest : emailRequestList) {
             
            mailService.sendMail {
                to emailRequest.emailTo
                subject emailRequest.subject
                text emailRequest.text
            }
            
            emailRequest.status = EmailStatus.SENT
            emailRequest.save(failOnError: true)
        }
    }
}
