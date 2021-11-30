<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Ejemplos Webpay Plus Mall Deferred - Crear Transaccion</title>
    <jsp:include page="template/header.jsp"/>
</head>

<body class="container">
    <nav aria-label="breadcrumb">
    <ol class="breadcrumb">
    <li class="breadcrumb-item"><a href="#">Ejemplos Webpay Plus Mall Deferred - Crear Transaccion</a></li>
    <li class="breadcrumb-item active" aria-current="page">Step: <strong>Create Transaction</strong></li>
    </ol>
    </nav>


<h3>request</h3>
<pre><code class="language-json">${details.get("req")}</code></pre>

<h3>result</h3>
<pre><code class="language-json">${details.get("resp")}</code></pre>

<br>
<p><samp>Sesion iniciada con exito en Webpay</samp></p>
<br>
<form action="${details.get("url")}" method="POST">
    <input type="hidden" name="token_ws" value="${details.get("token")}">
    <input type="submit" value="Ejecutar Pago con Webpay">
</form>
<br>

<a href=".">&laquo; volver a index</a>
</body>
</html>