<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>

<jsp:include page="../template/tpl-header.jsp" />

<body class="container">
    <jsp:include page="../template/navbar-oneclick.jsp" />

    <div class="row">
        <div class="col">
            <h2>Autorizar transacción</h2>
            En este paso autorizaremos una transacción en la tarjeta inscrita.
        </div>
    </div><br>

    <div class="row">
        <div class="col-6">
            <h4>Petición</h4>
            Una vez que ya tenemos el username y el tbk_user podemos autorizar transacciones en la tarjeta inscrita.
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
Oneclick.MallTransaction tx = new Oneclick.MallTransaction(new WebpayOptions(IntegrationCommerceCodes.ONECLICK_MALL_DEFERRED, IntegrationApiKeys.WEBPAY, IntegrationType.TEST));


double childAmount1 = 1000;
double childAmount2 = 1000;
String buyOrder = "buyOrder_" + getRandomNumber();
String buyOrderMallOne = "childBuyOrder1_" + getRandomNumber();
String buyOrderMallTwo = "childBuyOrder2_" + getRandomNumber();
String childCommerceCode1 = IntegrationCommerceCodes.ONECLICK_MALL_CHILD1;
String childCommerceCode2 = IntegrationCommerceCodes.ONECLICK_MALL_CHILD2;

MallTransactionCreateDetails mallDetails = MallTransactionCreateDetails.build()
        .add(childAmount1, childCommerceCode1, buyOrderMallOne, (byte) 1)
        .add(childAmount2, childCommerceCode2, buyOrderMallTwo, (byte) 1);

OneclickMallTransactionAuthorizeResponse response = tx.authorize(username, tbkUser, buyOrder, mallDetails);

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
            Una vez autorizada la transacción, estos serán los datos de respuesta
        </div>
    </div><br>

    <jsp:include page="../deferred_common/deferred-options.jsp" />
    <jsp:include page="../template/footer.jsp" />

</body>
</html>