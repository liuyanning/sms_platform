<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/common.jsp" %>
<%@ include file="/common/layui_head.html" %>
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
            <form class="layui-form layui-card-header layuiadmin-card-header-auto" id="inputForm" onsubmit="return loadInputLogData();">
                <div class="layui-inline">
                    &nbsp;&nbsp;消息类型&nbsp;
                    <div class="layui-inline">
                        <ht:herocodeselect sortCode="message_type_code"  name="message_Type_Code"/>
                    </div>
                </div>
                <div class="layui-inline">
                    &nbsp;&nbsp;提交时间&nbsp;
                    <div class="layui-inline" style="width: 200px">
                        <input type="text" class="layui-input" id="minInputDate" name="minInputDate">
                    </div>-
                    <div class="layui-inline" style="width: 200px">
                        <input type="text" class="layui-input" id="maxInputDate" name="maxInputDate">
                    </div>
                </div>
                <div class="layui-inline">
                    <button class="layui-btn layui-btn-sm" type="submit" lay-submit="" lay-filter="reload">搜索
                    </button>
                </div>
            </form>

            <div class="layui-form layui-border-box">
                <div class="layui-card-body">
                    <table class="layui-hide" id="inpuLog_table" lay-filter="inpuLog_table"></table>
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
    var minCreateDate = window.parent.minCreateDate;
    layui.use('laydate', function(){
        var laydate = layui.laydate;
        laydate.render({     //创建时间选择框
            elem: '#minInputDate' //指定元素
            ,type:'datetime'
            ,trigger : 'click'
            ,value: minCreateDate?minCreateDate:new Date(today.getFullYear(),today.getMonth(),today.getDate())
        });
        laydate.render({     //创建时间选择框
            elem: '#maxInputDate' //指定元素
            ,type:'datetime'
            ,trigger : 'click'
            ,value: new Date(today.getFullYear(),today.getMonth(),today.getDate(),23,59,59)
        });
    });

    loadInputLogData();

    function loadInputLogData() {
        map = new Map();
        var minInputDate = $("#minInputDate").val()==''? minCreateDate:$("#minInputDate").val();
        var maxInputDate = $("#maxInputDate").val()?$("#maxInputDate").val():getFormatDateBefore(0)+' 23:59:59';
        var inputLogUrl = '/admin/sended_queryInputLogListTotalData?';
        layui.use('layer', function(){
            var loading= layui.layer.load(''); //遮罩层
            $.ajax({
                type: 'post',
                url: inputLogUrl+'&min_Create_Date='+minInputDate+'&max_Create_Date='+maxInputDate+'&'+$("#inputForm").serialize(), // 需要提交的 url
                dataType: 'json',
                success: function (res) {
                    map.set("phone_Nos_Count",res.data.count?res.data.phone_Nos_Count:0);
                    map.set("fee_Count",res.data.fee_Count?res.data.fee_Count:0);
                    map.set("faild_Count",res.data.faild_Count?res.data.faild_Count:0);
                    map.set("sale_Amount",res.data.sale_Amount?res.data.sale_Amount:0);
                    loadInpuLogTable();
                    return false;
                },
                complete:function (XMLHttpRequest,status){
                    layer.close(loading);//关闭遮罩层
                }
            })
        });


        return false;
    }

    function loadInpuLogTable() {
        layui.use('table', function(){
            var table = layui.table;
            table.render({
                elem: '#inpuLog_table'
                ,height: 'full-150'
                ,cols: [[
                    {field: 'phone_Nos_Count', title: '号码个数',width: 200}
                    ,{field: 'faild_Count', title: '分拣失败',width: 200}
                    ,{field: 'fee_Count', title: '计费条数',width: 200}
                    ,{field: 'sale_Amount', title: '计费（元）',minWidth: 200}
                ]]
                ,data: [{
                    "phone_Nos_Count": map.get("phone_Nos_Count")
                    ,"faild_Count": map.get("faild_Count")
                    ,"fee_Count": map.get("fee_Count")
                    ,"sale_Amount": map.get("sale_Amount")
                }]
                ,even: true
            });
        });
    }
</script>
</html>
