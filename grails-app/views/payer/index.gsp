<html>
<head>
    <meta name="layout" content="main" >
    <title>Todos Pagadores</title>
</head>
    <body>
    
        <h1>Mostrando todos pagadores</h1>
        <button> + Novo pagador</button>
    
        <table>
            <tr>
                <th>Nome</th>
                <th>mail</th>
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
                    <tr id="${payer.id}">
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
                            <button>Editar</button>
                            <button>Desativar</button>
                        </td>
                    </tr>
                </g:each>
            </g:if>
            <g:else>
                <td><g:message code="${flash.message}"/></td>
            </g:else>
            
        </table>
    
    </body>
</html>