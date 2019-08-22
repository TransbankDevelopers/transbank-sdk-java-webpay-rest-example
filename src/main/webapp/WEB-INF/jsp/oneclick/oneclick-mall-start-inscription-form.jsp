<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Oneclick Mall - Start Inscription</title>
    <jsp:include page="../template/header.jsp"/>
</head>

<body class="container">
    <nav aria-label="breadcrumb">
    <ol class="breadcrumb">
    <li class="breadcrumb-item"><a href="#">Oneclick Mall - Start Inscription</a></li>
    <li class="breadcrumb-item active" aria-current="page">Step: <strong>Start </strong></li>
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
<p><samp>Session successfully started with Oneclick Mall</samp></p>
<br>
<form action="${model.url_webpay}" method="post">
    <input type="hidden" name="TBK_TOKEN" value="${model.tbk_token}">
    <input type="submit" value="Start Oneclick Inscription">
</form>
<br>

<a href="/">&laquo; Back Index</a>
</body>
</html>