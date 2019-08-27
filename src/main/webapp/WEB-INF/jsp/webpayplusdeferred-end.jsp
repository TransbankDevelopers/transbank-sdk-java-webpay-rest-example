<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Ejemplos Webpay - Capturar Transacci&oacute;n</title>
    <jsp:include page="template/header.jsp"/>
</head>

<body class="container">
    <nav aria-label="breadcrumb">
    <ol class="breadcrumb">
    <li class="breadcrumb-item"><a href="#">Ejemplos Webpay - Capturar Transacci&oacute;n</a></li>
    <li class="breadcrumb-item active" aria-current="page">Step: <strong>Commit Transaction</strong></li>
    </ol>
    </nav>

    <div class="alert alert-warning" role="alert">
    <h3>request</h3>
    [token_ws]: ${details.get("token_ws")}, [buy_order]: ${details.get("buy_order")}, [authorization_code]: ${details.get("authorization_code")},
    [capture_amount]: ${details.get("capture_amount")}
</div>
    <div class="alert alert-primary" role="alert">
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