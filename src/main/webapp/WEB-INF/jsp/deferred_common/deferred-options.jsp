<form action="${details.get("capture-endpoint")}" method="POST">
    <input type="hidden" name="token_ws" value="${details.get("token_ws")}">
    <input type="hidden" name="buy_order" value="${details.get("buy_order")}">
    <input type="hidden" name="child_buy_order" value="${details.get("child_buy_order")}">
    <input type="hidden" name="child_commerce_code" value="${details.get("child_commerce_code")}">
    <input type="hidden" name="authorization_code" value="${details.get("authorization_code")}">
    <div class="row">
        <div class="col-4">
            <button type="submit" class="btn btn-primary">CAPTURAR</button>
        </div>
        <div class="col-4">
             <div class="form-floating mb-3">
               <input type="text" class="form-control" name="amount" id="amount" value="${details.get("amount")}">
               <label for="amount">Monto a capturar</label>
             </div>
        </div>
        <div class="col-4">
            Capturar la transacci�n para realmente capturar el dinero que habia sido previamente reservado.
        </div>
    </div>
</form>
