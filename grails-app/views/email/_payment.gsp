<!DOCTYPE html>
<html lang="pt-BR">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Email cobrança</title>

        <link rel="preconnect" href="https://fonts.googleapis.com">
        <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
        <link href="https://fonts.googleapis.com/css2?family=Open+Sans:wght@400;600;700&display=swap" rel="stylesheet">

        <style>

        body {
            font-family: 'Open Sans', Roboto, RobotoDraft, Helvetica, Arial, sans-serif;
            background-color: #f0f0f0;
            margin: 0px;
            border: none;
            padding: 0px;
        }

        .container {
            max-width: 1000px;
            width: 60%;
            min-width: 900px;
            margin: auto;
            background-color: white;
        }

        .container header {
            display: flex;
            height: 80px;
            background-color: black;
            color: white;
            align-items: center;
            justify-content: center;
            padding: 20px;
        }

        .container .logo {
            margin: auto;
            display: block;
            height: 80px;
        }

        .container .main {
            padding: 20px;
            min-width: 700px;
            width: 98%;
            max-width: 900px;
            margin: auto;
        }

        .container h1 {
            text-align: center;
        }

        div.payment {
            border-radius: 5px;
            background-color: white;
            border: 1px solid #ddd;
        }

        div.payment div.payment_header {
            height: 80px;
            padding: 20px;
            border-radius: 5px 5px 0px 0px;
            background-color: #ddd;
            display: flex;
        }

        div.payment div.payment_header img {
            height: 80px;
            padding-right: 20px;
        }

        div.payment div.payment_main {
            padding: 20px;
        }

        table.data_table {
            width: 100%;
        }

        table.data_table th {
            background-color: #ddd;
        }

        table.data_table td {
            text-align: center;
            background-color: #fafafa;
            padding: 10px;
        }

        div.payment div.payment_footer {
            height: 40px;
            padding: 0px 20px;
            border-radius: 0px 0px 5px 5px;
            background-color: #ddd;
            display: flex;
            justify-content: space-between;
            align-items: center;
        }

        a {
            text-decoration: none;
            color: inherit;
        }

        a:hover {
            text-decoration: underline;
        }

        .main a.show_data_button {
            margin: 30px 0px 15px 0px;
            border: none;
            padding: 5px;
            width: 100%;
            display: block;
            text-align: center;
            color: white;
            font-weight: 600;
            height: 10vh;
            max-height: 40px;
            font-size: 1.6rem;
            border-radius: 5px;
            background-color: black;
            cursor: pointer;
        }

        .main a.show_data_button:hover {
            text-decoration: underline;
            background-color: #1f1f1f;
        }

        .main a span {
            color: black;
        }

        footer {
            display: flex;
            height: 40px;
            background-color: black;
            color: white;
            padding: 20px;
        }

        footer h3 {
            display: block;
            margin: auto;
            text-align: center;
        }

        footer h3 strong {
            display: block;
            margin: auto;
            text-align: center;
        }

        footer h3 strong a {
            color: white !important;
        }

        .heart {
            color: red;
            font-size: larger;
        }
        </style>

    </head>

    <body>
        <div class="container">

            <header>
                <img class="logo" src="https://asaas.com/images/home3/header-logo.svg"
                     alt="Logo QuickCharge completa em branco">
            </header>

            <div class="main">

                <h1>${subject}</h1>

                <div class="payment">

                    <div class="payment_header">
                        <h1>${customer.name}</h1>
                    </div>

                    <div class="payment_main">
                        <div class="payer_info_container">
                            <h4>Dados do cliente | Pagador</h4>

                            <table class="data_table">
                                <tr>
                                    <th>Nome</th>
                                    <th>E-mail</th>
                                    <th>CPF/CNPJ</th>
                                    <th>Telefone</th>
                                </tr>

                                <tr>
                                    <td>${payment.payer.name}</td>
                                    <td>${payment.payer.email}</td>
                                    <td>${payment.payer.cpfCnpj}</td>
                                    <td>${payment.payer.phone}</td>
                                </tr>
                            </table>

                        </div>

                        <div class="payment_info_container">
                            <h4>Dados da cobrança</h4>

                            <table class="data_table">
                                <tr>
                                    <th>Data de criação</th>
                                    <th>Forma de pagamento</th>
                                    <th>Data de vencimento</th>
                                    <th>Situação</th>
                                </tr>

                                <tr>
                                    <td><g:formatDate format="dd/MM/yyyy" date="${payment.dateCreated}"/></td>
                                    <td><g:message code="ENUM.BillingType.${payment.billingType}"/></td>
                                    <td><g:formatDate format="dd/MM/yyyy" date="${payment.dueDate}"/></td>
                                    <td><g:message code="ENUM.PaymentStatus.${payment.status}"/></td>
                                </tr>
                            </table>
                        </div>
                    </div>

                    <div class="payment_footer">
                        <h3>Valor da cobrança:</h3>

                        <h3><strong>R$: ${payment.value}</strong></h3>
                    </div>
                </div>

                <a class="show_data_button" href="http://localhost:8080/payer/edit/${payment.payer.id}">
                    Mostrar mais dados
                </a>
            </div>


            <footer>
                <h3>
                    <strong>Atenciosamente, equipe
                        <a href="https://github.com/JezVini/quickCharge">QuickCharge</a>
                        <span class="heart">❤</span></strong>
                </h3>
            </footer>
        </div>
    </body>
</html>