package utils.controller

import grails.validation.ValidationException
import org.springframework.validation.ObjectError
import utils.message.MessageType 

abstract class BaseController {
    
    def validateExceptionHandler(ValidationException validationException) {
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
}
