<atlas-breadcrumb>
    <g:each var="name" in="${breadcrumbList}">
        <atlas-breadcrumb-item name="${name.name}"
                               href="${name.href}"
                               icon="home"></atlas-breadcrumb-item>
    </g:each>
</atlas-breadcrumb>
