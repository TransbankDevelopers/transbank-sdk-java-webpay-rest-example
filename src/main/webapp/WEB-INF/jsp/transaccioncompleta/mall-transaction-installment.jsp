    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Ejemplos Transaccion Completa Mall - Cuotas Transaccion</title>
        <jsp:include page="../template/header.jsp"/>
    </head>

        <body class="container">
        <nav aria-label="breadcrumb">
        <ol class="breadcrumb">
        <li class="breadcrumb-item"><a href="#">Ejemplos Transaccion Completa Mall - Procesando Cuotas</a></li>
        <li class="breadcrumb-item active" aria-current="page">Step: <strong>Cuotas Transaction</strong></li>
        </ol>
        </nav>

        <div  class="d-flex align-items-center">
        <div id="spinnerId">
        <strong>Procesando Cuotas...</strong>
        <div   class="spinner-border ml-auto" role="status" aria-hidden="true"></div>
        </div>
        </div>


        <br>
        <div class="card" >
        <div class="card-body">
        <div style="display: none;" id="cardId">
        <form action="/mallfulltransaction/commit" method="POST">
        <div class="form-row">
        <input type="hidden" name="token" value="${model.token}">
        <input type="hidden" name="idQueryInstallments" value="${model.response.responseList[0].idQueryInstallments}">
        <input type="hidden" name="buyOrder" value="${model.buyOrder}">
        <input type="hidden" name="commerceCode" value="${model.commerceCode}">

        </div>

        <div class="alert alert-success" role="alert">
        Se han procesado las cuotas exitosamente
        </div>
        <div class="alert alert-primary" role="alert">
        <h3>Response:</h3>
        ${model.response.responseList[0]}
        </div>
        <br>
        <button id="confirmBtn" type="submit" class="btn btn-primary">Confirmar Transaccion</button>
        </form>
        </div>
        </div>
        </div>
        <br>

        <a href="/">&laquo; volver a index</a>


        </body>
        <script>
            $( document ).ready(function() {
                setTimeout(
                function(){
                console.log("delay");
                $( "#spinnerId" ).hide( "slow", function() {

                });
                $("#cardId").css("display", "block");
                }, 1000);
            });
        </script>

</html>