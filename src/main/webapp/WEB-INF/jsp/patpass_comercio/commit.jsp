<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>

<jsp:include page="../template/tpl-header.jsp" />

<body class="container">
    <jsp:include page="../template/navbar-patpass.jsp" />

    <div class="row">
        <div class="col">
            <h2>Confirmar Registro</h2>
            Es necesario confirmar el registro, este solo se puede hacer una sola vez o retornara error.
        </div>
    </div><br>

    <div class="row">
        <div class="col-6">
            <h4>Datos recibidos</h4>
            Luego de que se termina el flujo en el formulario de inscripción recibirás un POST con lo siguiente
        </div>
        <div class="col-6">
            <pre>
                <code class="language-java">
{
    'J_TOKEN': '${details.get("token")}'
}
                </code>
            </pre>
        </div><br>

    <div class="row">
        <div class="col-6">
            <pre>
                <code class="language-java">

//inicio sección de importación
import cl.transbank.common.IntegrationApiKeys;
import cl.transbank.common.IntegrationCommerceCodes;
import cl.transbank.common.IntegrationType;
import cl.transbank.patpass.PatpassComercio;
import cl.transbank.patpass.model.PatpassOptions;
import cl.transbank.patpass.responses.*;
//fin sección de importación

PatpassComercio.Inscription inscription = new PatpassComercio.Inscription(new PatpassOptions(IntegrationCommerceCodes.PATPASS_COMERCIO, IntegrationApiKeys.PATPASS_COMERCIO, IntegrationType.TEST));

PatpassComercioTransactionStatusResponse response = inscription.status(token);

                </code>
            </pre>
        </div>
        <div class="col-6">
            <h4>Petición</h4>
            Usaras el token recibido para confirmar la inscripción usando nuevamente PatpassComercio
        </div>
    </div><br>

    <div class="row">
        <div class="col-6">
            <h4>Respuesta</h4>
            Transbank contestará con lo siguiente. Debes guardar esta información, lo único que debes validar es que el atributo authorized sea igual a true.
        </div>
        <div class="col-6">
            <pre>
                <code class="language-json">
${details.get("resp")}
                </code>
            </pre>
        </div>

    </div><br>







    <div class="row">
        <h2>Otras utilidades</h2>
        Luego de realizar la inscripcion puedes visualizar el voucher.
    </div>


    <div class="row">
        <div class="col-6">
            <h4>Visualizar  voucher</h4>
            Puedes utilizar estos datos para crear un formulario para redireccionar al voucher
        </div>
        <div class="col-6">
            <pre>
                <code class="language-html">
&lt;form action="${details.get("url_voucher")}" method="POST">
   &lt;input type="hidden" name="tokenComercio" value="${details.get("token")}"/>
   &lt;input type="submit" value="Ver Voucher"/>
&lt;/form>
                </code>
            </pre>
        </div>
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