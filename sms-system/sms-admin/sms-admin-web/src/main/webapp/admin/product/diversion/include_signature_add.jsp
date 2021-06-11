<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/admin/common/common.jsp" %>
<%@ include file="/admin/common/layui_head.html" %>
<script src="/js/jquery-3.4.1.min.js"></script>
<body>
<form class="layui-form" action="/admin/product/addProductChannelsDiversionSignature" onsubmit="return false;"
      style="padding: 20px 30px 0 0;">
    <input type="hidden" name="id" value="<c:out value="${diversion.id}"></c:out>" />
    <input type="hidden" name="name" value="签名导流"/>
    <input type="hidden" name="strategy_Rule" id="strategy_Rule"/>
    <input type="hidden" name="product_Channels_Id" id="product_Channels_Id"/>
    <div class="layui-form-item">
        <label class="layui-form-label" style="width: 20%">签名<font color="red">&nbsp;&nbsp;*</font></label>
        <div class="layui-input-inline" style="width: 60%">
            <input maxlength="400" name="signature" id="signature" lay-verify="required" class="layui-input"/>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label" style="width: 20%">子编码<font color="red">&nbsp;&nbsp;*</font></label>
        <div class="layui-input-inline" style="width: 60%">
            <input maxlength="400" name="subCode" id="subCode" lay-verify="required" class="layui-input"/>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label" style="width: 20%">状态<font color="red">&nbsp;&nbsp;*</font></label>
        <div class="layui-input-inline" style="width: 60%">
            <ht:herocodeselect sortCode="state" name="status_Code" selected="${diversion.status_Code}" layVerify="required"/>
        </div>
    </div>

    <div class="layui-form-item layui-hide">
        <input type="submit" lay-submit lay-filter="submit" id="layuiadmin-app-form-submit" value="提交" onclick="setValues()">
    </div>
</form>
<%@ include file="/admin/common/layui_bottom.jsp" %>
</body>
<script type="text/javascript">
    var product_Channels_Id = $(window.parent.document).find("#product_Channels_Id").val();
    $("#product_Channels_Id").val(product_Channels_Id);

    function setValues() {
        $("#strategy_Rule").val(JSON.stringify({
            "signature" : $('#signature').val(),
            "subCode" : $('#subCode').val()
        }));
    }
</script>