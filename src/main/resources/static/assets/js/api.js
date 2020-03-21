window.Api = {
    // GET 请求
    get: function(url, queryParams, cb, errorCb) {
        axios({
            method: 'get',
            url: url,
            data: queryParams,
            responseType: 'json',
            headers: {
                "Content-Type": "application/json"
            }
        }).then(function (result) {
            cb(result.data)
        }).catch(function (error) {
            errorCb(error)
        });

    },
    // POST请求
    post: function(url, queryParams, cb, errorCb) {
        axios({
            method: 'post',
            url: url,
            data: queryParams,
            responseType: 'json',
            headers: {
                "Content-Type": "application/json"
            }
        }).then(function (result) {
            cb({
                data: result.data,
            })
        }).catch(function (error) {
            errorCb(error)
        });

    },

    loginPageUrl: '/user/', // 登录页面
    loginSuccessPageUrl: '/user/login/success',

    loginUrl: '/user/login',// 登录接口
    registerUrl: '',//
}