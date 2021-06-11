<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/common/common.jsp" %>
<%@ include file="/common/layui_head.html" %>
<body>
<div class="layui-fluid">
    <div class="layui-row layui-col-space15">
        <div class="layui-col-md12">
            <div class="layui-card">
                <form class="layui-form layui-card-header layuiadmin-card-header-auto" onsubmit="return false;">
                    <div class="layui-inline">
                        &nbsp;&nbsp;模板名称&nbsp;
                        <div class="layui-input-inline">
                            <input name="template_Name" class="layui-input"/>
                        </div>
                    </div>&nbsp;&nbsp;
                    <div class="layui-inline">
                        &nbsp;模板编号
                        <div class="layui-input-inline">
                            <input name="template_Code" class="layui-input">
                        </div>
                    </div>
                    <div class="layui-inline">
                        &nbsp;&nbsp;行业类型&nbsp;
                        <div class="layui-input-inline">
                            <ht:herocodeselect sortCode="trade" name="trade_Type_Code" />
                        </div>
                    </div>
                    <div class="layui-inline">
                        &nbsp;&nbsp;通道模板编号&nbsp;
                        <div class="layui-input-inline">
                            <input name="channel_Template_Code" class="layui-input"/>
                        </div>
                    </div>&nbsp;&nbsp;
                    <div class="layui-inline">
                        &nbsp;&nbsp;审核状态&nbsp;
                        <div class="layui-input-inline">
                            <ht:herocodeselect sortCode="templateCheckStatus" name="approve_Status" />
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
    layui.extend({tableExt: '/js/layui-ext/tableExt'}).use(['tableExt'], function () {
        var table = layui.tableExt;
        table.render({
            url: '/admin/business_mmsTemplateList',
            cols: [[
                {checkbox: true},
                {field: 'template_Name', title: '模板名称', width: 150},
                {field: 'template_Code', title: '模板编号', width: 180},
                {field: 'channel_Template_Code', title: '通道模板编号', width: 180},
                {field: 'trade_Type_Code_name', title: '行业类型', width: 150},
                {field: 'approve_Status_name', title: '审核状态', width: 100},
                {field: 'description', title: '描述', width: 150},
                {field: 'remark', title: '备注', width: 150},
                {field: 'create_Date', title: '创建日期', width: 180,}
            ]]
        });
    });
</script>