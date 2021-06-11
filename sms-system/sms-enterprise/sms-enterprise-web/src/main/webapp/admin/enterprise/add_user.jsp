<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/common.jsp" %>
<%@ include file="/common/layui_head.html"%>
<body>
<form class="layui-form" action="/admin/enterprise_addUser" lay-filter="form" onsubmit="return false;"
	  style="padding: 20px 30px 0 0;">
	<div class="layui-form-item">
		<label class="layui-form-label">真实姓名</label>
		<div class="layui-input-inline" style="width: 60%">
			<input type="text" maxlength="128" name="real_Name" placeholder="请输入" autocomplete="off" class="layui-input" >
		</div>
	</div>
	<div class="layui-form-item">
		<label class="layui-form-label">登录用户名</label>
		<div class="layui-input-inline" style="width: 60%">
			<input type="text" maxlength="128" name="user_Name" placeholder="请输入"lay-verify="required|userName" autocomplete="off" class="layui-input">
		</div>
	</div>
	<div class="layui-form-item">
		<label class="layui-form-label">密码</label>
		<div class="layui-input-inline" style="width: 60%">
			<input type="password" name="password" placeholder="请输入" lay-verify="required|password" autocomplete="off" class="layui-input">
		</div>
	</div>
	<div class="layui-form-item">
		<label class="layui-form-label">确认密码</label>
		<div class="layui-input-inline" style="width: 60%">
			<input type="password" name="confirmPassword" lay-verify="required" placeholder="请输入" autocomplete="off" class="layui-input">
		</div>
	</div>
	<div class="layui-form-item">
		<label class="layui-form-label">货币类型</label>
		<div class="layui-input-inline" style="width: 60%">
			<ht:herocodeselect sortCode="currency_type" selected="CNY" name="settlement_Currency_Type_Code" />
		</div>
	</div>
	<div class="layui-form-item">
		<label class="layui-form-label">描述</label>
		<div class="layui-input-inline" style="width: 60%">
			<textarea type="text" maxlength="2048" name="description" autocomplete="off" class="layui-textarea"></textarea>
		</div>
	</div>
	<div class="layui-form-item layui-hide">
		<input type="button" lay-submit lay-filter="submit" id="layuiadmin-app-form-submit" value="确认">
	</div>
</form>
<%@ include file="/common/layui_bottom.jsp"%>
</body>