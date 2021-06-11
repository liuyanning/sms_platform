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
                <!-- <legend>错误编码</legend> -->
                <table class="layui-table" lay-filter="table">
                    <thead>
                    	<c:if test="${!empty realName}">
                    		<tr><th colspan="4">用户：${realName}</th></tr>
                    	</c:if>
					  
                    <tr>
                        <td width="30%">通道名称</td>
                        <td width="30%">错误码</td>
                        <td width="20%">数量</td>
                        <td width="20%">占比</td>
                    </tr>
                    </thead>
                    <tbody>
                  	<c:forEach items="${details}" var="entity">
						<tr>
							<td><c:out value="${entity.channel_No}"/></td>
		                    <td><c:out value="${entity.native_Status}"/></td>
		                    <td><c:out value="${entity.count_Total}"/></td>
		                    <td><c:out value="${entity.faild_Code_Rate}%"/></td>
	                    </tr>
					</c:forEach>
                    
                    </tbody>
                </table>
            </fieldset>
        </div>
    </div> 
</body>

