<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/common/common.jsp" %>
<%@ include file="/common/layui_head.html" %>
<body>
<form class="layui-form" action="/admin/charge_addInvoice" lay-filter="form" onsubmit="return false;"
      style="padding: 20px 30px 0 0;">
    <div class="layui-form-item">
        <label class="layui-form-label">发票抬头<font color="red">&nbsp;&nbsp;*</font></label>
        <div class="layui-input-inline" >
            <input type="text" maxlength="512" name="invoice_Title"
                   lay-verify="required" placeholder="发票抬头" autocomplete="off"
                   class="layui-input">
        </div>
        <label class="layui-form-label">发票金额<font color="red">&nbsp;&nbsp;*</font></label>
        <div class="layui-input-inline" >
            <input type="text" name="invoice_Amount" autocomplete="off" class="layui-input"  lay-verify="required" placeholder="发票金额">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">发票类型<font color="red">&nbsp;&nbsp;*</font></label>
        <div class="layui-input-inline" >
            <ht:herocodeselect sortCode="invoice_type" name="invoice_Type_Code" />
        </div>
        <label class="layui-form-label">纳税人识别号<font color="red">&nbsp;&nbsp;*</font></label>
        <div class="layui-input-inline" >
            <input type="text" name="duty_Daragraph" autocomplete="off"  lay-verify="required"   class="layui-input" placeholder="纳税人识别号">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">开户行</label>
        <div class="layui-input-inline" >
            <input type="text" name="opening_Bank" autocomplete="off"  class="layui-input" placeholder="增值税专票必填">
        </div>
        <label class="layui-form-label">开户行账户</label>
        <div class="layui-input-inline">
            <input type="text" name="bank_Account" autocomplete="off"  class="layui-input" placeholder="增值税专票必填">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">企业电话<font color="red">&nbsp;&nbsp;*</font></label>
        <div class="layui-input-inline" >
            <input type="text" name="phone_No" autocomplete="off"  lay-verify="required"   class="layui-input" placeholder="企业电话">
        </div>
        <label class="layui-form-label">收件电话<font color="red">&nbsp;&nbsp;*</font></label>
        <div class="layui-input-inline" >
            <input type="text" name="recipient_Phone_No" autocomplete="off" lay-verify="required"  class="layui-input" placeholder="收件电话">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">企业地址<font color="red">&nbsp;&nbsp;*</font></label>
        <div class="layui-input-block" style="width: 72%">
            <input type="text" name="address" autocomplete="off"  lay-verify="required"  class="layui-input" placeholder="企业地址">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">收件地址<font color="red">&nbsp;&nbsp;*</font></label>
        <div class="layui-input-block" style="width: 72%">
            <input type="text" name="mailing_Address" autocomplete="off" lay-verify="required"  class="layui-input" placeholder="收件地址" >
        </div>
    </div>
    <div class="layui-form-item layui-hide">
        <input type="button" lay-submit lay-filter="submit" id="layuiadmin-app-form-submit" value="确认">
    </div>
</form>
<%@ include file="/common/layui_bottom.jsp" %>
</body>