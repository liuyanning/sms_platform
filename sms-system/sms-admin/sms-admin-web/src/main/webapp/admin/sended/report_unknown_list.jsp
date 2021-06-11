<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/admin/common/common.jsp" %>
<%@ include file="/admin/common/layui_head.html" %>
<%@ include file="/admin/common/dynamic_data.jsp" %>
<%@ include file="/admin/common/country_operator.jsp" %>
<body>
<div class="layui-fluid">
    <div class="layui-col-md12">
        <div class="layui-card">
            <form id="layuiForm" class="layui-form layui-card-header layuiadmin-card-header-auto" onsubmit="return false;">
                <div class="layui-inline">
                    &nbsp;&nbsp;企业名称&nbsp;
                    <div class="layui-inline" style="width: 200px">
                        <ht:heroenterpriseselect id="enterprise_No_Head" name="enterprise_No" />
                    </div>
                </div>
                <div class="layui-inline">
                    &nbsp;&nbsp;企业用户&nbsp;
                    <div class="layui-inline" style="width: 200px">
                        <ht:herocustomdataselect dataSourceType="allEnterpriseUsers" name="enterprise_User_Id" />
                    </div>
                </div>
                <div class="layui-inline">
                    &nbsp;&nbsp;通道&nbsp;
                    <div class="layui-inline" style="width: 200px">
                        <ht:herocustomdataselect dataSourceType="allChannels" name="channel_No" />
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
                    <div class="layui-inline">
                        <ht:herocodeselect sortCode="message_type_code"  name="message_Type_Code"/>
                    </div>
                </div>
                <div class="layui-inline">
                    &nbsp;&nbsp;短信内容&nbsp;
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
                    &nbsp;&nbsp;批次号&nbsp;
                    <div class="layui-input-inline">
                        <input name="msg_Batch_No" id="msg_Batch_No" class="layui-input"/>
                    </div>
                </div>&nbsp;&nbsp;
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
    layui.extend({tableExt: '/layuiadmin/extends/tableExt'}).use(['tableExt','laydate'], function () {
        var table = layui.tableExt,laydate = layui.laydate,$ = layui.$;
        var today = new Date()
        laydate.render({
            elem: '#minSubmitDate',
            type:'datetime',
            value: new Date(today.getFullYear(),today.getMonth(),today.getDate(),today.getHours()-1,00,00)
        });
        laydate.render({
            elem: '#maxSubmitDate',
            type:'datetime',
            value: new Date(today.getFullYear(),today.getMonth(),today.getDate(),today.getHours()-1,59,59)
        });
        table.render({
            url: '/admin/sended_reportUnknownList',
            height: 'full-150',
            where:{
                'minSubmitDate':$("#minSubmitDate").val(),
                'maxSubmitDate':$("#maxSubmitDate").val()
            },
            cols: [[
                {field: 'channel_No_ext',title: '发送通道',minWidth: 180,templet:function (d) {
                        return !d.channel_No_ext?'---':handleData(d.channel_No_ext.name);
                    }},
                {title: '企业名称/企业用户', width:180,templet:function (d) {
                    return !d.enterprise_No_ext?'---':handleData(d.enterprise_No_ext.name)
                        +"<br>"+handleData(d.enterprise_User_Id_ext.real_Name)
                        +"("+handleData(d.enterprise_User_Id_ext.user_Name)+")";
                }},
                {title: '手机号/批次号',width: 200,templet:function (d) {
                    return handleData(d.phone_No)+"<br>"+handleData(d.msg_Batch_No);
                    }},
                    { title: '提交状态',templet:function (d) {
                        var resTime = d.submitResponseTime||0;
                        var color = "#06b832";
                        if(resTime>100 && resTime<=200){
                          color="#ebc207";
                        }
                        if(resTime>200){
                          color="red";
                        }
                        var a = handleData(d.submit_Status_Code_name);
                        a += '('+ handleData(d.submit_Description)+')/<font color='+color+'>'+resTime+'ms</font><br>';
                        a += handleData(d.submit_Date);
                        return a;
                    }, width: 152},
                    {field: 'content', title: '内容',width: 300},
                    { title: '运营商',templet:function (d) {
                        var a = handleData(d.country_Number_name)+'/'+handleData(d.operator)+'<br>';
                        a += handleData(d.area_Name)+'<br>';
                        a += handleData(d.sp_Number);
                        return a;
                    }, width: 180},
                    { title: '类型/字数/长消息/序列',templet:function (d) {
                            var a = handleData(d.message_Type_Code_name);
                            a += '/'+handleData(d.content_Length);
                            a += "/"+handleData(d.is_LMS_name)+"/"+handleData(d.sequence);
                            return a;
                    }, width: 170},
              { title: '成本/利润',templet:function (d) {
                  var a = '发票成本:'+handleData(d.enterprise_User_Taxes);
                  a += '/发票抵消:' + handleData(d.channel_Taxes)+'<br>';
                  a += '成本:'+handleData(d.channel_Unit_Price) + '/利润:'+handleData(d.profits);
                  return a;
                }, width: 180},
                {field: 'create_Date', title: '创建时间',minWidth: 170},
            ]]
        });
    });

    function getFormData() {
        return $("#layuiForm").serialize();
    }
</script>