<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Ejemplos Webpay Plus - Crear Transaccion</title>
    <!-- Latest compiled and minified CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
</head>

<body class="container">
<h1>Ejemplos Patpass webpay - Crear Transaccion</h1>

<h2>Step: Create Transaction</h2>

<div style="background-color:lightyellow;">
    <h3>request</h3>
    <c:forEach var="detail" items="${details}">
        [<c:out value="${detail.key}"/>] = <c:out value="${detail.value}"/>,
    </c:forEach>
</div>
<div style="background-color:lightgrey;">
    <h3>result</h3>
    [url] = ${details.get("url")} , [token_ws] = ${details.get("token")}
</div>
<br>
<p><samp>Sesion iniciada con exito en Webpay patpass</samp></p>
<br>
<form action="${details.get("url")}" method="POST">
<input type="hidden" name="token_ws" value="${details.get("token")}">
<input type="submit" value="Ejecutar Pago con Webpay">
</form>
<br>

<a href="/">&laquo; volver a index</a>
</body>
</html>