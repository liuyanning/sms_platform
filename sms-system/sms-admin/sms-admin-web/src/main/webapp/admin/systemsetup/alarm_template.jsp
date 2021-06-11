<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/admin/common/common.jsp" %>
<%@ include file="/admin/common/layui_head.html" %>
<body>
<div class="layui-fluid">
    <div class="layui-row layui-col-space15">
        <div class="layui-col-md12">
            <div class="layui-card">
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
            height: 'full-50',
            elem: '#list_table',
            url: '/admin/alarm_sms_template/business_codeListBySortCode',
            cols: [[
                {field: 'name', title: '告警模板名称：', minWidth:300 },
                {field: 'value', title: '告警模板样式：', minWidth: 300, edit: 'text'}
            ]],page: false
        });
        var $ = layui.$;
        //监听单元格编辑
        table.on('edit(list_table)', function(obj){
            var value = obj.value, //得到修改后的值
                data = obj.data, //得到所在行所有键值
                field = obj.field; //得到字段

            var queryButtonUrl = '/admin/business_editCode';
            $.ajax({
                url: queryButtonUrl,
                type: 'POST',
                data: {id:data.id,value:value},
                dataType:'json',
                success: function (res) {
                    if (res.code == '0') {

                    } else {
                        layer.msg(res.msg);
                    }
                }
            });
        });
    });



</script>