<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/admin/common/common.jsp" %>
<%@ include file="/admin/common/money_letter.jsp" %>
<%@ include file="/admin/common/layui_head.html"%>

<body>
<form class="layui-form" action="/admin/charge_charge" id="subForm" onsubmit="return ajaxSubmitForm()"
      enctype="multipart/form-data" method="post" style="padding: 20px 30px 0 0;">
    <input hidden name="enterprise_No" value="<c:out value="${enterprise.no}"/>"/>
    <div class="layui-form-item">
        <label class="layui-form-label">企业名称</label>
        <div class="layui-input-inline">
            <input type="text"  name="enterprise_Name" value="<c:out value="${enterprise.name}"/>" readonly="true" autocomplete="off" class="layui-input" >
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">充值账户</label>
        <div class="layui-input-inline">
            <select name="enterprise_User_Id" id="enterprise_User_Id" >
                <c:forEach items="${userList}" var="user">
                    <option value="${user.id}">${user.real_Name}</option>
                </c:forEach>
            </select>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">充值金额</label>
        <div class="layui-input-inline">
            <input type="number" step="0.0001" min="-999999999.9999" max="999999999.9999" name="money"
                   onblur="letterValue('money_Letter','money')" alt="(单位:元)"
                   placeholder="请输入" autocomplete="off" class="layui-input">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">金额大写</label>
        <div class="layui-input-inline">
            <input type="text" name="money_Letter" placeholder="请输入" autocomplete="off" class="layui-input" >
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">支付方式</label>
        <div class="layui-input-inline">
            <ht:herocodeselect sortCode="018"
                               name="pay_Type_Code"/>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">备注</label>
        <div class="layui-input-inline">
            <input type="text" maxlength="2048" name="input_Remark"  placeholder="请输入" autocomplete="off" class="layui-input" >
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">转账凭证</label>
        <div class="layui-input-inline">
            <input id="file" type="file" name="file">
        </div>
    </div>
    <div class="layui-form-item layui-hide">
        <button class="layui-btn" id="layuiadmin-app-form-submit">提交</button>
    </div>

</form>
<%@ include file="/admin/common/layui_bottom.jsp"%>

<script >
    layui.use(['form'], function () {
        var form = layui.form
        form.render();
    });


    function ajaxSubmitForm() {

        var data = $("#subForm").val();
        var enterprise_User_Id = $("#enterprise_User_Id").val();
        if (!enterprise_User_Id){
            layer.msg('请选择充值账户！')
            return false;
        }
        var money = $("input[name='money']").val();
        if (!money){
            layer.msg('充值金额不能为空')
            return false;
        }
        var pay_Type_Code = $("select[name='pay_Type_Code']").val();
        if (!pay_Type_Code){
            layer.msg('支付方式不能为空')
            return false;
        }
        var loading= parent.layer.load('', {time: 10*1000});
        $("#subForm").ajaxSubmit({
            type: 'post', // 提交方式 get/post
            url: '/admin/charge_charge', // 需要提交的 url
            dataType: 'json',
            data: data,
            success: function (d) { // data 保存提交后返回的数据，一般为 json 数据
                // 此处可对 data 作相关处理

                layer.alert('提交成功', function () {
                    $('#subForm')[0].reset();
                    parent.layer.closeAll();
                });
                $('#layuiadmin-app-form-submit').attr('disabled',true);
                parent.layer.close(loading);
            },
            error: function (d) {
                parent.layer.close(loading);
                layer.msg('提交失败', {icon: 2, time: 2000}, function () {
                    $('#layuiadmin-app-form-submit').attr('disabled',false);
                });
            }
        })
        return false; // 必须返回false，否则表单会自己再做一次提交操作，并且页面跳转
    }
</script>
</body>