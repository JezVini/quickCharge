package com.quickcharge.app.payer

import com.quickcharge.app.customer.Customer
import com.quickcharge.app.payment.Payment
import grails.gorm.PagedResultList
import grails.gorm.transactions.Transactional
import grails.validation.ValidationException
import utils.CpfCnpjUtils
import utils.Utils
import utils.baseperson.PersonType
import utils.payment.PaymentStatus

import java.util.regex.Pattern
import utils.address.State

@Transactional
class PayerService {

    public Payer save(Map parameterMap, Customer customer) {
        Payer validatedPayer = validateSave(parameterMap)

        if (validatedPayer.hasErrors()) {
            throw new ValidationException("Erro ao salvar pagador", validatedPayer.errors)
        }

        Map sanitizedParameterMap = sanitizeParameterMap(parameterMap)
        Payer payer = new Payer()

        payer.customer = customer
        setPayerProperties(payer, sanitizedParameterMap)

        return payer.save(failOnError: true)
    }

    public Payer update(Map parameterMap, Customer customer) {
        Payer validatedPayer = validateSave(parameterMap)

        if (validatedPayer.hasErrors()) {
            throw new ValidationException("Erro ao salvar pagador", validatedPayer.errors)
        }

        Payer payer = Payer.getById(parameterMap.long("id"), customer.id)

        Map sanitizedParameterMap = sanitizeParameterMap(parameterMap)
        setPayerProperties(payer, sanitizedParameterMap)

        return payer.save(failOnError: true)
    }

    private void setPayerProperties(Payer payer, Map parameterMap) {
        payer.name = parameterMap.name
        payer.email = parameterMap.email
        payer.cpfCnpj = parameterMap.cpfCnpj
        payer.phone = parameterMap.phone
        payer.state = parameterMap.state
        payer.city = parameterMap.city
        payer.district = parameterMap.district
        payer.addressNumber = parameterMap.addressNumber
        payer.postalCode = parameterMap.postalCode
        payer.address = parameterMap.address
        payer.addressComplement = parameterMap.addressComplement
        payer.personType = CpfCnpjUtils.isCpf(parameterMap.cpfCnpj) ? PersonType.NATURAL : PersonType.LEGAL
    }

    public Payer delete(Map parameterMap, Customer customer) {
        Map paymentQuery = [
            payerId   : parameterMap.id,
            customerId: customer.id,
            statusList    : PaymentStatus.getUpdatableList()
        ]

        Payer payer = Payer.getById(parameterMap.long("id"), customer.id)
        if (Payment.query(paymentQuery).get()) {
            payer.errors.rejectValue("id", "has.updatable.payment")
            throw new ValidationException("Erro ao remover pagador", payer.errors)
        }

        payer.deleted = true

        return payer.save(failOnError: true)
    }

    public Payer restore(Map parameterMap, Customer customer) {
        Payer payer = Payer.query([id: parameterMap.id, customerId: customer.id, deletedOnly: true]).get()
        if (!payer) {
            payer.errors.rejectValue("id", "not.found")
            throw new ValidationException("Erro ao remover pagador", payer.errors)
        }

        payer.deleted = false

        return payer.save(failOnError: true)
    }

    private Payer validatePatternMatching(Map parameterMap) {
        final String DEFAULT_FIELD_INVALID_PATTERN = "default.field.invalid.pattern"

        Payer validatedPayer = new Payer()

        if (!CpfCnpjUtils.isCpfCnpjPatternMatch(parameterMap.cpfCnpj as String)) {
            validatedPayer.errors.rejectValue("cpfCnpj", "", DEFAULT_FIELD_INVALID_PATTERN)
        }

        if (!Utils.isPhonePatternMatch(parameterMap.phone as String)) {
            validatedPayer.errors.rejectValue("phone", "", DEFAULT_FIELD_INVALID_PATTERN)
        }

        if (!Utils.isPostalCodePatternMatch(parameterMap.postalCode as String)) {
            validatedPayer.errors.rejectValue("postalCode", "", DEFAULT_FIELD_INVALID_PATTERN)
        }

        if (!Utils.isStatePatternMatch(parameterMap.state as String)) {
            validatedPayer.errors.rejectValue("state", "invalid")
        }

        return validatedPayer
    }

    private Payer validateInvalidSpecials(Map parameterMap) {
        final Pattern INVALID_CHARACTERS_PATTERN = ~/(.*)\p{Punct}+(.*)/
        final String DEFAULT_FIELD_INVALID_SPECIAL_CHARACTERS = "default.field.invalid.special.characters"

        Payer validatedPayer = new Payer()

        List<String> shouldNotHaveSpecialsFieldList = [
            "name",
            "state",
            "city",
            "district",
            "address",
            "addressNumber",
            "addressComplement"
        ]

        for (String field : shouldNotHaveSpecialsFieldList) {
            if (!(parameterMap[field] as String).matches(INVALID_CHARACTERS_PATTERN)) continue
            validatedPayer.errors.rejectValue(field, "", DEFAULT_FIELD_INVALID_SPECIAL_CHARACTERS)
        }

        return validatedPayer
    }

    private Payer validateSave(Map parameterMap) {

        Payer patterMatchingPayer = validatePatternMatching(parameterMap)
        if (patterMatchingPayer.hasErrors()) return patterMatchingPayer

        Payer invalidSpecialsPayer = validateInvalidSpecials(parameterMap)
        if (invalidSpecialsPayer.hasErrors()) return invalidSpecialsPayer

        Payer validatedPayer = new Payer()

        if (!CpfCnpjUtils.validate(parameterMap.cpfCnpj as String)) {
            validatedPayer.errors.rejectValue("cpfCnpj", "invalid")
        }

        if (!State.validate(parameterMap.state)) {
            validatedPayer.errors.rejectValue("state", "invalid")
        }

        return validatedPayer
    }

    private Map sanitizeParameterMap(Map parameterMap) {
        List<String> mustRemoveNonNumericsParameterList = ["cpfCnpj", "phone", "postalCode"]

        Map sanitizedParameterMap = [:]
        for (def parameter : parameterMap) {
            if (!(parameter.value instanceof String)) {
                sanitizedParameterMap[parameter.key] = parameter.value
                continue
            }

            if (mustRemoveNonNumericsParameterList.contains(parameter.key)) {
                sanitizedParameterMap[parameter.key] = Utils.removeNonNumeric(parameter.value as String)
                continue
            }

            sanitizedParameterMap[parameter.key] = (parameter.value as String).trim()
        }

        return sanitizedParameterMap
    }

    public PagedResultList<Payer> paginatedPayer(Map parameterMap, Customer customer) {
        PagedResultList<Payer> pagedResultList = Payer.query([
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

    public Map getPayerCounterMap(Customer customer) {
        Integer active = Payer.query([customerId: customer.id]).list().size()
        Integer deleted = Payer.query([customerId: customer.id, deletedOnly: true]).list().size()
        
        return [
            active: active,
            deleted: deleted,
            total: active + deleted
        ]
    }
}