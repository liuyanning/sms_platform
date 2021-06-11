<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/admin/common/common.jsp" %>
<%@ include file="/admin/common/layui_head.html" %>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=8">
    <meta http-equiv="Expires" content="0">
    <meta http-equiv="Pragma" content="no-cache">
    <meta http-equiv="Cache-control" content="no-cache">
    <meta http-equiv="Cache" content="no-cache">
</head>
<body>
<div style="padding: 5px 20px 5px 20px;">
    <div class="layui-card" style="padding: 10px 20px 10px 20px;">
        <fieldset class="layui-elem-field">
            <legend>连接详情</legend>
            <table class="layui-table" lay-filter="table">
                <thead>
                <tr>
                    <td width="25%">网关IP</td>
                    <td width="25%">协议</td>
                    <td width="25%">用户IP</td>
                    <td width="25%">连接数</td>
                </tr>
                </thead>
                <tbody>
                <c:if test="${empty resultData}">
                    <tr><td colspan="4" align="center">无数据</td></tr>
                </c:if>
                <c:forEach var="item" items="${resultData}">
                    <c:forEach var="entity" items="${item.detailMap}">
                        <tr>
                            <td><c:out value="${item.netwayIp}"/></td>
                            <td><c:out value="${item.protocol}"/></td>
                            <td><c:out value="${entity.userIp}"/></td>
                            <td><c:out value="${entity.linkCount}"/></td>
                        </tr>
                    </c:forEach>
                </c:forEach>
                </tbody>
            </table>
        </fieldset>
    </div>
</div>
</body>
