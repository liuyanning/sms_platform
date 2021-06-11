<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/admin/common/common.jsp" %>
<%@ include file="/admin/common/layui_head.html" %>
<script src="/js/jsencrypt/jsencrypt.min.js"></script>
<script src="/js/jsencrypt/rsakey.js"></script>
<body>
<form class="layui-form" action="/admin/account_resetPassword" lay-filter="form" onsubmit="return false;"
      style="padding: 50px 30px 0 0;">
    <input type="hidden" name="id" value="<c:out value="${userId}"/>">
    <div class="layui-form-item">
                            <label class="layui-form-label">登录密码</label>
                            <div class="layui-input-inline">
                                <input type="password" id="password" name="password" lay-verify="password"
                                       lay-verType="tips"
                                       placeholder="请输入登录密码" autocomplete="off" class="layui-input">
                            </div>
                        </div>

                        <div class="layui-form-item">
                            <label class="layui-form-label">确认密码</label>
                            <div class="layui-input-inline">
                                <input type="password" name="loginPassword" lay-verify="confirmPassword"
                                       placeholder="请输入确认密码" lay-verType="tips" autocomplete="off" class="layui-input">
                            </div>
                        </div>

    <div class="layui-form-item layui-hide">
        <input type="submit" lay-submit lay-filter="submit" id="layuiadmin-app-form-submit" value="确认">
    </div>
</form>
<%@ include file="/admin/common/layui_bottom.jsp" %>
    <script type="text/javascript">
        function encryptPassword() {
            var encrypt = new JSEncrypt();
            encrypt.setPublicKey(rsaPublicKey);
            var plainPassword = $("#password").val();
            var encryptedPassword = encrypt.encrypt(plainPassword);
            $("#password").val(encryptedPassword);
        }
    </script>
</body>
