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
        <label class="layui-form-label">service type </label>
        <div class="layui-input-inline" style="width: 300px">
            <input type="text" maxlength="128" name="service_type" autocomplete="off"  value="<c:out value="${reMap['service_type'].value}"/>"  class="layui-input">
            <c:out value="${reMap['service_type'].remark}"/>
        </div>
        <label class="layui-form-label">protocol ID</label>
        <div class="layui-input-inline" style="width: 300px">
            <input type="text" maxlength="128" name="protocol_ID" autocomplete="off"
                   value="<c:out value="${reMap['protocol_ID'].value}"/>" class="layui-input">
            <c:out value="${reMap['protocol_ID'].remark}"/>
        </div>

    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">priorityflag</label>
        <div class="layui-input-inline" style="width: 300px">
            <input type="text" maxlength="128" name="priority_flag" value="<c:out value="${reMap['priority_flag'].value}"/>" autocomplete="off"
                   class="layui-input">
            <c:out value="${reMap['priority_flag'].remark}"/>
        </div>
        <label class="layui-form-label"></label>
        <div class="layui-input-inline"  style="width: 300px">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">计划时间</label>
        <div class="layui-input-inline" style="width: 300px">
            <input type="text" maxlength="128" name="schedule_delivery_time" autocomplete="off"
                   value="<c:out value="${reMap['schedule_delivery_time'].value}"/>" class="layui-input">
            <c:out value="${reMap['schedule_delivery_time'].remark}"/>
        </div>
        <label class="layui-form-label">生存期限</label>
        <div class="layui-input-inline" style="width: 300px">
            <input type="text" maxlength="128" name="validity_period" autocomplete="off"
                   value="<c:out value="${reMap['validity_period'].value}"/>" class="layui-input">
            <c:out value="${reMap['validity_period'].remark}"/>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">状态报告</label>
        <div class="layui-input-inline" style="width: 300px">
            <input type="text" maxlength="11" name="registered_delivery_flag" autocomplete="off"
                   value="<c:out value="${reMap['registered_delivery_flag'].value}"/>" class="layui-input">
            <c:out value="${reMap['registered_delivery_flag'].remark}"/>
        </div>
        <label class="layui-form-label">替换存在消息</label>
        <div class="layui-input-inline" style="width: 300px">
            <input type="text" maxlength="128" name="replace_if_present_flag" autocomplete="off"
                   value="<c:out value="${reMap['replace_if_present_flag'].value}"/>" class="layui-input">
            <c:out value="${reMap['replace_if_present_flag'].remark}"/>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">编码方案</label>
        <div class="layui-input-inline" style="width: 300px">
            <input type="text" maxlength="128" name="data_coding" value="<c:out value="${reMap['data_coding'].value}"/>" autocomplete="off"
                   class="layui-input">
            ${reMap['data_coding'].remark}
        </div>
        <label class="layui-form-label">预定义消息ID</label>
        <div class="layui-input-inline" style="width: 300px">
            <input type="text" maxlength="128" name="sm_default_msg_id" autocomplete="off" value="<c:out value="${reMap['sm_default_msg_id'].value}"/>"
                   class="layui-input">
            <c:out value="${reMap['sm_default_msg_id'].remark}"/>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">心跳间隔 <font color="red">&nbsp;&nbsp;*</font></label>
        <div class="layui-input-inline" style="width: 300px">
            <input type="text" maxlength="11" name="active_test_time" autocomplete="off" lay-verify="required"
                   value="<c:out value="${reMap['active_test_time'].value}"/>"   class="layui-input">心跳时间（秒）
            <c:out value="${reMap['active_test_time'].remark}"/>
        </div>
        
        <label class="layui-form-label">日限额(条)</label>
        <div class="layui-input-inline" style="width: 300px">
            <input type="text" maxlength="11" name="day_quota" autocomplete="off"
                   value="<c:out value="${reMap['day_quota'].value}"/>"   class="layui-input">
        </div>
    </div>
    
    <div class="layui-form-item">
        <label class="layui-form-label">submit response id radix</label>
        <div class="layui-input-inline"  style="width: 300px">
        <input type="text" maxlength="128" name="submit_response_id_radix" autocomplete="off"
                   value="<c:out value="${reMap['submit_response_id_radix'].value}"/>"   class="layui-input" >
            响应消息ID的进制
        </div>
        <label class="layui-form-label">report id radix</label>
        <div class="layui-input-inline"  style="width: 300px">
        <input type="text" maxlength="128" name="report_id_radix" autocomplete="off"
                   value="<c:out value="${reMap['report_id_radix'].value}"/>"   class="layui-input" >
        </div>
    </div>
    
    <div class="layui-form-item">
        <label class="layui-form-label">号码池策略</label>
        <div class="layui-input-inline"  style="width: 300px">
            <ht:herocodeselect sortCode="024" selected="${reMap['phone_no_pool_strategy_status'].value}" name="phone_no_pool_strategy_status" cssClass="combox"/>
            <c:out value="${reMap['phone_no_pool_strategy_status'].remark}"/>
        </div>
        <label class="layui-form-label">源码号长度</label>
        <div class="layui-input-inline"  style="width: 300px">
            <input type="text" maxlength="11" name="src_id_length" autocomplete="off"  value="<c:out value="${reMap['src_id_length'].value}"/>"    class="layui-input">
            <c:out value="${reMap['src_id_length'].remark}"/>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">滑动窗口</label>
        <div class="layui-input-inline"  style="width: 300px">
            <input type="text" name="window_size" value="<c:out value="${reMap['window_size'].value}"/>"    class="layui-input">
            滑动窗口大小
        </div>
        <label class="layui-form-label">是否支持emoji符号</label>
        <div class="layui-input-inline"  style="width: 300px">
            <ht:herocodeselect sortCode="006" selected="${reMap['is_support_emoji'].value}" name="is_support_emoji" cssClass="required combox"/>
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