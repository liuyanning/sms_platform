<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<div class="pagelist"><c:out value="pagination.totalCount"/>条&nbsp;&nbsp;&nbsp;&nbsp;<c:out value="pagination.pageCount"/>页&nbsp;&nbsp;&nbsp;&nbsp;第<c:out value="pageIndex"/>页&nbsp;&nbsp;&nbsp;<s:a href="javascript:void(0)" onclick="submitClosest(this,'%{#pageUrl}?pageIndex=%{pageIndex-1}')">上一页</s:a>&nbsp;&nbsp;&nbsp;<s:a href="javascript:void(0)" onclick="submitClosest(this,'%{#pageUrl}?pageIndex=%{pageIndex+1}')">下一页</s:a> &nbsp;&nbsp;&nbsp;
	<input cssStyle="width:20px" name="goto"></input>
	<s:a href="javascript:void(0)" onclick="submitClosest(this,'%{#pageUrl}?pageIndex='+$(\"input[name='goto']\").val())">GO</s:a>	
</div>
	
	
