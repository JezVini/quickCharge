<atlas-sidebar
    slot="sidebar"
    product-logo="${asset.assetPath(src: '/logo/quickChargeNormal.svg')}"
    product-logo-small="${asset.assetPath(src: '/logo/quickChargeSmall.svg')}"
    home-path="/"
    collapsed>
    <atlas-sidebar-menu slot="body">
        <g:each var="menu" in="${menuList}">
            <atlas-sidebar-menu-item
                icon="${menu.icon}"
                text="${menu.text}"
                href="${menu.href}"
                value="${menu.value}"
                ${menu.active ? 'active' : ''}>
            </atlas-sidebar-menu-item>
        </g:each>
    </atlas-sidebar-menu>
</atlas-sidebar>
