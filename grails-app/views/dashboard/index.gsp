<html>
    <head>
        <meta name="layout" content="main">
        <title>QuickCharge</title>
        <asset:stylesheet src="dashboard.css"/>
    </head>

    <body class="menu-page-container">

        <atlas-panel header="Olá ${customer.name}!">
            <atlas-grid>
                <atlas-row>
                    <atlas-col lg="6" class="col-item">
                        <atlas-layout gap="4">
                            <atlas-card header="Suas cobranças" class="payment-info-card">
                                <atlas-layout gap="4">
                                    <div class="menu-payment-data-table-wrap">
                                        <table class="menu-data-table">
                                            <tr>
                                                <th class="dashboard-table-data-header">Recebidas</th>
                                                <th class="dashboard-table-data-header">Pendentes</th>
                                                <th class="dashboard-table-data-header">Vencidas</th>
                                            </tr>
                                            <tr>
                                                <td class="dashboard-table-data">${paymentGeneralData.received}</td>
                                                <td class="dashboard-table-data">${paymentGeneralData.pending}</td>
                                                <td class="dashboard-table-data">${paymentGeneralData.overdue}</td>
                                            </tr>
                                        </table>
                                    </div>
                                    <div class="menu-payment-data-table-wrap">
                                        <table class="menu-data-table">
                                            <tr>
                                                <th class="dashboard-table-data-header">Valor já recebido</th>
                                                <th class="dashboard-table-data-header">Valor a receber</th>
                                            </tr>
                                            <tr>    
                                                <td class="dashboard-table-data"><g:formatMonetaryValue
                                                    value="${paymentGeneralData.receivedValue}"/></td>
                                                <td class="dashboard-table-data"><g:formatMonetaryValue
                                                    value="${paymentGeneralData.toReceiveValue}"/></td>
                                            </tr>
                                        </table>
                                    </div>
                                    <atlas-button
                                        description="Ver minhas cobranças"
                                        icon="eye"
                                        href="${createLink(controller: "payment", action: "index")}">
                                    </atlas-button>
                                </atlas-layout>
                            </atlas-card>
                        </atlas-layout>
                    </atlas-col>
                    <atlas-col lg="6">
                        <atlas-card header="Seus clientes" class="payment-info-card">
                            <atlas-layout gap="4">
                                <div class="menu-payment-data-table-wrap">
                                    <table class="menu-data-table">
                                        <tr>
                                            <th class="dashboard-table-data-header">Ativos</th>
                                            <th class="dashboard-table-data-header">Desativados</th>
                                            <th class="dashboard-table-data-header">Cadastrados</th>
                                        </tr>
                                        <tr>
                                            <td class="dashboard-table-data">${payerGeneralData.active}</td>
                                            <td class="dashboard-table-data">${payerGeneralData.deleted}</td>
                                            <td class="dashboard-table-data">${payerGeneralData.total}</td>
                                        </tr>
                                    </table>
                                </div>
                                <atlas-button
                                    description="Ver meus clientes"
                                    icon="eye"
                                    href="${createLink(controller: "payer", action: "index")}">
                                </atlas-button>
                            </atlas-layout>
                        </atlas-card>
                    </atlas-col>
                    <atlas-col lg="8"></atlas-col>
                </atlas-row>
            </atlas-grid>
        </atlas-panel>
    </body>
</html>
