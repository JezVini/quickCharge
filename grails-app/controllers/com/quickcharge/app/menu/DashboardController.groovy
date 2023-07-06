package com.quickcharge.app.menu

import com.quickcharge.app.customer.Customer
import com.quickcharge.app.payer.PayerService
import com.quickcharge.app.payment.PaymentService
import utils.controller.BaseController

class DashboardController extends BaseController{

    PaymentService paymentService
    PayerService payerService
    
    def index() {
        Customer customer = getCurrentCustomer()
        return [
            customer: customer,
            paymentGeneralData: paymentService.getPaymentCounterMap(customer),
            payerGeneralData: payerService.getPayerCounterMap(customer)
        ]
    }
}
