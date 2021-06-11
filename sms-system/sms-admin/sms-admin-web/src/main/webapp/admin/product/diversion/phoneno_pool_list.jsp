<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/admin/common/common.jsp" %>
<%@ include file="/admin/common/layui_head.html" %>
<script src="/js/jquery-3.4.1.min.js"></script>
<body>
<div class="layui-fluid">
	<div class="layui-row layui-col-space15">
		<div class="layui-col-md12">
			<div class="layui-card">
				<form class="layui-form layui-card-header layuiadmin-card-header-auto" onsubmit="return false;">
					<input value="<c:out value="${productChannelsId}"></c:out>" name="productChannelsId" id="product_Channels_Id" type="hidden" />
					<div class="layui-inline">
						&nbsp;&nbsp;号码&nbsp;
						<div class="layui-input-inline">
							 <input name="strategy_Rule" class="layui-input" class="layui-input" />
						</div>
					</div>&nbsp;&nbsp;
					<div class="layui-inline">
						&nbsp;&nbsp;状态&nbsp;
						<div class="layui-input-inline">
							 <ht:herocodeselect sortCode="state" name="status_Code" />
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
			url: '/admin/product/diversionPhoneNoPoolList',
			where: {
				"product_Channels_Id":'<c:out value="${productChannelsId}"></c:out>'
			},
			cols: [[
				{checkbox: true},
				{field: 'strategy_Rule', title: '号码',minWidth: 180,templet:function (d) {
                        var json = JSON.parse(d.strategy_Rule);
                        return json.callerNo;
                    }},
				{field: 'strategy_Rule', title: '随机位数',minWidth: 180,templet:function (d) {
                        var json = JSON.parse(d.strategy_Rule);
                        return json.appendRandomSize;
                    }},
				{field: 'status_Code_name', title: '状态', minWidth: 90},
				{field: 'weight', title: '权重', minWidth: 90},
				{field: 'description', title: '描述', minWidth: 100},
				{field: 'remark', title: '备注', minWidth: 100},
				{field: 'create_Date', title: '创建日期', width: 200}
			]]
		});
	});
</script>