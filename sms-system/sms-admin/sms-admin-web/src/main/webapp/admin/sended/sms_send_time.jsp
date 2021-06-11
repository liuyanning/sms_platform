<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/admin/common/common.jsp"%>
<%@ include file="/admin/common/layui_head.html"%>
<body>
	<div class="layui-fluid">
			<div class="layui-col-md12">
				<div class="layui-card">
					<form id="layuiForm" class="layui-form layui-card-header layuiadmin-card-header-auto"
						onsubmit="return false;">
						<div class="layui-inline">
							&nbsp;&nbsp;企业名称&nbsp;
							<div class="layui-inline" style="width: 200px">
								<ht:heroenterpriseselect name="enterprise_No" />
							</div>
						</div>
						<div class="layui-inline">
							&nbsp;&nbsp;企业用户&nbsp;&nbsp;
							<div class="layui-inline" style="width: 200px">
								<ht:herocustomdataselect dataSourceType="allEnterpriseUsers" name="enterprise_User_Id" />
							</div>
						</div>
                        <div class="layui-inline">
                            &nbsp;&nbsp;消息类型&nbsp;
                            <div class="layui-inline" style="width: 200px">
                                <ht:herocodeselect sortCode="message_type_code"  name="message_Type_Code"/>
                            </div>
                        </div>
						<div class="layui-inline">
							&nbsp;&nbsp;&nbsp;手机号码&nbsp;&nbsp;
							<div class="layui-inline" style="width: 200px">
								<input name="phone_Nos" id="phone_Nos" class="layui-input" />
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
</body>
<script>
    layui.extend({tableExt: '/layuiadmin/extends/tableExt'}).use(['tableExt'], function () {
        var table = layui.tableExt;
        table.render({
            url: '/admin/sms_sentTime',
			height: 'full-115',
            cols: [[
                {field: 'id', title: 'ID',  minWidth: 30, sort: true,  type: 'checkbox'},
                {field: 'enterprise_No_ext', title: '企业名称/企业用户', width: 180, templet: function (d) {
                        return (!d.enterprise_No_ext ? '---' : handleData(d.enterprise_No_ext.name))
								+"<br>"+handleData(d.enterprise_User_Id_ext.real_Name)
								+"("+handleData(d.enterprise_User_Id_ext.user_Name)+")";
                    }
                },
				{field: 'msg_Batch_No', title: '批次号', width: 180},
				{field: 'phone_Nos', title: '手机号码',  width: 180},
				{title: '审核状态/定时时间',width: 170,templet:function (d) {
						return handleData(d.audit_Status_Code_name)+"<br>"+handleData(d.send_Time);
					}},
                {field: 'content', title: '内容', minWidth: 300},
                { title: '类型/长消息/数量',templet:function (d) {
                    var a = handleData(d.message_Type_Code_name);
                    a += "/"+handleData(d.is_LMS_name)
                    a += '<br>'+'号码:'+handleData(d.phone_Nos_Count)+'个';
                    return a;
           		}, width: 160},
                {field: 'create_Date', title: '创建时间', width: 170}
            ]]
        });
    });

</script>