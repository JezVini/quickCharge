package com.quickcharge.app.payer

class PayerController {

    PayerService payerService

    def create() {
        return params
    }

    def save() {
        try {
            payerService.save(params)
            flash.message = 'Registro criado com sucesso'
            params = [:]
        } catch (ValidationException error) {
            flash.message = 'Falha no registro'
        } catch (Exception error) {
            flash.message = 'Ocorreu um erro, contate o desenvolvimento'
        } finally {
            redirect([
                action: 'create',
                params: params
            ])
        }
    }

}
