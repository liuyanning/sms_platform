<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/common/common.jsp" %>
<%@ include file="/common/layui_head.html" %>
<%@ include file="/common/country_operator.jsp" %>
<body>
<div class="layui-fluid">
	<div class="layui-col-md12">
	    <div class="layui-card">
	        <form class="layui-form layui-card-header layuiadmin-card-header-auto" id="layuiForm" onsubmit="return false;">
				<div class="layui-inline">
					&nbsp;&nbsp;国家&nbsp;
					<div class="layui-input-inline">
						<ht:herocodeselect sortCode="country" name="country_Number" id="country_Number" valueField="Value"/>
					</div>
				</div>&nbsp;&nbsp;
				<div class="layui-inline">
					&nbsp;&nbsp;运营商&nbsp;
					<div class="layui-input-inline">
						<ht:countryoperatorselect id="operator" name="operator" />
					</div>
				</div>&nbsp;&nbsp;
	            <div class="layui-inline">
	                &nbsp;&nbsp;消息类型&nbsp;
	                <div class="layui-inline">
	                    <ht:herocodeselect sortCode="message_type_code"  name="message_Type_Code"/>
	                </div>
	            </div>
	            <div class="layui-inline">
	            &nbsp;&nbsp;    提交状态&nbsp;
	                <div class="layui-input-inline" style="width: 100px">
	                    <ht:herocodeselect sortCode="034" name="submit_Status_Code"/>
	                </div>
	            </div>&nbsp;&nbsp;
	            <div class="layui-inline">
	                &nbsp;&nbsp;手机号码&nbsp;
	                <div class="layui-input-inline">
	                    <input name="phone_No" class="layui-input"/>
	                </div>
	            </div>&nbsp;&nbsp;
	            <div class="layui-inline">
	                &nbsp;&nbsp;批次号&nbsp;
	                <div class="layui-input-inline">
	                    <input name="msg_Batch_No"   class="layui-input"/>
	                </div>
	            </div>&nbsp;&nbsp;
	            <div class="layui-inline">
	                &nbsp;&nbsp;短信内容&nbsp;
	                <div class="layui-input-inline">
	                    <input name="content" class="layui-input"/>
	                </div>
	            </div>&nbsp;&nbsp;
	            <div class="layui-inline">
	                &nbsp;&nbsp;创建时间&nbsp;
	                <div class="layui-inline">
	                    <input name="minSubmitDate" id="minSubmitDate" class="layui-input layui-input-sm"/>
	                </div>
	                <div class="layui-inline">--</div>
	                <div class="layui-inline">
	                    <input name="maxSubmitDate" id="maxSubmitDate"  class="layui-input layui-input-sm"/>
	                </div>
	            </div>&nbsp;&nbsp;
	            <div class="layui-inline">
	                <button class="layui-btn layui-btn-sm" type="submit" lay-submit="" lay-filter="reload">搜索
	                </button>
	            </div>
	        </form>
	        <div class="layui-form layui-border-box layui-table-view">
	            <div class="layui-card-body">
<%--	                <blockquote class="layui-elem-quote" id="statistics" style="padding: 10px;"></blockquote>--%>
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

<c:forEach items="${ADMIN_USER.limits}" var="i" varStatus="st">
    <c:if test="${i.code=='006022002'}">
        <input id="limitTypeInput" value="006022002" hidden />
    </c:if>
</c:forEach>
</body>
<%--<script>--%>
<script type="text/javascript">
    var $;
    layui.extend({tableExt: '/js/layui-ext/tableExt'}).use(['tableExt'], function () {
        var table = layui.tableExt;
        $ = layui.$;
        var limitTypeInput = $("#limitTypeInput").val();
		var minSubmitDate = '<c:out value="${minSubmitDate}"/>';
		layui.use('laydate', function(){
			var laydate = layui.laydate;
			var today = new Date();
			laydate.render({     //创建时间选择框
				elem: '#minSubmitDate' //指定元素
				,type:'datetime'
				,trigger : 'click'
				,value: minSubmitDate?minSubmitDate:new Date(today.getFullYear(),today.getMonth(),today.getDate())
			});
			laydate.render({     //创建时间选择框
				elem: '#maxSubmitDate' //指定元素
				,type:'datetime'
				,trigger : 'click'
				,value: new Date(today.getFullYear(),today.getMonth(),today.getDate(),23,59,59)
			});
		});
        table.render({
            url: '/admin/sended_submitList',
            height: 'full-150',
            where:{
				'minSubmitDate':$("#minSubmitDate").val()==''? minSubmitDate:$("#minSubmitDate").val(),
                'maxSubmitDate':$("#maxSubmitDate").val()
            },
            cols: [[
                {checkbox: true},
                {title: '企业用户',templet:function (d) {
					return !d.enterprise_User_Id_ext ? '---' : handleData(d.enterprise_User_Id_ext.real_Name)
							+"("+handleData(d.enterprise_User_Id_ext.user_Name)+")";
                }, width: 150},
            	{title: '手机号/批次号',width: 170,templet:function (d) {
						return handleData(d.phone_No)+"<br><a href='javascript:;' lay-event='{\"type\":\"dialogNoBtnCustomerArea\",\"url\":\"/admin/sended_submitDetails?" +
								"id="+d.id+"&submit_Date_Str="+d.submit_Date+"\",\"width\":\"70%\",\"height\":\"90%\",\"title\":\"详情\"}' style='color: #01aaed;text-decoration-line: underline;'>"+handleData(d.msg_Batch_No)+"</a>";
					}},
				{field: 'content', title: '内容',minWidth: 300},
				{ title: '提交状态',templet:function (d) {
                    var resTime = d.submitResponseTime||0;
                    var color = "#06b832";
                    if(resTime>100 && resTime<=200){
                      color="#ebc207";
                    }
                    if(resTime>200){
                      color="red";
                    }
                    var a = handleData(d.submit_Status_Code_name);
                    a += '/<font color='+color+'>'+resTime+'ms</font><br>';
                    a += handleData(d.submit_Date);
                    return a;
                }, width: 170},
                { title: '运营商',templet:function (d) {
                  return handleData(d.operator)+'/'+handleData(d.area_Name);
                }, width: 220},
                { title: '类型/字数/长消息/序列',templet:function (d) {
                        var a = handleData(d.message_Type_Code_name);
                        a += '/'+handleData(d.content_Length);
                        a += "/"+handleData(d.is_LMS_name)+"/"+handleData(d.sequence);
                        return a;
                }, width: 170},
                {field: 'create_Date', title: '创建时间',width: 170}
            ]],
            done: function (res) {
                layui.$("#statistics").html("");
                layui.use('element', function() {
                    var element = layui.element;
                    element.init();
                });
            }
        });
    });


    function getFormData() {
        return $("#layuiForm").serialize();
    }

</script>