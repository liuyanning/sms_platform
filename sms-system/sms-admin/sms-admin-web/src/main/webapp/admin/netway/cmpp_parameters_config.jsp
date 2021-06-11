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
    <input type="hidden" name="id" value="<c:out value="${channel.id}"/>">
    <input type="hidden" name="no" value="<c:out value="${channel.no}"/>">
    <div class="layui-form-item">
        <label class="layui-form-label">企业代码 </label>
        <div class="layui-input-inline"  style="width: 300px">
            <input type="text" maxlength="11" name="enterprise_code" autocomplete="off"  value="<c:out value="${reMap['enterprise_code'].value}"/>"    class="layui-input">
            <c:out value="${reMap['enterprise_code'].remark}"/>
        </div>
        <label class="layui-form-label">业务代码 </label>
        <div class="layui-input-inline"  style="width: 300px">
            <input type="text" maxlength="128" name="service_id" autocomplete="off"  value="<c:out value="${reMap['service_id'].value}"/>"    class="layui-input">
            <c:out value="${reMap['service_id'].remark}"/>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">计费用户类型</label>
        <div class="layui-input-inline"  style="width: 300px">
            <input type="text" maxlength="11" name="fee_user_type" value="<c:out value="${reMap['fee_user_type'].value}"/>" autocomplete="off" class="layui-input">
            <c:out value="${reMap['fee_user_type'].remark}"/>
        </div>
        <label class="layui-form-label">资费类别</label>
        <div class="layui-input-inline"  style="width: 300px">
            <input type="text" maxlength="11" name="fee_type" value="<c:out value="${reMap['fee_type'].value}"/>" autocomplete="off" class="layui-input">
            <c:out value="${reMap['fee_type'].remark}"/>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">被计费用户号码</label>
        <div class="layui-input-inline"  style="width: 300px">
            <input type="text" maxlength="128"name="fee_terminal_Id" value="<c:out value="${reMap['fee_terminal_Id'].value}"/>" autocomplete="off" class="layui-input">
            <c:out value="${reMap['fee_terminal_Id'].remark}"/>
        </div>
        <label class="layui-form-label">资费代码</label>
        <div class="layui-input-inline"  style="width: 300px">
            <input type="text" maxlength="128"name="fee_code" value="<c:out value="${reMap['fee_code'].value}"/>" autocomplete="off" class="layui-input">
            <c:out value="${reMap['fee_code'].remark}"/>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">信息内容来源</label>
        <div class="layui-input-inline"  style="width: 300px">
            <input type="text" maxlength="128"name="msg_src" value="<c:out value="${reMap['msg_src'].value}"/>" autocomplete="off" class="layui-input">
            <c:out value="${reMap['msg_src'].remark}"/>
        </div>
        <label class="layui-form-label">信息级别</label>
        <div class="layui-input-inline"  style="width: 300px">
            <input type="text" maxlength="11"name="msg_level" autocomplete="off" value="<c:out value="${reMap['msg_level'].value}"/>" class="layui-input">
            <c:out value="${reMap['msg_level'].remark}"/>
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label">状态报告</label>
        <div class="layui-input-inline"  style="width: 300px">
            <input type="text" maxlength="11" name="registered_delivery" autocomplete="off"
                   value="<c:out value="${reMap['registered_delivery'].value}"/>" class="layui-input">
            <c:out value="${reMap['registered_delivery'].remark}"/>
        </div>
        <label class="layui-form-label">单包群发</label>
        <div class="layui-input-inline"  style="width: 300px">
            <input type="text" maxlength="11" name="batch_total" autocomplete="off" value="<c:out value="${reMap['batch_total'].value}"/>"
                   class="layui-input">
            <c:out value="${reMap['batch_total'].remark}"/>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">心跳间隔 <font color="red">&nbsp;&nbsp;*</font></label>
        <div class="layui-input-inline"  style="width: 300px">
            <input type="text" maxlength="11" name="active_test_time" autocomplete="off" lay-verify="required"  value="<c:out value="${reMap['active_test_time'].value}"/>"    class="layui-input">心跳时间（秒）
            <c:out value="${reMap['active_test_time'].remark}"/>
        </div>
        <label class="layui-form-label">日限额(条)</label>
        <div class="layui-input-inline"  style="width: 300px">
            <input type="text" maxlength="128" name="day_quota" autocomplete="off"  value="<c:out value="${reMap['day_quota'].value}"/>"    class="layui-input">
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
    </div>

    <div class="layui-form-item layui-hide">
        <input type="button" lay-submit lay-filter="submit" id="layuiadmin-app-form-submit" value="确认">
    </div>
</form>
<%@ include file="/admin/common/layui_bottom.jsp" %>
</body>