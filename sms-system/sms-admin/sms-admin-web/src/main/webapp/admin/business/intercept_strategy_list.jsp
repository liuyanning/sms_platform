<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/admin/common/common.jsp" %>
<%@ include file="/admin/common/layui_head.html" %>
<body>
<div class="layui-fluid">
    <div class="layui-row layui-col-space15">
        <div class="layui-col-md12">
            <div class="layui-card">
                <form id="subForm" class="layui-form layui-card-header layuiadmin-card-header-auto" onsubmit="return false;">
                    <div class="layui-inline">
                        &nbsp;&nbsp;策略名称:&nbsp;
                        <div class="layui-input-inline ">
                            <input name="name" class="layui-input" size="15"/>
                        </div>
                    </div>&nbsp;
                    <div class="layui-inline">
                        &nbsp;&nbsp;黑名单类型:&nbsp;
                        <div class="layui-inline" style="width: 150px">
                            <ht:herocodeselect sortCode="black_pool"  name="black_Pool_Code"/>
                        </div>
                    </div>&nbsp;
                    <div class="layui-inline">
                        &nbsp;&nbsp;白名单类型:&nbsp;
                        <div class="layui-inline" style="width: 150px">
                            <ht:herocodeselect sortCode="white_pool"  name="white_Pool_Code"/>
                        </div>
                    </div>&nbsp;
                    <div class="layui-inline">
                        &nbsp;&nbsp;敏感字类型:&nbsp;
                        <div class="layui-inline" style="width: 150px">
                            <ht:herocodeselect sortCode="sensitive_word_pool"  name="sensitive_Word_Pool_Code"/>
                        </div>
                    </div>&nbsp;
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
</div>

</body>
<script type="text/javascript">
    var $ ;
    layui.extend({tableExt: '/layuiadmin/extends/tableExt'}).use(['tableExt'], function () {
        var table = layui.tableExt;
        $ = layui.$;
        table.render({
            url: '/admin/business_InterceptStrategyList'
            ,cols: [[
                {checkbox: true},
                {field: 'name', title: '策略名称',minWidth: 180, width:180},
                {field: 'faild_Type_Code_name',title: '失败类型',minWidth: 180,templet:function (d) {
                        return handleData(d.faild_Type_Code_name);
                    }},
                {field: 'black_Pool_Code_name',title: '黑名单类型',minWidth: 180,templet:function (d) {
                        return handleData(d.black_Pool_Code_name);
                    }},
                {field: 'black_Pool_Code_name',title: '外部黑名单',minWidth: 180,templet:function (d) {
                        return handleData(d.external_Black_Pool_name);
                    }},
                {field: 'white_Pool_Code_name',title: '白名单类型',minWidth: 180,templet:function (d) {
                        return handleData(d.white_Pool_Code_name);
                    }},
                {field: 'sensitive_Word_Pool_Code_name',title: '敏感字类型',minWidth: 180,templet:function (d) {
                        return handleData(d.sensitive_Word_Pool_Code_name);
                    }},
                // {field: 'areas',title: '限制地域',minWidth: 100,templet:function (d) {
                //         var a = "<button class=\"layui-btn layui-btn-xs\" title=\"详情\" onclick=\"areaDetail('"+d.areas+"')\" >地域详情</button>";
                //         return a;
                //     }},
                {field: 'remark', title: '备注', minWidth: 80},
                {field: 'create_Date', title: '创建日期', minWidth: 170}
            ]]
        });
    });

    //地域详情
    function areaDetail(areas) {
        if(!areas){
            layer.alert("无数据！");
            return ;
        }
        $.ajax({
            type: 'POST',
            url: '/admin/business_getAreaNameByAreaCode',
            dataType: 'json',
            data: {'code':areas},
            success: function (d) {
                if(d.code == 0){
                    layer.alert(d.data);
                    return false;
                }
            }
        });
    }
</script>