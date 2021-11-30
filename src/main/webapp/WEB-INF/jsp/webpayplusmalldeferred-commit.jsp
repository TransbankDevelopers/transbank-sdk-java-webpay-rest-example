<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Ejemplos Webpay Mall Deferred - Confirmar Transacci&oacute;n</title>
    <jsp:include page="template/header.jsp"/>
</head>

<body class="container">
    <nav aria-label="breadcrumb">
    <ol class="breadcrumb">
    <li class="breadcrumb-item"><a href="#">Ejemplos Webpay Mall Deferred - Confirmar Transacci&oacute;n</a></li>
    <li class="breadcrumb-item active" aria-current="page">Step: <strong>Commit Transaction</strong></li>
    </ol>
    </nav>

<div class="alert alert-warning" role="alert">
    <h3>request</h3>
    token_ws: ${details.get("token_ws")}
</div>

<h3>result</h3>
<pre><code class="language-json">${details.get("resp")}</code></pre>

<p><samp>Pago ACEPTADO por webpay</samp></p>
<br>
<form action="/webpayplusmalldeferred-capture" method="POST">
    <input type="hidden" name="token_ws" value="${details.get("token_ws")}">
    <input type="text" name="child_buy_order" value="${details.get("child_buy_order")}">
    <input type="text" name="child_commerce_code" value="${details.get("child_commerce_code")}">
    <input type="text" name="child_amount" value="${details.get("child_amount")}">
    <input type="hidden" name="authorization_code" value="${details.get("authorization_code")}">
    <input type="submit" value="Capturar Pago">
</form>
<a href=".">&laquo; volver a index</a>
</body>
</html>