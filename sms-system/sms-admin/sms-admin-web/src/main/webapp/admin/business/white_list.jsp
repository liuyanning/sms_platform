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
                    <table>
                        <tr>
                            <td class="layui-inline">
                                企业名称：
                            <td class="layui-inline">
                                <ht:heroenterpriseselect  name="enterprise_No"  id="enterprise_No"/>
                            </td>
                            </td>
                            <td class="layui-inline">&nbsp;&nbsp;&nbsp;&nbsp;</td>
                            <td class="layui-inline">
                                手机：
                                <td class="layui-inline">
                                    <input name="phone_No" class="layui-input layui-input-sm"/>
                                </td>
                            </td>
                            <td class="layui-inline">&nbsp;&nbsp;&nbsp;&nbsp;</td>
                            <td class="layui-inline">
                                <button class="layui-btn layui-btn-sm" type="submit" lay-submit="" lay-filter="reload">搜索
                                </button>
                            </td>
                        </tr>
                        <tr>
                            <td><font color="red" size="4">说明：添加或修改5分钟生效</font></td>
                        </tr>
                    </table>
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
      url: '/admin/business_whiteList'
      ,cols: [[
        {checkbox: true},
        {field: 'phone_No', title: '手机号码', width: 150, width: 150},
        {
          title: '白名单类型', width: 150, width: 150, templet: function (d) {
            return handleData(d.pool_Code_name);
          }
        },
        {
          title: '企业编号/名称', width: 200, width: 180, templet: function (d) {
            return d.enterprise_No + "<br>" + (handleData(d.enterprise_No_ext.name));
          }
        },
        {field: 'create_User', title: '创建人', width: 150},
        {field: 'remark', title: '备注', minWidth: 80},
        {field: 'create_Date', title: '创建日期', width: 170}
      ]]
    });
  });
</script>