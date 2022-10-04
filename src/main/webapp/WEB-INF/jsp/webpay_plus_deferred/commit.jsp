<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>

<jsp:include page="../template/tpl-header.jsp" />

<body class="container">
    <jsp:include page="../template/navbar-webpayplus.jsp" />

    <div class="row">
        <div class="col">
            <h2>Confirmar Transacci�n diferida</h2>
            En este paso tenemos que confirmar la transacci�n con el objetivo de avisar a Transbank que hemos recibido la transacci�n ha sido recibida exitosamente. En caso de que no se confirme la transacci�n, �sta ser� reversada.
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
    //inicio secci�n de importaci�n
    import cl.transbank.common.IntegrationApiKeys;
    import cl.transbank.common.IntegrationCommerceCodes;
    import cl.transbank.common.IntegrationType;
    import cl.transbank.webpay.common.WebpayOptions;
    import cl.transbank.webpay.webpayplus.WebpayPlus;
    import cl.transbank.webpay.webpayplus.responses.*;
    //fin secci�n de importaci�n

    WebpayPlus.Transaction tx = new WebpayPlus.Transaction(new WebpayOptions(IntegrationCommerceCodes.WEBPAY_PLUS_DEFERRED, IntegrationApiKeys.WEBPAY, IntegrationType.TEST));
    WebpayPlusTransactionStatusResponse response = tx.commit(token);
                    </code>
                </pre>
            </div>
            <div class="col-6">
                <h4>Petici�n</h4>
                 Usaras el token recibido para confirmar la transacci�n usando nuevamente WebpayPlus
            </div>
        </div><br>



    <div class="row">
        <div class="col-6">
            <h4>Respuesta</h4>
            Transbank contestar� con lo siguiente. Debes guardar esta informaci�n, lo �nico que debes validar es que response_code sea igual a cero.
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
        <h2>Casi listo !</h2>
        Ya puedes mostrar al usuario una p�gina de �xito de la transacci�n. Debes tener en cuenta que la transacci�n aun no ha sido capturada solo ha sido retenido el saldo en la tarjeta del Tarjetahabiente. Luego de confirmar la transacci�n podemos:
    </div><br>


    <jsp:include page="../deferred_common/deferred-options.jsp" />
    <jsp:include page="../template/footer.jsp" />

</body>
</html>

