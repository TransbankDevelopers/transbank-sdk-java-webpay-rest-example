<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
        <head>
        <jsp:include page="../template/header.jsp"/>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Transaccion Completa - Transaccion Anulaci&oacute;n</title>
        </head>

        <body class="container">
        <nav aria-label="breadcrumb">
        <ol class="breadcrumb">
        <li class="breadcrumb-item"><a href="#">Transaccion Completa - Transaccion Anulaci&oacute;n</a></li>
        <li class="breadcrumb-item active" aria-current="page">Step: <strong>Create Transaction</strong></li>
        </ol>
        </nav>
        <div class="card">
        <div class="card-body col-sm-4">

                <form id="formulario" action="/fulltransaction/refund" method="POST">
                        <legend>Formulario de Anulaci&oacute;n</legend>
                        <div class="form-group">
                        <label>token_ws:</label><input name="token" type="text" class="form-control"/>
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