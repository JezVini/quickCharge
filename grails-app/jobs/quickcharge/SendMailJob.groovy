package quickcharge

import com.quickcharge.app.email.EmailNotify

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
