<div class="pagination-controllers-container">

    <script>
        document.addEventListener("DOMContentLoaded", function () {
            const select = document.querySelector(".pagination-max-value");
            select.addEventListener("atlas:select-change", function () {
                <%
                    parameterMap.offset = 0        
                    parameterMap.remove("max")
                %>

                const maxValue = select.getElementValue();
                const link = "${createLink(action: 'index', params: parameterMap)}" + "&max=" + maxValue
                window.location.href = link.replace(/&amp;/g, "&");
            })
        })
    </script>

    <div class="pagination-range-select-controller">
        <span>Exibindo</span>

        <div class="select-wrap">
            <atlas-select class="pagination-max-value" value="${max}">
                <atlas-option label="10" value="10"></atlas-option>
                <atlas-option label="25" value="25"></atlas-option>
                <atlas-option label="50" value="50"></atlas-option>
                <atlas-option label="100" value="100"></atlas-option>
            </atlas-select>
        </div>

        <span>registros</span>
    </div>

    <div class="pagination-page-controllers">
        <div class="pagination-page-buttons-container">
            <g:each in="${buttonList}" var="button">
                <span class="pagination-page-button-background">
                    <atlas-button type="${button.type}" size="md" theme="primary"
                                  description="${button.number}"
                                  class="${button.class}"
                                  href="${button.href}"
                                  tooltip="${button.tooltip}"
                                  icon="${button.icon}"
                        ${button.disabled}>
                    </atlas-button>
                </span>
            </g:each>
        </div>
    </div>
</div>