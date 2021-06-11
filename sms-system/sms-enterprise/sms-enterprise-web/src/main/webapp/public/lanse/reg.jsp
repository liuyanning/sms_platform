<%@ taglib prefix="ht" uri="/hero-tags" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="/common/layui_head.html" %>
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
    <link href="/public/lanse/css/reg.css" type="text/css" rel="stylesheet">
    <script src='<ht:heropageconfigurationtext code="enterprise_webpage_css" defaultValue="/layuiadmin"/>/lib/admin.js' charset="utf-8"></script>
</head>
<body>

<div class="regs">
    <div class="regMessage">
        <ht:heropageconfigurationtext code="platform_name" defaultValue="信相通"/>
        客户平台
        <ht:heropageconfigurationtext code="platform_version" defaultValue="V2.0.0"/>
        -企业平台
    </div>
    <hr class="hr20">
    <div class="layadmin-user-login-box layadmin-user-login-body layui-form">
                <form id="layuiForm" onsubmit="return submitForm()">
                    <div class="layui-form-item">
                        <label class="layadmin-user-login-icon layui-icon layui-icon-username" for="LAY-user-login-nickname"></label>
                        <input type="text" name="enterprise_Name" id="LAY-user-login-nickname" lay-verify="nickname" placeholder="企业名称" class="layui-input">
                    </div>
                    <hr class="hr15">
                    <div class="layui-form-item">
                        <label class="layadmin-user-login-icon layui-icon layui-icon-cellphone" for="LAY-user-login-cellphone"></label>
                        <input type="text" name="user_Name" id="LAY-user-login-cellphone"
                               lay-verify="phone" onblur="checkPhone()"
                               placeholder="手机" class="layui-input">
                    </div>
                    <hr class="hr15">
                    <div class="layui-form-item">
                        <label class="layadmin-user-login-icon layui-icon layui-icon-password" for="LAY-user-login-password"></label>
                        <input type="password" name="password" id="LAY-user-login-password" lay-verify="pass" placeholder="密码" class="layui-input">
                    </div>
                    <hr class="hr15">
                    <div class="layui-form-item">
                        <label class="layadmin-user-login-icon layui-icon layui-icon-password" for="LAY-user-login-repass"></label>
                        <input type="password" name="repass" id="LAY-user-login-repass" lay-verify="required" placeholder="确认密码" class="layui-input">
                    </div>
                    <hr class="hr15">
                    <div class="layui-form-item " >
                        <div class="layui-row">
                            <div class="layui-col-xs7 code">
                                <label class="layadmin-user-login-icon layui-icon layui-icon-vercode" for="LAY-user-login-vercode"></label>
                                <input type="text" name="graph_ValidateCode" id="graph_ValidateCode"
                                       readonly="readonly"
                                       lay-verify="required" placeholder="图形验证码" class="layui-input"style="width: 65%">
                                    <img class="yzm" src="/Kaptcha.do" id="imgsrc" name="imgsrc" style="width: 125px;height: 35px"
                                         onclick="javaScript:change()"/>
                            </div>
                        </div>
                    </div>
                    <hr class="hr15">
                    <div class="layui-form-item">
                        <div class="layui-row">
                                <label class="layadmin-user-login-icon layui-icon layui-icon-vercode" for="LAY-user-login-vercode"></label>
                                <input type="text" name="sms_ValidateCode" id="LAY-user-login-vercode" lay-verify="required" placeholder="手机验证码" class="layui-input"style="width: 65%;">
                                <button type="button" class="layui-btn layui-btn-primary layui-btn-fluid verify" id="LAY-user-reg-getsmscode" onclick="getPhoneCode()"style="width: 30%">获取验证码</button>
                        </div>
                    </div>
                    <hr class="hr15">
                    <div class="layui-form-item">
                        <button style="background: #99a9bf" class="layui-btn layui-btn-fluid layui-btn-normal layui-disabled" id="LAY-user-reg-submit" disabled="disabled">注 册</button>
                    </div>
                    <div class="layui-form-item">
                        <a href="/public/lanse/login.jsp" class="layadmin-user-jump-change layadmin-link layui-hide-xs">用已有帐号登入</a>
                    </div>
                </form>
            </div>
            <footer style="color:white;width: 500px;">
                <ht:heropageconfigurationtext code="enterprise_home_information" defaultValue="请设置信息"/>
            </footer>
        </div>
    </div>

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

<script>
    var clock = '';
    var nums = 60;
    //获取手机验证码
    function getPhoneCode(){
        var graph_ValidateCode = $("#graph_ValidateCode").val();
        if(!graph_ValidateCode){
            layer.msg("请输入图形验证码！");
            return;
        }
        var tel = $("#LAY-user-login-cellphone").val();
        if(!tel){
            layer.msg("请输入手机号！");
            return;
        }
        if (!/^1\d{10}$/.test(tel)) {
            return layer.msg('请输入正确的手机号')
        };
        $.ajax({
            url:"/admin/enterprise_sendVerificationCode", //发送验证码
            data:{
                user_Name:tel,
                graph_ValidateCode:graph_ValidateCode
            },
            dataType:'json',
            success:function(data){
                if(data.code == "0"){
                    $("#LAY-user-reg-getsmscode").addClass('layui-disabled').html(nums + '秒后重获');
                    clock = setInterval(countDown, 1000); //一秒执行一次
                    layer.msg("验证码发送成功，请在手机上查看");
                    $("#LAY-user-reg-submit").removeClass('layui-disabled');//注册按钮
                    $("#LAY-user-reg-submit").prop('disabled',false);//注册按钮
                }else{
                    layer.msg(data.msg);
                    change();
                }
            },
            error:function(data){
                layer.msg("系统繁忙，请稍后再试");
            }
        });
    }

    //读秒
    function countDown(){
        nums--;
        if(nums > 0){
            $("#LAY-user-reg-getsmscode").addClass('layui-disabled').html(nums + '秒后重获');
            $("#LAY-user-reg-getsmscode").prop('disabled',true);//按钮
        }else{
            $("#LAY-user-reg-getsmscode").removeClass('layui-disabled').html('获取验证码');
            $("#LAY-user-reg-getsmscode").prop('disabled',false);//按钮
            clearInterval(clock); //清除js定时器
            nums = 60; //重置时间
            change(); //图形验证码
        }
    }

    //提交
    function submitForm() {
        $("#LAY-user-reg-submit").addClass('layui-disabled');//注册按钮
        $("#LAY-user-reg-submit").prop('disabled','disabled');//注册按钮
        var vercode = $("#LAY-user-login-vercode").val();
        if(!vercode){
            layer.msg("请输入验证码！");
            return false;
        }
        var tel = $("#LAY-user-login-cellphone").val();
        if(!tel){
            layer.msg("请输入手机号！");
            return false;
        }
        var userName = $("#LAY-user-login-nickname").val();
        if(!userName){
            layer.msg("请输入企业名称！");
            return false;
        }
        var password = $("#LAY-user-login-password").val();
        if(!password){
            layer.msg("请输入密码！");
            return false;
        }
        var repass = $("#LAY-user-login-repass").val();
        if(!repass){
            layer.msg("请输入密码！");
            return false;
        }
        if(repass != password){
            layer.msg("两次密码输入不一致！");
            return false;
        }
        $.ajax({
            url: "/admin/enterprise_registerEnterpriseUser?"+ $("#layuiForm").serialize(),
            dataType:'json',
            success: function (res) {
                if (res.code == '0') {
                    layer.msg('注册成功', {icon: 1, time: 2000}, function () {
                        window.parent.location.href="../admin/login.jsp";
                        return false;
                    });
                    return false;
                }else {
                    layer.msg(res.msg, {icon: 2, time: 2000}, function () {
                        $("#LAY-user-reg-submit").removeClass('layui-disabled');//注册按钮
                        $("#LAY-user-reg-submit").prop('disabled',false);//注册按钮
                        return false;
                    });
                    return false;
                }
            }
        });
        return false;
    }

    //图形验证码
    function change() {
        document.getElementById("imgsrc").setAttribute("src", "/Kaptcha.do?" + Math.floor(Math.random() * 100));
    }

    //校验手机号
    function checkPhone() {
        var tel = $("#LAY-user-login-cellphone").val();
        if(!tel){
            layer.msg("请输入手机号！");
            return;
        }
        if (!/^1\d{10}$/.test(tel)) {
            return layer.msg('请输入正确的手机号')
        };
        //验证手机号是否已注册
        $.ajax({
            url:"/admin/enterprise_checkPhoneRepeat",
            data:{
                user_Name:tel
            },
            dataType:'json',
            success:function(data){
                if(data.code == "-1"){
                    layer.msg("该手机号已注册！",{offset:['180px']});
                    return false;
                }else{
                    $("#graph_ValidateCode").prop('readonly',false);
                }
            },
            error:function(data){
                layer.msg("系统繁忙，请稍后再试");
            }
        });
    }
</script>