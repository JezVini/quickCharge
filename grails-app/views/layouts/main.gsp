<!doctype html>
<html lang="pt-br" class="no-js">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
        <title>
        <g:layoutTitle default="quickCharge"/>
        </title>
        <meta name="viewport" content="width=device-width, initial-scale=1"/>
        <asset:link rel="icon" href="logo/quickCharge.ico" type="image/x-ico"/>

        <link rel="stylesheet" href="https://atlas.asaas.com/v7.5.0/atlas.css"
              integrity="sha384-TNq7rPEaeJzo2yVc9iwEks5VwcqkV651ULn51l5YiJxYQGLxfHFFgsk3nWSBiD0A"
              crossorigin="anonymous">
        <script defer src="https://atlas.asaas.com/v7.5.0/atlas.js"
                integrity="sha384-ZrJyf8Bowh8ESuZeAX1awzuL0VeximAlaKYvOIfcGLqavQDguQXgMxKJ5hRRUL53"
                crossorigin="anonymous"></script>

        <asset:stylesheet src="atlas-colors/colors.css"/>
        <asset:stylesheet src="main/main.css.css"/>
        <asset:javascript src="pages/currentPage.js"/>

        <g:layoutHead/>
    </head>

    <body>
        <atlas-screen>
            <sideBar:render/>
            <atlas-page>
                <atlas-page-header slot="header" page-name="">
                </atlas-page-header>
                <atlas-page-content slot="content" class="js-atlas-content">
                    <g:layoutBody/>
                </atlas-page-content>
            </atlas-page>
        </atlas-screen>
    </body>
</html>
