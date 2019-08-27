<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Ejemplos Webpay - Transaccion Anulaci&oacute;n</title>
    <jsp:include page="template/header.jsp"/>
</head>

<body class="container">
    <nav aria-label="breadcrumb">
    <ol class="breadcrumb">
    <li class="breadcrumb-item"><a href="#">Ejemplos Webpay - Transaccion Anulaci&oacute;n</a></li>
    </ol>
    </nav>

    <div class="card">
    <div class="card-body col-sm-4">
        <form id="formulario" action="${details.get("refund-endpoint")}" method="POST">
                <legend>Formulario de Anulaci&oacute;n</legend>
                <div class="form-group">
                    <label>token_ws:</label><input name="token_ws" type="text" class="form-control"/>
                </div>
                <div class="form-group">
                    <label>amount:</label><input name="amount" type="text" class="form-control"/>
                </div>
                <input name="enviar" type="submit" value="Enviar" class="btn btn-primary"/>
        </form>
    </div>
    </div>
<br>
<a href=".">&laquo; volver a index</a>
    <jsp:include page="template/footer.jsp"/>
</body>
</html>