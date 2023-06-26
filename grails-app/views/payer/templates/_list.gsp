<atlas-panel header="Exibindo clientes">
    <pagination:payerHeader
        parameterMap="${parameterMap}"/>

    <div class="list-wrap">
        <table class="list">
            <thead>
                <tr>
                    <th>Nome do cliente</th>
                    <th>E-mail</th>
                    <th>CPF/CNPJ</th>
                    <th>Telefone</th>
                    <th>Ações</th>
                </tr>
            </thead>

            <tbody>
                <g:if test="${payerList}">
                    <g:each in="${payerList}" var="payer">
                        <tr class="${payer.deleted ? 'deleted' : ''}">
                            <td>${payer.name}</td>
                            <td>${payer.email}</td>
                            <td class="value">${payer.cpfCnpj}</td>
                            <td class="value">${payer.phone}</td>

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