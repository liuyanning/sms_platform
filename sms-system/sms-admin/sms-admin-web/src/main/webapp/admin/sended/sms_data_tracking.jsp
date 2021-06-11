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
                <div class="layui-input-inline">
                    <button type="button" id="data_tracking" class="layui-btn layui-btn-sm">搜索</button>
                </div>
            </form>
        </div>
    </div>
</div>

</body>
<script>
    var $;
    var today = new Date();
    layui.use(['laydate'], function () {
        var laydate = layui.laydate;
        laydate.render({
            elem: '#minCreateDate',
            type:'datetime',
            trigger : 'click',
            value: new Date(today.getFullYear(),today.getMonth(),today.getDate())
        });
        laydate.render({
            elem: '#maxCreateDate',
            type:'datetime',
            trigger : 'click',
            value: new Date(today.getFullYear(),today.getMonth(),today.getDate(),23,59,59)
        });
    });

    $(document).on('click', '#data_tracking', function () {
        var minCreateDate = $("#minCreateDate").val();
        var maxCreateDate = $("#maxCreateDate").val();
        var phone_No = $("#phone_No").val();
        if(minCreateDate==""||maxCreateDate==""){
            layer.msg("请选择查询起止时间时间");
            return;
        }
        if(phone_No==""){
            layer.msg("请选择查询手机号");
            return;
        }
        layer.open({
            type: 2,
            title: "数据追踪",
            content: "/admin/sms_data_tracking?phone_No="+phone_No+"&minCreateDate="+minCreateDate+"&maxCreateDate="+maxCreateDate,
            maxmin: true,
            shadeClose: true,
            area: ["80%","90%"]
        });
    });
</script>