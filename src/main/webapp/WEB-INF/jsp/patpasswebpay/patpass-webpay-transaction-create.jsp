<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Ejemplos Webpay Plus - Crear Transaccion</title>
    <jsp:include page="../template/header.jsp"/>
</head>

<body class="container">
    <nav aria-label="breadcrumb">
    <ol class="breadcrumb">
    <li class="breadcrumb-item"><a href="#">Ejemplos Patpass webpay - Crear Transaccion</a></li>
    <li class="breadcrumb-item active" aria-current="page">Step: <strong>Create Transaction</strong></li>
    </ol>
    </nav>


    <div class="alert alert-warning" role="alert">
    <h3>request</h3>
    <c:forEach var="detail" items="${details}">
        [<c:out value="${detail.key}"/>] = <c:out value="${detail.value}"/>,
    </c:forEach>
</div>
    <div class="alert alert-primary" role="alert">
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