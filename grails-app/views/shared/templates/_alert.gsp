<g:if test="message">
    <g:javascript>
        document.addEventListener("DOMContentLoaded", function () {
            Atlas.notifications.showAlert("${message}", "${type}");
        });
    </g:javascript>
</g:if>