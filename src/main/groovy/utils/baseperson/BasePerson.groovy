package utils.baseperson

import utils.entity.BaseEntity

abstract class BasePerson extends BaseEntity {

    String name
    String email
    String cpfCnpj
    String phone
    String state
    String city
    String district
    String addressNumber
    String postalCode
    String address
    String addressComplement

    static constraints = {
        name blank: false
        email blank: false, email: true
        cpfCnpj blank: false, size: 11..14
        phone blank: false, size: 10..11
        state blank: false, size: 2..2
        city blank: false
        district blank: false
        addressNumber blank: false
        postalCode blank: false, size: 8..8
        address blank: false
        addressComplement blank: false, nullable: true
    }
}
