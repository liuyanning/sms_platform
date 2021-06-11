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
                                &nbsp;&nbsp;号码前7位&nbsp;
                                <td class="layui-inline">
                                    <input name="mobile_Number" class="layui-input layui-input-sm"/>
                                </td>
                            </td>
                            <td class="layui-inline">&nbsp;&nbsp;&nbsp;&nbsp;</td>
                            <td class="layui-inline">
                                &nbsp;&nbsp;号码归属地&nbsp;
                                <td class="layui-inline">
                                    <input name="mobile_Area" class="layui-input layui-input-sm"/>
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
            url: '/admin/business_mobileAreaList'
            ,cols: [[
                {checkbox: true},
                {field: 'mobile_Number', title: '号码前7位', minWidth: 100},
                {field: 'mobile_Area', title: '号码归属地', minWidth: 150},
                {field: 'mobile_Type', title: '号码类型', minWidth: 80},
                {field: 'area_Code', title: '区号', minWidth: 100},
                {field: 'post_Code', title: '邮编', minWidth: 100},
            ]]
        });
    });
</script>