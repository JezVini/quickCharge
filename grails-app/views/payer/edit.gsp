<html>
    <head>
        <meta name="layout" content="main">
        <title>Alterar dados de Cliente</title>
        <asset:javascript src="cep/cepAutomatically.js"/>
    </head>

    <body>
        <g:if test="${payer}">
            <atlas-panel>
                <atlas-form action="${createLink(controller: "payer", action: "update")}" method="post">
                    <atlas-input type="hidden" name="id" value="${payer.id}"></atlas-input>
                    <atlas-layout gap="8">
                        <atlas-section header="Dados pessoais">
                            <atlas-grid>
                                <atlas-row>
                                    <atlas-col lg="3">
                                        <atlas-input
                                            label="Nome"
                                            name="name"
                                            value="${payer.name}"
                                            size="md"
                                            placeholder="Digite o nome"
                                            required
                                            ${(payer.deleted) ? "disabled" : ""}></atlas-input>
                                    </atlas-col>
                                    <atlas-col lg="3">
                                        <atlas-masked-input
                                            mask-alias="email"
                                            label="E-mail"
                                            name="email"
                                            value="${payer.email}"
                                            size="md"
                                            placeholder="Digite o e-mail"
                                            required
                                            ${(payer.deleted) ? "disabled" : ""}></atlas-masked-input>
                                    </atlas-col>
                                </atlas-row>

                                <atlas-row>
                                    <atlas-col lg="2">
                                        <atlas-masked-input
                                            mask-alias="cpf-cnpj"
                                            label="CPF ou CNPJ"
                                            name="cpfCnpj"
                                            value="<g:formatCpfCnpj value='${payer.cpfCnpj}'/>"
                                            size="md"
                                            required
                                            ${(payer.deleted) ? "disabled" : ""}></atlas-masked-input>
                                    </atlas-col>
                                    <atlas-col lg="2">
                                        <atlas-masked-input
                                            mask-alias="phone"
                                            label="Telefone ou celular"
                                            name="phone"
                                            value="<g:formatPhone value='${payer.phone}'/>"
                                            size="md"
                                            required
                                            ${(payer.deleted) ? "disabled" : ""}></atlas-masked-input>
                                    </atlas-col>
                                </atlas-row>
                            </atlas-grid>
                        </atlas-section>

                        <atlas-section header="Dados de endereço">
                            <atlas-grid>
                                <atlas-row>
                                    <atlas-col lg="2">
                                        <atlas-postal-code
                                            label="CEP"
                                            name="postalCode"
                                            value="<g:formatCep value='${payer.postalCode}'/>"
                                            size="md"
                                            required
                                            ${(payer.deleted) ? "disabled" : ""}
                                            disable-search
                                            placeholder="Digite o CEP"
                                            onblur="cepSearch(this.value)"></atlas-postal-code>
                                    </atlas-col>
                                </atlas-row>

                                <atlas-row>
                                    <atlas-col lg="2">
                                        <atlas-input
                                            label="Estado"
                                            name="state"
                                            id="state"
                                            value="${payer.state}"
                                            size="md"
                                            maxlength="2"
                                            placeholder="Digite o estado"
                                            required
                                            ${(payer.deleted) ? "disabled" : ""}></atlas-input>
                                    </atlas-col>

                                    <atlas-col lg="2">
                                        <atlas-input
                                            label="Cidade"
                                            name="city"
                                            id="city"
                                            value="${payer.city}"
                                            size="md"
                                            placeholder="Digite a cidade"
                                            required
                                            ${(payer.deleted) ? "disabled" : ""}></atlas-input>
                                    </atlas-col>

                                    <atlas-col lg="2">
                                        <atlas-input
                                            label="Bairro"
                                            name="district"
                                            id="district"
                                            value="${payer.district}"
                                            size="md"
                                            placeholder="Digite o bairro"
                                            required
                                            ${(payer.deleted) ? "disabled" : ""}></atlas-input>
                                    </atlas-col>
                                </atlas-row>

                                <atlas-row>
                                    <atlas-col lg="2">
                                        <atlas-input
                                            label="Rua"
                                            name="address"
                                            id="address"
                                            value="${payer.address}"
                                            size="md"
                                            placeholder="Digite a rua"
                                            required
                                            ${(payer.deleted) ? "disabled" : ""}></atlas-input>
                                    </atlas-col>

                                    <atlas-col lg="2">
                                        <atlas-input
                                            label="Número"
                                            name="addressNumber"
                                            value="${payer.addressNumber}"
                                            size="md"
                                            placeholder="Digite o número"
                                            required
                                            ${(payer.deleted) ? "disabled" : ""}></atlas-input>
                                    </atlas-col>

                                    <atlas-col lg="3">
                                        <atlas-input
                                            label="Complemento"
                                            name="addressComplement"
                                            value="${payer.addressComplement}"
                                            size="md"
                                            placeholder="Digite o complemento"
                                            ${(payer.deleted) ? "disabled" : ""}></atlas-input>
                                    </atlas-col>
                                </atlas-row>
                                <atlas-button
                                    submit
                                    size="md"
                                    description="Salvar"
                                    ${(payer.deleted) ? ("disabled") : ""}></atlas-button>
                            </atlas-grid>
                        </atlas-section>
                    </atlas-layout>
                </atlas-form>
            </atlas-panel>
        </g:if>
    </body>
</html>
