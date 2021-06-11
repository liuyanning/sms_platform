<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/admin/common/common.jsp" %>
<%@ include file="/admin/common/layui_head.html" %>
<body>
<form class="layui-form" action="/admin/business_addSmsRoute" lay-filter="form" onsubmit="return false;"
      style="padding: 20px 30px 0 0;">
    <div class="layui-form-item">
        <label class="layui-form-label">路由前缀<font color="red">&nbsp;&nbsp;*</font></label>
        <div class="layui-input-inline">
            <input type="text" maxlength="64"  name="prefix_Number" lay-verify="required" placeholder="请输入" autocomplete="off"
                   class="layui-input">
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label" >国家<font color="red">&nbsp;&nbsp;*</font></label>
        <div class="layui-input-inline" >
            <ht:herocodeselect sortCode="country" name="country_Number" id="country_Number" selected="cn" valueField="Value" layVerify="required"/>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label" >运营商<font color="red">&nbsp;&nbsp;*</font></label>
        <div class="layui-input-inline" >
            <input type="text" maxlength="64"  name="route_Name_Code" lay-verify="required" placeholder="请输入" autocomplete="off"
                   class="layui-input">
        </div>
    </div>



    <div class="layui-form-item">
        <label class="layui-form-label">国家代码<font color="red">&nbsp;&nbsp;*</font></label>
        <div class="layui-input-inline">
            <input type="text"  maxlength="128" name="country_Code" lay-verify="required" placeholder="请输入" autocomplete="off"
                   class="layui-input">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">MCC码<font color="red">&nbsp;&nbsp;*</font></label>
        <div class="layui-input-inline">
            <input type="text"  maxlength="128" name="MCC" lay-verify="required" placeholder="请输入" autocomplete="off"
                   class="layui-input">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">MNC码<font color="red">&nbsp;&nbsp;*</font></label>
        <div class="layui-input-inline">
            <input type="text"  maxlength="128" name="MNC" lay-verify="required" placeholder="请输入" autocomplete="off"
                   class="layui-input">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">备注</label>
        <div class="layui-input-block">
            <textarea type="text"  maxlength="2048"  name="remark" autocomplete="off" class="layui-textarea"></textarea>
        </div>
    </div>
    <div class="layui-form-item layui-hide">
        <input type="submit" lay-submit lay-filter="submit" id="layuiadmin-app-form-submit" value="确认">
    </div>
</form>
<%@ include file="/admin/common/layui_bottom.jsp" %>
</body>