package quickcharge

class SendMailJob {
    static List<Map> emailQueue = [] 
    
    def mailService
    
    static triggers = {
      simple repeatInterval: 5000l // execute job once in 5 seconds
    }
    
    def execute() {
        if (!this.emailQueue) return
        
        for (def email : emailQueue) {
            mailService.sendMail = email;
        }
        
    }
}
