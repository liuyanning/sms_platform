<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/admin/common/common.jsp" %>
<%@ include file="/admin/common/layui_head.html" %>
<body>
<div class="layui-fluid">
    <div class="layui-col-md12">
        <div class="layui-card">
            <form class="layui-form layui-card-header layuiadmin-card-header-auto" id="layuiForm"
                  onsubmit="return false;">
                <div class="layui-inline">
                    &nbsp;&nbsp;企业用户
                    <div class="layui-inline" style="width: 200px">
                        <ht:herocustomdataselect dataSourceType="allEnterpriseUsers" name="enterprise_User_Id"/>
                    </div>
                </div>
                <div class="layui-inline">
                    &nbsp;&nbsp;企业编号&nbsp;
                    <div class="layui-input-inline">
                        <input name="enterprise_No" id="enterprise_No" class="layui-input"/>
                    </div>
                </div>&nbsp;&nbsp;
                <div class="layui-inline">
                    &nbsp;&nbsp;消息内容&nbsp;
                    <div class="layui-input-inline">
                        <input name="content" id="content" class="layui-input"/>
                    </div>
                </div>&nbsp;&nbsp
                <div class="layui-inline">
                    <button class="layui-btn layui-btn-sm" type="submit" lay-submit="" lay-filter="reload">搜索
                    </button>
                </div>
            </form>
            <div class="layui-form layui-border-box layui-table-view">
                <div class="layui-card-body">
                    <table class="layui-hide" id="list_table" lay-filter="list_table"></table>
                    <script type="text/html" id="table-toolbar">
                        <div class="layui-btn-container">
                            <%@include file="/admin/common/button_action_list.jsp" %>
                        </div>
                    </script>
                </div>
            </div>
        </div>
    </div>
</div>
<div id="dialogId" hidden>
<form class="layui-form"  lay-filter="form" onsubmit="return false;"
      style="padding: 20px 30px 0 0;">
	    <div class="layui-form-item">
	        <label class="layui-form-label">企业用户</label>
	        <div class="layui-input-inline" style="width: 600px">
	            <input type="text" id = "userName" value="" disabled autocomplete="off" class="layui-input" >
	            <input type="hidden" id = "userId" value="" disabled autocomplete="off" class="layui-input" >
	            <input type="hidden" id = "eNo" value="" disabled autocomplete="off" class="layui-input" >
	        </div>
	    </div>
	    <div class="layui-form-item">
	        <label class="layui-form-label">短信内容</label>
	        <div class="layui-input-inline" style="width: 600px">
	            <textarea type="text" name="content" id="editContent" autocomplete="off" onkeyup="smsWordCount()"
	                      class="layui-textarea"></textarea>
	        </div>
	    </div>
	    <div class="layui-form-item">
	        <label class="layui-form-label">字数统计: </label>
	
	        <div class="layui-form-mid layui-word-aux">
	            <dd style="width: 400px;" id="wordCount">
	               
	            </dd>
	        </div>
	    </div>
	    <div class="layui-form-item">
	         <label class="layui-form-label">注意: </label>
	         <div class="layui-form-mid layui-word-aux">
	             <dd style="width: 500px;">包含签名短信小于等于70个字的按70个字一条计费，大于70个字按67字一条计费, 短信内容总字数不能超过 500字
	             </dd>
	         </div>
	     </div>
    </form>
</div>


</body>
<script>
	var $ = layui.$;
    layui.extend({tableExt: '/layuiadmin/extends/tableExt'}).use(['tableExt'], function () {
        var table = layui.tableExt;
        var $ = layui.$;
        table.render({
            url: '/admin/sended_approveInputList',
            height: 'full-100',
            cols: [[
                {checkbox: true},
                {field: 'enterprise_No_ext', title: '企业名称/企业用户',width:200, templet:function (d) {
                    return !d.enterprise_No_ext?'---':handleData(d.enterprise_No_ext.name)
                        +"<br>"+handleData(d.enterprise_User_Id_ext.real_Name)
                        +"("+handleData(d.enterprise_User_Id_ext.user_Name)+")";
                    }},
                {field: 'phone_Nos_Count',width: 150, title: '号码(个)'},
                {field: 'content', title: '消息内容',minWidth: 500, templet:function (d) {
                    var a = "<button class=\"layui-btn layui-btn-xs\" title=\"修改短信\" onclick=\"editSmsContent('"+
                        d.enterprise_No+"','"+d.enterprise_User_Id+"','"+encodeURI(d.content)+"','"+
                        d.enterprise_User_Id_ext.real_Name+"')\" >修改</button>&nbsp;&nbsp;&nbsp;";
                     a+=handleData(d.content);
                     return a;
                 }}
            ]]
        });
    }); 
    //短信修改
    function editSmsContent(enterprise_No,enterprise_User_Id,content,userName) {
        content = decodeURI(content);
       var $ = layui.$;
       $("#userName").val(userName);
       $("#userId").val(enterprise_User_Id);
       $("#editContent").text(content);
       $("#eNo").val(enterprise_No);
       $("#wordCount").html(content.length+"个字符");
       layer.open({
            area: ['800px', '550px'],
            title:'修改短信',
            type: 1,
            content:$("#dialogId"),
            btn: ['提交', '取消'],
            btn1: function () {
            	 var newContent = $("#editContent").val();
            	 var queryButtonUrl = '/admin/sended_editAgreeInput';
                 //查询要补发的数量
                 $.ajax({
                     url: queryButtonUrl,
                     type: 'POST',
                     data: {enterprise_No:enterprise_No,enterprise_User_Id:enterprise_User_Id,content:content,editContentValue:newContent},
                     dataType:'json',
                     success: function (res) {
                         if (res.code == '0') {
                        	 layer.alert('修改成功', {icon: 1}, function (index) {
                                 window.location.reload();
                             });
                         } else {
                             layer.msg(res.msg);
                         }
                     }
                 });
            },
            btn2: function () {
            	  $("#userName").val("");
                  $("#userId").val("");
                  $("#editContent").text("");
                  $("#eNo").val("");
                  $("#wordCount").html("");
            }
        }); 
    }

    function smsWordCount() {
    	var $ = layui.$;
        var fullSms = $("#editContent").val();
        var splitSmsCount = 1;
        if (fullSms.length > 70) {
            splitSmsCount = fullSms.length / 67;
            if (fullSms.length % 67 != 0) {
                splitSmsCount += 1;
            }
        }
        splitSmsCount = Math.floor(splitSmsCount);
        $("#wordCount").text(fullSms.length + '个字符,拆分' + splitSmsCount + '条短信');
    }
    
</script>