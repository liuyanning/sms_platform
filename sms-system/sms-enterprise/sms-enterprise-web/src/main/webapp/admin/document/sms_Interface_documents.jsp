<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/common/common.jsp" %>
<%@ include file="/common/layui_head.html" %>
<body class="layui-layout-body">
<div class="layui-fluid">
    <div class="layui-row layui-col-space10">
        <div class="layui-col-sm2">
            <div class="layui-card">
                <div id="treeDemo_m"></div>
            </div>
        </div>
        <div class="layui-col-sm10">
            <div class="layui-card">
                <div class="layui-card-body">
                    <div class="layui-fluid">
                        <div class="layui-row layui-col-space15">
                            <div class="layui-col-md12">
                                <div class="layui-card">
                                    <div class="layui-form layui-border-box layui-table-view">
                                        <div class="layui-card-body">
                                            <div id="externalHtml"></div>
                                            <div  class="layui-card-body"><iframe style="height: 88%;width: 100%" id="iframeUrlId" src="/admin/document/sms_Interface_document.jsp"></iframe></div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>

<script>
    layui.use(['tree', 'util'], function () {
        var $ = layui.$;
        var tree = layui.tree
            , layer = layui.layer
            , util = layui.util

        var treeData = '[{"title":"接口文档","url":"/admin/document/sms_Interface_document.jsp","spread":"true","children":[';
        treeData += '{"title":"短信接口","url":"/admin/document/sms_Interface_document.jsp"},';
        treeData += '{"title":"短信提交接口","url":"/admin/document/submit_Interface_document.jsp"},';
        treeData += '{"title":"余额查询接口","url":"/admin/document/balace_Interface_document.jsp"},';
        treeData += '{"title":"状态报告查询接口","url":"/admin/document/report_Interface_document.jsp"},';
        treeData += '{"title":"短信回复查询接口","url":"/admin/document/mo_Interface_document.jsp"},';
        treeData += '{"title":"状态码说明","url":"/admin/document/status_code_document.jsp"}';
        treeData += "]}]";
        var d = JSON.parse(treeData);
        tree.render({
            elem: '#treeDemo_m'
            , data: d
            // ,spread: true
            // , showCheckbox: true  //是否显示复选框
            , click: function (obj) {
                var data = obj.data;  //获取当前点击的节点数据
                var url = data.url;
                $("#iframeUrlId").prop('src',url);
            }
        });
    });


</script>
</body>



