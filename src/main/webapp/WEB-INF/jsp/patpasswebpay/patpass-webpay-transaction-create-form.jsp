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
        <form id="formulario" action="create" method="POST">
                <legend>Formulario de Creaci&oacute;n</legend>
                <div class="form-group">
                    <label>buyOrder:</label><input name="buyOrder" type="text" class="form-control" value="${model.buyOrder}"/>
                </div>
                <div class="form-group">
                <label>sessionId:</label><input name="sessionId" type="text" class="form-control" value="${model.sessionId}"/>
                </div>
                <div class="form-group">
                <label>returnUrl:</label><input name="returnUrl" type="text" class="form-control" value="${model.returnUrl}"/>
                </div>
                <div class="form-group">
                <label>serviceId:</label><input name="serviceId" type="text" class="form-control" value="${model.serviceId}"/>
                </div>
                <div class="form-group">
                <label>cardHolderId:</label><input name="cardHolderId" type="text" class="form-control" value="${model.cardHolderId}"/>
                </div>
                <div class="form-group">
                <label>cardHolderName:</label><input name="cardHolderName" type="text" class="form-control" value="${model.cardHolderName}"/>
                </div>
                <div class="form-group">
                <label>cardHolderLastName1:</label><input name="cardHolderLastName1" type="text" class="form-control" value="${model.cardHolderLastName1}"/>
                </div>
                <div class="form-group">
                <label>cardHolderLastName2:</label><input name="cardHolderLastName2" type="text" class="form-control" value="${model.cardHolderLastName2}"/>
                </div>
                <div class="form-group">
                <label>cardHolderMail:</label><input name="cardHolderMail" type="text" class="form-control" value="${model.cardHolderMail}"/>
                </div>
                <div class="form-group">
                <label>cellphoneNumber:</label><input name="cellphoneNumber" type="text" class="form-control" value="${model.cellphoneNumber}"/>
                </div>
                <div class="form-group">
                <label>expirationDate:</label><input name="expirationDate" type="text" class="form-control" value="${model.expirationDate}"/>
                </div>
                <div class="form-group">
                <label>commerceMail:</label><input name="commerceMail" type="text" class="form-control" value="${model.commerceMail}"/>
                </div>

                <input name="enviar" type="submit" value="Enviar" class="btn btn-primary" />
        </form>
    </div>
    </div>
<br>
<a href="/">&laquo; volver a index</a>
    <jsp:include page="../template/footer.jsp"/>
</body>
</html>