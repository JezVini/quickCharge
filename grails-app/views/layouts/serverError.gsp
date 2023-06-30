<html lang="pt-br" class="no-js">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
        <title>
        <g:layoutTitle default="quickCharge"/>
        </title>
        <meta name="viewport" content="width=device-width, initial-scale=1"/>
        <asset:link rel="icon" href="logo/quickCharge.ico" type="image/x-ico"/>

        <link rel="stylesheet" href="https://atlas.asaas.com/v9.2.0/atlas.css"
              integrity="sha384-YmA7EnYqhHBpt32Gq7QBxbKy8jWbw5qIYu8f++Wl+PpqKTRmK+Pw+VKItsPpmLJI"
              crossorigin="anonymous">

        <script defer src="https://atlas.asaas.com/v9.2.0/atlas.js"
                integrity="sha384-9ousY7CNvMeUL2TT4DpJcImS2zvyMrsHUywHo++f3XItWZHc+c/6u7pyFqIEp/Jx"
                crossorigin="anonymous"></script>

        <asset:stylesheet src="atlas-colors/colors.css"/>
        <asset:stylesheet src="main/main.css"/>
        <asset:stylesheet src="external.css"/>
        <asset:stylesheet src="errors.css"/>
        <g:layoutHead/>
    </head>

    <body class="external-container">
        <header class="external-header">
            <g:link controller="login" action="auth">
                <asset:image src="logo/quickChargeNormalWhite.svg" class="external-header-logo"/>
            </g:link>
        </header>

        <main class="external-main-container">
            <g:layoutBody/>
        </main>

        <footer class="external-footer">
            <p>Feito com <span class="heart">❤️</span> por Jezreel Moraes e Vinícius Wille</p>
        </footer>
    </body>
</html>
