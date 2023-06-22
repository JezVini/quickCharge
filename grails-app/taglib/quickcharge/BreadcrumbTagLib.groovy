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
                for (def item : menuItem.dropdownItemsList) {
                    if (item.active)
                        breadcrumbList.add([
                            name: item.text,
                            href: item.href
                        ])
                    
                }
            }
        }
        
        return breadcrumbList
    }
}
