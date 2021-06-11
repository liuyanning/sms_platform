<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/common.jsp" %>
<%@ include file="/common/layui_head.html"%>
<%@ include file="/common/dynamic_data.jsp" %>
<script src="/js/common/rcs_common.js"></script>
<body>
<div class="layui-fluid">
        <div class="layui-col-md12">
            <div class="layui-card">
                <form id="layuiForm" class="layui-form layui-card-header layuiadmin-card-header-auto" onsubmit="return false;">
                    <div class="layui-inline">
                        &nbsp;&nbsp;国家&nbsp;
                        <div class="layui-input-inline">
                            <ht:herocodeselect sortCode="country" name="country_Code" id="country_Code" valueField="Value"/>
                        </div>
                    </div>&nbsp;&nbsp;
                    <div class="layui-inline">
                        &nbsp;&nbsp;运营商&nbsp;
                        <div class="layui-input-inline">
                            <ht:countryoperatorselect id="operator" name="operator" />
                        </div>
                    </div>&nbsp;&nbsp;
                    <div class="layui-inline">
                        &nbsp;&nbsp;状态&nbsp;
                        <div class="layui-input-inline" style="width: 100px">
                            <ht:herocodeselect sortCode="sms_status_code" name="status_Code"/>
                        </div>
                    </div>&nbsp;&nbsp;
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
                            <input name="message_Batch_No" id="message_Batch_No" class="layui-input"/>
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
            url: '/admin/sended_RcsRecordsList',
            height: 'full-160',
            where:{
                'minCreateDate': $("#minCreateDate").val()==''? minCreateDate:$("#minCreateDate").val(),
                'maxCreateDate':$("#maxCreateDate").val()
            },
            cols: [[
                {title: '企业名称/企业用户', width:180,templet:function (d) {
                    return !d.enterprise_No_ext?'---':handleData(d.enterprise_No_ext.name)
                        +"<br>"+handleData(d.enterprise_User_Id_ext.real_Name)
                        +"("+handleData(d.enterprise_User_Id_ext.user_Name)+")";
                    }},
                {title: '手机号/批次号',width: 170,templet:function (d) {
                	 var a = handleData(d.phone_No);
                     a += '<br>'+handleData(d.message_Batch_No);
                     return a;
                    }},
                { title: '短信状态',templet:function (d) {
                        var a = handleData(d.status_Code_name);
                        return a;
                    }, width: 120},
                { title: '回执状态/回执时间',templet:function (d) {
                    var a = handleData(d.report_Status);
                    a += '/'+handleData(d.report_Date);
                    return a;
                }, width: 170},
                {title: '内容',templet:function (d) {
                        var button = "<button class=\"layui-btn layui-btn-xs\" title=\"详情\"" +
                            " onclick=\"contentDetail('"+encodeURIComponent(d.content)+"')\" >查看详情</button>";
                        return button;
                    },width: 120},
                { title: '运营商',templet:function (d) {
                    var a = handleData(d.operator)+'<br>';
                    a += handleData(d.area_Name);
                    return a;
                }, width: 180},
                { title: '通知次数/推送状态/推送时间',templet:function (d) {
                        var a = handleData(d.notify_Count)=='---'?0:handleData(d.notify_Count);
                        a += ' / '+(handleData(d.notify_Status)=='---'?'未知':handleData(d.notify_Status));
                        a += ' / '+handleData(d.notify_Date);
                        return a;
                    }, width: 200},
                {field: 'create_Date', title: '创建时间',minWidth: 170}
            ]],
        });
    });
  
    function getFormData() {
        return $("#layuiForm").serialize();
    }

</script>