<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>

<jsp:include page="../template/tpl-header.jsp" />

<body class="container">
    <jsp:include page="../template/navbar-oneclick.jsp" />

    <div class="row">
        <div class="col">
            <h2>Finalizar inscripción</h2>
            En este paso terminaremos la inscripción, para luego poder hacer cargos cargos a la tarjeta que el tarjetahabiente inscriba.
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
        'token_ws': '${details.get("token")}'
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
import cl.transbank.webpay.common.WebpayOptions;
import cl.transbank.webpay.oneclick.Oneclick;
import cl.transbank.webpay.oneclick.model.*;
import cl.transbank.webpay.oneclick.responses.*;
//fin sección de importación

Oneclick.MallInscription inscription = new Oneclick.MallTransaction(new WebpayOptions(IntegrationCommerceCodes.ONECLICK_MALL, IntegrationApiKeys.WEBPAY, IntegrationType.TEST));
OneclickMallInscriptionFinishResponse response = inscription.finish(token);

                </code>
            </pre>
        </div>
        <div class="col-6">
            <h4>Petición</h4>
            Usaras el token recibido para autorizar la transacción usando nuevamente Oneclick
        </div>
    </div><br>


    <div class="row">
        <div class="col-6">
            <h4>Respuesta</h4>
            Transbank contestará con lo siguiente. Debes guardar esta información. Para poder luego autorizar transacciones vas a necesitar el 'tbk_user' y 'username'
        </div>
        <div class="col-6">
            <pre>
                <code class="language-json">
${details.get("resp")}
                </code>
            </pre>
        </div>
    </div><br>

    <br>
    <div class="row">
        <h2>La tarjeta ya está inscrita!</h2>
    </div>

    <div class="row">
        <h4>Autorizar una transacción</h4>
        Para autorizar una transacción debes guardar datos de la respuesta necesarios
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
                <td>TBK User (tbk_user)</td>
                <td>${details.get("tbk_user")}</td>
            </tr>
        </table>
    </div>
    <div class="row">
        <div class="col-6">
                <form action="${details.get("delete-endpoint")}" method="POST">
                    <input type="hidden" name="username" value="${details.get("username")}">
                    <input type="hidden" name="tbk_user" value="${details.get("tbk_user")}">
                    <button type="submit" class="btn btn-primary">BORRAR USUARIO</button>
                </form>
         </div>
        <div class="col-6">
            <form action="${details.get("authorize-endpoint")}" method="POST">
                <input type="hidden" name="username" value="${details.get("username")}">
                <input type="hidden" name="tbk_user" value="${details.get("tbk_user")}">
                <div class="card">
                  <div class="card-body">
                    <h6 class="card-title">Formulario de autorización</h6>
                    <div class="form-floating mb-3">
                      <input type="text" class="form-control" name="amount1" id="amount1" value="${details.get("amount1")}">
                      <label for="amount1">Monto a autorizar Comercio 1</label>
                    </div>
                    <div class="form-floating mb-3">
                      <input type="text" class="form-control" name="installments1" id="installments1" value="${details.get("installments1")}">
                      <label for="installments1">Cuotas Comercio 1</label>
                    </div>
                    <div class="form-floating mb-3">
                      <input type="text" class="form-control" name="amount2" id="amount2" value="${details.get("amount2")}">
                      <label for="amount2">Monto a autorizar Comercio 2</label>
                    </div>
                    <div class="form-floating mb-3">
                      <input type="text" class="form-control" name="installments2" id="installments2" value="${details.get("installments2")}">
                      <label for="installments2">Cuotas Comercio 2</label>
                    </div>
                    <button type="submit" class="btn btn-primary">AUTORIZAR PAGO</button>
                  </div>
                </div>
            </form>
        </div>

    </div><br>

    <jsp:include page="../template/footer.jsp" />
    <script>

    </script>
</body>
</html>