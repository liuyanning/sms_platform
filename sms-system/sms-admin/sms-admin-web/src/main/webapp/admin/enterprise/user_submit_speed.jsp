<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/admin/common/common.jsp" %>
<%@ include file="/admin/common/layui_head.html" %>
<script src="${pageContext.request.contextPath}/layuiadmin/lib/extend/echarts.min.js"></script>
<body>
<div class="layui-card layui-fluid">
    <c:forEach items="${ckIds}" var="id">
        <div id="myChart_${id}" name="myChart" class="myChart" tag="${id}"
             style="width: 100%;height:350px;"></div>
    </c:forEach>
</div>
</body>
<script src="/js/common/echarts_common.js"></script>
<script>
    document.title = "用户并发量";
    var $;
    layui.extend({tableExt: '/layuiadmin/extends/tableExt'}).use(['tableExt'], function () {
        $ = layui.$;
    })
    window.onload = function () {
        var loading = loadingMask();//遮罩
        var url = '/admin/enterprise_userSubmitSpeed';
        var title = "用户提交速度";
        var legend = ['提交速度（条/秒）', '回执速度（条/秒）'];
        var seriesType = 'user_submit';
        var idType = "userId";
        loadMultipleCharts(url, loading, title, legend, seriesType, idType);
        setInterval(function () {
            var $ = layui.jquery;
            $('div[name=myChart]').each(function () {
                ajaxDataById($(this).attr('tag'), url, seriesType, idType);
            });
        }, 5 * 1000);
    }
</script>
