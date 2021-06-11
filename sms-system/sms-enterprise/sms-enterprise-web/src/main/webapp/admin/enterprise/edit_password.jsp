<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/common/common.jsp" %>
<%@ include file="/common/layui_head.html" %>
<script src="/js/jsencrypt/jsencrypt.min.js"></script>
<script src="/js/jsencrypt/rsakey.js"></script>
<body>
<div class="layui-fluid">
    <div class="layui-row layui-col-space15">
        <div class="layui-col-md10">
            <div class="layui-card">
                <div class="layui-card-header">修改密码</div>
                <div class="layui-card-body" pad15>

                    <form class="layui-form" action="/admin/enterprise_editPassword" lay-filter="form"
                          onsubmit="return false;"
                          style="padding: 20px 30px 0 0;">
                        <div class="layui-form-item">
                            <label class="layui-form-label">原始密码<font color="red">&nbsp;&nbsp;*</font></label>
                            <div class="layui-input-inline">
                                <input type="password" maxlength="12" name="oldPassword" lay-verify="required" placeholder="请输入原始密码"
                                       autocomplete="off" lay-verType="tips" class="layui-input">
                            </div>
                        </div>
                        <div class="layui-form-item">
                            <label class="layui-form-label">新密码<font color="red">&nbsp;&nbsp;*</font></label>
                            <div class="layui-input-inline">
                                <input type="password" maxlength="12" id="password" name="cp_newPassword" lay-verify="required|password"
                                       lay-verType="tips"
                                       placeholder="请输入新密码" autocomplete="off" class="layui-input">
                            </div>
                        </div>

                        <div class="layui-form-item">
                            <label class="layui-form-label">确认密码<font color="red">&nbsp;&nbsp;*</font></label>
                            <div class="layui-input-inline">
                                <input type="password" maxlength="12" name="confirmPassword" lay-verify="required|confirmPassword"
                                       placeholder="请输入确认密码" lay-verType="tips" autocomplete="off" class="layui-input">
                            </div>
                        </div>

                        <div class="layui-form-item">
                            <div class="layui-input-block">
                                <button class="layui-btn layui-btn-normal" type="submit" lay-submit lay-filter="submit">
                                    提交
                                </button>
                                <button type="reset" class="layui-btn layui-btn-primary" style="background-color: white">取消</button>
                            </div>
                        </div>
                    </form>

                </div>
            </div>
        </div>
    </div>
</div>
<%--<%@ include file="/common/layui_bottom.jsp" %>--%>
<script type="text/javascript">
    function encryptPassword() {
        var encrypt = new JSEncrypt();
        encrypt.setPublicKey(rsaPublicKey);
        var plainPassword = $("#password").val();
        var encryptedPassword = encrypt.encrypt(plainPassword);
        $("#password").val(encryptedPassword);

        var plainOldPassword = $("input[name='oldPassword']").val();
        var encryptedOldPassword = encrypt.encrypt(plainOldPassword);
        $("input[name='oldPassword']").val(encryptedOldPassword);
    }

    layui.extend({formExt: '/js/layui-ext/formExt'}).use('formExt', function () {
        var formExt = layui.formExt;
        var param = {success: function () {
                $('.layui-form')[0].reset();
            }};
        // 初始化
        formExt.init(param);
    });
</script>
</body>