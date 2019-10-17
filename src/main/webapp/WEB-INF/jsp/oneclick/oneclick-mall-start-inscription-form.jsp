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


    <div class="card">
    <div class="card-body col-sm-6">
    <form id="formulario" action="start" method="POST">
    <legend>Formulario de Creaci&oacute;n</legend>
    <div class="form-group">
    <label>username:</label><input name="username" type="text" class="form-control" value="${model.username}"/>
    </div>
    <div class="form-group">
    <label>email:</label><input name="email" type="text" class="form-control" value="${model.email}"/>
    </div>
    <div class="form-group">
    <label>responseUrl:</label><input name="responseUrl" type="text" class="form-control" value="${model.responseUrl}"/>
    </div>
    <input name="enviar" type="submit" value="Enviar" class="btn btn-primary" />
    </form>
    </div>
    </div>


<a href="/">&laquo; Back Index</a>
</body>
</html>