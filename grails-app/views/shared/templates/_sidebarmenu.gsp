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
                
        <atlas-sidebar-menu-item
            icon="user"
            text= "Meu Perfil"
            value= "customer">
            <atlas-sidebar-menu-item
                icon="user"
                text= "Editar Perfil"
                href= "${createLink(controller: 'customer', action: 'edit')}"
                value= "customer"></atlas-sidebar-menu-item>
            <atlas-sidebar-menu-item
                icon="power"
                text="Logout"
                href="${createLink(controller:'logout')}"
                value="logout">
            </atlas-sidebar-menu-item>
        </atlas-sidebar-menu-item>
    </atlas-sidebar-menu>
</atlas-sidebar>
