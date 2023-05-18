package com.quickcharge.app.payer

class PayerController {

    def payerService

    def create() {
        return [:]
    }

    def save() {
        Payer payer = payerService.save(params)

        if (payer.hasErrors()){
            redirect([action: "create"])
            return
        }
    }
}
