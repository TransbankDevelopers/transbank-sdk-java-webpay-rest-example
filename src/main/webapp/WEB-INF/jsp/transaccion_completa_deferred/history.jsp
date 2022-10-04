<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>

<jsp:include page="../template/tpl-header.jsp" />

<body class="container">
    <jsp:include page="../template/navbar-txcompleta.jsp" />

    <div class="row">
        <div class="col">
            <h2>Historial de transacciones captura diferida</h2>
            Permite visualizar el historial de operaciones ejecutadas sobre una pre-autorización de captura diferida.
        </div>
    </div><br>

    <div class="row">
        <div class="col-6">
            <h4>Petición</h4>
            Para visualizar el historial de transacciones necesitaremos el Token. Se hace de la siguiente manera.
        </div>
        <div class="col-6">
            <pre>
                <code class="language-java">
//inicio sección de importación
import cl.transbank.common.IntegrationApiKeys;
import cl.transbank.common.IntegrationCommerceCodes;
import cl.transbank.common.IntegrationType;
import cl.transbank.webpay.common.WebpayOptions;
import cl.transbank.webpay.responses.*;
import cl.transbank.webpay.transaccioncompleta.FullTransaction;
//fin sección de importación

FullTransaction tx = new FullTransaction(new WebpayOptions(IntegrationCommerceCodes.TRANSACCION_COMPLETA_DEFERRED, IntegrationApiKeys.WEBPAY, IntegrationType.TEST));
List<DeferredCaptureHistoryResponse> response = tx.deferredCaptureHistory(tokenWs);
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
            Transbank contestará con lo siguiente.
        </div>
    </div><br>

    <jsp:include page="../deferred_common/deferred-options.jsp" />
    <jsp:include page="../template/footer.jsp" />
</body>
</html>