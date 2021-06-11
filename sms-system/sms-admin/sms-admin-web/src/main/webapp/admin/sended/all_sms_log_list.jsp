<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/admin/common/common.jsp" %>
<%@ include file="/admin/common/layui_head.html" %>
<body>
<div class="layui-fluid">
    <div class="layui-row layui-col-space15">
        <div class="layui-col-md12">
            <div class="layui-card">
                <form class="layui-form layui-card-header layuiadmin-card-header-auto" onsubmit="return false;">
                    <div class="layui-inline">
                        企业ID&nbsp;&nbsp;&nbsp;
                        <div class="layui-input-inline">
                            <input name="enterprise_Id" class="layui-input"/>
                        </div>
                    </div>
                    <div class="layui-inline">
                        通道ID&nbsp;&nbsp;&nbsp;
                        <div class="layui-input-inline">
                            <input name="channel_Id" class="layui-input"/>
                        </div>
                    </div>&nbsp;&nbsp;<div class="layui-inline">
                    手机号码&nbsp;&nbsp;&nbsp;
                        <div class="layui-input-inline">
                            <input name="mobile" class="layui-input"/>
                        </div>
                    </div>&nbsp;&nbsp;
                    <div class="layui-inline">
                        短信内容&nbsp;&nbsp;&nbsp;
                        <div class="layui-input-inline">
                            <input name="content" class="layui-input"/>
                        </div>
                    </div>&nbsp;&nbsp;
                    <div class="layui-inline">
                        提交时间&nbsp;&nbsp;&nbsp;
                        <div class="layui-inline">
                            <input name="minSubmitDate" id="minSubmitDate" class="layui-input layui-input-sm" size="5"/>
                        </div>
                        <div class="layui-inline">--</div>
                        <div class="layui-inline">
                            <input name="maxSubmitDate" id="maxSubmitDate"  class="layui-input layui-input-sm" size="5"/>
                        </div>
                    </div>&nbsp;&nbsp;
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
    layui.extend({tableExt: '/layuiadmin/extends/tableExt'}).use(['tableExt'], function () {
        var table = layui.tableExt;
        table.render({
            url: '/admin/sended_sendedHistorySMSList',
            cols: [[
                {checkbox: true},
                {field: 'recive_Result', title: '接收状态'},
                {field: 'recive_Desc', title: '接收备注'},
                {field: 'recive_Date', title: '接收时间'},
                {field: 'submit_Result', title: '提交状态'},
                {field: 'submit_Desc', title: '提交备注'},
                {field: 'submit_Date', title: '提交时间'},
                {field: 'mobile', title: '发送号码'},
                {field: 'mobile_Type_name', title: '号码类型'},
                {field: 'channel_Id', title: '发送通道'},
                {field: 'enterprise_Id', title: '企业名称'},
                {field: 'content', title: '短信内容'},
                {field: 'content_Word_Len', title: '短信长度'},
                {field: 'sms_Total', title: '短信条数'},
                {field: 'create_Date', title: '创建时间'},
            ]]
        });
    });


    layui.use('laydate', function(){
        var laydate = layui.laydate;
        laydate.render({     //创建时间选择框
            elem: '#minSubmitDate' //指定元素
            ,type:'datetime'
            ,trigger : 'click'
        });
        laydate.render({     //创建时间选择框
            elem: '#maxSubmitDate' //指定元素
            ,type:'datetime'
            ,trigger : 'click'
        });
    });
</script>