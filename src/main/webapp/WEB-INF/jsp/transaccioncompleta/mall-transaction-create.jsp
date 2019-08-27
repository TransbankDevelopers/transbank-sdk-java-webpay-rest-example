<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Ejemplos Transaccion Completa Mall  - Crear Transaccion</title>
    <jsp:include page="../template/header.jsp"/>>
</head>

<body class="container">

<nav aria-label="breadcrumb">
    <ol class="breadcrumb">
        <li class="breadcrumb-item"><a href="#">Ejemplos Transaccion Completa Mall - Crear Transaccion</a></li>
    <li class="breadcrumb-item active" aria-current="page">Step: <strong>Create Transaction</strong></li>
    </ol>
</nav>

<div class="alert alert-warning" role="alert">
    <h3>request</h3>
    <c:forEach var="detail" items="${details}">
        [<c:out value="${detail.key}"/>] = <c:out value="${detail.value}"/>,
    </c:forEach>
</div>
<div class="alert alert-primary" role="alert">
    <h3>result</h3>
     [token_ws] = ${details.get("token")}
</div>
    <div class="card col-sm-4">
        <div class="card-body">
        <form action="/mallfulltransaction/status" method="POST">
            <div class="form-row">
                <input type="hidden" name="token" value="${details.get("token")}">
                    <div class="form-group ">
                        <button type="submit" class="btn btn-primary">Consultar Status</button>
                    </div>
            </div>
        </form>
        </div>
    </div>

    <br>
    <div class="card col-sm-4">
        <div class="card-body ">
            <form action="/mallfulltransaction/installments" method="POST">
                <input type="hidden" name="token" value="${details.get("token")}">

                <div class="form-group">
                <label for="installmentsNumber">Cantidad de Cuotas</label>
                    <select class="form-control" name="installmentsNumber" id="installmentsNumber">
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
                <small id="emailHelp" class="form-text text-muted">Seleccione la cantidad de cuotas o continuar.</small>
                </div>
                <div class="form-group">
                <label for="buyOrder">Orden de Compra</label>
                    <input class="form-control" name="buyOrder" id="buyOrder" type="text" value="${details.get("buyOrder")}"/>
                </div>
                <div class="form-group">
                <label for="commerceCode">Codigo de comercio</label>
                <input class="form-control" name="commerceCode" id="commerceCode" type="text" value="${details.get("commerceCode")}"/>
                </div>

                <button type="submit" class="btn btn-success">Continuar</button>
            </form>

        </div>
    </div>

    <br>
<a href="/">&laquo; volver a index</a>
    <jsp:include page="../template/footer.jsp"/>
</body>
</html>