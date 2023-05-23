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

}
