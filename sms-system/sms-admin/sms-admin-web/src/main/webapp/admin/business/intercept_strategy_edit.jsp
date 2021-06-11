<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/admin/common/common.jsp" %>
<%@ include file="/admin/common/layui_head.html" %>
<body>
<form class="layui-form" id="subForm" action="/admin/business_editInterceptStrategy" onsubmit="return ajaxSubmitForm();"
      style="padding: 20px 30px 0 0;">
    <input name="id" value="${interceptStrategy.id}" hidden>
    <div class="layui-form-item">
        <label class="layui-form-label">策略名称<font color="red">&nbsp;&nbsp;*</font></label>
        <div class="layui-input-inline">
            <input type="text" maxlength="128" name="name" id="name" value="${interceptStrategy.name}" lay-verify="required" placeholder="请输入" autocomplete="off"
                   class="layui-input">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">失败类型</label>
        <div class="layui-input-inline">
            <ht:herocodeselect sortCode="faild_type_code" selected="${interceptStrategy.faild_Type_Code}" name="faild_Type_Code"/>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">黑名单类型</label>
        <div class="layui-input-inline">
            <ht:herocodeselect sortCode="black_pool" selected="${interceptStrategy.black_Pool_Code}"  name="black_Pool_Code"/>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">外部黑名单</label>
        <div class="layui-input-inline">
            <ht:herocodeselect sortCode="external_Black_Pool" selected="${interceptStrategy.external_Black_Pool}"  name="external_Black_Pool"/>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">白名单类型</label>
        <div class="layui-input-inline">
            <ht:herocodeselect sortCode="white_pool" selected="${interceptStrategy.white_Pool_Code}"  name="white_Pool_Code"/>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">敏感字类型</label>
        <div class="layui-input-inline">
            <ht:herocodeselect sortCode="sensitive_word_pool" selected="${interceptStrategy.sensitive_Word_Pool_Code}"  name="sensitive_Word_Pool_Code"/>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">备注</label>
        <div class="layui-input-block">
            <textarea type="text"  maxlength="128" name="remark" autocomplete="off" class="layui-textarea"><c:out value="${interceptStrategy.remark}"/> </textarea>
        </div>
    </div>
    <div class="layui-form-item layui-hide">
        <button class="layui-btn" id="layuiadmin-app-form-submit">提交</button>
    </div>
</form>
<%@ include file="/admin/common/layui_bottom.jsp" %>
</body>
<script type="text/javascript">
    var $;
    layui.use('form', function() {
        $ = layui.$;
    });
    function ajaxSubmitForm() {
        var name = $("#name").val();
        if(!name){
            layer.msg("策略名称不能为空！");
            return false;
        }
        var loading= parent.layer.load('', {time: 10*1000});
        var data = $("#subForm").serialize();
        $.ajax({
            type: 'post', // 提交方式 get/post
            url: '/admin/business_editInterceptStrategy', // 需要提交的 url
            dataType: 'json',
            data: data,
            success: function (d) {
                parent.layer.close(loading);
                if(d.code != 0){
                    layer.alert(d.msg, {icon: 2, time: 2000}); return false;
                }
                layer.msg(d.msg, {icon: 1, time: 2000}, function () {
                    parent.layui.table.reload('list_table');/// 刷新父级表格
                    parent.layer.closeAll();//关
                });
            },
            error: function (d) {
                parent.layer.close(loading);
                layer.msg('提交失败', {icon: 2, time: 2000});
            }
        })
        return false; // 必须返回false，否则表单会自己再做一次提交操作，并且页面跳转
    }
</script>