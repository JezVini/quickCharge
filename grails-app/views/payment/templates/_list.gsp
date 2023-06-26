<atlas-panel header="Exibindo cobranças">
    <pagination:paymentHeader
        parameterMap="${parameterMap}"/>
    
    <div class="list-wrap">
        <table class="list">
            <thead>
                <tr>
                    <th>Data de criação</th>
                    <th>Nome do cliente</th>
                    <th>Valor</th>
                    <th>Forma de pagamento</th>
                    <th>Data de vencimento</th>
                    <th>Situação</th>
                    <th>Ações</th>
                </tr>
            </thead>

            <tbody>
                <g:if test="${paymentList}">
                    <g:each in="${paymentList}" var="payment">
                        <tr class="${payment.deleted ? 'deleted' : ''}">
                            <td class="value"><g:formatDate format="dd/MM/yyyy"
                                                            date="${payment.dateCreated}"/></td>

                            <td>
                                <atlas-button type="ghost" size="sm" theme="primary"
                                              tooltip="Ver cliente"
                                              description="${payment.payer.name}"
                                              href="${createLink(controller: 'payer', action: 'edit', params: [id: payment.payer.id])}">
                                </atlas-button>
                            </td>

                            <td class="value">R$ ${String.format("%,.2f", payment.value)}</td>
                            <td><g:message code="ENUM.BillingType.${payment.billingType}"/></td>
                            <td class="value"><g:formatDate format="dd/MM/yyyy"
                                                            date="${payment.dueDate}"/></td>
                            <td><g:message code="ENUM.PaymentStatus.${payment.status}"/></td>

                            <pagination:paymentActions
                                payment="${payment}"
                                parameterMap="${parameterMap}"/>
                        </tr>
                    </g:each>
                </g:if>
            </tbody>
        </table>
    </div>

    <pagination:footer
        total="${total}"
        action="index"
        controller="payment"
        parameterMap="${parameterMap}"/>
</atlas-panel>