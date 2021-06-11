<!DOCTYPE html>
<meta http-equiv="Content-Type" content="multipart/form-data; charset=utf-8"/>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/common/common.jsp" %>
<%@ include file="/common/layui_head.html" %>
<script src="/js/jquery-3.4.1.min.js"></script>
<div class="layui-fluid">
    <form action="/admin/contact_editContact" id="subForm" class="layui-form" onsubmit="return false">
        <input hidden name="id" value="<c:out value="${contactExt.id}"/>"></input hidden>
        <input hidden name="enterprise_No" value="<c:out value="${contactExt.enterprise_No}"/>"></input hidden>
        <div class="pageFormContent" layoutH="57">
            <div class="layui-form-item">
                <label class="layui-form-label">分组<font color="red">&nbsp;&nbsp;*</font></label>
                <div class="layui-input-inline">
                    <ht:herocontactgroupselect name	="group_Id" selected="${contactExt.group_Id}" layVerify="required"/>
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">手机号码:</label>
                <div class="layui-input-inline">
                    <input maxlength="15" name="phone_No" class="layui-input" minlength="11" maxlength="11" size="30"
                           value="<c:out value="${contactExt.phone_No}"/>"/>
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">真实姓名:</label>
                <div class="layui-input-inline">
                    <input maxlength="128" name="real_Name" class="layui-input" size="30"
                           value="<c:out value="${contactExt.real_Name}"/>"/>
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">性别:</label>
                <div class="layui-input-inline">
                    <input name="gender" class="layui-input" size="30" value="<c:out value="${contactExt.gender}"/>">
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">生日:</label>
                <div class="layui-input-inline">
                    <input name="birthday" class="layui-input" id="date"
                           value="<fmt:formatDate value="${contactExt.birthday}" pattern="yyyy-MM-dd"/>" dateFmt="yyyy-MM-dd HH:mm:ss" size="30"></input>
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">联系地址:</label>
                <div class="layui-input-inline">
                    <input name="address" class="layui-input" size="30" value="<c:out value="${contactExt.address}"/>">
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">单位:</label>
                <div class="layui-input-inline">
                    <input name="company" class="layui-input" size="30" value="<c:out value="${contactExt.company}"/>">
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">职务:</label>
                <div class="layui-input-inline">
                    <input name="position" class="layui-input" size="30" value="<c:out value="${contactExt.position}"/>">
                </div>
            </div>
        </div>
        <div class="layui-form-item layui-hide">
            <div class="layui-input-block">
                <input class="layui-btn" type="submit" lay-submit lay-filter="submit" id="layuiadmin-app-form-submit"
                       value="立即提交">
            </div>
        </div>
    </form>
</div>
<script>


    layui.use(['form', 'layedit', 'laydate'], function () {
        var form = layui.form
            , layer = layui.layer
            , layedit = layui.layedit
            , laydate = layui.laydate;

        //日期
        laydate.render({
            elem: '#date',
            type: 'date',
            trigger: 'click' //采用click弹出
        });

        // 监听提交
        form.on('submit(submit)', function (data) {
            $.ajax({
                type: "POST",
                url: "/admin/contact_editContact",
                dataType: "json",
                data: $('#subForm').serialize(),
                success: function (data) {
                    if (data.code == "0") {
                        layer.msg('修改成功', {icon: 1, time: 2000}, function () {
                            window.parent.location.reload();
                        });
                    } else {
                        layer.msg('修改失败')
                    }
                }
            });
            return false;
        });
    })

</script>