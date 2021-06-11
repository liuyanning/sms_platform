// 扩展form 新增方法 init
layui.define(['form'], function (exports) {
    var $ = layui.$, form = layui.form;
    var formExt = $.extend({}, form);
    // 自定义初始化
    formExt.init = function (params) {
        if (!params) {
            params = {};
        }
        // 默认post
        params.urlType = params.urlType ? params.urlType : "post";
        // 判定事件 默认标签属性lay-submit lay-filter="submit"
        params.event = params.event ? params.event : 'submit(submit)';
        // 获取当前窗口索引
        var index = parent.layer.getFrameIndex(window.name);
        form.on(params.event, function (data) {
        	//提交之前
        	if(params.beforeSubmitEvent){
        		params.beforeSubmitEvent(data);
        	}
            var loading= parent.layer.load('', {time: 10*1000}); //遮罩层
            $.ajax({
                url: data.form.action,
                type: params.urlType,
                data: data.field,
                dataType: "json",
                success: function (res) {
                    parent.layer.close(loading);//关闭遮罩层
                    //超时
                    if (res.code == '301') {
                        layer.msg(res.msg, {icon: 2, time: 2000}, function () {
                            top.location.href = res.url;
                            return;
                        });
                    }
                    //增加外部扩展
                    if (params.success) {
                        params.success(res);
                    }
                    parent.layer.msg(res.msg);
                    if (res.code == '0') {
                        // 刷新父级表格 默认tableID都为list_table
                        parent.layui.table.reload('list_table');
                        // 关闭弹窗
                        parent.layer.close(index);
                    }
                },
                error: function (XMLHttpRequest, textStatus, errorThrown) {
                    parent.layer.close(loading);//关闭遮罩层
                    layer.msg("请求异常：" + XMLHttpRequest.status + ",错误描述：" + textStatus, function () {
                    });
                    //增加外部扩展
                    if (params.error) {
                        params.error(XMLHttpRequest, textStatus, errorThrown);
                    }
                }
            });
        });
    };
    // 新增额外校验属性 lay-verify="required|confirmPassword"
    formExt.verify({
        password: [
            /^[\S]{6,12}$/,
            '密码必须6到12位，且不能出现空格'
        ],
        editPassword: [
            /(^$)|[\S]{6,12}$/,
            '密码必须6到12位，且不能出现空格'
        ],
        userName: [
            /^[0-9a-zA-Z\u4E00-\u9FA5\\s]{2,16}$/,
            '登陆名含有空格等非法字符'
        ],
        confirmPassword: function (val) {
            var password = $("#password").val();
            if (password != val) {
                return "两次密码不一致";
            }
            if(typeof encryptPassword === "function") { //FunName为函数名称
                encryptPassword();
            }
        }
    })

    //输出接口
    exports('formExt', formExt);
});