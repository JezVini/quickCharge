<html>
<head>
    <meta name="layout" content="main" >
    <title>Listagem de pagadores</title>
</head>
    <body>
    
        <h1>Mostrando todos pagadores</h1>
        <p><g:message code="${flash.message}"/></p>
    
        <g:if test="${!invalidCustomer}">
            <g:link action="create">
                <button>Novo pagador</button>
            </g:link>
                
            <table>
                <tr>
                    <th>Nome</th>
                    <th>E-mail</th>
                    <th>CPF/CNPJ</th>
                    <th>Telefone</th>
                    <th>Ações</th>
                </tr>
                <g:if test="${payers}">
                    <g:each in="${payers}" var="payer" >
                        <tr>
                            <td>${payer.name}</td>
                            <td>${payer.email}</td>
                            <td>${payer.cpfCnpj}</td>
                            <td>${payer.phone}</td>
                            <td>
                                <g:link action="edit" params="${[id: payer.id, customerId: customerId]}">
                                    <button>Editar</button>
                                </g:link>
                                
                                <g:link action="delete" params="${[id: payer.id, customerId: customerId]}">
                                    <button>Desativar</button>
                                </g:link>
                            </td>
                        </tr>

                    </g:each>
                </g:if>
                <g:else>
                    <tr><td>Nenhum pagador encontrado</td></tr>
                </g:else>
            </table>
        </g:if>
    </body>
</html>