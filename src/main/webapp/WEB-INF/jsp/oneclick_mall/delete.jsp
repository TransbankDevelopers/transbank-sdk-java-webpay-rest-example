<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>

<jsp:include page="../template/tpl-header.jsp" />

<body class="container">
    <jsp:include page="../template/navbar-oneclick.jsp" />

    <div class="row">
        <div class="col">
            <h2>Eliminar inscripci�n</h2>
            En este paso eliminaremos la inscripci�n.
        </div>
    </div><br>

    <div class="row">
        <div class="col-6">
            <h4>Petici�n</h4>
            Necesitas el userName (Nombre de Usuario) y el tbkUser y llamar a Oneclick.MallInscription.
        </div>
        <div class="col-6">
            <pre>
                <code class="language-java">

//inicio secci�n de importaci�n
import cl.transbank.common.IntegrationApiKeys;
import cl.transbank.common.IntegrationCommerceCodes;
import cl.transbank.common.IntegrationType;
import cl.transbank.webpay.common.WebpayOptions;
import cl.transbank.webpay.oneclick.Oneclick;
import cl.transbank.webpay.oneclick.model.*;
import cl.transbank.webpay.oneclick.responses.*;
//fin secci�n de importaci�n

Oneclick.MallInscription inscription = new Oneclick.MallTransaction(new WebpayOptions(IntegrationCommerceCodes.ONECLICK_MALL, IntegrationApiKeys.WEBPAY, IntegrationType.TEST));
inscription.delete(tbkUser, username);

                </code>
            </pre>
        </div>
    </div><br>


    <div class="row">
        <div class="col-6">
            <h4>Respuesta</h4>
            En caso de �xito Transbank responder� con un status code 204 (No Content) y el sdk no retornar� respuesta. En el caso de no encontrarse el userName o tbkUser Transbank responder� con un status code 404 (Not Found) y el sdk retornara una excepci�n.
        </div>
    </div><br>

    <jsp:include page="../template/footer.jsp" />

</body>
</html>