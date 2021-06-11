<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/admin/common/common.jsp" %>
<%@ include file="/admin/common/layui_head.html" %>
<script src="/js/jquery-3.4.1.min.js"></script>
<body>
<div class="layui-fluid">
    <div class="layui-row layui-col-space15 layui-fluid">
        <div class="layui-col-md10">
            <div class="layui-card">
                <div class="layui-card-body" pad15>
                    <form id="layuiForm" class="layui-form" action="/admin/task_refreshTask"
                          onsubmit="return false;"
                          style="padding: 15% 20% 1% 10%;">
                        <h3 style="text-align:center"><font color="red" >定时重新执行可能会导致数据错误，非开发人员禁止操作此功能</font></h3>
                        <br>
                        <br>

                        <div class="layui-form-item">
                            <label class="layui-form-label">定时任务:</label>
                            <div class="layui-input-block">
                                <input type="radio" name="taskName" value="smsStatisticsData" title="数据汇总" lay-filter="taskName">
                                <input type="radio" name="taskName" value="dataMigration"  title="迁移数据" lay-filter="taskName">
                                <input type="radio" name="taskName" value="smsSendFailed"  title="失败返还" lay-filter="taskName">
                            </div>
                        </div>

                        <div class="layui-form-item">
                            <label class="layui-form-label">操作日期:</label>
                            <div class="layui-input-block">
                                <div class="layui-inline">
                                    <input name="date" id="date" placeholder="操作日期" class="layui-input layui-input-sm"/>
                                </div>
                            </div>
                        </div>

                        <div class="layui-form-item" id="databaseId" style="display: none" >
                            <label class="layui-form-label">选择库:</label>
                            <div class="layui-input-block">
                                <input type="radio" name="database" value="s_send" title="s_send">
                            </div>
                        </div>

                        <div class="layui-form-item" id="tableId" style="display: none">
                            <label class="layui-form-label">选择表:</label>
                            <div class="layui-input-block">
                                <input type="radio" name="table" value="input_log" title="input_log">
                                <input type="radio" name="table" value="submit" title="submit">
                                <input type="radio" name="table" value="report" title="report">
                            </div>
                        </div>

                        <div class="layui-form-item">
                            <div class="layui-input-block">
                                <a class="layui-btn layui-btn-normal" onclick="submitForm()">
                                    提交
                                </a>
                                <button type="reset" class="layui-btn layui-btn-primary">重置</button>
                            </div>
                        </div>
                    </form>

                </div>
            </div>
        </div>
    </div>
</div>
<%@ include file="/admin/common/layui_bottom.jsp" %>
</body>
<script>
    layui.use('laydate', function () {
        var laydate = layui.laydate;
        laydate.render({
            elem: '#date'
            ,type:'date'
            ,trigger: 'click'
        });
    })
    //提交
    function submitForm() {
        layer.confirm('确定要执行所选择的定时任务吗？', function () {
            $.ajax({
                url: "/admin/task_refreshTask?"+ $("#layuiForm").serialize(),
                dataType:'json',
                success: function (res) {
                    if (res.code == '0') {
                        layer.alert('操作成功',{ icon: 1},function () {
                            $('#layuiForm')[0].reset();
                            layer.closeAll();
                        });
                    }else {
                        layer.alert(res.msg,{ icon: 0}, function () {
                            layer.closeAll();
                        });
                    }
                }
            });
        });
        return false;
    }

    layui.use(['form'], function () {
        var form = layui.form;
        form.on('radio(taskName)', function (data) {
            if(data.value == 'smsStatisticsData'){//数据汇总
                $("#databaseId").attr("style","display:block;");//显示div
                $("#tableId").attr("style","display:none;");//隐藏div
            }else if(data.value == 'dataMigration'){//迁移数据
                $("#tableId").attr("style","display:block;");
                $("#databaseId").attr("style","display:none;");
            }else if(data.value == 'smsSendFailed'){//失败返还
                $("#databaseId").attr("style","display:none;");
                $("#tableId").attr("style","display:none;");
            }
            form.render();//渲染
        });
    })
</script>