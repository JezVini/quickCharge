package utils.entity

abstract class BaseEntity {

    Date createdAt
    Date lastUpdated
    Boolean deleted

    static mapping = {
        version false
        tablePerHierarchy false
    }
}
