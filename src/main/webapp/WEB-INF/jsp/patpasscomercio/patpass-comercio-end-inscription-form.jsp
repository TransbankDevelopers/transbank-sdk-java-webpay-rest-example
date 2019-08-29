<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Patpass comercio - End Subscription</title>
    <jsp:include page="../template/header.jsp"/>
    </head>

<body class="container">
<nav aria-label="breadcrumb">
    <ol class="breadcrumb">
        <li class="breadcrumb-item"><a href="#">Patpass comercio - End Subscription</a></li>
        <li class="breadcrumb-item active" aria-current="page">Step: End Subscription</li>
    </ol>
</nav>


<div class="alert alert-primary" role="alert">
    <h3>Response:</h3>
    ${model.token_ws}
</div>
<br>
    <div class="alert alert-success" role="alert">
        <p>Subscription successfully with Patpass comercio</p>
    </div>
<br>
<form action="/patpass-comercio/status" method="post" name="tokenForm">
    <input type="hidden" name="tokenComercio" value="${model.token_ws}">
    <input type="submit" class="btn btn-success" value=" Get Patpass comercio status">
</form>
<br>
    <jsp:include page="../template/footer.jsp"/>
<a href="/">&laquo; Back Index</a>

</body>
</html>