<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/admin/common/common.jsp" %>
<%@ include file="/admin/common/layui_head.html" %>
<body>
<form class="layui-form" id="layui-form" action="/admin/enterprise_editUser" lay-filter="form" onsubmit="return false;"
      style="padding: 20px 30px 0 0;">
    <input hidden name="id" value="<c:out value="${enterpriseUser.id}"/>"/>
    <div class="layui-form-item">
        <label class="layui-form-label">用户名称<font color="red">&nbsp;&nbsp;*</font></label>
        <div class="layui-input-inline">
            <input type="text"  value="<c:out value="${enterpriseUser.real_Name}"/>"  disabled="disabled" class="layui-input" >
        </div>
    </div>
    <div class="layui-form-item">
        <div class="layui-form-item">
            <label class="layui-form-label">比率</label>
            <div class="layui-input-inline" style="width: 500px">
                <input type="number" name="deduct_Rate"value="<c:out value="${enterpriseUser.deduct_Rate}"/>"  lay-verify="number" class="layui-input"  style="width: 100px;display:inline-block;" step="0.01" oninput="if(value>1)value=1;if(value.length>4)value=value.slice(0,4);if(value<=0)value=0.00"/>
                <span style="color: red;width: 500px;display: block;">
                    例：0.01，系统按0.01概率扣除，100个手机号码概率扣除1条。
         		</span>
            </div>
        </div>

    </div>
    <div class="layui-form-item layui-hide">
        <input type="button" lay-submit lay-filter="submit" id="layuiadmin-app-form-submit" value="确认">
    </div>
</form>
<%@ include file="/admin/common/layui_bottom.jsp" %>
</body>
<script>

    $scope.restrictInp = function(e) {
        var reg = /^(0.\d+|0|1)$/;
        if (reg.test(e.target.value)) {
            $scope.deptExpressFeeVo.configValue = e.target.value;
        } else {
            if (e.target.value != "0.")
                $scope.deptExpressFeeVo.configValue = "";
        }
    };
</script>