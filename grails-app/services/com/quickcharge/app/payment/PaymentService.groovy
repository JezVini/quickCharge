package com.quickcharge.app.payment

import com.quickcharge.app.customer.Customer
import com.quickcharge.app.payer.Payer
import grails.gorm.transactions.Transactional
import grails.plugin.springsecurity.SpringSecurityService
import grails.validation.ValidationException
import org.apache.commons.lang3.EnumUtils
import org.apache.commons.lang3.time.DateUtils
import utils.payment.BillingType
import utils.payment.PaymentStatus

import java.text.SimpleDateFormat

@Transactional
class PaymentService {

    SpringSecurityService springSecurityService

    def save(Map parameterMap) {
        Payment validatedPayment = validateSave(parameterMap)

        if (validatedPayment.hasErrors()) {
            throw new ValidationException("Erro ao salvar cobrança", validatedPayment.errors)
        }

        Payment payment = new Payment()
        Customer customer = (springSecurityService.getCurrentUser().customer)
        Payer payer = Payer.query([id: parameterMap.payerId, customerId: customer.id]).get()
        BillingType billingType = BillingType[parameterMap.billingType as String] as BillingType
        Double value = parameterMap.double("value")

        Date selectedDay = new SimpleDateFormat("yyyy-MM-dd").parse(parameterMap.dueDate as String)
        Date dueDate = DateUtils.addDays(selectedDay, 1)

        payment.payer = payer
        payment.customer = customer
        payment.billingType = billingType
        payment.value = value
        payment.dueDate = dueDate

        payment.save(failOnError: true)
    }

    public Payment validateSave(Map parameterMap) {
        Payment validatedPayment = new Payment()

        if (!parameterMap.payerId) {
            validatedPayment.errors.rejectValue("payer", "not.selected")
        }

        if (!EnumUtils.isValidEnum(BillingType.class, parameterMap.billingType as String)) {
            validatedPayment.errors.rejectValue("billingType", "not.selected")
        }

        if (!parameterMap.dueDate) {
            validatedPayment.errors.rejectValue("dueDate", "not.selected",)
        }

        return validatedPayment
    }

    public void updatePendingPaymentStatus(PaymentStatus paymentStatus) {
        List<Long> overduePendingPaymentsIdList = Payment.query(["column": "id", "onlyOverduePendingPayments": true]).list()

        if (!overduePendingPaymentsIdList.isEmpty()) {
            for (Long paymentId : overduePendingPaymentsIdList) {
                Payment.withNewTransaction { status ->
                    Payment payment = Payment.get(paymentId)
                    try {
                        payment.status = paymentStatus
                        payment.save(failOnError: true)
                    }
                    catch (Exception exception) {
                        log.info("updatePendingPaymentStatus >> Erro ao atualizar status da cobrança: [${payment}] [Mensagem de erro]: ${exception.message}")
                        status.setRollbackOnly()
                    }
                }
            }
        }
    }
}
