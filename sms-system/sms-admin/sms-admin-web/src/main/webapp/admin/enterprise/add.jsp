<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/admin/common/common.jsp" %>
<%@ include file="/admin/common/layui_head.html" %>
<body>
<form class="layui-form" action="/admin/enterprise_add" lay-filter="form" onsubmit="return false;"
      style="padding: 20px 30px 0 0;">
    <div class="layui-form-item">
        <label class="layui-form-label">企业名称<font color="red">&nbsp;&nbsp;*</font></label>
        <div class="layui-input-block" style="width: 70%">
            <input type="text" maxlength="128" name="name" placeholder="请输入" autocomplete="off" class="layui-input"  lay-verify="required">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">企业联系人</label>
        <div class="layui-input-inline">
            <input type="text" maxlength="128" name="contract" placeholder="请输入" autocomplete="off" class="layui-input">
        </div>
        <label class="layui-form-label">联系方式</label>
        <div class="layui-input-inline">
            <input type="text" maxlength="11" name="phone_No" placeholder="请输入" autocomplete="off" class="layui-input">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">商务</label>
        <div class="layui-input-inline">
            <ht:herocustomdataselect dataSourceType="allBusinessUser" name="business_User_Id" />
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">邮箱</label>
        <div class="layui-input-block"  style="width: 70%">
            <input type="text" maxlength="64" name="email" placeholder="请输入" autocomplete="off" class="layui-input">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">企业地址</label>
        <div class="layui-input-block"  style="width: 70%">
            <input type="text" maxlength="256" name="address" placeholder="请输入" autocomplete="off" class="layui-input">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">备注</label>
        <div class="layui-input-block">
            <textarea type="text" maxlength="2048" name="remark" autocomplete="off" class="layui-textarea"></textarea>
        </div>
    </div>
    <div class="layui-form-item layui-hide">
        <input type="button" lay-submit lay-filter="submit" id="layuiadmin-app-form-submit" value="确认">
    </div>
</form>
<%@ include file="/admin/common/layui_bottom.jsp" %>
</body>