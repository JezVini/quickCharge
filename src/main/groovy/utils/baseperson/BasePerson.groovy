package utils.baseperson

import utils.baseperson.Address

class BasePerson {

    String name
    String email
    String cpfCnpj
    String phone
    Address address

    static embedded = ['address']

}
