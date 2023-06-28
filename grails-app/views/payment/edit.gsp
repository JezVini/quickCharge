<html>
    <head>
        <meta name="layout" content="main">
        <title>Alterar dados da Cobrança</title>
    </head>

    <body>
        <%@ page import="utils.payment.BillingType" %>

        <main>
            <g:if test="${payment}">
                <atlas-panel>
                    <atlas-form action="${createLink(controller: "payment", action: "update")}" method="post">
                        
                        <atlas-input type="hidden" name="id" value="${payment.id}"></atlas-input>
                        
                        <atlas-layout gap="8">
                            <atlas-section header="Dados cobrança">
                                <atlas-grid>
                                    <atlas-row>
                                        <atlas-col lg="6">
                                            <atlas-input
                                                label="Cliente"
                                                value="${payment.payer.name}"
                                                required
                                                disabled></atlas-input>
                                        </atlas-col>

                                        <atlas-col lg="6">
                                            <atlas-input
                                                label="Forma de pagamento"
                                                value="${message(code: 'ENUM.BillingType.' + payment.billingType.toString())}"
                                                required
                                                disabled></atlas-input>
                                        </atlas-col>
                                    </atlas-row>

                                    <atlas-row>
                                        <atlas-col lg="6">
                                            <atlas-money
                                                label="Valor da cobrança"
                                                value="<g:formatNumberSeparatorWithComma value='${payment.value}'/>"
                                                name="value"
                                                min-value="5"
                                                min-value-error-message="O valor mínimo é 5 reais"
                                                required></atlas-money>
                                        </atlas-col>

                                        <atlas-col lg="6">
                                            <atlas-datepicker label="Data de vencimento"
                                                              value="${g.formatDate(format: "dd/MM/yyyy", date: payment.dueDate)}}"
                                                              name="dueDate"
                                                              icon="calendar"
                                                              prevent-past-date
                                                              required-error-message="Necessário preencher"
                                                              required></atlas-datepicker>
                                        </atlas-col>
                                    </atlas-row>

                                    <atlas-button submit description="Salvar Cobrança"></atlas-button>
                                </atlas-grid>
                            </atlas-section>
                            
                        </atlas-layout>
                        
                    </atlas-form>
                </atlas-panel>
            </g:if>
        </main>
    </body>
</html>