package com.quickcharge.app.payment

import com.quickcharge.app.customer.Customer
import com.quickcharge.app.email.BuildEmailContentService
import com.quickcharge.app.payer.Payer
import grails.gorm.PagedResultList
import grails.gorm.transactions.Transactional
import grails.validation.ValidationException
import org.apache.commons.lang3.EnumUtils
import utils.Utils
import utils.email.payment.PaymentEmailAction
import utils.payment.BillingType
import utils.payment.PaymentStatus

import java.text.SimpleDateFormat

@Transactional
class PaymentService {

    PaymentReceiptService paymentReceiptService
    BuildEmailContentService buildEmailContentService

    def save(Map parameterMap, Customer customer) {
        Payment validatedPayment = validateSave(parameterMap)
        
        if (validatedPayment.hasErrors()) {
            throw new ValidationException("Erro ao salvar cobrança", validatedPayment.errors)
        }
        
        Payment payment = new Payment()
        Payer payer = Payer.query([id: parameterMap.payerId, customerId: customer.id]).get()
        BillingType billingType = BillingType[parameterMap.billingType as String] as BillingType
        Double value = Utils.toBigDecimalFormatted(parameterMap.value as String).toDouble()
        Date dueDate = new SimpleDateFormat("dd/MM/yyyy").parse(parameterMap.dueDate as String)

        payment.payer = payer
        payment.customer = customer
        payment.billingType = billingType
        payment.value = value
        payment.dueDate = dueDate

        payment.save(failOnError: true)
        buildEmailContentService.createEmail(payment, PaymentEmailAction.CREATED)
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
            return validatedPayment
        }

        SimpleDateFormat simpleDate = new SimpleDateFormat("dd/MM/yyyy")
        Date dueDate = simpleDate.parse(parameterMap.dueDate)
        if (dueDate.before(simpleDate.parse(simpleDate.format(new Date())))) {
            validatedPayment.errors.rejectValue("dueDate", "past.date")
        }

        return validatedPayment
    }

    public void delete(Map parameterMap, Customer customer) {
        Payment payment = Payment.getById(parameterMap.id, customer.id)
        if (!payment.status.canUpdate()) {
            payment.errors.rejectValue("status", "already.received")
            throw new ValidationException("Erro ao remover cobrança", payment.errors)
        }

        payment.deleted = true

        payment.save(failOnError: true)
        buildEmailContentService.createEmail(payment, PaymentEmailAction.DELETED)
    }

    public void restore(Map parameterMap, Customer customer) {
        Payment payment = Payment.query([id: parameterMap.id, customerId: customer.id, deletedOnly: true]).get()
        if (!payment) {
            payment.errors.rejectValue("status", "can.not.delete")
            throw new ValidationException("Erro ao remover cobrança", payment.errors)
        }

        payment.deleted = false

        payment.save(failOnError: true)
        buildEmailContentService.createEmail(payment, PaymentEmailAction.RESTORED)
    }

    public void receiveInCash(Map parameterMap, Customer customer) {
        Payment payment = Payment.getById(parameterMap.id, customer.id)
        if (!payment.status.canUpdate()) {
            payment.errors.rejectValue("status", "already.received")
            throw new ValidationException("Erro ao confirmar pagamento em dinheiro da cobrança", payment.errors)
        }

        payment.status = PaymentStatus.RECEIVED_IN_CASH
        payment.paymentDate = new Date()

        paymentReceiptService.createReceipt(payment)
        payment.save(failOnError: true)
        buildEmailContentService.createEmail(payment, PaymentEmailAction.RECEIVED)
    }

    public void update(Map parameterMap, Customer customer) {
        Payment validatedPayment = validateUpdate(parameterMap.dueDate as String)

        if (validatedPayment.hasErrors()) {
            throw new ValidationException("Erro ao salvar cobrança", validatedPayment.errors)
        }

        Long paymentId = parameterMap.long("id")
        Payment payment = Payment.getById(paymentId, customer.id)

        Double value = Utils.toBigDecimalFormatted(parameterMap.value as String).toDouble()
        Date dueDate = new SimpleDateFormat("dd/MM/yyyy").parse(parameterMap.dueDate as String)

        payment.value = value
        payment.dueDate = dueDate
        
        if (payment.status == PaymentStatus.OVERDUE) payment.status = PaymentStatus.PENDING

        payment.save(failOnError: true)
        buildEmailContentService.createEmail(payment, PaymentEmailAction.UPDATED)
    }

    public Payment validateUpdate(String dueDate) {
        Payment validatedPayment = new Payment()

        SimpleDateFormat simpleDate = new SimpleDateFormat("dd/MM/yyyy")
        Date dueDateValidate = simpleDate.parse(dueDate)
        if (dueDateValidate.before(simpleDate.parse(simpleDate.format(new Date())))) {
            validatedPayment.errors.rejectValue("dueDate", "past.date")
        }

        return validatedPayment
    }

    public void processPaymentOverdue() {
        List<Long> overduePendingPaymentsIdList = Payment.query(["column": "id", "dueDate[lt]": new Date(), "onlyPendingPayments": true]).list()

        if (overduePendingPaymentsIdList.isEmpty()) return

        for (Long paymentId : overduePendingPaymentsIdList) {
            Payment.withNewTransaction { status ->
                try {
                    Payment payment = Payment.get(paymentId)
                    payment.status = PaymentStatus.OVERDUE

                    payment.save(failOnError: true)
                    buildEmailContentService.createEmail(payment, PaymentEmailAction.OVERDUE)
                } catch (Exception exception) {
                    log.info("updatePendingPaymentStatus >> Erro ao atualizar status da cobrança de id: [${paymentId}] [Mensagem de erro]: ${exception.message}")
                    status.setRollbackOnly()
                }
            }
        }
    }

    public PagedResultList<Payment> paginatedPayment(Map parameterMap, Customer customer) {
        PagedResultList<Payment> pagedResultList = Payment.query([
            customerId    : customer.id,
            deletedOnly   : parameterMap.deletedOnly,
            includeDeleted: parameterMap.includeDeleted
        ]).list([
            max   : parameterMap.max,
            offset: parameterMap.offset,
            sort  : "id",
            order : "desc"
        ])

        return pagedResultList
    }
}
