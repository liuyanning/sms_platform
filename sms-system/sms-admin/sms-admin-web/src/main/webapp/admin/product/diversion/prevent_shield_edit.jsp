<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/admin/common/common.jsp" %>
<%@ include file="/admin/common/layui_head.html" %>
<script src="/js/jquery-3.4.1.min.js"></script>
<body>
<form class="layui-form" id="subForm" action="/admin/product/editProductChannelsDiversionPreventShield" onsubmit="return false;"
style="padding: 20px 30px 0 0;">
    <input type="text" name="id"  Id="id" value="<c:out value="${diversion.id}"/>" hidden/>
    <input type="text" name="strategy_Rule"  Id="strategy_Rule" hidden/>
    <input type="text" name="product_Channels_Id"  Id="product_Channels_Id" value="<c:out value="${productChannelsId}"/>" hidden/>
    <div class="layui-form-item">
        <label class="layui-form-label" style="width: 20%">类型</label>
        <div class="layui-input-inline" style="width: 60%">
            <ht:herocodeselect sortCode="diversion_prevent_shield_type_code" id="typeCode" name="typeCode" layVerify="required"/>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label" style="width: 20%">位置</label>
        <div class="layui-input-inline" style="width: 60%">
            <ht:herocodeselect sortCode="diversion_prevent_shield_position_config" id="position" name="position" layVerify="required"/>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label" style="width: 20%">长度<font color="red">&nbsp;&nbsp;*</font></label>
        <div class="layui-input-inline" style="width: 60%">
            <input name="randomLength" id="randomLength" lay-verify="required|number" class="layui-input"/>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label" style="width: 20%">状态<font color="red">&nbsp;&nbsp;*</font></label>
        <div class="layui-input-inline" style="width: 60%">
            <ht:herocodeselect sortCode="state" name="status_Code" selected="${diversion.status_Code}" layVerify="required"/>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label" style="width: 20%">内容<font color="red">&nbsp;&nbsp;*</font></label>
        <div class="layui-input-inline" style="width: 60%">
            <input name="randomLength" id="templateContent" lay-verify="required" class="layui-input"/>
            <font color="red">说明：文字+{0}。{0}为变量，例如：xxx{0}</font>
        </div>
    </div>
    <div class="layui-form-item" hidden>
        <input type="submit" lay-submit lay-filter="submit" id="layuiadmin-app-form-submit" value="提交" onclick="setValues()">
    </div>
</form>
<%@ include file="/admin/common/layui_bottom.jsp" %>
</body>
<script type="text/javascript">

    var strategy_Rule = '${diversion.strategy_Rule}';
    if (strategy_Rule) {
        var json = $.parseJSON(strategy_Rule);
        $("#position").val(json.position);
        $("#typeCode").val(json.typeCode);
        $("#randomLength").val(json.randomLength);
        $("#templateContent").val(json.templateContent);
    }

    layui.use(['form'], function () {
        var form = layui.form;
        form.render();//渲染
    })

    function setValues() {
        var position = $("#position").val();
        var typeCode = $("#typeCode").val();
        var templateContent = $("#templateContent").val();
        var randomLength = $("#randomLength").val();
        var strategy_Rule = '{"position":"';
        strategy_Rule += position;
        strategy_Rule += '", "typeCode":"';
        strategy_Rule += typeCode;
        strategy_Rule += '", "templateContent":"';
        strategy_Rule += templateContent;
        strategy_Rule += '", "randomLength":"';
        strategy_Rule += randomLength;
        strategy_Rule += '"}';
        $("#strategy_Rule").val(strategy_Rule);
    }

</script>