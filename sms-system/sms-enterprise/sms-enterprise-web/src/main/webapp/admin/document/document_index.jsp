<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">

</head>
<br/>
<br/>
<br/>
    已为您打开在线文档，可能会被您的浏览器拦截，您可以放开拦截，另外您还可以复制以下链接，用浏览器打开
<br/>
<br/>
<br/>
<c:out value="${documentUrl}"/>
<script>
    window.onload = function () {
        window.open('${documentUrl}','_blank','');
    }

</script>
</html>
