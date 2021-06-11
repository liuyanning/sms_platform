<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/admin/common/common.jsp" %>
<%@ include file="/admin/common/layui_head.html"%>
<%@ include file="/admin/common/dynamic_data.jsp" %>
<body>
<form class="layui-form" action="" id="subForm" onsubmit="return ajaxSubmitForm()"
      enctype="multipart/form-data" method="post" style="padding: 30px 30px 0 0;">
    <div class="layui-form-item">
        <label class="layui-form-label" style="width:20%;">素材名称<font color="red">&nbsp;&nbsp;*</font></label>
        <div class="layui-input-inline" style="width:60%;">
            <input type="text" maxlength="128"  name="material_Name" id="material_Name" lay-verify="required" placeholder="请输入名称" autocomplete="off"
                   class="layui-input">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label" style="width:20%;">企业<font color="red">&nbsp;&nbsp;*</font></label>
        <div class="layui-input-inline" style="width:60%;">
            <ht:heroenterpriseselect  layVerify = "required" name="enterprise_No" id="enterprise_No"/>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label" style="width:20%;">企业用户<font color="red">&nbsp;&nbsp;*</font></label>
        <div class="layui-input-inline" style="width:60%;">
            <select id="enterprise_User_Id"  name="enterprise_User_Id">

            </select>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label" style="width:20%;">素材类型<font color="red">&nbsp;&nbsp;*</font></label>
        <div class="layui-input-inline" style="width:60%;">
            <ht:herocodeselect sortCode="material_type_code" id="material_Type_Code" layVerify="required" name="material_Type_Code"/>
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label" style="width:20%;">素材<font color="red">&nbsp;&nbsp;*</font></label>
        <div class="layui-input-inline" style="width:60%;">
            <div class="layui-upload-list">
            </div>
            <input id="file" type="file" name="file">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label" style="width:20%;"></label>
        <div class="layui-input-inline" style="width:60%;">
            图片格式支持：PNG、JPG、GIF；<br>
            音频格式支持：MP3；<br>
            视频格式支持：MP4、3GP；<br>
            每次上传一个文件，小于<c:out value="${maxMaterialSize}"/>KB
        </div>
    </div>

    <div class="layui-form-item layui-hide">
        <button class="layui-btn" id="layuiadmin-app-form-submit">提交</button>
    </div>

</form>
<%@ include file="/admin/common/layui_bottom.jsp"%>

<script >
    layui.use(['form'], function () {
        var form = layui.form
        form.render();
    });


    function ajaxSubmitForm() {
        var material_Name = $("#material_Name").val();
        if (!material_Name){
            layer.msg('请填写素材名称！')
            return false;
        }
        var enterprise_No = $("#enterprise_No").val();
        if (!enterprise_No){
            layer.msg('请选择企业！')
            return false;
        }
        var enterprise_User_Id = $("#enterprise_User_Id").val();
        if (!enterprise_User_Id){
            layer.msg('请选择用户！')
            return false;
        }
        var file = document.getElementById("file").files[0];
        if (!file){
            layer.msg('请选择要上传的素材！')
            return false;
        }
        var maxMaterialSize = '${maxMaterialSize}';
        if(file.size > maxMaterialSize*1024){
            layer.msg('您选择的文件太大！')
            return false;
        }

        var material_Type = $("#material_Type_Code").val();
        if (material_Type == 'picture') {
            if (file.type.search("image") == -1){
                layer.msg('文件格式错误，请重新选择！')
                return false;
            }
        }if (material_Type == 'video') {
            if (file.type.search("video") == -1){
                layer.msg('文件格式错误，请重新选择！')
                return false;
            }
        }if (material_Type == 'audio') {
            if (file.type.search("audio") == -1){
                layer.msg('文件格式错误，请重新选择！')
                return false;
            }
        }

        var data = $("#subForm").val();
        var loading= parent.layer.load('', {time: 10*1000});
        $("#subForm").ajaxSubmit({
            type: 'post',
            url: '/admin/business_addMaterial',
            dataType: 'json',
            data: data,
            success: function (d) {
                parent.layer.close(loading);
                if(d.code == '0'){
                    layer.msg('提交成功', {icon: 1, time: 2000}, function () {
                        $('#subForm')[0].reset();
                        window.parent.location.reload();
                    });
                    return false;
                }else{
                    layer.msg(d.msg, {icon: 2, time: 2000}, function () {
                    });
                    return false;
                }
            },
            error: function (d) {
                parent.layer.close(loading);
                layer.msg(d.msg, {icon: 2, time: 2000}, function () {
                    $('#layuiadmin-app-form-submit').attr('disabled',false);
                });
            }
        })
        return false;
    }

</script>
</body>