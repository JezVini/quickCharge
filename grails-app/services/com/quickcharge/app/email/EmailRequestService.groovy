package com.quickcharge.app.email

import grails.gorm.transactions.Transactional
import utils.email.EmailStatus

@Transactional
class EmailRequestService {

    def mailService
    
    public void sendPendingEmails() {
        List<EmailRequest> emailRequestList = EmailRequest.query([status: EmailStatus.PENDING]).list()
        
        for (EmailRequest emailRequest : emailRequestList) {
            try {
                mailService.sendMail {
                    to emailRequest.emailTod
                    subject emailRequest.subject
                    html emailRequest.html
                }

                emailRequest.status = EmailStatus.SENT
                emailRequest.save(failOnError: true)
            } catch (Exception exception) {
                emailRequest.status = EmailStatus.ERROR
                emailRequest.errorMessage = exception.message
                emailRequest.save(flush: true)
                log.error("EmailRequestService.sendPendingEmails >> Erro ao enviar email [EmailId: ${emailRequest.id}] [Mensagem de erro]: ${exception.message}")
            }
        }
    }
}
