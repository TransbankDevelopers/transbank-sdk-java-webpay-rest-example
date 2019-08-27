<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Ejemplos Oneclick - Estado de la transacci&oacute;n</title>
    <jsp:include page="../template/header.jsp"/>
</head>

<body class="container">
    <nav aria-label="breadcrumb">
    <ol class="breadcrumb">
    <li class="breadcrumb-item"><a href="#">Ejemplos Webpay - Transaccion Anulaci&oacute;n</a></li>
    </ol>
    </nav>
    <div class="card">
    <div class="card-body col-sm-4">
        <form id="formulario" action="/oneclick-mall/status" method="POST">
                <legend>Formulario de Anulaci&oacute;n</legend>
                <div class="form-group">
                    <label>buy_order:</label><input name="buy_order" type="text" class="form-control"/>
                </div>
                <input name="enviar" type="submit" value="Enviar" class="btn btn-primary"/>
        </form>
    </div>
    </div>
<br>
<a href="/">&laquo; volver a index</a>

</body>
</html>