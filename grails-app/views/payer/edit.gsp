<html>
    <head>
        <meta name="layout" content="main">
        <title>Alterar dados de Pagador</title>
        <asset:javascript src="cep/cepAutomatically.js"/>
    </head>
    <body>
        
        <g:message code="${flash.message}"/>
        
        <g:if test="${payer}">
            <form action="${createLink(controller: "payer", action: "update")}" method="post">

                <input type="hidden" name="id" value="${payer.id}">

                <label for="name">Nome</label>
                <input type="text" name="name" id="name" placeholder="Digite seu nome" value="${payer.name}">
                <br>

                <label for="email">E-mail</label>
                <input type="email" name="email" id="email" placeholder="Digite seu e-mail" value="${payer.email}">
                <br>

                <label for="cpfCnpj">CPF ou CNPJ</label>
                <input type="text" name="cpfCnpj" id="cpfCnpj" placeholder="Digite o CPF ou CNPJ"
                       value="${payer.cpfCnpj}">
                <br>

                <label for="phone">Telefone</label>
                <input type="tel" name="phone" id="phone" placeholder="Digite o número de telefone/celular"
                       value="${payer.phone}">
                <br>

                <label for="postalCode">CEP</label>
                <input type="text" name="postalCode" id="postalCode" onblur="cepSearch(this.value)"
                       placeholder="Digite o CEP" value="${payer.postalCode}">
                <br>

                <label for="state">Estado</label>
                <input type="text" name="state" id="state" placeholder="Digite seu estado" value="${payer.state}">
                <br>

                <label for="city">Cidade</label>
                <input type="text" name="city" id="city" placeholder="Digite sua cidade" value="${payer.city}">
                <br>

                <label for="district">Bairro</label>
                <input type="text" name="district" id="district" placeholder="Digite o distrito"
                       value="${payer.district}">
                <br>

                <label for="address">Rua</label>
                <input type="text" name="address" id="address" placeholder="Digite a rua" value="${payer.address}">
                <br>

                <label for="addressNumber">Número</label>
                <input type="text" name="addressNumber" id="addressNumber" placeholder="Digite o número"
                       value="${payer.addressNumber}">
                <br>

                <label for="addressComplement">Complemento</label>
                <input type="text" name="addressComplement" id="addressComplement" placeholder="Digite o complemento"
                       value="${payer.addressComplement}">
                <br>

                <button type="submit">Salvar</button>

            </form>
        </g:if>
        <g:else>
            Pagador não encontrado
        </g:else>
    </body>
</html>
