package utils.entity

abstract class BaseEntity {

    Date dateCreated
    Date lastUpdated
    Boolean deleted

    static mapping = {
        tablePerHierarchy false
    }
}
