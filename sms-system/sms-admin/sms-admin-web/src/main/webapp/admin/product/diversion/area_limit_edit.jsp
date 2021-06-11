<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/admin/common/common.jsp" %>
<%@ include file="/admin/common/layui_head.html" %>
<script src="/js/jquery-3.4.1.min.js"></script>
<body>
<form class="layui-form" action="/admin/product/editProductChannelsDiversionAreaLimit" onsubmit="return false;"
      style="padding: 20px 30px 0 0;">
    <input type="text" name="id"  Id="id" value="<c:out value="${diversion.id}"/>" hidden/>
    <input type="text" name="strategy_Rule" Id="strategy_Rule" hidden/>
    <input type="text" name="product_Channels_Id" value="<c:out value="${productChannelsId}"/>" hidden/>
    <div class="layui-form-item">
        <label class="layui-form-label" style="width: 20%">发送区域:</label>
        <div class="layui-input-inline" style="width: 20%">
            <input type="checkbox" lay-filter="allCheckboxFilter" name="allCheckboxId" id ="allCheckboxId" title="全选">
        </div>
        <div class="layui-input-inline"  style="width:70%">
            <c:forEach items='${locationCode}' var="i">
                <div class="layui-input-inline" style="width:120px;">
                    <c:if test="${i.code != 'international' and i.code != 'all'}">
                        <input type="checkbox" name="checkboxArea" lay-filter="singleCheckboxFilter"  id = "checkboxArea${i.code}" value = "${i.code}" title="${i.name}">
                    </c:if>
                </div>
            </c:forEach>
        </div>
    </div>
    <div class="layui-form-item">
        <div class="layui-input-block"  style="width: 60%">
            <font color="red">说明：请选择允许发送的区域</font>
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

    var product_Channels_Id = $(window.parent.document).find("#product_Channels_Id").val();
    $("#product_Channels_Id").val(product_Channels_Id);

    var strategy_Rule = '${diversion.strategy_Rule}';
    if (strategy_Rule) {
        var array = strategy_Rule.split(",");
        for(var i=0;i<array.length;i++){
            var  idStr = "checkboxArea"+array[i];
            $("#"+idStr).prop("checked",true);
        }

    }
    //初始化全选按钮
    $("input:checkbox[name='allCheckboxId']").prop("checked",true);
    $("input:checkbox[name='checkboxArea']").each(function(t){
        var checked = $(this).prop('checked');
        if(!checked){
            $("input:checkbox[name='allCheckboxId']").prop("checked",false);
            return false;
        }
    });
    var form ;
    layui.use('form', function() {
        $ = layui.$;
        form = layui.form;
        //监听点击地域全选事件
        form.on('checkbox(allCheckboxFilter)', function(data){
            var allCheckboxId = $("input:checkbox[name='allCheckboxId']").prop("checked");
            // 获取区域的值
            $("input:checkbox[name='checkboxArea']").each(function(i){
                if(allCheckboxId){//全选
                    $(this).prop('checked',true);
                }else{//取消
                    $(this).prop('checked',false);
                }
            });
            form.render();
        });
        //监听地域是否全选事件
        form.on('checkbox(singleCheckboxFilter)', function(data){
            // 获取区域的值
            var allCheck = 1;
            $("input:checkbox[name='checkboxArea']").each(function(i){
                if(!$(this).is(':checked')){
                    allCheck = 0;
                }
            });
            if (allCheck){
                $("input:checkbox[name='allCheckboxId']").prop('checked',true);
            } else {
                $("input:checkbox[name='allCheckboxId']").prop('checked',false);
            }
            form.render();
        });
    });

    function setValues() {
        var area = '';
        $("input:checkbox[name='checkboxArea']").each(function(i){
            if ($(this).is(':checked')){
                area += $(this).val()+',';
            }
        });
        area = area.substring(0,area.lastIndexOf(','));
        $("#strategy_Rule").val(area);
    }

    layui.use(['form'], function () {
        var form = layui.form;
        form.render();//渲染
    })

</script>