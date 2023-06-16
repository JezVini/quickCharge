package com.quickcharge.app.email

import grails.gorm.transactions.Transactional
import utils.email.EmailStatus

@Transactional
class EmailRequestService {

    void addEmail(Map email) {
        EmailRequest emailRequest = new EmailRequest()
        
        emailRequest.emailTo = email.to
        emailRequest.subject = email.subject
        emailRequest.text = email.text
        
        emailRequest.save(failOnErro: true)
    }
    
    void sendPendingEmails(def mailService) {
        List<EmailRequest> emailRequestList = EmailRequest.query([status: EmailStatus.PENDING]).list()
        
        while (emailRequestList) {
            EmailRequest emailRequest = emailRequestList.pop() as EmailRequest

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
