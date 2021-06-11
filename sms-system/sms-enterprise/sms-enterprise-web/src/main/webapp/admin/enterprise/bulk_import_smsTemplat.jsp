<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/common.jsp" %>
<%@ include file="/common/layui_head.html"%>
<head>
    <meta http-equiv="Content-Type" content="multipart/form-data; charset=utf-8"/>
    <script src="/js/jquery-3.4.1.min.js"></script>
    <script src='<ht:heropageconfigurationtext code="enterprise_webpage_css" defaultValue="/layuiadmin"/>/layui/layui.all.js'></script>
    <script src="/js/jquery-form.js"></script>
</head>
<body>
<form class="layui-form" action="/admin/enterprise_importSmsTemplate" id="subForm" onsubmit="return ajaxSubmitForm()"
      enctype="multipart/form-data" method="post" style="padding: 20px 30px 0 100px;">
    <div class="layui-form-item">
        <label class="layui-form-label">行业类型：</label>
        <div class="layui-input-inline">
            <ht:herocodeselect sortCode="trade" name="template_Type"/>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">模板文件</label>
        <div class="layui-input-inline">
            <input type="file" name="smsTemplateFile" id="smsTemplateFile"
                   class="required"/>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">&nbsp;</label>
        <div class="layui-form-mid layui-word-aux" >
            <font color="red">注意：每个模板占用一行数据,多个模板必须换行！</font><br/>
            <font color="red">
                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                每行数据格式为：模板名称 + <font color="blue">#</font> + 模板内容
            </font><br/>
            <font color="red">
                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                示例：验证码模板<font color="blue">#</font>您的验证码:{0},请勿告知他人!失效时间{1}分钟!
            </font><br/>
            <font color="red">
                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                支持文件格式:&nbsp;txt
            </font>
        </div>
    </div>
    <div class="layui-form-item layui-hide">
        <button class="layui-btn" id="layuiadmin-app-form-submit">提交</button>
    </div>

</form>
<%@ include file="/common/layui_bottom.jsp"%>

<script >
    var $;
    layui.use(['form'], function () {
        var form = layui.form;
        $ = layui.$;
        form.render();
    });

    function ajaxSubmitForm() {
        var data = $("#subForm").val();
        var smsTemplateFile = $("#smsTemplateFile").val();
        if (!smsTemplateFile){
            layer.msg('请选择模板文件！')
            return false;
        }
        var loading= parent.layer.load('', {time: 10*1000});
        $("#subForm").ajaxSubmit({
            type: 'post', // 提交方式 get/post
            url: '/admin/enterprise_importSmsTemplate', // 需要提交的 url
            dataType: 'json',
            data: data,
            success: function (d) {
                parent.layer.close(loading);//关闭遮罩层
                if(d.code == '0'){
                    layer.alert('提交成功',function () {
                        parent.layer.closeAll();
                        parent.layui.table.reload('list_table');/// 刷新父级表格
                    });
                }else{
                    layer.alert(d.msg, {icon: 1, time: 2000}, function () {
                    });
                }
            },
            error: function (d) {
                parent.layer.close(loading);//关闭遮罩层
                layer.msg('提交失败', {icon: 2, time: 2000}, function () {
                });
            }
        })
        return false; // 必须返回false，否则表单会自己再做一次提交操作，并且页面跳转
    }
</script>
</body>