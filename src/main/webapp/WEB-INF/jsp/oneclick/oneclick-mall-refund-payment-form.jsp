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
    <li class="breadcrumb-item active" aria-current="page">Step: <strong>Authorize</strong></li>
    </ol>
    </nav>


<h3>request</h3>
<pre><code class="language-json">${details.get("req")}</code></pre>

<h3>result</h3>
<pre><code class="language-json">${details.get("resp")}</code></pre>

<br>
<p><samp>Payment accepted by Oneclick Mall</samp></p>
<br>
<form action="/oneclick-mall/refund" method="post">
    buy_order<input type="text" name="buy_order" value="${details.get("buyOrder")}">
    <br>
    child_commerce_code<input type="text" name="child_commerce_code" value="${details.get("childOneCommerceCode")}">
    <br>
    child_buy_order<input type="text" name="child_buy_order" value="${details.get("chileOneBuyOrder")}">
    <br>
    amount<input type="text" name="amount" value="${details.get("amountMallOne")}">
    <input type="submit" value="Refund Oneclick Mall Payment">
</form>
<br>

<a href="/">&laquo; Back Index</a>
</body>
</html>