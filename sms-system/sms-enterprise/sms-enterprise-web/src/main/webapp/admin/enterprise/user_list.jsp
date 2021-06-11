<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/common/common.jsp" %>
<%@ include file="/common/layui_head.html" %>
<body>
<div class="layui-fluid">
	<div class="layui-row layui-col-space15">
		<div class="layui-col-md12">
			<div class="layui-card">
				<form class="layui-form layui-card-header layuiadmin-card-header-auto" onsubmit="return false;">
					<table>
						<tr>
							<td class="layui-inline">
								&nbsp;&nbsp;真实姓名&nbsp;
							<td class="layui-inline">
								<input name="real_Name" class="layui-input layui-input-sm" size="15"/>
							</td>
							<td class="layui-inline">&nbsp;&nbsp;&nbsp;&nbsp;</td>
							<td class="layui-inline">
								&nbsp;&nbsp;登录名&nbsp;
							<td class="layui-inline">
								<input name="user_Name" class="layui-input layui-input-sm" size="15"/>
							</td>
							<td class="layui-inline">&nbsp;&nbsp;&nbsp;&nbsp;</td>
							<td class="layui-inline">
								&nbsp;&nbsp;状态&nbsp;
								<td class="layui-inline" style="width: 100px">
									<ht:herocodeselect name="status" sortCode="003" />
								</td>
							<td class="layui-inline">&nbsp;&nbsp;&nbsp;&nbsp;</td>
							<td class="layui-inline">
								<button class="layui-btn layui-btn-sm" type="submit" lay-submit="" lay-filter="reload">搜索
								</button>
							</td>
						</tr>
					</table>
				</form>
				<div class="layui-form layui-border-box layui-table-view">
					<div class="layui-card-body">
						<table class="layui-hide" id="list_table" lay-filter="list_table"></table>
						<script type="text/html" id="table-toolbar">
                            <div class="layui-btn-container">
								<%@include file="/common/button_action_list.jsp" %>
							</div>
						</script>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

<div id="dialogId" hidden>
	<form class="layui-form" onsubmit="return false;" style="padding: 30px 1px 0 0;">
		<div class="layui-form-item">
			<div class="layui-input-inline" style="width: 50px">
				<label class="layui-form-label" style="width: 50px"></label>
			</div>
			<div class="layui-input-inline" style="width: 450px">
                <input hidden id="httpId" value="${netwayHttpIp.value}" />
				<label class="layui-form-label" style="width: 50px"></label>
				<textarea id="areaId" style="width: 550px; margin: 0px; height: 400px;"></textarea>
			</div>
		</div>
	</form>
</div>
</body>
<script>
	var $;
	layui.extend({tableExt: '/js/layui-ext/tableExt'}).use(['tableExt'], function () {
		var table = layui.tableExt;
		$ = layui.$;
		table.render({
			url: '/admin/enterprise_userList',
			cols: [[
				{checkbox: true, width: 50},
				{field: 'real_Name', title: '真实姓名'},
				{field: 'user_Name', title: '登录名'},
				{field: 'status_name', title: '用户状态'},
				{title: '余额', minWidth:110,templet:function (d) {
						return d.settlement_Currency_Type_Code_name =="USD"?'$':'¥ ' +handleData(d.available_Amount);
					}},
				{title: '已消费', minWidth:135,templet:function (d) {
						return (d.settlement_Currency_Type_Code_name =="USD"?'$':'¥ ' +handleData(d.used_Amount)) + "=" + handleData(d.sent_Count) +"条";
					}},
				{field: 'create_Date', title: '创建时间'},
                {title: '接入信息',templet:function (d) {
                	var a = "<button class=\"layui-btn layui-btn-xs\" title=\"详情\" onclick=\"cmppDetail('"+d.user_Name+"','"+d.tcp_Password+"','"+d.enterprise_No+"','"+d.http_Sign_Key+"','"+d.tcp_User_Name+"','"+d.tcp_Submit_Speed+"','"+d.tcp_Max_Channel+"')\" >接入信息</button>";
                    return a;
                    }}
			]]
		});
	});

	 //接入信息详情
    function cmppDetail(userName,password,enterprise_No,http_Sign_Key,tcp_User_Name,tcpSubmitSpeed,tcpMaxChannel) {
		var area = $("#areaId");//初始值
		area.append("HTTP接入信息");
		area.append("\n");
		area.append("IP地址："+$("#httpId").val());
		area.append("\n");
		area.append("端口号："+'${netwayHttpPort.value}');
		area.append("\n");
		area.append("企业编号："+enterprise_No);
		area.append("\n");
		area.append("账号："+userName);
		area.append("\n");
		area.append("秘钥："+http_Sign_Key);
		area.append("\n");
		area.append('接口文档：${apiDocUrl}');
		area.append("\n");
		area.append("\n");
		area.append("=============================================================");
		area.append("\n");
		area.append("\n");
		area.append("CMPP2接入信息");
		area.append("\n");
		area.append("IP地址："+'${netwayCmppIp.value}');
		area.append("\n");
		area.append("端口号："+'${netwayCmppPort.value}');
		area.append("\n");
		area.append("速度："+(tcpSubmitSpeed!='null'?tcpSubmitSpeed:200));
		area.append("\n");
		area.append("连接数："+(tcpMaxChannel!='null'?tcpMaxChannel:3));
		area.append("\n");
		area.append("接入号："+tcp_User_Name);
		area.append("\n");
		area.append("账号："+tcp_User_Name);
		area.append("\n");
		area.append("密码："+password);
		area.append("\n");
		area.append("\n");
		area.append("=============================================================");
		area.append("\n");
		area.append("\n");
		area.append("SGIP接入信息");
		area.append("\n");
		area.append("IP地址："+'${netwayCmppIp.value}');
		area.append("\n");
		area.append("端口号："+'${netwaySgipPort.value}');
		area.append("\n");
		area.append("速度："+(tcpSubmitSpeed!='null'?tcpSubmitSpeed:200));
		area.append("\n");
		area.append("连接数："+(tcpMaxChannel!='null'?tcpMaxChannel:3));
		area.append("\n");
		area.append("接入号："+tcp_User_Name);
		area.append("\n");
		area.append("账号："+tcp_User_Name);
		area.append("\n");
		area.append("密码："+password);
		area.append("\n");
		area.append("\n");
		area.append("=============================================================");
		area.append("\n");
		area.append("\n");
		area.append("SMGP接入信息");
		area.append("\n");
		area.append("IP地址："+'${netwayCmppIp.value}');
		area.append("\n");
		area.append("端口号："+'${netwaySmgpPort.value}');
		area.append("\n");
		area.append("速度："+(tcpSubmitSpeed!='null'?tcpSubmitSpeed:200));
		area.append("\n");
		area.append("连接数："+(tcpMaxChannel!='null'?tcpMaxChannel:3));
		area.append("\n");
		area.append("接入号："+tcp_User_Name);
		area.append("\n");
		area.append("账号："+tcp_User_Name);
		area.append("\n");
		area.append("密码："+password);
		area.append("\n");
		area.append("\n");
		area.append("=============================================================");
		area.append("\n");
		area.append("\n");
		area.append("SMPP接入信息");
		area.append("\n");
		area.append("IP地址："+'${netwayCmppIp.value}');
		area.append("\n");
		area.append("端口号："+'${netwaySmppPort.value}');
		area.append("\n");
		area.append("速度："+(tcpSubmitSpeed!='null'?tcpSubmitSpeed:200));
		area.append("\n");
		area.append("连接数："+(tcpMaxChannel!='null'?tcpMaxChannel:3));
		area.append("\n");
		area.append("接入号："+tcp_User_Name);
		area.append("\n");
		area.append("账号："+tcp_User_Name);
		area.append("\n");
		area.append("密码："+password);
        layer.open({
            area: ['650px', '550px'],
            title:'接入信息',
            type: 1,
            content: $("#dialogId"),
            cancel: function(index, layero){
                $("#areaId").empty();//清空
                $("#areaId").append(area);//还原
                layer.close(index)
                return false;
            }
        });
    }
</script>