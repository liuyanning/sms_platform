<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/admin/common/common.jsp" %>
<%@ include file="/admin/common/layui_head.html" %>
<body>
<form class="layui-form" action="/admin/enterprise_editLimit" lay-filter="form" onsubmit="return false;"
      style="padding: 20px 30px 0 0;">
    <input type="hidden" name="id" value="<c:out value="${enterpriseLimit.id}"/>">
    <div class="layui-form-item">
        <label class="layui-form-label">权限名称<font color="red">&nbsp;&nbsp;*</font></label>
        <div class="layui-input-inline">
            <input type="text" maxlength="128" name="name" value="<c:out value="${enterpriseLimit.name}"/>" lay-verify="required"
                   placeholder="请输入权限名称" autocomplete="off"
                   class="layui-input">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">权限URL </label>
        <div class="layui-input-block">
            <input type="text" maxlength="512" name="url" value="<c:out value="${enterpriseLimit.url}"/>"
                   placeholder="请输入url" autocomplete="off"
                   class="layui-input">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">代码<font color="red">&nbsp;&nbsp;*</font></label>
        <div class="layui-input-inline">
            <input type="text" maxlength="128" name="code" value="<c:out value="${enterpriseLimit.code}"/>" lay-verify="required"
                   placeholder="请输入代码" autocomplete="off"
                   class="layui-input">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">上级代码<font color="red">&nbsp;&nbsp;*</font></label>
        <div class="layui-input-inline">
            <input type="text" maxlength="128" name="up_Code" value="<c:out value="${enterpriseLimit.up_Code}"/>" lay-verify="required"
                   placeholder="请输入上级代码" autocomplete="off"
                   class="layui-input">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">显示顺序<font color="red">&nbsp;&nbsp;*</font></label>
        <div class="layui-input-inline">
            <input type="number" min="0" max="2147483647" name="order_Id" value="<c:out value="${enterpriseLimit.order_Id}"/>" lay-verify="required"
                   placeholder="请输入显示顺序" autocomplete="off"
                   class="layui-input">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">菜单图标</label>
        <div class="layui-input-inline">
            <input type="text" name="icon" placeholder="显示图标" autocomplete="off" class="layui-input"
                   value="<c:out value="${enterpriseLimit.icon}"/>"/>
        </div>
    </div>
    <div class=" layui-form-item">
        <label class="layui-form-label">权限类型</label>
        <div class="layui-input-inline">
            <ht:herocodeselect sortCode="007" name="type_Code" selected="${enterpriseLimit.type_Code}"/>
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label">权限按钮</label>
        <div class="layui-input-block">
            <textarea type="text" maxlength="512" name="button_Action"
                      autocomplete="off" class="layui-textarea">${enterpriseLimit.button_Action}</textarea>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">备注</label>
        <div class="layui-input-block">
            <textarea type="text"maxlength="2048" name="remark" autocomplete="off" class="layui-textarea">${enterpriseLimit.remark}</textarea>
        </div>
    </div>
    <div class="layui-form-item layui-hide">
        <input type="submit" lay-submit lay-filter="submit" id="layuiadmin-app-form-submit" value="确认">
    </div>
</form>
<%@ include file="/admin/common/layui_bottom.jsp" %>
</body>