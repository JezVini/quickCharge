package quickcharge

import com.quickcharge.app.user.Role

class BootStrap {

    def init = { servletContext ->
        def authorities = ['ROLE_USER']
        authorities.each {
            if (!Role.findByAuthority(it)) {
                new Role(authority: it).save()

            }
        }
    }
    
    def destroy = {
    }
}
