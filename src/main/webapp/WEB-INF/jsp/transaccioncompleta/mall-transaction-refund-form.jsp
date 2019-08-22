<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
        <head>
        <jsp:include page="../template/header.jsp"/>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Transaccion Completa Mall - Transaccion Anulaci&oacute;n</title>
        </head>

        <body class="container">
        <nav aria-label="breadcrumb">
        <ol class="breadcrumb">
        <li class="breadcrumb-item"><a href="#">Transaccion Completa Mall - Transaccion Anulaci&oacute;n</a></li>
        </ol>
        </nav>
        <div class="card">
        <div class="card-body col-sm-4">
                <form id="formulario" action="/mallfulltransaction/refund" method="POST">

                        <legend>Formulario de Anulaci&oacute;n</legend>
                        <br/><br/>
                        <div class="form-group">
                                <label for="token">token_ws:</label>
                                <input name="token" id="token" type="text" class="form-control"/>
                        </div>
                        <div class="form-group">
                                <label for="amount">amount:</label>
                                <input name="amount" id="amount" type="text" class="form-control"/>
                        </div>
                        <div class="form-group">
                                <label for="commerceCode">commerceCode:</label>
                                <input name="commerceCode" id="commerceCode" type="text" class="form-control"/>
                        </div>
                        <div class="form-group">
                                <label for="buyOrder">buyOrder:</label>
                                <input name="buyOrder" id="buyOrder" type="text" class="form-control"/>
                        </div>

                        <button type="submit" class="btn btn-primary">Enviar</button>
                </form>
        </div>
        </div>
        <br>
        <a href="/">&laquo; volver a index</a>
        <jsp:include page="../template/footer.jsp"/>
        </body>
</html>