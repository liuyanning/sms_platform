<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/admin/common/common.jsp" %>
<%@ include file="/admin/common/layui_head.html" %>
<body>
<form class="layui-form" action="/admin/enterprise_checkTemplate" lay-filter="form" onsubmit="return false;"
      style="padding: 20px 30px 0 0;">
    <input name="id" hidden="true" value="<c:out value="${smsTemplate.id}"/>">
    <div class="layui-form-item">
        <label class="layui-form-label">模板名称<font color="red">&nbsp;&nbsp;*</font></label>
        <div class="layui-input-block" >
            <input type="text" name="" readonly value = "<c:out value="${smsTemplate.template_Name}"/>" placeholder="请输入" autocomplete="off" class="layui-input"  lay-verify="required">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">模板内容<font color="red">&nbsp;&nbsp;*</font></label>
        <div class="layui-input-block">
            <textarea type="text" name="" readonly autocomplete="off" class="layui-textarea" lay-verify="required">${smsTemplate.template_Content}</textarea>
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label">行业类型</label>
        <div class="layui-input-inline">
            <ht:herocodeselect sortCode="trade" disabled="disabled" selected="${smsTemplate.template_Type}" name=""/>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">描述</label>
        <div class="layui-input-block">
            <textarea type="text" name="" readonly  autocomplete="off" class="layui-textarea">${smsTemplate.description}</textarea>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">审核<font color="red">&nbsp;&nbsp;*</font></label>
        <div class="layui-input-inline">
            <ht:herocodeselect sortCode="templateCheckStatus" selected="${smsTemplate.approve_Status}" name="approve_Status"/>
        </div>
    </div>

    <div class="layui-form-item layui-hide">
        <input type="button" lay-submit lay-filter="submit" id="layuiadmin-app-form-submit" value="确认">
    </div>
</form>
<%@ include file="/admin/common/layui_bottom.jsp" %>
</body>