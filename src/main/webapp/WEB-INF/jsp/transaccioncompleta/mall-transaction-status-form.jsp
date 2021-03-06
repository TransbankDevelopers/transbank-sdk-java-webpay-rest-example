<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
        <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Transaccion Completa Mall - Solicitar Estado</title>
        <jsp:include page="../template/header.jsp"/>
        </head>

        <body class="container">
    <nav aria-label="breadcrumb">
    <ol class="breadcrumb">
    <li class="breadcrumb-item"><a href="#">Transaccion Completa Mall - Solicitar Estado</a></li>
    </ol>
    </nav>

        <form id="formulario" action="${model.endpoint}" method="POST">
                <div class="card">
                  <div class="card-body col-sm-4">
                        <legend>Formulario solicitud de estado</legend>
                        <div class="form-group">
                                <label>token_ws:</label><input name="token" type="text" class="form-control"/>
                        </div>&nbsp;&nbsp;
                        <input name="enviar" type="submit" value="Enviar" class="btn btn-primary"/>
                  </div>
                </div>
        </form>
        <br>
        <a href="/">&laquo; volver a index</a>
        </body>
    <jsp:include page="../template/footer.jsp"/>
</html>