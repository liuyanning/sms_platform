<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/admin/common/common.jsp" %>
<%@ include file="/admin/common/layui_head.html"%>
<script src="/js/jquery-3.4.1.min.js"></script>
<body>
<form class="layui-form" action="/admin/sended_editInput" lay-filter="form" onsubmit="return false;"
      style="padding: 20px 30px 0 0;">
    <input type="text" name="id" value="<c:out value="${inputExt.id}"/>" hidden >
    <div class="layui-form-item">
        <label class="layui-form-label">企业用户</label>
        <div class="layui-input-inline" style="width: 600px">
            <input type="text" value="<c:out value="${inputExt.enterpriseUser.real_Name}"/>" disabled autocomplete="off" class="layui-input" >
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">短信内容</label>
        <div class="layui-input-inline" style="width: 600px">
            <textarea type="text"  name="content" id="content" autocomplete="off" onkeyup="smsWordCount()"
                      class="layui-textarea">${inputExt.content}</textarea>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">字数统计: </label>

        <div class="layui-form-mid layui-word-aux">
            <dd style="width: 400px;" id="wordCount">
                ${inputExt.content.length()}个字符
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
    <div class="layui-form-item layui-hide">
        <input type="button" lay-submit lay-filter="submit" id="layuiadmin-app-form-submit" value="确认">
    </div>
</form>
<%@ include file="/admin/common/layui_bottom.jsp"%>
<script >

    layui.use('laydate', function(){
        var laydate = layui.laydate;
        laydate.render({     //创建时间选择框
            elem: '#send_End_Time' //指定元素
            ,type:'datetime'
            ,trigger : 'click'
        });
        laydate.render({     //创建时间选择框
            elem: '#send_Begin_Time' //指定元素
            ,type:'datetime'
            ,trigger : 'click'
        });
    });
    function smsWordCount() {
        var fullSms = $("#content").val();
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
</body>