<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/admin/common/common.jsp" %>
<%@ include file="/admin/common/layui_head.html" %>

<script>

    function smsWordCount() {
        var $ = layui.$;
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
    smsWordCount();
</script>
<body>
<form class="layui-form" action="/admin/netway_try2Try" lay-filter="form" onsubmit="return false;"
      style="padding: 20px 30px 0 0;">
    <input name="channelNo" value="${param.no}" hidden />
    <div class="layui-form-item">
        <label class="layui-form-label"><font color="red">&nbsp;&nbsp;*</font>测试号码</label>
        <div class="layui-input-inline" style="width: 500px;">
            <textarea placeholder="请输入手机号码，多个手机号用英文逗号分隔" class="layui-textarea" lay-verify="required"
                      name="phoneNos" id="phoneNos" ></textarea>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label"><font color="red">&nbsp;&nbsp;*</font>短信内容</label>
        <div class="layui-input-inline" style="width: 500px;">
            <textarea placeholder="请输入内容" class="layui-textarea" lay-verify="required" onkeyup="smsWordCount()"
                      name="content" id="content"></textarea>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">国家码: </label>
        <div class="layui-input-inline" style="width: 200px;">
            <input type="text" class="layui-input" name="countryCode" value="86" />
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">扩展号: </label>
        <div class="layui-input-inline" style="width: 200px;">
            <input type="text" placeholder="请输入扩展号" class="layui-input"   name="subCode" />
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">字数统计: </label>
        <div class="layui-form-mid layui-word-aux">
            <dd style="width: 500px;" id="wordCount">
                0个字符
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

    <%@ include file="/admin/common/layui_bottom.jsp" %>

</body>