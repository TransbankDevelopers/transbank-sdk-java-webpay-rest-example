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
    <li class="breadcrumb-item"><a href="#">Ejemplos Webpay Plus - Crear Transaccion</a></li>
    <li class="breadcrumb-item active" aria-current="page">Step: <strong>Commit Transaction</strong></li>
    </ol>
    </nav>

<div class="alert alert-warning" role="alert">
    <h3>request</h3>
    token_ws: ${details.get("token_ws")}
</div>
    <div class="alert alert-primary" role="alert">
    <h3>result</h3>
    [vci] = ${details.get("response").getVci()}, [amount] = ${details.get("response").getAmount()}, [status] = ${details.get("response").getStatus()},
    [buy_order] = ${details.get("response").getBuyOrder()}, [session_id] = ${details.get("response").getSessionId()},
    [accounting_date] = ${details.get("response").getAccountingDate()}, [transaction_date] = ${details.get("response").getTransactionDate()},
    [authorization_code] = ${details.get("response").getAuthorizationCode()}, [payment_type_code] = ${details.get("response").getPaymentTypeCode()},
    [response_code] = ${details.get("response").getResponseCode()}, [installments_amount] = ${details.get("response").getInstallmentsAmount()},
    [installments_number] = ${details.get("response").getInstallmentsNumber()}, [balance] = ${details.get("response").getBalance()}
</div>
<p><samp>Pago ACEPTADO por webpay</samp></p>
<br>
<form action="${details.get("refund-endpoint")}" method="POST">
    <input type="hidden" name="token_ws" value="${details.get("token_ws")}">
    <input type="hidden" name="amount" value="${details.get("response").getAmount()}">
    <input type="submit" value="Reembolsar Transacci&oacute;n (Anular)">
</form>
<a href=".">&laquo; volver a index</a>
</body>
</html>