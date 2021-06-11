<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@taglib prefix="ht" uri="/hero-tags" %>
<%@ include file="/common/common.jsp" %>
<c:forEach items="${inputLogExtList}" var="bean">
    <c:out value="${bean.short_Message}"/>
    <textarea style="width:100%;height:250px"><c:out value="${bean.mobiles}"/></textarea>
</c:forEach>