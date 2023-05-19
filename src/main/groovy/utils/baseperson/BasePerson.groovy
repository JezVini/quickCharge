package utils.baseperson

abstract class BasePerson {

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
        name blank: false, nullable: false
        email blank: false, nullable: false, email: true
        cpfCnpj blank: false, nullable: false, size: 11..14
        phone blank: false, nullable: false, size: 10..11
        state blank: false, nullable: false, size: 2
        city blank: false, nullable: false
        district blank: false, nullable: false
        number blank: false, nullable: false
        postalCode blank: false, nullable: false, size: 8
    }

}
