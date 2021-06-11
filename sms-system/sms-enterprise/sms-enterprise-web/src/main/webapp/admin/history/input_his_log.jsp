<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/common/common.jsp" %>
<%@ include file="/common/layui_head.html" %>
<body>
<div class="layui-fluid">
    <div class="layui-row layui-col-space15">
    <div class="layui-col-md12">
        <div class="layui-card">
            <form class="layui-form layui-card-header layuiadmin-card-header-auto" onsubmit="return false;"
                  id="layuiForm">
                <input hidden name="pagingState"/>
                <div class="layui-inline">
                    &nbsp;&nbsp;手机号码&nbsp;
                    <div class="layui-input-inline">
                        <input id="phone_Nos" name="phone_Nos" class="layui-input"/>
                    </div>
                </div>&nbsp;&nbsp;
                <div class="layui-inline">
                    &nbsp;&nbsp;消息类型&nbsp;
                    <div class="layui-inline">
                        <ht:herocodeselect sortCode="message_type_code"  name="message_Type_Code"/>
                    </div>
                </div>
                <div class="layui-inline">
                    &nbsp;&nbsp;消息内容&nbsp;
                    <div class="layui-input-inline">
                        <input id="content" name="content" class="layui-input"/>
                    </div>
                </div>&nbsp;&nbsp;
                <div class="layui-inline">
                    &nbsp;&nbsp;批次号&nbsp;
                    <div class="layui-input-inline">
                        <input name="msg_Batch_No"  class="layui-input"/>
                    </div>
                </div>&nbsp;&nbsp;
                <div class="layui-inline">
                    &nbsp;&nbsp;分拣时间&nbsp;
                    <div class="layui-input-inline">
                        <input name="minCreateDate" id="minSubmitDate" class="layui-input layui-input-sm"
                               size="15"/>
                    </div>
                    <div class="layui-inline">--</div>
                    <div class="layui-input-inline">
                        <input name="maxCreateDate" id="maxSubmitDate" lay-verify="submitDate" class="layui-input layui-input-sm"/>
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
</div>
</body>
<script>
    var $;
    var pagingStateArray = new Array();
    var pageNum = 1;
    var pageSize = 20;
    var totalCount = pageSize;
    var pagingState;
    layui.extend({tableExt: '/js/layui-ext/tableExt'}).use(['tableExt'], function () {
        var table = layui.tableExt;
        $ = layui.$;
        table.render({
            url: '/admin/history_InputLogList',
            height: 'full-180',
            where: {
                pageSize: pageSize,
                pagingState: $('input[name="pagingState"]').val(),
                minCreateDate: $("#minSubmitDate").val()==''?getFormatDateBefore(4)+' 00:00:00':$("#minSubmitDate").val(),
                maxCreateDate: $("#maxSubmitDate").val()==''?getFormatDateBefore(4)+' 23:59:59':$("#maxSubmitDate").val()
            },
            cols: [[
                {type: 'numbers', title:'序号',width:60},
                {title: '手机号',width: 155,field:'phone_Nos'},
                {field: 'msg_Batch_No', title: '批次号', width: 220},
                {field: 'content', title: '内容', minWidth: 350},
                { title: '类型/字数/号码/计费',templet:function (d) {
                        var a = handleData(d.message_Type_Code_name);
                        a += '/'+handleData(d.content.length);
                        a += '/'+handleData(d.phone_Nos_Count)+'个<br>';
                        a += '计费:'+handleData(d.fee_Count)+'条='+ handleData(d.sale_Amount)+'元';
                        return a;
                    }, width: 190},

                {field: 'create_Date', title: '提交时间', width: 170}
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
            elem: '#minSubmitDate' //指定元素
            , type: 'datetime'
            , trigger: 'click'
            , value: getFormatDateBefore(4)+' 00:00:00'
        });
        laydate.render({     //创建时间选择框
            elem: '#maxSubmitDate' //指定元素
            , type: 'datetime'
            , trigger: 'click'
            , value: getFormatDateBefore(4)+' 23:59:59'
        });
        //自定义验证规则
        form.verify({
            submitDate: function(value){
                var minSubmitDate = document.getElementById("minSubmitDate").value,
                    maxSubmitDate = document.getElementById("maxSubmitDate").value;
                if(minSubmitDate.substr(0,10) != maxSubmitDate.substr(0,10)){
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