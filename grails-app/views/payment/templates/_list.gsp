<atlas-panel header="Exibindo cobranças">
    <pagination:paymentHeader
        parameterMap="${parameterMap}"/>
    
    <div class="table-wrap">
        <table class="table">
            <thead>
                <tr>
                    <th class="table-header">Data de criação</th>
                    <th class="table-header">Nome do cliente</th>
                    <th class="table-header">Valor</th>
                    <th class="table-header">Forma de pagamento</th>
                    <th class="table-header">Data de vencimento</th>
                    <th class="table-header">Situação</th>
                    <th class="table-header">Ações</th>
                </tr>
            </thead>

            <tbody>
                <g:if test="${paymentList}">
                    <g:each in="${paymentList}" var="payment">
                        <tr class="table-data-row ${payment.deleted ? 'deleted' : payment.status.toLowerCase()}">
                            <td class="table-data value"><g:formatDate format="dd/MM/yyyy"
                                                                       date="${payment.dateCreated}"/></td>

                            <td class="table-data">
                                <atlas-button type="ghost" size="sm" theme="primary"
                                              tooltip="Ver cliente"
                                              description="${payment.payer.name}"
                                              href="${createLink(controller: 'payer', action: 'edit', params: [id: payment.payer.id])}">
                                </atlas-button>
                            </td>

                            <td class="table-data value"><g:formatMonetaryValue value='${payment.value}'/></td>
                            <td class="table-data"><g:message code="ENUM.BillingType.${payment.billingType}"/></td>
                            <td class="table-data value"><g:formatDate format="dd/MM/yyyy"
                                                                       date="${payment.dueDate}"/></td>
                            <td class="table-data"><g:message code="ENUM.PaymentStatus.${payment.status}"/></td>

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