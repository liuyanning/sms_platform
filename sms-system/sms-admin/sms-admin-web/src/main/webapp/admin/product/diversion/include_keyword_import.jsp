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
<form class="layui-form" id="importFileForm" action="/admin/product/importProductChannelsKeyword" lay-filter="form"
      enctype="multipart/form-data" method="post" style="padding: 20px 30px 0 0;" onsubmit="return fileFormSubmit();">
    <input name="product_Channels_Id" id="product_Channels_Id" type="hidden" />
    <div class="layui-form-item">
        <label class="layui-form-label" style="width: 20%">状态<font color="red">&nbsp;&nbsp;*</font></label>
        <div class="layui-input-inline" style="width: 60%">
            <ht:herocodeselect sortCode="state" name="status_Code" layVerify="required"/>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label" style="width: 20%">选择文件</label>
        <div class="layui-input-inline" style="width: 60%">
            <input type="file" name="importFile" id="file" lay-verify="required"/>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">&nbsp;</label>
        <div class="layui-form-mid layui-word-aux" >
            <font color="red">注意：每个关键字占用一行数据,多个关键字必须换行！</font><br/>
            <font color="red"><br/>
                示例：【拼多多】<br/>
            </font><br/>
            <font color="red">
                【京东支付】<br/>
            </font><br/>
            <font color="red">
                支持文件格式:&nbsp;txt
            </font>
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