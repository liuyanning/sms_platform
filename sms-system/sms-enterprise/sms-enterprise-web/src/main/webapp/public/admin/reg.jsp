<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<%@ taglib prefix="ht" uri="/hero-tags" %>
<%@ include file="/common/layui_head.html" %>
<link rel="stylesheet"
      href='<ht:heropageconfigurationtext code="enterprise_webpage_css" defaultValue="/layuiadmin"/>/style/login.css'
      media="all">
<script src='<ht:heropageconfigurationtext code="enterprise_webpage_css" defaultValue="/layuiadmin"/>/lib/admin.js'
        charset="utf-8"></script>
<style>
    body {
        width: 100%;
        height: 100%;
    }
    .bottom_information{
        position: fixed;
        bottom: 0px;
        width: 100%;
        text-align: center;
        font-size: 14px;
        color: #eee;
        line-height: 3;
    }
</style>
<script src="/js/jquery-3.4.1.min.js"></script>
<script src="/public/admin/js/three.min.js"></script>
<script src="/public/admin/js/d3.js"></script>
<script src="/public/admin/js/reg.min.js"></script>

<body>
<div class="layadmin-user-login layadmin-user-display-show" id="LAY-user-login">
    <div class="layadmin-user-login-main" style="margin-top: -2%;">
        <div class="layadmin-user-login-box layadmin-user-login-header" style="height: 32px">
            <h3>
                <ht:heropageconfigurationtext code="platform_name" defaultValue=""/>客户平台
            </h3>
            <p>
                <ht:heropageconfigurationtext code="platform_version" defaultValue=""/>
            </p>
        </div>
        <div class="layadmin-user-login-box layadmin-user-login-body layui-form">
            <form id="layuiForm" method="POST">
                <div class="layui-form-item">
                    <label class="layadmin-user-login-icon layui-icon layui-icon-username"></label>
                    <input type="text" name="enterpriseName" maxlength="128" id="enterpriseName" placeholder="企业名称"
                           class="layui-input" required="required">
                </div>
                <div class="layui-form-item">
                    <div class="layui-col-xs3">
                        <input type="text" class="layui-input " maxlength="4" name="countryCodeValue"
                               id="countryCode" value="86" placeholder="国家码"/>
                    </div>
                    <div class="layui-col-xs8" style="margin-left: 10px;">
                        <label class="layadmin-user-login-icon layui-icon layui-icon-cellphone"></label>
                        <input type="text" maxlength="11" name="user_Name" id="phoneNo" lay-verify="phone"
                               placeholder="手机号" class="layui-input">
                    </div>
                </div>
                <div class="layui-form-item">
                    <div class="layui-col-xs7">
                        <label class="layadmin-user-login-icon layui-icon layui-icon-vercode"></label>
                        <input type="text" name="graphValidateCode" id="graphValidateCode" lay-verify="required"
                               placeholder="图形验证码" class="layui-input">
                    </div>
                    <div class="layui-col-xs5">
                        <div style="margin-left: 10px;width:calc(100% - 10px); border: 1px solid #ddd;">
                            <img class="yzm" src="/Kaptcha.do" id="imgsrc" name="imgsrc"
                                 style="width: 100%;height: 38px"
                                 onclick="javaScript:change()"/>
                        </div>
                    </div>
                </div>
                <div class="layui-form-item">
                    <div class="layui-col-xs7">
                        <label class="layadmin-user-login-icon layui-icon layui-icon-vercode"></label>
                        <input type="text" name="smsValidateCode" id="verifyCode" lay-verify="required"
                               placeholder="手机验证码" class="layui-input">
                    </div>
                    <div class="layui-col-xs5">
                        <div style="margin-left: 10px;">
                            <button type="button" class="layui-btn layui-btn-primary layui-btn-fluid"
                                    id="getSmsCodeBtn" onclick="getPhoneCode()">获取验证码
                            </button>
                        </div>
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layadmin-user-login-icon layui-icon layui-icon-password"></label>
                    <input type="password" name="password" id="loginPassword" lay-verify="password" placeholder="密码"
                           class="layui-input">
                </div>
                <div class="layui-form-item">
                    <label class="layadmin-user-login-icon layui-icon layui-icon-password"></label>
                    <input type="password" maxlength="12" name="repass" id="loginRepassword" lay-verify="password"
                           placeholder="确认密码" class="layui-input">
                </div>
                <div class="layui-form-item">
                    <input type="button" value="注 册" id="regSubmit" onclick="submitForm()"
                           class="layui-btn layui-btn-fluid layui-btn-normal">
                </div>
                <div class="layui-form-item">
                    <a class="layadmin-user-jump-change layui-hide-xs" href="<ht:heropageconfigurationtext
                    code="enterprise_login_url" defaultValue="/public/admin/login.jsp" />" style="color: #666;">已有帐号登入
                    </a>
                </div>
            </form>
        </div>
    </div>
    <div class="bottom_information">
        <ht:heropageconfigurationtext code="enterprise_home_information" defaultValue="请设置信息"/>
    </div>
</div>
</body>