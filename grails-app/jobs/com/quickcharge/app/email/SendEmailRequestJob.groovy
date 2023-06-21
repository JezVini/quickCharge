package com.quickcharge.app.email

class SendEmailRequestJob {
    
    EmailRequestService emailRequestService
    
    static triggers = {
      simple repeatInterval: 5000l
    }

    def execute() {
        emailRequestService.sendPendingEmails()
    }
}
