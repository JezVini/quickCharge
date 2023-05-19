package utils.entity

abstract class BaseEntity {

    Date dateCreated
    Date lastUpdated
    Boolean deleted

    static mapping = {
        tablePerHierarchy false
        deleted defaultValue: false
    }

    static constraints = {
        dateCreated blank: false, nullable: false
        lastUpdated blank: false, nullable: false
        deleted blank: false, nullable: false
    }

}
