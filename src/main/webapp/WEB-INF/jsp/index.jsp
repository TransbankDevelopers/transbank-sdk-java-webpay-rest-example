<html>
<head>
    <title>Ejemplo integraci&oacute;n Webpay Rest</title>
    <jsp:include page="template/header.jsp"/>
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
        <td><a href="/webpayplus-form">Crear Transacci&oacute;n</a></td>
        <td><a href="/webpayplus-refund-form">Reembolsar Transacci&oacute;n</a></td>
        <td><a href="/webpayplus-status-form">Consultar Estado Transacci&oacute;n</a></td>
    </tr>
    <tr>
        <td>Webpay Plus Mall</td>
        <td><a href="/webpayplusmall-form">Crear Transacci&oacute;n</a></td>
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
        <td><a href="/oneclick-mall/start-form">Comenzar Inscripci&oacute;n</a></td>
        <td><a href="/oneclick-mall/refund-form">Reembolsar Transacci&oacute;n</a></td>
        <td><a href="/oneclick-mall/status-form">Consultar Estado Transacci&oacute;n</a></td>
    </tr>

    <tr>
    <td>Transacci&oacute;n Completa</td>
    <td><a href="/fulltransaction/create-form">Crear Transacci&oacute;n</a></td>
    <td><a href="/fulltransaction/refund-form">Reembolsar Transacci&oacute;n</a></td>
    <td><a href="/fulltransaction/status-form">Consultar Estado Transacci&oacute;n</a></td>
    </tr>

    <tr>
    <td>Patpass-webpay</td>
    <td><a href="/patpass-webpay/create-form">Crear Transacci&oacute;n</a></td>
    <td><a href="/patpass-webpay/refund-form">Reembolsar Transacci&oacute;n</a></td>
    <td><a href="/patpass-webpay/status-form">Consultar Estado Transacci&oacute;n</a></td>
    </tr>

    <tr>
    <td>Transacci&oacute;n Completa Mall</td>
    <td><a href="/mallfulltransaction/create-form">Crear Transacci&oacute;n</a></td>
    <td><a href="/mallfulltransaction/refund-form">Reembolsar Transacci&oacute;n</a></td>
    <td><a href="/mallfulltransaction/status-form">Consultar Estado Transacci&oacute;n</a></td>
    </tr>

    <tr>
    <td>Patpass Comercio</td>
    <td><a href="/patpass-comercio/start-form">Crear Transacci&oacute;n</a></td>
    <td><a href="#"></a></td>
    <td><a href=""></a></td>
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
    <jsp:include page="template/footer.jsp"/>
</body>
</html>