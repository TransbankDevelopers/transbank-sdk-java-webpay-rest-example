<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <html>
        <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Transaccion Completa - Estado de Transacci&oacute;n</title>
        <jsp:include page="../template/header.jsp"/>
        </head>

        <body class="container">
            <nav aria-label="breadcrumb">
            <ol class="breadcrumb">
            <li class="breadcrumb-item"><a href="#">Transaccion Completa - Estado de Transacci&oacute;n</a></li>
            <li class="breadcrumb-item active" aria-current="page">Step: <strong>Status Transaction</strong></li>
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

            <a class="btn btn-success" href="/">&laquo; volver a index</a>
            <a class="btn btn-default" href="javascript:history.back();">&laquo; volver atras</a>
        </body>
    </html>