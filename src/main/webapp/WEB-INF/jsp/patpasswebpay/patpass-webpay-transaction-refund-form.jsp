<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
        <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Ejemplos Patpass Webpay - Transaccion Anulaci&oacute;n</title>
        <!-- Bootstrap CSS -->
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
        </head>

        <body class="container">
        <nav aria-label="breadcrumb">
        <ol class="breadcrumb">
        <li class="breadcrumb-item"><a href="#">Ejemplos Patpass Webpay - Transaccion Anulaci&oacute;n</a></li>
        </ol>
        </nav>
        <div class="card">
        <div class="card-body col-sm-4">

                <form id="formulario" action="/patpass-webpay/refund" method="POST">
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
        <a href="/">&laquo; volver a index</a>
        <jsp:include page="../template/footer.jsp"/>
        </body>
</html>