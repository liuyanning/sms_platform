<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/admin/common/common.jsp" %>
<%@ include file="/admin/common/layui_head.html" %>
<body>
<form class="layui-form" action="/admin/set_enterpriseDomain" lay-filter="form" onsubmit="return false;"
      style="padding: 50px 30px 0 0;">
         <input type="hidden" name="id" value="<c:out value="${enterprise.id}"/>">
         <div class="layui-form-item">
             <label class="layui-form-label">域名  </label>
             <div class="layui-input-inline">
                 <input type="text" maxlength="128" name="domain"  lay-verType="tips"
                        autocomplete="off" class="layui-input" value="<c:out value="${enterprise.domain}"/>">
             </div>
         </div>
         <div class="layui-form-item">
             <label class="layui-form-label">登录地址  </label>
             <div class="layui-input-inline">
                 <input type="text" maxlength="128" name="login_Url"  lay-verType="tips"
                        autocomplete="off" class="layui-input" value="<c:out value="${enterprise.login_Url}"/>">
             </div>
         </div>
    <div class="layui-form-item layui-hide">
        <input type="submit" lay-submit lay-filter="submit" id="layuiadmin-app-form-submit" value="确认">
    </div>
</form>
<%@ include file="/admin/common/layui_bottom.jsp" %>
</body>
