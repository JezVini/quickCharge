<html>
    <head>
        <meta name="layout" content="main">
        <title>Criar cobrança</title>
    </head>

    <body>
        <%@ page import="utils.payment.BillingType" %>

        <g:message code="${flash.message}"/>
        <form action="${createLink(controller: "payment", action: "save")}" method="post">

            <label for="payerId">Pagador</label>
            <g:select name="payerId" id="payerId" from="${payerList}" optionValue="name" optionKey="id"
                      noSelection="['': 'Selecione o pagador']" value="${params.payerId}"/>
            <br>

            <label for="billingType">Forma de pagamento</label>
            <g:select name="billingType" id="billingType" from="${BillingType.values()}" valueMessagePrefix="ENUM.BillingType"
                      noSelection="['': 'Selecione a forma de pagamento']" value="${params.billingType}"/>
            <br>

            <label for="value">Valor da cobrança</label>
            <input type="number" min="0.01" step="any" name="value" id="value" value="${params.value}"
                   placeholder="Digite o valor da cobrança">
            <br>

            <label for="dueDate">Data de vencimento</label>
            <input type="date" name="dueDate" id="dueDate" value="${params.dueDate}"
                   placeholder="Insira a data de vencimento">
            <br>

            <button type="submit">Criar cobrança</button>
        </form>
    </body>
</html>