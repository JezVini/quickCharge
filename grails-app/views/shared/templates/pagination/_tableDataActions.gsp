<td class="actions">
    <g:each in="${actionMap}" var="actionName, action">
        <atlas-button size="sm" class="action"
                      type="${action.type}"
                      slot="${action.slot}"
                      theme="${action.theme}"
                      icon="${action.icon}"
                      tooltip="${action.tooltip}"
                      href="${action.href}"
            ${action.disabled}>
        </atlas-button>
    </g:each>
</td>