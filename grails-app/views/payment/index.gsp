<html>
    <head>
        <meta name="layout" content="main">
        <title>Listagem de cobranças</title>
    </head>

    <body>
        <pagination:payment
            max="${max}"
            total="${total}"
            offset="${offset}"
            paymentList="${paymentList}"
            deletedOnly="${deletedOnly}"
            includeDeleted="${includeDeleted}"/>
    </body>
</html>