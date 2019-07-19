<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Ejemplos Webpay - Solicitar Estado</title>
</head>

<body class="container">
<h1>Ejemplos Webpay - Solicitar Estado</h1>

<form id="formulario" action="${model.endpoint}" method="POST">
    <fieldset>
        <legend>Formulario solicitud de estado</legend>
        <br/><br/>
        <label>token_ws:</label><input name="token_ws" type="text"/>&nbsp;&nbsp;&nbsp;
        <input name="enviar" type="submit" value="Enviar"/>
    </fieldset>
</form>
<br>
<a href=".">&laquo; volver a index</a>
</body>
</html>