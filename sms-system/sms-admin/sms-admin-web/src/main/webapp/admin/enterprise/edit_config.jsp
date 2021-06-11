<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/admin/common/common.jsp" %>
<%@ include file="/admin/common/layui_head.html"%>
<body>
<form class="layui-form" id="layui-form" action="/admin/enterprise_editUser" lay-filter="form" onsubmit="return false;"
      style="padding: 50px 30px 0 30px;">
    <input hidden name="id" value="<c:out value="${enterpriseUser.id}"/>"/>
    <div class="layui-form-item">
        <label class="layui-form-label">用户名称<font color="red">&nbsp;&nbsp;*</font></label>
        <div class="layui-input-inline">
            <input type="text"  value="<c:out value="${enterpriseUser.real_Name}"/>"  disabled="disabled" class="layui-input" >
        </div>
        <label class="layui-form-label">通道产品</label>
        <div class="layui-input-inline">
            <ht:heroproductselect  selected="${enterpriseUser.product_No}" name="product_No"/>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">优先级</label>
        <div class="layui-input-inline" >
            <ht:herocodeselect sortCode="009" selected="${enterpriseUser.priority_Level}"  name="priority_Level"/>
        </div>
        <label class="layui-form-label">签名</label>
        <div class="layui-input-inline" >
            <input type="text" maxlength="128" name="suffix" placeholder="请输入" autocomplete="off" class="layui-input"
                   value="<c:out value="${enterpriseUser.suffix}"/>">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">计费类型</label>
        <div class="layui-input-inline">
            <ht:herocodeselect sortCode="005" selected="${enterpriseUser.fee_Type_Code}" name="fee_Type_Code"
                               cssClass="required combox"/>
        </div>
        <label class="layui-form-label">结算类型</label>
        <div class="layui-input-inline">
            <ht:herocodeselect sortCode="pay_type" selected="${enterpriseUser.settlement_Type_Code}" name="settlement_Type_Code"
                               cssClass="required combox"/>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">免审核条数</label>
        <div class="layui-input-inline">
            <input type="text" maxlength="11" name="number_Of_Audits" value="<c:out value="${enterpriseUser.number_Of_Audits}"/>"
                   placeholder="请输入" autocomplete="off" class="layui-input">
        </div>
        <label class="layui-form-label">审核短信</label>
        <div class="layui-input-inline">
            <ht:herocodeselect sortCode="ApproveType" selected="${enterpriseUser.audit_Type_Code}" name="audit_Type_Code"/>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">未知返还</label>
        <div class="layui-input-inline">
            <input type="text" name="return_Unknown_Rate" value="<c:out value="${enterpriseUser.return_Unknown_Rate}"/>"
                   class="layui-input" lay-verify="required|number|uniqueNum">
            未知状态短信返还比例，0-1之间小数，0为不返还，1全部返还
        </div>
        <label class="layui-form-label">扩展号码</label>
        <div class="layui-input-inline">
            <input type="text" maxlength="21" name="sub_Code" value="<c:out value="${enterpriseUser.sub_Code}"/>"
                   placeholder="请输入" autocomplete="off" class="layui-input">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">MO码号类型</label>
        <div class="layui-input-inline">
            <input type="text" maxlength="128" name="mo_Sp_Type_Code" value="<c:out value="${enterpriseUser.mo_Sp_Type_Code}"/>"
                   placeholder="请输入" autocomplete="off" class="layui-input">
            [值00]：虚拟码号+导流码号+用户码号+自定义||
            [值01]：虚拟码号+自定义
        </div>
    </div>
    <div class="layui-form-item layui-hide">
        <input type="button" lay-submit lay-filter="submit" id="layuiadmin-app-form-submit" value="确认">
    </div>
</form>
<%@ include file="/admin/common/layui_bottom.jsp"%>
</body>
<script type="text/javascript">
    layui.use(['form', 'layer'], function () {
        var form = layui.form;
        form.verify({
            uniqueNum : function(value) {
                if (value>1 || value<0){
                    return '请输入0到1之间的数字';
                }
            }
        })
    });

</script>