<html>
    <head>
        <meta name="layout" content="main">
        <title>Listagem de cobranças</title>
    </head>

    <body>

        <h1>Mostrando cobranças válidas</h1>

        <p><g:message code="${flash.message}"/></p>

        <g:link action="create">
            <button>Criar cobrança</button>
        </g:link>

        <table>
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
                    <tr>
                        <td><g:link controller="payer" action="edit" params="${[id: payment.payer.id]}">
                            ${payment.payer.name}
                        </g:link></td>

                        <td><g:link controller="payer" action="edit" params="${[id: payment.payer.id]}">
                            ${payment.payer.email}
                        </g:link></td>

                        <td>${payment.value}</td>
                        <td><g:message code="ENUM.BillingType.${payment.billingType}"/></td>
                        <td>${payment.dueDate}</td>
                        <td><g:message code="ENUM.PaymentStatus.${payment.status}"/></td>

                        <g:if test="${payment.status.canUpdate()}">
                            <td><g:link action="delete" params="${[id: payment.id, customerId: payment.customer.id]}">
                                <button style="background-color: #f00">Remover</button>
                            </g:link></td>
                        </g:if>
                        
                    </tr>
                </g:each>
            </g:if>
            <g:else>
                <tr><td>Nenhuma cobrança encontrada</td></tr>
            </g:else>
        </table>
    </body>
</html>