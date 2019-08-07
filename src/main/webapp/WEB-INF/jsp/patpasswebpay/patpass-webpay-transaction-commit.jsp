    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <html>
        <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Ejemplos Patpass Webpay - Confirmar Transacci&oacute;n</title>
        <!-- Latest compiled and minified CSS -->
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
        </head>

        <body class="container">
        <h1>Ejemplos Patpass Webpay - Confirmar Transacci&oacute;n</h1>
        <h2>Step: Commit Transaction</h2>
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
        <p><samp>Pago ACEPTADO por webpay</samp></p>
        <br>
        <form action="/patpass-webpay/refund" method="POST">
        <input type="hidden" name="token_ws" value="${details.get("token_ws")}">
        <input type="hidden" name="amount" value="${details.get("amount")}">
        <input type="submit" value="Reembolsar Transacci&oacute;n (Anular)">
        </form>
        <a href="/">&laquo; volver a index</a>
        </body>
        </html>