<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/admin/common/common.jsp" %>
<%@ include file="/admin/common/layui_head.html" %>
<body>
<form class="layui-form" action="/admin/charge_open" lay-filter="form" onsubmit="return false;"
      style="padding: 20px 30px 0 0;">
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
            <input type="text" value="<c:out value="${chargeOrder.money}"/>" disabled="true" autocomplete="off"
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
            <input type="text" value="<fmt:formatDate value="${chargeOrder.input_Date}" pattern="yyyy-MM-dd HH:mm:ss"/>"
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
                <div class="layui-form-mid layui-word-aux">无凭证</div>
            </c:if>
            <c:if test="${chargeOrder.transfer_Img != '' and chargeOrder.transfer_Img != null}">
                <img width="100px" height="100px" layer-src="${chargeOrder.transfer_Img}" src="${chargeOrder.transfer_Img}" alt="转账凭证">
            </c:if>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">支付方式</label>
        <div class="layui-input-inline">
            <ht:herocodeselect sortCode="018" selected="${chargeOrder.pay_Type_Code}" disabled="disabled"/>
        </div>
        <label class="layui-form-label">开通</label>
        <div class="layui-input-inline">
            <ht:herocodeselect sortCode="017" selected="${chargeOrder.open_Status_Code}" name="open_Status_Code"
                               cssClass="required"/>
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label">备注</label>
        <div class="layui-input-block">
            <textarea type="text" name="open_Remark" autocomplete="off" class="layui-textarea"><c:out
                    value="${chargeOrder.open_Remark}"/></textarea>
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
    })


</script>