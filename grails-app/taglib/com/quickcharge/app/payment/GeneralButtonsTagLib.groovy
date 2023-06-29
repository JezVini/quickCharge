package com.quickcharge.app.payment

class GeneralButtonsTagLib {
    static namespace = "generalButtons"

    def paymentReceipt = { attrs ->
        Map buttonAttributes = createPaymentReceiptButton(attrs.payment as Payment)

        out << g.render(template: "/shared/templates/payment/paymentAtlasButton", model: [buttonAttributes: buttonAttributes])
    }

    def paymentSave = { attrs ->
        Map buttonAttributes = createPaymentSaveButton(attrs.payment as Payment)

        out << g.render(template: "/shared/templates/payment/paymentAtlasButton", model: [buttonAttributes: buttonAttributes])
    }

    private Map createPaymentReceiptButton(Payment payment) {
        Map paymentReceiptButtonAttributes = [:]

        paymentReceiptButtonAttributes.putAll([
            description: "Comprovante de pagamento",
            icon       : "file-dolar"
        ])

        if (payment.status.canUpdate()) {
            paymentReceiptButtonAttributes.putAll([
                tooltip : "Pagamento ainda não recebido!",
                disabled: true
            ])
            
            return paymentReceiptButtonAttributes
        }
        
        paymentReceiptButtonAttributes.putAll([
            tooltip: "Clique para visualizar!",
            href   : createLink(controller: 'paymentReceipt', action: 'show', params: [paymentReceiptUniqueId: payment.getPaymentReceiptUniqueId()]),
            'is-external-link': true
        ])

        return paymentReceiptButtonAttributes
    }

    private Map createPaymentSaveButton(Payment payment) {
        Map paymentSaveButtonAttributes = [:]

        paymentSaveButtonAttributes.putAll([
            description: "Salvar Cobrança",
            icon       : "check"
        ])

        if (payment.status.canUpdate()) {
            paymentSaveButtonAttributes.putAll([
                tooltip: "Clique para salvar!",
                submit : true
            ])
            
            return paymentSaveButtonAttributes
        }
        
        paymentSaveButtonAttributes.putAll([
            tooltip : "Não é possível editar!",
            disabled: true
        ])
        
        return paymentSaveButtonAttributes
    }
}
