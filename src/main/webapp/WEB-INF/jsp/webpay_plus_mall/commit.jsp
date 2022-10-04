<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>

<jsp:include page="../template/tpl-header.jsp" />

<body class="container">
    <jsp:include page="../template/navbar-webpayplus.jsp" />

    <div class="row">
        <div class="col">
            <h2>Confirmar Transacción Mall</h2>
            Luego de que se termina el flujo en el formulario de pago recibiras un POST con lo siguiente.
        </div>
    </div><br>



    <div class="row">
        <div class="col-6">
            <h4>Datos recibidos</h4>
            Luego de que se termina el flujo en el formulario de pago recibiras un POST con lo siguiente
        </div>
        <div class="col-6">
            <pre>
                <code class="language-java">
{
    'token_ws': '${details.get("token_ws")}'
}
                </code>
            </pre>
        </div>

    </div><br>

        <div class="row">

            <div class="col-6">
                <pre>
                    <code class="language-json">
    //inicio sección de importación
    import cl.transbank.common.IntegrationApiKeys;
    import cl.transbank.common.IntegrationCommerceCodes;
    import cl.transbank.common.IntegrationType;
    import cl.transbank.model.MallTransactionCreateDetails;
    import cl.transbank.webpay.common.WebpayOptions;
    import cl.transbank.webpay.webpayplus.responses.*;
    import cl.transbank.webpay.webpayplus.WebpayPlus;
    //fin sección de importación

    WebpayPlus.MallTransaction tx = new WebpayPlus.MallTransaction(new WebpayOptions(IntegrationCommerceCodes.WEBPAY_PLUS_MALL, IntegrationApiKeys.WEBPAY, IntegrationType.TEST));
    WebpayPlusMallTransactionCommitResponse response = tx.commit(tokenWs);
                    </code>
                </pre>
            </div>
            <div class="col-6">
                <h4>Petición</h4>
                 Usaras el token recibido para confirmar la transacción usando nuevamente WebpayPlus
            </div>
        </div><br>



    <div class="row">
        <div class="col-6">
            <h4>Respuesta</h4>
            Transbank contestará con lo siguiente. Debes guardar esta información, lo único que debes validar es que response_code sea igual a cero.
        </div>
        <div class="col-6">
            <pre>
                <code class="language-json">
${details.get("resp")}
                </code>
            </pre>
        </div>
    </div><br>

    <div class="row">
        <h2>Listo !</h2>
        Ya puedes mostrar al usuario una página de éxito de la transacción.
    </div><br>

    <jsp:include page="../template/mall-status-refund.jsp" />
    <jsp:include page="../template/footer.jsp" />

</body>
</html>