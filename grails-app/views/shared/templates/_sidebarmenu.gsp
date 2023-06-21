<atlas-sidebar
    slot="sidebar"
    product-logo="${asset.assetPath(src: '/logo/quickChargeNormal.svg')}"
    product-logo-small="${asset.assetPath(src: '/logo/quickChargeSmall.svg')}"
    home-path="/"
    collapsed
    class="js-atlas-sidebar">

    <atlas-button
        block
        size="lg"
        slot="header"
        description="Criar Cobrança"
        href="${createLink(controller: "payment", action: "create")}"
        class="js-create-payment-button"></atlas-button>

    <atlas-sidebar-menu slot="body">
        <g:each var="menu" in="${menuList}">
            <atlas-sidebar-menu-item
                icon="${menu.icon}"
                text="${menu.text}"
                href="${menu.href}"
                value="${menu.value}"
                ${menu.active ? 'active' : ''}>

                <g:if test="${(menu.containsKey('dropdownItemsList'))}">
                    <g:each var="menuItem" in="${menu.dropdownItemsList}">
                        <atlas-sidebar-menu-item
                            icon="${menuItem.icon}"
                            text="${menuItem.text}"
                            href="${menuItem.href}"
                            value="${menuItem.value}"
                            ${menuItem.active ? 'active' : ''}>
                        </atlas-sidebar-menu-item>
                    </g:each>
                </g:if>
            </atlas-sidebar-menu-item>
        </g:each>
    </atlas-sidebar-menu>
</atlas-sidebar>
