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
            icon: "money",
            text: "Minhas Cobranças",
            value: "paymentIndex",
            active: (controllerName == "payment") && ((actionName == "index") || (actionName == "create") || (actionName == "edit")),
            dropdownItemsList: [
                [
                    icon: "money",
                    text: "Visualizar Cobranças",
                    href: createLink(controller: "payment", action: "index"),
                    value: "paymentIndex",
                    active: (controllerName == "payment") && ((actionName == "index") || (actionName == "edit"))
                ],
                [
                    icon: "hand-shake",
                    text: "Criar Cobrança",
                    href: createLink(controller: "payment", action: "create"),
                    value: "paymentCreate",
                    active: (controllerName == "payment") && (actionName == "create")
                ]
            ]
        ])

        menuItemsList.add([
            icon: "users",
            text: "Meus Clientes",
            value: "payer",
            active: (controllerName == "payer") && ((actionName == "index") || (actionName == "create") || (actionName == "edit")),
            dropdownItemsList: [
                [
                    icon: "users",
                    text: "Visualizar Clientes",
                    href: createLink(controller: "payer", action: "index"),
                    value: "payerIndex",
                    active: (controllerName == "payer") && ((actionName == "index") || (actionName == "edit"))
                ],
                [
                    icon: "user-plus",
                    text: "Cadastrar Novo Cliente",
                    href: createLink(controller: "payer", action: "create"),
                    value: "payerCreate",
                    active: (controllerName == "payer") && (actionName == "create")
                ]
            ]
        ])

        menuItemsList.add([
            icon: "user",
            text: "Meu Perfil",
            value: "customer",
            active: (controllerName == "customer") && (actionName == "edit"),
            dropdownItemsList: [
                [
                    icon: "user",
                    text:  "Editar Perfil",
                    href:  "${createLink(controller: 'customer', action: 'edit')}",
                    value:  "customer",
                    active: (controllerName == "customer") && (actionName == "edit"),
                ],
                [
                    icon: "power",
                    text: "Logout",
                    href: "${createLink(controller:'logout', action: 'index')}",
                    value: "logout"
                ]
            ]
        ])

        flash.sidebar = menuItemsList
        return menuItemsList
    }
}
