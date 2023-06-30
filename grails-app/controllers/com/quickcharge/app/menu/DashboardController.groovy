package com.quickcharge.app.menu

import com.quickcharge.app.payer.PayerService
import com.quickcharge.app.payment.PaymentService
import utils.controller.BaseController

class DashboardController extends BaseController{

    PaymentService paymentService
    PayerService payerService
    
    def index() {
        return [
            customer: getCurrentCustomer(),
            paymentGeneralData: paymentService.getPaymentCounterMap(getCurrentCustomer()),
            payerGeneralData: payerService.getPayerCounterMap(getCurrentCustomer())
        ]
    }
}
