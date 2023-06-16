<html>
    <head>
        <meta name="layout" content="main">
        <title>Listagem de cobranças</title>
    </head>

    <body>

        <h1>
            <g:if test="${deletedOnly}">
                Mostrando cobranças desativadas
            </g:if>
            <g:elseif test="${includeDeleted}">
                Mostrando todas cobranças
            </g:elseif>
            <g:else>
                Mostrando cobranças ativas
            </g:else>
        </h1>

        <p><g:message code="${flash.message}"/></p>

        <g:link action="create">
            <button>Criar cobrança</button>
        </g:link>

        <table>

            <tr>
                <th>
                    <g:link action="index">
                        <button>Mostrar apenas cobranças ativas</button>
                    </g:link>
                </th>

                <th>
                    <g:link action="index" params="${[deletedOnly: true]}">
                        <button>Mostrar apenas cobranças desativadas</button>
                    </g:link>
                </th>

                <th>
                    <g:link action="index" params="${[includeDeleted: true]}">
                        <button>Mostrar todas cobranças</button>
                    </g:link>
                </th>
            </tr>

            <tr>
                <th>Nome do pagador</th>
                <th>E-mail do pagador</th>
                <th>Valor da cobrança</th>
                <th>Forma de pagamento</th>
                <th>Data de vencimento</th>
                <th>Situação</th>
                <th>Ações</th>
            </tr>
            <g:if test="${paymentList}">
                <g:each in="${paymentList}" var="payment">

                    <%
                        Map parameterMap = [
                            id            : payment.id,
                            deletedOnly   : deletedOnly,
                            includeDeleted: includeDeleted
                        ]
                    %>

                    <tr>
                        <td><g:link controller="payer" action="edit" params="${[id: payment.payer.id]}">
                            ${payment.payer.name}
                        </g:link></td>

                        <td><g:link controller="payer" action="edit" params="${[id: payment.payer.id]}">
                            ${payment.payer.email}
                        </g:link></td>

                        <td>${payment.value}</td>
                        <td><g:message code="ENUM.BillingType.${payment.billingType}"/></td>
                        <td><g:formatDate format="dd/MM/yyyy" date="${payment.dueDate}"/></td>
                        <td><g:message code="ENUM.PaymentStatus.${payment.status}"/></td>

                        <td>
                            <g:if test="${payment.deleted}">
                                <g:link action="restore" params="${parameterMap}">
                                    <button style="background-color: #0f0">Restaurar</button>
                                </g:link>
                            </g:if>
                            <g:elseif test="${payment.status.canUpdate()}">
                                <g:link action="delete" params="${parameterMap}">
                                    <button style="background-color: #f00">Remover</button>
                                </g:link>
                            </g:elseif>
                        </td>

                    </tr>
                </g:each>
            </g:if>
            <g:else>
                <tr><td>Nenhuma cobrança encontrada</td></tr>
            </g:else>
        </table>
    </body>
</html>