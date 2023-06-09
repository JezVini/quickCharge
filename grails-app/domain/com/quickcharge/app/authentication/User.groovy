package com.quickcharge.app.authentication

import com.quickcharge.app.customer.Customer
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import grails.compiler.GrailsCompileStatic
import utils.entity.BaseEntity

@GrailsCompileStatic
@EqualsAndHashCode(includes='username')
@ToString(includes='username', includeNames=true, includePackage=false)

class User extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1
    
    String username
    String password
    Customer customer
    boolean enabled = true
    boolean accountExpired
    boolean accountLocked
    boolean passwordExpired

    Set<Role> getAuthorities() {
        (UserRole.findAllByUser(this) as List<UserRole>)*.role as Set<Role>
    }

    static constraints = {
        password blank: false, password: true
        username blank: false, unique: true, email: true
        customer blank: false
    }

    static mapping = {
	    password column: '`password`'
    }
}
