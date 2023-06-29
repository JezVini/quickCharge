package com.quickcharge.app.email

import com.quickcharge.app.payment.Payment
import grails.gorm.transactions.Transactional
import grails.gsp.PageRenderer
import grails.validation.ValidationException
import utils.Utils
import utils.email.payment.PaymentEmailAction

@Transactional
class BuildEmailContentService {

    PageRenderer groovyPageRenderer

    private List<EmailRequest> createEmailRequestList(Payment payment, PaymentEmailAction action) {
        List<EmailRequest> emailRequestList = []
        
        String subjectCustomer = "Cobrança para ${Utils.getWordInString(payment.payer.name, 0)} ${action.getDescription().toLowerCase()}"
        if (action != PaymentEmailAction.OVERDUE) subjectCustomer += " com sucesso!"

        String subjectPayer
        if (payment.status.isReceived()) {
            subjectPayer = "Uma cobrança foi paga com sucesso, ${Utils.getWordInString(payment.payer.name, 0)}!"
        } else {
            subjectPayer = "Uma cobrança foi ${action.getDescription().toLowerCase()}, ${Utils.getWordInString(payment.payer.name, 0)}!"
        }
        
        final String viewPathCustomer = "/email/_payment"
        
        final String viewPathPayer = "/email/_paymentPayer"

        EmailRequest emailRequestCustomer = createEmailRequest(payment.customer.email, subjectCustomer, viewPathCustomer, payment)
        emailRequestList.add(emailRequestCustomer)
        
        EmailRequest emailRequestPayer = createEmailRequest(payment.payer.email, subjectPayer, viewPathPayer, payment)
        emailRequestList.add(emailRequestPayer)

        return emailRequestList
    }
    
    private EmailRequest createEmailRequest(String emailTo, String subject, String viewPath, Payment payment) {
        EmailRequest emailRequestPayer = new EmailRequest()
        emailRequestPayer.emailTo = emailTo
        emailRequestPayer.subject = subject
        emailRequestPayer.html = groovyPageRenderer.render(view: viewPath, model: [payment: payment, subject: subject])
        return emailRequestPayer
    }

    public void createEmail(Payment payment, PaymentEmailAction action) {
        try {
            List<EmailRequest> emailRequestList = createEmailRequestList(payment, action)
            for (EmailRequest emailRequest : emailRequestList) {
                emailRequest.save(failOnError: true)
            }
        } catch (ValidationException validationException) {
            Map args = [paymentId: payment.id, action: action.getDescription()]
            log.error("BuildEmailContentService.createEmail >> Erro na validação dos campos do email ${args} [Mensagem de erro]: ${validationException.message}")
        } catch (Exception exception) {
            Map args = [paymentId: payment.id, action: action.getDescription()]
            log.error("BuildEmailContentService.createEmail >> Erro ao criar email ${args} [Mensagem de erro]: ${exception.message}")
        }
    }
}