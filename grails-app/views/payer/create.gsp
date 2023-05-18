<html>
<head>
    <meta name="layout" content="main" >
    <title>Document</title>
</head>
<body>

    <form action="${createLink(controller: "payer", action: "save")}">

        <label for="name">Nome</label>
        <input type="text" name="name" value="${name}" id="name" placeholder="Digite seu nome">
        <br>
        <label for="email">E-mail</label>
        <input type="email" name="email" id="email" placeholder="Digite seu e-mail">
        <br>
        <label for="cpfCnpj">CPF ou CNPJ</label>
        <input type="text" name="cpfCnpj" id="cpfCnpj" placeholder="Digite o CPF ou CNPJ">
        <br>
        <label for="phone">Telefone</label>
        <input type="tel" name="phone" id="phone" placeholder="Digite o número de telefone/celular">
        <br>
        <label for="postalCode">CEP</label>
        <input type="text" name="postalCode" id="postalCode" placeholder="Digite o CEP">
        <br>
        <label for="state">Estado</label>
        <input type="text" name="state" id="state" placeholder="Digite seu estado">
        <br>
        <label for="city">Cidade</label>
        <input type="text" name="city" id="city" placeholder="Digite sua cidade">
        <br>
        <label for="district">Distrito</label>
        <input type="text" name="district" id="district" placeholder="Digite o distrito">
        <br>
        <label for="number">Número</label>
        <input type="text" name="number" id="number" placeholder="Digite o número">
        <br>
        <button type="submit">Criar</button>

    </form>

</body>
</html>