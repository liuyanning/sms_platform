<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/admin/common/common.jsp" %>
<%@ include file="/admin/common/layui_head.html" %>
<body>
<form class="layui-form" action="/admin/product/editProduct" lay-filter="form" onsubmit="return false;"
      style="padding: 20px 30px 0 0;">
    <input type="hidden" name="id" value="<c:out value="${product.id}"/>"/>
    <div class="layui-form-item">
        <label class="layui-form-label">名称<font color="red">&nbsp;&nbsp;*</font></label>
        <div class="layui-input-inline">
            <input type="text" maxlength="128" name="name" lay-verify="required" placeholder="请输入名称" autocomplete="off"
                   value="<c:out value="${product.name}"/>" class="layui-input">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">产品编码</label>
        <div class="layui-input-inline">
            <input type="text" name="code"  placeholder="请输入代码" autocomplete="off"
                   value="<c:out value="${product.no}"/>" class="layui-input" readonly>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">行业类型</label>
        <div class="layui-input-inline">
            <ht:herocodeselect sortCode="trade" selected="${product.trade_Type_Code}" name="trade_Type_Code"/>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">拦截策略</label>
        <div class="layui-input-inline">
            <ht:herocustomdataselect dataSourceType="interceptStrategy" name="intercept_Strategy_Id" headerValue="-1" selected="${product.intercept_Strategy_Id}"/>
        </div>
    </div>
    <div class="layui-form-item layui-hide">
        <input type="submit" lay-submit lay-filter="submit" id="layuiadmin-app-form-submit" value="确认">
    </div>
</form>
<%@ include file="/admin/common/layui_bottom.jsp" %>
</body>