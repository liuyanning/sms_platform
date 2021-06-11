<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/admin/common/common.jsp" %>
<%@ include file="/admin/common/layui_head.html"%>
<body>
<form class="layui-form" id="subForm" action="/admin/enterprise_addUser" lay-filter="form"
	  style="padding: 20px 30px 0 0;">
	<div class="layui-form-item">
		<label class="layui-form-label">真实姓名</label>
		<div class="layui-input-inline">
			<input type="text" maxlength="128" name="real_Name" placeholder="请输入"  autocomplete="off" class="layui-input" >
		</div>
		<label class="layui-form-label">登录用户名<font color="red">&nbsp;&nbsp;*</font></label>
		<div class="layui-input-inline">
			<input type="text" maxlength="128" name="user_Name" lay-verify="required|userName" placeholder="请输入" autocomplete="off" class="layui-input">
		</div>
	</div>
	<div class="layui-form-item">
		<label class="layui-form-label">密码<font color="red">&nbsp;&nbsp;*</font></label>
		<div class="layui-input-inline">
			<input type="password" name="password" lay-verify="required|password" placeholder="请输入" autocomplete="off" class="layui-input">
		</div>
		<label class="layui-form-label">确认密码<font color="red">&nbsp;&nbsp;*</font></label>
		<div class="layui-input-inline">
			<input type="password" name="confirmPassword" lay-verify="required" placeholder="请输入" autocomplete="off" class="layui-input">
		</div>
	</div>
	<div class="layui-form-item">
        <label class="layui-form-label">行业类型</label>
        <div class="layui-input-inline">
            <ht:herocodeselect sortCode="trade" selected="verificationcode"  name="trade_Type_Code" />
        </div>
		<label class="layui-form-label">货币类型</label>
		<div class="layui-input-inline">
			<ht:herocodeselect sortCode="currency_type" selected="CNY" name="settlement_Currency_Type_Code" />
		</div>
	</div>
	<c:choose>
		<c:when test="${param.enterprise_No == null or param.enterprise_No == ''}">
			<div class="layui-form-item">
				<label class="layui-form-label">企业</label>
				<div class="layui-input-block" style="width: 65%">
					<ht:heroenterpriseselect name="enterprise_No"/>
				</div>
			</div>
		</c:when>
		<c:otherwise>
			<input  name="enterprise_No" hidden value="${param.enterprise_No}" />
		</c:otherwise>
	</c:choose>
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
<%@ include file="/admin/common/layui_bottom.jsp"%>
</body>
