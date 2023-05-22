<!DOCTYPE html>
<html>
<head>
    <title>Clientes</title>
    <meta name="layout" content="main">
</head>

<body>
    <table>
        <tbody>
        <g:if test="${customerCount > 0}">
            <g:each in="${customerList}" var="customer">
                <tr>
                    <td>${customer.name}</td>
                    <td>${customer.email}</td>
                    <td>${customer.cpfCnpj}</td>
                    <td>${customer.phone}</td>
                    <td>${customer.state}</td>
                    <td>${customer.city}</td>
                    <td>${customer.postalCode}</td>
                    <td><button>Editar</button></td>
                </tr>
            </g:each>
        </g:if>
        <g:else>
            <g:link action="create">Registre-se</g:link>
        </g:else>

        </tbody>
    </table>
</body>
</html>
