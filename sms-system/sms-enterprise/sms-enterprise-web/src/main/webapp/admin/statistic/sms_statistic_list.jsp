<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/common/common.jsp" %>
<%@ include file="/common/layui_head.html" %>
<script src='<ht:heropageconfigurationtext code="enterprise_webpage_css" defaultValue="/layuiadmin"/>/lib/extend/echarts.js'></script>
<body>

<div class="layui-fluid">
    <div class="layui-row layui-col-space15">
        <div class="layui-col-md12">
            <div class="layui-card">
                <form class="layui-form layui-card-header layuiadmin-card-header-auto" id="layuiForm" onsubmit="return false">
                    <div class="layui-inline">
                        &nbsp;&nbsp;消息类型&nbsp;
                        <div class="layui-inline">
                            <ht:herocodeselect sortCode="message_type_code"  name="message_Type_Code"/>
                        </div>
                    </div>
                    <div class="layui-inline">
                        &nbsp;&nbsp;统计时间&nbsp;
                        <div class="layui-inline" style="width: 200px">
                            <input type="text" class="layui-input" id="min" placeholder="yyyy-MM-dd"
                                   name="min_Statistics_Date_Str">
                        </div>-
                        <div class="layui-inline" style="width: 200px">
                            <input type="text" class="layui-input" id="max" placeholder="yyyy-MM-dd"
                                   name="max_Statistics_Date_Str">
                        </div>
                    </div>
                    <div class="layui-inline">
                        <button class="layui-btn layui-btn-sm" type="submit" lay-submit="" lay-filter="reload">搜索
                        </button>
                    </div>
                </form>
                <div class="layui-form layui-border-box layui-table-view">
                    <div class="layui-card-body">
                        <table class="layui-hide" id="list_table" lay-filter="list_table"></table>
                        <script type="text/html" id="table-toolbar">
                            <div class="layui-btn-container">
                                <%@include file="/common/button_action_list.jsp" %>
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
    layui.extend({tableExt: '/js/layui-ext/tableExt'}).use(['tableExt', 'laydate'], function () {
        var table = layui.tableExt;
        $ = layui.$;
        var laydate = layui.laydate;
        var nowTime=new Date();
        laydate.render({
            elem: '#min'
            , format: "yyyy-MM-dd"
            , max:'nowTime' //默认最大值为当前日期
            ,value: getFormatDateBefore(34)
        });
        laydate.render({
            elem: '#max'
            , format: "yyyy-MM-dd"
            , max:'nowTime' //默认最大值为当前日期
            ,value: getFormatDateBefore(4)
        });
        table.render({
            url: '/admin/statistic_smsStatisticList',
            totalRow: true,
            where: {
                'min_Statistics_Date_Str': $("#min").val()==''?getFormatDateBefore(34):$("#min").val(),
                'max_Statistics_Date_Str': $("#max").val()==''?getFormatDateBefore(4):$("#max").val(),
                'time_Cycle' : 'day',
                'group_Str' : 'Time_Cycle'
            },
            cols: [[
                {type: 'numbers', title:'序号',width:100,totalRowText: '合计'},
                {field: 'submit_Total', title: '提交条数', width: 180, totalRow: true},
                {field: 'submit_Success_Total', title: '提交成功', width: 180, totalRow: true},
                {field: 'submit_Faild_Total', title: '提交失败', width: 180, totalRow: true},
                {field: 'send_Success_Total', title: '发送成功', width: 180, totalRow: true},
                {field: 'send_Faild_Total', title: '发送失败', width: 180, totalRow: true},
                { title: '发送未知', width: 180,templet:function (d) {
                        var send_Unknown_Total = d.submit_Success_Total-(d.send_Success_Total+d.send_Faild_Total);
                        return send_Unknown_Total<0?0:send_Unknown_Total;
                    }},
                {field: 'statistics_Date', title: '时间', minWidth: 200,templet : "<div>{{layui.util.toDateString(d.statistics_Date, 'yyyy年MM月dd日')}}</div>"}
            ]]
        });
    });

    function getFormData() {
        var str = $("#layuiForm").serialize();
        str += "&time_Cycle=day&group_Str=Time_Cycle";
        return str;
    }

</script>