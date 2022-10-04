<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>

<jsp:include page="../template/tpl-header.jsp" />

<body class="container">
    <jsp:include page="../template/navbar-webpayplus.jsp" />

    <div class="row">
        <div class="col">
            <h2>Capturar Transacci�n diferida</h2>
            En este paso debemos capturar la transacci�n para realmente capturar el dinero que habia sido previamente reservado al hacer la transacci�n
        </div>
    </div><br>



    <div class="row">
        <div class="col-6">
            <h4>Petici�n</h4>
            Para capturar una transacci�n necesitaremos el Token, Orden de compra, C�digo de autorizaci�n y monto a capturar. Se hace de la siguiente manera.
        </div>
        <div class="col-6">
            <pre>
                <code class="language-json">
    //inicio secci�n de importaci�n
    import cl.transbank.common.IntegrationApiKeys;
    import cl.transbank.common.IntegrationCommerceCodes;
    import cl.transbank.common.IntegrationType;
    import cl.transbank.webpay.common.WebpayOptions;
    import cl.transbank.webpay.webpayplus.WebpayPlus;
    import cl.transbank.webpay.webpayplus.responses.*;
    //fin secci�n de importaci�n

    WebpayPlus.Transaction tx = new WebpayPlus.Transaction(new WebpayOptions(IntegrationCommerceCodes.WEBPAY_PLUS_DEFERRED, IntegrationApiKeys.WEBPAY, IntegrationType.TEST));
    WebpayPlusTransactionCaptureResponse response = tx.capture(tokenWs, buyOrder, authorizationCode, amount);
                    </code>
            </pre>
        </div>

    </div><br>

    <div class="row">
        <div class="col-6">
            <pre>
                <code class="language-json">
${details.get("resp")}
                </code>
            </pre>
        </div>
        <div class="col-6">
            <h4>Respuesta</h4>
            Transbank contestar� con lo siguiente. Debes guardar esta informaci�n, lo �nico que debes validar es que response_code sea igual a cero.
        </div>
    </div><br>

    <jsp:include page="../template/status-refund.jsp" />
    <jsp:include page="../template/footer.jsp" />

</body>
</html>