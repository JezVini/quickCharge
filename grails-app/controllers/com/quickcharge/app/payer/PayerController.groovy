package com.quickcharge.app.payer

class PayerController {

    def payerService

    def index() {

        if (params.payer) {
            redirect([
                action: "show",
                params: [
                    payer: payer
                ]
            ])
            return
        }

        redirect([action: "create"])
        return

    }

    def show() {
        return [
            payer: params.payer ?: null
        ]
    }

    def create() {
        return [:]
    }

    def save() {
        Payer payer = payerService.save(params)

        if (payer.hasErrors()){
            redirect([
                action: "create",
            ])
            return
        }

        redirect([
            action: "show",
            params: [
                payer: payer
            ]
        ])
        return
    }
}
