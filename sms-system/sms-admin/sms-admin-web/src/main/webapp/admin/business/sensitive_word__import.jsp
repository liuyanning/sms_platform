<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/admin/common/common.jsp" %>
<%@ include file="/admin/common/layui_head.html"%>
<head>
    <meta http-equiv="Content-Type" content="multipart/form-data; charset=utf-8"/>
    <script src="/js/jquery-3.4.1.min.js"></script>
    <script src="/layuiadmin/layui/layui.all.js"></script>
    <script src="/js/jquery-form.js"></script>
</head>
<body>
<form class="layui-form" action="/admin/business_importSensitiveWord" id="subForm" onsubmit="return ajaxSubmitForm()"
      enctype="multipart/form-data" method="post" style="padding: 20px 30px 0 180px;">
    <div class="layui-form-item">
        <label class="layui-form-label">敏感字类型</label>
        <div class="layui-input-inline">
            <ht:herocodeselect sortCode="sensitive_word_pool" name="pool_Code"/>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">行业：</label>
        <div class="layui-input-inline">
            <ht:herocodeselect sortCode="trade" name="trade_Type_Code"/>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">选择文件&nbsp;<font color="red">*</font></label>
        <div class="layui-input-inline">
            <input id="file" type="file" name="file">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label"></label>
        <div class="layui-form-mid layui-word-aux">
            <font color="red">注意：多个敏感字用逗号分隔 </font><br/>
            <font color="red">
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
        var file = $("#file").val();
        if (!file){
            layer.msg('请选择上传文件！')
            return false;
        }
        var loading= parent.layer.load('', {time: 10*1000});
        $("#subForm").ajaxSubmit({
            type: 'post', // 提交方式 get/post
            url: '/admin/business_importSensitiveWord', // 需要提交的 url
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
                    layer.alert(d.msg,function () {
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