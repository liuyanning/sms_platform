<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/common/common.jsp" %>
<%@ include file="/common/layui_head.html" %>
<%@ include file="/common/dynamic_data.jsp" %>
<script src="/js/common/rcs_common.js"></script>
<body>
<div class="layui-fluid">
    <div class="layui-col-md12">
        <div class="layui-card">
            <form class="layui-form layui-card-header layuiadmin-card-header-auto" id="layuiForm"
                  onsubmit="return false;">
                <div class="layui-inline">
                    &nbsp;&nbsp;批次号&nbsp;
                    <div class="layui-input-inline">
                        <input name="message_Batch_No" id="message_Batch_No" class="layui-input"/>
                    </div>
                </div>&nbsp;&nbsp;
                <div class="layui-inline">
                    &nbsp;&nbsp;手机号码&nbsp;
                    <div class="layui-input-inline">
                        <input name="phone_Nos" id="phone_Nos" class="layui-input"/>
                    </div>
                </div>&nbsp;&nbsp;
                <div class="layui-inline">
                    &nbsp;&nbsp;消息内容&nbsp;
                    <div class="layui-input-inline">
                        <input name="content" id="content" class="layui-input"/>
                    </div>
                </div>
                <div class="layui-inline">
                    &nbsp;&nbsp;分拣时间&nbsp;
                    <div class="layui-inline">
                        <input name="minCreateDate" id="minCreateDate" class="layui-input layui-input-sm"/>
                    </div>
                    <div class="layui-inline">--</div>
                    <div class="layui-inline">
                        <input name="maxCreateDate" id="maxCreateDate" class="layui-input layui-input-sm"/>
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
    layui.extend({tableExt: '/js/layui-ext/tableExt'}).use(['tableExt'], function () {
        var table = layui.tableExt;
        $ = layui.$;
        var minCreateDate = '<c:out value="${minCreateDate}"/>';
        var today = new Date();
        layui.use('laydate', function(){
            var laydate = layui.laydate;
            laydate.render({     //创建时间选择框
                elem: '#minCreateDate' //指定元素
                ,type:'datetime'
                ,trigger : 'click'
                ,value: minCreateDate?minCreateDate:new Date(today.getFullYear(),today.getMonth(),today.getDate())
            });
            laydate.render({     //创建时间选择框
                elem: '#maxCreateDate' //指定元素
                ,type:'datetime'
                ,trigger : 'click'
                ,value: new Date(today.getFullYear(),today.getMonth(),today.getDate(),23,59,59)
            });
        });
        table.render({
            url: '/admin/sended_RcsInputLogList',
            height: 'full-150',
            where:{
                'minCreateDate': $("#minCreateDate").val()==''? minCreateDate:$("#minCreateDate").val(),
            },
            cols: [[
                {title: '批次号',width: 155,field:'message_Batch_No'},
                {title: '手机号',width: 155,field:'phone_Nos'},
                {title: '内容',templet:function (d) {
                        var button = "<button class=\"layui-btn layui-btn-xs\" title=\"详情\"" +
                            " onclick=\"contentDetail('"+encodeURIComponent(d.content)+"')\" >查看详情</button>";
                        return button;
                    },width: 120},
                {field: 'create_Date', title: '分拣时间', minWidth: 170},
            ]]
        });
    });

    //获取form表单数据
    function getFormData() {
        return $("#layuiForm").serialize();
    }

  
</script>