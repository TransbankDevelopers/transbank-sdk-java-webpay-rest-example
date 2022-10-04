<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>

<jsp:include page="../template/tpl-header.jsp" />

<body class="container">
    <jsp:include page="../template/navbar-patpass.jsp" />

    <div class="row">
        <div class="col">
            <h2>Inscribir Servicio</h2>
            En este paso inscribiremos una tarjeta con el objetivo de obtener un identificador unico y poder en el siguiente paso redirigir al Tarjetahabiente hacia el formulario de inscripción
        </div>
    </div><br>

    <div class="row">
        <div class="col-6">
            <h4>Petición</h4>
            Para comenzar debes importar PatpassComercio y luego comenzar una inscripción
            Tener en cuenta que actualmente el ambiente de integración no soporta direcciones locales (como localhost, 127.0.0.1, 192.168.*.*) en los atributos en los atributos 'url' y 'finalUrl'.
        </div>
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

PatpassComercioInscriptionStartResponse response = inscription.start(
                    returnUrl,
                    name,
                    lastName,
                    secondLastName,
                    rut,
                    serviceId,
                    finalUrl,
                    maxAmount,
                    phone,
                    cellPhone,
                    patpassName,
                    personEmail,
                    commerceEmail,
                    address,
                    city);

                </code>
            </pre>
        </div>
    </div><br>


    <div class="row">
        <div class="col-6">
            <pre>
                <code class="language-json">
${details.get("resp")}
                </code>
            </pre>
        </div>
        <div class="col-6">
            <h4>Respuesta</h4>
            Una vez iniciada la inscripción, estos serán los datos de respuesta
        </div>
    </div><br>


    <div class="row">
        <div class="col-6">
            <h4>Por último</h4>
            Debes utilizar estos datos para crear un formulario
        </div>
        <div class="col-6">
            <pre>
                <code class="language-html">
&lt;form action="${details.get("url_webpay")}" method="POST">
   &lt;input type="hidden" name="tokenComercio" value="${details.get("tbk_token")}"/>
   &lt;input type="submit" value="Inscribir"/>
&lt;/form>
                </code>
            </pre>
        </div>
    </div><br>


    <br>
    <div class="row">
        <h2>Ejemplo</h2>
    </div>

    <div class="row">
        <div class="col-6">
            <h4>Iniciamos la inscripción</h4>
            Usando los siguientes datos
            <table class="table table-bordered">
                <tr>
                    <th>Campo</th>
                    <th>Valor</th>
                </tr>
                <tr>
                    <td>Url de retorno luego de realizar la inscripción</td>
                    <td>${details.get("returnUrl")}</td>
                </tr>
                <tr>
                    <td>Nombre del Cliente</td>
                    <td>${details.get("name")}</td>
                </tr>
                <tr>
                    <td>Apellido del Cliente</td>
                    <td>${details.get("firstLastName")}</td>
                </tr>
                <tr>
                    <td>Segundo Apellido del Cliente</td>
                    <td>${details.get("secondLastName")}</td>
                </tr>
                <tr>
                    <td>Rut del Cliente</td>
                    <td>${details.get("rut")}</td>
                </tr>
                <tr>
                    <td>Id del Servicio </td>
                    <td>${details.get("serviceId")}</td>
                </tr>
                <tr>
                    <td>Url de redirección luego de generar el voucher</td>
                    <td>${details.get("finalUrl")}</td>
                </tr>
                <tr>
                    <td>Monto máximo de pago</td>
                    <td>${details.get("maxAmount")}</td>
                </tr>
                <tr>
                    <td>Teléfono del contacto</td>
                    <td>${details.get("phoneNumber")}</td>
                </tr>
                <tr>
                    <td>Celular del contacto</td>
                    <td>${details.get("mobileNumber")}</td>
                </tr>
                <tr>
                    <td>Nombre de la inscripción en Patpass	</td>
                    <td>${details.get("patpassName")}</td>
                </tr>
                <tr>
                    <td>Correo del contacto</td>
                    <td>${details.get("personEmail")}</td>
                </tr>
                <tr>
                    <td>Correo del comercio	</td>
                    <td>${details.get("commerceEmail")}</td>
                </tr>
                <tr>
                    <td>Correo del comercio	</td>
                    <td>${details.get("address")}</td>
                </tr>
                <tr>
                    <td>Ciudad del contacto</td>
                    <td>${details.get("city")}</td>
                </tr>
            </table>
        </div>
        <div class="col-6">
            <h4>Por último</h4>
            Con la respuesta del servicio creamos el formulario, para efectos del ejemplo haremos el campo token_ws visible
            <div class="card">
              <div class="card-body">
                <h6 class="card-title">Formulario de redirección</h6>
                <form action="${details.get("url_webpay")}" method="POST">
                  <div class="mb-3">
                    <label for="token_ws" class="form-label">token_ws</label>
                    <input type="text" class="form-control" name="tokenComercio" value="${details.get("tbk_token")}">
                  </div>
                  <button type="submit" class="btn btn-primary">INSCRIBIR</button>
                </form>
              </div>
            </div>

        </div>
    </div><br>

    <jsp:include page="../template/footer.jsp" />

</body>
</html>