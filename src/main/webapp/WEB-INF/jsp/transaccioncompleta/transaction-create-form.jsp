<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
        <head>
        <jsp:include page="../template/header.jsp"/>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Transaccion Completa - Crear Transaccion</title>
        </head>

        <body class="container">
        <nav aria-label="breadcrumb">
        <ol class="breadcrumb">
        <li class="breadcrumb-item"><a href="#">Transaccion Completa - Crear Transaccion</a></li>
        <li class="breadcrumb-item active" aria-current="page">Step: <strong>Create Transaction</strong></li>
        </ol>
        </nav>
        <div class="card">
        <div class="card-body col-sm-4">

                <form id="formulario" action="/fulltransaction/create" method="POST">
                        <legend>Formulario Creacion</legend>
                        <div class="form-group">
                        <label>buyOrder:</label><input name="buyOrder" type="text" class="form-control" value="${model.buyOrder}"/>
                        </div>
                        <div class="form-group">
                        <label>sessionId:</label><input name="sessionId" type="text" class="form-control" value="${model.sessionId}"/>
                        </div>
                        <div class="form-group">
                        <label>amount:</label><input name="amount" type="text" class="form-control" value="${model.amount}"/>
                        </div>
                        <div class="form-group">
                        <label>cardNumber:</label><input name="cardNumber" type="text" class="form-control" value="${model.cardNumber}"/>
                        </div>
                        <div class="form-group">
                        <label>cardExpirationDate:</label><input name="cardExpirationDate" type="text" class="form-control" value="${model.cardExpirationDate}"/>
                        </div>
                        <div class="form-group">
                        <label>cvv:</label><input name="cvv" type="text" class="form-control" value="${model.cvv}"/>
                        </div>
                        <input name="enviar" type="submit" value="Enviar" class="btn btn-primary" />
                </form>
        </div>
        </div>
        <br>
        <a href="/">&laquo; volver a index</a>
        <jsp:include page="../template/footer.jsp"/>
        </body>
</html>