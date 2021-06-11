<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/common.jsp"%>
<%@ include file="/common/layui_head.html"%>
<html>
<head>
    <meta http-equiv="Content-Type" content="multipart/form-data; charset=utf-8" />
    <script src="/js/jquery-3.4.1.min.js"></script>
    <script src="/js/jquery-form.js"></script>
</head>
<body>
<div class="layui-fluid">
    <div class="layui-col-md12">
        <div class="layui-card">
            <div class="layui-card-header"></div>
            <div class="layui-card-body" pad15>
                <form class="layui-form" id="subForm" enctype="multipart/form-data" method="post" >
                    <div class="layui-fluid" layoutH="57">
                        <fieldset class="layui-elem-field layui-field-title" style="margin-top: 0px;">
                            <legend><font color="blue">发送号码</font></legend>
                        </fieldset>
                        <div class="layui-form-item">
                            <label class="layui-form-label">手机号码:</label>
                            <div class="layui-input-inline" style="width: 50%;">
                                <textarea placeholder="(多个号码换行或者逗号分割,最多2W个号码)" class="layui-textarea" id="phone_Nos"></textarea>
                            </div>
                        </div>
                        <div class="layui-form-item">
                            <label class="layui-form-label">号码文件：</label>
                            <div class="layui-input-inline" id="fileDivId">
                                <input hidden id="filePath" name="filePath"/>
                                <button type="button" class="layui-btn" id="uploadExample">
                                    <i class="layui-icon">&#xe67c;</i>
                                    <span id="fileName">上传文件</span>
                                </button>
                            </div>
                            <div class="layui-form-mid layui-word-aux">每个文件最大支持5W个号码换行或者逗号分割。支持文件格式: TXT</div>
                        </div>
                        <div id="templateDivId">
                            <fieldset class="layui-elem-field layui-field-title" style="margin-top: 30px;">
                                <legend><font color="blue">请选择彩信模板</font></legend>
                            </fieldset>
                            <div class="layui-form-item">
                                <label class="layui-form-label">彩信模板：</label>
                                <div class="layui-input-inline">
                                    <ht:herommstemplatetag  enterpriseNo="${ADMIN_USER.enterprise_No}"
                                                            enterpriseUserId="${ADMIN_USER.id}"
                                                            name="mmsTemplateCode" />
                                </div>
                            </div>
                        </div>
                        <div class="layui-form-item">
                            <label class="layui-form-label"></label>
                            <div class="layui-form-mid layui-word-aux">签名: ${ADMIN_USER.suffix}</div>
                        </div>
                    </div>
                </form>
                <div class="layui-form-item">
                    <label class="layui-form-label" style="width: 8%;"></label>
                    <div class="layui-input-block">
                        <button class="layui-btn" onclick="clickSend()">发&nbsp;&nbsp;&nbsp;&nbsp;送</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
<script type="text/javascript">
    var form ;
    var $ ;
    layui.use(['form'], function(){
        form = layui.form;
        $ = layui.jquery;
        form.render();//渲染
    });

    layui.use('upload', function(){
        var upload = layui.upload;
        var layerload;
        var uploadInst = upload.render({
            elem: '#uploadExample' //绑定元素
            ,size: 1024*2
            ,url: '/admin/sended_uploadSendFile' //上传接口
            ,accept: 'file'
            ,before: function(obj){//参数
                layerload= layer.load(''); //遮罩层
            }
            ,done: function(res){
                layer.close(layerload);
                if (!$("#filePath").val()){
                    $("#fileDivId").after('<br class="append"/><br class="append"/><label class="layui-form-label"></label>');
                }
                if(res.code == '0'){
                    $("#filePath").val(res.data.realPath);
                    $("#fileName").html('【'+res.data.fileName+'】（'+res.data.mobileCount+'个号码）');
                    var html = '您上传了文件'+res.data.fileName+',号码：'+res.data.mobileCount+'个';
                    layer.msg(html);
                }else{
                    layer.msg(res.msg);
                }
            }
            ,error: function(){
                layer.close(layerload);
                layer.alert('上传失败');
            }
        });
    });
    //=========== 提交 =================
    function clickSend() {
        var moblieFile = $("#filePath").val();//号码文件
        var phone_Nos = $("#phone_Nos").val();//号码输入框
        if(!moblieFile && !phone_Nos){
            return layer.msg("请输入发送号码！");
        }
        var mmsTemplateCode = $("select[name='mmsTemplateCode']").val();
        if(!mmsTemplateCode){
            return layer.msg("请选择视频彩信模板！");
        }
        var loading= layer.load(''); //遮罩层
        $("#subForm").ajaxSubmit({
            type: 'post', // 提交方式 get/post
            url: '/admin/sended_sendVideoMMS', // 需要提交的 url
            dataType: 'json',
            data: {
                "moblieFile":moblieFile,
                "phone_Nos":phone_Nos,
                "content":mmsTemplateCode
            },
            contentType : "application/json; charset=utf-8",
            success: function (d) {
                layer.close(loading);
                if(d.code!=0){
                    layer.alert(d.msg, {icon: 2}, function () {
                        layer.closeAll();
                    });
                    return;
                }
                layer.alert(d.msg, {icon: 1}, function (index) {
                    $('#subForm')[0].reset();
                    $("#fileName").html('上传文件');
                    layer.closeAll();
                });
            },
            error: function (d) {
                layer.close(loading);
                layer.msg('提交失败', {icon: 2, time: 2000}, function () {
                    layer.closeAll();
                });
            }
        })
        return false;
    }
</script>
</html>
