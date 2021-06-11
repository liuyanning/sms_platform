<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/admin/common/common.jsp" %>
<%@ include file="/admin/common/layui_head.html" %>
<script src="${pageContext.request.contextPath}/layuiadmin/lib/extend/echarts.min.js"></script>
<body>
<div class="layui-card layui-fluid">
    <div id="myChart" name="myChart"></div>
</div>
</body>
<script src="/js/common/echarts_common.js"></script>
<script>
    document.title = "网关并发量";
    document.getElementById("myChart").style.height = (window.screen.height * 0.8) + "px";
    var $;
    layui.extend({tableExt: '/layuiadmin/extends/tableExt'}).use(['tableExt'], function () {
        $ = layui.$;
    })
    var myChart;
    window.onload = function () {
        var loading = loadingMask();//遮罩
        var url = '/admin/enterprise_totalSubmitSpeed';
        var title = "网关并发图";
        var legend = ['提交速度（条/秒）', '回执速度（条/秒）'];
        var seriesType = 'user_submit_total';
        initData(url, loading, title, legend, seriesType);
        setInterval(function () {
            ajaxData(url, seriesType);
        }, 5 * 1000);
    }
</script>