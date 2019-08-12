<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <html>
        <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Transaccion Completa - Confirmar Transacci&oacute;n</title>
    <!-- Bootstrap CSS -->
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
        </head>

        <body class="container">
            <h1>Transaccion Completa - Confirmar Transacci&oacute;n</h1>
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

            <br>
            <div class="card">
                <div class="card-body">
                    <form action="/fulltransaction/refund" method="POST">
                        <div class="form-row">
                        <input type="hidden" name="token" value="${details.get("token_ws")}">
                            <div class="form-group col-sm-4">
                            <input type="text" id="amount" name="amount" value="${details.get("amount")}" />
                            <br>

                            </div>
                            <button type="submit" class="btn btn-primary">Pedir Reembolso</button>
                        </div>
                    </form>
                </div>
            </div>
            <br>
            <a href="/">&laquo; volver a index</a>
        </body>
    <!-- jQuery first, then Popper.js, then Bootstrap JS -->
    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
    </html>