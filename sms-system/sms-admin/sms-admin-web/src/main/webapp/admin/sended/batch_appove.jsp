<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/admin/common/common.jsp" %>
<%@ include file="/admin/common/layui_head.html"%>
<%@ include file="/admin/common/dynamic_data.jsp" %>

<body>
<form class="layui-form" action="" id="subForm" onsubmit="return ajaxSubmitForm()"
      enctype="multipart/form-data" method="post" style="padding: 20px 30px 0 0;">
    <div class="layui-form-item">
        <label class="layui-form-label">企业<font color="red">&nbsp;&nbsp;*</font></label>
        <div class="layui-input-inline" style="width: 300px">
            <ht:heroenterpriseselect  layVerify = "required" name="enterprise_No" id="enterprise_No"/>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">企业用户<font color="red">&nbsp;&nbsp;*</font></label>
        <div class="layui-input-inline" style="width: 300px">
            <select id="enterprise_User_Id"  name="enterprise_User_Id">

            </select>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">审核<font color="red">&nbsp;&nbsp;*</font></label>
        <div class="layui-input-inline" style="width: 300px">
            <ht:herocodeselect sortCode="035" name="audit_Status_Code"></ht:herocodeselect>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">消息内容   </label>
        <div class="layui-input-inline" style="width: 300px">
            <textarea type="text" name="content" autocomplete="off" class="layui-textarea"></textarea>
        </div>
    </div>
    <div class="layui-form-item layui-hide">
        <button class="layui-btn" id="layuiadmin-app-form-submit">审核</button>
    </div>

</form>
<%@ include file="/admin/common/layui_bottom.jsp"%>

<script >
    layui.use(['form'], function () {
        var form = layui.form
        form.render();
    });

    function ajaxSubmitForm() {
        var enterprise_No = $("#enterprise_No").val();
        if (!enterprise_No){
            layer.msg('请选择企业！')
            return false;
        }
        var enterprise_User_Id = $("#enterprise_User_Id").val();
        if (!enterprise_User_Id){
            layer.msg('请选择提交账户！')
            return false;
        }
        var audit_Status_Code = $("select[name='audit_Status_Code']").find("option:selected").text();
        if (audit_Status_Code=='请选择'){
            layer.msg('请选择审核结果！')
            return false;
        }
        var data = $("#subForm").val();
        var loading= parent.layer.load('', {time: 10*1000});
        $("#subForm").ajaxSubmit({
            type: 'post', // 提交方式 get/post
            url: '/admin/sended_auditingInputCount', // 需要提交的 url
            dataType: 'json',
            data: data,
            success: function (d) {
                parent.layer.close(loading);
                if(d < 1){
                    layer.alert("根据您输入的条件，未查询到待审核的短信");
                }else {
                    var enterprise_No = $("#enterprise_No").find("option:selected").text();
                    var enterprise_User_Id = $("#enterprise_User_Id").find("option:selected").text();
                    var audit_Status_Code = $("select[name='audit_Status_Code']").find("option:selected").text();
                    var htmlStr = '您选择了企业【'+enterprise_No+'】，用户【'+enterprise_User_Id+'】提交的 '+d+' 条数据，确定【'+audit_Status_Code+'】吗？';
                    layer.confirm(htmlStr,{title:" "}, function(index){
                        var loading1= parent.layer.load('');
                        $("#subForm").ajaxSubmit({
                            type: 'post', // 提交方式 get/post
                            url: '/admin/sended_batchApproveInput', // 需要提交的 url
                            dataType: 'json',
                            data: data,
                            success: function (d) {
                                layer.msg('提交成功', {icon: 1, time: 2000}, function () {
                                    parent.layer.close(loading1);
                                    window.parent.location.reload();
                                });
                            },
                            error: function (d) {
                                parent.layer.close(loading1);
                                layer.msg('提交失败', {icon: 2, time: 2000}, function () {
                                    $('#layuiadmin-app-form-submit').attr('disabled',false);
                                });
                            }
                        })
                        return false;
                    });
                }
            },
            error: function (d) {
                parent.layer.close(loading);
                layer.msg('提交失败', {icon: 2, time: 2000}, function () {
                    $('#layuiadmin-app-form-submit').attr('disabled',false);
                });
            }
        })
        return false; // 必须返回false，否则表单会自己再做一次提交操作，并且页面跳转
    }

</script>
</body>