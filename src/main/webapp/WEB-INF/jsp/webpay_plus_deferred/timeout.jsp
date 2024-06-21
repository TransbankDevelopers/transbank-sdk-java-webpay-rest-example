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
		 <h2>Timeout (Tiempo excedido en el formulario de Webpay)</h2>
         <p>El tiempo es de 4 minutos para el ambiente de producción y 10 minutos para el entorno de integración. Llegará solamente TBK_ID_SESION que contiene el session_id enviado al crear la transacción, TBK_ORDEN_COMPRA que representa el buy_order enviado. No llegará token.</p>
	</div>
	<div class="row">
                <div class="col m4">
                    <pre class="z-depth-2">
                          <code class="language-java">
                            {
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