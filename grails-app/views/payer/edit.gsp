<html>
    <head>
        <meta name="layout" content="main">
        <title>Alterar dados de Cliente</title>
        <asset:javascript src="cep/cepAutomatically.js"/>
    </head>

    <body>
        <g:message code="${flash.message}"/>
        <g:if test="${payer}">
            <atlas-form action="${createLink(controller: "payer", action: "update")}" method="post">
                <atlas-input type="hidden" name="id" value="${payer.id}"></atlas-input>

                <atlas-input
                    label="Nome"
                    name="name"
                    value="${payer.name}"
                    size="md"
                    placeholder="Digite o nome"
                    required></atlas-input>

                <atlas-masked-input
                    mask-alias="email"
                    label="E-mail"
                    name="email"
                    value="${payer.email}"
                    size="md"
                    placeholder="Digite o e-mail"
                    required></atlas-masked-input>

                <atlas-masked-input
                    mask-alias="cpf-cnpj"
                    label="CPF ou CNPJ"
                    name="cpfCnpj"
                    value="<g:formatCpfCnpj value='${payer.cpfCnpj}'/>"
                    size="md"
                    required></atlas-masked-input>

                <atlas-masked-input
                    mask-alias="phone"
                    label="Telefone ou celular"
                    name="phone"
                    value="<g:formatPhone value='${payer.phone}'/>"
                    size="md"
                    required></atlas-masked-input>

                <atlas-postal-code
                    label="CEP"
                    name="postalCode"
                    value="<g:formatCep value='${payer.postalCode}'/>"
                    size="md"
                    required
                    disable-search
                    placeholder="Digite o CEP"
                    onblur="cepSearch(this.value)"></atlas-postal-code>

                <atlas-input
                    label="Estado"
                    name="state"
                    id="state"
                    value="${payer.state}"
                    size="md"
                    maxlength="2"
                    placeholder="Digite o estado"
                    required></atlas-input>

                <atlas-input
                    label="Cidade"
                    name="city"
                    id="city"
                    value="${payer.city}"
                    size="md"
                    placeholder="Digite a cidade"
                    required></atlas-input>

                <atlas-input
                    label="Bairro"
                    name="district"
                    id="district"
                    value="${payer.district}"
                    size="md"
                    placeholder="Digite o bairro"
                    required></atlas-input>

                <atlas-input
                    label="Rua"
                    name="address"
                    id="address"
                    value="${payer.address}"
                    size="md"
                    placeholder="Digite a rua"
                    required></atlas-input>

                <atlas-input
                    label="Número"
                    name="addressNumber"
                    value="${payer.addressNumber}"
                    size="md"
                    placeholder="Digite o número"
                    required></atlas-input>

                <atlas-input
                    label="Complemento"
                    name="addressComplement"
                    value="${payer.addressComplement}"
                    size="md"
                    placeholder="Digite o complemento"></atlas-input>

                <atlas-button submit
                              size="md"
                              description="Salvar"></atlas-button>
            </atlas-form>
        </g:if>
    </body>
</html>
