<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/admin/common/common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <meta charset="UTF-8">
    <title>
        <ht:heropageconfigurationtext code="platform_name" defaultValue="信相通"/>
        运营平台
        <ht:heropageconfigurationtext code="platform_version" defaultValue="V2.0.0"/>
    </title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <link rel="stylesheet" href="/layuiadmin/layui/css/layui.css" media="all">
    <link rel="stylesheet" href="/layuiadmin/style/admin.css" media="all">
</head>
<body class="layui-layout-body">

<div id="LAY_app">
    <div class="layui-layout layui-layout-admin">
        <div class="layui-header">
            <!-- 头部区域 -->
            <ul class="layui-nav layui-layout-left">
                <li class="layui-nav-item layadmin-flexible" lay-unselect>
                    <a href="javascript:;" layadmin-event="flexible" title="侧边伸缩">
                        <i class="layui-icon layui-icon-shrink-right" id="LAY_app_flexible"></i>
                    </a>
                </li>
                <li class="layui-nav-item" lay-unselect>
                    <a href="javascript:;" layadmin-event="refresh" title="刷新">
                        <i class="layui-icon layui-icon-refresh-3"></i>
                    </a>
                </li>
            </ul>
            <ul class="layui-nav layui-layout-right" lay-filter="layadmin-layout-right">
                <li class="layui-nav-item  layui-show-xs-inline-block" lay-unselect>
                    <a href="javascript:;">
                        <cite><c:out value="${ADMIN_USER.user_Name}"/>(<c:out value="${ADMIN_USER.real_Name}"/>)</cite>
                    </a>
                    <dl class="layui-nav-child ">
                        <dd style="text-align: center;"><a lay-href="/admin/document/responsibility_notification_document.jsp">信息安全</a></dd>
                        <hr>
                        <dd layadmin-event="logout" style="text-align: center;"><a lay-href="/public/logout">退出</a></dd>
                    </dl>
                </li>
            </ul>
        </div>

        <!-- 侧边菜单 -->
        <div class="layui-side layui-side-menu">
            <div class="layui-side-scroll">
                <div class="layui-logo">
                    <span>菜单目录</span>
                </div>
                <ul class="layui-nav layui-nav-tree" lay-shrink="all" id="LAY-system-side-menu"
                    lay-filter="layadmin-system-side-menu">
                    <c:forEach items="${ADMIN_USER.limits}" var="i" varStatus="st">
                        <c:if test="${i.type_Code=='menu'}">
                            <c:if test="${i.up_Code=='00'}">
                                <c:if test="${!st.first}"></dl></li></c:if>
                                <li data-name="<c:out value="${i.remark}"/>" class="layui-nav-item" />
                                <a href="javascript:;" lay-tips="<c:out value="${i.code}"/>" lay-direction="2">
                                    <i class="layui-icon <c:out value="${i.icon}"/>"></i>
                                    <cite><c:out value="${i.name}"/></cite>
                                </a>
                                <dl class="layui-nav-child">
                            </c:if>
                            <c:if test="${i.up_Code!='00'}">
                                <dd data-name="<c:out value="${i.code}"/>">
                                    <a lay-href="<c:out value="${i.url}"/>?<c:out value="${i.url_Param}"/>&limitCode=<c:out value="${i.code}"/>"><c:out
                                            value="${i.name}"/></a>
                                </dd>
                            </c:if>
                        </c:if>
                        <c:if test="${st.last}"></dl></li></c:if>
                    </c:forEach>
                </ul>
            </div>
        </div>

        <!-- 页面标签 -->
        <div class="layadmin-pagetabs" id="LAY_app_tabs">
            <div class="layui-icon layadmin-tabs-control layui-icon-prev" layadmin-event="leftPage"></div>
            <div class="layui-icon layadmin-tabs-control layui-icon-next" layadmin-event="rightPage"></div>
            <div class="layui-icon layadmin-tabs-control layui-icon-down">
                <ul class="layui-nav layadmin-tabs-select" lay-filter="layadmin-pagetabs-nav">
                    <li class="layui-nav-item" lay-unselect>
                        <a href="javascript:;"></a>
                        <dl class="layui-nav-child layui-anim-fadein">
                            <dd layadmin-event="closeThisTabs"><a href="javascript:;">关闭当前标签页</a></dd>
                            <dd layadmin-event="closeOtherTabs"><a href="javascript:;">关闭其它标签页</a></dd>
                            <dd layadmin-event="closeAllTabs"><a href="javascript:;">关闭全部标签页</a></dd>
                        </dl>
                    </li>
                </ul>
            </div>
            <div class="layui-tab" lay-unauto lay-allowClose="true" lay-filter="layadmin-layout-tabs">
                <ul class="layui-tab-title" id="LAY_app_tabsheader">
                    <li class="layui-this"><i class="layui-icon layui-icon-home"></i></li>
                </ul>
            </div>
        </div>
        <!-- 主体内容 -->
        <div class="layui-body" id="LAY_app_body">
            <div class="layadmin-tabsbody-item layui-show">
                 <iframe src="/admin/index" frameborder="0" class="layadmin-iframe"></iframe>
            </div>
        </div>
        <!-- 辅助元素，一般用于移动设备下遮罩 -->
         <div class="layadmin-body-shade" layadmin-event="shade"></div>
    </div>
</div>
</body>
</html>
<script src="/layuiadmin/layui/layui.js"></script>
    <script>
        layui.config({
            base: '/layuiadmin/',//静态资源所在路径
        }).extend({
            index: 'lib/index' //主入口模块
        }).use('index');
    </script>