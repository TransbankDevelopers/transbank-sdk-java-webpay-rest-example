<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>

<jsp:include page="../template/tpl-header.jsp" />

<body class="container">
    <jsp:include page="../template/navbar-txcompleta.jsp" />

    <div class="row">
        <div class="col">
            <h2>Estado de Transacci�n</h2>
            Puedes solicitar el estado de una transacci�n hasta 7 d�as despues de que haya sido realizada. No hay limite de solicitudes de este tipo, sin embargo, una vez pasados los 7 d�as ya no podr�s revisar su estado.
        </div>
    </div><br>

    <div class="row">
        <div class="col-6">
            <h4>Petici�n</h4>
            Necesitas el token de la transacci�n de la cual quieres obtener el estado y con ello llamar nuevamente a TransaccionCompleta.Transaction.
        </div>
        <div class="col-6">
            <pre>
                <code class="language-java">
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
MallFullTransactionStatusResponse response = tx.status(tokenWs);
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
            Transbank contestar� con lo siguiente. Si no lo hiciste ya, debes guardar esta informaci�n, lo �nico que debes validar es que response_code sea igual a cero.
        </div>
    </div><br>

    <jsp:include page="../template/footer.jsp" />
</body>
</html>