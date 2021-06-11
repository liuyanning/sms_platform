<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/admin/common/common.jsp" %>
<%@ include file="/admin/common/layui_head.html" %>
<head>
    <meta http-equiv="Content-Type" content="multipart/form-data; charset=utf-8"/>
    <script src="/js/jquery-3.4.1.min.js"></script>
    <script src="/layuiadmin/layui/layui.all.js"></script>
    <script src="/js/jquery-form.js"></script>
</head>
<body>
<form class="layui-form" action="/admin/business_addCode" id="subForm" onsubmit="return ajaxSubmitForm()"
      enctype="multipart/form-data" method="post" style="padding: 20px 30px 0 0;">
    <div class="layui-form-item">
        <label class="layui-form-label">名称<font color="red">&nbsp;&nbsp;*</font></label>
        <div class="layui-input-inline">
            <input type="text" maxlength="2048" name="name" id="poolName" lay-verify="required" placeholder="请输入名称" autocomplete="off"
                   class="layui-input">
             <input name="code" id = "codeId" type="hidden"  />
             <input name="value" id = "valueId" type="hidden"  />
             <input name="up_Code" type="hidden"  value=""/>
             <input name="sort_Code" type="hidden" value="white_pool"/>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">备注</label>
        <div class="layui-input-block">
            <textarea type="text" maxlength="2048" name="remark" autocomplete="off" class="layui-textarea"></textarea>
        </div>
    </div>
      <div class="layui-form-item layui-hide">
        <button class="layui-btn" id="layuiadmin-app-form-submit">提交</button>
    </div>
</form>
<%@ include file="/admin/common/layui_bottom.jsp" %>

<script type="text/javascript">

function mkLinks(num){
        var str="123456789abcdefghijglmnopqrstuvwxyz123567zxcvbnmasdfghjklqwertyuiop";
        var res='';
        for(var i=0;i<num;i++){
            res+=str[Math.floor(Math.random()*str.length)];
        }
        return "white_pool_"+res;
    }
    
function ajaxSubmitForm() {
	var $ = layui.$;
	var poolName = $("#poolName").val();
    if(!poolName){
        layer.msg('请输入名称');
        return false;
    }
	var code = $("#codeId").val(this.mkLinks(4));
	var valueId = $("#valueId").val($("#codeId").val());
	var data = $("#subForm").serialize();
    var loading= parent.layer.load('', {time: 10*1000}); //遮罩层 最长3秒后自动关闭
   $.ajax({
        type: 'post', // 提交方式 get/post
        url: '/admin/business_addCode', // 需要提交的 url
        dataType: 'json',
        data: data,
        success: function (d) {
            parent.layer.close(loading);
            layer.msg('提交成功', {icon: 1, time: 2000}, function () {
                $('#subForm')[0].reset();
                parent.layer.closeAll();
            });
        },
        error: function (d) {
            parent.layer.close(loading);
            layer.msg('提交失败', {icon: 2, time: 2000}, function () {
            });
        }
    })
    return false; // 必须返回false，否则表单会自己再做一次提交操作，并且页面跳转
}
</script>
</body>