<atlas-panel header="Exibindo cobranças">
    <pagination:paymentHeader
        parameterMap="${parameterMap}"/>

    <div class="pagination-table-wrap">
        <table class="pagination-table">
            <thead>
                <tr>
                    <th class="pagination-table-column-header">Data de criação</th>
                    <th class="pagination-table-column-header">Nome do cliente</th>
                    <th class="pagination-table-column-header">Valor</th>
                    <th class="pagination-table-column-header">Forma de pagamento</th>
                    <th class="pagination-table-column-header">Data de vencimento</th>
                    <th class="pagination-table-column-header">Situação</th>
                    <th class="pagination-table-column-header">Ações</th>
                </tr>
            </thead>

            <tbody>
                <g:if test="${paymentList}">
                    <g:each in="${paymentList}" var="payment">
                        <tr class="pagination-table-data-row ${payment.deleted ? 'deleted' : payment.status.toLowerCase()}">
                            <td class="pagination-table-column-data pagination-data-value">
                                <g:formatDate format="dd/MM/yyyy" date="${payment.dateCreated}"/>
                            </td>

                            <td class="pagination-table-column-data">
                                <atlas-button
                                    type="ghost" size="sm" theme="primary"
                                    tooltip="Ver cliente"
                                    description="${payment.payer.name}"
                                    href="${createLink(controller: 'payer', action: 'edit', params: [id: payment.payer.id])}">
                                </atlas-button>
                            </td>

                            <td class="pagination-table-column-data pagination-data-value">
                                <g:formatMonetaryValue value='${payment.value}'/>
                            </td>

                            <td class="pagination-table-column-data">
                                <g:message code="ENUM.BillingType.${payment.billingType}"/>
                            </td>

                            <td class="pagination-table-column-data pagination-data-value">
                                <g:formatDate format="dd/MM/yyyy" date="${payment.dueDate}"/>
                            </td>

                            <td class="pagination-table-column-data">
                                <g:message code="ENUM.PaymentStatus.${payment.status}"/>
                            </td>

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