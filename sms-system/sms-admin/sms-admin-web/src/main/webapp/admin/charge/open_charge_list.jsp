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
                        &nbsp;&nbsp;企业名称
                        <div class="layui-inline" style="width: 200px">
                            <ht:heroenterpriseselect  name="enterprise_No" />
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
    layui.extend({tableExt: '/layuiadmin/extends/tableExt'}).use(['tableExt'], function () {
        var table = layui.tableExt;
        table.render({
            url: '/admin/charge_openChargeOrderList',
            cols: [[
                {checkbox: true},
                {title: '企业名称/企业编号', minWidth:180,templet:function (d) {
                        return handleData(d.enterprise_Name)+"<br>"+handleData(d.enterprise_No);
                    }},
                {title: '企业用户', width:180,templet:function (d) {
                        return !d.enterprise_User_Id_ext?'---':handleData(d.enterprise_User_Id_ext.real_Name)
                            +"<br>"+handleData(d.enterprise_User_Id_ext.user_Name);
                    }},
                {
                    title: '充值   信息', minWidth: 400, templet: function (d) {
                        return "金额(元)：" + handleData(d.money)
                            + "/金额大写：" + handleData(d.money_Letter)
                            + "<br>支付方式：" + handleData(d.pay_Type_Code_name)
                            + "/充值人：" + handleData(d.input_User)
                    }
                },
                {field: 'input_Remark', title: '充值备注', width: 100},
                {field: 'input_Date', title: '充值时间', width: 180},
            ]]
        });
    });
</script>