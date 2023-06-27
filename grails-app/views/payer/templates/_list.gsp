<%@ page import="utils.Utils" %>
<atlas-panel header="Exibindo clientes">
    <pagination:payerHeader
        parameterMap="${parameterMap}"/>

    <div class="table-wrap">
        <table class="table">
            <thead>
                <tr>
                    <th class="table-header">Nome do cliente</th>
                    <th class="table-header">E-mail</th>
                    <th class="table-header">CPF/CNPJ</th>
                    <th class="table-header">Telefone</th>
                    <th class="table-header">Ações</th>
                </tr>
            </thead>

            <tbody>
                <g:if test="${payerList}">
                    <g:each in="${payerList}" var="payer">
                        <tr class="table-data-row ${payer.deleted ? 'deleted' : ''}">
                            <td class="table-data">${payer.name}</td>
                            <td class="table-data">${payer.email}</td>
                            <td class="table-data value">${Utils.formatCpfCnpj(payer.cpfCnpj)}</td>
                            <td class="table-data value">${Utils.formatPhone(payer.phone)}</td>

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