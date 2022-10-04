<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>

<jsp:include page="../template/tpl-header.jsp" />

<body class="container">
    <jsp:include page="../template/navbar-txcompleta.jsp" />

    <div class="row">
        <div class="col">
            <h2>Confirmar Transacci�n</h2>
            En este paso tenemos que confirmar la transacci�n con el objetivo de avisar a Transbank que hemos recibido la transacci�n ha sido recibida exitosamente. En caso de que no se confirme la transacci�n, �sta ser� reversada.
        </div>
    </div><br>

        <div class="row">
            <div class="col-6">
                <h4>Petici�n</h4>
                 Para poder confirmar la transacci�n debes enviar el token, y en caso de pago en cuotas debes tambi�n enviar el ID de la consulta de couotas. En algunos casos tambien debes enviar el Indice del periodo diferido y un boolean indicando si se tomar� el per�odo de gracia.
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

    MallFullTransaction tx = new MallFullTransaction(new WebpayOptions(IntegrationCommerceCodes.TRANSACCION_COMPLETA_MALL, IntegrationApiKeys.WEBPAY, IntegrationType.TEST));
    MallTransactionCommitDetails detailss = MallTransactionCommitDetails.build().add(childCommerceCode, childBuyOrder, idQueryInstallments, deferredPeriodIndex, gracePeriod);
                    </code>
                </pre>
            </div>

        </div><br>





    <div class="row">
        <div class="col-6">
            <h4>Respuesta</h4>
            Una vez confirmada la transacci�n recibir�s la siguiente informaci�n.
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
        Ya puedes mostrar al usuario una p�gina de �xito de la transacci�n.
    </div><br>

    <jsp:include page="../template/tx-mall-status-refund.jsp" />
    <jsp:include page="../template/footer.jsp" />

</body>
</html>