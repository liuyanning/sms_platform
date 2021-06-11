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
                                描述&nbsp;
                                <td class="layui-inline" style="width: 180px">
                                    <input name="description" class="layui-input"/>
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
    var layer;
    layui.extend({tableExt: '/layuiadmin/extends/tableExt'}).use(['tableExt'], function () {
        var table = layui.tableExt;
        $ = layui.$;
        layer = layui.layer;
        table.render({
            url: '/admin/business_alarmLogList'
            , cols: [[
                {field: 'id', title: 'ID', width: 50, sort: true, fixed: 'left', type: 'checkbox'},
                {field: 'type_Code_name', title: '告警类型', minWidth: 180},
                {field: 'bind_Value', title: '告警对象', minWidth: 180, templet: function (d) {
                        return handleData(d.bind_Value);
                    }},
				{field: 'threshold_Value', title: '阈值', width: 80},
				{field: 'probe_Value', title: '探测值', width: 80},
                {title: '探测结果', width: 90, templet: function (d) {
                        return handleData(d.probe_Result_name);
                    }},
                {field: 'description', title: '描述', minWidth: 100},
                {field: 'create_Date', title: '探测时间',width: 180},
                {title: '操作',Width: 150,templet:function (d) {
                        var a = "<button class=\"layui-btn layui-btn-xs\" " + "onclick=\"clickBtn('"
                            + d.type_Code + "','"
                            + d.bind_Value + "','"
                            + d.begin_Date + "','"
                            + d.end_Date + "','"
                            + d.description + "','"
                            + "')\" >查看详情</button>";
                        return a;
                    }},
            ]]
        });
    });

    //查看详情
    function clickBtn(typeCode,bindValue,beginDate,endDate,description) {
        //提交成功率预警
        if(typeCode == 'product_submit_success_rate_alarm'
            || typeCode == 'channel_submit_success_rate_alarm'){
            return toShowDetail(typeCode,bindValue,beginDate,endDate,'submitSuccessAlarm')
        }
        //接收成功率预警
        if(typeCode == 'product_reception_success_rate_alarm'
            || typeCode == 'channel_reception_success_rate_alarm'){
            return toShowDetail(typeCode,bindValue,beginDate,endDate,'receptionSuccessAlarm')
        }
        //回执率预警
        if(typeCode == 'product_return_rate_alarm'
            || typeCode == 'channel_return_rate_alarm'){
            return toShowDetail(typeCode,bindValue,beginDate,endDate,'returnRateAlarm')
        }
        //没弹框的弹描述
        return layer.msg(description?description:"无详情！");
    }

    //获取预警类型
    function getBingValveType(typeCode) {
        var bingValveType = '';
        if(typeCode == 'product_submit_success_rate_alarm'
            || typeCode == 'product_reception_success_rate_alarm'
            || typeCode == 'product_return_rate_alarm' ){
            bingValveType = 'product';
        }
        if(typeCode == 'channel_submit_success_rate_alarm'
            || typeCode == 'channel_reception_success_rate_alarm'
            || typeCode == 'channel_return_rate_alarm' ){
            bingValveType = 'channel';
        }
        return bingValveType;
    }

    //页面跳转
    function toShowDetail(typeCode,bindValue,beginDate,endDate,alarmType) {
        var bingValveType = getBingValveType(typeCode);
        var url = "/admin/business_alarmDetail?bingValveType="+bingValveType+"&bindValue="+bindValue
            +"&beginDate="+beginDate+"&endDate="+endDate+"&alarmType="+alarmType;
        parent.layui.index.openTabsPage(url,"查看详情");
        return;
    }

</script>