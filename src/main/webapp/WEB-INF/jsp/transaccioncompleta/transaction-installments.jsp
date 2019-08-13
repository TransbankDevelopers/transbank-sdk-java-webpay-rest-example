<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Ejemplos Webpay Plus - Crear Transaccion</title>
    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
</head>

<body class="container">
<h2>Ejemplos Transaccion Completa - Procesando Cuotas</h2>

<h3>Step: Cuotas</h3>

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
            <form action="/fulltransaction/commit" method="POST">
                <div class="form-row">
                    <input type="hidden" name="token" value="${model.token}">
                    <input type="hidden" name="idQueryInstallments" value="${model.response.idQueryInstallments}">

                </div>
                <p>Se han procesado las cuotas exitosamente</p>
                <div style="background-color:lightgrey;">
                <h3>Response:</h3>
                ${model.response}
                </div>
                <br>
                <button id="confirmBtn" type="submit" class="btn btn-primary">Confirmar Transaccion</button>
            </form>
         </div>
        </div>
    </div>
    <br>

<a href="/">&laquo; volver a index</a>

    <!-- jQuery first, then Popper.js, then Bootstrap JS -->
    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
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