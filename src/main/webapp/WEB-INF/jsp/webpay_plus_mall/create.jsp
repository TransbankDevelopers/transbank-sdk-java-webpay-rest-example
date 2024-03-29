<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>

<jsp:include page="../template/tpl-header.jsp" />

<body class="container">
    <jsp:include page="../template/navbar-webpayplus.jsp" />

    <div class="row">
        <div class="col">
            <h2>Crear Transacci�n Mall</h2>
            En este paso crearemos la transacci�n con el objetivo de obtener un identificador unico y poder en el siguiente paso redirigir al Tarjetahabiente hacia el formulario de pago
        </div>
    </div><br>

    <div class="row">
        <div class="col-6">
            <h4>Petici�n</h4>
            Para comenzar debes importar WebpayPlus y luego crear una transacci�n mall
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

WebpayPlus.MallTransaction tx = new WebpayPlus.MallTransaction(new WebpayOptions(IntegrationCommerceCodes.WEBPAY_PLUS_MALL, IntegrationApiKeys.WEBPAY, IntegrationType.TEST));

String buyOrder = "buyOrder_" + getRandomNumber();
String buyOrderMallOne = "childBuyOrder_" + getRandomNumber();
String buyOrderMallTwo = "childBuyOrder_" + getRandomNumber();
String sessionId = "sessionId_" + getRandomNumber();
double amountMallOne = 1000;
double amountMallTwo = 1000;
String returnUrl = request.getRequestURL().toString().replace("create","commit");

String mallOneCommerceCode = IntegrationCommerceCodes.WEBPAY_PLUS_MALL_CHILD1;
String mallTwoCommerceCode = IntegrationCommerceCodes.WEBPAY_PLUS_MALL_CHILD2;
final MallTransactionCreateDetails mallDetails = MallTransactionCreateDetails.build()
        .add(amountMallOne, mallOneCommerceCode, buyOrderMallOne)
        .add(amountMallTwo, mallTwoCommerceCode, buyOrderMallTwo);

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
            Una vez creada la transacci�n, estos ser�n los datos de respuesta
        </div>
    </div><br>


    <div class="row">
        <div class="col-6">
            <h4>Por �ltimo</h4>
            Debes utilizar estos datos para crear un formulario
        </div>
        <div class="col-6">
            <pre>
                <code class="language-html">
&lt;form action="${details.get("token")}" method="POST">
   &lt;input type="hidden" name="token_ws" value="${details.get("token")}"/>
   &lt;input type="submit" value="Pagar"/>
&lt;/form>
                </code>
            </pre>
        </div>
    </div><br>


    <br>
    <div class="row">
        <h2>Ejemplo</h2>
    </div>

    <div class="row">
        <div class="col-6">
            <h4>Creamos la transacci�n</h4>
            Usando los siguientes datos
            <table class="table table-bordered">
                <tr>
                    <th>Campo</th>
                    <th>Valor</th>
                </tr>
                <tr>
                    <td>Orden de compra (buyOrder)</td>
                    <td>${details.get("buyOrder")}</td>
                </tr>
                <tr>
                    <td>ID de sesi�n (sessionid)</td>
                    <td>${details.get("sessionId")}</td>
                </tr>
                <tr>
                    <td>Detalle 1 (details)</td>
                    <td>
                        Monto: ${details.get("amount1")}<br>
                        C�digo de Comercio: ${details.get("childCommerceCode1")}<br>
                        Orden de Compra: ${details.get("childBuyOrder1")} <br>
                    </td>
                </tr>
                <tr>
                    <td>Detalle 2 (details)</td>
                    <td>
                        Monto: ${details.get("amount2")}<br>
                        C�digo de Comercio: ${details.get("childCommerceCode2")}<br>
                        Orden de Compra: ${details.get("childBuyOrder2")} <br>
                    </td>
                </tr>
                <tr>
                    <td>URL de retorno (returnUrl)</td>
                    <td>${details.get("returnUrl")}</td>
                </tr>
            </table>
        </div>
        <div class="col-6">
            <h4>Por �ltimo</h4>
            Con la respuesta del servicio creamos el formulario, para efectos del ejemplo haremos el campo token_ws visible
            <div class="card">
              <div class="card-body">
                <h6 class="card-title">Formulario de redirecci�n</h6>
                <form action="${details.get("url")}" method="POST">
                  <div class="mb-3">
                    <label for="token_ws" class="form-label">token_ws</label>
                    <input type="text" class="form-control" name="token_ws" value="${details.get("token")}">
                  </div>
                  <button type="submit" class="btn btn-primary">PAGAR</button>
                </form>
              </div>
            </div>

        </div>
    </div><br>

    <jsp:include page="../template/footer.jsp" />

</body>
</html>