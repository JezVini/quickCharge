<html>
    <head>
        <meta name="layout" content="main">
        <title>Alterar conta</title>
        <asset:javascript src="cep/cepAutomatically.js"/>
    </head>

    <body>
        <atlas-panel>
            <atlas-form action="${createLink(controller: "customer", action: "update")}" method="post">
                <atlas-input type="hidden" name="id" value="${customer.id}"></atlas-input>
                <atlas-layout gap="8">
                    <atlas-section header="Dados pessoais">
                        <atlas-grid>
                            <atlas-row>
                                <atlas-col lg="6">
                                    <atlas-input
                                        label="Nome"
                                        name="name"
                                        value="${customer.name}"
                                        size="md"
                                        placeholder="Digite o nome"
                                        required></atlas-input>
                                </atlas-col>

                                <atlas-col lg="6">
                                    <atlas-masked-input
                                        mask-alias="phone"
                                        label="Telefone ou celular"
                                        name="phone"
                                        value="<g:formatPhone value='${customer.phone}'/>"
                                        size="md"
                                        required></atlas-masked-input>
                                </atlas-col>
                            </atlas-row>

                            <atlas-row>
                                <atlas-col lg="6">
                                    <atlas-masked-input
                                        mask-alias="cpf-cnpj"
                                        label="CPF ou CNPJ"
                                        name="cpfCnpj"
                                        value="<g:formatCpfCnpj value='${customer.cpfCnpj}'/>"
                                        size="md"
                                        required></atlas-masked-input>
                                </atlas-col>
                            </atlas-row>
                        </atlas-grid>
                    </atlas-section>

                    <atlas-section header="Dados de endereço">
                        <atlas-grid>
                            <atlas-row>
                                <atlas-col lg="4">
                                    <atlas-postal-code
                                        label="CEP"
                                        name="postalCode"
                                        value="<g:formatCep value='${customer.postalCode}'/>"
                                        size="md"
                                        required
                                        disable-search
                                        placeholder="Digite o CEP"
                                        onblur="cepSearch(this.value)"></atlas-postal-code>
                                </atlas-col>

                                <atlas-col lg="4">
                                    <atlas-input
                                        label="Estado"
                                        name="state"
                                        id="state"
                                        value="${customer.state}"
                                        size="md"
                                        maxlength="2"
                                        placeholder="Digite o estado"
                                        required></atlas-input>
                                </atlas-col>

                                <atlas-col lg="4">
                                    <atlas-input
                                        label="Cidade"
                                        name="city"
                                        id="city"
                                        value="${customer.city}"
                                        size="md"
                                        placeholder="Digite a cidade"
                                        required></atlas-input>
                                </atlas-col>
                            </atlas-row>

                            <atlas-row>
                                <atlas-col lg="4">
                                    <atlas-input
                                        label="Bairro"
                                        name="district"
                                        id="district"
                                        value="${customer.district}"
                                        size="md"
                                        placeholder="Digite o bairro"
                                        required></atlas-input>
                                </atlas-col>

                                <atlas-col lg="4">
                                    <atlas-input
                                        label="Rua"
                                        name="address"
                                        id="address"
                                        value="${customer.address}"
                                        size="md"
                                        placeholder="Digite a rua"
                                        required></atlas-input>
                                </atlas-col>

                                <atlas-col lg="4">
                                    <atlas-input
                                        label="Número"
                                        name="addressNumber"
                                        value="${customer.addressNumber}"
                                        size="md"
                                        placeholder="Digite o número"
                                        required></atlas-input>
                                </atlas-col>
                            </atlas-row>
                            <atlas-input
                                label="Complemento"
                                name="addressComplement"
                                value="${customer.addressComplement}"
                                size="md"
                                placeholder="Digite o complemento"></atlas-input>

                            <atlas-button submit
                                          size="md"
                                          description="Salvar"
                                          block></atlas-button>
                        </atlas-grid>
                    </atlas-section>
                </atlas-layout>
            </atlas-form>
        </atlas-panel>
    </body>
</html>
