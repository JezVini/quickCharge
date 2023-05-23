<html>
<head>
    <meta name="layout" content="main" >
    <title>Registrar Pagador</title>
</head>
<body>

    <g:message code="${flash.message}"/>
    <form action="${createLink(controller: "payer", action: "save")}" method="post">

        <label for="name">Customer Id</label>
        <input type="number"  name="customer" id="customer" value="1">
        <br>
        <label for="name">Nome</label>
        <input type="text"  name="name" id="name" value="${name}" placeholder="Digite seu nome">
        <br>
        <label for="email">E-mail</label>
        <input type="email" name="email" id="email" value="${email}" placeholder="Digite seu e-mail">
        <br>
        <label for="cpfCnpj">CPF ou CNPJ</label>
        <input type="text"  name="cpfCnpj" id="cpfCnpj" value="${cpfCnpj}" placeholder="Digite o CPF ou CNPJ">
        <br>
        <label for="phone">Telefone</label>
        <input type="tel" name="phone" id="phone" value="${phone}" placeholder="Digite o número de telefone/celular">
        <br>
        <label for="postalCode">CEP</label>
        <input type="text" name="postalCode" id="postalCode" value="${postalCode}" placeholder="Digite o CEP">
        <br>
        <label for="state">Estado</label>
        <input type="text" name="state" id="state" value="${state}" placeholder="Digite seu estado">
        <br>
        <label for="city">Cidade</label>
        <input type="text" name="city" id="city" value="${city}" placeholder="Digite sua cidade">
        <br>
        <label for="district">Bairro</label>
        <input type="text" name="district" id="district" value="${district}" placeholder="Digite o distrito">
        <br>
        <label for="number">Número</label>
        <input type="text" name="number" id="number" value="${number}" placeholder="Digite o número">
        <br>
        <button type="submit">Criar</button>

    </form>

</body>
</html>
