<html>
    <head>
        <meta name="layout" content="main">
        <title>Criar cobrança</title>
    </head>

    <body>
        <%@ page import="utils.payment.BillingType" %>

        <g:message code="${flash.message}"/>
        <atlas-form action="${createLink(controller: "payment", action: "save")}" method="post">
            <atlas-layout gap="4">
                <atlas-select label="Cliente"
                              name="payerId"
                              required>
                    <g:each var="payer" in="${payerList}">
                        <atlas-option
                            label="${payer.name}"
                            value="${payer.id}"></atlas-option>
                    </g:each>
                </atlas-select>

                <atlas-money
                    label="Valor da cobrança"
                    name="value"
                    min-value="5"
                    min-value-error-message="O valor mínimo é 5 reais"
                    required></atlas-money>

                <atlas-select label="Forma de pagamento"
                              name="billingType"
                              required>
                    <g:each var="type" in="${BillingType.values()}">
                        <atlas-option
                            label="${message(code: 'ENUM.BillingType.' + type.toString())}"
                            value="${type}"></atlas-option>
                    </g:each>
                </atlas-select>

                <atlas-datepicker label="Data de vencimento"
                                  name="dueDate"
                                  icon="calendar"
                                  required-error-message="Necessário preencher"
                                  prevent-past-date
                                  required></atlas-datepicker>

                <atlas-button submit description="Criar Cobrança"></atlas-button>
            </atlas-layout>
        </atlas-form>
    </body>
</html>