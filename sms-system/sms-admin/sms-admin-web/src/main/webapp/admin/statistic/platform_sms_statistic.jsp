<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/admin/common/common.jsp" %>
<%@ include file="/admin/common/layui_head.html" %>
<script src="/layuiadmin/lib/extend/echarts.js"></script>
<body>

<div class="layui-fluid">
    <div class="layui-row layui-col-space15">
        <div class="layui-col-md12">
            <div class="layui-card">
                <form class="layui-form layui-card-header layuiadmin-card-header-auto" id="layuiForm" onsubmit="return false;">
                    <div class="layui-inline">
                        &nbsp;&nbsp;平台名称&nbsp;
                        <div class="layui-inline" style="width: 200px">
                            <ht:heroplatformselect name="platform_No" />
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
    layui.use(['form'], function () {
        var form = layui.form;
        var laydate = layui.laydate;
    })
    layui.extend({tableExt: '/layuiadmin/extends/tableExt'}).use(['tableExt', 'laydate'], function () {
        var table = layui.tableExt;
        $ = layui.$;
        var laydate = layui.laydate;
        var nowTime=new Date();
        laydate.render({
            elem: '#min'
            , format: "yyyy-MM-dd"
            , max:'nowTime' //默认最大值为当前日期
            ,value: getFormatDateBefore(34)
            ,trigger: 'click'
        });
        laydate.render({
            elem: '#max'
            , format: "yyyy-MM-dd"
            , max:'nowTime' //默认最大值为当前日期
            ,value: getFormatDateBefore(4)
            ,trigger: 'click'
        });
        var index = layer.load(0, {
            offset: '200px'
        });
        table.render({
            url: '/admin/statistic_platformSmsList',
            height: 'full-120',
            where: {
                'min_Statistics_Date_Str': $("#min").val()==''?getFormatDateBefore(34):$("#min").val(),
                'max_Statistics_Date_Str': $("#max").val()==''?getFormatDateBefore(4):$("#max").val(),
            },
            totalRow: true,
            cols: [[
                {type: 'checkbox', totalRowText: '合计'},
                {field: 'platform_Name', title: '平台名称', width: 120},
                {field: 'submit_Total', title: '提交条数', width: 130, totalRow: true},
                {field: 'sort_Faild_Total', title: '分拣失败', width: 120, totalRow: true},
                {field: 'submit_Success_Total', title: '提交成功', width: 130, totalRow: true},
                {field: 'submit_Faild_Total', title: '提交失败', width: 120, totalRow: true},
                {field: 'send_Success_Total', title: '发送成功', width: 130, totalRow: true},
                {field: 'send_Faild_Total', title: '发送失败', width: 120, totalRow: true},
                {field: 'send_Unknown_Total', title: '发送未知', width: 120, totalRow: true},
                {field: 'statistics_Date', title: '统计时间', minWidth: 120,templet :function (d) {
                      return layui.util.toDateString(d.statistics_Date, 'yyyy年MM月dd日');
                    } }
            ]]
            ,done:function (re) {
                layer.close(index);
            }
        });
    });

    layui.use(['form', 'laydate'], function () {
        var $ = layui.$
            , form = layui.form;
        form.on('checkbox(group)', function (data) {
            var groupStr = $("input[lay-filter='group']:checked").map(function () {
                return $(this).val();
            }).get().join(',');
            var Delimiter = groupStr?',':'';
            $("#group_Str").val('Time_Cycle'+Delimiter+groupStr);
            var groupStrNoChecked = $("input[lay-filter='group']:not(:checked)").map(function () {
                var str = $(this).val();
                return str.replace(str[0],str[0].toLowerCase());
            }).get().join(',');
            $("#groupStrNoChecked").val(groupStrNoChecked);
        });
    });
    function getFormData() {
        var str = $("#layuiForm").serialize();
        return str;
    }
</script>