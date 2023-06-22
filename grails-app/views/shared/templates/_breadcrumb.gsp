<atlas-breadcrumb>
    <atlas-breadcrumb-item name="QuickCharge" icon="home" href="/index"></atlas-breadcrumb-item>
    <g:each var="name" in="${breadcrumbList}">
        <atlas-breadcrumb-item name="${name.name}"
                               href="${name.href}"></atlas-breadcrumb-item>
    </g:each>
</atlas-breadcrumb>
