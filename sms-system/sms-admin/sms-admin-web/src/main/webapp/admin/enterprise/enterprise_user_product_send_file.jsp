<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/admin/common/common.jsp" %>
<%@ include file="/admin/common/layui_head.html" %>
<script src="/js/jquery-3.4.1.min.js"></script>
<script src="/js/jquery-form.js"></script>

<body>
<div class="layui-fluid" id="LAY-component-nav">
    <div class="layui-row layui-col-space15">
        <div class="layui-col-md9">
            <div class="layui-card">
                <div class="layui-card-body" >
                    <div class="layui-fluid" layoutH="57">
                        <form class="layui-form" id="subForm" action="" onsubmit="return sendSms();">
                            <div class="layui-form-item">
                                <label class="layui-form-label">请选择<font color="red">&nbsp;&nbsp;*</font></label>
                                <div class="layui-input-block">
                                    <input type="file" id="importFile" name="importFile" style="display: none"/>
                                    <button type="button" class="layui-btn" id="buttonId">
                                        <i class="layui-icon">&#xe67c;</i>
                                        <span id="fileName">&nbsp;上&nbsp;传&nbsp;文&nbsp;件&nbsp;&nbsp;&nbsp;</span>
                                    </button>
                                    <br/>
                                    <br/>
                                    支持的文件格式: Excel。<br/>
                                    数据格式：<font color="red">号码,内容。</font><br/>
                                    第一行默认为标题不被导入,列数据对应上述<font color="red">数据格式</font>。
                                </div>
                            </div>
                        </form>
                        <div class="layui-form-item">
                            <label class="layui-form-label" style="width: 8%;"></label>
                            <div class="layui-input-inline">
                                <button class="layui-btn layui-btn-lg" onclick="sendSms()">&nbsp;&nbsp;&nbsp;立&nbsp;即&nbsp;发&nbsp;送&nbsp;&nbsp;&nbsp;</button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">
    var layer;
    layui.use(['layer'], function () {
        layer = layui.layer
    });

    $(document).ready(function(){
        $('#buttonId').click(function(){
            $('#importFile').click();
        });
    });

    //立即发送
    function sendSms() {
        var userId = "<%=request.getParameter("userId")%>";
        var importFile = $("#importFile").val();
        if (!importFile) {
            return layer.msg("上传文件不能为空！");
        }
        $('.layui-btn').attr('disabled', true);
        var loading = layer.load(''); //遮罩层
        $("#subForm").ajaxSubmit({
            type: 'post', // 提交方式 get/post
            url: '/admin/enterprise_testProduct', // 需要提交的 url
            dataType: 'json',
            data: {
                'userId':userId,
                'importFile':$("#importFile").val(),
            },
            success: function (d) {
                $('.layui-btn').attr('disabled', false);
                layer.close(loading);
                if (d.code != 0) {
                    layer.alert(d.msg, {icon: 2}, function () {
                        layer.closeAll();
                    });
                    return;
                }
                layer.alert(d.msg, {icon: 1}, function (index) {
                    $('#subForm')[0].reset();
                    layer.closeAll();
                });
            },
            error: function (d) {
                $('.layui-btn').attr('disabled', false);
                layer.close(loading);
                layer.msg('提交失败', {icon: 2, time: 2000}, function () {
                    layer.closeAll();
                });
            }
        })
        return false; // 必须返回false，否则表单会自己再做一次提交操作，并且页面跳转
    }

</script>
</body>
</html>
