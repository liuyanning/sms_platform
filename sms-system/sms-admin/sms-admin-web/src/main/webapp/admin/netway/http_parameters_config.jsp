<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/admin/common/common.jsp" %>
<%@ include file="/admin/common/layui_head.html" %>
<style>
    .layui-form-label{
        width: 120px;
    }
</style>
<body>
<form class="layui-form" action="/admin/netway_editChannelParameters" lay-filter="form" onsubmit="return false;"
      style="padding: 20px 30px 0 0;">
    <input hidden name="id" value="<c:out value="${channel.id}"/>"/>
    <input hidden name="no" value="<c:out value="${channel.no}"/>"/>
    <hr class="layui-bg-white">
    <div class="layui-form-item">
        <label class="layui-form-label">平台供应商</label>
        <div class="layui-input-inline"  style="width: 300px">
            <ht:herocodeselect sortCode="FullClassImpl" selected="${reMap['full_class_impl'].value}" name="full_class_impl" cssClass="required combox"/>
            <c:out value="${reMap['full_class_impl'].remark}"/>
        </div>
    </div>
    <div class="layui-form-item">
        <div class="layui-form-item">
            <label class="layui-form-label">号码分隔符</label>
            <div class="layui-input-inline" style="width: 300px;">
                <input type="text" maxlength="128"name="mobile_split" autocomplete="off"  value="<c:out value="${reMap['mobile_split'].value}"/>" class="layui-input">
                <c:out value="${reMap['mobile_split'].remark}"/>
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">最大号码 </label>
            <div class="layui-input-inline"  style="width: 300px">
                <input type="text" maxlength="11" name="max_sms_number" autocomplete="off"  value="<c:out value="${reMap['max_sms_number'].value}"/>" class="layui-input" >
                <c:out value="${reMap['max_sms_number'].remark}"/>
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">最大字符 </label>
            <div class="layui-input-inline"  style="width: 300px">
                <input type="text" maxlength="11" name="max_word_total" autocomplete="off"  value="<c:out value="${reMap['max_word_total'].value}"/>" class="layui-input">
                <c:out value="${reMap['max_word_total'].remark}"/>
            </div>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">提交地址</label>
        <div class="layui-input-inline"  style="width: 700px">
            <input type="text" maxlength="128" name="interface_url" autocomplete="off"  value="<c:out value="${reMap['interface_url'].value}"/>" class="layui-input">
            <c:out value="${reMap['interface_url'].remark}"/>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">状态地址</label>
        <div class="layui-input-inline"  style="width: 700px">
            <input type="text" maxlength="128" name="report_url" autocomplete="off"  value="<c:out value="${reMap['report_url'].value}"/>" class="layui-input">
            <c:out value="${reMap['report_url'].remark}"/>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">上行地址</label>
        <div class="layui-input-inline"  style="width: 700px">
            <input type="text" maxlength="128" name="mo_url" autocomplete="off"  value="<c:out value="${reMap['mo_url'].value}"/>" class="layui-input">
            <c:out value="${reMap['mo_url'].remark}"/>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">余额地址</label>
        <div class="layui-input-inline"  style="width: 700px">
            <input type="text" maxlength="128" name="balance_url" autocomplete="off"  value="<c:out value="${reMap['balance_url'].value}"/>" class="layui-input">
            <c:out value="${reMap['balance_url'].remark}"/>
        </div>
    </div>
    <div class="layui-form-item">
        <div class="layui-inline">
            <label class="layui-form-label">扩展信息</label>
            <div class="layui-input-inline" style="width: 100px;">
                <input type="text" maxlength="128" name="extInfo1" autocomplete="off" placeholder="扩展信息1" value="<c:out value="${reMap['extInfo1'].value}"/>"  class="layui-input" >
                <c:out value="${reMap['extInfo1'].remark}"/>
            </div>
            <div class="layui-input-inline" style="width: 100px;">
                <input type="text" maxlength="128" name="extInfo2" autocomplete="off" placeholder="扩展信息2" value="<c:out value="${reMap['extInfo2'].value}"/>" class="layui-input" >
                <c:out value="${reMap['extInfo2'].remark}"/>
            </div>
            <div class="layui-input-inline" style="width: 100px;">
                <input type="text" maxlength="128" name="extInfo3" autocomplete="off" placeholder="扩展信息3" value="<c:out value="${reMap['extInfo3'].value}"/>"  class="layui-input" >
                <c:out value="${reMap['extInfo3'].remark}"/>
            </div>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">是否支持emoji符号</label>
        <div class="layui-input-inline"  style="width: 300px">
            <ht:herocodeselect sortCode="006" selected="${reMap['is_support_emoji'].value}" name="is_support_emoji" cssClass="required combox"/>
        </div>
        <label class="layui-form-label"></label>
        <div class="layui-input-inline"  style="width: 300px">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">通道类型</label>
        <div class="layui-input-inline"  style="width: 300px">
            <ht:herocodeselect sortCode="channel_msg_type" selected="${reMap['channel_msg_type'].value}" name="channel_msg_type" cssClass="required combox"/>
        </div>
        <label class="layui-form-label"></label>
        <div class="layui-input-inline"  style="width: 300px">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">签名长度</label>
        <div class="layui-input-inline"  style="width: 300px">
            <input type="text" maxlength="128" name="signature_length" autocomplete="off"  value="<c:out value="${reMap['signature_length'].value}"/>"    class="layui-input">
        </div>
        <label class="layui-form-label"></label>
            <div class="layui-input-inline"  style="width: 300px">
        </div>
    </div>

    <div class="layui-form-item layui-hide">
        <input type="button" lay-submit lay-filter="submit" id="layuiadmin-app-form-submit" value="确认">
    </div>
</form>
<%@ include file="/admin/common/layui_bottom.jsp" %>
</body>