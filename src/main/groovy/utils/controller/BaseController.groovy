package utils.controller

import com.quickcharge.app.customer.Customer
import com.quickcharge.app.email.BuildEmailContentService
import com.quickcharge.app.payment.Payment
import grails.plugin.springsecurity.SpringSecurityService
import grails.validation.ValidationException
import org.springframework.validation.ObjectError
import utils.message.MessageType 

class BaseController {

    SpringSecurityService springSecurityService
    BuildEmailContentService buildEmailContentService
    
    protected Customer getCurrentCustomer() {
        return springSecurityService.getCurrentUser().customer
    }
    
    protected void validateExceptionHandler(ValidationException validationException) {
        ObjectError error = validationException.errors.allErrors.first()
        String subMessage
        
        if (error.defaultMessage) {
            String argument = error.field ? message(code: "field.${error.field}") : ""
            subMessage = message(code: error.defaultMessage ?: error.codes[0], args: [argument])
        } else {
            subMessage = error.codes.first()
        }
        
        flash.message = subMessage
        flash.type = MessageType.WARNING
    }
    
    void sendPaymentCreatedEmail(Payment payment) {
        buildEmailContentService.savePaymentCreatedEmail(payment, getCurrentCustomer())
    }
    
    void sendPaymentDeletedEmail(Payment payment) {
        buildEmailContentService.savePaymentDeletedEmail(payment, getCurrentCustomer())
    }
    
    void sendPaymentRestoredEmail(Payment payment) {
        buildEmailContentService.savePaymentRestoredEmail(payment, getCurrentCustomer())
    }
    
    void sendPaymentReceivedEmail(Payment payment) {
        buildEmailContentService.savePaymentReceivedEmail(payment, getCurrentCustomer())
    }
    
    void sendPaymentUpdatedEmail(Payment payment) {
        buildEmailContentService.savePaymentUpdatedEmail(payment, getCurrentCustomer())
    }
}
