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
					<input value="${productChannelsId}" name="product_Channels_Id" id="product_Channels_Id" hidden />
					<input value="${channelTypeCode}" name="channel_Type_Code" id="channel_Type_Code" hidden />
					<input value="${limitCode}" name="limitCode" id="limitCode" hidden />
					<div class="layui-inline">
						&nbsp;&nbsp;签名/子编码&nbsp;
						<div class="layui-input-inline">
							<input name="strategy_Rule" class="layui-input"/>
						</div>
					</div>&nbsp;&nbsp;
					<div class="layui-inline">
						<button class="layui-btn layui-btn-sm" type="submit" lay-submit="" lay-filter="reload">搜索
						</button>
					</div>
					<div class="layui-inline">
						<font color="red" size="4">&ensp;说明：添加或修改5分钟生效</font>
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
			url: '/admin/product/productChannelsDiversionSignatureList',
			where: {
				"product_Channels_Id":'${productChannelsId}'
			},
			cols: [[
				{checkbox: true, width: 150},
				{title: '签名',minWidth: 150,templet:function (d) {
						var json = JSON.parse(d.strategy_Rule);
						return json.signature;
					}},
				{title: '子编码', minWidth: 150, templet: function(d){
						var json = JSON.parse(d.strategy_Rule);
						return json.subCode;
					}},
				// {field: 'description', title: '描述', minWidth: 100},
				// {field: 'remark', title: '备注', minWidth: 100},
				{title: '状态', minWidth: 150, templet: function(d){
						return handleData(d.status_Code_name);
					}},
				{field: 'create_Date', title: '创建日期', width: 200}
			]]
		});
	});
	//导出用的勿删
	function getFormData() {
		return 'productChannelsId='+$("#product_Channels_Id").val();
	}
</script>