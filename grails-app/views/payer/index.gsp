<html>
    <head>
        <meta name="layout" content="main">
        <title>Listagem de clientes</title>
    </head>

    <body>
        <p><g:message code="${flash.message}"/></p>
        
        <pagination:payer
            max="${max}"
            total="${total}"
            offset="${offset}"
            payerList="${payerList}"
            deletedOnly="${deletedOnly}"
            includeDeleted="${includeDeleted}"/>
    </body>
</html>