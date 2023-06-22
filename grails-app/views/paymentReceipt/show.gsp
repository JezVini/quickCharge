<html>
    <head>
        <title>Comprovante de Pagamento</title>
    </head>

    <body>
        <g:if test="${receipt}">
            <header>
                <div>
                    <p>Nome do beneficiário: ${receipt.payment.payer.name}</p>

                    <p>Telefone do beneficiário: ${receipt.payment.payer.phone}</p>
                </div>

                <div>
                    <h1>Comprovante de pagamento</h1>

                    <p>
                        Gerado dia ${g.formatDate(format: "dd/MM/yyyy", date: receipt.dateCreated)},
                        às ${g.formatDate(format: "HH:mm", date: receipt.dateCreated)} horas
                    </p>
                </div>
            </header>

            <main>
                <div>
                    <h2>Dados do recibo</h2>

                    <div>
                        <p>Forma de pagamento: <g:message code="ENUM.BillingType.${receipt.payment.billingType}"/></p>
                        
                        <p>Valor pago: R$ ${String.format("%.2f", receipt.payment.value)} reais</p>

                        <p>Data de vencimento: ${g.formatDate(format: "dd/MM/yyyy", date: receipt.payment.dueDate)}</p>

                        <p>Data de pagamento: ${g.formatDate(format: "dd/MM/yyyy", date: receipt.payment.paymentDate)}</p>
                    </div>
                </div>

                <div>
                    <h2>Dados do pagador</h2>

                    <div>
                        <p>Nome: ${receipt.payment.payer.name}</p>

                        <p>CPF/CNPJ: ${receipt.payment.payer.cpfCnpj}</p>
                    </div>
                </div>

                <div>
                    <h2>Dados do beneficiário</h2>

                    <div>
                        <p>Nome: ${receipt.payment.customer.name}</p>

                        <p>CPF/CNPJ: ${receipt.payment.customer.cpfCnpj}</p>
                    </div>
                </div>
            </main>

            <footer>
                <p>Este documento e cobrança não possuem valor fiscal e são de responsabilidade única e exclusiva de ${receipt.payment.customer.name}</p>

                <p>Cobrança realizada via QuickCharge</p>
            </footer>
        </g:if>
        <g:else>
            <tr><td>Nenhuma cobrança encontrada</td></tr>
        </g:else>
    </body>
</html>