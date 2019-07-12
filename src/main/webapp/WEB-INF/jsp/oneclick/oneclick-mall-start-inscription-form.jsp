<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Oneclick Mall - Start Inscription</title>
    <!-- Latest compiled and minified CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
</head>

<body class="container">
<h1>Oneclick Mall - Start Inscription</h1>

<h2>Step: Start</h2>

<div style="background-color:lightyellow;">
    <h3>Request:</h3>
    <c:forEach var="request" items="${model.request}">
        [<c:out value="${request.key}"/>] = <c:out value="${request.value}"/>,
    </c:forEach>
</div>
<div style="background-color:lightgrey;">
    <h3>Response:</h3>
    ${model.response}
</div>
<br>
<p><samp>Session successfully started with Oneclick Mall</samp></p>
<br>
<form action="${model.url_webpay}" method="post">
    <input type="hidden" name="TBK_TOKEN" value="${model.tbk_token}">
    <input type="submit" value="Start Oneclick Inscription">
</form>
<br>

<a href=".">&laquo; Back Index</a>
</body>
</html>