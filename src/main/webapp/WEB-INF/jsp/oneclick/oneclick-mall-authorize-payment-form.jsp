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
<div class="col-sm-4">
    <form action="/oneclick-mall/authorize" method="post">
        <input type="hidden" name="username" value="${model.username}">
        <input type="hidden" name="tbk_user" value="${model.tbk_user}">
        <div class="form-group">
        <label>buyOrder:</label><input name="buyOrder" type="text" class="form-control" value="${model.buyOrder}"/>
        </div>
        <legend>Comercio 1</legend>

        <div class="form-group">
        <label>buyOrderMallOne:</label><input name="buyOrderMallOne" type="text" class="form-control" value="${model.buyOrderMallOne}"/>
        </div>
        <div class="form-group">
        <label>installmentsOne:</label><input name="installmentsOne" type="number" class="form-control" value="${model.installmentsOne}"/>
        </div>
        <div class="form-group">
        <label>commerceCodeOne:</label><input name="commerceCodeOne" type="text" class="form-control" value="${model.commerceCodeOne}"/>
        </div>
        <div class="form-group">
        <label>amount1:</label><input name="amount1" type="number" class="form-control" value="${model.amount1}"/>
        </div>
        <legend>Comercio 2</legend>
        <div class="form-group">
        <label>buyOrderMallTwo:</label><input name="buyOrderMallTwo" type="text" class="form-control" value=""/>
        </div>
        <div class="form-group">
        <label>installmentsTwo:</label><input name="installmentsTwo" type="number" class="form-control" value=""/>
        </div>
        <div class="form-group">
        <label>commerceCodeTwo:</label><input name="commerceCodeTwo" type="text" class="form-control" value=""/>
        </div>
        <div class="form-group">
        <label>amount2:</label><input name="amount2" type="number" class="form-control" value=""/>
        </div>
        <input type="submit" value="Authorize Payment">

    </form>
</div>
<br>

<a href="/">&laquo; Back Index</a>
</body>
</html>