<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/admin/common/common.jsp" %>
<%@ include file="/admin/common/layui_head.html"%>
<%@ include file="/admin/common/dynamic_data.jsp" %>
<head>
    <meta http-equiv="Content-Type" content="multipart/form-data; charset=utf-8"/>
    <script src="/js/jquery-3.4.1.min.js"></script>
    <script src="/js/jquery-form.js"></script>
</head>
<body>
<form class="layui-form" action="/admin/business_importBlackList" id="subForm" onsubmit="return ajaxSubmitForm()"
      enctype="multipart/form-data" method="post" style="padding: 20px 30px 0 30px;">
    <div class="layui-form-item">
        <label class="layui-form-label">企业</label>
        <div class="layui-input-inline">
            <ht:heroenterpriseselect name="enterprise_No" id="enterprise_No"/>
        </div>
        <label class="layui-form-label">企业用户</label>
        <div class="layui-input-inline">
            <select id="enterprise_User_Id" name="enterprise_User_Id"></select>
            <font color="red">用户属性设置过滤黑名单为：是，参数才生效</font>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">黑名单类型</label>
        <div class="layui-input-inline">
            <ht:herocodeselect sortCode="black_pool" name="pool_Code"/>
        </div>

        <label class="layui-form-label">号码文件</label>
        <div class="layui-input-inline">
            <input id="phoneNosFile" type="file" name="phoneNosFile">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label"></label>
        <div class="layui-form-mid layui-word-aux">
            <font color="red">注意：手机号码前必须有国家代码，例如：“86158xxxxxxxx”<br/>
                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                国家代码前后不要有非数字符号，例如:“+”<br/>
                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                多个手机号用逗号或换行分隔<br/>
                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                支持文件格式:txt
            </font>

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
        var data = $("#subForm").val();
        var phoneNosFile = $("#phoneNosFile").val();
        if (!phoneNosFile){
            layer.msg('请选择号码文件！')
            return false;
        }
        var loading= parent.layer.load('', {time: 10*1000});
        $("#subForm").ajaxSubmit({
            type: 'post', // 提交方式 get/post
            url: '/admin/business_importBlackList', // 需要提交的 url
            dataType: 'json',
            data: data,
            success: function (d) {
                parent.layer.close(loading);
                if(d.code == '0'){
                    layer.alert('提交成功',function () {
                        parent.layer.closeAll();
                        parent.layui.table.reload('list_table');/// 刷新父级表格
                    });
                }else{
                    layer.alert(d.msg,function () {
                    });
                }
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