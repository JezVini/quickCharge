<html>
<head>
    <meta name="layout" content="main" >
    <title>Todos Pagadores</title>
</head>
    <body>
    
        <h1>Mostrando todos pagadores</h1>
        <p><g:message code="${flash.message}"/></p>
    
        <g:if test="${!Boolean.valueOf(invalidCustomer)}">
            <g:link action="create">
                <button>Novo pagador</button>
            </g:link>
                
            <table>
                <tr>
                    <th>Nome</th>
                    <th>E-mail</th>
                    <th>CNPJ</th>
                    <th>Telefone</th>
                    <th>CEP</th>
                    <th>Estado</th>
                    <th>Cidade</th>
                    <th>Bairro</th>
                    <th>Número</th>
                    <th></th>
                </tr>
                <g:if test="${payers}">
                    <g:each in="${payers}" var="payer" >
                        <tr>
                            <td>${payer.name}</td>
                            <td>${payer.email}</td>
                            <td>${payer.cpfCnpj}</td>
                            <td>${payer.phone}</td>
                            <td>${payer.postalCode}</td>
                            <td>${payer.state}</td>
                            <td>${payer.city}</td>
                            <td>${payer.district}</td>
                            <td>${payer.number}</td>
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