<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/admin/common/common.jsp" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width,
                initial-scale=0.3, maximum-scale=2.3, minimum-scale=0.3, user-scalable=yes">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>
        <ht:heropageconfigurationtext code="platform_name" defaultValue="请设置名字"/>
        运营平台
        <ht:heropageconfigurationtext code="platform_version" defaultValue="请设置版本号"/>
    </title>
    <link rel="stylesheet" href="/public/css/reset.css">
    <link rel="stylesheet" href="/public/css/style.css">
    <script src="/js/jsencrypt/jsencrypt.min.js"></script>
    <script src="/js/jsencrypt/rsakey.js"></script>
    <script src="/js/jquery-3.4.1.min.js"></script>
</head>
<style>
    .errorMessage {
        list-style: none;
        color: red;
        margin-left: 120px;
        margin-bottom: 6px;
        font-size: 14px;
    }
</style>
<body>
<!-- 头部 -->
<header>
    <div class="header_box w1000">
        <span class="head_tit">
            <ht:heropageconfigurationtext code="platform_name" defaultValue="请设置名字"/>
            运营平台
            <ht:heropageconfigurationtext code="platform_version" defaultValue="请设置版本号"/>
        </span>
        <span class="head_rig fr">欢迎登录
            <ht:heropageconfigurationtext code="platform_name" defaultValue="请设置名字"/>
            运营平台
            <ht:heropageconfigurationtext code="platform_version" defaultValue="请设置版本号"/>
        </span>
    </div>
</header>
<!-- 内容部分 -->
<div class="con_wrap" id="con_wrap">
    <img src="/public/images/shape_bl.png" alt="" class="shape_bl ma">
    <div class="con_con w1000" id="con_con">
        <div class="con_con_in w1000">
            <img src="/public/images/left_pic.png" alt="">
            <form action="/public/login" method="post" onsubmit="return checkLogin();">
                <input type="text" name="user_Name" placeholder="请输入用户名" class="input1" autocomplete="off">
                <input type="password" name="password" placeholder="请输入密码" class="input2" autocomplete="off">
                <input type="text" class="input3" autocomplete="off" placeholder="请输入验证码"
                       id="J_captcha" name="validateCode">
                <div class="input-right">
                    <img class="yzm" src="/Kaptcha.do" id="imgsrc" name="imgsrc"
                         onclick="javaScript:change()" />
                </div>
                <p class="tip">${msg}</p>
                <button>登录</button>
            </form>
        </div>
    </div>
</div>
<footer>
    <ht:heropageconfigurationtext code="admin_home_information" defaultValue="请设置信息"/>
</footer>

<script type="text/javascript">
    function checkLogin() {
        var user_Name = $(".input1").val();
        if(CheckIsNullOrEmpty(user_Name)){
            $(".tip").html("请输入用户名")
            return false;
        }
        var password = $(".input2").val();
        if(CheckIsNullOrEmpty(password)){
            $(".tip").html("请输入密码")
            return false;
        }
        var validateCode = $(".input3").val();
        if(CheckIsNullOrEmpty(validateCode)){
            $(".tip").html("请输入验证码")
            return false;
        }
        // 密码RSA加密处理
        return encryptPassword();
    }

    function CheckIsNullOrEmpty(value) {
        //正则表达式用于判斷字符串是否全部由空格或换行符组成
        var reg = /^\s*$/
        //返回值为true表示不是空字符串
        return (value == null || value == undefined || reg.test(value))
    }

    if (window != top)
        top.location.href = location.href

    function on_return() {
        if (window.event.keyCode == 13) {
            $("form").submit();
        }
    }

    function change() {
        $("#imgsrc").attr("src", "/Kaptcha.do?" + Math.floor(Math.random() * 100));
    }

    function encryptPassword() {
        var encrypt = new JSEncrypt();
        encrypt.setPublicKey(rsaPublicKey);
        var encrypted = encrypt.encrypt($(".input2").val());
        $(".input2").val(encrypted);
        return true;
    }

    $(function () {
        var cssh = $(window).height() - 120 + "px";
        if (parseInt(cssh) < 380) {
            $(".con_wrap").height(380);
            $(".con_con").height(380);
        } else {
            $(".con_wrap").css("height", cssh);
            $(".con_con").css("height", cssh);
        }
        $(".input1").focus(function () {
            $(this).css({
                "background-image": "url(/public/images/user_sel.png)",
                "border": "1px solid #54aad7"
            });
        });
        $(".input1").blur(function () {
            $(this).css({
                "background-image": "url(/public/images/user_def.png)",
                "border": "1px solid #e5e5e5"
            });
        });
        $(".input2").focus(function () {
            $(this).css({
                "background-image": "url(/public/images/pass_sel.png)",
                "border": "1px solid #54aad7"
            });
        });
        $(".input2").blur(function () {
            $(this).css({
                "background-image": "url(/public/images/pass_def.png)",
                "border": "1px solid #e5e5e5"
            });
        });
        $(".input3").focus(function () {
            $(this).css("border", "1px solid #54aad7");
        });
        $(".input3").blur(function () {
            $(this).css("border", "1px solid #e5e5e5");
        });
    })
</script>
</body>
</html>