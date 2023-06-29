package com.quickcharge.app.email

class SendEmailRequestJob {
    
    EmailRequestService emailRequestService
    
    static triggers = {
      simple repeatInterval: 10000l
    }

    def execute() {
        emailRequestService.sendPendingEmails()
    }
}
