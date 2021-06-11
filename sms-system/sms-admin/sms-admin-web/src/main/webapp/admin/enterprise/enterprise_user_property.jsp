<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/admin/common/common.jsp" %>
<%@ include file="/admin/common/layui_head.html" %>
<body>
<form class="layui-form" id="layui-form" action="/admin/enterprise_editPropertySwitch" lay-filter="form" onsubmit="return false;"
      style="padding: 20px 30px 0 0;">
    <input hidden name="id" value="<c:out value="${enterpriseUserId}"/>"/>
    <div class="layui-form-item">
        <label class="layui-form-label" style="width: 100px">黑名单过滤</label>
        <div class="layui-input-inline">
            <ht:herocodeselect sortCode="006" selected="${blacklistSwitch}" name="blacklist_Switch"/>
            <span>开启黑名单限制到用户级别</span>
        </div>
        <label class="layui-form-label" style="width: 100px">签名位置</label>
        <div class="layui-input-inline">
            <ht:herocodeselect sortCode="user_signature_location" selected="${signatureLocation}" name="signatureLocation"/>
            <span>提交短信时默认签名位置</span>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label" style="width: 100px;">SGIP客户IP</label>
        <div class="layui-input-inline">
            <input type="text" maxlength="128" name="sgipSpIp" autocomplete="off"  value="<c:out value="${sgipSpIp}"/>" class="layui-input">
        </div>
        <label class="layui-form-label" style="width: 100px;">SGIP端口</label>
        <div class="layui-input-inline">
            <input type="text" maxlength="128" name="sgipSpPort" autocomplete="off"  value="<c:out value="${sgipSpPort}"/>" class="layui-input">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label" style="width: 100px;">国际区号过滤</label>
        <div class="layui-input-inline">
            <input type="text" maxlength="128" name="countryCodeValue" autocomplete="off"  value="<c:out value="${countryCodeValue}"/>" class="layui-input">
            <span>填写需要过滤的国际区号。例：+86 </span>
        </div>
        <label class="layui-form-label" style="width: 100px;">滑动窗口</label>
        <div class="layui-input-inline">
            <input type="text" name="windowSize" value="<c:out value="${windowSize}"/>" class="layui-input">
            <span>滑动窗口大小设置</span>
        </div>
    </div>
    <div class="layui-form-item layui-hide">
        <input type="button" lay-submit lay-filter="submit" id="layuiadmin-app-form-submit" value="确认">
    </div>
</form>
<%@ include file="/admin/common/layui_bottom.jsp" %>
</body>