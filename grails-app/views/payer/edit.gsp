<html>
<head>
    <meta name="layout" content="main">
    <title>Alterar dados de Pagador</title>
</head>
<body>
    <g:message code="${flash.message}"/>
    <g:if test="${payer}">
        <form action="${createLink(controller: "payer", action: "update" )}" method="post">

            <input type="hidden" name="id" value="${id}">

            <input type="hidden" name="customerId" value="${customerId}">

            <label for="name">Nome</label>
            <input type="text" name="name" id="name" placeholder="Digite seu nome" value="${payer.name}">
            <br>

            <label for="email">E-mail</label>
            <input type="email" name="email" id="email" placeholder="Digite seu e-mail" value="${payer.email}">
            <br>

            <label for="cpfCnpj">CPF ou CNPJ</label>
            <input type="text" name="cpfCnpj" id="cpfCnpj" placeholder="Digite o CPF ou CNPJ" value="${payer.cpfCnpj}">
            <br>

            <label for="phone">Telefone</label>
            <input type="tel" name="phone" id="phone" placeholder="Digite o número de telefone/celular" value="${payer.phone}">
            <br>

            <label for="postalCode">CEP</label>
            <input type="text" name="postalCode" id="postalCode" placeholder="Digite o CEP" value="${payer.postalCode}">
            <br>

            <label for="state">Estado</label>
            <input type="text" name="state" id="state" placeholder="Digite seu estado" value="${payer.state}">
            <br>

            <label for="city">Cidade</label>
            <input type="text" name="city" id="city" placeholder="Digite sua cidade" value="${payer.city}">
            <br>

            <label for="district">Bairro</label>
            <input type="text" name="district" id="district" placeholder="Digite o distrito" value="${payer.district}">
            <br>

            <label for="number">Número</label>
            <input type="text" name="number" id="number" placeholder="Digite o número" value="${payer.number}">
            <br>

            <button type="submit">Salvar</button>

        </form>
    </g:if>
</body>
</html>
