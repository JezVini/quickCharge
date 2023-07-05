package utils.controller

import com.quickcharge.app.customer.Customer
import grails.plugin.springsecurity.SpringSecurityService
import grails.validation.ValidationException
import org.springframework.validation.ObjectError
import utils.message.MessageType

class BaseController {

    SpringSecurityService springSecurityService

    protected Customer getCurrentCustomer() {
        return springSecurityService.getCurrentUser().customer
    }

    protected void validateExceptionHandler(ValidationException validationException) {
        ObjectError error = validationException.errors.allErrors.first()
        String errorMessage

        if (error.defaultMessage) {
            String argument = error.field ? message(code: "field.${error.field}") : ""
            errorMessage = message(code: error.defaultMessage ?: error.codes[0], args: [argument])
        } else {
            errorMessage = error.codes.first()
        }

        warning(errorMessage)
    }

    public void success(String message) {
        flash.message = message
        flash.type = MessageType.SUCCESS
    }

    public void warning(String message) {
        flash.message = message
        flash.type = MessageType.WARNING
    }

    public void error(String message) {
        flash.message = message
        flash.type = MessageType.ERROR
    }

    protected Integer getOffSet() {
        if (!params.offset) return 0 
        return Integer.parseInt(params.offset as String)
    }

    protected Integer getMax() {
        if (!params.max) return 10 
        return Integer.parseInt(params.max as String)
    }
}
