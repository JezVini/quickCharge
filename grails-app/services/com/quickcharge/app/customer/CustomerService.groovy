

import grails.gorm.transactions.Transactional

@Transactional
class CustomerService {

    public Customer save(Map params) {
        Customer customer = validateCustomer(params)

        if (customer.hasErrors()) {
            //throw ex
            return customer
        }

        customer.with {
            name = params.name
            email = params.email
            cpfCnpj = params.cpfCnpj
            phone = params.phone
            state = params.state
            city = params.city
            district = params.district
            number = params.number
            postalCode = params.postalCode
         }

        //throw ex
        customer.save()
        return customer
    }

    private Customer validateCustomer(Map params) {
        Customer customer = new Customer()

        if (!params.email) {
            customer.errors.reject("", null, "O campo e-mail é obrigatório")
        }
        if (!params.cpfCnpj) {
            customer.errors.reject("", null, "O campo CPF ou CNPJ é obrigatório")
        }
        if (!params.phone) {
            customer.errors.reject("", null, "O campo telefone é obrigatório")
        }
        if (!params.state) {
            customer.errors.reject("", null, "O campo estado é obrigatório")
        }
        if (!params.city) {
            customer.errors.reject("", null, "O campo cidade é obrigatório")
        }
        if (!params.district) {
            customer.errors.reject("", null, "O campo bairro é obrigatório")
        }
        if (!params.number) {
            customer.errors.reject("", null, "O campo número é obrigatório")
        }
        if (!params.postalCode) {
            customer.errors.reject("", null, "O campo CEP é obrigatório")
        }

        return customer
    }
}
