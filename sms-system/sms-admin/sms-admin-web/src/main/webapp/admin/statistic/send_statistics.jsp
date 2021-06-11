<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/admin/common/common.jsp" %>
<%@ include file="/admin/common/layui_head.html" %>
<%@ include file="/admin/common/country_operator.jsp" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="multipart/form-data; charset=utf-8" />
    <script src="/js/jquery-3.4.1.min.js"></script>
    <script src="/js/jquery-form.js"></script>
</head>
<body>
<div class="layui-fluid" style="padding: 0px">
    <div class="layui-col-md12" style="padding: 0px">
        <div class="layui-card"  style="padding: 0px 0px 30px 0px;">
            <form class="layui-form layui-card-header layuiadmin-card-header-auto" id="sendForm" action="" onsubmit="return loadSendData();">

                <div class="layui-inline">
                    &nbsp;&nbsp;商务&nbsp;
                    <div class="layui-inline" style="width: 200px">
                        <ht:herocustomdataselect dataSourceType="allBusinessUser" name="business_User_Id" />
                    </div>
                </div>
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
                    &nbsp;&nbsp;通道&nbsp;
                    <div class="layui-inline" style="width: 200px">
                        <ht:herocustomdataselect dataSourceType="allChannels" name="channel_No" />
                    </div>
                </div>

                <div class="layui-inline">
                    &nbsp;&nbsp;区域&nbsp;
                    <div class="layui-inline" style="width: 200px">
                        <ht:herocodeselect sortCode="location" name="province_Code"/>
                    </div>
                </div>
                <div class="layui-inline">
                    &nbsp;&nbsp;签名&nbsp;
                    <div class="layui-inline">
                        <input type="text" class="layui-input" name="signature">
                    </div>
                </div>
                <div class="layui-inline">
                    &nbsp;&nbsp;消息类型&nbsp;
                    <div class="layui-inline">
                        <ht:herocodeselect sortCode="message_type_code"  name="message_Type_Code"/>
                    </div>
                </div>
                <div class="layui-inline">
                    &nbsp;&nbsp;提交时间&nbsp;
                    <div class="layui-inline" style="width: 200px">
                        <input type="text" class="layui-input" id="minSubmitDate" >
                    </div> -
                    <div class="layui-inline" style="width: 200px">
                        <input type="text" class="layui-input" id="maxSubmitDate">
                    </div>
                </div>
                <div class="layui-inline">
                    <button class="layui-btn layui-btn-sm" type="submit" lay-submit="" lay-filter="reload">搜索
                    </button>
                </div>
            </form>
            <div class="layui-form layui-border-box">
                <div class="layui-card-body">
                    <table class="layui-hide" id="send_table" lay-filter="send_table"></table>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
<script type="text/javascript">
    layui.use(['element','jquery'], function() {
        var element = layui.element;
        var $ = jQuery = layui.jquery;
        FrameWH();
        function FrameWH() {
            var h = $(window).height()-30;
            if(h<445){h=445}
            $(".layui-card").css("height",h+"px");
        }
        $(window).resize(function () {
            FrameWH();
        });
    });
    var map = new Map();
    var today = new Date();
    var minSubmitDate = window.parent.minCreateDate;

    layui.use('laydate', function(){
        var laydate = layui.laydate;
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
            ,value: new Date(today.getFullYear(),today.getMonth(),today.getDate(),23,59,59)
        });
    });

    loadSendData();

    function loadSendData() {
        map = new Map();
        var minSendDate = $("#minSubmitDate").val()==''? minSubmitDate:$("#minSubmitDate").val();
        var maxSendDate = $("#maxSubmitDate").val()?$("#maxSubmitDate").val():getFormatDateBefore(0)+' 23:59:59';
        var submitUrl = '/admin/sended_querySubmitedListTotalData?';
        layui.use('layer', function(){
            var loading= layui.layer.load(''); //遮罩层
            $.ajax({
                type: 'post', // 提交方式 get/post
                url: submitUrl+'&minSubmitDate='+minSendDate+'&maxSubmitDate='+maxSendDate+'&'+$("#sendForm").serialize(), // 需要提交的 url
                dataType: 'json',
                async: true,
                success: function (res) { // data 保存提交后返回的数据，一般为 json 数据
                    map.set("submitCount", res.data.count?res.data.count:0);
                    map.set("submitSuccessSmsTotal", res.data.submit_Success_Total?res.data.submit_Success_Total:0);
                    map.set("submitFaildSmsTotal", res.data.submit_Faild_Total?res.data.submit_Faild_Total:0);
                    map.set("sortFaildSmsTotal", res.data.sort_Faild_Total?res.data.sort_Faild_Total:0);
                    map.set("channel_Unit_Price_Total", res.data.channel_Unit_Price_Total?res.data.channel_Unit_Price_Total:0);
                    map.set("enterprise_User_Taxes_Total", res.data.enterprise_User_Taxes_Total?res.data.enterprise_User_Taxes_Total:0);
                    map.set("channelTaxesTotal", res.data.channel_Taxes_Total?res.data.channel_Taxes_Total:0);
                    map.set("profitsTotal", res.data.profits_Total?res.data.profits_Total:0);

                    var reportUrl = '/admin/sended_queryReportListTotalData?';
                    $.ajax({
                        type: 'post',
                        url: reportUrl+'&minSubmitDate='+minSendDate+'&maxSubmitDate='+maxSendDate+'&'+$("#sendForm").serialize(), // 需要提交的 url
                        dataType: 'json',
                        async: true,
                        success: function (res) {
                            map.set("reportCount", res.data.count?res.data.count:0);
                            map.set("sendSuccessSmsTotal", res.data.send_Success_Total?res.data.send_Success_Total:0);
                            map.set("sendFaildSmsTotal", res.data.send_Faild_Total?res.data.send_Faild_Total:0);
                            loadSendTable();
                            layer.close(loading);//关闭遮罩层
                        },
                        error: function () {
                            layer.close(loading);//关闭遮罩层
                        }
                    })
                },
                error: function () {
                    layer.close(loading);//关闭遮罩层
                }
            })

        });
        return false;
    }
    function loadSendTable() {
        layui.use('table', function(){
            var table = layui.table;
            table.render({
                elem: '#send_table'
                ,height: 'full-200'
                ,cols: [[ //标题栏
                    {field: 'submitCount', title: '总条数',width: 130}
                    ,{field: 'submitSuccessSmsTotal', title: '提交成功',width: 130}
                    ,{field: 'submitFaildSmsTotal', title: '提交失败',width: 100}
                    ,{field: 'sortFaildSmsTotal', title: '分拣失败',width: 100}
                    ,{field: 'reportCount', title: '回执条数',width: 130}
                    ,{field: 'sendSuccessSmsTotal', title: '回执成功',width: 130}
                    ,{field: 'sendFaildSmsTotal', title: '回执失败',width: 100}
                    ,{field: 'channel_Unit_Price_Total', title: '通道成本',width: 130}
                    ,{field: 'enterprise_User_Taxes_Total', title: '发票成本',width: 130}
                    ,{field: 'channelTaxesTotal', title: '发票抵消',width: 130}
                    ,{field: 'profitsTotal', title: '利润',minWidth: 130}
                ]]
                ,data: [{
                    "submitCount": map.get("submitCount")
                    ,"submitSuccessSmsTotal": map.get("submitSuccessSmsTotal")
                    ,"submitFaildSmsTotal": map.get("submitFaildSmsTotal")
                    ,"sortFaildSmsTotal": map.get("sortFaildSmsTotal")
                    ,"reportCount": map.get("reportCount")
                    ,"sendSuccessSmsTotal": map.get("sendSuccessSmsTotal")
                    ,"sendFaildSmsTotal": map.get("sendFaildSmsTotal")
                    ,"channel_Unit_Price_Total": map.get("channel_Unit_Price_Total")
                    ,"enterprise_User_Taxes_Total": map.get("enterprise_User_Taxes_Total")
                    ,"channelTaxesTotal": map.get("channelTaxesTotal")
                    ,"profitsTotal": map.get("profitsTotal")
                }]
                ,even: true
            });
        });
    }
</script>
</html>
