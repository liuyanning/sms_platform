<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/admin/common/common.jsp" %>
<%@ include file="/admin/common/layui_head.html" %>
<%@ include file="/admin/common/country_operator.jsp" %>
<body>
<div class="layui-fluid">
    <div class="layui-col-md12">
        <div class="layui-card">
            <form class="layui-form layui-card-header layuiadmin-card-header-auto" id="layuiForm"
                  onsubmit="return false;">
                <input value="<c:out value="${limitCode}"/>" name="limitCode" hidden/>
                <input value="<c:out value="${channel_Master_Msg_No}"/>" name="channel_Master_Msg_No"
                       id="channel_Master_Msg_No" hidden/>
                <div class="layui-inline">
                    &nbsp;&nbsp;企业名称&nbsp;
                    <div class="layui-inline" style="width: 200px">
                        <ht:heroenterpriseselect id="enterprise_No_Head" name="enterprise_No"/>
                    </div>
                </div>
                <div class="layui-inline">
                    &nbsp;&nbsp;企业用户&nbsp;
                    <div class="layui-inline" style="width: 200px">
                        <ht:herocustomdataselect dataSourceType="allEnterpriseUsers" name="enterprise_User_Id"/>
                    </div>
                </div>
                <div class="layui-inline">
                    &nbsp;&nbsp;通道&nbsp;
                    <div class="layui-inline" style="width: 200px">
                        <ht:herocustomdataselect dataSourceType="allChannels" name="channel_No"/>
                    </div>
                </div>
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
                    <div class="layui-inline" style="width: 200px">
                        <ht:herocodeselect sortCode="message_type_code" name="message_Type_Code"/>
                    </div>
                </div>
                <div class="layui-inline">
                    &nbsp;&nbsp;提交状态&nbsp;
                    <div class="layui-input-inline" style="width: 100px">
                        <ht:herocodeselect sortCode="034" name="submit_Status_Code"/>
                    </div>
                </div>&nbsp;&nbsp;
                <div class="layui-inline">
                    &nbsp;&nbsp;回执状态&nbsp;
                    <div class="layui-input-inline" style="width: 100px">
                        <ht:herocodeselect sortCode="033" name="status_Code"/>
                    </div>
                </div>&nbsp;&nbsp;
                <div class="layui-inline">
                    &nbsp;&nbsp;原生状态&nbsp;
                    <div class="layui-input-inline" style="width: 100px">
                        <input name="native_Status" class="layui-input"/>
                    </div>
                </div>&nbsp;&nbsp;
                <div class="layui-inline">
                    &nbsp;&nbsp;提交描述&nbsp;
                    <div class="layui-input-inline" style="width: 100px">
                        <input name="submit_Description" class="layui-input"/>
                    </div>
                </div>&nbsp;&nbsp;
                <div class="layui-inline">
                    &nbsp;&nbsp;手机号码&nbsp;
                    <div class="layui-input-inline">
                        <input name="phone_No" class="layui-input"/>
                    </div>
                </div>&nbsp;&nbsp;
                <div class="layui-inline">
                    短信内容
                    <div class="layui-input-inline">
                        <input name="content" id = "content" class="layui-input"/>
                    </div>
                </div>
                <div class="layui-inline">
                    &nbsp;&nbsp;批次号&nbsp;
                    <div class="layui-input-inline">
                        <input name="msg_Batch_No" value='<c:out value="${msg_Batch_No}"></c:out>'
                               class="layui-input"/>
                    </div>
                </div>
                <div class="layui-inline">
                    &nbsp;&nbsp;提交时间&nbsp;
                    <div class="layui-inline">
                        <input name="minSubmitDate" id="minSubmitDate" class="layui-input layui-input-sm"/>
                    </div>
                    <div class="layui-inline">--</div>
                    <div class="layui-inline">
                        <input name="maxSubmitDate" id="maxSubmitDate" class="layui-input layui-input-sm"/>
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
    var table;
    layui.extend({tableExt: '/layuiadmin/extends/tableExt'}).use(['tableExt','laydate'], function () {
        table = layui.tableExt;
         var laydate = layui.laydate,
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
            url: '/admin/sended_reportNotifyAwaitList',
            height: 'full-160',
            where:{
                "minSubmitDate":minSubmitDate?minSubmitDate:$("#minSubmitDate").val()
                ,"maxSubmitDate":maxSubmitDate?maxSubmitDate:$("#maxSubmitDate").val()
                ,"channel_Master_Msg_No":channel_Master_Msg_No
                ,"msg_Batch_No":msg_Batch_No
                ,"flag":flag
            },
            cols: [[
                {checkbox: true},
                {field: 'channel_No_ext', title: '发送通道',minWidth: 180,templet:function (d) {
                        return !d.channel_No_ext?'---':handleData(d.channel_No_ext.name)+"<br>"+d.channel_No;
                    }},
                {field: 'enterprise_No_ext', title: '企业名称/企业用户',templet:function (d) {
                        return !d.enterprise_No_ext?'---':handleData(d.enterprise_No_ext.name)
                            +"<br>"+handleData(d.enterprise_User_Id_ext.real_Name)
                            +"("+handleData(d.enterprise_User_Id_ext.user_Name)+")";
                    }, width: 180},
                {title: '手机号/批次号',width: 170,templet:function (d) {
                        return handleData(d.phone_No)+"<br>"+handleData(d.msg_Batch_No);
                    }},
                { title: '提交状态',templet:function (d) {
                        var a = handleData(d.submit_Status_Code_name);
                        a += '('+ handleData(d.submit_Description)+')';
                        a += '<br>'+handleData(d.submit_Date);
                        return a;
                    }, width: 180},
                { title: '回执状态',templet:function (d) {
                        var a = handleData(d.status_Code_name);
                        a += '/'+handleData(d.native_Status_name);
                        a += '<br>'+handleData(d.status_Date);
                        return a;
                    }, width: 180},
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
                    }, width: 130},
                { field: 'content', title: '内容',width: 300},
                { title: '运营商',templet:function (d) {
                        var a = handleData(d.country_Number_name)+'/'+handleData(d.operator)+'<br>';
                        a += handleData(d.area_Name)+'<br>';
                        a += handleData(d.sp_Number);
                        return a;
                    }, width: 180},
                { title: '类型/字数/序列',templet:function (d) {
                        var a = handleData(d.message_Type_Code_name);
                        a += '/'+handleData(d.content_Length);
                        a += "/"+handleData(d.sequence);
                        return a;
                    }, width: 170},
                {field: 'create_Date', title: '创建时间',minWidth: 150}
            ]],
            done:function (res) {
                layui.$("#statistics").html("");
                layui.use('element', function() {
                    var element = layui.element;
                    element.init();
                });
            }
        });
    });

    function getFormData() {
        return $("#layuiForm").serialize();
    }

</script>