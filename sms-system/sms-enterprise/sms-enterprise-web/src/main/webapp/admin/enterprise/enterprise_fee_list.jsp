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
                <div class="layui-form layui-border-box layui-table-view">
                    <div class="layui-card-body">

                        <form id="layuiForm"  class="layui-form layui-card-header layuiadmin-card-header-auto" id="layuiForm" onsubmit="return false">
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
                                &nbsp;&nbsp;行业&nbsp;
                                <div class="layui-input-inline">
                                    <ht:herocodeselect sortCode="trade" name="trade_Type_Code"/>
                                </div>
                            </div>&nbsp;&nbsp;
                            <div class="layui-inline">
                                &nbsp;&nbsp;消息类型&nbsp;
                                <div class="layui-input-inline">
                                    <ht:herocodeselect sortCode="message_type_code" name="message_Type_Code"/>
                                </div>
                            </div>&nbsp;&nbsp;
                            <div class="layui-inline">
                                <button class="layui-btn layui-btn-sm" type="submit" lay-submit="" lay-filter="reload">搜索
                                </button>
                            </div>
                        </form>
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
        table.render({
            url: '/admin/enterprise_enterpriseUserFeeList',
            cols: [[
                {checkbox: true},
                {title: '国家', minWidth: 120, templet: function(d){
                        return handleData(d.country_Number_name);
                    }},
                {title: '运营商', minWidth: 120, templet: function(d){
                        return handleData(d.operator);
                    }},
                {field: 'unit_Price', title: '单价(元)', minWidth: 200},
                {title: '行业', minWidth: 120, templet: function(d){
                        return handleData(d.trade_Type_Code_name);
                    }},
                {title: '消息类型', minWidth: 120, templet: function(d){
                        return handleData(d.message_Type_Code_name);
                    }},
                {field: 'create_Date', title: '创建日期', minWidth: 170}
            ]]
        });
    });

    function getFormData() {
        return $("#layuiForm").serialize();
    }
</script>