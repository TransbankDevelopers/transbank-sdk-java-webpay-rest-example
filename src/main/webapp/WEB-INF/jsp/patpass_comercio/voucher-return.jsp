<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>

<jsp:include page="../template/tpl-header.jsp" />

<body class="container">
    <jsp:include page="../template/navbar-patpass.jsp" />

    <div class="row">
        <div class="col">
            <h2>Inscripción finalizada</h2>
            La inscripción ya se encuentra finalizada.
        </div>
    </div><br>


    <div class="row">
        <h2>Otras utilidades</h2>
        Luego de realizar la inscripcion puedes visualizar el voucher.
    </div><br>

    <div class="row">
        <div class="card">
          <div class="card-body">
            <h6 class="card-title">Formulario de redirección al voucher</h6>
            <form action="${details.get("url_voucher")}" method="POST">
              <div class="mb-3">
                <label for="tokenComercio" class="form-label">Token</label>
                <input type="text" class="form-control" name="tokenComercio" value="${details.get("token")}">
              </div>
              <button type="submit" class="btn btn-primary">VER VOUCHER</button>
            </form>
          </div>
        </div>
    </div><br>

    <jsp:include page="../template/footer.jsp" />

</body>
</html>