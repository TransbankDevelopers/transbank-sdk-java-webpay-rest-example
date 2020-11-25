<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Ejemplos Oneclick - Reembolsar Transacci&oacute;n</title>
    <jsp:include page="../template/header.jsp"/>
</head>

<body class="container">
    <nav aria-label="breadcrumb">
    <ol class="breadcrumb">
    <li class="breadcrumb-item"><a href="#">Ejemplos Oneclick - Reembolsar Transacci&oacute;n</a></li>
    <li class="breadcrumb-item active" aria-current="page">Step: <strong>Refund</strong></li>
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
<p><samp>Reembolso realizado con exito.</samp></p>
<br>

<a href="/oneclick-mall-deferred/inscription-delete" class="btn btn-primary">Eliminar inscripci&oacute;n</a>
<br/>
<a href="/">&laquo; volver a index</a>
</body>
</html>