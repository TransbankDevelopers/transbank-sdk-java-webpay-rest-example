<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Patpass comercio - Start Inscription</title>
    <jsp:include page="../template/header.jsp"/>
    </head>

<body class="container">
<nav aria-label="breadcrumb">
    <ol class="breadcrumb">
        <li class="breadcrumb-item"><a href="#">Patpass comercio - Start Inscription</a></li>
        <li class="breadcrumb-item active" aria-current="page">Step: Start</li>
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
    <div class="alert alert-success" role="alert">
        <p>Session successfully started with Patpass comercio</p>
    </div>
<br>
<form action="${model.url_webpay}" method="post" name="tokenForm">
    <input type="hidden" name="tokenComercio" value="${model.tbk_token}">
    <input type="submit" class="btn btn-success" value="Start Patpass comercio">
</form>
<br>
    <jsp:include page="../template/footer.jsp"/>
<a href="/">&laquo; Back Index</a>

</body>
</html>