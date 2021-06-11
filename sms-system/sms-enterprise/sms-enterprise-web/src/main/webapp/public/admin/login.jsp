<%@ taglib prefix="ht" uri="/hero-tags" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<!DOCTYPE html>
<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <title>
        <ht:heropageconfigurationtext code="platform_name" defaultValue="" />
        客户平台
        <ht:heropageconfigurationtext code="platform_version" defaultValue="" />
    </title>
    <meta name="description" content="">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <link rel="stylesheet" href="/public/admin/css/reset.css" />
    <link rel="stylesheet" href="/public/admin/css/login.css" />
    <script src="/js/jquery-3.4.1.min.js"></script>
    <script src="/js/jquery-validation-1.19.0/dist/jquery.validate.js"></script>
    <script src="/js/jquery-validation-1.19.0/dist/localization/messages_zh.js"></script>
    <script src="/public/admin/js/three.min.js"></script>
    <script src="/public/admin/js/d3.js"></script>
    <script src="/js/jsencrypt/jsencrypt.min.js"></script>
    <script src="/js/jsencrypt/rsakey.js"></script>
</head>

<body>
    <div id="page">
        <div class="container">
            <div class="login-area no-compatibility">
                <div class="login-box">
                    <div class="box">
                        <form action="/admin/enterpriseLogin" method="post" id="dataForm" onsubmit="return encryptPassword();">
                            <div class="show-login">
                                <div class="title"><ht:heropageconfigurationtext code="platform_name" defaultValue="" />客户平台</div>
                                <div class="vision">
                                    <ht:heropageconfigurationtext code="platform_version" defaultValue="V1.0.0" />
                                </div>
                                <div class="input-box">
                                    <div class="input-left">
                                        <i class="icon icon-user"></i>
                                    </div>
                                    <input type="hidden" name="urlFlag" value="/public/admin/login.jsp">
                                    <input type="text" class="input required" placeholder="账号" id="J_username"
                                        name="user_Name">
                                </div>
                                <div class="input-box">
                                    <div class="input-left">
                                        <i class="icon icon-password"></i>
                                    </div>
                                    <input type="password" class="input required" placeholder="密码" id="J_password"
                                        name="web_Password">
                                    <div class="input-right" id="J_changeShow">
                                        <i class="icon icon-eye-open icon-eye-close"></i>
                                    </div>
                                </div>
                                <div class="input-box">
                                    <div class="input-left">
                                        <i class="icon icon-safe"></i>
                                    </div>
                                    <input type="text" class="input required" autocomplete="off" placeholder="验证码"
                                        id="J_captcha" name="validateCode">
                                    <div class="input-right">
                                        <img class="yzm" src="/Kaptcha.do" id="imgsrc" name="imgsrc"
                                            onclick="javaScript:change()" />
                                    </div>
                                </div>
                                <div class="errorMessage">
                                    <span id="error">${msg}</span>
                                </div>
                                <button class="login-btn">登录</button>
                                <a href="/public/admin/reg.jsp" class="login-btn">注册</a>
                            </div>
                        </form>
                    </div>
                </div>
                <div class="copy-right">
                    <ht:heropageconfigurationtext code="enterprise_home_information" defaultValue="请设置信息" />
                </div>
            </div>
        </div>
    </div>
    <script type="text/javascript">

        if(document.all && !document.addEventListener){
            alert("您当前使用IE版本太低，无法使用，请更新到9.0以上版本或使用其他浏览器！！！");
        }



        //避免登录页被嵌套
        $(function () {
            if (top.location.href != location.href) {
                top.location.href = location.href;
            }
        });
        function change() {
            document.getElementById("imgsrc").setAttribute("src", "/Kaptcha.do?" + Math.floor(Math.random() * 100));
        }

        function encryptPassword() {
            var encrypt = new JSEncrypt();
            encrypt.setPublicKey(rsaPublicKey);
            var encrypted = encrypt.encrypt($("#J_password").val());
            $("#J_password").val(encrypted);
            return true;
        }

        /*Deprecated*/
        function on_return() {
            if (window.event.keyCode == 13) {
                $("form").submit();
            }
        }




    </script>
</body>

</html>