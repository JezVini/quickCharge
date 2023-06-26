package utils.controller

import com.quickcharge.app.customer.Customer
import grails.plugin.springsecurity.SpringSecurityService
import grails.validation.ValidationException
import org.springframework.validation.ObjectError
import utils.message.MessageType 

abstract class BaseController {
    
    SpringSecurityService springSecurityService
    
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

    protected Integer getOffSet() {
        if (params.offset) return Integer.parseInt(params.offset as String)
        return 0
    }

    protected Integer getMax() {
        if (params.max) return Integer.parseInt(params.max as String)
        return 10
    }
}
