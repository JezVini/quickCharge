<!DOCTYPE html>
<html>
<head>
    <title>Clientes</title>
    <meta name="layout" content="main">
</head>

<body>
    <g:each in="${customerList}" var="customer">
        <div>
            <ul>
                <li>${customer.name}</li>
                <li>${customer.email}</li>
            </ul>
        </div>
        <br/>
    </g:each>
</body>
</html>
