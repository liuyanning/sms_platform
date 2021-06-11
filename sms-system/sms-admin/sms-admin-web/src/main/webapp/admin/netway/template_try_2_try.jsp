<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/admin/common/common.jsp" %>
<%@ include file="/admin/common/layui_head.html" %>
<body>
<form class="layui-form" action="/admin/template_Try2Try" lay-filter="form" onsubmit="return false;"
      style="padding: 20px 30px 0 0;">
    <div class="layui-form-item">
        <label class="layui-form-label">模板内容：</label>
        <div class="layui-input-block" style="width: 80%;height: 30%">
            <textarea type="text" maxlength="1024" name="templateContent" placeholder="请输入模板内容" autocomplete="off" class="layui-textarea"><c:out value="${smsTemplate.template_Content}"/></textarea>
        </div>
    </div>
      <div class="layui-form-item">
        <label class="layui-form-label">测试短信内容：</label>
        <div class="layui-input-block" style="width: 80%;height: 30%">
            <textarea type="text" maxlength="1024" name="smsContent" placeholder="请输入测试短信内容" autocomplete="off" class="layui-textarea"></textarea>
        </div>
    </div>
    <div class="layui-form-item layui-hide">
        <input type="button" lay-submit lay-filter="submit" id="layuiadmin-app-form-submit" value="确认">
    </div>
</form>
<%@ include file="/admin/common/layui_bottom.jsp" %>
</body>