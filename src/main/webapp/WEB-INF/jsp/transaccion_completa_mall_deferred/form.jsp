<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>

<jsp:include page="../template/tpl-header.jsp" />

<body class="container">
    <jsp:include page="../template/navbar-txcompleta.jsp" />

    <div class="row">
        <div class="col">
            <h2>Formulario</h2>
            Para poder comenzar con la transacción primero necesitas obtener los datos de la tarjeta de crédito del tarjetahabiente.
        </div>
    </div><br>

    <div class="row">
        <div class="col-6">
            <h4>Formulario de pago</h4>
            Para comenzar debes obtener los datos de la tarjeta sobre la cual se desea generar una transacción.
        </div>
        <div class="col-6">

            <div class="card">
              <div class="card-body">
                <h6 class="card-title">Formulario de pago</h6>
                <form action="${details.get("create-endpoint")}" method="POST">
                    <div class="form-floating mb-3">
                      <input type="text" class="form-control" id="card_number" name="card_number" value="4051885600446623">
                      <label for="card_number">Número de tarjeta</label>
                    </div>
                    <div class="row">
                        <div class="col">
                          <div class="form-floating mb-3">
                            <input type="text" class="form-control" id="month" name="month" value="12">
                            <label for="month">Mes</label>
                          </div>
                        </div>
                        <div class="col">
                          <div class="form-floating mb-3">
                            <input type="text" class="form-control" id="year" name="year" value="29">
                            <label for="year">Año</label>
                          </div>
                        </div>
                        <div class="col">
                          <div class="form-floating mb-3">
                            <input type="text" class="form-control" id="cvv" name="cvv" value="123">
                            <label for="cvv">CVV</label>
                          </div>
                        </div>
                      </div>
                    <div class="form-floating mb-3">
                      <input type="text" class="form-control" id="amount" name="amount" value="${details.get("amount")}">
                      <label for="amount">Monto</label>
                    </div>

                  <button type="submit" class="btn btn-primary">PAGAR</button>
                </form>
              </div>
            </div>

        </div>
    </div><br>
    <br>

    <jsp:include page="../template/footer.jsp" />

</body>
</html>