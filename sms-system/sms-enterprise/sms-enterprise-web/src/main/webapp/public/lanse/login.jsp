<%@ taglib prefix="ht" uri="/hero-tags" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <title>
        <ht:heropageconfigurationtext code="platform_name" defaultValue="信相通"/>
        客户平台
        <ht:heropageconfigurationtext code="platform_version" defaultValue="V2.0.0"/>
    </title>
    <meta name="description" content="">
    <meta name="viewport" content="width=1200">
    <link href="/public/lanse/css/login.css" type="text/css" rel="stylesheet">
</head>
<body>

<div class="login">
    <div class="message">
        <ht:heropageconfigurationtext code="platform_name" defaultValue="信相通"/>
        客户平台
        <ht:heropageconfigurationtext code="platform_version" defaultValue="V2.0.0"/>
        -企业平台
    </div>
    <div id="darkbannerwrap"></div>

    <form action="/admin/enterpriseLogin" method="post" id="dataForm">

        <input type="text" class="input required" placeholder="账号" id="J_username"
               name="user_Name">
		<hr class="hr15">
        <input type="password" class="input required" placeholder="密码" id="J_password"
               name="web_Password">
        <input type="hidden" name="urlFlag" value="/public/lanse/login.jsp">
		<hr class="hr15">

        <input type="text" class="input required" autocomplete="off" placeholder="验证码"
               id="J_captcha" name="validateCode" style="width:80%;" >
		<img class="yzm" src="/Kaptcha.do" id="imgsrc" name="imgsrc"
                 onclick="javaScript:change()"/>

		<hr class="hr15">

        <span style="color: red;">${msg}<hr class="hr15"></span>

        <input value="登录" style="width:50%;" type="submit">&nbsp;&nbsp;
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        <a href="/public/lanse/reg.jsp" style="width:50%;">注 册</a>

    </form>

</div>

<div class="copyright">
    <ht:heropageconfigurationtext code="enterprise_home_information" defaultValue="请设置信息"/>
</div>

</body>
</html>
<script type="text/javascript">
	function change() {
		document.getElementById("imgsrc").setAttribute("src", "/Kaptcha.do?" + Math.floor(Math.random() * 100));
	}
</script>