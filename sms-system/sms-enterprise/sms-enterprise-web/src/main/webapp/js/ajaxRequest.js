// type：请求类型    url：请求的url  param：请求参数    title：弹框标题    backUrl：回调地址，没有就传null
function ajaxRequest(type, url, param, title, backUrl) {
    var res = null;
    $.ajax({
        type: type,
        url: url,
        data: param,
        success: function (data) {
            data = $.parseJSON(data);
            console.log(data)
            if (data.code == '200') {
                res = data
                layer.alert(data.message, {
                    title: title,
                    icon: 1,
                    end: function () {
                        if (backUrl != null && backUrl != "") {
                            window.location.href = backUrl;
                        }
                    }
                });
            } else {
                res = data
                layer.alert(data.message, {
                    title: title,
                    icon: 2,
                    end: function () {

                    }
                });
            }
        },
    })
    return res;

}