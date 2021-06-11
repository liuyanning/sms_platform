<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/admin/common/common.jsp" %>
<%@ include file="/admin/common/layui_head.html" %>
<body>
<div class="layui-fluid">
    <div class="layui-row layui-col-space15">
        <div class="layui-col-md12">
            <div class="layui-card">
                <form id="layuiForm" class="layui-form layui-card-header layuiadmin-card-header-auto" onsubmit="return false;">
                    <table>
                        <tr>
                            <td class="layui-inline">
                                告警类型&nbsp;
                            <td class="layui-inline" style="width: 180px">
                                <ht:herocodeselect sortCode="alarm_type" name="type_Code"/>
                            </td>
                            </td>
                            <td class="layui-inline">&nbsp;&nbsp;&nbsp;&nbsp;</td>
                            <td class="layui-inline">
                                告警对象&nbsp;
                            <td class="layui-inline" style="width: 180px">
                                <input name="bind_Value" id="bind_Value" class="layui-input"/>
                            </td>
                            </td>
                            <td class="layui-inline">&nbsp;&nbsp;&nbsp;&nbsp;</td>
                            <td class="layui-inline">
                                手机号码&nbsp;
                            <td class="layui-inline" style="width: 200px">
                                <input name="phone_Nos" id="phone_Nos" class="layui-input"/>
                            </td>
                            </td>
                            <td class="layui-inline">&nbsp;&nbsp;&nbsp;&nbsp;</td>
                            <td class="layui-inline">
                                &nbsp;邮箱&nbsp;
                            <td class="layui-inline" style="width: 200px">
                                <input name="Emails" id="Emails" class="layui-input"/>
                            </td>
                            </td>
                            <td class="layui-inline">&nbsp;&nbsp;&nbsp;&nbsp;</td>
                            <td class="layui-inline">
                                状态
                            <td class="layui-inline" style="width: 100px">
                                <ht:herocodeselect sortCode="alarm_status" name="status"/>
                            </td>
                            </td>
                            <td class="layui-inline">&nbsp;&nbsp;&nbsp;&nbsp;</td>
                            <td class="layui-inline">&nbsp;&nbsp;&nbsp;&nbsp;</td>
                            <td class="layui-inline">
                                <button class="layui-btn layui-btn-sm" type="submit" lay-submit="" lay-filter="reload">
                                    搜索
                                </button>
                            </td>
                        </tr>
                    </table>
                </form>
                <div class="layui-form layui-border-box layui-table-view">
                    <div class="layui-card-body">
                        <table class="layui-hide" id="list_table" lay-filter="list_table"></table>
                        <script type="text/html" id="table-toolbar">
                            <div class="layui-btn-container">
                                <%@include file="/admin/common/button_action_list.jsp" %>
                            </div>
                        </script>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
<script>
    var $;
    layui.extend({tableExt: '/layuiadmin/extends/tableExt'}).use(['tableExt'], function () {
        var table = layui.tableExt;
        $ = layui.$;
        table.render({
            url: '/admin/business_alarmList'
            , cols: [[
                {field: 'id', title: 'ID', width: 50, sort: true, fixed: 'left', type: 'checkbox'},
                {
                    field: 'bind_Value', title: '告警对象', minWidth: 180, templet: function (d) {
                        return handleData(d.bind_Value);
                    }},
                {field: 'phone_Nos', title: '告警手机号', minWidth: 150},
                {field: 'emails', title: '告警邮箱', minWidth: 200},
                {field: 'wechat', title: '告警微信', minWidth: 120, templet: function (d) {
                        return handleData(d.wechat_name);
                    }},
                {field: 'ding_Talk', title: '告警钉钉', minWidth: 120, templet: function (d) {
                        return handleData(d.ding_Talk_name);
                    }},
                {
                    field: 'type_code', title: '告警类型', minWidth: 180, templet: function (d) {
                        return handleData(d.type_Code_name);
                    }},
				{field: 'threshold_Value', title: '阈值', width: 80},
                {field: 'status_name', title: '告警状态', minWidth: 100},
                {
                    field: 'type_code', title: '探测结果', minWidth: 100, templet: function (d) {
                        return handleData(d.probe_Result_name);
                    }},
                {field: 'max_Alarm_Total', title: '告警次数', minWidth: 100},
                {field: 'alarm_Total', title: '已告警次数', minWidth: 100},
                {field: 'probe_Time_name', title: '告警间隔', width: 120},
                {field: 'last_Probe_Date', title: '探测时间', minWidth: 160},
                {field: 'description', title: '描述', width: 100},
                {field: 'remark', title: '备注', width: 100},
                {field: 'create_Date', title: '创建时间',width: 180},
            ]]
        });
    });

</script>