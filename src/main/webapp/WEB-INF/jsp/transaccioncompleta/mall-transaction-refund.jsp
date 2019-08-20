    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <html>
        <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Transaccion Completa Mall - Reembolso de Transacci&oacute;n</title>
        <!-- Bootstrap CSS -->
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
        </head>

        <body class="container">
        <h1>Transaccion Completa Mall - Reembolso de Transacci&oacute;n</h1>
        <div style="background-color:lightyellow;">
        <h3>Request:</h3>
        <c:forEach var="request" items="${model.request}">
            [<c:out value="${request.key}"/>] = <c:out value="${request.value}"/>,
        </c:forEach>
        </div>
        <div style="background-color:lightgrey;">
        <h3>Response:</h3>
        ${model.response}
        </div>
        <br>
            <br>
                    <c:set var="val" value="${model.response.responseCode}"/>
                    <c:choose>
                            <c:when test="${val == '0'}">
                                    <p><samp>Reembolso realizado con exito.</samp></p>
                            </c:when>
                            <c:otherwise>
                                    <p><samp>upss. el reembolso no se pudo llevar a cabo</samp></p>
                            </c:otherwise>
                    </c:choose>

            <br>
        <br>
        <a href="/">&laquo; volver a index</a>
        </body>
            <!-- jQuery first, then Popper.js, then Bootstrap JS -->
            <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
            <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
            <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
        </html>