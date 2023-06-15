<html>
    <head>
        <meta name="layout" content="main">
        <title>Alterar dados da Cobrança</title>
    </head>

    <body>
        <main>
            <g:message code="${flash.message}"/>
            <g:if test="${payment}">
                <form action="${createLink(controller: "payment", action: "update")}" method="post">
                    <input type="hidden" name="id" value="${payment.id}">

                    <label for="payerId">Cliente</label>
                    <input type="text" name="payerId" id="payerId" value="${payment.payer.name}" disabled="">
                    <br>

                    <label for="billingType">Forma de pagamento</label>
                    <g:select name="billingType" id="billingType"
                              from="${billingType}"
                              valueMessagePrefix="ENUM.BillingType"
                              value="${payment.billingType}"
                              disabled=""/>
                    <br>

                    <label for="value">Valor da cobrança</label>
                    <input type="number" min="0.01" step="any" name="value" id="value" value="${payment.value}"
                           placeholder="Digite o valor da cobrança">
                    <br>

                    <label for="dueDate">Data de vencimento</label>
                    <input type="date" name="dueDate" id="dueDate"
                           value="${g.formatDate(format: "dd/MM/yyyy", date: payment.dueDate)}">
                    <br>

                    <button type="submit">Salvar</button>
                </form>
            </g:if>
        </main>
    </body>
</html>