<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Ejemplos Webpay Plus Modal</title>
    <jsp:include page="../template/header.jsp"/>
</head>

<body>
    <script type="text/javascript" src="https://webpay3gint.transbank.cl/webpayserver/webpay.js"></script>
    <script>
        function closeModal(){
            let myModalEl = document.getElementById('staticBackdrop');
            let modal = bootstrap.Modal.getInstance(myModalEl);
            modal.hide();
        }
        function fnOk(token){
            alert('Ok');
            closeModal();
        }
        function fnError(token){
            alert('Error');
            closeModal();
        }
        function showWebpay(token, codigo_error, descripcion_error){
            document.getElementById('webpay_div').innerHTML = null;
            Webpay.show("webpay_div", token, fnOk, fnError);
        }
    </script>

    <nav aria-label="breadcrumb">
        <ol class="breadcrumb">
            <li class="breadcrumb-item"><a href="#">Ejemplos Webpay Plus Modal</a></li>
            <li class="breadcrumb-item active" aria-current="page">Step: <strong>Create Transaction</strong></li>
        </ol>
    </nav>


    <h3>request</h3>
    <pre><code class="language-json">${details.get("req")}</code></pre>

    <h3>result</h3>
    <pre><code class="language-json">${details.get("resp")}</code></pre>



    <button type="button" onclick="showWebpay('${details.get("token")}')" data-bs-toggle="modal" data-bs-target="#staticBackdrop" class="btn btn-outline-dark">Ejecutar Pago</button>
    <a href="/" class="btn btn-outline-dark">Regresar</a>

    <!-- Modal -->
    <div class="modal fade" id="staticBackdrop" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
      <div class="modal-dialog modal-lg">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title" id="staticBackdropLabel">Webpay Modal</h5>
            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
          </div>
          <div class="modal-body">
                <div id="webpay_div" style="margin: 0 auto; height:435px !important">
          </div>
        </div>
      </div>
    </div>
</body>
</html>