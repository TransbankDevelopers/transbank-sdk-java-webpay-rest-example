<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Oneclick Mall - Authorize Payment</title>
    <jsp:include page="../template/header.jsp"/>
</head>

<body class="container">
    <nav aria-label="breadcrumb">
    <ol class="breadcrumb">
    <li class="breadcrumb-item"><a href="#">Oneclick Mall - Authorize Payment</a></li>
    <li class="breadcrumb-item active" aria-current="page">Step: <strong>Authorize </strong></li>
    </ol>
    </nav>

<div class="alert alert-warning" role="alert">
    <h3>Request:</h3>
    <c:forEach var="request" items="${model.request}">

        [<c:out value="${request.key}"/>] = <c:out value="${request.value}"/>,
    </c:forEach>
</div>
<div class="alert alert-primary" role="alert">
    <h3>Response:</h3>
    ${model.response}
</div>
<br>
<p><samp>Authorization successfully finished on Oneclick Mall Deferred</samp></p>
<br>
<form action="/oneclick-mall-deferred/capture" method="post">
    <input type="hidden" name="buy_order" value="${model.buyOrder}">
    <input type="hidden" name="child_buy_order" value="${model.childBuyOrder}">
    <input type="hidden" name="authorization_code" value="${model.authorizationCode}">
    <input type="hidden" name="child_commerce_code" value="${model.commerceCode}">
    Capture Amount: <input type="text" name="amount" value="${model.amount}">
    <input type="submit" value="Capture Payment">
</form>
<br>

<a href="/">&laquo; Back Index</a>
</body>
</html>