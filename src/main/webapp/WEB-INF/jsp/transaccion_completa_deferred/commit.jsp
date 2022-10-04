<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>

<jsp:include page="../template/tpl-header.jsp" />

<body class="container">
    <jsp:include page="../template/navbar-txcompleta.jsp" />

    <div class="row">
        <div class="col">
            <h2>Confirmar Transacción</h2>
            En este paso tenemos que confirmar la transacción con el objetivo de avisar a Transbank que hemos recibido la transacción ha sido recibida exitosamente. En caso de que no se confirme la transacción, ésta será reversada.
        </div>
    </div><br>

        <div class="row">
            <div class="col-6">
                <h4>Petición</h4>
                 Para poder confirmar la transacción debes enviar el token, y en caso de pago en cuotas debes también enviar el ID de la consulta de couotas. En algunos casos tambien debes enviar el Indice del periodo diferido y un boolean indicando si se tomará el período de gracia.
            </div>
            <div class="col-6">
                <pre>
                    <code class="language-json">
    //inicio sección de importación
    import cl.transbank.common.IntegrationApiKeys;
    import cl.transbank.common.IntegrationCommerceCodes;
    import cl.transbank.common.IntegrationType;
    import cl.transbank.webpay.common.WebpayOptions;
    import cl.transbank.webpay.transaccioncompleta.FullTransaction;
    import cl.transbank.webpay.transaccioncompleta.responses.*;
    //fin sección de importación

    FullTransaction tx = new FullTransaction(new WebpayOptions(IntegrationCommerceCodes.TRANSACCION_COMPLETA_DEFERRED, IntegrationApiKeys.WEBPAY, IntegrationType.TEST));
    FullTransactionCommitResponse response = tx.commit(token, idQueryInstallments, deferredPeriodIndex, gracePeriod);
                    </code>
                </pre>
            </div>

        </div><br>





    <div class="row">
        <div class="col-6">
            <h4>Respuesta</h4>
            Una vez confirmada la transacción recibirás la siguiente información.
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

    <jsp:include page="../deferred_common/deferred-options.jsp" />
    <jsp:include page="../template/footer.jsp" />

</body>
</html>