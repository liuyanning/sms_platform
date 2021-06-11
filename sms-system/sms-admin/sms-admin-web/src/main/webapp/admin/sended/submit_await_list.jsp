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
                    <div class="layui-inline" style="width: 200px">
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
            url: '/admin/sended_submitAwaitList',
            height: 'full-160',
            where:{
                'minCreateDate': $("#minCreateDate").val()==''? minCreateDate:$("#minCreateDate").val(),
                'maxCreateDate':$("#maxCreateDate").val()
            },
            cols: [[
                {field: 'channel_No_ext',title: '发送通道',minWidth: 180,templet:function (d) {
                        return !d.channel_No_ext?'---':handleData(d.channel_No_ext.name)+"<br>"+d.channel_No;
                    }},
                {title: '企业名称/企业用户', width:180,templet:function (d) {
                        return !d.enterprise_No_ext?'-$--':handleData(d.enterprise_No_ext.name)
                            +"<br>"+handleData(d.enterprise_User_Id_ext.real_Name)
                            +"("+handleData(d.enterprise_User_Id_ext.user_Name)+")";
                    }},
                {title: '手机号',width: 170,templet:function (d) {
                        return handleData(d.phone_No);
                    }},
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
                {field: 'create_Date', title: '创建时间',minWidth: 170}
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