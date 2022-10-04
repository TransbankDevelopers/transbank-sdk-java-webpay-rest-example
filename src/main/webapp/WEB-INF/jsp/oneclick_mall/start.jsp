<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>

<jsp:include page="../template/tpl-header.jsp" />

<body class="container">
    <jsp:include page="../template/navbar-oneclick.jsp" />

    <div class="row">
        <div class="col">
            <h2>Comenzar inscripción</h2>
            En este paso comenzaremos la inscripción para poder en el siguiente paso redirigir al Tarjetahabiente hacia el formulario de inscripción de Oneclick
        </div>
    </div><br>

    <div class="row">
        <div class="col-6">
            <h4>Petición</h4>
            Para comenzar debes importar Oneclick y luego comenzar una inscripción
        </div>
        <div class="col-6">
            <pre>
                <code class="language-java">

//inicio sección de importación
import cl.transbank.common.IntegrationApiKeys;
import cl.transbank.common.IntegrationCommerceCodes;
import cl.transbank.common.IntegrationType;
import cl.transbank.webpay.common.WebpayOptions;
import cl.transbank.webpay.oneclick.Oneclick;
import cl.transbank.webpay.oneclick.model.*;
import cl.transbank.webpay.oneclick.responses.*;
//fin sección de importación

Oneclick.MallInscription inscription = new Oneclick.MallTransaction(new WebpayOptions(IntegrationCommerceCodes.ONECLICK_MALL, IntegrationApiKeys.WEBPAY, IntegrationType.TEST));

String username = "User-" + getRandomNumber();
String email = "user." + getRandomNumber() + "@example.cl";
String returnUrl = request.getRequestURL().toString().replace("start","finish");

OneclickMallInscriptionStartResponse response = inscription.start(username, email, returnUrl);

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
&lt;form action="${details.get("token")}" method="POST">
   &lt;input type="hidden" name="TBK_TOKEN" value="${details.get("token")}"/>
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
                    <td>Nombre de Usuario (userName)</td>
                    <td>${details.get("username")}</td>
                </tr>
                <tr>
                    <td>Email</td>
                    <td>${details.get("email")}</td>
                </tr>
                <tr>
                    <td>URL de respuesta (responseUrl)</td>
                    <td>${details.get("returnUrl")}</td>
                </tr>
            </table>
        </div>
        <div class="col-6">
            <h4>Por último</h4>
            Con la respuesta del servicio creamos el formulario, para efectos del ejemplo haremos el campo TBK_TOKEN visible
            <div class="card">
              <div class="card-body">
                <h6 class="card-title">Formulario de redirección</h6>
                <form action="${details.get("url")}" method="POST">
                  <div class="mb-3">
                    <label for="TBK_TOKEN" class="form-label">token_ws</label>
                    <input type="text" class="form-control" name="TBK_TOKEN" value="${details.get("token")}">
                  </div>
                  <button type="submit" class="btn btn-primary">INSCRIBIR</button>
                </form>
              </div>
            </div>

        </div>
    </div><br>

    <jsp:include page="../template/footer.jsp" />
    <script>
        window.localStorage.setItem('username', '${details.get("username")}');
    </script>
</body>
</html>