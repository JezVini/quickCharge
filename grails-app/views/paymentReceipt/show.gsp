<html>
    <head>
        <title>Comprovante de Pagamento</title>
        <asset:link rel="icon" href="logo/quickCharge.ico" type="image/x-ico"/>
        <asset:stylesheet src="paymentReceipt/paymentReceipt.css"/>
    </head>

    <body>
        <g:if test="${receipt}">
            <div class="mainContainer">
                <header>
                    <div class="headerInfo">
                        <h1>Comprovante de pagamento</h1>

                        <p>
                            Gerado dia ${g.formatDate(format: "dd/MM/yyyy", date: receipt.dateCreated)},
                            às ${g.formatDate(format: "HH:mm", date: receipt.dateCreated)} horas
                        </p>
                    </div>

                    <div class="headerInfo customerHeader">
                        
                        <p class="customerHeaderInfo">${receipt.payment.customer.name}</p>

                        <p class="customerHeaderInfo">${utils.Utils.formatPhone(receipt.payment.customer.phone)}</p>

                        <p class="customerHeaderInfo">${receipt.payment.customer.email}</p>
                    </div>

                </header>

                <main>
                    <div class="infoBlock separator">
                        <h2>Dados do pagamento</h2>

                        <div>
                            <p><strong>Forma de pagamento:</strong>
                                <g:message code="ENUM.BillingType.${receipt.payment.billingType}"/>
                            </p>

                            <p><strong>Valor pago:</strong>
                                R$ ${String.format("%.2f", receipt.payment.value)} reais
                            </p>

                            <p><strong>Data de vencimento:</strong>
                                ${g.formatDate(format: "dd/MM/yyyy", date: receipt.payment.dueDate)}
                            </p>

                            <p><strong>Data de pagamento:</strong>
                                ${g.formatDate(format: "dd/MM/yyyy", date: receipt.payment.paymentDate)}
                            </p>
                        </div>
                    </div>

                    <div class="infoBlock separator">
                        <h2>Dados do pagador</h2>

                        <div>
                            <p><strong>Nome:</strong> ${receipt.payment.payer.name}</p>

                            <p><strong>CPF/CNPJ:</strong> ${receipt.payment.payer.cpfCnpj}</p>
                        </div>
                    </div>

                    <div class="infoBlock">
                        <h2>Dados do beneficiário</h2>

                        <div>
                            <p><strong>Nome:</strong> ${receipt.payment.customer.name}</p>

                            <p><strong>CPF/CNPJ:</strong> ${receipt.payment.customer.cpfCnpj}</p>
                        </div>
                    </div>
                </main>

                <footer>
                    <p>
                        Este documento e cobrança não possuem valor fiscal e são de responsabilidade única e exclusiva
                        de ${receipt.payment.customer.name}
                    </p>

                    <strong>
                        Cobrança realizada via <a href="https://github.com/JezVini/quickCharge">QuickCharge ❤</a>
                    </strong>
                </footer>
            </div>
        </g:if>
        <g:else>
            <tr><td>Nenhuma cobrança encontrada</td></tr>
        </g:else>
    </body>
</html>