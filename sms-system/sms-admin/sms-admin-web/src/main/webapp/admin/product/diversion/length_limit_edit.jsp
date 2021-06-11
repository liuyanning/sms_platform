<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/admin/common/common.jsp" %>
<%@ include file="/admin/common/layui_head.html" %>
<script src="/js/jquery-3.4.1.min.js"></script>
<body>
<form class="layui-form" action="/admin/product/editProductChannelsDiversionLengthLimit" onsubmit="return false;"
      style="padding: 20px 30px 0 0;">
    <input type="text" name="id"  Id="id" value="<c:out value="${diversion.id}"/>" hidden/>
    <input type="text" name="product_Channels_Id"  Id="product_Channels_Id" value="<c:out value="${productChannelsId}"/>" hidden/>
    <div class="layui-form-item">
        <label class="layui-form-label" style="width: 20%">限制字符数<font color="red">&nbsp;&nbsp;*</font></label>
        <div class="layui-input-inline" style="width: 60%">
            <input maxlength="128" name="strategy_Rule" id="strategy_Rule" lay-verify="required" value="<c:out value="${diversion.strategy_Rule}"/>" class="layui-input"/>
            <font color="red">说明：短信长度不能超过此字符数</font>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label" style="width: 20%">状态<font color="red">&nbsp;&nbsp;*</font></label>
        <div class="layui-input-inline" style="width: 60%">
            <ht:herocodeselect sortCode="state" name="status_Code" selected="${diversion.status_Code}" layVerify="required"/>
        </div>
    </div>
    <div class="layui-form-item" hidden>
        <input type="submit" lay-submit lay-filter="submit" id="layuiadmin-app-form-submit" value="提交">
    </div>
</form>
<%@ include file="/admin/common/layui_bottom.jsp" %>
</body>
