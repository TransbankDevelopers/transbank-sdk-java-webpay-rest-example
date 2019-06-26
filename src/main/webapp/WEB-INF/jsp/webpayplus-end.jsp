<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Ejemplos Webpay - Transacci&oacute;n Normal</title>
    <!-- Latest compiled and minified CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
</head>

<body class="container">
<h1>Ejemplos Webpay - Transacci&oacute;n Normal</h1>
<h2>Step: Commit Transaction</h2>
<div style="background-color:lightyellow;">
    <h3>request</h3>
    token_ws: ${details.get("token_ws")}
</div>
<div style="background-color:lightgrey;">
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
<form action="/webpayplus-refund" method="POST">
    <input type="hidden" name="token_ws" value="${details.get("token_ws")}">
    <input type="hidden" name="amount" value="${details.get("response").getAmount()}">
    <input type="submit" value="Reenbolsar Transacci&oacute;n (Anular)">
</form>
<a href=".">&laquo; volver a index</a>
</body>
</html>