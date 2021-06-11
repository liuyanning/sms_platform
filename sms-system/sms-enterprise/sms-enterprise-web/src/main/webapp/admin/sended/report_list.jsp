<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/common/common.jsp" %>
<%@ include file="/common/layui_head.html" %>
<%@ include file="/common/country_operator.jsp"%>
<body>
<div class="layui-fluid">
    <div class="layui-row layui-col-space15">
        <div class="layui-col-md12">
            <div class="layui-card">
                <form class="layui-form layui-card-header layuiadmin-card-header-auto" id="layuiForm" onsubmit="return false;">
                    <input value='<c:out value="${limitCode}"/>' name="limitCode" type="hidden" />
                    <input value='<c:out value="${channel_Master_Msg_No}"/>' name="channel_Master_Msg_No" id="channel_Master_Msg_No" type="hidden" />
                    <div class="layui-inline">
                        &nbsp;&nbsp;国家&nbsp;
                        <div class="layui-input-inline">
                            <ht:herocodeselect sortCode="country" name="country_Number" id="country_Number" valueField="Value"/>
                        </div>
                    </div>&nbsp;&nbsp;
                    <div class="layui-inline">
                        &nbsp;&nbsp;运营商&nbsp;
                        <div class="layui-input-inline">
                            <ht:countryoperatorselect id="operator" name="operator" />
                        </div>
                    </div>&nbsp;&nbsp;
                    <div class="layui-inline">
                        &nbsp;&nbsp;消息类型&nbsp;
                        <div class="layui-inline">
                            <ht:herocodeselect sortCode="message_type_code"  name="message_Type_Code"/>
                        </div>
                    </div>
                    <div class="layui-inline">
                        &nbsp;&nbsp;提交状态&nbsp;
                        <div class="layui-input-inline" style="width: 100px">
                            <ht:herocodeselect sortCode="034" name="submit_Status_Code"/>
                        </div>
                    </div>&nbsp;&nbsp;
                    <div class="layui-inline">
                        &nbsp;&nbsp;接收状态&nbsp;
                        <div class="layui-input-inline" style="width: 100px">
                            <ht:herocodeselect sortCode="033" name="status_Code"/>
                        </div>
                    </div>&nbsp;&nbsp;
                    <div class="layui-inline">
                        &nbsp;&nbsp;手机号码&nbsp;
                        <div class="layui-input-inline">
                            <input name="phone_No" class="layui-input"/>
                        </div>
                    </div>&nbsp;&nbsp;
                    <div class="layui-inline">
                        &nbsp;&nbsp;批次号&nbsp;
                        <div class="layui-input-inline">
                            <input name="msg_Batch_No"  value="<c:out value="${msg_Batch_No}"/>" class="layui-input"/>
                        </div>
                    </div>&nbsp;&nbsp;
                    <div class="layui-inline">
                        &nbsp;&nbsp;提交时间&nbsp;
                        <div class="layui-inline">
                            <input name="minSubmitDate" id="minSubmitDate" class="layui-input layui-input-sm" />
                        </div>
                        <div class="layui-inline">--</div>
                        <div class="layui-inline">
                            <input name="maxSubmitDate" id="maxSubmitDate"  class="layui-input layui-input-sm" />
                        </div>
                    </div>&nbsp;&nbsp;
                    <div class="layui-inline">
                        <button class="layui-btn layui-btn-sm" type="submit" lay-submit="" lay-filter="reload">搜索
                        </button>
                    </div>
                </form>
                <div class="layui-form layui-border-box layui-table-view">
                    <div class="layui-card-body">
                        <%--                        <blockquote class="layui-elem-quote" id="statistics" style="padding: 10px;"></blockquote>--%>
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
    var $;
    layui.extend({tableExt: '/js/layui-ext/tableExt'}).use(['tableExt','laydate'], function () {
        var table = layui.tableExt,
            laydate = layui.laydate,
            today = new Date();
        $ = layui.$;
        var minSubmitDate = '<c:out value="${minSubmitDate}"/>';
        var maxSubmitDate = '<c:out value="${maxSubmitDate}"/>';
        var channel_Master_Msg_No = '<c:out value="${channel_Master_Msg_No}"/>';
        var msg_Batch_No = '<c:out value="${msg_Batch_No}"/>';
        var flag = '<c:out value="${flag}"/>';
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
        table.render({
            url: '/admin/sended_reportList',
            height: 'full-150',
            where:{
                "minSubmitDate":minSubmitDate?minSubmitDate:$("#minSubmitDate").val()
                ,"maxSubmitDate":maxSubmitDate?maxSubmitDate:$("#maxSubmitDate").val()
                ,"channel_Master_Msg_No":channel_Master_Msg_No
                ,"msg_Batch_No":msg_Batch_No
                ,"flag":flag
            },
            cols: [[
                {field: 'phone_No', title: '手机号码',minWidth: 130},
                {field: 'msg_Batch_No', title: '批次号', minWidth: 180},
                { title: '提交状态',templet:function (d) {
                        var a = handleData(d.submit_Status_Code_name);
                        a += '<br>'+handleData(d.submit_Date);
                        return a;
                    }, width: 170},
                { title: '回执状态',templet:function (d) {
                        var a = handleData(d.status_Code_name);
                        a += '/'+handleData(d.native_Status_name);
                        a += '<br>'+handleData(d.status_Date);
                        return a;
                    }, width: 170},
                { title: '回执耗时',templet:function (d) {
                        var resTime = timeInterval(d.submit_Date,d.status_Date)||0;
                        var color = "#06b832";
                        if(resTime>5 && resTime<=60){
                            color="#ebc207";
                        }
                        if(resTime>60){
                            color="red";
                        }
                        var formatDate = timeIntervalFormate(d.submit_Date,d.status_Date);
                        return "<font color='"+color+"'>"+formatDate+"</font>";
                    }, width: 150},
                { title: '运营商',templet:function (d) {
                        return handleData(d.operator)+'/'+handleData(d.area_Name);
                    }, width: 220},
                { title: '类型/字数/序列',templet:function (d) {
                        var a = handleData(d.message_Type_Code_name);
                        a += '/'+handleData(d.content_Length);
                        a += "/"+handleData(d.sequence);
                        return a;
                    }, width: 170},
                {field: 'create_Date', title: '创建时间',minWidth: 150}
            ]], done: function (res) {
                layui.$("#statistics").html("");
                layui.use('element', function() {
                    var element = layui.element;
                    element.init();
                });
                // queryTotalData();
            }
        });
    });

    function getFormData() {
        return $("#layuiForm").serialize();
    }

</script>