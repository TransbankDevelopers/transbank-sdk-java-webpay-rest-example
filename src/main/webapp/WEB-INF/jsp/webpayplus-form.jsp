<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Ejemplos Webpay - Crear Transaccionn</title>
    <jsp:include page="template/header.jsp"/>
</head>

<body class="container">
    <nav aria-label="breadcrumb">
    <ol class="breadcrumb">
    <li class="breadcrumb-item"><a href="#">Ejemplos Webpay - Crear Transaccion</a></li>
    </ol>
    </nav>

    <div class="card">
    <div class="card-body col-sm-4">
        <form id="formulario" action="webpayplus" method="POST">
                <legend>Formulario de Creaci&oacute;n</legend>
                <div class="form-group">
                    <label>sessionId:</label><input name="sessionId" type="text" class="form-control" value="${model.sessionId}"/>
                </div>
                <div class="form-group">
                <label>buyOrder:</label><input name="buyOrder" type="text" class="form-control" value="${model.buyOrder}"/>
                </div>
                <div class="form-group">
                <label>returnUrl:</label><input name="returnUrl" type="text" class="form-control" value="${model.returnUrl}"/>
                </div>
                <div class="form-group">
                    <label>amount:</label><input name="amount" type="text" class="form-control" value="${model.amount}"/>
                </div>
                <input name="enviar" type="submit" value="Enviar" class="btn btn-primary" />
        </form>
    </div>
    </div>
<br>
<a href="/">&laquo; volver a index</a>
    <jsp:include page="template/footer.jsp"/>
</body>
</html>