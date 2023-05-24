package utils.baseperson

import grails.gorm.dirty.checking.DirtyCheck
import utils.entity.BaseEntity

@DirtyCheck
abstract class BasePerson extends BaseEntity {

    String name
    String email
    String cpfCnpj
    String phone
    String state
    String city
    String district
    String number
    String postalCode

    static constraints = {
        name blank: false
        email blank: false, email: true
        cpfCnpj blank: false, size: 11..14
        phone blank: false, size: 10..11
        state blank: false, size: 2
        city blank: false
        district blank: false
        number blank: false
        postalCode blank: false, size: 8
    }

}
