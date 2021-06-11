<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/admin/common/common.jsp" %>
<%@ include file="/admin/common/layui_head.html" %>
<body>
<form class="layui-form" action="/admin/enterprise_editRole" lay-filter="form" onsubmit="return false;"
      style="padding: 20px 30px 0 0;">
    <input name="id" hidden="true" value="<c:out value="${enterpriseRole.id}"/>">
    <div class="layui-form-item">
        <label class="layui-form-label">角色名称<font color="red">&nbsp;&nbsp;*</font></label>
        <div class="layui-input-inline">
            <input type="text" maxlength="128" name="name" value="<c:out value="${enterpriseRole.name}"/>" lay-verify="required"
                   placeholder="请输入角色名称" autocomplete="off"
                   class="layui-input">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">角色代码<font color="red">&nbsp;&nbsp;*</font></label>
        <div class="layui-input-inline">
            <input type="text" maxlength="128" name="code" value="<c:out value="${enterpriseRole.code}"/>" lay-verify="required"
                   placeholder="请输入角色代码" autocomplete="off"
                   class="layui-input" readonly>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">上级代码</label>
        <div class="layui-input-inline">
            <input type="text" maxlength="128" name="up_Code" value="<c:out value="${enterpriseRole.up_Code}"/>" placeholder="请输入上级代码"
                   autocomplete="off"
                   class="layui-input">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">备注</label>
        <div class="layui-input-block">
            <textarea type="text" maxlength="2048" name="remark" autocomplete="off" class="layui-textarea">${enterpriseRole.remark}</textarea>
        </div>
    </div>
    <div class="layui-form-item layui-hide">
        <input type="submit" lay-submit lay-filter="submit" id="layuiadmin-app-form-submit" value="确认">
    </div>
</form>
<%@ include file="/admin/common/layui_bottom.jsp" %>
</body>