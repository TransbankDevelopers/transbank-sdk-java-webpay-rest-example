<html>

<jsp:include page="template/tpl-header.jsp" />

<body class="container">

    <jsp:include page="template/navbar-index.jsp" />

    <div class="row">
        <div class="col-4">
            <img class="product" src="/images/wpplus.png">
        </div>
        <div class="col-8">
            <table class="table table-bordered">
                <tr><td><a class="nav-link tbk-link" aria-current="page" href="/webpay_plus/create">Webpay Plus</a></td></tr>
                <tr><td><a class="nav-link tbk-link" aria-current="page" href="/webpay_plus_deferred/create">Webpay Plus Diferido</a></td></tr>
                <tr><td><a class="nav-link tbk-link" aria-current="page" href="/webpay_plus_mall/create">Webpay Plus Mall</a></td></tr>
                <tr><td><a class="nav-link tbk-link" aria-current="page" href="/webpay_plus_mall_deferred/create">Webpay Plus Mall Diferido</a></td></tr>
            </table>
        </div>
    </div><br>

    <div class="row">
        <div class="col-8">
            <table class="table table-bordered">
                <tr><td><a class="nav-link tbk-link" aria-current="page" href="/oneclick_mall/start">Oneclick Mall</a></td></tr>
                <tr><td><a class="nav-link tbk-link" aria-current="page" href="/oneclick_mall_deferred/start">Oneclick Mall Diferido</a></td></tr>
            </table>
        </div>
        <div class="col-4">
            <img class="product" src="/images/oneclick.png">
        </div>
    </div><br>

    <div class="row">
        <div class="col-4">
            <img class="product" src="/images/txcompleta.png">
        </div>
        <div class="col-8">
            <table class="table table-bordered">
                <tr><td><a class="nav-link tbk-link" aria-current="page" href="/transaccion_completa/form">Transacción Completa</a></td></tr>
                <tr><td><a class="nav-link tbk-link" aria-current="page" href="/transaccion_completa_deferred/form">Transacción Completa Diferido</a></td></tr>
                <tr><td><a class="nav-link tbk-link" aria-current="page" href="/transaccion_completa_mall/form">Transacción Completa Mall</a></td></tr>
                <tr><td><a class="nav-link tbk-link" aria-current="page" href="/transaccion_completa_mall_deferred/form">Transacción Completa Mall Diferido</a></td></tr>
            </table>
        </div>
    </div><br>

    <div class="row">
        <div class="col-8">
            <table class="table table-bordered">
                <tr><td><a class="nav-link tbk-link" aria-current="page" href="/patpass_comercio/start">PatPass Comercio</a></td></tr>
            </table>
        </div>
        <div class="col-4">
            <img class="product" src="/images/patpass.png">
        </div>
    </div>

    <jsp:include page="template/footer.jsp" />
</body>
</html>
