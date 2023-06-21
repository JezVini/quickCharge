package com.quickcharge.app.email

import com.quickcharge.app.customer.Customer
import com.quickcharge.app.payment.Payment
import grails.gorm.transactions.Transactional
import grails.gsp.PageRenderer

@Transactional
class BuildEmailContentService {

    PageRenderer groovyPageRenderer
    
    private EmailRequest createEmailRequest(Payment payment, Customer customer, String state) {
        EmailRequest emailRequest = new EmailRequest()

        String viewPath = "/email/_payment"
        String subject = "Cobrança para ${payment.payer.name} ${state} com sucesso!"
        Map args = [customer: customer, payment: payment, subject: subject]

        emailRequest.emailTo = customer.email
        emailRequest.subject = subject
        emailRequest.html = groovyPageRenderer.render(view: viewPath, model: args)
        
        return emailRequest
    }

    public void savePaymentCreatedEmail(Payment payment, Customer customer) {
        EmailRequest emailRequest = createEmailRequest(payment, customer, "criada")
        emailRequest.save(failOnError: true)
    }
    
    public void savePaymentDeletedEmail(Payment payment, Customer customer) {
        EmailRequest emailRequest = createEmailRequest(payment, customer, "deletada")
        emailRequest.save(failOnError: true)
    }
    
    public void savePaymentRestoredEmail(Payment payment, Customer customer) {
        EmailRequest emailRequest = createEmailRequest(payment, customer, "restaurada")
        emailRequest.save(failOnError: true)
    }
    
    public void savePaymentReceivedEmail(Payment payment, Customer customer) {
        EmailRequest emailRequest = createEmailRequest(payment, customer, "recebida")
        emailRequest.save(failOnError: true)
    }
    
    public void savePaymentUpdatedEmail(Payment payment, Customer customer) {
        EmailRequest emailRequest = createEmailRequest(payment, customer, "modificada")
        emailRequest.save(failOnError: true)
    }
}