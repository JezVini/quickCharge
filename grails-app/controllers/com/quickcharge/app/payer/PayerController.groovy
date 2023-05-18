package com.quickcharge.app.payer

class PayerController {

    def payerService

    def index() {

        println(params)

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
        println("Controller show chamado")
        return [
            payer: params.payer ?: null
        ]
    }

    def create() {
        println("Controller Create chamado")
        return [:]
    }

    def save() {
        println("Controller Save chamado")
        Payer payer = payerService.save(params)

        if (payer.hasErrors()){
            println("Service erros encontrados")
            redirect([
                action: "create",
            ])
            return
        }

        println("Service cadastro sem erros")
        redirect([
            action: "show",
            params: [
                payer: payer
            ]
        ])
        return
    }
}
