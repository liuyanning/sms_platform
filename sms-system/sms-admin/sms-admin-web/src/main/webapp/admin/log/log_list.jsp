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
                        &nbsp;&nbsp;真实姓名&nbsp;
                        <div class="layui-inline">
                            <input name="real_Name" class="layui-input layui-input-sm"/>
                        </div>
                    </div>

                    <div class="layui-inline">
                        &nbsp;&nbsp;登录名&nbsp;
                        <div class="layui-inline">
                            <input name="user_Name" class="layui-input layui-input-sm"/>
                        </div>
                    </div>
                    <div class="layui-inline">
                        &nbsp;&nbsp;操作模块
                        <div class="layui-inline">
                            <input name=" module_Name" class="layui-input layui-input-sm"/>
                        </div>
                    </div>
                    <div class="layui-inline">
                        &nbsp;详细描述
                        <div class="layui-inline">
                            <input name="specific_Desc" class="layui-input layui-input-sm"/>
                        </div>
                    </div>
                    <div class="layui-inline">
                        &nbsp;&nbsp;操作时间&nbsp;
                        <div class="layui-inline" style="width: 200px">
                            <input type="text" class="layui-input" id="min" placeholder="yyyy-MM-dd HH:mm:ss"
                                   name="minStatistcDate">
                        </div>
                    </div>
                    -
                    <div class="layui-inline">
                        <div class="layui-inline" style="width: 200px">
                            <input type="text" class="layui-input" id="max" placeholder="yyyy-MM-dd HH:mm:ss"
                                   name="maxStatistcDate">
                        </div>
                    </div>
                    &nbsp;&nbsp;
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
    layui.extend({tableExt: '/layuiadmin/extends/tableExt'}).use(['tableExt', 'laydate'], function () {
        var table = layui.tableExt
            , laydate = layui.laydate
            , $ = layui.$;
        table.render({
            url: '/admin/log_list',
            cols: [[
                {checkbox: true},
                {field: 'real_Name', title: '真实姓名'},
                {field: 'user_Name', title: '登录名'},
                {field: 'module_Name', title: '操作模块'},
                {field: 'operate_Desc', title: '操作描述'},
                {field: 'specific_Desc', title: '详细描述'},
                {field: 'ip_Address', title: '操作ip'},
                {field: 'create_Date', title: '创建时间'}
            ]]
        });

        //日期时间选择器
        laydate.render({
            elem: '#min'
            , type: 'datetime'
            , value: getNowFormatDate() + " 00:00:00"
        });
        laydate.render({
            elem: '#max'
            , type: 'datetime'
            , value: getNowFormatDate() + " 23:59:59"
        });
    });
</script>