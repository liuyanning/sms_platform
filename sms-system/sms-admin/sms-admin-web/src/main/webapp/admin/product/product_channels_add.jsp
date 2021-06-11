<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/admin/common/common.jsp" %>
<%@ include file="/admin/common/layui_head.html" %>
<%@ include file="/admin/common/country_operator.jsp" %>

<body>
<form class="layui-form" action="/admin/product/addProductChannels" lay-filter="form" onsubmit="return false;"
      style="padding: 20px 30px 0 0;">
    <input name="product_No" id="subProductNo" lay-verify="required" hidden/>
    <input name="channel_Type_Code" value="<c:out value="${channelTypeCode}"/>" lay-verify="required" hidden/>
    <div class="layui-form-item">
        <label class="layui-form-label" style="width: 20%">国家<font color="red">&nbsp;&nbsp;*</font></label>
        <div class="layui-input-inline" style="width: 60%">
            <ht:herocodeselect sortCode="country" name="country_Number" id="country_Number" selected="cn" valueField="Value" layVerify="required"/>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label" style="width: 20%">运营商<font color="red">&nbsp;&nbsp;*</font></label>
        <div class="layui-input-inline" style="width: 60%">
            <ht:countryoperatorselect countryNumber="cn" id="operator" layVerify="required" name="operator" />
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label" style="width: 20%">通道<font color="red">&nbsp;&nbsp;*</font></label>
        <div class="layui-input-inline" style="width: 60%">
            <ht:herocustomdataselect dataSourceType="allChannels" name="channel_No" layVerify="required"/>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label" style="width: 20%">权重<font color="red">&nbsp;&nbsp;*</font></label>
        <div class="layui-input-inline" style="width: 60%">
            <input type="number"  min="-2147483648" max="2147483647" name="weight" layVerify="required" value="50" class="layui-input layui-input-sm"/>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label" style="width: 20%">消息类型<font color="red">&nbsp;&nbsp;*</font></label>
        <div class="layui-input-inline" style="width: 60%">
            <ht:herocodeselect sortCode="message_type_code" name="message_Type_Code" layVerify="required"/>
        </div>
    </div>
    <div class="layui-form-item layui-hide">
        <input type="submit" lay-submit lay-filter="submit" id="layuiadmin-app-form-submit" value="确认">
    </div>
</form>
<%@ include file="/admin/common/layui_bottom.jsp" %>
</body>
<script>
    window.onload = function () {
        var product_No = $(window.parent.document).find("#product_No").val();
        $("#subProductNo").val(product_No);
    }

</script>