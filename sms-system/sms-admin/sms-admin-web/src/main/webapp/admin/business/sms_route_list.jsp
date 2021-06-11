<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/admin/common/common.jsp" %>
<%@ include file="/admin/common/layui_head.html" %>
<%@ include file="/admin/common/country_operator.jsp" %>
<body>
<div class="layui-fluid">
    <div class="layui-row layui-col-space15">
        <div class="layui-col-md12">
            <div class="layui-card">
                <form class="layui-form layui-card-header layuiadmin-card-header-auto" onsubmit="return false;">
                    <div class="layui-inline">
                        &nbsp;&nbsp;路由前缀&nbsp;
                        <div class="layui-input-inline">
                            <input name="prefix_Number" class="layui-input"/>
                        </div>
                    </div>&nbsp;&nbsp;
                    运营商
                    <div class="layui-inline">
                        <ht:herocodeselect sortCode="country" name="country_Number" id="country_Number" valueField="Value" />
                    </div>--<div class="layui-input-inline">
                    <ht:countryoperatorselect id="operator" name="route_Name_Code" />
                    </div>&nbsp;&nbsp;
                    <div class="layui-inline">
                        &nbsp;&nbsp;备注&nbsp;
                        <div class="layui-input-inline">
                            <input name="remark" class="layui-input"/>
                        </div>
                    </div>&nbsp;&nbsp;
                    <div class="layui-inline">
                        &nbsp;&nbsp;国家代码&nbsp;
                        <div class="layui-input-inline">
                            <input name="country_Code" class="layui-input">
                        </div>
                    </div>&nbsp;
                    <div class="layui-inline">
                        &nbsp;&nbsp;MCC码&nbsp;
                        <div class="layui-input-inline">
                            <input name="MCC" class="layui-input">
                        </div>
                    </div>&nbsp;
                    <div class="layui-inline">
                        <button class="layui-btn layui-btn-sm" type="submit" lay-submit="" lay-filter="reload">搜索
                        </button>
                    </div>
                    <br>
                    <div class="layui-inline">
                        <font color="red" size="4">&ensp;说明：添加或修改5分钟生效</font>
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
            url: '/admin/business_smsRouteList',
            cols: [[
                {checkbox: true},
                {field: 'prefix_Number', title: '路由前缀'},
                {field: 'route_Name_Code_name', title: '运营商', minWidth: 250},
                {field: 'country_Code', title: '国家代码'},
                {field: 'mcc', title: 'MCC码'},
                {field: 'mnc', title: 'MNC码'},
                {field: 'remark', title: '备注'},
                {field: 'create_Date', title: '创建日期'}
            ]]
        });
    });
</script>