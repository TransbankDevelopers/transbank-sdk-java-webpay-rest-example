<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Ejemplos Webpay - Capturar Transacci&oacute;n</title>
    <!-- Latest compiled and minified CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
</head>

<body class="container">
<h1>Ejemplos Webpay - Capturar Transacci&oacute;n</h1>
<h2>Step: Commit Transaction</h2>
<div style="background-color:lightyellow;">
    <h3>request</h3>
    [token_ws]: ${details.get("token_ws")}, [buy_order]: ${details.get("buy_order")}, [authorization_code]: ${details.get("authorization_code")},
    [capture_amount]: ${details.get("capture_amount")}
</div>
<div style="background-color:lightgrey;">
    <h3>result</h3>
    [authorization_code] = ${details.get("response").getAuthorizationCode()},
    [authorization_date] = ${details.get("response").getAuthorizationDate()},
    [captured_amount] = ${details.get("response").getCapturedAmount()},
    [response_code] = ${details.get("response").getResponseCode()}
</div>
<p><samp>Transacci&oacute; capturada en forma exitosa.</samp></p>
<br>
<a href=".">&laquo; volver a index</a>
</body>
</html>