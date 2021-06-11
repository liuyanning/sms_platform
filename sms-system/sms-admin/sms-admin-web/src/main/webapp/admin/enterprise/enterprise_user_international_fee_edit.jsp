<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/admin/common/common.jsp" %>
<%@ include file="/admin/common/layui_head.html" %>
<script src="/js/jquery-3.4.1.min.js"></script>

<body>
<form class="layui-form" action="/admin/enterprise_editEnterpriseUserFee" lay-filter="form" onsubmit="return false;"
      style="padding: 20px 30px 0 0;">
    <input name="id" value="<c:out value="${enterpriseUserFee.id}"/>" hidden/>
    <input name="enterprise_No" value="<c:out value="${enterpriseUserFee.enterprise_No}"/>"  hidden/>
    <input name="enterprise_User_Id" value="<c:out value="${enterpriseUserFee.enterprise_User_Id}"/>"  hidden/>
    <div class="layui-form-item">
        <label class="layui-form-label" style="width: 20%">运营商<font color="red">&nbsp;&nbsp;*</font></label>
        <div class="layui-input-inline" style="width: 60%">
            <ht:herocodeselect sortCode="001" selected="${enterpriseUserFee.operator}" layVerify="required" name="operator"/>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label" style="width: 20%">单价(元)<font color="red">&nbsp;&nbsp;*</font></label>
        <div class="layui-input-inline" style="width: 60%">
            <input type="number" step="0.0001" min="-99999999.9999" max="99999999.9999" name="unit_Price" layVerify="required" value="<c:out value="${enterpriseUserFee.unit_Price}"/>" class="layui-input layui-input-sm"/>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label" style="width: 20%">税点</label>
        <div class="layui-input-inline" style="width: 60%">
            <input type="number" step="0.0001" min="-99999999.9999" max="99999999.9999" name="tax_Point" value="<c:out value="${enterpriseUserFee.tax_Point}"/>" class="layui-input layui-input-sm"/>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label" style="width: 20%">行业<font color="red">&nbsp;&nbsp;*</font></label>
        <div class="layui-input-inline" style="width: 60%">
            <ht:herocodeselect sortCode="trade" selected="${enterpriseUserFee.trade_Type_Code}" layVerify="required" name="trade_Type_Code"/>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label" style="width: 20%">消息类型<font color="red">&nbsp;&nbsp;*</font></label>
        <div class="layui-input-inline" style="width: 60%">
            <ht:herocodeselect sortCode="message_type_code" selected="${enterpriseUserFee.message_Type_Code}"  name="message_Type_Code"/>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label" style="width: 20%">资费类型<font color="red">&nbsp;&nbsp;*</font></label>
        <div class="layui-input-inline" style="width: 60%">
            <ht:herocodeselect sortCode="fee_type_code" name="fee_Type_Code" selected="02" disabled="true"/>
        </div>
    </div>

    <div class="layui-form-item layui-hide">
        <input type="submit" lay-submit lay-filter="submit" id="layuiadmin-app-form-submit" value="确认">
    </div>
</form>
<%@ include file="/admin/common/layui_bottom.jsp" %>
</body>
