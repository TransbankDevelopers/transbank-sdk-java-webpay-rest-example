<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>

<jsp:include page="../template/tpl-header.jsp" />

<body class="container">
    <jsp:include page="../template/navbar-txcompleta.jsp" />

    <div class="row">
        <div class="col">
            <h2>Crear Transacci�n</h2>
            En este paso crearemos la transacci�n con el objetivo de obtener un identificador unico.
        </div>
    </div><br>

    <div class="row">
        <div class="col-6">
            <h4>Petici�n</h4>
            Para comenzar debes importar TransaccionCompleta y luego crear una transacci�n
        </div>
        <div class="col-6">
            <pre>
                <code class="language-java">

//inicio secci�n de importaci�n
import cl.transbank.common.IntegrationApiKeys;
import cl.transbank.common.IntegrationCommerceCodes;
import cl.transbank.common.IntegrationType;
import cl.transbank.webpay.common.WebpayOptions;
import cl.transbank.webpay.transaccioncompleta.FullTransaction;
import cl.transbank.webpay.transaccioncompleta.responses.*;
//fin secci�n de importaci�n

FullTransaction tx = new FullTransaction(new WebpayOptions(IntegrationCommerceCodes.TRANSACCION_COMPLETA_DEFERRED, IntegrationApiKeys.WEBPAY, IntegrationType.TEST));

String buyOrder = "buyOrder_" + getRandomNumber();
String sessionId = "sessionId_" + getRandomNumber();
String cardExpirationDate= year + "/" + month;
double amount = 1000;

FullTransactionCreateResponse response = tx.create(buyOrder, sessionId, amount, (short)cvv, cardNumber, cardExpirationDate);

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
        <h2>Lo siguiente ...</h2>
    </div><br>

    <div class="row">
        <div class="col-6">
            <form action="${details.get("commit-endpoint")}" method="POST">
              <input type="hidden" name="token" value="${details.get("token")}">
              <button type="submit" class="btn btn-primary">CONFIRMAR SIN CUOTAS</button>
            </form>
        </div>
        <div class="col-6">
            <div class="card">
              <div class="card-body">
                <h6 class="card-title">Formulario de cuotas</h6>
                <form action="${details.get("installments-endpoint")}" method="POST">
                  <input type="hidden" name="token" value="${details.get("token")}">
                  <div class="mb-3">
                    <label for="amount" class="form-label">N� de Cuotas</label>
                    <input type="text" class="form-control" name="installments" value="3">
                  </div>
                  <button type="submit" class="btn btn-primary">HACER CONSULTA DE CUOTAS</button>
                </form>
              </div>
            </div>
        </div>
    </div>





    <jsp:include page="../template/footer.jsp" />

</body>
</html>