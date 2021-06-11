<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/admin/common/common.jsp" %>
<%@ include file="/admin/common/layui_head.html" %>
<head>
    <meta http-equiv="Content-Type" content="multipart/form-data; charset=utf-8"/>
    <script src="/js/jquery-3.4.1.min.js"></script>
    <script src="/layuiadmin/layui/layui.all.js"></script>
    <script src="/js/jquery-form.js"></script>
</head>
<body>
<form class="layui-form"  id="subForm" action="/admin/charge_editChargeOrderPayType" onsubmit="return ajaxSubmitForm()"
      enctype="multipart/form-data" method="post" style="padding: 20px 30px 0 0;">
    <input type="hidden" name="id" value="<c:out value="${chargeOrder.id}"/>">
    <div class="layui-form-item">
        <label class="layui-form-label">企业名称</label>
        <div class="layui-input-inline">
            <input type="text" value="<c:out value="${chargeOrder.enterprise_Name}"/>" disabled="true"
                   autocomplete="off" class="layui-input">
        </div>
        <label class="layui-form-label">用户名称</label>
        <div class="layui-input-inline">
            <input type="text" value="<c:out value="${enterpriseUser.real_Name}"/>" disabled="true"
                   autocomplete="off" class="layui-input">
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label">充值金额</label>
        <div class="layui-input-inline">
            <input type="text" value="<c:out value="${chargeOrder.money}"/>" autocomplete="off" disabled="true"
                   class="layui-input">
        </div>
        <label class="layui-form-label">金额大写</label>
        <div class="layui-input-inline">
            <input type="text" value="<c:out value="${chargeOrder.money_Letter}"/>" disabled="true"
                   autocomplete="off" class="layui-input">
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label">充值人</label>
        <div class="layui-input-inline">
            <input type="text" value="<c:out value="${chargeOrder.input_User}"/>" disabled="true"
                   autocomplete="off" class="layui-input">
        </div>
        <label class="layui-form-label">充值时间</label>
        <div class="layui-input-inline">
            <input type="text" value="<fmt:formatDate value="${chargeOrder.input_Date}" pattern="yyyy-MM-dd"/>"
                   disabled="true"
                   autocomplete="off" class="layui-input">
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label">充值备注</label>
        <div class="layui-input-inline">
            <input type="text" value="<c:out value="${chargeOrder.input_Remark}"/>" disabled="true"
                   autocomplete="off" class="layui-input">
        </div>
        <label class="layui-form-label">转账凭证</label>
        <div class="layui-input-inline" id="layer-photos-demo">
            <c:if test="${chargeOrder.transfer_Img == '' or chargeOrder.transfer_Img == null}">
                <input id="file" type="file" name="file">
            </c:if>
            <c:if test="${chargeOrder.transfer_Img != '' and chargeOrder.transfer_Img != null}">
                <img width="100px" height="100px" layer-src="${chargeOrder.transfer_Img}" src="${chargeOrder.transfer_Img}" alt="转账凭证">
            </c:if>
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label">支付方式</label>
        <div class="layui-input-inline">
            <ht:herocodeselect sortCode="018" name="pay_Type_Code" selected="${chargeOrder.pay_Type_Code}"/>
        </div>
    </div>
    <div class="layui-form-item layui-hide">
        <input type="submit" lay-submit lay-filter="submit" id="layuiadmin-app-form-submit" value="确认">
    </div>
</form>
<%@ include file="/admin/common/layui_bottom.jsp" %>
</body>
<script>
    layui.use(['form', 'layedit', 'laydate'], function () {
        var form = layui.form
            , layer = layui.layer
        layer.photos({
            photos: '#layer-photos-demo',
            area: ['600px', '400px'],
            anim: 1 ,//0-6的选择，指定弹出图片动画类型，默认随机（请注意，3.0之前的版本用shift参数）
            closeBtn:1
        })
        form.render();
    })
    function ajaxSubmitForm() {
        var $ = layui.$;
        var data = $("#subForm").val();
        var file = $("#file").val();
        $("#subForm").ajaxSubmit({
            type: 'post', // 提交方式 get/post
            url: '/admin/charge_editChargeOrderPayType', // 需要提交的 url
            dataType: 'json',
            data: data,
            success: function (d) { // data 保存提交后返回的数据，一般为 json 数据
                if(d.code = '0'){
                    layer.msg('提交成功', {icon: 1, time: 2000}, function () {
                        window.parent.location.reload();
                    });
                    return false;
                }else{
                    layer.msg(d.msg, {icon: 2, time: 2000}, function () {
                    });
                    return false;
                }
            },
            error: function (d) {
                layer.msg('提交失败', {icon: 2, time: 2000}, function () {
                });
                return false;
            }
        })
        return false; // 必须返回false，否则表单会自己再做一次提交操作，并且页面跳转
    }


</script>