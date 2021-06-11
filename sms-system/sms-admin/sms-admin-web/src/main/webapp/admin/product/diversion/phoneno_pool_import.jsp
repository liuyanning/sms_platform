<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/admin/common/common.jsp" %>
<%@ include file="/admin/common/layui_head.html"%>
<head>
    <meta http-equiv="Content-Type" content="multipart/form-data; charset=utf-8"/>
    <script src="/js/jquery-3.4.1.min.js"></script>
    <script src="/js/jquery-form.js"></script>
    <script src="/js/form.js"></script>
</head>
<body>
<form class="layui-form" id="importFileForm" action="/admin/product/diversionPhoneNoPoolImport" lay-filter="form"
      enctype="multipart/form-data" method="post" style="padding: 20px 30px 0 0;" onsubmit="return fileFormSubmit();">
     <input name="product_Channels_Id" id="product_Channels_Id" type="hidden" />
    <div class="layui-form-item">
        <label class="layui-form-label" style="width: 80px">状态<font color="red">&nbsp;&nbsp;*</font></label>
        <div class="layui-input-inline" style="width: 100px">
            <ht:herocodeselect sortCode="state" name="status_Code" layVerify="required"/>
        </div>
    </div>
     <div class="layui-form-item">
        <label class="layui-form-label" style="width: 80px">权重<font color="red">&nbsp;&nbsp;*</font></label>
        <div class="layui-input-inline" style="width: 100px">
            <input  maxlength="9" name="weight" class="layui-input" lay-verify="required"/>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label" style="width: 100px">选择文件</label>
        <div class="layui-input-inline" style="width: 100px">
            <input type="file" name="phoneNoFile" id="file" lay-verify="required"/>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label" style="width: 50px;">
        </label>
        <div class="layui-input-inline" style="width: 300px;color: red;">
           	文件格式：txt格式<br>
        	单条导入：一行一条数据。<br>
        	区间导入：开始-结束。<br>
        	例如：62321494-62321400，包含开始和结束
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label" style="width: 80px">随机位数</label>
        <div class="layui-input-inline" style="width: 100px">
            <input maxlength="10" name="appendRandomSize" class="layui-input" lay-verify="required"/>
        </div>
    </div>
    <div class="layui-form-item layui-hide">
        <input type="submit" lay-submit class="layui-btn" id="layuiadmin-app-form-submit" value="提交">
    </div>
</form>
<%@ include file="/admin/common/layui_bottom.jsp"%>

<script type="text/javascript">
function setValue(){
   var product_Channels_Id = $(window.parent.document).find("#product_Channels_Id").val();
   $("#product_Channels_Id").val(product_Channels_Id);
}
function fileFormSubmit(){
	setValue();
    sms.ajaxSubmitForm({type:'post',dataType:'json'});
    return false;
};

</script>
</body>