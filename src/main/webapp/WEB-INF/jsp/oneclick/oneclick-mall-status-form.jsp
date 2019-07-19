<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Ejemplos Oneclick - Estado de la transacci&oacute;n</title>
</head>

<body class="container">
<h1>Ejemplos Webpay - Transaccion Anulaci&oacute;n</h1>

<form id="formulario" action="/oneclick-mall/status" method="POST">
    <fieldset>
        <legend>Formulario de Anulaci&oacute;n</legend>
        <br/><br/>
        <label>buy_order:</label><input name="buy_order" type="text"/><br/><br/><br/>
        <input name="enviar" type="submit" value="Enviar"/>
    </fieldset>
</form>
<br>
<a href="/">&laquo; volver a index</a>
</body>
</html>