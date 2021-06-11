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
                        模板名称&nbsp;
                        <div class="layui-input-inline">
                            <input name="template_Name" class="layui-input"/>
                        </div>
                    </div>&nbsp;&nbsp;
                    <div class="layui-inline">
                        行业类型&nbsp;
                        <div class="layui-input-inline">
                            <ht:herocodeselect sortCode="trade" name="template_Type" />
                        </div>
                    </div>&nbsp;&nbsp;
                    <div class="layui-inline">
                        审核状态&nbsp;
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
            url: '/admin/enterprise_smsTemplateList',
            cols: [[
                {checkbox: true},
                {field: 'enterprise_No_ext', title: '企业名称',minWidth: 250, width:180,templet:function (d) {
                        return !d.enterprise_No_ext?'---':handleData(d.enterprise_No_ext.name);
                    }},
                {field: 'template_Name', title: '模板名称', width: 180},
                {field: 'template_Content', title: '模板内容', minWidth: 300,},
                {field: 'template_Type_name', title: '行业类型', width: 150},
                {field: 'approve_Status_name', title: '审核状态', width: 100,templet:function (d) {
                        return handleData(d.approve_Status_name);
                    }},
                {field: 'description', title: '描述', width: 150},
                {field: 'create_Date', title: '创建日期', minWidth: 200,}
            ]]
        });
    });
    function getFormData() {
        return $("#layuiForm").serialize();
    }
</script>