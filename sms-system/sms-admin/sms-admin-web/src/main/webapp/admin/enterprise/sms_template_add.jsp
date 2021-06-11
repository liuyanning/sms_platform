<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/admin/common/common.jsp" %>
<%@ include file="/admin/common/layui_head.html" %>
<%@ include file="/admin/common/dynamic_data.jsp" %>
<form class="layui-form" action="/admin/enterprise_addSmsTemplate" lay-filter="form" id="dialogFormId"  onsubmit="return false;"
      style="padding: 20px 30px 0 0;">
    <div class="layui-form-item">
        <label class="layui-form-label">模板名称<font color="red">&nbsp;&nbsp;*</font></label>
        <div class="layui-input-block" >
            <input type="text" maxlength="256" name="template_Name" placeholder="请输入" autocomplete="off" class="layui-input"  lay-verify="required">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">企业<font color="red">&nbsp;&nbsp;*</font></label>
        <div class="layui-input-inline" style="width:300px;">
            <ht:heroenterpriseselect  layVerify = "required" name="enterprise_No" id="enterprise_No"/>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">企业用户</label>
        <div class="layui-input-inline" style="width:300px;">
            <select id="enterprise_User_Id"  name="enterprise_User_Id" >

            </select>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">模板内容<font color="red">&nbsp;&nbsp;*</font></label>
        <div class="layui-input-block">
            <textarea type="text" maxlength="1024" name="template_Content" autocomplete="off" class="layui-textarea" lay-verify="required"></textarea>
            <font color="red">格式说明：您的验证码:{0},请勿告知他人!失效时间{1}分钟</font>
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label">行业类型</label>
        <div class="layui-input-inline">
            <ht:herocodeselect sortCode="trade" name="template_Type"/>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">描述</label>
        <div class="layui-input-block">
            <textarea type="text" maxlength="128" name="description" autocomplete="off" class="layui-textarea"></textarea>
        </div>
    </div>

    <div class="layui-form-item layui-hide">
        <input type="button" lay-submit lay-filter="submit" id="layuiadmin-app-form-submit" value="确认">
    </div>
</form>
<%@ include file="/admin/common/layui_bottom.jsp" %>
</body>



