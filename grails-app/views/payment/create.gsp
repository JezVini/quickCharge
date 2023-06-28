<html>
    <head>
        <meta name="layout" content="main">
        <title>Criar cobrança</title>
    </head>

    <body>
        <%@ page import="utils.payment.BillingType" %>
%{--        <g:set var="pageName" scope="request" value="${flash.activePageName}"/>--}%
        <atlas-panel>
            <atlas-form action="${createLink(controller: "payment", action: "save")}" method="post">
                <atlas-layout gap="8">
                    <atlas-section header="Dados cobrança">
                        <atlas-grid>
                            <atlas-row>
                                <atlas-col lg="6">
                                    <atlas-money
                                        label="Valor da cobrança"
                                        name="value"
                                        min-value="5"
                                        min-value-error-message="O valor mínimo é 5 reais"
                                        required></atlas-money>
                                </atlas-col>

                                <atlas-col lg="6">
                                    <atlas-select label="Cliente"
                                                  name="payerId"
                                                  required>
                                        <g:each var="payer" in="${payerList}">
                                            <atlas-option
                                                label="${payer.name}"
                                                value="${payer.id}"></atlas-option>
                                        </g:each>
                                    </atlas-select>
                                </atlas-col>
                            </atlas-row>

                            <atlas-row>
                                <atlas-col lg="6">
                                    <atlas-select label="Forma de pagamento"
                                                  name="billingType"
                                                  required>
                                        <g:each var="type" in="${BillingType.values()}">
                                            <atlas-option
                                                label="${message(code: 'ENUM.BillingType.' + type.toString())}"
                                                value="${type}"></atlas-option>
                                        </g:each>
                                    </atlas-select>
                                </atlas-col>

                                <atlas-col lg="6">
                                    <atlas-datepicker label="Data de vencimento"
                                                      name="dueDate"
                                                      icon="calendar"
                                                      required-error-message="Necessário preencher"
                                                      prevent-past-date
                                                      required></atlas-datepicker>
                                </atlas-col>
                            </atlas-row>
                            
                            <atlas-button submit description="Criar Cobrança" block></atlas-button>
                        </atlas-grid>
                    </atlas-section>
                </atlas-layout>
            </atlas-form>
        </atlas-panel>
    </body>
</html>