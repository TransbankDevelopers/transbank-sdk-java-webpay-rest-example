<div class="row">
    <h4>Otras utilidades</h4>
    Luego de confirmada la transacción puedes Reembolsar (reversar o anular) el pago dependiendo de ciertas condiciones comerciales. También puedes consultar el estado de la transacción hasta 7 días después de realizada.
</div>
<br>
<div class="row">
    <div class="col-6">
        <form action="${details.get("status-endpoint")}" method="POST">
          <input type="hidden" name="token_ws" value="${details.get("token_ws")}">
          <button type="submit" class="btn btn-primary">CONSULTAR ESTADO</button>
        </form>
    </div>
    <div class="col-6">
        <div class="card">
          <div class="card-body">
            <h6 class="card-title">Formulario de reembolso</h6>
            <form action="${details.get("refund-endpoint")}" method="POST">
              <input type="hidden" name="token_ws" value="${details.get("token_ws")}">
              <div class="mb-3">
                  <label for="child_commerce_code" class="form-label">Código de tienda hija</label>
                  <input type="text" class="form-control" name="child_commerce_code" value="${details.get("child_commerce_code")}">
                </div>
                <div class="mb-3">
                  <label for="child_buy_order" class="form-label">Orden de compra de tienda hija</label>
                  <input type="text" class="form-control" name="child_buy_order" value="${details.get("child_buy_order")}">
                </div>
              <div class="mb-3">
                <label for="amount" class="form-label">Monto a reembolsar</label>
                <input type="text" class="form-control" name="amount" value="${details.get("amount")}">
              </div>
              <button type="submit" class="btn btn-primary">REEMBOLSAR</button>
            </form>
          </div>
        </div>
    </div>
</div>


