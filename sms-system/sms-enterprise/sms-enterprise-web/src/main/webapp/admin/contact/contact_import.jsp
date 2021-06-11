<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/common/common.jsp" %>
<%@ include file="/common/layui_head.html" %>
<script src="/js/jquery-form.js"></script>
<div id="formDivId" class="layui-form-item">
    <form enctype="multipart/form-data" action=""
          id="subForm" method="post" class="layui-form" lay-filter="form"
          onsubmit="return ajaxSubmitForm();" style="padding: 20px 30px 0 0;">
        <input type="hidden" name="id" value="${contactExt.id}">
        <input type="hidden" name="enterprise_No" value="${contactExt.enterprise_No}">
        <div class="layui-form-item">
            <label class="layui-form-label">号码文件<font color="red">&nbsp;&nbsp;*</font></label>
            <div class="layui-input-block">
                <input type="file" name="moblieFile" class="layui-input" lay-verify="required">
                <br/>
                支持的文件格式: TXT,Excel2003,Excel2007。<br/>
                数据格式：<font color="red">号码,姓名,性别,生日(yyyy/MM/dd),地址,单位,职务。</font><br/>
                TXT：最大1W个手机号码，<font color="red">换行分割</font>。<br/>
                Excel：第一行默认为标题不被导入,列数据对应上述<font color="red">数据格式</font>。
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">分组<font color="red">&nbsp;&nbsp;*</font></label>
            <div class="layui-input-block">
                <ht:herocontactgroupselect name="group_Id" selected="${param.group_Id}" layVerify="required"/>
                提示：若无分组请先增加分组
            </div>
        </div>
        <div class="layui-form-item layui-hide">
            <button class="layui-btn" id="layuiadmin-app-form-submit">提交</button>
        </div>
    </form>
</div>

<%@ include file="/common/layui_bottom.jsp" %>
</body>

<script>
    layui.use(['form', 'layedit', 'laydate'], function () {
        var form = layui.form
            , layer = layui.layer
            , layedit = layui.layedit
            , laydate = layui.laydate
            , table = layui.table;
    });

    function ajaxSubmitForm() {
        if($("input[name='moblieFile']").val() == ""){
            layer.msg('导入号码文件不能为空', {icon: 1, time: 2000}, function () {
            });
            return false;
        }
        if($(".layui-form-select .layui-this").attr("lay-value")==undefined){
            layer.msg('请选择分组', {icon: 1, time: 2000}, function () {
            });
            return false;
        }
        var data = $("#subForm").val();
        var loading= parent.layer.load('');
        $("#subForm").ajaxSubmit({
            type: 'post', // 提交方式 get/post
            url: '/admin/contact_importContact', // 需要提交的 url
            dataType: 'json',
            data: data,
            success: function (d) {
                parent.layer.close(loading);//关闭遮罩层
                if (d.code == "0") {
                    var map = d.data;
                    var successTotal = map['successTotal'];
                    var failTotal = map['failTotal'];
                    var failList = map['failList'];
                    parent.layer.closeAll();//关闭当前弹框
                    //父页面打开上传结果
                    parent.openUploadReslut(successTotal,failTotal,failList);
                    return false;
                } else {
                    parent.layer.msg('操作失败', {icon: 1, time: 2000}, function () {
                    });
                    return false;
                }
                return false;
            },
            error: function (d) {
                parent.layer.close(loading);//关闭遮罩层
                parent.layer.msg('提交失败,请确认文件格式是否正确', {icon: 1, time: 2000}, function () {
                    parent.layer.closeAll();
                });
                return false;
            }
        })
        return false;
    }
</script>