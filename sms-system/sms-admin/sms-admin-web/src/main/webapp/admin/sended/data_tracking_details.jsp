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
            <legend>发件箱</legend>
            <c:choose>
                <c:when test="${empty details.inputLogList}">
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;未查到数据
                </c:when>
                <c:otherwise>
                    <c:forEach items="${details.inputLogList}" var="inputLog">
                        <table class="layui-table" lay-filter="table">
                            <thead>
                            <tr>
                                <td width="30%">手机号码</td>
                                <td width="30%">批次号</td>
                                <td width="30%">创建时间</td>
                            </tr>
                            </thead>
                            <tbody>
                            <tr>
                                <td>${inputLog.phone_Nos}</td>
                                <td>${inputLog.msg_Batch_No}</td>
                                <td><fmt:formatDate value="${inputLog.create_Date}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                            </tr>
                            </tbody>
                        </table>
                        <table class="layui-table" lay-filter="table">
                            <thead>
                            <tr>
                                <td width="90%">短信内容</td>
                            </tr>
                            </thead>
                            <tbody>
                                <tr>
                                    <td>${inputLog.content}</td>
                                </tr>
                            </tbody>
                        </table>
                    </c:forEach>
                </c:otherwise>
            </c:choose>
        </fieldset>
        <fieldset class="layui-elem-field">
        <legend>发送记录</legend>
        <c:choose>
            <c:when test="${empty details.submitList}">
                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;未查到数据
            </c:when>
            <c:otherwise>
                <c:forEach items="${details.submitList}" var="submit">
                    <table class="layui-table" lay-filter="table">
                        <thead>
                        <tr>
                            <td width="30%">手机号码</td>
                            <td width="30%">批次号</td>
                            <td width="30%">提交时间</td>
                        </tr>
                        </thead>
                        <tbody>
                        <tr>
                            <td>${submit.phone_No}</td>
                            <td>${submit.msg_Batch_No}</td>
                            <td><fmt:formatDate value="${submit.submit_Date}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                        </tr>
                        </tbody>
                    </table>
                    <table class="layui-table" lay-filter="table">
                        <thead>
                        <tr>
                            <td width="90%">短信内容</td>
                        </tr>
                        </thead>
                        <tbody>
                        <tr>
                            <td>${submit.content}</td>
                        </tr>
                        </tbody>
                    </table>
                </c:forEach>
            </c:otherwise>
        </c:choose>
        </fieldset>
        <fieldset class="layui-elem-field">
            <legend>发送回执</legend>
            <c:choose>
                <c:when test="${empty details.reportList}">
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;未查到数据
                </c:when>
                <c:otherwise>
                    <c:forEach items="${details.reportList}" var="report">
                        <table class="layui-table" lay-filter="table">
                            <thead>
                            <tr>
                                <td width="30%">手机号码</td>
                                <td width="30%">批次号</td>
                                <td width="30%">回执时间</td>
                            </tr>
                            </thead>
                            <tbody>
                            <tr>
                                <td>${report.phone_No}</td>
                                <td>${report.msg_Batch_No}</td>
                                <td><fmt:formatDate value="${report.status_Date}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                            </tr>
                            </tbody>
                        </table>
                        <table class="layui-table" lay-filter="table">
                            <thead>
                            <tr>
                                <td width="90%">短信内容</td>
                            </tr>
                            </thead>
                            <tbody>
                            <tr>
                                <td>${report.content}</td>
                            </tr>
                            </tbody>
                        </table>
                    </c:forEach>
                </c:otherwise>
            </c:choose>
        </fieldset>
        <fieldset class="layui-elem-field">
            <legend>推送记录</legend>
            <c:choose>
                <c:when test="${empty details.notifyList}">
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;未查到数据
                </c:when>
                <c:otherwise>
                    <c:forEach items="${details.notifyList}" var="notify">
                        <table class="layui-table" lay-filter="table">
                            <thead>
                            <tr>
                                <td width="30%">手机号码</td>
                                <td width="30%">批次号</td>
                                <td width="30%">推送时间</td>
                            </tr>
                            </thead>
                            <tbody>
                            <tr>
                                <td>${notify.phone_No}</td>
                                <td>${notify.msg_Batch_No}</td>
                                <td><fmt:formatDate value="${notify.notify_Submit_Date}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                            </tr>
                            </tbody>
                        </table>
                        <table class="layui-table" lay-filter="table">
                            <thead>
                            <tr>
                                <td width="90%">短信内容</td>
                            </tr>
                            </thead>
                            <tbody>
                            <tr>
                                <td>${notify.content}</td>
                            </tr>
                            </tbody>
                        </table>
                    </c:forEach>
                </c:otherwise>
            </c:choose>
        </fieldset>
    </div>
</div>
</body>
