package com.quickcharge.app.email

import grails.gorm.transactions.Transactional
import utils.email.EmailStatus
import javax.xml.bind.ValidationException

@Transactional
class EmailRequestService {

    def mailService
    
    public void addEmail(Map email) {
        EmailRequest emailRequest = new EmailRequest()
        
        emailRequest.emailTo = email.to
        emailRequest.subject = email.subject
        emailRequest.html = email.html
        
        emailRequest.save(failOnErro: true)
    }
    
    public void sendPendingEmails() {
        List<EmailRequest> emailRequestList = EmailRequest.query([status: EmailStatus.PENDING]).list()
        
        for (EmailRequest emailRequest : emailRequestList) {
            try {
                mailService.sendMail {
                    to emailRequest.emailTo
                    subject emailRequest.subject
                    html emailRequest.html
                }

                emailRequest.status = EmailStatus.SENT
                emailRequest.save(failOnError: true)
            } catch (ValidationException validationException) {
                emailRequest.status = EmailStatus.ERROR
                emailRequest.errorMessage = validationException.message
                emailRequest.save()
            } catch (Exception exception) {
                log.error("EmailRequestService.sendPendingEmails >> Erro ao enviar email [EmailId: ${emailRequest.id}] [Mensagem de erro]: ${exception.message}")
            }
        }
    }
}
