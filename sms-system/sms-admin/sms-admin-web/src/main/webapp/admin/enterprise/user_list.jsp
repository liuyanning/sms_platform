<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/admin/common/common.jsp" %>
<%@ include file="/admin/common/layui_head.html" %>
<body>
	<div class="layui-fluid">
		<div class="layui-row layui-col-space15">
			<div class="layui-col-md12">
				<div class="layui-card">
					<form id="layuiForm" class="layui-form layui-card-header layuiadmin-card-header-auto" onsubmit="return false;">
						<input value='<c:out value="${limitCode}"></c:out>' name="limitCode" type="hidden" />
						<div class="layui-inline">
							&nbsp;&nbsp;企业名称&nbsp;
							<div class="layui-inline" style="width: 200px">
								<c:if test="${ADMIN_USER.roles.get(0).code=='Business'}">
									<ht:heroenterpriseselect name="enterprise_No" adminUserId="${ADMIN_USER.id}"
															 layVerify="required" selected="${enterprise_No}"/>
								</c:if>
								<c:if test="${ADMIN_USER.roles.get(0).code!='Business'}">
									<ht:heroenterpriseselect name="enterprise_No" selected="${enterprise_No}"/>
								</c:if>
							</div>
						</div>
						<div class="layui-inline">
							&nbsp;&nbsp;产品&nbsp;
							<div class="layui-inline" style="width: 200px">
                                <ht:heroproductselect  selected="${enterpriseUser.product_No}" name="product_No"/>
							</div>
						</div>
						<div class="layui-inline">
							&nbsp;&nbsp;登录名&nbsp;
							<div class="layui-inline" style="width: 150px">
								<input name="user_Name" class="layui-input layui-input-sm" />
							</div>
						</div>
						<div class="layui-inline">
							&nbsp;&nbsp;TCP账户&nbsp;
							<div class="layui-inline" style="width: 150px">
								<input name="tcp_User_Name" class="layui-input layui-input-sm" />
							</div>
						</div>
						<div class="layui-inline">
							&nbsp;&nbsp;签名&nbsp;
							<div class="layui-inline" style="width: 150px">
								<input name="suffix" class="layui-input layui-input-sm" />
							</div>
						</div>
						<div class="layui-inline">
							&nbsp;&nbsp;状态&nbsp;
							<div class="layui-inline" style="width: 150px">
								<ht:herocodeselect name="status" sortCode="003" />
							</div>
						</div>
						<div class="layui-inline">
							<button class="layui-btn layui-btn-sm" type="submit" lay-submit="" lay-filter="reload">搜索</button>
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
	<!-- 接入信息 -->
	<div id="dialogId" hidden>
	    <form class="layui-form" onsubmit="return false;" style="padding: 30px 1px 0 0;">
	        <div class="layui-form-item">
	            <div class="layui-input-inline" style="width: 50px">
	                <label class="layui-form-label" style="width: 50px"></label>
	            </div>
	            <div class="layui-input-inline" style="width: 450px">
	                <input type="hidden" id="httpId" value="${netwayHttpIp.value}" />
	                <label class="layui-form-label" style="width: 50px"></label>
	                <textarea id="areaId" style="width: 550px; margin: 0px; height: 400px;"></textarea>
	            </div>
	        </div>
	    </form>
	</div>
</body>
<script type="text/javascript">
    var $;
    layui.extend({tableExt: '/layuiadmin/extends/tableExt'}).use(['tableExt'], function () {
        var table = layui.tableExt;
        $ = layui.$;
        table.render({
            url: '/admin/enterprise_userList'
            ,where:{"enterprise_No":'${enterprise_No}'}
            ,cols: [[
                {checkbox: true}
                , { title: '企业名称/企业用户', minWidth:180,templet:function (d) {
						return !d.enterprise_No_ext?'---':handleData(d.enterprise_No_ext.name)
								+"<br>"+handleData(d.real_Name);
                    }}
                , {title: '登录名/TCP', minWidth:125,templet:function (d) {
                        return handleData(d.user_Name)+"<br>"+handleData(d.tcp_User_Name);
                    }}
                , { title: '用户状态', width:100,templet:function (d) {
                        return handleData(d.status_name)+"/"+handleData(d.audit_Type_Code_name);
                    }}
                , {field: 'priority_Level_name', title: '优先级别', width:87}
                , {field: 'suffix', title: '签名', width:100}
                , { title: '产品', width:180,templet:function (d) {
                        return !d.product_No?'---': handleData(d.product_No_ext.name);
                    }}
				, {title: '余额', minWidth:110,templet:function (d) {
						return d.settlement_Currency_Type_Code_name =="USD"?'$':'¥ ' +handleData(d.available_Amount);
					}}
				, {title: '已消费', minWidth:135,templet:function (d) {
						return (d.settlement_Currency_Type_Code_name =="USD"?'$':'¥ ' +handleData(d.used_Amount)) + "=" + handleData(d.sent_Count) +"条";
					}}
                , {field: 'create_Date', title: '创建时间', width: 150}
                , {title: '接入信息', width: 90,templet:function (d) {
                        var a = "<button class=\"layui-btn layui-btn-xs\" title=\"详情\" onclick=\"cmppDetail('"+d.user_Name+"','"+d.tcp_Password+"','"+d.enterprise_No+"','"+d.http_Sign_Key+"','"+d.tcp_User_Name+"','"+d.tcp_Submit_Speed+"','"+d.tcp_Max_Channel+"')\" >接入信息</button>";
                        return a;
                    }}
                , {field: 'remark', title: '备注', width: 100}
            ]]
            ,done:function () {
                var enterprise_No = '${enterprise_No}';
                var url = "/admin/enterprise/add_user.jsp?enterprise_No=" + enterprise_No;
                var value = '{"type":"dialog","url":"' + url + '","width":"800","height":"500"}';
                $("#addButton").attr("lay-event", value);
            }
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

    function getFormData() {
        return $("#layuiForm").serialize();
    }
</script>