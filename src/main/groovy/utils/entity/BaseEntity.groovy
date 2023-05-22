package utils.entity

abstract class BaseEntity {

    Date dateCreated
    Date lastUpdated
    Boolean deleted

    static mapping = {
        tablePerHierarchy false
    }

    static constraints = {
        dateCreated nullable: false
        lastUpdated nullable: false
        deleted nullable: false
    }

}
