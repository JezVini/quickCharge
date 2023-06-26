<g:each in="${actionMap}" var="actionName, action">
    <atlas-button
        type="${action.type}"
        slot="${action.slot}"
        description="${action.description}"
        tooltip="${action.tooltip}"
        href="${action.href}">
    </atlas-button>
</g:each>