<%@ page import="utils.Utils" %>
<atlas-panel header="Exibindo clientes">
    <pagination:payerHeader
        parameterMap="${parameterMap}"/>

    <div class="pagination-table-wrap">
        <table class="pagination-table">
            <thead>
                <tr>
                    <th class="pagination-table-column-header">Nome do cliente</th>
                    <th class="pagination-table-column-header">E-mail</th>
                    <th class="pagination-table-column-header">CPF/CNPJ</th>
                    <th class="pagination-table-column-header">Telefone</th>
                    <th class="pagination-table-column-header">Ações</th>
                </tr>
            </thead>

            <tbody>
                <g:if test="${payerList}">
                    <g:each in="${payerList}" var="payer">
                        <tr class="pagination-table-data-row ${payer.deleted ? 'deleted' : ''}">
                            <td class="pagination-table-column-data">${payer.name}</td>
                            <td class="pagination-table-column-data">${payer.email}</td>
                            <td class="pagination-table-column-data pagination-data-value"><g:formatCpfCnpj value='${payer.cpfCnpj}'/></td>
                            <td class="pagination-table-column-data pagination-data-value"><g:formatPhone value='${payer.phone}'/></td>

                            <pagination:payerActions
                                payer="${payer}"
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
        controller="payer"
        parameterMap="${parameterMap}"/>
</atlas-panel>