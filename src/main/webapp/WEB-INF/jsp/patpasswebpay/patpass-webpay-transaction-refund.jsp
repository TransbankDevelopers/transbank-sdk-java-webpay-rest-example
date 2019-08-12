    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <html>
        <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Ejemplos Webpay - Reembolso de Transacci&oacute;n</title>
        <!-- Latest compiled and minified CSS -->
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
        </head>

        <body class="container">
        <h1>Ejemplos Patpass Webpay - Reembolso de Transacci&oacute;n</h1>
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
        </html>