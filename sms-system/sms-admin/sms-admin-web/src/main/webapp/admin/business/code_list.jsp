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
                        &nbsp;&nbsp;代码名称&nbsp;
                        <div class="layui-input-inline">
                            <input name="name" class="layui-input"/>
                        </div>
                    </div>&nbsp;&nbsp;
                    <div class="layui-inline">
                        &nbsp;&nbsp;代码&nbsp;
                        <div class="layui-input-inline">
                            <input name="code" class="layui-input"/>
                        </div>
                    </div>&nbsp;&nbsp;
                    <div class="layui-inline">
                        &nbsp;&nbsp;分类&nbsp;
                        <div class="layui-input-inline">
                            <ht:herocodesortselect name="sort_Code"/>
                        </div>
                    </div>&nbsp;&nbsp;
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
            url: '/admin/business_codeList',
            cols: [[
                {checkbox: true},
                {title: '分类', minWidth: 120, templet: function(d){
                        return d.sort_Code_ext?handleData(d.sort_Code_ext.name):'---';
                    }},
                {field: 'code', title: '代码', minWidth: 200},
                {field: 'name', title: '名称', minWidth: 180},
                {field: 'value', title: '代码值', minWidth: 100},
                {title: '上级代码', minWidth: 120, templet: function(d){
                        return d.up_Code_ext?handleData(d.up_Code_ext.name):'---';
                    }},
                {field: 'create_Date', title: '创建日期', minWidth: 170},
                {field: 'description', title: '描述', minWidth: 100},
                {field: 'remark', title: '备注', minWidth: 150}
            ]]
        });
    });
</script>