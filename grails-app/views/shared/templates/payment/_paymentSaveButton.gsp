<atlas-button
    description="${buttonAttributes.description}"
    icon="${buttonAttributes.icon}"
    tooltip="${buttonAttributes.tooltip}"
    ${buttonAttributes.disabled ? "disabled" : ""}
    ${buttonAttributes.submit ? "submit" : ""}></atlas-button>