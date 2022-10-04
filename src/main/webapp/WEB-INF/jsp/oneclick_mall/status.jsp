<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>

<jsp:include page="../template/tpl-header.jsp" />

<body class="container">
    <jsp:include page="../template/navbar-webpayplus.jsp" />

    <div class="row">
        <div class="col">
            <h2>Estado de Transacción Mall</h2>
            Puedes solicitar el estado de una transacción hasta 7 días despues de que haya sido realizada. No hay limite de solicitudes de este tipo, sin embargo, una vez pasados los 7 días ya no podrás revisar su estado.
        </div>
    </div><br>

    <div class="row">
        <div class="col-6">
            <h4>Petición</h4>
            Necesitas el token de la transacción de la cual quieres obtener el estado y con ello llamar nuevamente a WebpayPlus.MallTransaction.
        </div>
        <div class="col-6">
            <pre>
                <code class="language-java">
//inicio sección de importación
import cl.transbank.common.IntegrationApiKeys;
import cl.transbank.common.IntegrationCommerceCodes;
import cl.transbank.common.IntegrationType;
import cl.transbank.webpay.common.WebpayOptions;
import cl.transbank.webpay.oneclick.Oneclick;
import cl.transbank.webpay.oneclick.model.*;
import cl.transbank.webpay.oneclick.responses.*;
//fin sección de importación

Oneclick.MallTransaction tx = new Oneclick.MallTransaction(new WebpayOptions(IntegrationCommerceCodes.ONECLICK_MALL, IntegrationApiKeys.WEBPAY, IntegrationType.TEST));
OneclickMallTransactionStatusResponse response = tx.status(buyOrder);
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
            Transbank contestará con lo siguiente. Si no lo hiciste ya, debes guardar esta información, lo único que debes validar es que response_code sea igual a cero.
        </div>
    </div><br>

    <jsp:include page="../template/footer.jsp" />
</body>
</html>