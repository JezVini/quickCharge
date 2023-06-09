<html>
    <head>
        <meta name="layout" content="main">
        <title>Listagem de pagadores</title>
    </head>
    <body>

        <h1>
            <g:if test="${deletedOnly}">
                Mostrando pagadores desativados
            </g:if>
            <g:elseif test="${includeDeleted}">
                Mostrando todos os pagadores
            </g:elseif>
            <g:else>
                Mostrando pagadores ativos
            </g:else>
        </h1>

        <p><g:message code="${flash.message}"/></p>

        <g:link action="create">
            <button>Novo pagador</button>
        </g:link>

        <table>
            <tr>
                <th>
                    <g:link action="index">
                        <button>Mostrar apenas pagadores ativos</button>
                    </g:link>
                </th>

                <th>
                    <g:link action="index" params="${[deletedOnly: true]}">
                        <button>Mostrar apenas pagadores desativados</button>
                    </g:link>
                </th>

                <th>
                    <g:link action="index" params="${[includeDeleted: true]}">
                        <button>Mostrar todos os pagadores</button>
                    </g:link>
                </th>
            </tr>
            <tr>
                <th>Nome</th>
                <th>E-mail</th>
                <th>CPF/CNPJ</th>
                <th>Telefone</th>
                <th>Ações</th>
            </tr>
            <g:if test="${payerList}">
                <g:each in="${payerList}" var="payer">

                    <%
                        Map parameterMap = [
                            id            : payer.id,
                            deletedOnly   : deletedOnly,
                            includeDeleted: includeDeleted
                        ]
                    %>

                    <tr>
                        <td>${payer.name}</td>
                        <td>${payer.email}</td>
                        <td>${payer.cpfCnpj}</td>
                        <td>${payer.phone}</td>
                        <td>
                            <g:if test="${payer.deleted}">
                                <g:link action="restore" params="${parameterMap}">
                                    <button style="background-color: #0f0">Restaurar</button>
                                </g:link>
                            </g:if>
                            <g:else>
                                <g:link action="edit" params="${[id: payer.id]}">
                                    <button style="background-color: yellow ">Editar</button>
                                </g:link>

                                <g:link action="delete" params="${parameterMap}">
                                    <button style="background-color: #f00">Remover</button>
                                </g:link>
                            </g:else>
                        </td>
                    </tr>

                </g:each>
            </g:if>
            <g:else>
                <tr><td>Nenhum pagador encontrado</td></tr>
            </g:else>
        </table>
    </body>
</html>