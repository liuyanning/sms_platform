<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/admin/common/common.jsp" %>
<%@ include file="/admin/common/layui_head.html" %>
<%@ include file="/admin/common/dynamic_data.jsp" %>
<body>
<div class="layui-fluid">
        <div class="layui-col-md12">
            <div class="layui-card">
                <form id="layuiForm" class="layui-form layui-card-header layuiadmin-card-header-auto" onsubmit="return false;">
                   <div class="layui-inline">
                           &nbsp;&nbsp;通道&nbsp;
                        <div class="layui-inline" style="width: 200px">
                            <ht:herocustomdataselect dataSourceType="allChannels" name="channel_No" />
                        </div>
                    </div>
                    <div class="layui-inline">
                        &nbsp;&nbsp;提交时间&nbsp;
                        <div class="layui-inline">
                            <input name="minSubmitDate" id="minSubmitDate" class="layui-input layui-input-sm"/>
                        </div>
                        <div class="layui-inline">--</div>
                        <div class="layui-inline">
                            <input name="maxSubmitDate" id="maxSubmitDate"  class="layui-input layui-input-sm"/>
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

</body>
<script>
    var $;
    var minSubmitDate = '<c:out value="${minSubmitDate}"/>';
    var maxSubmitDate = '<c:out value="${maxSubmitDate}"/>';
    layui.extend({tableExt: '/layuiadmin/extends/tableExt'}).use(['tableExt','laydate'], function () {
        var table = layui.tableExt,$ = layui.$;
        table.render({
            url: '/admin/statistic_sended_speed',
            where:{
                'minSubmitDate':$("#minSubmitDate").val()==''? minSubmitDate:$("#minSubmitDate").val(),
                'maxSubmitDate':$("#maxSubmitDate").val()==''? maxSubmitDate:$("#maxSubmitDate").val()
            },
            cols: [[
                {field: 'channel_No_ext',title: '发送通道',minWidth: 180,templet:function (d) {
                        return !d.channel_No_ext?'---':handleData(d.channel_No_ext.name)+"<br>"+d.channel_No;
                    }},
                {field: 'count_Total', title:'发送总条数', minWidth: 150},
                {field: 'sendSpeed', title: '发送速率(条/秒)',minWidth: 120}
            ]]
        });
    });
    layui.use('laydate', function(){
        var laydate = layui.laydate;
        var today = new Date();
        laydate.render({     //创建时间选择框
            elem: '#minSubmitDate' //指定元素
            ,type:'datetime'
            ,trigger : 'click'
            ,value: minSubmitDate?minSubmitDate:new Date(today.getFullYear(),today.getMonth(),today.getDate())
        });
        laydate.render({     //创建时间选择框
            elem: '#maxSubmitDate' //指定元素
            ,type:'datetime'
            ,trigger : 'click'
            ,value: maxSubmitDate?maxSubmitDate:new Date(today.getFullYear(),today.getMonth(),today.getDate(),23,59,59)
        });
    });

    function getFormData() {
        return $("#layuiForm").serialize();
    }
</script>