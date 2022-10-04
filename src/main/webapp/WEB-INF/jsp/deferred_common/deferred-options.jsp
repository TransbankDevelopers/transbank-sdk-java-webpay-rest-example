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
            Capturar la transacción para realmente capturar el dinero que habia sido previamente reservado.
        </div>
    </div>
</form>

<form action="${details.get("increase-endpoint")}" method="POST">
    <input type="hidden" name="token_ws" value="${details.get("token_ws")}">
    <input type="hidden" name="buy_order" value="${details.get("buy_order")}">
    <input type="hidden" name="child_buy_order" value="${details.get("child_buy_order")}">
    <input type="hidden" name="child_commerce_code" value="${details.get("child_commerce_code")}">
    <input type="hidden" name="authorization_code" value="${details.get("authorization_code")}">
    <div class="row">
        <div class="col-4">
            <button type="submit" class="btn btn-primary">AUMENTAR MONTO</button>
        </div>
        <div class="col-4">
             <div class="form-floating mb-3">
               <input type="text" class="form-control" name="amount" id="amount" value="1000">
               <label for="amount">Monto a aumentar</label>
             </div>
        </div>
        <div class="col-4">
            Aumentar el monto previamente pre-autorizado (todas las veces que se necesite).
        </div>
    </div>
</form>

<form action="${details.get("increase-date-endpoint")}" method="POST">
    <input type="hidden" name="token_ws" value="${details.get("token_ws")}">
    <input type="hidden" name="buy_order" value="${details.get("buy_order")}">
    <input type="hidden" name="child_buy_order" value="${details.get("child_buy_order")}">
    <input type="hidden" name="child_commerce_code" value="${details.get("child_commerce_code")}">
    <input type="hidden" name="authorization_code" value="${details.get("authorization_code")}">
    <input type="hidden" name="amount" value="${details.get("amount")}">
    <div class="row">
        <div class="col-4">
            <button type="submit" class="btn btn-primary">AUMENTAR PLAZO</button>
        </div>
        <div class="col-8">
            Aumentar el plazo pre-autorizado (todas las veces que se necesite). El plazo de captura en VISA y MASTERCARD para los Rent-a-car y los hoteles son 30 días, y en AMEX solo 7 días (no tiene posiblidad de aumento de fecha y de monto).
        </div>
    </div>
</form>

<form action="${details.get("reverse-endpoint")}" method="POST">
    <input type="hidden" name="token_ws" value="${details.get("token_ws")}">
    <input type="hidden" name="buy_order" value="${details.get("buy_order")}">
    <input type="hidden" name="child_buy_order" value="${details.get("child_buy_order")}">
    <input type="hidden" name="child_commerce_code" value="${details.get("child_commerce_code")}">
    <input type="hidden" name="authorization_code" value="${details.get("authorization_code")}">
    <div class="row">
        <div class="col-4">
            <button type="submit" class="btn btn-primary">REVERSAR MONTO</button>
        </div>
        <div class="col-4">
             <div class="form-floating mb-3">
               <input type="text" class="form-control" name="amount" id="amount" value="${details.get("amount")}">
               <label for="amount">Monto a reversar</label>
             </div>
        </div>
        <div class="col-4">
            Reversar el monto previamente pre-autorizado (todas las veces que se necesite).
        </div>
    </div>
</form>

<form action="${details.get("history-endpoint")}" method="POST">
    <input type="hidden" name="token_ws" value="${details.get("token_ws")}">
    <input type="hidden" name="buy_order" value="${details.get("buy_order")}">
    <input type="hidden" name="child_buy_order" value="${details.get("child_buy_order")}">
    <input type="hidden" name="child_commerce_code" value="${details.get("child_commerce_code")}">
    <input type="hidden" name="authorization_code" value="${details.get("authorization_code")}">
    <input type="hidden" name="amount" value="${details.get("amount")}">
    <div class="row">
        <div class="col-4">
            <button type="submit" class="btn btn-primary">HISTORIAL CAPTURA DIFERIDA</button>
        </div>
        <div class="col-8">
            Retorna el historial de operaciones ejecutadas sobre una pre-autorización de captura diferida.
        </div>
    </div>
</form>

