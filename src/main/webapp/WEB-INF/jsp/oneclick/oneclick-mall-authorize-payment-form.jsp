<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Oneclick Mall - Authorize Payment</title>
    <jsp:include page="../template/header.jsp"/>
</head>

<body class="container">
    <nav aria-label="breadcrumb">
    <ol class="breadcrumb">
    <li class="breadcrumb-item"><a href="#">Oneclick Mall - Authorize Payment</a></li>
    <li class="breadcrumb-item active" aria-current="page">Step: <strong>Authorize </strong></li>
    </ol>
    </nav>

<h3>request</h3>
<pre><code class="language-json">${details.get("req")}</code></pre>

<h3>result</h3>
<pre><code class="language-json">${details.get("resp")}</code></pre>
<br>
<p><samp>Inscription successfully finished on Oneclick Mall</samp></p>
<br>
<form action="/oneclick-mall/authorize" method="post">
    <input type="hidden" name="username" value="${details.get("username")}">
    <input type="hidden" name="tbk_user" value="${details.get("tbk_user")}">
    Payment Amount: <input type="text" name="amount" value="1000">
    <input type="submit" value="Authorize Payment">
</form>
<br>

<a href="/">&laquo; Back Index</a>
</body>
</html>