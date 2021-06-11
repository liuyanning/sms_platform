<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/common.jsp"%>
<%@ include file="/admin/common/layui_head.html"%>
<html>
<head>
    <meta http-equiv="Content-Type" content="multipart/form-data; charset=utf-8" />
    <script src="/js/jquery-3.4.1.min.js"></script>
    <script src="/js/jquery-form.js"></script>
</head>
<body>
<div class="layui-fluid">
    <div class="layui-row layui-col-space15">
        <div class="layui-card"  style="padding: 0px 0px 0px 0px;">
            <div class="layui-tab">
                <ul class="layui-tab-title">
                    <li class="layui-this">普通发送</li>
                    <li>文件发送</li>
                </ul>
                <div class="layui-tab-content">
                    <div class="layui-tab-item layui-show">
                        <iframe src="/admin/enterprise/enterprise_user_product_send.jsp?userId=${userId}" width="100%" height="100%" frameborder="0"></iframe>
                    </div>
                    <div id="uploadFileSms" class="layui-tab-item" >
                        <iframe src="/admin/enterprise/enterprise_user_product_send_file.jsp?userId=${userId}" width="100%" height="100%" frameborder="0"></iframe>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
<script type="text/javascript">
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
