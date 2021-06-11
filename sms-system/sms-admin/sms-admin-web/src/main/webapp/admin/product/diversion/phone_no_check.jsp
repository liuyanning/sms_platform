<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/admin/common/common.jsp" %>
<%@ include file="/admin/common/layui_head.html" %>
<body>
<form class="layui-form" action="/admin/product/editDiversionPhoneNoCheck" onsubmit="return false;"
      style="padding: 20px 30px 0 0;">
    <input type="text" name="id"  Id="id" value="<c:out value="${diversion.id}"/>" hidden/>
    <input type="text" name="product_Channels_Id"  Id="product_Channels_Id" value="<c:out value="${productChannelsId}"/>" hidden/>
    <div class="layui-form-item">
        <label class="layui-form-label" width="30">空号检测<font color="red">&nbsp;&nbsp;*</font></label>
        <div class="layui-input-inline" width="60">
            <ht:herocodeselect sortCode="phone_no_check" name="strategy_Rule" selected="${diversion.strategy_Rule}" layVerify="required"/>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label" width="30">状态<font color="red">&nbsp;&nbsp;*</font></label>
        <div class="layui-input-inline" width="60">
            <ht:herocodeselect sortCode="state" name="status_Code" selected="${diversion.status_Code}" layVerify="required"/>
        </div>
    </div>
    <div class="layui-form-item" hidden>
        <input type="submit" lay-submit lay-filter="submit" id="layuiadmin-app-form-submit" value="提交">
    </div>
</form>
<%@ include file="/admin/common/layui_bottom.jsp" %>
</body>
