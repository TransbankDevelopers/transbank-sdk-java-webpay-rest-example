<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Ejemplos Webpay Plus Mall- Crear Transaccionn</title>
    <jsp:include page="template/header.jsp"/>
</head>

<body class="container">
    <nav aria-label="breadcrumb">
    <ol class="breadcrumb">
    <li class="breadcrumb-item"><a href="#">Ejemplos Webpay Plus Mall - Crear Transaccion</a></li>
    </ol>
    </nav>

    <div class="card">
    <div class="card-body col-sm-6">
        <form id="formulario" action="webpayplusmall" method="POST">
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

                <legend>Comercio 1</legend>
                <div class="form-group">
                <label>buyOrder1:</label><input name="buyOrder1" type="text" class="form-control" value="${model.buyOrder1}"/>
                </div>
                <div class="form-group">
                <label>commerceCode1:</label><input name="commerceCode1" type="text" class="form-control" value="${model.commerceCode1}"/>
                </div>
                <div class="form-group">
                <label>amount1:</label><input name="amount1" type="text" class="form-control" value="${model.amount1}"/>
                </div>
                <legend>Comercio 2</legend>
                <div class="form-group">
                <label>buyOrder2:</label><input name="buyOrder2" type="text" class="form-control" value=""/>
                </div>
                <div class="form-group">
                <label>commerceCode2:</label><input name="commerceCode1" type="text" class="form-control" value=""/>
                </div>
                <div class="form-group">
                <label>amount2:</label><input name="amount2" type="text" class="form-control" value=""/>
                </div>
                <input name="enviar" type="submit" value="Enviar" class="btn btn-primary" />
        </form>
    </div>
    </div>
<br>
<a href=".">&laquo; volver a index</a>
    <jsp:include page="template/footer.jsp"/>
</body>
</html>