package quickcharge

class BreadcrumbTagLib {
    static namespace = "breadcrumb"

    def render = { attrs ->
        List<Map> breadcrumbList = getCurrentPageName()

        out << g.render(template: "/shared/templates/breadcrumb", model: [breadcrumbList: breadcrumbList])
    }

    private List<Map> getCurrentPageName() {
        List<Map> breadcrumbList = []

        for (def menuItem : flash.sidebar) {
            if (menuItem.active) {
                breadcrumbList.add([
                    name: menuItem.text
                ])
                
                for (def item : menuItem.dropdownItemsList) {
                    if (item.active)
                        breadcrumbList.add([
                            name: item.text,
                            href: item.href
                        ])

                    if (item.active && actionName == "edit" && item.value != "customer") {
                        breadcrumbList.add([
                            name: "Detalhes"
                        ])
                    }
                }
            }
        }

        return breadcrumbList
    }
}
