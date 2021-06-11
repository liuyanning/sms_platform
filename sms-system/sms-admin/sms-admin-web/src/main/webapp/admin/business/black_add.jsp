<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/admin/common/common.jsp" %>
<%@ include file="/admin/common/layui_head.html" %>
<%@ include file="/admin/common/dynamic_data.jsp" %>
<%@ include file="/admin/common/country_operator.jsp" %>
<body>
<form class="layui-form" action="/admin/business_addBlack" lay-filter="form" onsubmit="return false;"
      style="padding: 20px 30px 0 0;">
    <div class="layui-form-item">
        <label class="layui-form-label">手机号码<font color="red">&nbsp;&nbsp;*</font></label>
        <div class="layui-input-inline">
            <input  type="text" maxlength="15" onkeyup="value=value.replace(/[^\d]/g,'')"   name="phone_No"  placeholder="请输入带国家代码的手机号" autocomplete="off"
                   value="<c:out value="${inbox.country_Code}${inbox.phone_No}"/>"   class="layui-input" > <font color="red">添加的手机号必须有国家代码(例如:86158xxxxxxxx)</font></div>
            <label class="layui-form-label">黑名单类型</label>
            <div class="layui-input-inline">
                <ht:herocodeselect sortCode="black_pool" name="pool_Code"/>
            </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">企业</label>
        <div class="layui-input-inline">
            <ht:heroenterpriseselect name="enterprise_No" id="enterprise_No"/>
        </div>
        <label class="layui-form-label">企业用户</label>
        <div class="layui-input-inline">
            <ht:heroenterpriseselect name="enterprise_User_Id" id="enterprise_User_Id"/>
            <font color="red">用户属性设置过滤黑名单为：是，参数才生效</font>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">备注</label>
        <div class="layui-input-block">
            <textarea type="text" maxlength="2048" name="remark" autocomplete="off" class="layui-textarea"></textarea>
        </div>
    </div>
    <div class="layui-form-item layui-hide">
        <input type="submit" lay-submit lay-filter="submit" id="layuiadmin-app-form-submit" value="确认">
    </div>
</form>
<%@ include file="/admin/common/layui_bottom.jsp" %>
</body>