package utils.entity

abstract class BaseEntity implements Comparable {

    Date dateCreated
    Date lastUpdated
    Boolean deleted = false

    static mapping = {
        tablePerHierarchy false
    }

    int compareTo(obj) {
        obj.lastUpdated.compareTo(lastUpdated)
    }
}
