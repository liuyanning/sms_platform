<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/admin/common/common.jsp" %>
<%@ include file="/admin/common/layui_head.html" %>
<body>
<form class="layui-form" action="/admin/charge_editInvoice" lay-filter="form" onsubmit="return false;"
      style="padding: 20px 30px 0 0;">
    <input hidden name="id" value="<c:out value="${invoice.id}"/>">
    <div class="layui-form-item">
        <label class="layui-form-label">发票抬头<font color="red">&nbsp;&nbsp;*</font></label>
        <div class="layui-input-inline" >
            <input type="text" value="<c:out value="${invoice.invoice_Title}"/>"
                   lay-verify="required" placeholder="发票抬头" autocomplete="off" readonly
                   class="layui-input">
        </div>
        <label class="layui-form-label">发票金额<font color="red">&nbsp;&nbsp;*</font></label>
        <div class="layui-input-inline" >
            <input type="text"  value="<c:out value="${invoice.invoice_Amount}"/>" readonly
                   autocomplete="off" class="layui-input"  lay-verify="required" placeholder="发票金额">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">发票类型<font color="red">&nbsp;&nbsp;*</font></label>
        <div class="layui-input-inline" >
            <ht:herocodeselect sortCode="invoice_type"  disabled="disabled" selected="${invoice.invoice_Type_Code}" />
        </div>
        <label class="layui-form-label">纳税人识别号<font color="red">&nbsp;&nbsp;*</font></label>
        <div class="layui-input-inline" >
            <input type="text"   value="<c:out value="${invoice.duty_Daragraph}"/>" readonly
                   autocomplete="off"  lay-verify="required"   class="layui-input" placeholder="纳税人识别号">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">开户行</label>
        <div class="layui-input-inline" >
            <input type="text"   value="<c:out value="${invoice.opening_Bank}"/>" readonly
                   autocomplete="off"  class="layui-input" placeholder="增值税专票必填">
        </div>
        <label class="layui-form-label">开户行账户</label>
        <div class="layui-input-inline">
            <input type="text"  value="<c:out value="${invoice.bank_Account}"/>" readonly
                   autocomplete="off"  class="layui-input" placeholder="增值税专票必填">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">企业电话<font color="red">&nbsp;&nbsp;*</font></label>
        <div class="layui-input-inline" >
            <input type="text"   value="<c:out value="${invoice.phone_No}"/>" readonly
                   autocomplete="off"  lay-verify="required"   class="layui-input" placeholder="企业电话">
        </div>
        <label class="layui-form-label">收件电话<font color="red">&nbsp;&nbsp;*</font></label>
        <div class="layui-input-inline" >
            <input type="text"   value="<c:out value="${invoice.recipient_Phone_No}"/>" readonly
                   autocomplete="off" lay-verify="required"  class="layui-input" placeholder="收件电话">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">企业地址<font color="red">&nbsp;&nbsp;*</font></label>
        <div class="layui-input-block" style="width: 71%">
            <input type="text"   value="<c:out value="${invoice.address}"/>" readonly
                   autocomplete="off"  lay-verify="required"  class="layui-input" placeholder="企业地址">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">收件地址<font color="red">&nbsp;&nbsp;*</font></label>
        <div class="layui-input-block" style="width: 71%">
            <input type="text"   value="<c:out value="${invoice.mailing_Address}"/>" readonly
                   autocomplete="off" lay-verify="required"  class="layui-input" placeholder="收件地址" >
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">快递公司</label>
        <div class="layui-input-inline" >
            <input type="text" maxlength="512" name="express_Company"  value="<c:out value="${invoice.express_Company}"/>"
                   autocomplete="off" class="layui-input" placeholder="快递公司">
        </div>
        <label class="layui-form-label">快递单号</label>
        <div class="layui-input-inline" >
            <input type="text" maxlength="512" name="courier_Number"  value="<c:out value="${invoice.courier_Number}"/>"
                   autocomplete="off" class="layui-input" placeholder="快递单号">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">处理状态<font color="red">&nbsp;&nbsp;*</font></label>
        <div class="layui-input-inline" >
            <ht:herocodeselect sortCode="dispose_state_code" selected="${invoice.dispose_State_Code}" name="dispose_State_Code"/>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">备注</label>
        <div class="layui-input-block" >
            <textarea type="text" maxlength="2048" name="remark" class="layui-textarea" placeholder="拒绝原因">${invoice.remark}</textarea>
        </div>
    </div>
    <div class="layui-form-item layui-hide">
        <input type="button" lay-submit lay-filter="submit" id="layuiadmin-app-form-submit" value="确认">
    </div>
</form>
<%@ include file="/admin/common/layui_bottom.jsp" %>
</body>