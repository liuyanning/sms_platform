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
                    <input value="${enterpriseUserId}" name="enterprise_User_Id" id="enterpriseUserId" hidden />

                    <input value="${limitCode}" name="limitCode" id="limitCode" hidden />
                    <div class="layui-inline">
                        &nbsp;&nbsp;运营商&nbsp;
                        <div class="layui-input-inline">
                            <ht:herocodeselect sortCode="001" name="operator"/>
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
            url: '/admin/enterprise_enterpriseUserFeeList',
            where: {
                'fee_Type_Code': '02',
                "enterprise_User_Id":'${enterpriseUserId}'
            },
            cols: [[
                {checkbox: true},
                {title: '运营商', minWidth: 120, templet: function(d){
                        return handleData(d.operator_name);
                    }},
                {field: 'unit_Price', title: '单价(元)', minWidth: 150},
                {field: 'tax_Point', title: '税点', minWidth: 150},
                {title: '行业', minWidth: 120, templet: function(d){
                        return handleData(d.trade_Type_Code_name);
                    }},
                {title: '消息类型', minWidth: 120, templet: function(d){
                        return handleData(d.message_Type_Code_name);
                    }},
                {title: '资费类型', minWidth: 120, templet: function(d){
                        return handleData(d.fee_Type_Code_name);
                    }},
                {field: 'create_Date', title: '创建日期', minWidth: 170}
            ]]
        });
    });
</script>