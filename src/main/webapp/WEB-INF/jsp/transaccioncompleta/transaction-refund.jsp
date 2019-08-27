    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <html>
        <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Transaccion Completa - Reembolso de Transacci&oacute;n</title>
            <jsp:include page="../template/header.jsp"/>
        </head>

        <body class="container">
            <nav aria-label="breadcrumb">
            <ol class="breadcrumb">
            <li class="breadcrumb-item"><a href="#">Transaccion Completa - Reembolso de Transacci&oacute;n</a></li>
            <li class="breadcrumb-item active" aria-current="page">Step: <strong>Create Transaction</strong></li>
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
            <br>
                    <c:set var="val" value="${model.response.responseCode}"/>
                    <c:choose>
                        <c:when test="${val == '0'}">
                            <div class="alert alert-success" role="alert">
                            Reembolso realizado con exito.
                            </div>
                        </c:when>
                        <c:otherwise>
                            <div class="alert alert-danger" role="alert">
                            upss. el reembolso no se pudo llevar a cabo
                            </div>
                        </c:otherwise>
                    </c:choose>

            <br>
        <br>
        <a href="/">&laquo; volver a index</a>
        </body>
            <jsp:include page="../template/footer.jsp"/>
        </html>