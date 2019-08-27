<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <html>
        <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Transaccion Completa Mall - Confirmar Transacci&oacute;n</title>
        <jsp:include page="../template/header.jsp"/>
        </head>

        <body class="container">
        <nav aria-label="breadcrumb">
        <ol class="breadcrumb">
        <li class="breadcrumb-item"><a href="#">Transaccion Completa Mall - Confirmar Transacci&oacute;n</a></li>
        <li class="breadcrumb-item active" aria-current="page">Step: <strong>Commit Transaction</strong></li>
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

            <br>
            <div class="card">
                <div class="card-body">
                    <form action="/mallfulltransaction/refund" method="POST">
                        <div class="form-row">
                        <input type="hidden" name="token" value="${details.get("token_ws")}">
                            <div class="form-group col-sm-4">
                                <label for="amount">amount:</label>
                                <input type="text" id="amount" name="amount" value="${details.get("amount")}" />
                            </div>
                            <div class="form-group col-sm-4">
                                <label for="commerceCode">commerceCode:</label>
                                <input type="text" id="commerceCode" name="commerceCode" value="${details.get("commerceCode")}" />
                            </div>
                            <div class="form-group col-sm-4">
                                <label for="buyOrder">buyOrder:</label>
                                <input type="text" id="buyOrder" name="buyOrder" value="${details.get("buyOrder")}" />
                            </div>
                            <button type="submit" class="btn btn-primary">Pedir Reembolso</button>
                        </div>
                    </form>
                </div>
            </div>
            <br>
            <a href="/">&laquo; volver a index</a>
        </body>
    <jsp:include page="../template/footer.jsp"/>
    </html>