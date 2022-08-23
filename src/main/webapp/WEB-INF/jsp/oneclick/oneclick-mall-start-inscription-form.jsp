<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Oneclick Mall - Start Inscription</title>
    <jsp:include page="../template/header.jsp"/>
</head>

<body class="container">
    <nav aria-label="breadcrumb">
    <ol class="breadcrumb">
    <li class="breadcrumb-item"><a href="#">Oneclick Mall - Start Inscription</a></li>
    <li class="breadcrumb-item active" aria-current="page">Step: <strong>Start </strong></li>
    </ol>
    </nav>

<h3>request</h3>
<pre><code class="language-json">${details.get("req")}</code></pre>

<h3>result</h3>
<pre><code class="language-json">${details.get("resp")}</code></pre>

<br>
<p><samp>Session successfully started with Oneclick Mall</samp></p>
<br>
<form action="${details.get("url_webpay")}" method="post">
    <input type="hidden" name="TBK_TOKEN" value="${details.get("tbk_token")}">
    <input type="submit" value="Start Oneclick Inscription">
</form>
<br>

<a href="/">&laquo; Back Index</a>
</body>
</html>