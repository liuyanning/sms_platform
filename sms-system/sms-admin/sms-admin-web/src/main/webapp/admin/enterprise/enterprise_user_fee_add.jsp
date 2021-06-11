<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/admin/common/common.jsp" %>
<%@ include file="/admin/common/layui_head.html" %>
<%@ include file="/admin/common/country_operator.jsp" %>

<body>
<form class="layui-form" action="/admin/enterprise_addEnterpriseUserFee" lay-filter="form" onsubmit="return false;"
      style="padding: 20px 30px 0 0;">
    <input name="enterprise_User_Id" id="subEnterpriseUserId" lay-verify="required" hidden/>
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
        <label class="layui-form-label" style="width: 20%">单价(元)<font color="red">&nbsp;&nbsp;*</font></label>
        <div class="layui-input-inline" style="width: 60%">
            <input type="number" step="0.0001" min="-99999999.9999" max="99999999.9999" name="unit_Price" layVerify="required" class="layui-input layui-input-sm"/>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label" style="width: 20%">税点</label>
        <div class="layui-input-inline" style="width: 60%">
            <input type="number" step="0.0001" min="-99999999.9999" max="99999999.9999" name="tax_Point" class="layui-input layui-input-sm"/>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label" style="width: 20%">行业<font color="red">&nbsp;&nbsp;*</font></label>
        <div class="layui-input-inline" style="width: 60%">
            <ht:herocodeselect sortCode="trade" layVerify="required" name="trade_Type_Code"/>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label" style="width: 20%">消息类型<font color="red">&nbsp;&nbsp;*</font></label>
        <div class="layui-input-inline" style="width: 60%">
            <ht:herocodeselect sortCode="message_type_code" name="message_Type_Code"/>
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
        var enterpriseUserId = $(window.parent.document).find("#enterpriseUserId").val();
        $("#subEnterpriseUserId").val(enterpriseUserId);
    }

</script>