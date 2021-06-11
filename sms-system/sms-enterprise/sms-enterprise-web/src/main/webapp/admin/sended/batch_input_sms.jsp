<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/common.jsp"%>
<%@ include file="/common/layui_head.html"%>
<html>
<head>
    <meta http-equiv="Content-Type" content="multipart/form-data; charset=utf-8" />
    <link rel="stylesheet" type="text/css" id="send_sms_link"
          international_telephone_code=<ht:heropageconfigurationtext code="international_telephone_code" sortCode="custom_switch" defaultValue="off"/>>
    <script src="/js/jquery-3.4.1.min.js"></script>
    <script src="/js/jquery-form.js"></script>
    <script src='<ht:heropageconfigurationtext code="enterprise_webpage_css" defaultValue="/layuiadmin"/>/layui/layui.all.js'></script>
</head>
<body>
<div class="layui-fluid" style="padding: 0px">
    <div class="layui-col-md12" style="padding: 0px">
        <div class="layui-card"  style="padding: 0px 0px 30px 0px;">
            <div class="layui-card-body" pad15>
                <form class="layui-form" id="subForm" action="/admin/sended_formatSmsSend"
                      enctype="multipart/form-data" method="post" onsubmit="return ajaxSubmitForm()">
                    <input type="hidden" name="mobileFileName" id="mobileFileNameId"/>
                    <div class="layui-form-item">
                        <label class="layui-form-label">号码文件:</label>
                        <div class="layui-input-inline">
                            <button type="button" class="layui-btn" onclick="browsefile.click()">
                                <i class="layui-icon">&#xe67c;</i>
                                <span id="fileName">&nbsp;上&nbsp;传&nbsp;文&nbsp;件&nbsp;&nbsp;&nbsp;</span>
                            </button>
                            <input type="file" name="moblieFile" onchange="setMobileFileName(this)" style="display: none;"
                                   id="browsefile" class="required"/>
                        </div>
                    </div>
                    <div class="layui-form-item">
                        <label class="layui-form-label">&nbsp;&nbsp;&nbsp;&nbsp;</label>
                        <div class="layui-form-mid layui-word-aux" style="width: 60%">
                            (<font color="red">第一行默认为标题不被导入,第一列必须是手机号码。</font>文件大小不查过2M，支持文件格式:Excel2003,Excel2007)
                        </div>
                    </div>
                    <div class="layui-form-item">
                        <label class="layui-form-label">列:</label>
                        <div class="nowrap" style="width: 80%;">
                            <div>
                                <button type="button" onclick="$('#content').insertAtCaret('##B##')">&nbsp;B&nbsp;</button>&nbsp;&nbsp;
                                <button type="button" onclick="$('#content').insertAtCaret('##C##')">&nbsp;C&nbsp;</button>&nbsp;&nbsp;
                                <button type="button" onclick="$('#content').insertAtCaret('##D##')">&nbsp;D&nbsp;</button>&nbsp;&nbsp;
                                <button type="button" onclick="$('#content').insertAtCaret('##E##')">&nbsp;E&nbsp;</button>&nbsp;&nbsp;
                                <button type="button" onclick="$('#content').insertAtCaret('##F##')">&nbsp;F&nbsp;</button>&nbsp;&nbsp;
                                <button type="button" onclick="$('#content').insertAtCaret('##G##')">&nbsp;G&nbsp;</button>&nbsp;&nbsp;
                                <button type="button" onclick="$('#content').insertAtCaret('##H##')">&nbsp;H&nbsp;</button>&nbsp;&nbsp;
                                <button type="button" onclick="$('#content').insertAtCaret('##I##')">&nbsp;I&nbsp;</button>&nbsp;&nbsp;
                            </div>
                        </div>
                    </div>
                    <div id="global_roaming" class="layui-form-item">
                        <label class="layui-form-label">国际区号:</label>
                        <div class="layui-input-inline">
                            <input type="text" name="country_Code" autocomplete="off"
                                   value="86" class="layui-input">
                        </div>
                        <div class="layui-form-mid layui-word-aux">最长5位字符,您所提交的号码无区号时自动拼接</div>
                    </div>
                    <div class="layui-form-item">
                        <label class="layui-form-label">短信内容:</label>
                        <div class="layui-input-inline" style="width: 65%;">
                            <textarea placeholder="包含签名短信小于等于70个字的按70个字一条计费,大于70个字按67字一条计费, 短信内容总字数不能超过 500字" class="layui-textarea" onkeyup="smsWordCount()"
                                      name="content" id="content"></textarea>
                        </div>
                    </div>
                    <div class="layui-form-item">
                        <label class="layui-form-label"></label>
                        <div class="layui-form-mid layui-word-aux">
                            <span style="width: 100%;" id="wordCount">0个字符</span>
                            &nbsp;;&nbsp;&nbsp;签名: ${ADMIN_USER.suffix}
                        </div>
                    </div>
                    <div class="layui-form-item" style="display: none">
                        <label class="layui-form-label">定时发送:</label>
                        <div class="layui-input-inline">
                            <input type="text" name="send_Time" id="send_Time" lay-verify="datetime"
                                   placeholder="yyyy-MM-dd HH:mm:ss" autocomplete="off" class="layui-input">
                        </div>
                        <div class="layui-form-mid layui-word-aux">(不填为即时发送)</div>
                    </div>
                    <div class="layui-form-item">
                        <label class="layui-form-label" style="width: 6%;"></label>
                        <div class="layui-input-inline">
                            <button id="submit_button" class="layui-btn layui-btn-lg">&nbsp;&nbsp;&nbsp;立&nbsp;即&nbsp;发&nbsp;送&nbsp;&nbsp;&nbsp;</button>
                        </div>
                        <div class="layui-input-inline">
                            <button type="button" id="send_Time_button" class="layui-btn layui-btn-lg">&nbsp;&nbsp;&nbsp;定&nbsp;时&nbsp;发&nbsp;送&nbsp;&nbsp;&nbsp;</button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
</body>
<script>
    $(function () {
        var internationalSwitch = $('#send_sms_link').attr('international_telephone_code');
        if('on'!=internationalSwitch){
            $('#global_roaming').remove();
        }
    });
    function smsWordCount() {
        var eSignature = "${ADMIN_USER.suffix}";
        var fullSms = $("#content").val() + eSignature;
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

    function setMobileFileName(obj) {
        $("#mobileFileNameId").attr("value", obj.val());
        $("#fileName").html(event.target.files[0].name);
    }

    (function ($) {
        $.fn.extend({
            insertAtCaret: function (myValue) {
                var $t = $(this)[0];
                if (document.selection) {
                    this.focus();
                    sel = document.selection.createRange();
                    sel.text = myValue;
                    this.focus();
                } else if ($t.selectionStart || $t.selectionStart == '0') {
                    var startPos = $t.selectionStart;
                    var endPos = $t.selectionEnd;
                    var scrollTop = $t.scrollTop;
                    $t.value = $t.value.substring(0, startPos) + myValue + $t.value.substring(endPos, $t.value.length);
                    this.focus();
                    $t.selectionStart = startPos + myValue.length;
                    $t.selectionEnd = startPos + myValue.length;
                    $t.scrollTop = scrollTop;
                } else {
                    this.value += myValue;
                    this.focus();
                }
            }
        })
    })(jQuery);
    layui.use(['element','jquery'], function() {
        var element = layui.element;
        var $ = jQuery = layui.jquery;
        FrameWH();
        function FrameWH() {
            var h = $(window).height()-30;
            if(h<455){h=455}
            $(".layui-card").css("height",h+"px");
        }
        $(window).resize(function () {
            FrameWH();
        });
    });

    layui.use(['laydate'], function () {
        var laydate = layui.laydate;

        //日期
        laydate.render({
            elem: '#date',
            trigger : 'click',
            type: 'datetime'
        });
    });

    smsWordCount();

    $(document).on('click', '#send_Time_button', function () {
        var h = $(window).height()/6;
        layer.open({
            title: '选择日期',
            type: 1,
            icon: 3,
            //skin: 'layui-layer-rim',
            skin: 'layui-layer-molv',
            area: ['500px', '200px'],
            offset: h+'px',
            content: $('#send_Time_item'),
            btn:['确定'],
            success:function(layero, index) {
                layui.use(['laydate'], function () {
                    var laydate = layui.laydate;
                    laydate.render({     //创建时间选择框
                        elem: '#send_Time_input',
                        type: 'datetime',
                        trigger : 'click'
                    });
                });
                layero.find("#send_Time_input").val('');
            },
            yes: function(index, layero){
                var send_Time = layero.find("#send_Time_input").val()
                if(send_Time == null || send_Time.trim() ==""){
                    layer.msg("请选择发送时间");
                    return;
                }
                $("#send_Time").val(layero.find("#send_Time_input").val());
                $("#submit_button").trigger("click");
                layer.close(index); //如果设定了yes回调，需进行手工关闭
            }
        })
    });


    function ajaxSubmitForm() {
        $('.layui-btn').attr('disabled', true);
        var data = $("#subForm").val();
        var loading= layer.load(''); //遮罩层
        $("#subForm").ajaxSubmit({
            type: 'post', // 提交方式 get/post
            url: '/admin/sended_formatSmsSend', // 需要提交的 url
            dataType: 'json',
            data: data,
            success: function (d) {
                $('.layui-btn').attr('disabled', false);
                layer.close(loading);
                if(d.code!=0){
                    layer.alert(d.msg, {icon: 2}, function () {
                        layer.closeAll();
                    });
                    return;
                }
                // 此处可对 data 作相关处理
                layer.alert(d.msg, {icon:1}, function(index){
                    $('#subForm')[0].reset();
                    $("#fileName").html('&nbsp;上&nbsp;传&nbsp;文&nbsp;件&nbsp;&nbsp;&nbsp;');
                    layer.closeAll();
                });
            },
            error: function (d) {
                $('.layui-btn').attr('disabled', false);
                layer.close(loading);
                layer.msg('提交失败,请确认选择文件选择正确的文件', {icon: 2, time: 2000}, function () {
                });
            }
        })
        return false; // 必须返回false，否则表单会自己再做一次提交操作，并且页面跳转
    }
</script>
<div class="layui-form-item" id="send_Time_item" style="display: none">
    <div class="layui-row" style="height: 20px"></div>
    <label class="layui-form-label">定时发送:</label>
    <div class="layui-input-inline">
        <input type="text" name="send_Time" id="send_Time_input" lay-verify="datetime"
               placeholder="yyyy-MM-dd HH:mm:ss" autocomplete="off" class="layui-input">
    </div>
    <div class="layui-form-mid layui-word-aux">(不填为即时发送)</div>
</div>
</html>