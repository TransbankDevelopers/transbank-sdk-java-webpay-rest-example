<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Patpass comercio - Start Inscription</title>
    <jsp:include page="../template/header.jsp"/>
</head>

<body class="container">
    <nav aria-label="breadcrumb">
    <ol class="breadcrumb">
    <li class="breadcrumb-item"><a href="#">Patpass comercio - Start Inscription</a></li>
    </ol>
    </nav>

    <div class="card">
    <div class="card-body col-sm-6">
        <form id="formulario" action="start" method="POST">
                <legend>Formulario de Creaci&oacute;n</legend>
                <div class="form-group">
                    <label>url:</label><input name="url" type="text" class="form-control" value="${model.url}"/>
                </div>
                <div class="form-group">
                <label>name:</label><input name="name" type="text" class="form-control" value="${model.name}"/>
                </div>
                <div class="form-group">
                <label>firstLastName:</label><input name="firstLastName" type="text" class="form-control" value="${model.firstLastName}"/>
                </div>
                <div class="form-group">
                <label>secondLastName:</label><input name="secondLastName" type="text" class="form-control" value="${model.secondLastName}"/>
                </div>
                <div class="form-group">
                <label>rut:</label><input name="rut" type="text" class="form-control" value="${model.rut}"/>
                </div>
                <div class="form-group">
                <label>serviceId:</label><input name="serviceId" type="text" class="form-control" value="${model.serviceId}"/>
                </div>
                <div class="form-group">
                <label>finalUrl:</label><input name="finalUrl" type="text" class="form-control" value="${model.finalUrl}"/>
                </div>
                <div class="form-group">
                <label>commerceCode:</label><input name="commerceCode" type="text" class="form-control" value="${model.commerceCode}"/>
                </div>
                <div class="form-group">
                <label>maxAmount:</label><input name="maxAmount" type="text" class="form-control" value="${model.maxAmount}"/>
                </div>
                <div class="form-group">
                <label>phoneNumber:</label><input name="phoneNumber" type="text" class="form-control" value="${model.phoneNumber}"/>
                </div>
                <div class="form-group">
                <label>mobileNumber:</label><input name="mobileNumber" type="text" class="form-control" value="${model.mobileNumber}"/>
                </div>
                <div class="form-group">
                <label>patpassName:</label><input name="patpassName" type="text" class="form-control" value="${model.patpassName}"/>
                </div>
                <div class="form-group">
                <label>personEmail:</label><input name="personEmail" type="text" class="form-control" value="${model.personEmail}"/>
                </div>
                <div class="form-group">
                <label>commerceEmail:</label><input name="commerceEmail" type="text" class="form-control" value="${model.commerceEmail}"/>
                </div>
                <div class="form-group">
                <label>address:</label><input name="address" type="text" class="form-control" value="${model.address}"/>
                </div>
                <div class="form-group">
                <label>city:</label><input name="city" type="text" class="form-control" value="${model.city}"/>
                </div>
                <input name="enviar" type="submit" value="Enviar" class="btn btn-primary" />
        </form>
    </div>
    </div>
<br>
<a href=".">&laquo; volver a index</a>
    <jsp:include page="../template/footer.jsp"/>
</body>
</html>