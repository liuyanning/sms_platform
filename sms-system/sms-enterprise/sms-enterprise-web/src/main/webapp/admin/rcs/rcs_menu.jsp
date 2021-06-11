<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/common.jsp"%>
<%@ include file="/common/layui_head.html"%>
<html>
<head>
	<meta http-equiv="Content-Type" content="multipart/form-data; charset=utf-8" />

	<link rel="stylesheet" href="/css/rcs/bootstrap.min.css">
	<link rel="stylesheet" href="/css/rcs/font-awesome.min.css">
	<!-- 自定义样式 -->
	<link rel="stylesheet" href="/css/rcs/wx-custom.css">


	<script src="/js/jquery-3.4.1.min.js"></script>
	<script src="/js/jquery-form.js"></script>
</head>

<body>
<div class="container">
	<!-- 自定义菜单 -->
	<div class="custom-menu-edit-con">
		<div class="hbox">
			<div class="inner-left">
				<div class="custom-menu-view-con">
					<div class="custom-menu-view">
						<div class="custom-menu-view__title">自定义静态菜单</div>
						<div class="custom-menu-view__body">
							<div class="weixin-msg-list"><ul class="msg-con"></ul></div>
						</div>
						<div id="menuMain" class="custom-menu-view__footer">
							<div class="custom-menu-view__footer__left"></div>
							<div class="custom-menu-view__footer__right" ></div>
						</div>
					</div>
				</div>
			</div>
			<div class="inner-right">
				<div class="cm-edit-after">
					<div class="cm-edit-right-header b-b"><span id="cm-tit"></span> <a id="delMenu" class="pull-right" href="javascript:;">删除菜单</a></div>
					<form class="form-horizontal wrapper-md" name="custom_form">
						<div class="form-group">
							<label class="col-sm-2 control-label">菜单名称:</label><div class="col-sm-5">
							<input name="custom_input_title" type="text" class="form-control" ng-model="menuname" value="" placeholder="" ng-maxlength="5"></div><div class="col-sm-5 help-block">
							<div ng-show="custom_form.custom_input_title.$dirty&&custom_form.custom_input_title.$invalid-maxlength">字数不超过6个汉字或12个字符</div>
							<div class="font_sml" style="display: none;">若无二级菜单，可输入20个汉字或60个字符</div>
						</div>
						</div>
						<div class="form-group" id="radioGroup">
							<label class="col-sm-2 control-label">类型:</label>
							<div class="col-sm-10 LebelRadio">
								<label class="checkbox-inline"><input type="radio" name="radioBtn" value="reply" checked> 建议回复</label>
								<label class="checkbox-inline"><input type="radio" name="radioBtn" value="openUrl"> 跳转网页</label>
								<label class="checkbox-inline"><input type="radio" name="radioBtn" value="dialPhoneNumber" > 拨打电话</label>
							</div>
						</div>
					</form>

					<div class="cm-edit-content-con" id="editMsg">
						<div class="cm-edit-page">
							<div class="row">
								<label class="col-sm-6 control-label" style="text-align: left;">点击该菜单自动回复以下内容:
								</label>
							</div>
							<div class="row">
								<label class="col-sm-2 control-label" style="text-align: left;">回复内容:
								</label>
								<div class="col-sm-5">
									<input type="text" name="data" maxlength="16" class="form-control" >
									<span class="help-block">必填,16个汉字或字符以内</span>
								</div>
							</div>
						</div>
					</div>
					<div class="cm-edit-content-con" id="editLink">
						<div class="cm-edit-page">
							<div class="row">
								<label class="col-sm-6 control-label" style="text-align: left;">点击该菜单会跳转到以下链接:
								</label>
							</div>
							<div class="row">
								<label class="col-sm-2 control-label" style="text-align: left;">页面地址:
								</label>
								<div class="col-sm-5">
									<input type="text" name="url" class="form-control" >
									<span class="help-block">必填,必须是正确的URL格式</span>
								</div>
							</div>
						</div>
						<div class="cm-edit-page">
							<div class="row">
								<label class="col-sm-2 control-label" style="text-align: left;">回发数据:
								</label>
								<div class="col-sm-5">
									<input type="text" name="urlData" class="form-control" >
									<span class="help-block">必填，回调内容</span>
								</div>
							</div>
						</div>
					</div>
					<div class="cm-edit-content-con" id="editPhone">
						<div class="cm-edit-page">
							<div class="row">
								<label class="col-sm-7 control-label" style="text-align: left;">点击该菜单会跳转到拨打电话界面:
								</label>
							</div>
							<div class="row">
								<label class="col-sm-2 control-label" style="text-align: left;">电话号码:
								</label>
								<div class="col-sm-5">
									<input type="text" name="phoneNumber" class="form-control">
									<span class="help-block">必填,必须是正确的电话号码格式</span>
								</div>
							</div>
						</div>
						<div class="cm-edit-page">
							<div class="row">
								<label class="col-sm-2 control-label" style="text-align: left;">回发数据:
								</label>
								<div class="col-sm-5">
									<input type="text" name="phoneData" class="form-control" >
									<span class="help-block">必填，回调内容</span>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="cm-edit-before"><h5>点击左侧菜单进行操作</h5></div>
			</div>
		</div>
	</div>
	<div class="cm-edit-footer">
		<button id="saveBtns" type="button" class="btn btn-info1">保存</button>
	</div>
</div>

<script src="/js/rcs/bootstrap.min.js"></script>

<script src="/js/rcs/menu.js"></script>
<div id="reminderModal" class="modal fade" tabindex="-1" role="dialog">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span>×</span></button>
				<h4 class="modal-title">
					温馨提示
				</h4>
			</div>
			<div class="modal-body">
				<h5>添加子菜单后，一级菜单的内容将被清除。确定添加子菜单？</h5>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-info reminder">确定</button>
				<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
			</div>
		</div>
	</div>
</div>

<div id="abnormalModal" class="modal fade" tabindex="-1" role="dialog">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span>×</span></button>
				<h4 class="modal-title">
					温馨提示
				</h4>
			</div>
			<div class="modal-body">
				<h5>数据异常</h5>
			</div>
			<div class="modal-footer">
				<!-- <button type="button" class="btn btn-info reminder">确定</button> -->
				<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
			</div>
		</div>
	</div>
</div>
</body>
<script type="text/javascript">

	window.onresize = ()=>{
		$("body").css("height",window.screen.availHeight/(Number(detectZoom())/100)+"px");
	}


	//获取当前页面的缩放值
	function detectZoom() {
		var ratio = 0,
				screen = window.screen,
				ua = navigator.userAgent.toLowerCase();

		if (window.devicePixelRatio !== undefined) {
			ratio = window.devicePixelRatio;
		}
		else if (~ua.indexOf('msie')) {
			if (screen.deviceXDPI && screen.logicalXDPI) {
				ratio = screen.deviceXDPI / screen.logicalXDPI;
			}
		}
		else if (window.outerWidth !== undefined && window.innerWidth !== undefined) {
			ratio = window.outerWidth / window.innerWidth;
		}

		if (ratio) {
			ratio = Math.round(ratio * 100);
		}
		return ratio;
	}
</script>
</html>
