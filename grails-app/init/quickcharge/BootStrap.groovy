package quickcharge

import com.quickcharge.app.authentication.Role
import com.quickcharge.app.authentication.User
import com.quickcharge.app.authentication.UserRole
import com.quickcharge.app.customer.Customer

class BootStrap {
    
    def init = { servletContext ->
        Role roleUser = Role.findByAuthority("ROLE_USER")
        if (roleUser == null){
            roleUser = new Role(authority: "ROLE_USER").save()
        }

        User user = User.findByUsername("user@email.com")
        if (user == null) {
            Customer customer = new Customer(
                name: "User",
                email: "user@email.com",
                cpfCnpj: "54493586010",
                phone: "0000000000",
                state: "AA",
                city: "city",
                district: "district",
                addressNumber: "addressNumber",
                postalCode: "00000000",
                address: "address",
                addressComplement: "addressComplement").save()
            
            user = new User(username: customer.email, password: "user", customer: customer, enabled: true).save()
        }
        
        if (!UserRole.findByUserAndRole(user, roleUser)) {
            UserRole.withTransaction {
                    new UserRole(user: user, role: roleUser).save(flush:true)
            }
        }
    }
        
    def destroy = {
    }
}
