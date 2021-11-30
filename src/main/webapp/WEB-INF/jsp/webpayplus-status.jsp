<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Ejemplos Webpay - Estado de Transacci&oacute;n</title>
    <jsp:include page="template/header.jsp"/>
</head>

<body class="container">
    <nav aria-label="breadcrumb">
    <ol class="breadcrumb">
    <li class="breadcrumb-item"><a href="#">Ejemplos Webpay - Estado de Transacci&oacute;n</a></li>
    </ol>
    </nav>

<div class="alert alert-warning" role="alert">
    <h3>request</h3>
    token_ws: ${details.get("token_ws")}
</div>

<h3>result</h3>
<pre><code class="language-json">${details.get("resp")}</code></pre>


<br>
<br>
<a href=".">&laquo; volver a index</a>
</body>
</html>