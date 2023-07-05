<html>
    <head>
        <meta name="layout" content="main">
        <title>Cadastrar Cliente</title>
        <asset:javascript src="cep/cepAutomatically.js"/>
    </head>

    <body>
        <atlas-panel>
            <atlas-form action="${createLink(controller: "payer", action: "save")}" method="post">
                <atlas-layout gap="8">
                    <atlas-section header="Dados pessoais">
                        <atlas-grid>
                            <atlas-row>
                                <atlas-col lg="3">
                                    <atlas-input
                                        label="Nome"
                                        name="name"
                                        value="${name}"
                                        size="md"
                                        placeholder="Digite o nome"
                                        required></atlas-input>
                                </atlas-col>
                                <atlas-col lg="3">
                                    <atlas-masked-input
                                        mask-alias="email"
                                        label="E-mail"
                                        name="email"
                                        value="${email}"
                                        size="md"
                                        placeholder="Digite o e-mail"
                                        required></atlas-masked-input>
                                </atlas-col>
                            </atlas-row>

                            <atlas-row>
                                <atlas-col lg="2">
                                    <atlas-masked-input
                                        mask-alias="cpf-cnpj"
                                        label="CPF ou CNPJ"
                                        name="cpfCnpj"
                                        value="${cpfCnpj}"
                                        size="md"
                                        required></atlas-masked-input>
                                </atlas-col>
                                <atlas-col lg="2">
                                    <atlas-masked-input
                                        mask-alias="phone"
                                        label="Telefone ou celular"
                                        name="phone"
                                        value="${phone}"
                                        size="md"
                                        required></atlas-masked-input>
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
                                        value="${postalCode}"
                                        size="md"
                                        required
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
                                        value="${state}"
                                        size="md"
                                        maxlength="2"
                                        placeholder="Digite o estado"
                                        required></atlas-input>
                                </atlas-col>

                                <atlas-col lg="2">
                                    <atlas-input
                                        label="Cidade"
                                        name="city"
                                        id="city"
                                        value="${city}"
                                        size="md"
                                        placeholder="Digite a cidade"
                                        required></atlas-input>
                                </atlas-col>

                                <atlas-col lg="2">
                                    <atlas-input
                                        label="Bairro"
                                        name="district"
                                        id="district"
                                        value="${district}"
                                        size="md"
                                        placeholder="Digite o bairro"
                                        required></atlas-input>
                                </atlas-col>
                            </atlas-row>
                            
                            <atlas-row>
                                <atlas-col lg="2">
                                    <atlas-input
                                        label="Rua"
                                        name="address"
                                        id="address"
                                        value="${address}"
                                        size="md"
                                        placeholder="Digite a rua"
                                        required></atlas-input>
                                </atlas-col>
                                
                                <atlas-col lg="2">                                    
                                    <atlas-input
                                        label="Número"
                                        name="addressNumber"
                                        value="${addressNumber}"
                                        size="md"
                                        placeholder="Digite o número"
                                        required></atlas-input>
                                </atlas-col>

                                <atlas-col lg="3">
                                    <atlas-input
                                        label="Complemento"
                                        name="addressComplement"
                                        value="${addressComplement}"
                                        size="md"
                                        placeholder="Digite o complemento"></atlas-input>
                                </atlas-col>
                            </atlas-row>
                            <atlas-button submit
                                          size="md"
                                          description="Criar"></atlas-button>
                        </atlas-grid>
                    </atlas-section>
                </atlas-layout>
            </atlas-form>
        </atlas-panel>
    </body>
</html>