<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/admin/common/common.jsp" %>
<%@ include file="/admin/common/layui_head.html" %>
<body>
<div class="layui-fluid">
    <div class="layui-row layui-col-space15">
        <div class="layui-col-md12">
            <div class="layui-card">
                <form class="layui-form layui-card-header layuiadmin-card-header-auto" onsubmit="return false;">
                   <div class="layui-inline">
                       &nbsp;&nbsp;企业名称&nbsp;
                        <div class="layui-input-inline">
                             <ht:heroenterpriseselect  name="enterprise_No" />
                        </div>
                    </div> 
                    <div class="layui-inline">
                        &nbsp;&nbsp;模板名称&nbsp;
                        <div class="layui-input-inline">
                            <input name="template_Name" class="layui-input"/>
                        </div>
                    </div>&nbsp;&nbsp;
                    <div class="layui-inline">
                        &nbsp;&nbsp;行业类型&nbsp;
                        <div class="layui-input-inline">
                            <ht:herocodeselect sortCode="trade" name="template_Type" />
                        </div>
                    </div>&nbsp;&nbsp;
                    <div class="layui-inline">
                        &nbsp;&nbsp;审核状态&nbsp;
                        <div class="layui-input-inline">
                            <ht:herocodeselect sortCode="templateCheckStatus" selected="0" name="approve_Status" />
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
</div>

</body>
<script>
    layui.extend({tableExt: '/layuiadmin/extends/tableExt'}).use(['tableExt'], function () {
        var table = layui.tableExt;
        table.render({
            url: '/admin/enterprise_smsTemplateList',
            where:{'approve_Status':'0'},
            cols: [[
                {checkbox: true},
                {title: '企业编号/名称',width: 300, width:180,templet:function (d) {
                        return d.enterprise_No+"<br>"+
                            (!d.enterprise_No_ext?'---':handleData(d.enterprise_No_ext.name));
                    }},
                {title: '企业用户',width: 150, templet:function (d) {
                  return !d.enterprise_User_Id_ext ? '---' : handleData(d.enterprise_User_Id_ext.real_Name)
                      +"("+handleData(d.enterprise_User_Id_ext.user_Name)+")";}},
                {title: '行业类型/状态', width: 130,templet:function (d) {
                  return handleData(d.template_Type_name)+"<br>"+d.approve_Status_name;
                }},
                {title: '模板名称/内容', minWidth: 300, templet: function (d) {
                  return handleData(d.template_Name)+"<br>"+d.template_Content;
                  }},
                {field: 'description', title: '描述', width: 150},
                {field: 'create_Date', title: '创建日期', width: 200,}
            ]]
        });
    });
</script>