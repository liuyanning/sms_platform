<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/admin/common/common.jsp" %>
<%@ include file="/admin/common/layui_head.html" %>
<body>
<form class="layui-form" id="layui-form" action="/admin/enterprise_authenticate" lay-filter="form" onsubmit="return false;"
      style="padding: 20px 30px 0 0;">
    	<input type="hidden" name="id" value="<c:out value="${eBean.id}"/>"/>
    	<input type="hidden" name="agent_No" value="<c:out value="${eBean.agent_No}"/>"/>
		<div class="layui-form-item">
			<label class="layui-form-label">身份证</label>
			<div class="layui-input-inline" id="id_Card">
				<c:if test="${eBean.id_Card_Before_Picture_Url == null and eBean.id_Card_Back_Picture_Url == null}">
					<div class="layui-form-mid layui-word-aux">无</div>
				</c:if>
				<div>
					<c:if test="${eBean.id_Card_Before_Picture_Url != '' and eBean.id_Card_Before_Picture_Url != null}">
						<div style="margin-right: 5px; float: left">
							<img width="100px" height="100px" layer-src="${eBean.id_Card_Before_Picture_Url}"
								src="${eBean.id_Card_Before_Picture_Url}" alt="身份证正面">
						</div>
					</c:if>
					<c:if test="${eBean.id_Card_Back_Picture_Url != '' and eBean.id_Card_Back_Picture_Url != null}">
						<div style="margin-left: 130px; margin-right: 5px;">
							<img width="100px" height="100px" layer-src="${eBean.id_Card_Back_Picture_Url}"
								src="${eBean.id_Card_Back_Picture_Url}" alt="身份证反面">
						</div>
					</c:if>
				</div>
			</div>
		</div>
		<div class="layui-form-item">
        <label class="layui-form-label">营业执照</label>
        <div class="layui-input-inline" id="layer-photos-demo">
            <c:if test="${eBean.business_License_Picture_Url == '' or eBean.business_License_Picture_Url == null}">
                <div class="layui-form-mid layui-word-aux">无</div>
            </c:if>
            <c:if test="${eBean.business_License_Picture_Url != '' and eBean.business_License_Picture_Url != null}">
                <img width="100px" height="100px" layer-src="${eBean.business_License_Picture_Url}" src="${eBean.business_License_Picture_Url}" alt="营业执照">
            </c:if>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">认证</label>
        <div class="layui-input-inline">
            <ht:herocodeselect sortCode="authentication_State" name="authentication_State_Code" selected="${eBean.authentication_State_Code}"/>
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label">备注<font color="red">&nbsp;&nbsp;*</font></label>
        <div class="layui-input-block">
            <textarea type="text" name="remark" lay-verify="required" autocomplete="off" class="layui-textarea">${eBean.remark}</textarea>
        </div>
    </div>
    <div class="layui-form-item layui-hide">
        <input type="button" lay-submit lay-filter="submit" id="layuiadmin-app-form-submit" value="确认">
    </div>
</form>
<%@ include file="/admin/common/layui_bottom.jsp" %>

</body>
<script>
    layui.use(['form', 'layedit', 'laydate'], function () {
        var form = layui.form
            , layer = layui.layer
        layer.photos({
            photos: '#id_Card',
            area: ['600px', '400px'],
            anim: 1 ,//0-6的选择，指定弹出图片动画类型，默认随机（请注意，3.0之前的版本用shift参数）
            closeBtn:1
        })
        layer.photos({
            photos: '#layer-photos-demo',
            area: ['600px', '400px'],
            anim: 1 ,//0-6的选择，指定弹出图片动画类型，默认随机（请注意，3.0之前的版本用shift参数）
            closeBtn:1
        })
    })


</script>