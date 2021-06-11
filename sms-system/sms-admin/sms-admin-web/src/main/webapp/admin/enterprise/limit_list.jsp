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
                    <table>
                        <tr>
                            <td class="layui-inline">
                                权限名称：
                                <td class="layui-inline">
                                    <input name="name" class="layui-input layui-input-sm"/>
                                </td>
                            </td>
                            <td class="layui-inline">&nbsp;&nbsp;&nbsp;&nbsp;</td>
                            <td class="layui-inline">
                                上级代码：
                                <td class="layui-inline">
                                    <input name="up_Code" class="layui-input layui-input-sm" size="5"/>
                                </td>
                            </td>
                            <td class="layui-inline">&nbsp;&nbsp;&nbsp;&nbsp;</td>
                            <td class="layui-inline">
                                <button class="layui-btn layui-btn-sm" type="submit" lay-submit="" lay-filter="reload">搜索
                                </button>
                            </td>
                        </tr>
                    </table>
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
            url: '/admin/enterprise_limitList'
            ,cols: [[
                {checkbox: true},
                {field: 'name', title: '权限', minWidth: 120},
                {field: 'code', title: '代码', minWidth: 120},
                {field: 'up_Code', title: '上级代码', minWidth: 100},
                {field: 'url', title: '权限URL', minWidth: 250},
                {field: 'button_Action', title: '按钮动作', minWidth: 100},
                {field: 'type_Code_name', title: '权限类型', minWidth: 100},
                {field: 'order_Id', title: '排序Id', width: 80},
                {field: 'icon', title: '显示图标', minWidth: 80},
                {field: 'create_User', title: '创建者', width: 100},
                {field: 'create_Date', title: '创建日期', width: 160},
                {field: 'remark', title: '备注', minWidth: 80}
            ]]
        });
    });
</script>