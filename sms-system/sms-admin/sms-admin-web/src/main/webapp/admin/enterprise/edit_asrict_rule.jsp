<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/admin/common/common.jsp" %>
<%@ include file="/admin/common/layui_head.html" %>
<head>
    <meta http-equiv="Content-Type" content="multipart/form-data; charset=utf-8"/>
    <script src="/js/jquery-3.4.1.min.js"></script>
    <script src="/layuiadmin/layui/layui.all.js"></script>
    <script src="/js/jquery-form.js"></script>
</head>
<body>

<form class="layui-form" action="/admin/enterprise_editUser" id="subForm" onsubmit="return false;"
      enctype="multipart/form-data" method="post" style="padding: 20px 30px 0 0;">
    <input type="hidden" name="id" value="<c:out value="${enterpriseUser.id}"/>"/>
    <input name="limit_Repeat" id = "limit_Repeat" type="hidden"/>
    <input name="sourceFlag" value="1" type="hidden"/>
    <div class="layui-form-item">
        <label class="layui-form-label">用户名称<font color="red">&nbsp;&nbsp;*</font></label>
        <div class="layui-input-inline">
            <input type="text"  value="<c:out value="${enterpriseUser.real_Name}"/>"  disabled="disabled" class="layui-input" >
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">同一手机号限制</label>
        <div class="layui-input-block"  style="width:930px;">
            <c:forEach items='${limitCode}' var="i">
                <div class="layui-input-inline" style="width:90px;">
                    <input type="text" class="layui-input" style="width:80px;"
                    <c:forEach items="${phoneNos}" var="phone">
                    <c:if test="${phone.key eq i.value}">
                           value = ${phone.value}
                           </c:if>
                           </c:forEach>
                                   name="phoneNos" id="phone${i.value}" ><font>${i.name}</font>
                </div>
            </c:forEach>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">同内容限制</label>
        <div class="layui-input-block"  style="width:930px;">
            <c:forEach items='${limitCode}' var="i">
                <div class="layui-input-inline" style="width:90px;">
                    <input type="text" class="layui-input" style="width:80px;"
                    <c:forEach items="${content}" var="cont">
                    <c:if test="${cont.key eq i.value}">
                           value = ${cont.value}
                           </c:if>
                           </c:forEach>
                                   name="content" id="content${i.value}"><font>${i.name}</font>
                </div>
            </c:forEach>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">开始时间</label>
        <div class="layui-input-inline">
            <input class="layui-input" value="${First_Group_Begin_Time}" id="firstGroupBeginTime" autocomplete="off" name="firstGroupBeginTime">
        </div>
        <label class="layui-form-label">结束时间</label>
        <div class="layui-input-inline">
            <input class="layui-input" value="${First_Group_End_Time}"  autocomplete="off" id="firstGroupEndTime" name="firstGroupEndTime">
        </div>

    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">开始时间</label>
        <div class="layui-input-inline">
            <input class="layui-input" value="${Second_Group_Begin_Time}" autocomplete="off" id="secondGroupBeginTime" name="secondGroupBeginTime">
        </div>
        <label class="layui-form-label">结束时间</label>
        <div class="layui-input-inline">
            <input class="layui-input" value="${Second_Group_End_Time}" autocomplete="off" id="secondGroupEndTime" name="secondGroupEndTime">
        </div>
    </div>
    <div class="layui-form-item layui-hide">
        <input type="submit"  id="layuiadmin-app-form-submit" onclick="ajaxSubmitForm();" value="确认">
    </div>
</form>
<%@ include file="/admin/common/layui_bottom.jsp" %>
</body>
<script>

    layui.use(['laydate'], function () {
        var laydate = layui.laydate;
        laydate.render({     //创建时间选择框
            elem: '#firstGroupBeginTime' //指定元素
            , type: 'time'
            , trigger: 'click'
        });
        laydate.render({     //创建时间选择框
            elem: '#firstGroupEndTime' //指定元素
            , type: 'time'
            , trigger: 'click'
        });
        laydate.render({     //创建时间选择框
            elem: '#secondGroupBeginTime' //指定元素
            , type: 'time'
            , trigger: 'click'
        });
        laydate.render({     //创建时间选择框
            elem: '#secondGroupEndTime' //指定元素
            , type: 'time'
            , trigger: 'click'
        });
    });

    function ajaxSubmitForm() {
        var $ = layui.$;
        var arr = $("input[name='phoneNos']") ;
        var totalStr = '';
        var str = '';
        for(var i=0;i<arr.length;i++){
            var val=$(arr[i]).val();//获取value值
            var id=$(arr[i]).attr("id");//获取id值
            var oldId = id.substring(5,id.length);
            if(val != null && val != ''){
                if(i == (arr.length - 1)){
                    str += '"'+oldId+'"' + ":" + val;
                } else {
                    str += '"'+oldId+'"' + ":" + val + ",";
                }
            }
        }
        if(str != ''){
            var mark = str.substring(str.length-1,str.length);
            if(mark.indexOf(",") >= 0 ) {
                str = str.substring(0,str.length-1);
            }
        }
        str = '{"phoneNos":{'+str+"},";
        var contentarr = $("input[name='content']") ;
        var contentstr = '';
        for(var i=0;i<contentarr.length;i++){
            var val=$(contentarr[i]).val();//获取value值
            var id=$(contentarr[i]).attr("id");//获取id值
            var oldId = id.substring(7,id.length);
            if(val != null && val != ''){
                if(i == (contentarr.length - 1)){
                    contentstr += '"'+oldId+'"' + ":" + val;
                } else {
                    contentstr += '"'+oldId+'"' + ":" + val + ",";
                }
            }
        }
        if(contentstr != ''){
            var contentmark = contentstr.substring(contentstr.length-1,contentstr.length);
            if(contentmark.indexOf(",") >= 0 ) {
                contentstr = contentstr.substring(0,contentstr.length-1);
            }
        }
        contentstr = '"content":{'+contentstr+"}}";
        totalStr = str + contentstr;
        $("#limit_Repeat").val(totalStr);
        if (!checkSendTime()){
            return false;
        };
        var data = $("#subForm").serialize();
        $.ajax({
            type: 'post', // 提交方式 get/post
            url: '/admin/enterprise_editUser', // 需要提交的 url
            dataType: 'json',
            data: data,
            success: function (d) { // data 保存提交后返回的数据，一般为 json 数据
                // 此处可对 data 作相关处理
                layer.msg('提交成功', {icon: 1, time: 2000}, function () {
                    $('#subForm')[0].reset();
                    parent.layer.closeAll();
                });
            },
            error: function (d) {
                layer.msg('提交失败', {icon: 2, time: 2000}, function () {
                });
            }
        })
        return false; // 必须返回false，否则表单会自己再做一次提交操作，并且页面跳转
    }

    function checkSendTime() {
        if ($("#firstGroupBeginTime").val() || $("#firstGroupEndTime").val()) {
            if ($("#firstGroupBeginTime").val() && $("#firstGroupEndTime").val()) {
                if ($("#firstGroupBeginTime").val() > $("#firstGroupEndTime").val()) {
                    layer.alert("开始时间不能大于结束时间");
                    return false;
                }
            }else {
                layer.alert("开始时间和结束时间必须对应");
                return false;
            }
        }
        if ($("#secondGroupBeginTime").val() || $("#secondGroupEndTime").val()) {
            if ($("#secondGroupBeginTime").val() && $("#secondGroupEndTime").val()) {
                if ($("#secondGroupBeginTime").val() > $("#secondGroupEndTime").val()) {
                    layer.alert("开始时间不能大于结束时间");
                    return false;
                }
            }else {
                layer.alert("开始时间和结束时间必须对应");
                return false;
            }
        }
        return true;
    }

</script>