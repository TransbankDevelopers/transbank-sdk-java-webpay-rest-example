<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <html>
        <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Transaccion Completa Mall - Estado de Transacci&oacute;n</title>
        <!-- Latest compiled and minified CSS -->
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
        </head>

        <body class="container">
            <h1>Transaccion Completa Mall - Estado de Transacci&oacute;n</h1>
            <h2>Step: Status Transaction</h2>
            <div style="background-color:lightyellow;">
            <h3>request</h3>
            token_ws: ${details.get("token_ws")}
            </div>
            <div style="background-color:lightgrey;">
            <h3>result</h3>
            <c:forEach var="detail" items="${details}">
                [<c:out value="${detail.key}"/>] = <c:out value="${detail.value}"/>,
            </c:forEach>
            </div>

            <a class="btn btn-success" href="/">&laquo; volver a index</a>
            <a class="btn btn-default" href="javascript:history.back();">&laquo; volver atras</a>
        </body>
    </html>