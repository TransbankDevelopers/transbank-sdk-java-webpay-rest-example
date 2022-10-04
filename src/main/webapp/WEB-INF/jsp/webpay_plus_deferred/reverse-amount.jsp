<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>

<jsp:include page="../template/tpl-header.jsp" />

<body class="container">
    <jsp:include page="../template/navbar-webpayplus.jsp" />

    <div class="row">
        <div class="col">
            <h2>Reversar Monto Pre-autorizado</h2>
            A diferencia del m�todo refund, esta operaci�n act�a sobre los montos pre-autorizados y no sobre los montos ya capturados. Por lo tanto, esta operaci�n permitir� disminuir de manera directa el monto previamente autorizado, tanto de forma parcial (todas las veces que lo necesite), como total. Solo es soportado por tarjetas VISA/MASTERCARD (AMEX retornara un error 'Unsupported Operation').
        </div>
    </div><br>

    <div class="row">
        <div class="col-6">
            <h4>Petici�n</h4>
            Para reversar un monto pre-autorizado de una transacci�n necesitaremos el Token, Orden de compra, C�digo de autorizaci�n y monto a incrementar. Se hace de la siguiente manera.
        </div>
        <div class="col-6">
            <pre>
                <code class="language-java">
//inicio secci�n de importaci�n
import cl.transbank.common.IntegrationApiKeys;
import cl.transbank.common.IntegrationCommerceCodes;
import cl.transbank.common.IntegrationType;
import cl.transbank.webpay.common.WebpayOptions;
import cl.transbank.webpay.webpayplus.WebpayPlus;
import cl.transbank.webpay.responses.*;
//fin secci�n de importaci�n

WebpayPlus.Transaction tx = new WebpayPlus.Transaction(new WebpayOptions(IntegrationCommerceCodes.WEBPAY_PLUS_DEFERRED, IntegrationApiKeys.WEBPAY, IntegrationType.TEST));
ReversePreAuthorizedAmountResponse response = tx.reversePreAuthorizedAmount(tokenWs, buyOrder, authorizationCode, amount);
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

    <jsp:include page="../deferred_common/deferred-options.jsp" />
    <jsp:include page="../template/footer.jsp" />
</body>
</html>