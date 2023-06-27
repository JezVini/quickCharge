package com.quickcharge.app.alert

import utils.message.MessageType

class AlertTagLib {
    static namespace = "alertTagLib"
    
    def renderAlert = {
        
        flash.type = flash.message ? flash.type ?: MessageType.WARNING : ''
        
        Map model = [
            message: message(code: flash.message),
            type: flash.type.toLowerCase()
        ]
        
        out << g.render(template: "/shared/templates/alert", model: model)
    }
}
