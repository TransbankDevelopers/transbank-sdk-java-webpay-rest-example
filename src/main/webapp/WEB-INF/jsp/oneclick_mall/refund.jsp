<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>

<jsp:include page="../template/tpl-header.jsp" />

<body class="container">
    <jsp:include page="../template/navbar-webpayplus.jsp" />

    <div class="row">
        <div class="col">
            <h2>Reembolso de Transacci�n Mall</h2>
            Puedes solicitar el estado de una transacci�n hasta 7 d�as despues de que haya sido realizada. No hay limite de solicitudes de este tipo, sin embargo, una vez pasados los 7 d�as ya no podr�s revisar su estado.
        </div>
    </div><br>

    <div class="row">
        <div class="col-6">
            <h4>Petici�n</h4>
            Necesitas el token de la transacci�n, el monto que quieres reversar, el c�digo de comercio de la tienda hijo y el orden de compra del detalle de la transacci�n, si anulas el monto total puede ser una Reversa o Anulaci�n dependiendo de ciertas condiciones o una Anulaci�n parcial si el monto es menor al total.
             No es posible hacer ni Anulaciones ni Anulaciones parciales en tarjetas que no sean de cr�dito. Tampoco es posible realizar reembolsos de compras en cuotas.
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
import cl.transbank.webpay.webpayplus.responses.*;
import cl.transbank.webpay.webpayplus.WebpayPlus;
//fin secci�n de importaci�n

Oneclick.MallTransaction tx = new Oneclick.MallTransaction(new WebpayOptions(IntegrationCommerceCodes.ONECLICK_MALL, IntegrationApiKeys.WEBPAY, IntegrationType.TEST));
OneclickMallTransactionRefundResponse response = tx.refund(buyOrder, childCommerceCode, childBuyOrder, amount);
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
            Transbank contestar� con el resultado de la reversa o anulaci�n.
        </div>
    </div><br>

    <jsp:include page="../template/footer.jsp" />
</body>
</html>