<atlas-button
    description="${buttonAttributes.description}"
    icon="${buttonAttributes.icon}"
    href="${buttonAttributes.href}"
    tooltip="${buttonAttributes.tooltip}"
    ${buttonAttributes.disabled ? "disabled" : ""}
    ${buttonAttributes.'is-external-link' ? "is-external-link" : ""}
    ${buttonAttributes.submit ? "submit" : ""}></atlas-button>