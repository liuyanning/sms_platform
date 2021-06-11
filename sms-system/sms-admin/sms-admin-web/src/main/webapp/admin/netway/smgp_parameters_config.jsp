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
    <input type="hidden" name="id" value="${channel.id}">
    <input type="hidden" name="no" value="${channel.no}">
    <div class="layui-form-item">
        <label class="layui-form-label">企业代码 </label>
        <div class="layui-input-inline" style="width: 300px">
            <input type="text" maxlength="11" name="msg_src" autocomplete="off"  value="<c:out value="${reMap['msg_src'].value}"/>"  class="layui-input">
            <c:out value="${reMap['msg_src'].remark}"/>
        </div>
        <label class="layui-form-label">业务代码</label>
        <div class="layui-input-inline" style="width: 300px">
            <input type="text" maxlength="128" name="service_id" autocomplete="off"
                   value="<c:out value="${reMap['service_id'].value}"/>" class="layui-input">
            <c:out value="${reMap['service_id'].remark}"/>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">接入号</label>
        <div class="layui-input-inline" style="width: 300px">
            <input type="text" maxlength="128" name="src_term_id" value="<c:out value="${reMap['src_term_id'].value}"/>" autocomplete="off"
                   class="layui-input">
            <c:out value="${reMap['src_term_id'].remark}"/>
        </div>
        <label class="layui-form-label">收费类型</label>
        <div class="layui-input-inline" style="width: 300px">
            <input type="text" maxlength="11" name="fee_type" autocomplete="off"
                   value="<c:out value="${reMap['fee_type'].value}"/>" class="layui-input">
            <c:out value="${reMap['fee_type'].remark}"/>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">封顶费</label>
        <div class="layui-input-inline" style="width: 300px">
            <input type="text" maxlength="11" name="fixed_fee" autocomplete="off"
                   value="<c:out value="${reMap['fixed_fee'].value}"/>" class="layui-input">
            <c:out value="${reMap['fixed_fee'].remark}"/>
        </div>
        <label class="layui-form-label">计费用户号码</label>
        <div class="layui-input-inline" style="width: 300px">
            <input type="text" maxlength="128" name="charge_term_id" autocomplete="off"
                   value="<c:out value="${reMap['charge_term_id'].value}"/>" class="layui-input">
            <c:out value="${reMap['charge_term_id'].remark}"/>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">资费代码</label>
        <div class="layui-input-inline" style="width: 300px">
            <input type="text" maxlength="11" name="fee_code" autocomplete="off"
                   value="<c:out value="${reMap['fee_code'].value}"/>" class="layui-input">
            <c:out value="${reMap['fee_code'].remark}"/>
        </div>
        <label class="layui-form-label">短消息类型</label>
        <div class="layui-input-inline" style="width: 300px">
            <input type="text" maxlength="11" name="msg_type" value="<c:out value="${reMap['msg_type'].value}"/>" autocomplete="off"
                   class="layui-input">
            ${reMap['msg_type'].remark}
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">状态报告</label>
        <div class="layui-input-inline" style="width: 300px">
            <input type="text" maxlength="11" name="need_report" autocomplete="off" value="<c:out value="${reMap['need_report'].value}"/>"
                   class="layui-input">
            <c:out value="${reMap['need_report'].remark}"/>
        </div>
        <label class="layui-form-label">登录模式 <font color="red">&nbsp;&nbsp;*</font></label>
        <div class="layui-input-inline" style="width: 300px">
            <input type="text" maxlength="11" name="login_Mode" autocomplete="off" lay-verify="required"
                   value="<c:out value="${reMap['login_Mode'].value}"/>"    class="layui-input">
            <c:out value="${reMap['login_Mode'].remark}"/>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">心跳间隔 <font color="red">&nbsp;&nbsp;*</font></label>
        <div class="layui-input-inline" style="width: 300px">
            <input type="text" maxlength="11" name="active_test_time" autocomplete="off" lay-verify="required"
                   value="<c:out value="${reMap['active_test_time'].value}"/>"   class="layui-input">
            <c:out value="${reMap['active_test_time'].remark}"/>
        </div>
        <label class="layui-form-label">优先级</label>
        <div class="layui-input-inline" style="width: 300px">
            <input type="text" maxlength="11" name="priority" autocomplete="off"
                   value="<c:out value="${reMap['priority'].value}"/>"  class="layui-input">
            <c:out value="${reMap['priority'].remark}"/>
        </div>
    </div>
    <div class="layui-form-item">

        <label class="layui-form-label">号码池策略</label>
        <div class="layui-input-inline"  style="width: 300px">
            <ht:herocodeselect sortCode="024" selected="${reMap['phone_no_pool_strategy_status'].value}" name="phone_no_pool_strategy_status" cssClass="required combox"/>
            <c:out value="${reMap['phone_no_pool_strategy_status'].remark}"/>
        </div>
        <label class="layui-form-label">源码号长度</label>
        <div class="layui-input-inline"  style="width: 300px">
            <input type="text" maxlength="11" name="src_id_length" autocomplete="off"  value="<c:out value="${reMap['src_id_length'].value}"/>"    class="layui-input">
            <c:out value="${reMap['src_id_length'].remark}"/>
        </div>
    </div>
    <div class="layui-form-item">

        <label class="layui-form-label">是否支持emoji符号</label>
        <div class="layui-input-inline"  style="width: 300px">
            <ht:herocodeselect sortCode="006" selected="${reMap['is_support_emoji'].value}" name="is_support_emoji" cssClass="required combox"/>
        </div>
        <label class="layui-form-label">滑动窗口</label>
        <div class="layui-input-inline"  style="width: 300px">
            <input type="text" name="window_size" value="<c:out value="${reMap['window_size'].value}"/>"    class="layui-input">
            滑动窗口大小
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