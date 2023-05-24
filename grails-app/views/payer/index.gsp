<html>
<head>
    <meta name="layout" content="main" >
    <title>Registrar Pagador</title>
</head>
    <body>
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
                    </tr>
                </g:each>
            </g:if>
            <g:else>
                <td>Nenhum pagador encontrado...</td>
            </g:else>
            
        </table>
    
        <button>Novo pagador</button>
    
    </body>
</html>