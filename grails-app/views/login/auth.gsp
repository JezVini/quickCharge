<html>
    <head>
        <meta name="layout" content="external">

        <title>Fazer login</title>

        <asset:stylesheet src="login.css"/>
    </head>

    <body>
        <div class="login-container">
            <atlas-panel header="Fazer login">
                <atlas-form action="${createLink(controller: 'login', action: 'authenticate')}" method="POST">

                    <atlas-layout gap="4">

                        <atlas-masked-input
                            mask-alias="email"
                            label="E-mail"
                            name="username"
                            size="md"
                            placeholder="Digite seu e-mail"
                            required>
                        </atlas-masked-input>
                        
                        <atlas-password-input
                            label="Senha"
                            name="password"
                            size="md"
                            placeholder="Senha"
                            required>
                        </atlas-password-input>

                        <atlas-checkbox name="remember-me">Lembrar de mim</atlas-checkbox>

                        <atlas-layout gap="2">

                            <atlas-button
                                block=""
                                type="filled"
                                size="md"
                                theme="primary"
                                description="Fazer login"
                                submit>
                            </atlas-button>

                            <atlas-text class="or-text" muted>ou então</atlas-text>

                            <atlas-button
                                block=""
                                type="outlined"
                                size="md" theme="primary"
                                description="Crie uma conta"
                                href="${createLink(controller: "customer", action: "create")}">
                            </atlas-button>

                        </atlas-layout>
                    </atlas-layout>

                    <div class="spacement"></div>
                </atlas-form>
            </atlas-panel>
        </div>
    </body>
</html>