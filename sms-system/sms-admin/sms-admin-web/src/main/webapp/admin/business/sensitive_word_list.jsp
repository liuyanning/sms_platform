<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/admin/common/common.jsp" %>
<%@ include file="/admin/common/layui_head.html" %>
<body>
<div class="layui-fluid">
    <div class="layui-row layui-col-space15">
        <div class="layui-col-md12">
            <div class="layui-card">
                <form class="layui-form layui-card-header layuiadmin-card-header-auto" id="layuiForm" onsubmit="return false;">
                    <div class="layui-inline">
                        &nbsp;&nbsp;敏感字&nbsp;
                        <div class="layui-input-inline">
                            <input name="word" class="layui-input"/>
                        </div>
                    </div>
                    <div class="layui-inline">
                        &nbsp;&nbsp;行业类型&nbsp;
                        <div class="layui-input-inline">
                            <ht:herocodeselect sortCode="trade" name="trade_Type_Code"/>
                        </div>
                    </div>
                    <div class="layui-inline">
                        &nbsp;&nbsp;敏感字类型&nbsp;
                        <div class="layui-input-inline">
                            <ht:herocodeselect sortCode="sensitive_word_pool" name="pool_Code"/>
                        </div>
                    </div>
                    <div class="layui-inline">
                        &nbsp;&nbsp;创建人&nbsp;
                        <div class="layui-input-inline">
                            <input name="create_User" class="layui-input"/>
                        </div>
                    </div>
                    <div class="layui-inline">
                        &nbsp;&nbsp;创建时间&nbsp;
                        <div class="layui-input-inline">
                            <input name="minCreateDate" id="minCreateDate" autocomplete="off" class="layui-input"/>
                        </div>
                        <div class="layui-inline">--</div>
                        <div class="layui-input-inline">
                            <input name="maxCreateDate" id="maxCreateDate" autocomplete="off" class="layui-input"/>
                        </div>
                    </div>&nbsp;&nbsp;
                    <div class="layui-inline">
                        <button class="layui-btn layui-btn-sm" type="submit" lay-submit="" lay-filter="reload">搜索
                        </button>
                    </div>
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
        layui.use('laydate', function(){
            var laydate = layui.laydate;
            laydate.render({     //创建时间选择框
                elem: '#minCreateDate' //指定元素
                ,type:'datetime'
                ,trigger : 'click'
            });
            laydate.render({     //创建时间选择框
                elem: '#maxCreateDate' //指定元素
                ,type:'datetime'
                ,trigger : 'click'
            });
        });
        table.render({
            url: '/admin/business_sensitiveWordList'
            ,cols: [[
                {checkbox: true},
                {field: 'word', title: '敏感字', minWidth: 100, Width: 100},
                {title: '行业类型', minWidth: 150,  Width: 150
                    ,templet:function(d){
                        return handleData(d.trade_Type_Code_name);
                    }},
                {title: '敏感字类型', minWidth: 150, width: 150,templet:function (d) {
                        return handleData(d.pool_Code_name);
                    }
                },
                {field: 'create_User', title: '创建', minWidth: 150},
                {field: 'remark', title: '备注', minWidth: 80},
                {field: 'create_Date', title: '创建日期', minWidth: 100}
            ]]
        });
    });
</script>