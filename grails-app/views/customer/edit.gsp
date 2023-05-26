<html>
<head>
    <meta name="layout" content="main">
    <title>Alterar conta</title>
</head>

<body>
    <g:message code="${flash.message}"/>
    <form action="${createLink(controller: "customer", action: "update" )}" method="post">
        <input type="hidden" name="id" value="${customer.id}">
        <label for="name">Nome</label>

        <input type="text" name="name" id="name" placeholder="Digite seu nome" value="${customer.name}">
        <br>

        <label for="email">E-mail</label>
        <input type="email" name="email" id="email" placeholder="Digite seu e-mail" value="${customer.email}">
        <br>

        <label for="cpfCnpj">CPF ou CNPJ</label>
        <input type="text" name="cpfCnpj" id="cpfCnpj" placeholder="Digite o CPF ou CNPJ" value="${customer.cpfCnpj}">
        <br>

        <label for="phone">Telefone</label>
        <input type="tel" name="phone" id="phone" placeholder="Digite o número de telefone/celular" value="${customer.phone}">
        <br>

        <label for="postalCode">CEP</label>
        <input type="text" name="postalCode" id="postalCode" placeholder="Digite o CEP" value="${customer.postalCode}">
        <br>

        <label for="state">Estado</label>
        <input type="text" name="state" id="state" placeholder="Digite seu estado" value="${customer.state}">
        <br>

        <label for="city">Cidade</label>
        <input type="text" name="city" id="city" placeholder="Digite sua cidade" value="${customer.city}">
        <br>

        <label for="district">Bairro</label>
        <input type="text" name="district" id="district" placeholder="Digite o distrito" value="${customer.district}">
        <br>

        <label for="street">Rua</label>
        <input type="text" name="street" id="street" placeholder="Digite a rua" value="${customer.street}">
        <br>
    
        <label for="number">Número</label>
        <input type="text" name="number" id="number" placeholder="Digite o número" value="${customer.number}">
        <br>

        <label for="addressComplement">Complemento</label>
        <input type="text" name="addressComplement" id="addressComplement" placeholder="Digite o complemento" value="${customer.addressComplement}">
        <br>

        <button type="submit">Salvar</button>
    </form>
</body>
</html>
