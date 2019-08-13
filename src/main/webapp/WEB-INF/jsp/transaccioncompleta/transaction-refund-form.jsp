<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
        <head>
        <!-- Bootstrap CSS -->
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Transaccion Completa - Transaccion Anulaci&oacute;n</title>
        </head>

        <body class="container">
        <h1>Transaccion Completa - Transaccion Anulaci&oacute;n</h1>

                <form id="formulario" action="/fulltransaction/refund" method="POST">
                        <fieldset>
                        <legend>Formulario de Anulaci&oacute;n</legend>
                        <br/><br/>
                        <label>token_ws:</label><input name="token" type="text"/><br/><br/><br/>
                        <label>amount:</label><input name="amount" type="text"/><br/><br/><br/>
                        <input name="enviar" type="submit" value="Enviar"/>
                        </fieldset>
                </form>
        <br>
        <a href="/">&laquo; volver a index</a>
        </body>
</html>