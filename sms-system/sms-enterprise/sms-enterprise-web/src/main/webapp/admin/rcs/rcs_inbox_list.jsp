<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/common/common.jsp" %>
<%@ include file="/common/layui_head.html" %>
<%@ include file="/common/dynamic_data.jsp" %>
<script src="/js/common/rcs_common.js"></script>
<body>
<div class="layui-fluid">
        <div class="layui-col-md12">
            <div class="layui-card">
                <form id="layuiForm" class="layui-form layui-card-header layuiadmin-card-header-auto" onsubmit="return false;">
                    <div class="layui-inline">
                        &nbsp;&nbsp;上行内容&nbsp;
                        <div class="layui-input-inline">
                            <input name="content" id = "content" class="layui-input"/>
                        </div>
                    </div>&nbsp;&nbsp;
                    <div class="layui-inline">
                        &nbsp;&nbsp;手机号码&nbsp;
                        <div class="layui-input-inline">
                            <input name="phone_No" id = "phone_No" class="layui-input"/>
                        </div>
                    </div>&nbsp;&nbsp;
                    <div class="layui-inline">
                        &nbsp;&nbsp;创建时间&nbsp;
                        <div class="layui-inline">
                            <input name="minCreateDate" id="minCreateDate" class="layui-input layui-input-sm"/>
                        </div>
                        <div class="layui-inline">--</div>
                        <div class="layui-inline">
                            <input name="maxCreateDate" id="maxCreateDate"  class="layui-input layui-input-sm"/>
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

</body>
<script>
    var $;
    layui.extend({tableExt: '/js/layui-ext/tableExt'}).use(['tableExt','laydate'], function () {
        var table = layui.tableExt,laydate = layui.laydate,$ = layui.$;
        var today = new Date();
        var minCreateDate = '<c:out value="${minCreateDate}"/>';
        laydate.render({
            elem: '#minCreateDate',
            type:'datetime',
            value: minCreateDate?minCreateDate:new Date(today.getFullYear(),today.getMonth(),today.getDate())
        });
        laydate.render({
            elem: '#maxCreateDate',
            type:'datetime',
            value: new Date(today.getFullYear(),today.getMonth(),today.getDate(),23,59,59)
        });
        table.render({
            url: '/admin/sended_RcsInboxList',
            height: 'full-160',
            where:{
                'minCreateDate': $("#minCreateDate").val()==''? minCreateDate:$("#minCreateDate").val(),
                'maxCreateDate': $("#maxCreateDate").val()
            },
            cols: [[
                {field: 'phone_No', title: '手机号',width: 170},
                {field: 'content', title: '上行内容',width: 300},
                {title: '下行内容',templet:function (d) {
                        var button = "<button class=\"layui-btn layui-btn-xs\" title=\"详情\"" +
                            " onclick=\"contentDetail('"+encodeURIComponent(d.record_Content)+"')\" >查看详情</button>";
                        return d.record_Content?button:"--";
                    },width: 120},
                {field: 'create_Date', title: '创建时间',minWidth: 170}
            ]],
        });
    });

    function getFormData() {
        return $("#layuiForm").serialize();
    }
</script>