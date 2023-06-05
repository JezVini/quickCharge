package com.quickcharge.app.authentication

import com.quickcharge.app.customer.Customer
import grails.gorm.transactions.Transactional

@Transactional
class UserService {
    
    public void save(Customer customer, String email, String password) {
        User user = new User()
        user.customer = customer
        user.username = email
        user.password = password

        user.save(failOnError: true)
        new UserRole(user: user, role: Role.findByAuthority("ROLE_USER")).save(failOnError: true)
    }
}
