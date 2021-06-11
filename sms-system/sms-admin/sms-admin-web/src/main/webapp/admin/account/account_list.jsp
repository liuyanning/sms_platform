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
                        真实姓名&nbsp;
                        <div class="layui-inline">
                            <input name="real_Name" class="layui-input layui-input-sm"/>
                        </div>
                    </div>
                    &nbsp;&nbsp;
                    <div class="layui-inline">
                        账号状态&nbsp;
                        <div class="layui-inline" style="width: 100px">
                            <ht:herocodeselect sortCode="003" name="status"/>
                        </div>
                    </div>
                    &nbsp;&nbsp;
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
            url: '/admin/account_list',
            cols: [[
                {checkbox: true},
                {field: 'real_Name', title: '真实姓名',minWidth: 120},
                {field: 'user_Name', title: '登录名',minWidth: 120},
                {field: 'status_name', title: '状态', minWidth: 50},
                {title: '谷歌验证码', minWidth: 100,templet:function (d) {
                    var a = "<button class=\"layui-btn layui-btn-xs\" " +
                        "lay-event='{\"type\":\"dialogNoBtnCustomerArea\",\"url\":\"https://wenhairu.com/static/api/qr/?size=300&text=otpauth://totp/" +
                        d.user_Name+"@"+'${platformAdminTags}'+"?secret="+d.google_ID_Key+
                        "\",\"width\":\"305px\",\"height\":\"350px\",\"title\":\""+d.user_Name+"\"}' title=\"谷歌二维码\" >谷歌二维码</button>";
                    return a;
                }},
                {field: 'current_Login_Time', title: '上次登录时间',minWidth: 170},
                {field: 'current_Login_IP', title: '上次登录IP',minWidth: 170},
                {field: 'login_Faild_Count', title: '失败次数', minWidth: 100},
                {field: 'login_Faild_Lock_Count', title: '失败锁定次数',minWidth: 100},
                {field: 'create_Date', title: '创建时间',minWidth: 170},
                {field: 'description', title: '描述',minWidth: 150},
                {field: 'remark', title: '备注',minWidth: 150}
            ]]
        });

    });
</script>