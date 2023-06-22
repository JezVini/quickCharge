package com.quickcharge.app.email

import com.quickcharge.app.customer.Customer
import com.quickcharge.app.payment.Payment
import grails.gorm.transactions.Transactional
import grails.gsp.PageRenderer
import grails.validation.ValidationException
import utils.email.payment.PaymentEmailAction

@Transactional
class BuildEmailContentService {

    PageRenderer groovyPageRenderer
    
    private EmailRequest createEmailRequest(Payment payment, Customer customer, PaymentEmailAction action) {
        EmailRequest emailRequest = new EmailRequest()

        final String viewPath = "/email/_payment"
        
        String subject = "Cobrança para ${payment.payer.name} ${action.getDescription().toLowerCase()}"
        if (action != PaymentEmailAction.OVERDUE) {
            subject += " com sucesso!"
        }
        
        Map args = [customer: customer, payment: payment, subject: subject]

        emailRequest.emailTo = customer.email
        emailRequest.subject = subject
        emailRequest.html = groovyPageRenderer.render(view: viewPath, model: args)
        
        return emailRequest
    }

    public void createEmail(Payment payment, Customer customer, PaymentEmailAction action) {
        try {
            EmailRequest emailRequest = createEmailRequest(payment, customer, action)
            emailRequest.save(failOnError: true)
        } catch (ValidationException validationException) {
            Map args = [paymentId: payment.id, customerId: customer.id, action: action.getDescription()]
            log.error("BuildEmailContentService.createEmail >> Erro na validação dos campos do email ${args} [Mensagem de erro]: ${validationException.message}")
        } catch (Exception exception) {
            Map args = [paymentId: payment.id, customerId: customer.id, action: action.getDescription()]
            log.error("BuildEmailContentService.createEmail >> Erro ao criar email ${args} [Mensagem de erro]: ${exception.message}")
        }
    }
}