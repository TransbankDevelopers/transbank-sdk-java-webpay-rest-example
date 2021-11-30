<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Ejemplos Webpay - Reembolso de Transacci&oacute;n</title>
    <jsp:include page="template/header.jsp"/>
</head>

<body class="container">
    <nav aria-label="breadcrumb">
    <ol class="breadcrumb">
    <li class="breadcrumb-item"><a href="#">Ejemplos Webpay - Reembolso de Transacci&oacute;n</a></li>
    <li class="breadcrumb-item active" aria-current="page">Step: <strong>Reembolso Transaction</strong></li>
    </ol>
    </nav>

<h3>request</h3>
<pre><code class="language-json">${details.get("req")}</code></pre>

<h3>result</h3>
<pre><code class="language-json">${details.get("resp")}</code></pre>

<br>
<br>
<a href=".">&laquo; volver a index</a>
</body>
</html>