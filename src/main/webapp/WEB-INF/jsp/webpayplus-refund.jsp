<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Ejemplos Webpay - ReembolsarTransacci&oacute;n</title>
    <jsp:include page="template/header.jsp"/>
</head>

<body class="container">
    <nav aria-label="breadcrumb">
    <ol class="breadcrumb">
    <li class="breadcrumb-item"><a href="#">Ejemplos Webpay - Reembolsar Transacci&oacute;nn</a></li>
    <li class="breadcrumb-item active" aria-current="page">Step: <strong>Refund</strong></li>
    </ol>
    </nav>

    <div class="alert alert-warning" role="alert">
    <h3>request</h3>
    [token_ws] = ${details.get("token_ws")}
</div>
    <div class="alert alert-primary" role="alert">
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