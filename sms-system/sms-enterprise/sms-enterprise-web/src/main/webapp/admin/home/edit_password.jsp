<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/common/common.jsp" %>
<%@ include file="/common/layui_head.html" %>
<script src="/js/jsencrypt/jsencrypt.min.js"></script>
<script src="/js/jsencrypt/rsakey.js"></script>
<body>
<div class="layui-fluid">
    <div class="layui-row layui-col-space15 layui-fluid">
        <div class="layui-col-md10">
            <div class="layui-card">
                <div class="layui-card-body" pad15>
                    <form id="layuiForm" class="layui-form" action="/admin/enterprise_editPassword"
                          onsubmit="return false;"
                          style="padding: 5% 0 1% 0;">
                        <h3 style="text-align:center"><font color="red" >系统检测到您当前密码为初始密码，安全等级较低！<br>为确保账户安全，建议修改新密码后继续使用！</font></h3>
                        <br>
                        <hr>
                        <br>
                        <div class="layui-form-item">
                            <label class="layui-form-label">原始密码<font color="red">&nbsp;&nbsp;*</font></label>
                            <div class="layui-input-inline">
                                <input type="password" id="oldPassword" name="oldPassword" lay-verify="required" placeholder="请输入原始密码"
                                       autocomplete="off" lay-verType="tips" class="layui-input">
                            </div>
                        </div>
                        <div class="layui-form-item">
                            <label class="layui-form-label">新密码<font color="red">&nbsp;&nbsp;*</font></label>
                            <div class="layui-input-inline">
                                <input type="password" maxlength="12" id="cp_newPassword" name="cp_newPassword" lay-verify="required|password"
                                       lay-verType="tips"
                                       placeholder="请输入新密码" autocomplete="off" class="layui-input">
                            </div>
                        </div>

                        <div class="layui-form-item">
                            <label class="layui-form-label">确认密码<font color="red">&nbsp;&nbsp;*</font></label>
                            <div class="layui-input-inline">
                                <input type="password" maxlength="12" name="confirmPassword" id="confirmPassword" lay-verify="required|confirmPassword"
                                       placeholder="请输入确认密码" lay-verType="tips" autocomplete="off" class="layui-input">
                            </div>
                        </div>

                        <div class="layui-form-item">
                            <div class="layui-input-block">
                                <a class="layui-btn layui-btn-normal" onclick="submitForm()">
                                    提交
                                </a>
                                <button type="reset" class="layui-btn layui-btn-primary">重置</button>
                            </div>
                        </div>
                    </form>

                </div>
            </div>
        </div>
    </div>
</div>
<%@ include file="/common/layui_bottom.jsp" %>
</body>
<script>
    //提交
    function submitForm() {
        var oldPassword = $("#oldPassword").val();
        var cp_newPassword = $("#cp_newPassword").val();
        var confirmPassword = $("#confirmPassword").val();
        if(!oldPassword){
            return layer.msg("请输入原始密码！");
        }
        if(!cp_newPassword){
            return layer.msg("请输入新密码！");
        }
        if(!confirmPassword){
            return layer.msg("请输入确认密码！");
        }
        if(cp_newPassword != confirmPassword){
            return layer.msg("两次密码输入不一致！");
        }
        if(!/^[\S]{6,12}$/.test(cp_newPassword)){
            return layer.msg("密码必须6到12位，且不能出现空格");
        }
        if(oldPassword == cp_newPassword) {
            return layer.msg("新密码和原始密码一致，建议重设新密码！");
        }
        var encrypt = new JSEncrypt();
        encrypt.setPublicKey(rsaPublicKey);
        var encryptedOld = encrypt.encrypt(oldPassword);
        $("#oldPassword").val(encryptedOld);
        var encryptedNew = encrypt.encrypt(cp_newPassword);
        $("#cp_newPassword").val(encryptedNew);
        layer.confirm('确定要提交修改吗？', function () {
            $.ajax({
                url: "/admin/enterprise_editPassword?"+ $("#layuiForm").serialize(),
                dataType:'json',
                success: function (res) {
                    if (res.code == '0') {
                        layer.alert('修改成功',{ icon: 1 , closeBtn: 0 },function () {
                            window.parent.location.href="/";
                            return false;
                        });
                        return false;
                    }else {
                        layer.alert(res.msg,{ icon: 0 , closeBtn: 0 }, function () {
                            layer.closeAll();
                        });
                    }
                }
            });
        });
        return false;
    }
</script>