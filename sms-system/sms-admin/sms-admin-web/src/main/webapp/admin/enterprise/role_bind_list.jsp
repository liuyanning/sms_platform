<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<%@ include file="/admin/common/common.jsp" %>
<%@ include file="/admin/common/layui_head.html" %>
<body>
<form class="layui-form" action="/admin/enterprise_bindUserRole" onsubmit="return false;" style="padding: 20px 30px 0 0;">
    <div class="layui-form-item">
        <input type="hidden" name="ckIds" value="<c:out value="${userId}"/>">
        <label class="layui-form-label">角色</label>
        <div class="layui-input-block" id="roles">
            <c:forEach items="${enterpriseRoleExtList}" var="st" varStatus="i">
                <input type="checkbox"
                    <c:out value="${st.enterpriseUserInfo!=null?'checked=true': ''}"/> name="ckRoleId" lay-skin="primary"
                       title="<c:out value="${st.name}"/>" value="<c:out value="${st.id}"/>">
            </c:forEach>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">描述</label>
        <div class="layui-input-block">
            <textarea type="text" name="desc" autocomplete="off" class="layui-textarea"></textarea>
        </div>
    </div>
    <div class="layui-form-item layui-hide">
        <input type="submit" lay-submit lay-filter="submit" id="layuiadmin-app-form-submit" value="确认">
    </div>
</form>
<%@ include file="/admin/common/layui_bottom.jsp" %>
</body>

