<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Ejemplos Webpay Plus - Transaccion Normal</title>
</head>

<body>
<h1>Ejemplos Webpay Plus - Transaccion Normal</h1>

<h2>Step: Create Transaction</h2>

<div style="background-color:lightyellow;">
    <h3>request</h3>
    [amount] = ${details.get("amount")}, [buy_order] = ${details.get("buyOrder")}, [session_id] = ${details.get("sessionId")}, [return_url] = ${details.get("returnUrl")}
</div>
<div style="background-color:lightgrey;">
    <h3>result</h3>
    [url] = ${details.get("url")}, [token_ws] = ${details.get("token")}
</div>

<p><samp>Sesion iniciada con exito en Webpay</samp></p>
<br><form action="${details.get("url")}" method="POST"><input type="hidden" name="token_ws" value="${details.get("token")}"><input type="submit" value="Ejecutar Pago con Webpay"></form>
<br>

<a href=".">&laquo; volver a index</a>
</body>
</html>