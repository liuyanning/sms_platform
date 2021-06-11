<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/common.jsp"%>
<%@ include file="/common/layui_head.html"%>
<html>
<head>
    <meta http-equiv="Content-Type" content="multipart/form-data; charset=utf-8" />
    <script src="/js/jquery-3.4.1.min.js"></script>
    <script src="/js/jquery-form.js"></script>
</head>
<body>
<div class="layui-fluid">
    <div class="layui-row layui-col-space15">
        <div class="layui-col-md12">
            <div class="layui-card">
                <form id="layuiForm" class="layui-form layui-card-header layuiadmin-card-header-auto" onsubmit="return false;">
                    <div class="layui-inline">
                        充值时间&nbsp;
                        <div class="layui-input-inline">
                            <input name="min_Input_Date" id="minInputDate" class="layui-input layui-input-sm" size="15"/>
                        </div>
                        <div class="layui-inline">--</div>
                        <div class="layui-input-inline">
                            <input name="max_Input_Date" id="maxInputDate" class="layui-input layui-input-sm" size="15"/>
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
                                <button id="excelButton" class="layui-btn layuiadmin-btn-useradmin layui-btn-sm" lay-event='{"type":"exportTodo","url":"/admin/charge_exportChargeOrderList"}'>导出下载</button>
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
    var $ ;
    layui.extend({tableExt: '/js/layui-ext/tableExt'}).use(['tableExt','laydate'], function () {
        var table = layui.tableExt;
        $ = layui.$;
        table.render({
            url: '/admin/charge_chargeOrderList',
            cols: [[
                {field: 'enterprise_User_Id_ext', title: '充值用户', width: 130,templet: function (d) {
                        return !d.enterprise_User_Id_ext?'---':handleData(d.enterprise_User_Id_ext.real_Name);
                    }
                },
                {field: 'before_Money', title: '期初金额', width: 120},
                // ,{field: 'after_Money', title: '充值后金额【￥】', width:120}
                {field: 'money', title: '充值金额', width: 120},
                {field: 'money_Letter', title: '金额大写', minWidth: 150},
                {field: 'pay_Type_Code_name', title: '支付方式', width: 100,templet: function (d) {
                        if(d.pay_Type_Code_name =='NATIVE'){
                            return "微信";
                        }else if(d.pay_Type_Code_name =='ZFB_F2F_SWEEP_PAY'){
                            return "支付宝";
                        }else{
                            return handleData(d.pay_Type_Code_name);
                        }
                    }},
                {field: 'create_Date', title: '充值时间', width: 180},
                {field: 'open_Status_Code_name', title: '开通状态', width: 100,templet: function (d) {
                        return handleData(d.open_Status_Code_name);
                    }},
                {field: 'open_Date', title: '开通时间', minWidth: 180}
            ]]
        });
    });

    layui.use('laydate', function(){
        var laydate = layui.laydate;
        var today = new Date();
        laydate.render({     //创建时间选择框
            elem: '#minInputDate' //指定元素
            ,type:'datetime'
            ,trigger : 'click'
            ,value: new Date(today.getFullYear(),today.getMonth(),today.getDate())
        });
        laydate.render({     //创建时间选择框
            elem: '#maxInputDate' //指定元素
            ,type:'datetime'
            ,trigger : 'click'
            ,value: new Date(today.getFullYear(),today.getMonth(),today.getDate(),23,59,59)
        });
    });
    function getFormData() {
        return $("#layuiForm").serialize();
    }
</script>
</html>
