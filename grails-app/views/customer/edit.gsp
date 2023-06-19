<html>
    <head>
        <meta name="layout" content="main">
        <title>Alterar conta</title>
        <asset:javascript src="cep/cepAutomatically.js"/>
    </head>

    <body>
        <g:message code="${flash.message}"/>
        <atlas-form action="${createLink(controller: "customer", action: "update")}" method="post">
            <atlas-input type="hidden" name="id" value="${id}"></atlas-input>

            <atlas-input
                label="Nome"
                name="name"
                value="${customer.name}"
                size="md"
                placeholder="Digite o nome"
                required></atlas-input>

            <atlas-masked-input
                mask-alias="cpf-cnpj"
                label="CPF ou CNPJ"
                name="cpfCnpj"
                value="${customer.cpfCnpj}"
                size="md"
                required></atlas-masked-input>

            <atlas-masked-input
                mask-alias="phone"
                label="Telefone ou celular"
                name="phone"
                value="${customer.phone}"
                size="md"
                required></atlas-masked-input>

            <atlas-postal-code
                label="CEP"
                name="postalCode"
                value="${customer.postalCode}"
                size="md"
                required
                disable-search
                placeholder="Digite o CEP"
                onblur="cepSearch(this.value)"></atlas-postal-code>

            <atlas-input
                label="Estado"
                name="state"
                id="state"
                value="${customer.state}"
                size="md"
                maxlength="2"
                placeholder="Digite o estado"
                required></atlas-input>

            <atlas-input
                label="Cidade"
                name="city"
                id="city"
                value="${customer.city}"
                size="md"
                placeholder="Digite a cidade"
                required></atlas-input>

            <atlas-input
                label="Bairro"
                name="district"
                id="district"
                value="${customer.district}"
                size="md"
                placeholder="Digite o bairro"
                required></atlas-input>

            <atlas-input
                label="Rua"
                name="address"
                id="address"
                value="${customer.address}"
                size="md"
                placeholder="Digite a rua"
                required></atlas-input>

            <atlas-input
                label="Número"
                name="addressNumber"
                value="${customer.addressNumber}"
                size="md"
                placeholder="Digite o número"
                required></atlas-input>

            <atlas-input
                label="Complemento"
                name="addressComplement"
                value="${customer.addressComplement}"
                size="md"
                placeholder="Digite o complemento"
                required></atlas-input>

            <atlas-button submit
                          size="md"
                          description="Salvar"></atlas-button>
        </atlas-form>
    </body>
</html>
