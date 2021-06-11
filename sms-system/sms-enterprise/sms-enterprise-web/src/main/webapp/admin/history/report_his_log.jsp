<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/common/common.jsp" %>
<%@ include file="/common/layui_head.html" %>
<%@ include file="/common/country_operator.jsp" %>
<body>
<div class="layui-fluid">
    <div class="layui-col-md12">
        <div class="layui-card">
            <form class="layui-form layui-card-header layuiadmin-card-header-auto" id="layuiForm"
                  onsubmit="return false;">
                <input hidden name="pagingState"/>
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
                    &nbsp;&nbsp;手机号码&nbsp;
                    <div class="layui-input-inline">
                        <input name="phone_No" class="layui-input"/>
                    </div>
                </div>&nbsp;&nbsp;
                <div class="layui-inline">
                    &nbsp;&nbsp;批次号&nbsp;
                    <div class="layui-input-inline">
                        <input name="msg_Batch_No" value='<c:out value="${msg_Batch_No}"></c:out>'
                               class="layui-input"/>
                    </div>
                </div>&nbsp;
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
   &nbsp;
                <div class="layui-inline">
                    &nbsp;&nbsp;提交时间&nbsp;
                    <div class="layui-inline">
                        <input name="minCreateDate" id="minCreateDate" class="layui-input layui-input-sm"/>
                    </div>
                    <div class="layui-inline">--</div>
                    <div class="layui-input-inline">
                        <input name="maxCreateDate" id="maxCreateDate" lay-verify="createDate" class="layui-input layui-input-sm"/>
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
    var $ ;
    var pagingStateArray = new Array();
    var pageNum = 1;
    var pageSize = 20;
    var totalCount = pageSize;
    var pagingState;
    layui.extend({tableExt: '/js/layui-ext/tableExt'}).use(['tableExt'], function () {
        var table = layui.tableExt,
        $ = layui.$;
        table.render({
            url: '/admin/history_ReportList',
            height: 'full-200',
            where:{
                pageSize: pageSize,
                pagingState: $('input[name="pagingState"]').val(),
                minCreateDate: $("#minCreateDate").val()==''?getFormatDateBefore(4)+' 00:00:00':$("#minCreateDate").val(),
                maxCreateDate: $("#maxCreateDate").val()==''?getFormatDateBefore(4)+' 23:59:59':$("#maxCreateDate").val()
            },
            cols: [[
                {type: 'numbers', title:'序号',width:60},
                {title: '手机号/批次号',width: 155,templet:function (d) {
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
                { title: '运营商',templet:function (d) {
                        var a = handleData(d.country_Number_name)+'/'+handleData(d.operator)+'<br>';
                        a += handleData(d.area_Name)+'<br>';
                        a += handleData(d.sp_Number);
                        return a;
                    }, width: 180},
                { title: '类型/字数/序列',templet:function (d) {
                        var a = handleData(d.message_Type_Code_name);
                        a += '/'+handleData(d.content.length);
                        a += "/"+handleData(d.sequence);
                        return a;
                    }, width: 170},
                {field: 'create_Date', title: '创建时间',minWidth: 150}
            ]],
            done: function (res) {
                layui.use(['laypage', 'layer'], function(){
                    var laypage = layui.laypage
                        ,layer = layui.layer;
                    if(res.code!="0") return;
                    //保存下一页的pagingState,增加totalCount
                    if(res.map.pagingState != null && pageNum > pagingStateArray.length){
                        totalCount = totalCount+pageSize;
                        pagingStateArray.push(res.map.pagingState)
                    }
                    //自定义分页,layout: ['count', 'prev', 'page', 'next', 'limit', 'refresh', 'skip']
                    laypage.render({
                        elem: 'layui-table-page1'
                        ,count: totalCount
                        ,curr: pageNum
                        ,limit: pageSize
                        ,layout: ['prev', 'page', 'next', 'limit']
                        ,jump: function(obj, first){
                            if(obj.curr > pageNum ){
                                if(obj.curr -2 >= pagingStateArray.length){
                                    pageNum = pagingStateArray.length +1;
                                }else{
                                    pageNum = obj.curr;
                                }
                                pagingState = pagingStateArray[pageNum-2];
                            }else if(obj.curr < pageNum){
                                if(obj.curr <= 1){
                                    pageNum = 1;
                                    pagingState = null;
                                }else{
                                    pageNum = obj.curr;
                                    pagingState = pagingStateArray[pageNum-2];
                                }
                            }
                            if(obj.limit != pageSize){
                                pageSize = obj.limit;
                                pagingStateArray = new Array();
                                pageNum = 1;
                                totalCount = pageSize;
                                pagingState = null;
                            }
                            if(!first){
                                table.reload('list_table',{
                                    where: {
                                        pageSize: pageSize,
                                        pagingState:pagingState
                                    }
                                })
                            }
                        }
                    });
                });
            }
        });
    });
    layui.use(['form', 'laydate'], function () {
        var laydate = layui.laydate;
        var form = layui.form;
        laydate.render({     //创建时间选择框
            elem: '#minCreateDate' //指定元素
            , type: 'datetime'
            , trigger: 'click'
            , value: getFormatDateBefore(4)+' 00:00:00'
        });
        laydate.render({     //创建时间选择框
            elem: '#maxCreateDate' //指定元素
            , type: 'datetime'
            , trigger: 'click'
            , value: getFormatDateBefore(4)+' 23:59:59'
        });
        //自定义验证规则
        form.verify({
            createDate: function(value){
                var minCreateDate = document.getElementById("minCreateDate").value,
                    maxCreateDate = document.getElementById("maxCreateDate").value;
                if(minCreateDate.substr(0,10) != maxCreateDate.substr(0,10)){
                    return"查询时间只能为同一天";
                };
                //查询条件改变重置分页数据
                pagingStateArray = new Array();
                pageNum = 1;
                totalCount = pageSize;
                pagingState = null;
            }
        });
    });
    function getFormData() {
        return $("#layuiForm").serialize();
    }
</script>