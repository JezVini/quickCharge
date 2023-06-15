package quickcharge

import com.quickcharge.app.email.EmailNotifyService
import com.quickcharge.app.email_notify.EmailNotify

class SendMailJob {
    private static List<Map> emailQueue = [] 
    
    def mailService
    
    static triggers = {
        simple repeatInterval: 5000l
    }


    def execute() {
        EmailNotifyService.sendEmails()
    }
}
