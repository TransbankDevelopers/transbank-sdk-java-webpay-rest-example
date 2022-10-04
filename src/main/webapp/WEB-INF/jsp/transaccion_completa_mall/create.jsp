<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>

<jsp:include page="../template/tpl-header.jsp" />

<body class="container">
    <jsp:include page="../template/navbar-txcompleta.jsp" />

    <div class="row">
        <div class="col">
            <h2>Crear Transacción</h2>
            En este paso crearemos la transacción con el objetivo de obtener un identificador unico.
        </div>
    </div><br>

    <div class="row">
        <div class="col-6">
            <h4>Petición</h4>
            Para comenzar debes importar TransaccionCompleta y luego crear una transacción
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
import cl.transbank.webpay.transaccioncompleta.MallFullTransaction;
import cl.transbank.webpay.transaccioncompleta.model.MallTransactionCommitDetails;
import cl.transbank.webpay.transaccioncompleta.responses.*;
//fin sección de importación

MallFullTransaction tx = new MallFullTransaction(new WebpayOptions(IntegrationCommerceCodes.TRANSACCION_COMPLETA_MALL, IntegrationApiKeys.WEBPAY, IntegrationType.TEST));

MallFullTransactionCreateResponse response = tx.create(buyOrder, sessionId, cardNumber, cardExpirationDate, MallTransactionCreateDetails.build()
                    .add(amount, childCommerceCode, childBuyOrder), (short) cvv);

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
            Una vez creada la transacción, estos serán los datos de respuesta
        </div>
    </div><br>

    <div class="row">
        <h2>Lo siguiente ...</h2>
    </div><br>

    <div class="row">
        <div class="col-6">
            <div class="card">
              <div class="card-body">
                <h6 class="card-title">Formulario confirmación sin cuotas</h6>
                <form action="${details.get("commit-endpoint")}" method="POST">
                  <input type="hidden" name="token" value="${details.get("token")}">
                  <div class="mb-3">
                    <label for="child_commerce_code" class="form-label">Código de tienda hija</label>
                    <input type="text" class="form-control" name="child_commerce_code" value="${details.get("child_commerce_code")}">
                  </div>
                  <div class="mb-3">
                    <label for="child_buy_order" class="form-label">Orden de compra de tienda hija</label>
                    <input type="text" class="form-control" name="child_buy_order" value="${details.get("child_buy_order")}">
                  </div>
                  <button type="submit" class="btn btn-primary">CONFIRMAR SIN CUOTAS</button>
                </form>
              </div>
            </div>
        </div>
        <div class="col-6">
            <div class="card">
              <div class="card-body">
                <h6 class="card-title">Formulario de cuotas</h6>
                <form action="${details.get("installments-endpoint")}" method="POST">
                  <input type="hidden" name="token" value="${details.get("token")}">
                  <div class="mb-3">
                    <label for="child_commerce_code" class="form-label">Código de tienda hija</label>
                    <input type="text" class="form-control" name="child_commerce_code" value="${details.get("child_commerce_code")}">
                  </div>
                  <div class="mb-3">
                    <label for="child_buy_order" class="form-label">Orden de compra de tienda hija</label>
                    <input type="text" class="form-control" name="child_buy_order" value="${details.get("child_buy_order")}">
                  </div>
                  <div class="mb-3">
                    <label for="installments" class="form-label">Nº de Cuotas</label>
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