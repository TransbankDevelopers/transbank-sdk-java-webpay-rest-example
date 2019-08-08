<html>
<head>
    <title>Ejemplo integraci&oacute;n Webpay Rest</title>
    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
</head>

<body class="container">
<h1>Ejemplos Webpay</h1>
    <table class="table">
        <thead class="thead-dark">
            <tr>
            <th scope="col">Service</th>
            <th scope="col">Create</th>
            <th scope="col">Refund</th>
            <th scope="col">Status</th>
            </tr>
        </thead>
    <tbody>
    <tr>
        <td>Webpay Plus</td>
        <td><a href="/webpayplus">Crear Transacci&oacute;n</a></td>
        <td><a href="/webpayplus-refund-form">Reembolsar Transacci&oacute;n</a></td>
        <td><a href="/webpayplus-status-form">Consultar Estado Transacci&oacute;n</a></td>
    </tr>
    <tr>
        <td>Webpay Plus Mall</td>
        <td><a href="/webpayplusmall">Crear Transacci&oacute;n</a></td>
        <td><a href="/webpayplusmall-refund-form">Reembolsar Transacci&oacute;n</a></td>
        <td><a href="/webpayplusmall-status-form">Consultar Estado Transacci&oacute;n</a></td>
    </tr>
    <tr>
        <td>Webpay Plus Captura Diferida</td>
        <td><a href="/webpayplusdeferred">Crear Transacci&oacute;n</a></td>
        <td><a href="/webpayplusdeferred-refund-form">Reembolsar Transacci&oacute;n</a></td>
        <td><a href="/webpayplusdeferred-status-form">Consultar Estado Transacci&oacute;n</a></td>
    </tr>

    <tr>
        <td>Oneclick Mall</td>
        <td><a href="/oneclick-mall/start">Comenzar Inscripci&oacute;n</a></td>
        <td><a href="/oneclick-mall/refund-form">Reembolsar Transacci&oacute;n</a></td>
        <td><a href="/oneclick-mall/status-form">Consultar Estado Transacci&oacute;n</a></td>
    </tr>

    <tr>
    <td>Transacci&oacute;n Completa</td>
    <td><a href="/fulltransaction/create">Crear Transacci&oacute;n</a></td>
    </tr>

    <tr>
    <td>Patpass-webpay</td>
    <td><a href="/patpass-webpay/create">Crear Transacci&oacute;n</a></td>
    <td><a href="/patpass-webpay/refund-form">Reembolsar Transacci&oacute;n</a></td>
    <td><a href="/patpass-webpay/status-form">Consultar Estado Transacci&oacute;n</a></td>
    </tr>
    <%-- <tr>
        <td>Oneclick Mall Deferred Capture</td>
        <td><a href="/oneclick-mall-deferred/start">Start Inscription</a></td>
    </tr> --%>
    <!--
    <tr>
        <td>Transacci&oacute;n Mall</td>
        <td><a href="tbk-mall-normal.jsp">Webpay Plus Mall</a></td>
        <td><a href="tbk-nullify-mall-normal.jsp">Webpay Plus Mall Anulaci&oacute;n </a></td>
    </tr>
    <tr>
        <td>Transacci&oacute;n Captura Diferida</td>
        <td><a href="tbk-normal-capture.jsp">Webpay Plus Captura Diferida</a></td>
        <td><a href="tbk-capture.jsp">Webpay Plus Captura</a></td>
    </tr>
    <tr>
        <td>Transacci&oacute;n OneClick</td>
        <td><a href="tbk-oneclick.jsp">Webpay OneClick Normal</a></td>
        <td> - </td>
    </tr>
    -->
    </tbody>
</table>
    <!-- jQuery first, then Popper.js, then Bootstrap JS -->
    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
</body>
</html>