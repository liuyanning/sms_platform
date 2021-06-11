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
							&nbsp;&nbsp;企业名称&nbsp;
							<div class="layui-inline" style="width: 200px">
								<c:if test="${ADMIN_USER.roles.get(0).code=='Business'}">
									<ht:heroenterpriseselect name="no" adminUserId="${ADMIN_USER.id}"/>
								</c:if>
								<c:if test="${ADMIN_USER.roles.get(0).code!='Business'}">
									<ht:heroenterpriseselect name="no"/>
								</c:if>
							</div>
						</div>
						<div class="layui-inline">
							&nbsp;&nbsp;可用金额&nbsp;
							<div class="layui-inline">
								<input type="number" step="0.0001" min="-999999999.9999" max="999999999.9999"  name="min_Available_Money" class="layui-input layui-input-sm" size="5" />
							</div>
							--
							<div class="layui-inline">
								<input type="number" step="0.0001" min="-999999999.9999" max="999999999.9999" name="max_Available_Money" class="layui-input layui-input-sm" size="5" />
							</div>
						</div>
						<div class="layui-inline">
							&nbsp;&nbsp;状态&nbsp;
							<div class="layui-inline" style="width: 100px">
								<ht:herocodeselect sortCode="004" name="status"/>
							</div>
						</div>
						<div class="layui-inline">
							&nbsp;&nbsp;认证状态&nbsp;
							<div class="layui-inline" style="width: 100px">
								<ht:herocodeselect sortCode="authentication_State" name="authentication_State_Code" />
							</div>
						</div>
						<div class="layui-inline">
							<button class="layui-btn layui-btn-sm" type="submit" lay-submit="" lay-filter="reload">搜索
							</button>
						</div>
					</form>
					<div class="layui-form layui-border-box layui-table-view">
						<div class="layui-card-body">
							<blockquote class="layui-elem-quote" id="statistics" style="padding: 10px;"></blockquote>
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
           	 url: '/admin/enterprise_list'
            ,height: 'full-170'
            ,cols: [[
                {field: 'id', title: 'ID', width: 70, sort: true, type: 'checkbox'}
				, {title: '企业名称/企业编号', minWidth:180,templet:function (d) {
						return handleData(d.name)+"<br>"+handleData(d.no);
					}}
				, {title: '状态/认证状态', width:180,templet:function (d) {
						return handleData(d.status_name)+"<br>"+handleData(d.authentication_State_Code_name);
					}}
				, {title: '余额', width:180,templet:function (d) {
						return "¥ "+handleData(d.available_Amount);
					}}
				, {title: '已消费', width:180,templet:function (d) {
						return "¥ "+handleData(d.used_Amount) + "=" + handleData(d.sent_Count) +"条";
					}}
                , {field: 'create_Date', title: '创建时间', width: 200}
            ]]
        ,done: function(res){
                queryTotalData();
            }
        });
    });

    function queryTotalData(){
        var queryUrl = '/admin/enterprise_queryEnterpriseListTotalData?';
        $.ajax({
            type: 'post', // 提交方式 get/post
            url: queryUrl+$("#layuiForm").serialize(), // 需要提交的 url
            dataType: 'json',
            // data: data,
            success: function (res) { // data 保存提交后返回的数据，一般为 json 数据
                var sent_Count = res.data.sent_Count==null?0:res.data.sent_Count;
                var available_Amount = res.data.available_Amount==null?0:res.data.available_Amount;
                var used_Amount = res.data.used_Amount==null?0:res.data.used_Amount;
                layui.$("#statistics").html("共发送:"+sent_Count+"条&nbsp;|&nbsp;余额:"+available_Amount+"元&nbsp;|&nbsp;消费:"+used_Amount+"元");
            }
        })
        return false; // 必须返回false，否则表单会自己再做一次提交操作，并且页面跳转
    }

    function getFormData() {
        return $("#layuiForm").serialize();
    }
</script>