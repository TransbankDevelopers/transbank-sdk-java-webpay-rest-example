<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>

<jsp:include page="../template/tpl-header.jsp" />

<body class="container">
    <jsp:include page="../template/navbar-webpayplus.jsp" />

    <div class="row">
        <div class="col">
            <h2>Incrementar Monto Pre-autorizado</h2>
            En este paso podemos aumentar de manera directa el monto previamente pre-autorizado (todas las veces que se necesite). La transacción no debe haber sido capturada. Solo es soportado por tarjetas VISA/MASTERCARD (AMEX retornara un error 'Unsupported Operation').
        </div>
    </div><br>

    <div class="row">
        <div class="col-6">
            <h4>Petición</h4>
            Para incrementar el monto pre-autorizado de una transacción necesitaremos el Token, Orden de compra, Código de autorización y monto a incrementar. Se hace de la siguiente manera.
        </div>
        <div class="col-6">
            <pre>
                <code class="language-java">
//inicio sección de importación
import cl.transbank.common.IntegrationApiKeys;
import cl.transbank.common.IntegrationCommerceCodes;
import cl.transbank.common.IntegrationType;
import cl.transbank.model.MallTransactionCreateDetails;
import cl.transbank.webpay.common.WebpayOptions;
import cl.transbank.webpay.webpayplus.WebpayPlus;
import cl.transbank.webpay.responses.*;
//fin sección de importación

WebpayPlus.MallTransaction tx = new WebpayPlus.MallTransaction(new WebpayOptions(IntegrationCommerceCodes.WEBPAY_PLUS_MALL_DEFERRED, IntegrationApiKeys.WEBPAY, IntegrationType.TEST));
IncreaseAmountResponse response = tx.increaseAmount(tokenWs, buyOrder, authorizationCode, amount);
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
            Transbank contestará con lo siguiente. Debes guardar esta información, lo único que debes validar es que response_code sea igual a cero.
        </div>
    </div><br>

    <jsp:include page="../deferred_common/deferred-options.jsp" />
    <jsp:include page="../template/footer.jsp" />
</body>
</html>