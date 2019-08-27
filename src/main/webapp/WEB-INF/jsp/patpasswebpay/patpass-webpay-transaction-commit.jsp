    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Ejemplos Patpass Webpay - Confirmar Transacci&oacute;n</title>
<jsp:include page="../template/header.jsp"/>
</head>

<body class="container">
    nav aria-label="breadcrumb">
    <ol class="breadcrumb">
    <li class="breadcrumb-item"><a href="#">Ejemplos Patpass Webpay - Confirmar Transacci&oacute;n</a></li>
    <li class="breadcrumb-item active" aria-current="page">Step: Commit Transaction</li>
    </ol>
    </nav>

<div class="alert alert-warning" role="alert">
<h3>request</h3>
token_ws: ${details.get("token_ws")}
</div>
<div class="alert alert-primary" role="alert">
<h3>result</h3>
<c:forEach var="detail" items="${details}">
    [<c:out value="${detail.key}"/>] = <c:out value="${detail.value}"/>,
</c:forEach>
</div>
<p><samp>Pago ACEPTADO por webpay</samp></p>
<br>
<form action="/patpass-webpay/refund" method="POST">
<input type="hidden" name="token_ws" value="${details.get("token_ws")}">
<input type="hidden" name="amount" value="${details.get("amount")}">
<input type="submit" class="btn btn-success" value="Reembolsar Transacci&oacute;n (Anular)">
</form>
<a href="/">&laquo; volver a index</a>
</body>
</html>