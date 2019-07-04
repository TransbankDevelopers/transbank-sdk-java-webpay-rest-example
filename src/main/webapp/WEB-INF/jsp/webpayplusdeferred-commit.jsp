<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Ejemplos Webpay - Confirmar Transacci&oacute;n</title>
    <!-- Latest compiled and minified CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
</head>

<body class="container">
<h1>Ejemplos Webpay - Confirmar Transacci&oacute;n</h1>
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