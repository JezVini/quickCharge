<!DOCTYPE html>
<html>
<head>
    <title>Clientes</title>
    <meta name="layout" content="main">
</head>

<body>
    <table>
        <tbody>
            <g:each in="${customerList}" var="customer">
                <tr>
                    <td>${customer.name}</td>
                    <td>${customer.email}</td>
                    <td>${customer.cpfCnpj}</td>
                    <td>${customer.phone}</td>
                    <td>${customer.state}</td>
                    <td>${customer.city}</td>
                    <td>${customer.postalCode}</td>
                </tr>
            </g:each>
        </tbody>
    </table>
</body>
</html>
