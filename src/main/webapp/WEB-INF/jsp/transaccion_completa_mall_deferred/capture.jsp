<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>

<jsp:include page="../template/tpl-header.jsp" />

<body class="container">
    <jsp:include page="../template/navbar-txcompleta.jsp" />

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
    import cl.transbank.model.MallTransactionCreateDetails;
    import cl.transbank.webpay.common.WebpayOptions;
    import cl.transbank.webpay.transaccioncompleta.MallFullTransaction;
    import cl.transbank.webpay.transaccioncompleta.model.MallTransactionCommitDetails;
    import cl.transbank.webpay.transaccioncompleta.responses.*;
    //fin secci�n de importaci�n

    MallFullTransaction tx = new MallFullTransaction(new WebpayOptions(IntegrationCommerceCodes.TRANSACCION_COMPLETA_MALL_DEFERRED, IntegrationApiKeys.WEBPAY, IntegrationType.TEST));
    MallFullTransactionCaptureResponse response = tx.capture(tokenWs, childCommerceCode, childBuyOrder, authorizationCode, amount);
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

    <jsp:include page="../template/tx-mall-status-refund.jsp" />
    <jsp:include page="../template/footer.jsp" />

</body>
</html>