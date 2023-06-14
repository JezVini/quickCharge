package quickcharge

class SideBarTagLib {
    static namespace = "sideBar"

    def render = { attrs ->
        List<Map> menuList = buildMenu()
                
        out << g.render(template: "/shared/templates/sidebarmenu", model: [menuList: menuList])
    }
    
    private List<Map> buildMenu() {
        List<Map> menuItemsList = []
        
        menuItemsList.add([
            icon: "hand-shake", 
            text: "Criar Cobrança",
            href: "#",
            value: "#"
        ])

        menuItemsList.add([
            icon: "money",
            text: "Minhas Cobranças",
            href: "#",
            value: "#"
        ])

        menuItemsList.add([
            icon: "users",
            text: "Meus Clientes",
            href: createLink(controller: "payer", action: "index"),
            value: "payer",
            active: (controllerName == "payer") && (actionName == "index")
        ])

        menuItemsList.add([
            icon: "user",
            text: "Meu Perfil",
            href: createLink(controller: "customer", action: "edit"),
            value: "customer",
            active: (controllerName == "customer") && (actionName == "edit")
        ])
        
        return menuItemsList
    }
}