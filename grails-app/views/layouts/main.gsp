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

        <link rel="stylesheet" href="https://atlas.asaas.com/v9.2.0/atlas.css"
              integrity="sha384-YmA7EnYqhHBpt32Gq7QBxbKy8jWbw5qIYu8f++Wl+PpqKTRmK+Pw+VKItsPpmLJI"
              crossorigin="anonymous">
        <script defer src="https://atlas.asaas.com/v9.2.0/atlas.js"
                integrity="sha384-9ousY7CNvMeUL2TT4DpJcImS2zvyMrsHUywHo++f3XItWZHc+c/6u7pyFqIEp/Jx"
                crossorigin="anonymous"></script>

        <asset:stylesheet src="atlas-colors/colors.css"/>
        <asset:stylesheet src="main/main.css.css"/>
        <asset:javascript src="pages/currentPage.js"/>
        <asset:javascript src="sidebar/SidebarController.js"/>

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
