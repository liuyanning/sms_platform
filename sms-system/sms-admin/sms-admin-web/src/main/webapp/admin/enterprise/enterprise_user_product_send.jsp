<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/admin/common/common.jsp" %>
<%@ include file="/admin/common/layui_head.html" %>
<script src="/js/jquery-3.4.1.min.js"></script>

<body>
<div class="layui-fluid" id="LAY-component-nav">
	<div class="layui-row layui-col-space15">
		<div class="layui-col-md9">
			<div class="layui-card">
				<div class="layui-card-body" >
					<div class="layui-fluid" layoutH="57">
						<form class="layui-form" id="subForm">
							<div class="layui-form-item">
								<label class="layui-form-label">手机号码:</label>
								<div class="layui-input-inline" style="width: 60%;">
                                    <textarea rows="2" placeholder="多个号码换行或者逗号分割&#10;发送时根据手机号码个数轮循发送"
                                      class="layui-textarea" name="phone_Nos" id="phone_Nos"></textarea>
								</div>
							</div>
							<div class="layui-form-item">
								<label class="layui-form-label">短信内容:</label>
								<div class="layui-input-inline" style="width: 60%;">
												<textarea placeholder="每条短信内容占一行&#10;多条短信内容请换行&#10;发送时每行内容会从上述号码框中选择一个号码发送"
														class="layui-textarea" onkeyup="smsWordCount()"
														name="content" id="content"></textarea>
								</div>
							</div>
							<div class="layui-form-item">
								<label class="layui-form-label"></label>
								<div class="layui-form-mid layui-word-aux">
									<span style="width: 100%;" id="wordCount">0个字符</span>
								</div>
							</div>
						</form>
                        <div class="layui-form-item">
                            <label class="layui-form-label" style="width: 8%;"></label>
                            <div class="layui-input-inline">
                                <button class="layui-btn layui-btn-lg" onclick="sendSms()">&nbsp;&nbsp;&nbsp;立&nbsp;即&nbsp;发&nbsp;送&nbsp;&nbsp;&nbsp;</button>
                            </div>
                        </div>
                    </div>
				</div>
			</div>
		</div>
	</div>
</div>
<script type="text/javascript">
    var layer = "";
    layui.use(['layer'], function () {
        layer = layui.layer
    });

	function smsWordCount() {
		var fullSms = $("#content").val();
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

	//立即发送
	function sendSms() {
        var userId = "<%=request.getParameter("userId")%>";
        var phone_Nos = $("#phone_Nos").val();//号码输入框
        if (!phone_Nos) {
            return layer.msg("请输入发送号码！");
        }
		var content = $("#content").val();
        if (!content) {
			return layer.msg("短信内容不能为空！");
		}
		$('.layui-btn').attr('disabled', true);
		var loading = layer.load(''); //遮罩层
		$.ajax({
			type: 'post', // 提交方式 get/post
			url: '/admin/enterprise_testProduct', // 需要提交的 url
			dataType: 'json',
			data: {
			    'userId':userId,
			    'phoneNos':phone_Nos,
			    'content':content
            },
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

</script>
</body>
</html>
