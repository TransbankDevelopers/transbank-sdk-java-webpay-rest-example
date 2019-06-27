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
<h2>Step: Refund</h2>
<div style="background-color:lightyellow;">
    <h3>request</h3>
    [token_ws] = ${details.get("token_ws")}
</div>
<div style="background-color:lightgrey;">
    <h3>result</h3>
    [type] = ${details.get("response").getType()},
    [balance] = ${details.get("response").getBalance()},
    [authorization_code] = ${details.get("response").getAuthorizationCode()},
    [response_code] = ${details.get("response").getResponseCode()},
    [authorization_date] = ${details.get("response").getAuthorizationDate()},
    [nullified_amount] = ${details.get("response").getNullifiedAmount()}
</div>
<br>
<p><samp>Reembolso realizado con exito.</samp></p>
<br>
<a href=".">&laquo; volver a index</a>
</body>
</html>