<html>

<head>
    <title>Ejemplo integraci&oacute;n Webpay Rest</title>
    <jsp:include page="template/header.jsp" />
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
                <td>Webpay Plus Mall Mall Deferred</td>
                <td><a href="/webpayplusmalldeferred">Crear Transacci&oacute;n</a></td>
                <td><a href="/webpayplusmalldeferred-refund-form">Reembolsar Transacci&oacute;n</a></td>
                <td><a href="/webpayplusmalldeferred-status-form">Consultar Estado Transacci&oacute;n</a></td>
            </tr>
            <tr>
                <td>Oneclick Mall</td>
                <td><a href="/oneclick-mall/start">Comenzar Inscripci&oacute;n</a></td>
                <td><a href="/oneclick-mall/refund-form">Reembolsar Transacci&oacute;n</a></td>
                <td><a href="/oneclick-mall/status-form">Consultar Estado Transacci&oacute;n</a></td>
            </tr>
            <tr>
                <td>Oneclick Mall Deferred Capture</td>
                <td><a href="/oneclick-mall-deferred/start">Start Inscription</a></td>
                <td><a href="/oneclick-mall-deferred/refund-form">Reembolsar Transacci&oacute;n</a></td>
                <td><a href="/oneclick-mall-deferred/status-form">Consultar Estado Transacci&oacute;n</a></td>
            </tr>
            <tr>
                <td>Transacci&oacute;n Completa</td>
                <td><a href="/fulltransaction/create">Crear Transacci&oacute;n</a></td>
                <td><a href="/fulltransaction/refund-form">Reembolsar Transacci&oacute;n</a></td>
                <td><a href="/fulltransaction/status-form">Consultar Estado Transacci&oacute;n</a></td>
            </tr>
            <tr>
                <td>Transacci&oacute;n Completa Mall</td>
                <td><a href="/mallfulltransaction/create">Crear Transacci&oacute;n</a></td>
                <td><a href="/mallfulltransaction/refund-form">Reembolsar Transacci&oacute;n</a></td>
                <td><a href="/mallfulltransaction/status-form">Consultar Estado Transacci&oacute;n</a></td>
            </tr>
            <tr>
                <td>Patpass-webpay</td>
                <td><a href="/patpass-webpay/create">Crear Transacci&oacute;n</a></td>
                <td><a href="/patpass-webpay/refund-form">Reembolsar Transacci&oacute;n</a></td>
                <td><a href="/patpass-webpay/status-form">Consultar Estado Transacci&oacute;n</a></td>
            </tr>
            <tr>
                <td>Patpass Comercio</td>
                <td><a href="/patpass-comercio/start">Crear Transacci&oacute;n</a></td>
                <td><a href="#"></a></td>
                <td><a href="/patpass-comercio/status">Consultar Estado Transacci&oacute;n</a></td>
            </tr>
        </tbody>
    </table>
    <jsp:include page="template/footer.jsp" />
</body>
</html>
