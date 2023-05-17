package utils.entity

import java.time.LocalDateTime

abstract class BaseEntity {

    long id
    LocalDateTime createdAt
    boolean deleted

    static mapping = {
        version false
    }
}
