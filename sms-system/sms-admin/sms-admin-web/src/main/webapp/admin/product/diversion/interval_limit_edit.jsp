<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/admin/common/common.jsp" %>
<%@ include file="/admin/common/layui_head.html" %>
<script src="/js/jquery-3.4.1.min.js"></script>
<body>
<form class="layui-form" action="/admin/product/editProductChannelsDiversionIntervalLimit" onsubmit="return false;"
      style="padding: 20px 30px 0 0;">
    <input type="text" name="id"  Id="id" value="<c:out value="${diversion.id}"/>" hidden/>
    <input type="text" name="strategy_Rule"  Id="strategy_Rule" hidden/>
    <input type="text" name="product_Channels_Id" value="<c:out value="${productChannelsId}"/>" hidden/>
    <div class="layui-form-item">
        <label class="layui-form-label" style="width: 20%">手机号限制:</label>
        <div class="layui-input-inline" style="width: 70%">
            <c:forEach items='${limitCode}' var="i">
                <div class="layui-input-inline" style="width:90px;">
                    <input type="text" maxlength="11" class="layui-input" style="width:80px;"
                           value="" name="phoneNos" id="phone${i.value}" data-custom="${i.value}"><font>${i.name}</font>
                </div>
            </c:forEach>
        </div>
    </div>
    <div class="layui-form-item">
        <div class="layui-input-block"  style="width:60%">
            <font color="red">说明：同一个手机号条数限制</font>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label" style="width: 20%">同内容限制:</label>
        <div class="layui-input-inline" style="width: 70%">
            <c:forEach items='${limitCode}' var="i">
                <div class="layui-input-inline" style="width:90px;">
                    <input type="text" maxlength="11" class="layui-input" style="width:80px;"
                           value="" name="content" id="content${i.value}" data-custom="${i.value}"><font>${i.name}</font>
                </div>
            </c:forEach>
        </div>
    </div>
    <div class="layui-form-item">
        <div class="layui-input-block"  style="width:60%">
            <font color="red">说明：同一个手机号相同内容条数限制</font>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label" style="width: 20%">状态<font color="red">&nbsp;&nbsp;*</font></label>
        <div class="layui-input-inline" style="width: 60%">
            <ht:herocodeselect sortCode="state" layVerify="required" name="status_Code" selected="${diversion.status_Code}"/>
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
        var phoneNos = json.phoneNos;
        for(var p in phoneNos){
            var  idStr = "phone"+p;
            $("#" + idStr).val(phoneNos[p]);
        }
        var content = json.content;
        for(var p1 in content){
            var  idStr1 = "content"+p1;
            $("#" + idStr1).val(content[p1]);
        }

    }
    function setValues() {
        var limitRepeat = '{"phoneNos":{"';
        var phoneNosFlag = 0;
        $("input[name='phoneNos']").each(function(){
            if($(this).val()){
                var key = $(this).attr("data-custom");
                limitRepeat += key;
                limitRepeat += '":';
                limitRepeat += $(this).val();
                limitRepeat += ',"';
                phoneNosFlag = 1;
            }
        })
        if(phoneNosFlag){
            limitRepeat = limitRepeat.substring(0,limitRepeat.lastIndexOf(',"'));
        }else {
            limitRepeat = limitRepeat.substring(0,limitRepeat.length-1);
        }
        var contentFlag = 0;
        limitRepeat += '},"content":{"';
        $("input[name='content']").each(function(){
            if($(this).val()){
                var key1 = $(this).attr("data-custom");
                limitRepeat += key1;
                limitRepeat += '":';
                limitRepeat += $(this).val();
                limitRepeat += ',"';
                contentFlag = 1;
            }
        })
        if(contentFlag){
            limitRepeat = limitRepeat.substring(0,limitRepeat.lastIndexOf(',"'));
        }else {
            limitRepeat = limitRepeat.substring(0,limitRepeat.length-1);
        }
        limitRepeat += '}}';
        $("#strategy_Rule").val(limitRepeat);
    }

</script>