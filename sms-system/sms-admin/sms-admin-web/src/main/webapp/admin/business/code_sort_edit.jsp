<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/admin/common/common.jsp" %>
<%@ include file="/admin/common/layui_head.html" %>
<body>
<form class="layui-form" action="/admin/business_editCodeSort" lay-filter="form" onsubmit="return false;"
      style="padding: 20px 30px 0 0;">
    <input type="hidden" name="id" value="<c:out value="${codeSort.id}"/>"/>
    <div class="layui-form-item">
        <label class="layui-form-label">分类名称<font color="red">&nbsp;&nbsp;*</font></label>
        <div class="layui-input-inline">
            <input type="text" maxlength="56" name="name" lay-verify="required" placeholder="请输入分类名称" autocomplete="off"
                   value="<c:out value="${codeSort.name}"/>" class="layui-input">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">分类代码<font color="red">&nbsp;&nbsp;*</font></label>
        <div class="layui-input-inline">
            <input type="text"  maxlength="56" name="code" lay-verify="required" placeholder="请输入分类代码" autocomplete="off"
                   value="<c:out value="${codeSort.code}"/>" class="layui-input">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">备注</label>
        <div class="layui-input-block">
            <textarea type="text" name="remark" autocomplete="off" class="layui-textarea"><c:out
                    value="${codeSort.remark}"/></textarea>
        </div>
    </div>
    <div class="layui-form-item layui-hide">
        <input type="submit" lay-submit lay-filter="submit" id="layuiadmin-app-form-submit" value="确认">
    </div>
</form>
<%@ include file="/admin/common/layui_bottom.jsp" %>
</body>