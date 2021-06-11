<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/admin/common/common.jsp" %>
<%@ include file="/admin/common/layui_head.html" %>
<%@ include file="/admin/common/country_operator.jsp" %>
<body>
<div class="layui-fluid">
	<div class="layui-row layui-col-space15">
		<div class="layui-col-md12">
			<div class="layui-card">
				<form class="layui-form layui-card-header layuiadmin-card-header-auto" onsubmit="return false;">
					<input value="<c:out value="${product_No}"></c:out>" name="product_No" id="product_No" type="hidden" />
					<input value="<c:out value="${channelTypeCode}"></c:out>" name="channel_Type_Code" id="channel_Type_Code" type="hidden" />
					<input value="<c:out value="${limitCode}"></c:out>" name="limitCode" id="limitCode" type="hidden" />
                    <div class="layui-input-inline">
                        <ht:herocodeselect sortCode="country" name="country_Number" id="country_Number" valueField="Value" />
                    </div>--<div class="layui-input-inline">
                    <ht:countryoperatorselect id="operator" name="operator" />
                </div>
					<div class="layui-inline">
						&nbsp;&nbsp;消息类型&nbsp;
						<div class="layui-input-inline">
							<ht:herocodeselect sortCode="message_type_code" name="message_Type_Code"/>
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
			url: '/admin/product/productChannelsList',
			where: {
				'channel_Type_Code': '${channelTypeCode}',
				"product_No":'${product_No}'
			},
			cols: [[
				{checkbox: true},
				{field: 'product_No', title: '产品编号', width: 200},
				{field: 'weight', title: '通道权重', width: 180},

				{field: 'channel_No_ext',title: '通道',width: 220,templet:function (d) {
						return !d.channel_No_ext?'---':handleData(d.channel_No_ext.name+'('+d.channel_No_ext.status_Code_name+')');
					}},
                {title: '国家', minWidth: 180, templet: function(d){
                        return handleData(d.country_Number_name);
                    }},
                {field: 'operator',title: '运营商', minWidth: 180},
				{field: 'message_Type_Code_name', title: '消息类型', minWidth: 100},
				{field: 'create_Date', title: '创建日期', width: 200},
				{title: '操作',minWidth: 150,templet:function (d) {
						var a = "<button class=\"layui-btn layui-btn-xs\" lay-event='{\"type\":\"selectedTodo\",\"url\":\"/admin/product/delProductChannels?id="+d.id+"\",\"title\":\"确定要删除吗？\"}'  title=\"删除\" >删除</button>";
						return a;
					}},
			]]
		});
	});
</script>