function get(method, fnOk, fnError) {
    axios.get(method, {
        responseType: 'json'
    })
        .then(function (res) {
            if (res.status == 200) {
                console.log(res.data);
                //mensaje.innerHTML = res.data.title;

                if (res.data.ErrorMessage) {
                    fnError(res.data.ErrorMessage);
                    return;
                }

                console.log(res);
                if (fnOk)
                    fnOk(res);
            }
        })
        .catch(function (err) {
            console.log(err);
            fnError(err);
        })
        .then(function () {
            //loading.style.display = 'none';
        });
}


function post(method, data, fnOk, fnError) {
    axios.post(method, data)
        .then(function (res) {
            if (res.status == 200) {
                console.log(res.data);

                if (res.data.ErrorMessage) {
                    fnError(res.data.ErrorMessage);
                    return;
                }

                //mensaje.innerHTML = res.data.title;
                console.log(res);
                if (fnOk)
                    fnOk(res);
            }
        })
        .catch(function (err) {
            console.log(err);
            fnError(err);
        })
        .then(function () {
            //loading.style.display = 'none';
        });
}