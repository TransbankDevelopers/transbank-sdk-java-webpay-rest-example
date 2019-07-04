<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Ejemplos Webpay - Transaccion Anulaci&oacute;n</title>
</head>

<body class="container">
<h1>Ejemplos Webpay - Transaccion Anulaci&oacute;n</h1>

<form id="formulario" action="${details.get("refund-endpoint")}" method="POST">
    <fieldset>
        <legend>Formulario de Anulaci&oacute;n</legend>
        <br/><br/>
        <label>token_ws:</label><input name="token_ws" type="text"/>&nbsp;&nbsp;&nbsp;
        <label>amount:</label><input name="amount" type="text"/><br/><br/><br/>
        <input name="enviar" type="submit" value="Enviar"/>
    </fieldset>
</form>
<br>
<a href=".">&laquo; volver a index</a>
</body>
</html>