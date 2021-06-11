<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/common/common.jsp" %>
<%@ include file="/common/layui_head.html" %>
<head>
    <meta http-equiv="Content-Type" content="multipart/form-data; charset=utf-8"/>
    <script src="/js/jquery-3.4.1.min.js"></script>
    <script src="/js/jquery-form.js"></script>
    <script src='<ht:heropageconfigurationtext code="enterprise_webpage_css" defaultValue="/layuiadmin"/>/layui/layui.all.js'></script>
</head>
<body>
<div class="layui-fluid">
    <div class="layui-row layui-col-space15">
        <div class="layui-col-md12">
            <div class="layui-card">
                <div class="layui-card-header"></div>
                <div class="layui-card-body" pad15>
                    <form class="layui-form" id="layui-form" action="/admin/enterprise_authEnterpriseInfo"
                          enctype="multipart/form-data" method="post" onsubmit="return ajaxSubmitForm()">
                        <input hidden name="id" value="<c:out value="${eBean.id}"/>"/>
                        <div class="layui-form-item">
                            <label class="layui-form-label"  style="width: 20%">企业名称<font color="red">&nbsp;&nbsp;*</font></label>
                            <div class="layui-input-inline"  style="width: 60%">
                                <input type="text" maxlength="128" name="name" readonly value="<c:out value="${eBean.name}"/>"  placeholder="请输入" autocomplete="off"  class="layui-input" >
                            </div>
                        </div>
                        <div class="layui-form-item">
                            <label class="layui-form-label"  style="width: 20%">企业地址<font color="red">&nbsp;&nbsp;*</font></label>
                            <div class="layui-input-inline" style="width: 60%">
                                <input type="text" maxlength="256" name="address" value="<c:out value="${eBean.address}"/>" placeholder="请输入省市县街道等信息··"
                                       lay-verify="required" autocomplete="off" class="layui-input">
                            </div>
                        </div>
                        <div class="layui-form-item">
                            <label class="layui-form-label"  style="width: 20%">身份证正面照<font color="red">&nbsp;&nbsp;*</font></label>
                            <div class="layui-input-block">
                                <input type="file" name="idCardBefore" id="idCardBefore" class="required"/>
                            </div>
                        </div>
                        <div class="layui-form-item">
                            <label class="layui-form-label"  style="width: 20%">身份证背面照<font color="red">&nbsp;&nbsp;*</font></label>
                            <div class="layui-input-block">
                                <input type="file" name="idCardBack" id="idCardBack" class="required"/>
                            </div>
                        </div>
                        <div class="layui-form-item">
                            <label class="layui-form-label"  style="width: 20%">营业执照<font color="red">&nbsp;&nbsp;*</font></label>
                            <div class="layui-input-block">
                                <input type="file" name="uploadImg" id="uploadImg" class="required"/>
                            </div>
                        </div>
                        <div class="layui-form-item" style="border-bottom:none;width: 500px;">
                            <div class="text-normal" style="line-height:24px;text-align: center">
                                <p style="margin-bottom: 5px;color:red">* 营业执照需要加盖公章，否则无效</p>
                                <p style="margin-bottom: 5px;">* 认证图片格式需为<font color="blue">jpg,jpeg,png</font>格式</p>
                            </div>
                        </div>
                        <div class="layui-form-item">
                            <label class="layui-form-label"></label>
                            <div class="layui-input-block">
                                <button type="button"  class="layui-btn" onclick="return ajaxSubmitForm()">提交</button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
<%@ include file="/common/layui_bottom.jsp" %>
<script>
    function ajaxSubmitForm() {
        var idCardBefore = $("#idCardBefore").val();
        var idCardBack = $("#idCardBack").val();
        var uploadImg = $("#uploadImg").val();
        if((!idCardBefore || !idCardBack) && !uploadImg){
            layer.msg('请选择上传文件！', {icon: 2, time: 2000}, function () {
            });
            return false;
        }
        var data = $("#layui-form").val();
        $("#layui-form").ajaxSubmit({
            type: 'post', // 提交方式 get/post
            url: '/admin/enterprise_authEnterpriseInfo', // 需要提交的 url
            dataType: 'json',
            data: data,
            success: function (d) {
                if(d.code!=0){
                    layer.alert(d.msg, {icon: 2});
                    return;
                }
                // 此处可对 data 作相关处理
                layer.alert("提交成功，请等待客服审核！", {icon:1}, function(index){
                    $('#layui-form')[0].reset();
                    parent.layer.closeAll();
                });
            },
            error: function (d) {
                layer.msg('提交失败,请确认是否已选择文件', {icon: 2, time: 2000}, function () {
                });
            }
        })
        return false; // 必须返回false，否则表单会自己再做一次提交操作，并且页面跳转
    }

    function setMobileFileName(obj) {
        $("#mobileFileNameId").attr("value", obj.val());
    }
</script>
</body>
