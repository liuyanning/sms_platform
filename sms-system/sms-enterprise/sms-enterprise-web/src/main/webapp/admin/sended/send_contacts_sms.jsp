<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/common/common.jsp" %>
<%@ include file="/common/layui_head.html" %>
<html>
<head>
	<meta http-equiv="Content-Type" content="multipart/form-data; charset=utf-8"/>
	<script src="/js/jquery-3.4.1.min.js"></script>
	<script src="/js/jquery-form.js"></script>
	<link rel="stylesheet" type="text/css" href="/css/send_sms.css" id="send_sms_link"
		  international_telephone_code=<ht:heropageconfigurationtext code="international_telephone_code" sortCode="custom_switch" defaultValue="off"/>>
	<script src="/js/tagsinput.js" type="text/javascript" charset="utf-8"></script>
</head>
<body>
<div class="layui-fluid" id="LAY-component-nav">
	<div class="layui-row layui-col-space15">
		<div class="layui-col-md9">
			<div class="layui-card">
				<div class="layui-card-header"></div>
				<div class="layui-card-body">
					<div class="layui-fluid" layoutH="57">

						<div class="layui-form-item">
							<label class="layui-form-label">手机号码:</label>
							<div class="layui-input-inline box" style="width: 80%;">
								<div class="tagsinput-primary form-group">
									<input name="tagsinput" id="tagsinputval" class="tagsinput layui-input"
										   data-role="tagsinput" placeholder="多个号码换行或者逗号分割,最多2W个号码">
								</div>
							</div>
						</div>
						<form class="layui-form" id="subForm" action="" onsubmit="return ajaxSubmitForm();">
							<div class="layui-form-item" style="display:none;">
								<label class="layui-form-label">手机号码:</label>
								<div class="layui-input-inline" style="width: 80%;">
                                    <textarea rows="6" placeholder="(多个号码换行或者逗号分割,最多2W个号码)" class="layui-textarea"
											  name="phone_Nos" id="phone_Nos"></textarea>
								</div>
							</div>
							<div id="global_roaming" class="layui-form-item">
								<label class="layui-form-label">国际区号:</label>
								<div class="layui-input-inline">
									<input type="text" name="country_Code" autocomplete="off"
										   value="86" class="layui-input">
								</div>
								<div class="layui-form-mid layui-word-aux">最长5位字符,您所提交的号码无区号时自动拼接</div>
							</div>
							<div class="layui-form-item">
								<label class="layui-form-label">短信模板：</label>
								<div class="layui-input-inline">
									<ht:herosmstemplatetag id="selectSmsTemplateId"  enterpriseNo="${ADMIN_USER.enterprise_No}"
														   enterpriseUserId="${ADMIN_USER.id}"/>
								</div>
							</div>
							<div class="layui-form-item">
								<label class="layui-form-label">短信内容:</label>
								<div class="layui-input-inline" style="width: 80%;">
												<textarea
														placeholder="包含签名短信小于等于70个字的按70个字一条计费, 大于70个字按67字一条计费, 短信内容总字数不能超过 500字"
														class="layui-textarea" onkeyup="smsWordCount()"
														name="content" id="content"></textarea>
								</div>
							</div>
							<div class="layui-form-item">
								<label class="layui-form-label"></label>
								<div class="layui-form-mid layui-word-aux">
									<span style="width: 100%;" id="wordCount">0个字符</span>
									&nbsp;;&nbsp;&nbsp;签名: ${ADMIN_USER.suffix}
								</div>
							</div>
							<div class="layui-form-item" style="display: none">
								<label class="layui-form-label">定时发送:</label>
								<div class="layui-input-inline">
									<input type="text" name="send_Time" id="send_Time" lay-verify="datetime"
										   placeholder="yyyy-MM-dd HH:mm:ss" autocomplete="off" class="layui-input">
								</div>
								<div class="layui-form-mid layui-word-aux">(不填为即时发送)</div>
							</div>
							<div class="layui-form-item">
								<label class="layui-form-label" style="width: 8%;"></label>
								<div class="layui-input-inline">
									<button id="submit_button" class="layui-btn layui-btn-lg">&nbsp;&nbsp;&nbsp;立&nbsp;即&nbsp;发&nbsp;送&nbsp;&nbsp;&nbsp;</button>
								</div>
								<div class="layui-input-inline">
									<button type="button" id="send_Time_button" class="layui-btn layui-btn-lg">&nbsp;&nbsp;&nbsp;定&nbsp;时&nbsp;发&nbsp;送&nbsp;&nbsp;&nbsp;</button>
								</div>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>

		<div class="layui-col-md3">
			<div class="layui-card" style="overflow:auto;">
				<div class="layui-card-header">联系人</div>
				<div class="layui-card-body">
					<div>
						<button type="button" class="layui-btn layui-btn-radius" id="addPhoneNo">&nbsp;确&nbsp;&nbsp;定&nbsp;</button>
						<button type="button" class="layui-btn layui-btn-radius" id="cleanAll">&nbsp;清&nbsp;&nbsp;空&nbsp;</button>
					</div>
					<div id="treeDemo_m"></div>
				</div>
			</div>
		</div>
	</div>
</div>
<script type="text/javascript">
	$(function () {
		var internationalSwitch = $('#send_sms_link').attr('international_telephone_code');
		if('on'!=internationalSwitch){
			$('#global_roaming').remove();
		}
	});
	var layerLoadingNum = 0;
	var layerCloseLoadingNum = 0;
	layui.use(['tree', 'util'], function () {
		var $ = layui.$;
		var tree = layui.tree
				, layer = layui.layer
				, util = layui.util
		$.ajax({
			type: "GET",
			url: "/admin/contact_manager",
			dataType: 'json',
			success: function (data) {
				var treeData = '[{"title":"全部分组","id":"group_all","spread":"true","children":[';
				if (data.length > 0) {
					for (var i = 0; i < data.length; i++) {
						if (i != (data.length - 1)) {
							treeData += '{"title":"' + data[i].group_Name + '","id":"' + "group_" + data[i].id + '"},';
						} else {
							treeData += '{"title":"' + data[i].group_Name + '","id":"' + "group_" + data[i].id + '"}';
						}
					}
				}
				treeData += "]}]";
				var d = JSON.parse(treeData);
				tree.render({
					elem: '#treeDemo_m'
					, data: d
					// ,spread: true
					, showCheckbox: true  //是否显示复选框
					,id: 'contacts' //定义索引
					, click: function (obj) {
						if(obj.data.id=="group_all"){
							return;
						}
						getTreeChildren(tree,obj,false);

					},oncheck: function(obj){
						if(obj.data.id=="group_all"){
							if(obj.checked==true){
								var checkedBoxVal = obj.data.children
								$.each(checkedBoxVal, function(i, val) {
									if($("div[data-id='"+val.id+"']").children("div[lay-filter]").length==0){
										var childrenObj = {
											data:val,
											elem:$("div[data-id='"+val.id+"']")
										}
										getTreeChildren(tree,childrenObj,true);
									}
								});
							}
						}else{
							getTreeChildren(tree,obj,true);
						}
					}
				});
			}
		})
	});
	function getTreeChildren(tree,obj,checked){
		var selectNode = obj.data;  //获取当前点击的节点数据
		$.ajax({
			type: "post",
			url: '/admin/contact_listContact?pagination.pageIndex=1&pagination.pageSize=99999999&group_Id=' + selectNode.id.split("_")[1],
			dataType: 'json',
			beforeSend: function (){
				layer.load(0, {shade: false});
				layerLoadingNum++;
			},
			success: function (res) {
				var treeData = '[{"title":"' + selectNode.title + '","id":"' + selectNode.id + '","spread":"true"';
				if(checked){
					treeData += ',"checked":"true"';
				}
				treeData += ',"children":[';
				var data = res.data;
				if (data != null && data.length > 0) {
					for (var i = 0; i < data.length; i++) {
						if (i != (data.length - 1)) {
							treeData += '{"title":"' + data[i].phone_No + '-' + data[i].real_Name + '","id":"' + data[i].real_Name.replace(/\s+/g,"") + '<' + data[i].phone_No.replace(/\s+/g,"") + '>"},';
						} else {
							treeData += '{"title":"' + data[i].phone_No + '-' + data[i].real_Name + '","id":"' + data[i].real_Name.replace(/\s+/g,"") + '<' + data[i].phone_No.replace(/\s+/g,"") + '>"}';
						}
					}
				}
				treeData += "]}]";
				var d = JSON.parse(treeData);
				tree.render({
					elem: obj.elem
					, data: d
					, showCheckbox: true  //是否显示复选框
				})
			},
			error: function () {
			},
			complete:function (XMLHttpRequest,status){
				if(status=='success'){
					layerCloseLoadingNum++;
					if(layerCloseLoadingNum==layerLoadingNum){
						layer.closeAll('loading')
						layerLoadingNum = 0;
						layerCloseLoadingNum = 0;
					}
				}else{
					layerLoadingNum = 0;
					layerCloseLoadingNum = 0;
					layer.closeAll('loading')
				}
			}
		})
	}

	function smsWordCount() {
		var eSignature = "<c:out value="${ADMIN_USER.suffix}"></c:out>";
		var fullSms = $("#content").val() + eSignature;
		var splitSmsCount = 1;
		if (fullSms.length > 70) {
			splitSmsCount = fullSms.length / 67;
			if (fullSms.length % 67 != 0) {
				splitSmsCount += 1;
			}
		}
		splitSmsCount = Math.floor(splitSmsCount);
		$("#wordCount").text(fullSms.length + '个字符,拆分' + splitSmsCount + '条短信');
	}

	layui.use('form', function () {
		var form = layui.form;
		$("select[id='selectSmsTemplateId']").attr('lay-filter','onselect');
		//监听选中事件
		form.on('select(onselect)', function (data) {
			var selectContent = $("#selectSmsTemplateId").val();
			$("#content").val(selectContent);//赋值
			smsWordCount();
			form.render('select');//渲染
		});
		form.render();
		FrameWH();

		function FrameWH() {
			var h = $(window).height() - 95;
			if (h < 495) {
				h = 495
			}
			$(".layui-col-md9 .layui-card .layui-card-body").css("height", h + "px");
			$(".layui-col-md3 .layui-card .layui-card-body").css("height", h + "px");
		}

		$(window).resize(function () {
			FrameWH();
		});
	});


	function ajaxSubmitForm() {
		var content = $("#content").val();//短信标题
		if (!content) {
			layer.msg("短信内容不能为空！");
			return false;
		}

		var tagsinputval = $("#tagsinputval").val();//号码输入框
		if (!tagsinputval) {
			layer.msg("请输入发送号码！");
			return false;
		}
		//提取输入框手机号码
		var phoneNosArray = tagsinputval.split(",");
		if(phoneNosArray.length>20000){
			layer.msg("发送号码总数为: " + phoneNosArray.length +"个,大于最大可发送条数 ！");
			return false;
		}
		var dataArray = new Array();
		for(var i=0;i<phoneNosArray.length;i++){

			var phoneNo = phoneNosArray[i];
			if(phoneNo.indexOf(">") == phoneNo.indexOf("<") ){
				dataArray.push(phoneNo);
			}else if(phoneNo.indexOf(">") > phoneNo.indexOf("<")){
				var pat = /<(.*?)>/g;
				dataArray.push(pat.exec(phoneNo)[1]);
			}
		}
		$("#phone_Nos").val(dataArray.join(','));

		$('.layui-btn').attr('disabled', true);
		var data = $("#subForm").val();
		var loading = layer.load(''); //遮罩层
		$("#subForm").ajaxSubmit({
			type: 'post', // 提交方式 get/post
			url: '/admin/sended_fileInputSms', // 需要提交的 url
			dataType: 'json',
			data: data,
			success: function (d) {
				$('.layui-btn').attr('disabled', false);
				layer.close(loading);
				if (d.code != 0) {
					layer.alert(d.msg, {icon: 2}, function () {
						layer.closeAll();
					});
					return;
				}
				layer.alert(d.msg, {icon: 1}, function (index) {
					$('#subForm')[0].reset();
					$("#phone_Nos").val('');
					$("#send_Time").val('');
					$("#tagsinputval").tagsinput('removeAll');
					smsWordCount();
					layer.closeAll();
				});
			},
			error: function (d) {
				$('.layui-btn').attr('disabled', false);
				layer.close(loading);
				layer.msg('提交失败', {icon: 2, time: 2000}, function () {
					layer.closeAll();
				});
			}
		})
		return false; // 必须返回false，否则表单会自己再做一次提交操作，并且页面跳转
	}

	//联系人数据
	function setContactDatas(dataArray) {
		var phoneNosArray = new Array();
		while (dataArray.length>1000){
			phoneNosArray.push(dataArray.splice(0,1000))
		}
		phoneNosArray.push(dataArray)
		layer.load(0, {shade: false});
		$.each(phoneNosArray,function (i,val) {
			setTimeout(function(){
				if(i==phoneNosArray.length-1){
					layer.closeAll('loading');
				}
				$("#tagsinputval").tagsinput('add',val.join(','));
			}, i*100);

		})
	}
	layui.use(['tree', 'layer'], function () {
		var $ = layui.jquery,
				layer = layui.layer,
				tree = layui.tree;
		$(document).on('click', '.layui-tree-iconClick .layui-icon-file', function () {
			$(this).parent().parent().find('.layui-tree-txt').trigger("click");
		});
		$(document).on('click', '#addPhoneNo', function () {
			var phoneNos = $(".layui-tree-lineExtend .layui-form-checked").parent().find("input");
			if (phoneNos.length == 0) {
				layer.msg("请选择联系人");
				return;
			}
			var dataArray = new Array();
			phoneNos.each(function () {
				var checkedBoxVal = $(this).val();
				if (checkedBoxVal.indexOf("group_") == -1) {
					dataArray.push(checkedBoxVal)
				}
			});
			if (dataArray.length == 0) {
				layer.msg("请选择联系人");
				return;
			}
			setContactDatas(dataArray);
		});
		$(document).on('click', '#cleanAll', function () {
			tree.reload('contacts', {});
			$("#tagsinputval").tagsinput('removeAll');
		});
		$(document).on('click', '#send_Time_button', function () {
			var h = $(window).height()/6;
			layer.open({
				title: '选择日期',
				type: 1,
				icon: 3,
				//skin: 'layui-layer-rim',
				skin: 'layui-layer-molv',
				area: ['500px', '200px'],
                offset: h+'px',
                content: $('#send_Time_item'),
				btn:['确定'],
				success:function(layero, index) {
					layui.use(['laydate'], function () {
						var laydate = layui.laydate;
						laydate.render({     //创建时间选择框
							elem: '#send_Time_input',
							type: 'datetime',
                            trigger : 'click'
						});
					});
					layero.find("#send_Time_input").val('');
				},
				yes: function(index, layero){
					var send_Time = layero.find("#send_Time_input").val()
					if(send_Time == null || send_Time.trim() ==""){
						layer.msg("请选择发送时间");
						return;
					}
					$("#send_Time").val(send_Time);
					$("#submit_button").trigger("click");
					layer.close(index); //如果设定了yes回调，需进行手工关闭
				}
			})
		});
	});
	$('#tagsinputval').on('itemAdded', function(event) {
		$('.bootstrap-tagsinput input[type="text"]').attr("placeholder","");
	});
</script>
</body>
<div class="layui-form-item" id="send_Time_item" style="display: none">
	<div class="layui-row" style="height: 20px"></div>
	<label class="layui-form-label">定时发送:</label>
	<div class="layui-input-inline">
		<input type="text" name="send_Time" id="send_Time_input" lay-verify="datetime"
			   placeholder="yyyy-MM-dd HH:mm:ss" autocomplete="off" class="layui-input">
	</div>
</div>
</html>
