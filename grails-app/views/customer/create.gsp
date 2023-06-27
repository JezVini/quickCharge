<html>
    <head>
        <meta name="layout" content="external">

        <title>Criar conta</title>

        <asset:stylesheet src="register.css"/>
        <asset:javascript src="cep/cepAutomatically.js"/>
    </head>

    <body>

        <div class="content-container">
            <atlas-button
                type="filled"
                size="md"
                theme="primary"
                tooltip="Voltar ao início"
                icon="chevron-left"
                href="${createLink(controller: "login", action: "auth")}">
            </atlas-button>

            <div class="form-container">
                <atlas-panel header="Criar conta">
                    <atlas-form action="${createLink(controller: "customer", action: "save")}" method="post">
                        <atlas-layout gap="4">
                            <atlas-section header="Dados da conta">
                                <atlas-grid>
                                    <atlas-row>
                                        <atlas-col lg="6">
                                            <atlas-input
                                                label="Nome de usuário"
                                                name="name"
                                                value="${name}"
                                                size="md"
                                                placeholder="Digite seu nome"
                                                required>
                                            </atlas-input>
                                        </atlas-col>

                                        <atlas-col lg="6">
                                            <atlas-masked-input
                                                mask-alias="email"
                                                label="E-mail"
                                                name="email"
                                                value="${email}"
                                                size="md"
                                                placeholder="Digite seu e-mail"
                                                required>
                                            </atlas-masked-input>
                                        </atlas-col>

                                        <atlas-col>
                                            <atlas-password-input
                                                label="Senha"
                                                name="password"
                                                size="md"
                                                placeholder="Digite sua senha"
                                                required>
                                            </atlas-password-input>
                                        </atlas-col>
                                    </atlas-row>
                                </atlas-grid>
                            </atlas-section>

                            <atlas-section header="Dados pessoais">
                                <atlas-grid>

                                    <atlas-row>

                                        <atlas-col lg="4">
                                            <atlas-masked-input
                                                mask-alias="cpf-cnpj"
                                                label="CPF ou CNPJ"
                                                name="cpfCnpj"
                                                value="${cpfCnpj}"
                                                size="md"
                                                required>
                                            </atlas-masked-input>
                                        </atlas-col>

                                        <atlas-col lg="4">
                                            <atlas-masked-input
                                                mask-alias="phone"
                                                label="Telefone ou celular"
                                                name="phone"
                                                value="${phone}"
                                                size="md"
                                                required>
                                            </atlas-masked-input>
                                        </atlas-col>

                                        <atlas-col lg="4">
                                            <atlas-postal-code
                                                label="CEP"
                                                name="postalCode"
                                                value="${postalCode}"
                                                size="md"
                                                required
                                                disable-search
                                                placeholder="Digite o CEP"
                                                onblur="cepSearch(this.value)">
                                            </atlas-postal-code>
                                        </atlas-col>
                                    </atlas-row>


                                    <atlas-row>
                                        <atlas-col lg="4">
                                            <atlas-input
                                                label="Estado"
                                                name="state"
                                                id="state"
                                                value="${state}"
                                                size="md"
                                                maxlength="2"
                                                placeholder="Digite seu estado"
                                                required>
                                            </atlas-input>
                                        </atlas-col>

                                        <atlas-col lg="4">
                                            <atlas-input
                                                label="Cidade"
                                                name="city"
                                                id="city"
                                                value="${city}"
                                                size="md"
                                                placeholder="Digite sua cidade"
                                                required>
                                            </atlas-input>
                                        </atlas-col>

                                        <atlas-col lg="4">
                                            <atlas-input
                                                label="Bairro"
                                                name="district"
                                                id="district"
                                                value="${district}"
                                                size="md"
                                                placeholder="Digite seu bairro"
                                                required>
                                            </atlas-input>
                                        </atlas-col>
                                    </atlas-row>


                                    <atlas-row>
                                        <atlas-col lg="6">
                                            <atlas-input
                                                label="Rua"
                                                name="address"
                                                id="address"
                                                value="${address}"
                                                size="md"
                                                placeholder="Digite a rua"
                                                required>
                                            </atlas-input>
                                        </atlas-col>
                                        <atlas-col lg="6">
                                            <atlas-input
                                                label="Número"
                                                name="addressNumber"
                                                value="${addressNumber}"
                                                size="md"
                                                placeholder="Digite seu número de endereço"
                                                required>
                                            </atlas-input>
                                        </atlas-col>
                                    </atlas-row>

                                    <atlas-input
                                        label="Complemento"
                                        name="addressComplement"
                                        value="${addressComplement}"
                                        size="md"
                                        placeholder="Complemento">
                                    </atlas-input>

                                    <atlas-button
                                        submit
                                        size="md"
                                        block
                                        description="Criar conta">
                                    </atlas-button>

                                </atlas-grid>
                            </atlas-section>
                        </atlas-layout>
                    </atlas-form>
                </atlas-panel>
            </div>
        </div>
    </body>
</html>
