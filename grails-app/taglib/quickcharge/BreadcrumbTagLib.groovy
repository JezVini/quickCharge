package quickcharge

class BreadcrumbTagLib {
    static namespace = "breadcrumb"

    def render = { attrs ->
        out << g.render(template: "/shared/templates/breadcrumb", model: [breadcrumbList: flash.breadcrumbList])
    }

    def activePageName = { attrs ->
        List<Map> breadcrumbList = generateBreadcrumbList()
        flash.breadcrumbList = breadcrumbList
        out << (!breadcrumbList.isEmpty() ? breadcrumbList.get(breadcrumbList.size() - 1).name : "").toString()
    }

    private List<Map> generateBreadcrumbList() {
        List<Map> breadcrumbList = []

        for (Map menuItem : flash.sidebar) {
            if (menuItem.active) {
                breadcrumbList.add([
                    name: menuItem.text
                ])
                
                for (Map item : menuItem.dropdownItemsList) {
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
