<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/admin/common/common.jsp" %>
<%@ include file="/admin/common/layui_head.html"%>
<%@ include file="/admin/common/dynamic_data.jsp" %>
<body>
<form class="layui-form" action="" id="subForm" onsubmit="return ajaxSubmitForm()"
      enctype="multipart/form-data" method="post" style="padding: 20px 30px 0 0;">
    <%--    <div class="layui-form-item">--%>
    <%--        <label class="layui-form-label">素材名称<font color="red">&nbsp;&nbsp;*</font></label>--%>
    <%--        <div class="layui-input-inline" style="width:300px;">--%>
    <%--            <input type="text" name="material_Name" id="material_Name" lay-verify="required" placeholder="请输入名称" autocomplete="off"--%>
    <%--                   class="layui-input">--%>
    <%--        </div>--%>
    <%--    </div>--%>
    <div class="layui-form-item">
        <label class="layui-form-label">企业<font color="red">&nbsp;&nbsp;*</font></label>
        <div class="layui-input-inline" style="width:300px;">
            <ht:heroenterpriseselect layVerify = "required" selected="${material.enterprise_No}" name="enterprise_No" id="enterprise_No"/>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">企业用户<font color="red">&nbsp;&nbsp;*</font></label>
        <div class="layui-input-inline" style="width:300px;">
            <ht:herocustomdataselect selected="${material.enterprise_User_Id}" dataSourceType="allEnterpriseUsers" name="enterprise_User_Id" />

        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">素材类型<font color="red">&nbsp;&nbsp;*</font></label>
        <div class="layui-input-inline" style="width:300px;">
            <ht:herocodeselect sortCode="material_type_code" selected="${material.material_Type_Code}" id="material_Type_Code" name="material_Type_Code"/>
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label">素材<font color="red">&nbsp;&nbsp;*</font></label>
        <div class="layui-input-inline" id="materialId">

        </div>
    </div>


    <div class="layui-form-item">
        <label class="layui-form-label">审核<font color="red">&nbsp;&nbsp;*</font></label>
        <div class="layui-input-inline" style="width:300px;">
            <ht:herocodeselect sortCode="audit_status" selected="${material.approve_Status}" name="approve_Status"/>
        </div>
    </div>

    <div class="layui-form-item layui-hide">
        <button class="layui-btn" id="layuiadmin-app-form-submit">提交</button>
    </div>

</form>
<%@ include file="/admin/common/layui_bottom.jsp"%>

</body>
<script>

    window.onload = function () {
        var html = ''
        var material_Type_Code = '${material.material_Type_Code}';
        var url = '';
        url = "${material.url}";
        if(material_Type_Code=='picture'){
            html = '<div style="text-align: center"><img style=\"height:100px;width:100px;\" src=\"'+url+'\"></div>';
        }else if(material_Type_Code=='video'){
            if (format != '3GP') {
                html = '<div style="text-align: center"><img style=\"height:100px;width:120px;\" src=\"/public/images/videoPlayButton.png\"></div>';
            }else {
                html = url;
            }
        }else if(d.material_Type_Code=='audio'){
            html ='<div style="text-align: center"><img style=\"height:80px;width:80px;\" src=\"/public/images/audioPlayButton.png\"></div>';
        }
        $("#materialId").append(html);
    }
</script>