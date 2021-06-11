<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/admin/common/common.jsp" %>
<%@ include file="/admin/common/layui_head.html" %>
<%@ include file="/admin/common/dynamic_data.jsp" %>
<body>
<div class="layui-fluid">
    <div class="layui-col-md12">
        <div class="layui-card">
            <form id="layuiForm" class="layui-form layui-card-header layuiadmin-card-header-auto"
                  onsubmit="return false;">
                <input hidden name="alarmType" value="<c:out value="${alarmType}"/>">
                <input hidden name="bingValveType" value="<c:out value="${bingValveType}"/>">
                <input hidden name="bindValue" value="<c:out value="${bindValue}"/>">
                <c:if test="${bingValveType == 'product'}">
                    <div class="layui-inline">
                        &nbsp;&nbsp;产品&nbsp;
                        <div class="layui-inline" style="width: 200px">
                            <ht:heroproductselect selected="${bingValve}" name="product_No"/>
                        </div>
                    </div>
                </c:if>
                <c:if test="${bingValveType == 'channel'}">
                    <div class="layui-inline">
                        &nbsp;&nbsp;通道&nbsp;
                        <div class="layui-inline" style="width: 200px">
                            <ht:herocustomdataselect dataSourceType="allChannels"
                                                     selected="${bingValve}" name="channel_No"/>
                        </div>
                    </div>
                </c:if>
                <div class="layui-inline">
                    &nbsp;&nbsp;时间&nbsp;
                    <div class="layui-inline">
                        <input name="beginDate" id="beginDate" class="layui-input layui-input-sm"/>
                    </div>
                    <div class="layui-inline">--</div>
                    <div class="layui-inline">
                        <input name="endDate" id="endDate" class="layui-input layui-input-sm"/>
                    </div>
                </div>&nbsp;&nbsp;
                <div class="layui-inline">
                    <button class="layui-btn layui-btn-sm" type="submit" lay-submit=""
                            lay-filter="reload">搜索
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
    layui.extend({tableExt: '/layuiadmin/extends/tableExt'}).use(['tableExt', 'laydate'], function () {
        var table = layui.tableExt, laydate = layui.laydate, $ = layui.$;
        var beginDate = '<c:out value="${beginDate}"/>';
        var endDate = '<c:out value="${endDate}"/>';
        var alarmType = '<c:out value="${alarmType}"/>';
        var bingValveType = '<c:out value="${bingValveType}"/>';
        var bindValue = '<c:out value="${bindValue}"/>';
        laydate.render({
            elem: '#beginDate',
            type: 'datetime',
            trigger: 'click',
            value: beginDate
        });
        laydate.render({
            elem: '#endDate',
            type: 'datetime',
            trigger: 'click',
            value: endDate
        });
        if (beginDate == 'null' || endDate == 'null') {
            return layer.alert("时间不能为空！");
        }
        table.render({
            url: '/admin/sended_alarmLogDetailList',
            height: 'full-100',
            where: {
                'beginDate': beginDate,
                'endDate': endDate,
                'bingValveType': bingValveType,
                'bindValue': bindValue,
                'alarmType': alarmType,
            },
            cols: [[
                {
                    field: 'channel_No_ext', title: '发送通道', minWidth: 180, templet: function (d) {
                        return !d.channel_No_ext ? '---' : handleData(d.channel_No_ext.name);
                    }
                },
                {
                    title: '企业名称/企业用户', width: 180, templet: function (d) {
                        return !d.enterprise_No_ext ? '---' : handleData(d.enterprise_No_ext.name)
                            + "<br>" + handleData(d.enterprise_User_Id_ext.real_Name)
                            + "(" + handleData(d.enterprise_User_Id_ext.user_Name) + ")";
                    }
                },
                {
                    title: '手机号/批次号', width: 200, templet: function (d) {
                        return handleData(d.phone_No) + "<br>" + handleData(d.msg_Batch_No);
                    }
                },
                {
                    title: '提交状态', templet: function (d) {
                        var resTime = d.submitedResponseTime || 0;
                        var color = "#06b832";
                        if (resTime > 100 && resTime <= 200) {
                            color = "#ebc207";
                        }
                        if (resTime > 200) {
                            color = "red";
                        }
                        var a = handleData(d.submit_Status_Code_name);
                        a += '(' + handleData(d.submit_Description) + ')/<font color=' + color + '>' + resTime + 'ms</font><br>';
                        a += handleData(d.submit_Date);
                        return a;
                    }, width: 152
                },
                {field: 'content', title: '内容', width: 300},
                {
                    title: '运营商', templet: function (d) {
                        var a = handleData(d.country_Number_name) + '/' + handleData(d.operator) + '<br>';
                        a += handleData(d.area_Name) + '<br>';
                        a += handleData(d.sp_Number);
                        return a;
                    }, width: 180
                },
                {
                    title: '类型/字数/长消息/序列', templet: function (d) {
                        var a = handleData(d.message_Type_Code_name);
                        a += "/" + handleData(d.content_Length);
                        a += "/" + handleData(d.is_LMS_name) + "/" + handleData(d.sequence);
                        return a;
                    }, width: 170
                },
                {field: 'create_Date', title: '创建时间', minWidth: 170},
            ]]
        });
    });

    function getFormData() {
        return $("#layuiForm").serialize();
    }
</script>