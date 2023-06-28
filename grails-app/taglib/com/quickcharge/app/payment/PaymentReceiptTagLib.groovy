package com.quickcharge.app.payment

class PaymentReceiptTagLib {
    static namespace = "generalButtons"

    def paymentReceipt = { attrs ->
        Map buttonAttributes = createPaymentReceiptButton(attrs.payment as Payment)

        out << g.render(template: "/shared/templates/payment/paymentReceiptButton", model: [buttonAttributes: buttonAttributes])
    }

    def paymentSave = { attrs ->
        Map buttonAttributes = createPaymentSaveButton(attrs.payment as Payment)

        out << g.render(template: "/shared/templates/payment/paymentSaveButton", model: [buttonAttributes: buttonAttributes])
    }

    private Map createPaymentReceiptButton(Payment payment) {
        Map paymentReceiptButtonAttributes = [:]

        paymentReceiptButtonAttributes.putAll([
            description: "Comprovante de pagamento",
            icon       : "file-dolar"
        ])

        if (payment.status.isReceived()) {
            paymentReceiptButtonAttributes.putAll([
                tooltip: "Clique para visualizar!",
                href   : createLink(controller: 'paymentReceipt', action: 'show', params: [paymentReceiptUniqueId: payment.getPaymentReceiptUniqueId()])
            ])
        } else {
            paymentReceiptButtonAttributes.putAll([
                tooltip : "Pagamento ainda não recebido!",
                disabled: true
            ])
        }

        return paymentReceiptButtonAttributes
    }

    private Map createPaymentSaveButton(Payment payment) {
        Map paymentSaveButtonAttributes = [:]

        paymentSaveButtonAttributes.putAll([
            description: "Salvar Cobrança",
            icon       : "check"
        ])

        if (payment.status.isReceived()) {
            paymentSaveButtonAttributes.putAll([
                tooltip : "Cobrança já recebida!",
                disabled: true
            ])
        } else {
            paymentSaveButtonAttributes.putAll([
                tooltip: "Clique para salvar!",
                submit : true
            ])
        }

        return paymentSaveButtonAttributes
    }
}
