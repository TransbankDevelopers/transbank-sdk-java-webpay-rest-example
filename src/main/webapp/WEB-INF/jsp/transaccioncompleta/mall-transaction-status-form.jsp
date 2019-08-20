<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
        <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Transaccion Completa Mall - Solicitar Estado</title>
        </head>

        <body class="container">
        <h1>Transaccion Completa Mall - Solicitar Estado</h1>

        <form id="formulario" action="${model.endpoint}" method="POST">
        <div class="card">
        <div class="card-body">
        <legend>Formulario solicitud de estado</legend>
        <br/><br/>
        <label>token_ws:</label><input name="token" type="text"/>&nbsp;&nbsp;&nbsp;
        <input name="enviar" type="submit" value="Enviar"/>
        </div>
        </div>
        </form>
        <br>
        <a href="/">&laquo; volver a index</a>
        </body>
        </html>