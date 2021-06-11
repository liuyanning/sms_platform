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
                        协议类型&nbsp;
                        <div class="layui-inline" style="width: 200px">
                            <ht:herocodeselect sortCode="protocol_type" name="protocol_Type_Code"/>
                        </div>
                    </div>
                    &nbsp;
                    <div class="layui-inline">
                        通道名称&nbsp;
                        <div class="layui-inline">
                            <input name="name" class="layui-input layui-input-sm"/>
                        </div>
                    </div>
                    &nbsp;
                    <div class="layui-inline">
                        通道编号&nbsp;
                        <div class="layui-inline">
                            <input name="no" class="layui-input layui-input-sm"/>
                        </div>
                    </div>
                    &nbsp;
                    <div class="layui-inline">
                        状态&nbsp;
                        <div class="layui-inline" style="width: 200px">
                            <ht:herocodeselect sortCode="024" name="status_Code" selected="start"/>
                        </div>
                    </div>
                    &nbsp;
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
            url: '/admin/netway_channelList',
            where: {"status_Code": "Start"},
            cols: [[
                {checkbox: true},
                {title: '操作', width: 100,templet: function(d){
                        var test = "<button class=\"layui-btn layui-btn-xs\" lay-event='{\"type\":\"dialog\",\"url\":\"/admin/netway/try_2_try.jsp?limitCode=004001024&no="+d.no+"\",\"width\":\"800\",\"height\":\"500\",\"title\":\"通道测试\"}'  title=\"通道测试\" >通道测试</button>";
                        if(d.protocol_Type_Code=='http'){
                            var balance = "<button class=\"layui-btn layui-btn-xs\" lay-event='{\"type\":\"dialog\",\"url\":\"/admin/netway_channelBalance?limitCode=004001025&no="+d.no+"\",\"width\":\"800\",\"height\":\"500\",\"title\":\"查看余额\"}'  title=\"查看余额\" >查看余额</button>";
                            return test+balance;
                        }
                        return test;
                    }},
                {title: '编号/名称', width: 220, templet: function(d){
                    return handleData(d.no)+'<br>'+handleData(d.name);
                  }},
                {title: '状态/会话', width: 200,templet: function(d){
                    return handleData(d.status_Code_name)+'/'+handleData(d.session_Status)+
                    '<br>'+handleData(d.session_Status_Date);
                }},
                {title: '账号/密码', width: 150,templet: function(d){
                    return handleData(d.account)+'<br>'+handleData(d.password);
                }},
                {title: '签名方向/位置/码号', width: 200,templet: function(d){
                    return handleData(d.signature_Direction_Code_name)+'/'+handleData(d.signature_Position_name)+
                        '<br>'+handleData(d.sp_Number);
                }},
                {title: '行业', width: 100,templet: function(d){
                    return handleData(d.trade_Type_Code_name);
                }},
                {title: '协议/版本/分组编码', width: 160,templet: function(d){
                    return handleData(d.protocol_Type_Code)
                        +'<br>'+handleData(d.version)+"/"+handleData(d.group_Code);
                }},
                {title: 'IP/PORT/连接数/速度/编码', width: 200,templet: function(d){
                    return handleData(d.ip)+'/'+handleData(d.port)+
                    "<br>"+handleData(d.max_Concurrent_Total)+"/"+handleData(d.submit_Speed)
                        +"/"+handleData(d.charset);
                }},
                {field: 'create_Date', title: '创建时间'}
            ]]
        });
    });
</script>