<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/admin/common/common.jsp" %>
<%@ include file="/admin/common/layui_head.html"%>
<body>
<form class="layui-form" action="/admin/enterprise_editUser" lay-filter="form" onsubmit="return false;"
      style="padding: 20px 30px 0 0;">
    <input hidden name="id" value="<c:out value="${enterpriseUser.id}"/>"/>
    <div class="layui-form-item">
        <label class="layui-form-label">真实姓名</label>
        <div class="layui-input-inline">
            <input type="text" maxlength="128" name="real_Name" value="<c:out value="${enterpriseUser.real_Name}"/>"  placeholder="请输入" autocomplete="off" class="layui-input" >
        </div>
        <label class="layui-form-label">登录名</label>
        <div class="layui-input-inline">
            <input type="text" maxlength="128" readonly value="<c:out value="${enterpriseUser.user_Name}"/>"  placeholder="请输入" autocomplete="off" class="layui-input" >
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">密码</label>
        <div class="layui-input-inline">
            <input type="password"  maxlength="12" name="password" lay-verify="editPassword" placeholder="建议:修改后及时更新密码" autocomplete="off"
                   class="layui-input">
        </div>
        <label class="layui-form-label">确认密码</label>
        <div class="layui-input-inline">
            <input type="password" maxlength="12" name="confirmPassword"  lay-verify="editPassword" placeholder="建议:修改后及时更新密码" autocomplete="off"
                   class="layui-input">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">行业类型</label>
        <div class="layui-input-inline">
            <ht:herocodeselect sortCode="trade" selected="${enterpriseUser.trade_Type_Code}"  name="trade_Type_Code" />
        </div>
        <label class="layui-form-label">货币类型</label>
        <div class="layui-input-inline">
            <ht:herocodeselect sortCode="currency_type" selected="${enterpriseUser.settlement_Currency_Type_Code}" name="settlement_Currency_Type_Code" />
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">备注</label>
        <div class="layui-input-block">
            <textarea type="text" maxlength="2048" name="remark" autocomplete="off" class="layui-textarea">${enterpriseUser.remark}</textarea>
        </div>
    </div>
    <div class="layui-form-item layui-hide">
        <input type="button" lay-submit lay-filter="submit" id="layuiadmin-app-form-submit" value="确认">
    </div>
</form>
<%@ include file="/admin/common/layui_bottom.jsp"%>
<script >
    layui.use('laydate', function(){
        var laydate = layui.laydate;
        laydate.render({     //创建时间选择框
            elem: '#create_Date' //指定元素
            ,type:'datetime'
            ,trigger : 'click'
        });
    });
</script>
</body>