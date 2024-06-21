<head>
    <meta charset="UTF-8">

</head>
<jsp:include page="../template/tpl-header.jsp" />

<body class="container">
    <jsp:include page="../template/navbar-webpayplus.jsp" />

<style>
	.row {
		padding-bottom: 20px;
	}
</style>

<div class="row">
	<div class="col">
		 <h2>Compra cancelada por el usuario</h2>
         <p>Luego de que se anula la compra en el formulario de pago recibirás un GET con lo siguiente:</p>
	</div>
	<div class="row">
                <div class="col m4">
                    <pre class="z-depth-2">
                          <code class="language-java">
                            {
                                   'TBK_TOKEN': '${details.tbkToken}',
                                   'TBK_ORDEN_COMPRA': '${details.buyOrder}',
                                   'TBK_ID_SESION': '${details.sessionId}',
                            }
                        </code>
                    </pre>
                </div>
            </div>
</div>
<jsp:include page="../template/footer.jsp" />

</body>
</html>
