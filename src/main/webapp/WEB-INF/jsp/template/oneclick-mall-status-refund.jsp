<div class="row">
    <h4>Otras utilidades</h4>
    Luego de autorizada la transacci�n puedes Reembolsar (reversar o anular) el pago dependiendo de ciertas condiciones comerciales. Tambi�n puedes consultar el estado de la transacci�n hasta 7 d�as despu�s de realizada.
</div>
<br>
<div class="row">
    <div class="col-6">
        <form action="${details.get("status-endpoint")}" method="POST">
          <input type="hidden" name="buy_order" value="${details.get("buy_order")}">
          <button type="submit" class="btn btn-primary">CONSULTAR ESTADO</button>
        </form>
    </div>
    <div class="col-6">
        <div class="card">
          <div class="card-body">
            <h6 class="card-title">Formulario de reembolso</h6>
            <form action="${details.get("refund-endpoint")}" method="POST">
                <div class="form-floating mb-3">
                  <input type="text" class="form-control" id="buy_order" name="buy_order" value="${details.get("buy_order")}">
                  <label for="buy_order">Orden de compra</label>
                </div>
                <div class="form-floating mb-3">
                  <input type="text" class="form-control" id="child_buy_order" name="child_buy_order" value="${details.get("child_buy_order")}">
                  <label for="child_buy_order">Orden de compra de tienda hija</label>
                </div>
                <div class="form-floating mb-3">
                  <input type="text" class="form-control" id="child_commerce_code" name="child_commerce_code" value="${details.get("child_commerce_code")}">
                  <label for="child_commerce_code">C�digo de Comercio tienda hija</label>
                </div>
                <div class="form-floating mb-3">
                  <input type="text" class="form-control" id="amount" name="amount" value="${details.get("amount")}">
                  <label for="amount">Monto a reembolsar</label>
                </div>
              <button type="submit" class="btn btn-primary">REEMBOLSAR</button>
            </form>
          </div>
        </div>
    </div>
</div>


