<html>
    <head>
        <meta name="layout" content="main">
        <title>Criar cobrança</title>
    </head>

    <body>
        <%@ page import="utils.payment.BillingType" %>
        <atlas-panel header="Dados cobrança" style="display: flex">
            <atlas-form action="${createLink(controller: "payment", action: "save")}" method="post">
                <atlas-layout gap="8">
                    <atlas-section style="display: flex;">
                        <atlas-grid>
                            <atlas-row margin-bottom="5">
                                <atlas-col lg="2">
                                    <atlas-money
                                        label="Valor da cobrança"
                                        name="value"
                                        min-value="5"
                                        min-value-error-message="O valor mínimo é 5 reais"
                                        required></atlas-money>
                                </atlas-col>

                                <atlas-col lg="3">
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

                                <atlas-col lg="7">
                                    <atlas-card
                                        header="Nota!"
                                        background="light"
                                        badge-theme="primary"
                                        badge-text="Lembrete"><atlas-text>Ao criar a cobrança, serão encaminhadas notificações ao seu e-mail e do cliente com todos os detalhes!</atlas-text>
                                    </atlas-card>
                                </atlas-col>
                            </atlas-row>

                            <atlas-row>
                                <atlas-col lg="2">
                                    <atlas-datepicker label="Data de vencimento"
                                                      name="dueDate"
                                                      icon="calendar"
                                                      required-error-message="Necessário preencher"
                                                      prevent-past-date
                                                      required></atlas-datepicker>
                                </atlas-col>

                                <atlas-col lg="3">
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
                            </atlas-row>
                        </atlas-grid>
                    </atlas-section>
                    <atlas-button submit description="Criar Cobrança"></atlas-button>
                </atlas-layout>
            </atlas-form>
        </atlas-panel>
    </body>
</html>