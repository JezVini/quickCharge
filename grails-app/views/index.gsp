<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport"
              content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
        <meta http-equiv="X-UA-Compatible" content="ie=edge">

        <title>QuickCharge</title>
        <link rel="stylesheet" href="https://atlas.asaas.com/v9.2.0/atlas.css"
              integrity="sha384-YmA7EnYqhHBpt32Gq7QBxbKy8jWbw5qIYu8f++Wl+PpqKTRmK+Pw+VKItsPpmLJI"
              crossorigin="anonymous">
        <script defer src="https://atlas.asaas.com/v9.2.0/atlas.js"
                integrity="sha384-9ousY7CNvMeUL2TT4DpJcImS2zvyMrsHUywHo++f3XItWZHc+c/6u7pyFqIEp/Jx"
                crossorigin="anonymous"></script>

        <asset:stylesheet src="atlas-colors/colors.css"/>
        <asset:link rel="icon" href="logo/quickCharge.ico" type="image/x-ico"/>
        <asset:stylesheet src="main/main.css.css"/>
        <asset:stylesheet src="landingpage/landingpage.css"/>

        <style>
        .background-image {
            background-image: url("${asset.assetPath(src: '/landingpage/background.jpg')}");
        }
        </style>
    </head>

    <body class="body">
        <header>
            <nav class="navbar">
                <div class="left-navbar">
                    <img class="quickcharge-logo" src="${asset.assetPath(src: '/logo/quickChargeNormalWhite.svg')}"
                         alt="Logo QuickCharge">

                    <div>
                        <ul class="navbar-option">
                            <li class="navbar-li"><a class="navbar-link" href="#about-us">Sobre</a></li>
                            <li class="navbar-li"><a class="navbar-link" href="#services-tab">Serviços</a></li>
                            <li class="navbar-li"><a class="navbar-link" href="#contacts">Contatos</a></li>
                        </ul>
                    </div>
                </div>

                <g:if>
                    <div class="account-links">
                        <g:link class="account-link" controller="login" action="auth">Acessar minha conta</g:link>
                        <g:link class="account-link create" controller="customer"
                                action="create">Criar minha conta</g:link>
                    </div>
                </g:if>
                <g:else>
                    <g:link class="account-link" controller="login" action="auth">Acessar o sistema</g:link>
                </g:else>
            </nav>

            <div class="background-image"></div>
        </header>

        <main class="main-content">

            <div id="about-us" class="about-us">
                <div class="main-title">
                    <p>FACILITANDO</p>

                    <p>PAGAMENTOS</p>
                </div>

                <div class="about-us-icons">
                    <div class="icon">
                        <atlas-icon name="dollar-sign" size="6x"></atlas-icon>

                        <p>Controle financeiro</p>
                    </div>

                    <div class="icon">
                        <atlas-icon name="money" size="6x"></atlas-icon>

                        <p>Geração de cobranças</p>
                    </div>

                    <div class="icon">
                        <atlas-icon name="bell" size="6x"></atlas-icon>

                        <p>Notificações em tempo real</p>
                    </div>
                </div>
            </div>

            <atlas-panel header="Nossos serviços" carousel items-per-page="1" id="services-tab" class="services-tab">
                <atlas-card
                    header="Controle financeiro para sua empresa"
                    image="assets/landingpage/business.jpg">
                    Realize a gestão de pagamentos e cobranças da sua empresa com facilidade, contando com um sistema
                    eficiente que automatiza e simplifica todo o processo. Com o Quickcharge, você pode reduzir
                    significativamente o tempo gasto no processo de cobrança. O sistema permite
                    o monitoramento das transações, emissão de faturas de forma rápida e precisa, além de
                    oferecer recursos para o envio automatizado de lembretes de pagamento. Dessa forma, você pode se
                    concentrar em outras atividades estratégicas, enquanto o sistema cuida das suas finanças de maneira
                    eficaz. Simplifique a gestão financeira da sua empresa e ganhe tempo para se dedicar ao crescimento
                    do seu negócio.
                </atlas-card>
                <atlas-card
                    header="Crie cobranças com facilidade"
                    image="assets/landingpage/handshake.jpg">
                    Simplifique o processo de cobranças para os seus clientes com facilidade e agilidade. Com o Quickcharge,
                    você pode criar cobranças de forma rápida e descomplicada, personalizando-as de acordo com as
                    necessidades específicas de cada cliente. Dessa forma você terá mais controle sobre o processo de
                    cobrança, otimizando o fluxo de caixa da sua empresa e mantendo um relacionamento saudável com seus
                    clientes.
                </atlas-card>

                <atlas-card
                    header="Notifique prazos de vencimento"
                    image="assets/landingpage/notify.jpg">
                    Notifique seus clientes de forma ágil e eficiente sobre as cobranças pendentes. Com Quickcharge,
                    você consegue enviar notificações automáticas aos seus clientes, informando sobre as cobranças e
                    seus respectivos prazos de pagamento.
                </atlas-card>
            </atlas-panel>

            <atlas-panel header="Entre em contato conosco!" id="contacts" class="contactus">
                <atlas-layout class="contactus" inline gap="9">
                    <a class="contactus-card" href="https://github.com/Jezreel-Moraes-Asaas" target="_blank">
                        <asset:image src="github/github-mark.svg" class="github-logo"/>
                        Jezreel Moraes
                    </a>

                    <a class="contactus-card" href="https://github.com/willevini" target="_blank">
                        <asset:image src="github/github-mark.svg" class="github-logo"/>
                        Vinícius Wille Alves
                    </a>
                </atlas-layout>
            </atlas-panel>
        </main>

        <footer class="footer">
            <p>Feito com <span class="heart">❤️</span> por Jezreel Moraes e Vinícius Wille</p>
        </footer>
    </body>
</html>