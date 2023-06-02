package quickcharge

import com.quickcharge.app.authentication.Role
import com.quickcharge.app.authentication.User
import com.quickcharge.app.authentication.UserRole

class BootStrap {
    
    def init = { servletContext ->
        Role roleUser = Role.findByAuthority("ROLE_USER")
        if (roleUser == null){
            roleUser = new Role(authority: "ROLE_USER").save()
        }

        User user = User.findByUsername("user")
        if (user == null) {
            user = new User(username: "user", password: "user", enabled: true).save()
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
