package utils.controller

import com.quickcharge.app.customer.Customer
import com.quickcharge.app.email.BuildEmailContentService
import com.quickcharge.app.payment.Payment
import grails.plugin.springsecurity.SpringSecurityService
import grails.validation.ValidationException
import org.springframework.validation.ObjectError
import utils.email.payment.PaymentEmailAction
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
    
    void createEmail(Payment payment, PaymentEmailAction action) {
        buildEmailContentService.createEmail(payment, action)
    }
}
