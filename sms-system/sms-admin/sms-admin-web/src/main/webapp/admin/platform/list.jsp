<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/admin/common/common.jsp"%>
<%@ include file="/admin/common/layui_head.html"%>
<body>
	<div class="layui-fluid">
		<div class="layui-row layui-col-space15">
			<div class="layui-col-md12">
				<div class="layui-card">
					<form id="layuiForm" class="layui-form layui-card-header layuiadmin-card-header-auto"
						onsubmit="return false;">
						<div class="layui-inline">
							&nbsp;&nbsp;平台名称&nbsp;
							<div class="layui-inline" style="width: 200px">
								<ht:heroplatformselect name="platform_No" />
							</div>
						</div>
						<div class="layui-inline">
							&nbsp;&nbsp;统计状态&nbsp;
							<div class="layui-input-inline" style="width: 50%;">
								<ht:herocodeselect sortCode="006" selected="" name="statistics_Status"/>
							</div>
						</div>
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
    var $;
    layui.extend({tableExt: '/layuiadmin/extends/tableExt'}).use(['tableExt'], function () {
        var table = layui.tableExt;
        $ = layui.$;
        table.render({
           	 url: '/admin/platform_list'
            ,height: 'full-120'
            ,cols: [[
                {field: 'id', title: 'ID', width: 70, sort: true, type: 'checkbox'}
				, {title: '平台名称/平台编号', width:180,templet:function (d) {
						return handleData(d.platform_Name)+"<br>"+handleData(d.platform_No);
					}}
				, {field: 'sign_Key', title: '秘钥', minWidth: 100}
				, {field: 'statistics_Status_name', title: '是否统计', minWidth: 100}
                , {field: 'remark', title: '备注', minWidth: 100}
                , {field: 'create_Date', title: '创建时间',}
            ]]
        });
    });

    function getFormData() {
        return $("#layuiForm").serialize();
    }
</script>