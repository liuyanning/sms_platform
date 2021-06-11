<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/common/common.jsp" %>
<%@ include file="/common/layui_head.html" %>
<%@ include file="/common/country_operator.jsp" %>
<body>
<div class="layui-fluid">
    <div class="layui-row layui-col-space15">
        <div class="layui-col-md12">
            <div class="layui-card">
                <form class="layui-form layui-card-header layuiadmin-card-header-auto" onsubmit="return false;">
                    <div class="layui-inline">
                        &nbsp;&nbsp;国家&nbsp;
                        <div class="layui-input-inline">
                            <ht:herocodeselect sortCode="country" name="country_Number" id="country_Number" valueField="Value"/>
                        </div>
                    </div>&nbsp;&nbsp;
                    <div class="layui-inline">
                        &nbsp;&nbsp;运营商&nbsp;
                        <div class="layui-input-inline">
                            <ht:countryoperatorselect id="operator" name="operator" />
                        </div>
                    </div>&nbsp;&nbsp;
                    <div class="layui-inline">
                        &nbsp;&nbsp;区域&nbsp;
                        <div class="layui-inline" style="width: 200px">
                            <ht:herocodeselect sortCode="location" name="province_Code"/>
                        </div>
                    </div>
                    <div class="layui-inline">
                        &nbsp;&nbsp;消息类型&nbsp;
                        <div class="layui-inline">
                            <ht:herocodeselect sortCode="message_type_code"  name="message_Type_Code"/>
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
        table.render({
            url: '/admin/statistic_smsNowStatisticList',
            totalRow: true,
            cols: [[
                {type: 'numbers', title:'序号',width:100,totalRowText: '合计'},
                {field: 'submit_Date', title: '日期', width:120,templet:function (d) {
                        return !d.submit_Date?'---':d.submit_Date.substring(0,10);
                    }},
                {field: 'submit_Total', title: '提交条数', width: 120, totalRow: true},
                {field: 'submit_Success_Total', title: '提交成功', width: 120, totalRow: true},
                {field: 'submit_Faild_Total', title: '提交失败', width: 120, totalRow: true},
                {field: 'sort_Faild_Total', title: '分拣失败', width: 120, totalRow: true},
                {field: 'send_Success_Total', title: '发送成功', width: 120, totalRow: true},
                {field: 'send_Faild_Total', title: '发送失败', width: 120, totalRow: true},
                { title: '发送未知', width: 120,templet:function (d) {
                        var send_Unknown_Total = d.submit_Success_Total-(d.send_Success_Total+d.send_Faild_Total);
                        return send_Unknown_Total<0?0:send_Unknown_Total;
                    }},
                { title: '成功率',templet:function (d) {
                        if(!d.submit_Success_Total)return '---';
                        var point = d.send_Success_Total/d.submit_Success_Total;
                        return Number(point*100).toFixed(1)+"%";
                    },width: 100},
                { title: '失败率',templet:function (d) {
                        if(!d.submit_Success_Total)return '---';
                        var point = d.send_Faild_Total/d.submit_Success_Total;
                        return Number(point*100).toFixed(1)+"%";
                    },width: 100},
            ]]
        });
    });
</script>