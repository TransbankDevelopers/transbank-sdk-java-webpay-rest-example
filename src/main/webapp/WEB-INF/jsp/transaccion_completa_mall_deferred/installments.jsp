<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>

<jsp:include page="../template/tpl-header.jsp" />

<body class="container">
    <jsp:include page="../template/navbar-txcompleta.jsp" />

    <div class="row">
        <div class="col">
            <h2>Consulta de cuotas</h2>
            En este paso haremos una consulta de cuotas para poder saber sus condiciones. Este paso es opcional, se utiliza solo en el caso de que quieras utilizar cuotas
        </div>
    </div><br>

        <div class="row">
            <div class="col-6">
                <h4>Petición</h4>
                 Para hacer la consulta de cuotas debemos enviar los siguientes datos.
            </div>
            <div class="col-6">
                <pre>
                    <code class="language-json">
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

    MallFullTransaction tx = new MallFullTransaction(new WebpayOptions(IntegrationCommerceCodes.TRANSACCION_COMPLETA_MALL_DEFERRED, IntegrationApiKeys.WEBPAY, IntegrationType.TEST));
    MallFullTransactionInstallmentsDetails iDetails = MallFullTransactionInstallmentsDetails.build().add(childCommerceCode, childBuyOrder, installments);
    MallFullTransactionInstallmentsResponse response = tx.installments(token, iDetails);
                    </code>
                </pre>
            </div>

        </div><br>





    <div class="row">
        <div class="col-6">
            <h4>Respuesta</h4>
            Una vez hecha la consulta de cuotas, estos serán los datos de respuesta.
        </div>
        <div class="col-6">
            <pre>
                <code class="language-json">
${details.get("resp")}
                </code>
            </pre>
        </div>
    </div><br>

    <div class="row">
        <h2>Lo siguiente...</h2>
    </div><br>

    <div class="row">
        <h4>Confirmar transacción </h4>
        Para confirmar una transacción debemos pasar los siguentes datos.
    </div><br>

    <div class="row">
        <div class="col-3">
            <h4>Confirmar transacción </h4>
             Para confirmar una transacción debemos pasar los siguentes datos.
        </div>
        <div class="col-9">
            <div class="card">
              <div class="card-body">
                <h6 class="card-title">Formulario confirmación con cuotas</h6>
                <form action="${details.get("commit-endpoint")}" method="POST">
                  <div class="mb-3">
                      <label for="token" class="form-label">Token</label>
                      <input type="text" class="form-control" id="token" name="token" value="${details.get("token")}">
                  </div>
                  <div class="mb-3">
                      <label for="child_commerce_code" class="form-label">Código de tienda hija</label>
                      <input type="text" class="form-control" name="child_commerce_code" value="${details.get("child_commerce_code")}">
                    </div>
                    <div class="mb-3">
                      <label for="child_buy_order" class="form-label">Orden de compra de tienda hija</label>
                      <input type="text" class="form-control" name="child_buy_order" value="${details.get("child_buy_order")}">
                    </div>
                  <div class="mb-3">
                    <label for="id_query_installments" class="form-label">ID de consulta de cuotas (Opcional)</label>
                    <input type="text" class="form-control" id="id_query_installments" name="id_query_installments" value="${details.get("id_query_installments")}">
                 </div>
                  <div class="form-check form-switch">
                    <input class="form-check-input" type="checkbox" id="grace_period" name="grace_period" value="1">
                    <label class="form-check-label" for="grace_period">Periodo de gracia (Opcional)</label>
                  </div>
                  <div class="mb-3">
                    <label for="deferred_period_index" class="form-label">Indice de periodo diferido (Opcional)</label>
                    <input type="text" class="form-control" id="deferred_period_index" name="deferred_period_index">
                   </div>
                  <button type="submit" class="btn btn-primary">CONFIRMAR TRANSACCIÓN</button>
                </form>
              </div>
            </div>
        </div>
    </div>

    <jsp:include page="../template/footer.jsp" />

</body>
</html>