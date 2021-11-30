<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Ejemplos Webpay - Confirmar Transacci&oacute;n</title>
    <jsp:include page="template/header.jsp"/>
</head>

<body class="container">
    <nav aria-label="breadcrumb">
    <ol class="breadcrumb">
    <li class="breadcrumb-item"><a href="#">Ejemplos Webpay - Confirmar Transacci&oacute;n</a></li>
    <li class="breadcrumb-item active" aria-current="page">Step: <strong>Create Transaction</strong></li>
    </ol>
    </nav>

<div class="alert alert-warning" role="alert">
    <h3>request</h3>
    token_ws: ${details.get("token_ws")}
</div>

<h3>result</h3>
<pre><code class="language-json">${details.get("resp")}</code></pre>

<p><samp>Pago ACEPTADO por webpay</samp></p>
<br>
<label>
    <table border="0">
        <tbody>
        <tr>
            <td>
                <form action="/webpayplusdeferred-capture" method="post">
                    <input type="hidden" name="token_ws" value="${details.get("token_ws")}">
                    <input type="hidden" name="authorization_code" value="${details.get("response").getAuthorizationCode()}">
                    <input type="hidden" name="buy_order" value="${details.get("response").getBuyOrder()}">
                    Capturar monto: <input type="text" name="capture_amount"/> <input type="submit" value="Capturar"/>
                </form>
            </td>
            <td> || </td>
            <td>
                <form action="${details.get("refund-endpoint")}" method="post">
                    <input type="hidden" name="token_ws" value="${details.get("token_ws")}">
                    <input type="hidden" name="amount" value="${details.get("response").getAmount()}">
                    <input type="submit" value="Reembolsar Transacci&oacute;n (Anular)">
                </form>
            </td>
        </tr>
        </tbody>
    </table>
</label>
<br>
<a href=".">&laquo; volver a index</a>
</body>
</html>