<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/common.jsp" %>
<%@ include file="/common/layui_head.html" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="multipart/form-data; charset=utf-8" />
    <script src="/js/jquery-3.4.1.min.js"></script>
    <script src="/js/jquery-form.js"></script>
</head>
<body>
<div class="layui-fluid" style="padding: 25px;">
    <div class="layui-row layui-col-space15">
        <div class="layui-card"  style="padding: 0px 0px 30px 0px;">
            <div class="layui-tab">
                <ul class="layui-tab-title">
                    <li class="layui-this">提交统计</li>
                    <li>发送统计</li>
                </ul>
                <div class="layui-tab-content" style="padding: 0px">
                    <div class="layui-tab-item layui-show">
                        <iframe src="/admin/charge/input_log_statistics.jsp" width="100%" height="100%" frameborder="0"></iframe>
                    </div>
                    <div id="uploadFileSms" class="layui-tab-item" >
                        <iframe src="/admin/charge/send_statistics.jsp" width="100%" height="100%" frameborder="0"></iframe>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
<script type="text/javascript">
    var minCreateDate = '<c:out value="${minCreateDate}"/>';

    layui.use(['element','jquery'], function() {
        var element = layui.element;
        var $ = jQuery = layui.jquery;
        FrameWH();

        function FrameWH() {
            var h = $(window).height()-110;
            $("iframe").css("height",h+"px");
        }

        $(window).resize(function () {
            FrameWH();
        });

    });
</script>
</html>
