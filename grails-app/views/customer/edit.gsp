<html>
<head>
    <meta name="layout" content="main">
    <title>Alterar conta</title>
    <asset:javascript src="cep/cepAutomatically.js"/>
</head>

<body>
    <g:message code="${flash.message}"/>
    <form action="${createLink(controller: "customer", action: "update" )}" method="post">
        <input type="hidden" name="id" value="${customer.id}">
        <label for="name">Nome</label>

        <input type="text" name="name" id="name" placeholder="Digite seu nome" value="${customer.name}">
        <br>
    
        <label for="cpfCnpj">CPF ou CNPJ</label>
        <input type="text" name="cpfCnpj" id="cpfCnpj" placeholder="Digite o CPF ou CNPJ" value="${customer.cpfCnpj}">
        <br>

        <label for="phone">Telefone</label>
        <input type="tel" name="phone" id="phone" placeholder="Digite o número de telefone/celular" value="${customer.phone}">
        <br>

        <label for="postalCode">CEP</label>
        <input type="text" name="postalCode" id="postalCode" onblur="cepSearch(this.value)" placeholder="Digite o CEP" value="${customer.postalCode}">
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

        <label for="address">Rua</label>
        <input type="text" name="address" id="address" placeholder="Digite a rua" value="${customer.address}">
        <br>
    
        <label for="addressNumber">Número</label>
        <input type="text" name="addressNumber" id="addressNumber" placeholder="Digite o número" value="${customer.addressNumber}">
        <br>

        <label for="addressComplement">Complemento</label>
        <input type="text" name="addressComplement" id="addressComplement" placeholder="Digite o complemento" value="${customer.addressComplement}">
        <br>

        <button type="submit">Salvar</button>
    </form>
</body>
</html>
