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
        <label class="layui-form-label">企业代码</label>
        <div class="layui-input-inline" style="width: 300px">
            <input type="text" maxlength="11" name="corp_id" autocomplete="off"
                   value="<c:out value="${reMap['corp_id'].value}"/>"   class="layui-input">
            <c:out value="${reMap['corp_id'].remark}"/>
        </div>
        <label class="layui-form-label">业务代码</label>
        <div class="layui-input-inline" style="width: 300px">
            <input type="text" maxlength="128" name="service_type" autocomplete="off"
                   value="<c:out value="${reMap['service_type'].value}"/>"   class="layui-input">
            <c:out value="${reMap['service_type'].remark}"/>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">付费号码</label>
        <div class="layui-input-inline" style="width: 300px">
            <input type="text" maxlength="128" name="charge_number"
                   value="<c:out value="${reMap['charge_number'].value}"/>" autocomplete="off" class="layui-input">
            <c:out value="${reMap['charge_number'].remark}"/>
        </div>
        <label class="layui-form-label">收费值</label>
        <div class="layui-input-inline" style="width: 300px">
            <input type="text" maxlength="11" name="fee_value" value="<c:out value="${reMap['fee_value'].value}"/>"
                   autocomplete="off" class="layui-input" placeholder="资费代码[6] 单位分">
            <c:out value="${reMap['fee_value'].remark}"/>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">代收费</label>
        <div class="layui-input-inline" style="width: 300px">
            <input type="text" maxlength="11" name="agent_flag" autocomplete="off"
                   value="<c:out value="${reMap['agent_flag'].value}"/>"
                   class="layui-input" placeholder="0：应收；1：实收[1]">
            <c:out value="${reMap['agent_flag'].remark}"/>
        </div>
        <label class="layui-form-label">计费类型</label>
        <div class="layui-input-inline" style="width: 300px">
            <input type="text" maxlength="11" name="fee_type" autocomplete="off"
                   value="<c:out value="${reMap['fee_type'].value}"/>" placeholder="0-99999,单位分"
                   class="layui-input">
            <c:out value="${reMap['fee_type'].remark}"/>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">单包群发</label>
        <div class="layui-input-inline" style="width: 300px">
            <input type="text" maxlength="11" name="lms_Msg_Fmt" value="<c:out value="${reMap['lms_Msg_Fmt'].value}"/>" autocomplete="off"
                   class="layui-input" placeholder="单包群发条数">
            <c:out value="${reMap['lms_Msg_Fmt'].remark}"/>
        </div>
        <label class="layui-form-label">优先级</label>
        <div class="layui-input-inline" style="width: 300px">
            <ht:herocodeselect sortCode="009" selected="${reMap['priority'].value}" name="priority"/>
            <c:out value="${reMap['priority'].remark}"/>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">登录模式</label>
        <div class="layui-input-inline" style="width: 300px">
            <input type="text" maxlength="11" name="login_type" autocomplete="off"
                   value="<c:out value="${reMap['login_type'].value}"/>"
                   class="layui-input" placeholder="1：SP向SMG连接 2：SMG向SP连接">
            <c:out value="${reMap['login_type'].remark}"/>
        </div>
        <label class="layui-form-label">本地端口 </label>
        <div class="layui-input-inline" style="width: 300px">
            <input type="text" maxlength="11" name="local_port" autocomplete="off"
                   value="<c:out value="${reMap['local_port'].value}"/>" class="layui-input">
            <c:out value="${reMap['local_port'].remark}"/>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">节点 </label>
        <div class="layui-input-inline" style="width: 300px" >
            <input type="text" maxlength="11" name="node_id" autocomplete="off"  value="<c:out value="${reMap['node_id'].value}"/>" class="layui-input">
            <c:out value="${reMap['node_id'].remark}"/>
        </div>
        <label class="layui-form-label">心跳间隔</label>
        <div class="layui-input-inline" style="width: 300px">
            <input type="text" maxlength="11" name="active_test_time" autocomplete="off"
                   value="<c:out value="${reMap['active_test_time'].value}"/>"   class="layui-input"
                   placeholder="心跳时间（秒）">
            <c:out value="${reMap['active_test_time'].remark}"/>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">状态报告标记</label>
        <div class="layui-input-inline" style="width: 300px">
            <input type="text" maxlength="11" name="report_flag" value="${reMap['report_flag'].value}" autocomplete="off"
                   class="layui-input">
            <c:out value="${reMap['report_flag'].remark}"/>
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
        <label class="layui-form-label">备注</label>
        <div class="layui-input-inline" style="width: 300px" >
            <input type="text" maxlength="2048" name="remark" autocomplete="off"  value="<c:out value="${reMap['remark'].value}"/>" class="layui-input">
            <c:out value="${reMap['remark'].remark}"/>
        </div>
    </div>

    <div class="layui-form-item layui-hide">
        <input type="button" lay-submit lay-filter="submit" id="layuiadmin-app-form-submit" value="确认">
    </div>
</form>
<%@ include file="/admin/common/layui_bottom.jsp" %>
</body>