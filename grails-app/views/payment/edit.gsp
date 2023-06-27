<html>
    <head>
        <meta name="layout" content="main">
        <title>Alterar dados da Cobrança</title>
    </head>

    <body>
        <%@ page import="utils.payment.BillingType" %>

        <main>
            <g:if test="${payment}">
                <atlas-form action="${createLink(controller: "payment", action: "update")}" method="post">
                    <atlas-layout gap="4">
                        <atlas-input type="hidden" name="id" value="${payment.id}"></atlas-input>
                        
                        <atlas-input
                            label="Cliente"
                            value="${payment.payer.name}"
                            required
                            disabled></atlas-input>

                        <atlas-money
                            label="Valor da cobrança"
                            value="<g:formatNumberSeparatorWithComma value='${payment.value}'/>"
                            name="value"
                            min-value="5"
                            min-value-error-message="O valor mínimo é 5 reais"
                            required></atlas-money>

                        <atlas-input
                            label="Forma de pagamento"
                            value="${message(code: 'ENUM.BillingType.' + payment.billingType.toString())}"
                            required
                            disabled></atlas-input>

                        <atlas-datepicker label="Data de vencimento"
                                          value="${g.formatDate(format: "dd/MM/yyyy", date: payment.dueDate)}}"
                                          name="dueDate"
                                          icon="calendar"
                                          prevent-past-date
                                          required-error-message="Necessário preencher"
                                          required></atlas-datepicker>

                        <atlas-button submit description="Salvar Cobrança"></atlas-button>
                    </atlas-layout>
                </atlas-form>
            </g:if>
        </main>
    </body>
</html>