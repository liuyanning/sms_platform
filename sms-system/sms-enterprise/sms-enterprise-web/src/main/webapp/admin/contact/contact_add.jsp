<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/common/common.jsp" %>
<%@ include file="/common/layui_head.html" %>
<body>
<form class="layui-form" action="/admin/contact_addContact" lay-filter="form" onsubmit="return false;"
      style="padding: 20px 30px 0 0;">
    <div class="layui-form-item">
        <label class="layui-form-label">分组<font color="red">&nbsp;&nbsp;*</font></label>
        <div class="layui-input-inline">
            <ht:herocontactgroupselect name="group_Id" selected="${param.group_Id}" layVerify="required"/>
            提示：若无分组请先增加分组
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">手机号码<font color="red">&nbsp;&nbsp;*</font></label>
        <div class="layui-input-inline">
            <input type="text" maxlength="15" name="phone_No" lay-verify="required" autocomplete="off"
                   class="layui-input">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">真实姓名<font color="red">&nbsp;&nbsp;*</font></label>
        <div class="layui-input-inline">
            <input maxlength="128" name="real_Name" lay-verify="required" autocomplete="off" class="layui-input">
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label">性别</label>
        <div class="layui-input-inline">
            <input maxlength="32" name="gender" autocomplete="off" class="layui-input">
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label">生日</label>
        <div class="layui-input-inline">
            <input name="birthday" id="birthday" autocomplete="off" class="layui-input">
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label">联系地址</label>
        <div class="layui-input-inline">
            <input maxlength="512" name="address" autocomplete="off" class="layui-input">
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label">单位</label>
        <div class="layui-input-inline">
            <input maxlength="128" name="company" autocomplete="off" class="layui-input">
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label">职务</label>
        <div class="layui-input-inline">
            <input maxlength="128" name="position" autocomplete="off" class="layui-input">
        </div>
    </div>

    <div class="layui-form-item layui-hide">
        <input type="button" lay-submit lay-filter="submit" id="layuiadmin-app-form-submit" value="确认">
    </div>
</form>
<%@ include file="/common/layui_bottom.jsp" %>
</body>
<script src="/js/jquery-3.4.1.min.js"></script>
<script>
    $(function () {
        var groupId = localStorage.groupId;
        request.setAttribute("groupId", groupId);
    })
    layui.use('laydate', function(){
        var laydate = layui.laydate;
        laydate.render({     //创建时间选择框
            elem: '#birthday' //指定元素
            ,type:'date'
            ,trigger : 'click'
        });
    });
</script>