<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Ejemplos Webpay Plus - Crear Transaccion</title>
    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
</head>

<body class="container">
<h1>Ejemplos Transaccion Completa - Crear Transaccion</h1>

<h2>Step: Create Transaction</h2>

<div style="background-color:lightyellow;">
    <h3>request</h3>
    <c:forEach var="detail" items="${details}">
        [<c:out value="${detail.key}"/>] = <c:out value="${detail.value}"/>,
    </c:forEach>
</div>
<div style="background-color:lightgrey;">
    <h3>result</h3>
     [token_ws] = ${details.get("token")}
</div>
    <div class="card">
        <div class="card-body">
        <form action="/fulltransaction/status" method="POST">
            <div class="form-row">
                <input type="hidden" name="token" value="${details.get("token")}">
                    <div class="form-group col-sm-4">
                        <button type="submit" class="btn btn-primary">Consultar Status</button>
                    </div>
            </div>
        </form>
        </div>
    </div>

    <br>
    <div class="card">
        <div class="card-body">
            <form action="/fulltransaction/installments" method="POST">
                <div class="form-row">
                    <input type="hidden" name="token" value="${details.get("token")}">
                    <div class="form-group col-sm-4">
                    <label for="installmentsNumber">Cantidad de Cuotas</label>
                        <select class="form-control" name="installmentsNumber">
                        <option value="2">2</option>
                        <option value="3">3</option>
                        <option value="4">4</option>
                        <option value="5">5</option>
                        <option value="6">6</option>
                        <option value="7">7</option>
                        <option value="8">8</option>
                        <option value="9">9</option>
                        <option value="10">10</option>
                        <option value="11">11</option>
                        <option value="12">12</option>
                        </select>
                    <small id="emailHelp" class="form-text text-muted">seleccione la cantidad de cuotas o continuar.</small>
                    </div>

                </div>
                <button type="submit" class="btn btn-primary">Continuar</button>
            </form>
        </div>
    </div>

    <br>
<a href="/">&laquo; volver a index</a>
    <!-- jQuery first, then Popper.js, then Bootstrap JS -->
    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
</body>
</html>