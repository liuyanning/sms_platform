<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/admin/common/common.jsp" %>
<%@ include file="/admin/common/layui_head.html"%>
<head>
    <script src="/js/jquery-3.4.1.min.js"></script>
</head>
<body>
<form class="layui-form" action="/admin/product/diversionPhoneNoPoolEditSave" lay-filter="form" onsubmit="return false;"
	style="padding: 20px 30px 0 0;">
     <input name="id" id="id" type="hidden" value='<c:out value="${productChannelsDiversion.id }"></c:out>'/>
     <input name="strategy_Rule" id="strategy_Rule" type="hidden" />
    <div class="layui-form-item">
        <label class="layui-form-label" style="width: 80px">号码</label>
        <div class="layui-input-inline" style="width: 200px">
            <input class="layui-input" maxlength="20" name="callerNo" id="callerNo" lay-verify="required"/>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label" style="width: 80px">随机位数</label>
        <div class="layui-input-inline" style="width: 200px">
            <input class="layui-input" maxlength="10" name="appendRandomSize" id="appendRandomSize" lay-verify="required"/>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label" style="width: 80px">状态</label>
        <div class="layui-input-inline" style="width: 200px">
            <ht:herocodeselect sortCode="state" name="status_Code" selected="${productChannelsDiversion.status_Code}" layVerify="required"/>
        </div>
    </div>
     <div class="layui-form-item">
        <label class="layui-form-label" style="width: 80px">权重</label>
        <div class="layui-input-inline" style="width: 200px">
            <input maxlength="9" name="weight" class="layui-input" lay-verify="required" value='<c:out value="${productChannelsDiversion.weight }"></c:out>'/>
        </div>
    </div>
    <div class="layui-form-item layui-hide">
        <input type="submit" lay-submit lay-filter="submit" id="layuiadmin-app-form-submit" value="提交">
    </div>
</form>
<%@ include file="/admin/common/layui_bottom.jsp"%>
</body>
<script type="text/javascript">
    var strategy_Rule = ${productChannelsDiversion.strategy_Rule};
    $("#callerNo").val(strategy_Rule.callerNo);
    $("#appendRandomSize").val(strategy_Rule.appendRandomSize);
</script>