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
                        产品名称&nbsp;
                        <div class="layui-input-inline">
                            <input name="name" class="layui-input"/>
                        </div>
                    </div>&nbsp;&nbsp;
                    <div class="layui-inline">
                        状态&nbsp;
                        <div class="layui-inline" style="width: 100px">
                            <ht:herocodeselect sortCode="state" name="status_Code" selected="Start"/>
                        </div>
                    </div>
                    <div class="layui-inline">
                        &nbsp;&nbsp;通道&nbsp;
                        <div class="layui-inline" style="width: 250px">
                            <ht:herocustomdataselect dataSourceType="allChannels" name="channel_No" />
                        </div>
                    </div>
                    <div class="layui-inline">
                        签名&nbsp;
                        <div class="layui-input-inline">
                            <input name="signature" class="layui-input"/>
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
            url: '/admin/product/productList',
            cols: [[
                {checkbox: true},
                {field: 'name', title: '名称', minWidth: 200},
                {field: 'no', title: '产品编号', minWidth: 270},
                {title: '行业类型', minWidth: 200,templet: function(d){
                        return handleData(d.trade_Type_Code_name);
                }},
                {title: '拦截策略', minWidth: 200,templet: function(d){
                        return d.intercept_Strategy_Id_ext?handleData(d.intercept_Strategy_Id_ext.name):"---";
                }},
                {title: '产品状态', minWidth: 200,templet: function(d){
                     return handleData(d.status_Code_name);
              	}},
                {field: 'create_Date', title: '创建日期', width: 230},
            ]]
        });
    });
</script>