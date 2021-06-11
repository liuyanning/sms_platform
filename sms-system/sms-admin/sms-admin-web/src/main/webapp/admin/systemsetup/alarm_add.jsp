<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<script src="/js/jquery-3.4.1.min.js"></script>
<%@ include file="/admin/common/common.jsp" %>
<%@ include file="/admin/common/layui_head.html" %>
<body>
<form class="layui-form" action="/admin/business_addAlarm" lay-filter="form" onsubmit="return false;"
      style="padding: 20px 30px 0 0;">
    <div class="layui-form-item">
        <label class="layui-form-label">告警类型<font color="red">&nbsp;&nbsp;*</font></label>
        <div class="layui-input-inline">
            <ht:herocodeselect sortCode="alarm_type" name="type_Code"/>
        </div>
        <label class="layui-form-label">告警间隔<font color="red">&nbsp;&nbsp;*</font></label>
        <div class="layui-input-inline">
            <ht:herocodeselect sortCode="alarm_probe_time" name="probe_Time" id="probe_Time"/>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">告警次数<font color="red">&nbsp;&nbsp;*</font></label>
        <div class="layui-input-inline">
            <ht:herocodeselect sortCode="alarm_total" name="max_Alarm_Total"/>
        </div>
        <label class="layui-form-label">恢复短信<font color="red">&nbsp;&nbsp;</font></label>
        <div class="layui-input-inline">
            <ht:herocodeselect sortCode="006" name="recovery_Notify"/>
        </div>
    </div>
    <div class="layui-form-item" id="threshold_Value">
        <label class="layui-form-label">告警阈值<font color="red">&nbsp;&nbsp;*</font></label>
        <div class="layui-input-block" style="width:70%">
            <input type="text" maxlength="128" name="threshold_Value" placeholder="根据告警类型输入对应值" autocomplete="off" class="layui-input"
                   lay-verify="required">
            <div class="layui-word-aux" style="width:90%">
                (说明：百分比类型输入两位整数。例：90，则值为90%。其他类型值原样输入)
            </div>
        </div>
    </div>
    <div class="layui-form-item" id="monitorTime" style="display: none">
        <label class="layui-form-label">开始时间<font color="red">&nbsp;&nbsp;*</font></label>
        <div class="layui-input-inline">
            <input type="text" class="layui-input" id="monitorStartTime" name="monitorStartTime" autocomplete="off" placeholder="HH:mm:ss">
        </div>
        <label class="layui-form-label">结束时间<font color="red">&nbsp;&nbsp;</font></label>
        <div class="layui-input-inline">
            <input type="text" class="layui-input" id="monitorEndTime" name="monitorEndTime" autocomplete="off" placeholder="HH:mm:ss">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label" id="bindBaseDiv" >告警对象<font color="red">&nbsp;&nbsp;*</font></label>
        <div id="bindAccountDiv" style="display: none">
            <div class="layui-input-inline" style="width:70%">
                <ht:herocustomdataselect dataSourceType="allEnterpriseUsers" name="bind_Value_User_Id"/>
            </div>
        </div>
        <div id="bindAgentDiv" style="display: none">
            <div class="layui-input-inline" style="width:70%">
                <ht:herocustomdataselect dataSourceType="allAgent" name="bind_Value_Agent_No"/>
            </div>
        </div>
        <div id="bindChannelDiv" style="display: none">
            <div class="layui-input-inline" style="width:70%">
                <ht:herocustomdataselect dataSourceType="allChannels" name="bind_Value_Channel_No"/>
            </div>
        </div>
        <div id="bindProductDiv" style="display: none">
            <div class="layui-input-inline" style="width:70%">
                <ht:herocustomdataselect dataSourceType="allProducts" name="bind_Value_Product_No"/>
            </div>
        </div>
        <div id="bindInputDiv" style="display: none">
            <div class="layui-input-inline" style="width:70%">
                <ht:herocustomdataselect dataSourceType="allAdmin" name="bind_Value_Input_Id"/>
            </div>
        </div>
        <div id="bindServerDiv">
            <div class="layui-input-inline" style="width: 70%;">
                <textarea maxlength="128" placeholder="请输入服务器ip地址" class="layui-textarea" name="bind_Value_Server_Ip"></textarea>
                <div class="layui-word-aux" style="width:70%">
                    (服务器ip地址,多个ip逗号分割)
                </div>
            </div>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">手机号码</label>
        <div class="layui-input-inline" style="width: 70%;">
            <textarea maxlength="128" placeholder="请输入手机号码" class="layui-textarea" name="phone_Nos" ></textarea>
            <div class="layui-word-aux" style="width:70%">
                (多个号码逗号分割,最多10个号码)
            </div>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">邮箱</label>
        <div class="layui-input-inline" style="width: 70%;">
            <textarea maxlength="128" placeholder="请输入邮箱地址" class="layui-textarea" name="emails"></textarea>
            <div class="layui-word-aux" style="width:70%">
                (多个邮箱地址逗号分割,最多8个邮箱地址)
            </div>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">微信</label>
        <div class="layui-input-inline">
            <ht:herocodeselect sortCode="wechat_party" name="wechat"/>
        </div>
        <label class="layui-form-label">钉钉</label>
        <div class="layui-input-inline">
            <ht:herocodeselect sortCode="ding_talk_party" name="ding_Talk"/>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">描述</label>
        <div class="layui-input-block" style="width: 70%;">
            <textarea type="text" maxlength="512" name="description" autocomplete="off" class="layui-textarea"></textarea>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">备注</label>
        <div class="layui-input-block" style="width: 70%;">
            <textarea type="text" maxlength="512" name="remark" autocomplete="off" class="layui-textarea"></textarea>
        </div>
    </div>
    <div class="layui-form-item layui-hide">
        <input type="button" lay-submit lay-filter="submit" id="layuiadmin-app-form-submit" value="确认">
    </div>
</form>
<%@ include file="/admin/common/layui_bottom.jsp" %>
</body>
<script>
    $(function(){
        $("select[name='type_Code']").attr('lay-filter','onselect');
        layui.use(['form'], function () {
            var form = layui.form;
            form.render('select');//渲染
        })
    })
    layui.use('laydate', function(){
        var laydate = layui.laydate;
        laydate.render({     //创建时间选择框
            elem: '#monitorStartTime' //指定元素
            ,type:'time'
            ,trigger : 'click'
            ,done: function (value, date, endDate) {
                var startDate = parseInt(value.replace(":" , ""));
                var endTime = parseInt($('#monitorEndTime').val().replace(":" , ""));
                console.log(endTime)
                if(!isNaN(endTime) &&endTime <= startDate) {
                    layer.msg('开始时间不能大于结束时间');
                }
            }
        });
        laydate.render({     //创建时间选择框
            elem: '#monitorEndTime' //指定元素
            ,type:'time'
            ,trigger : 'click'
            ,done: function (value, date, endDate) {
                var startDate = parseInt($('#monitorStartTime').val().replace(":" , ""));
                var endTime = parseInt(value.replace(":" , ""));
                if(isNaN(startDate)){
                    layer.msg('开始时间不能为空');
                }
                if(endTime <= startDate) {
                    layer.msg('结束时间不能小于开始时间');
                }
            }
        });
    });
    layui.use(['form'], function () {
        var form = layui.form;
        //监听选中事件
        form.on('select(onselect)', function (data) {
            var type_Code = $("select[name='type_Code']").val();
            if(type_Code){
                fixProbeTime(false)
                var splits = type_Code.split("_");
                $("#bindAccountDiv").attr("style","display:none;");//隐藏div
                $("#bindChannelDiv").attr("style","display:none;");//隐藏div
                $("#bindProductDiv").attr("style","display:none;");//隐藏div
                $("#bindServerDiv").attr("style","display:none;");//隐藏div
                $("#bindInputDiv").attr("style","display:none;");//隐藏div
                $("#monitorTime").attr("style","display:none;");//隐藏div
                $("#bindAgentDiv").attr("style","display:none;");//隐藏div
                $("#threshold_Value").attr("style","display:block;");//显示div
                $('input[name="threshold_Value"]').val('');
                if("channel" == splits[0]){//通道
                    $("#bindChannelDiv").attr("style","display:block;");//显示div
                    $("select[name='bind_Value_User_Id']").val('');
                }else if("account" == splits[0]){//账号
                    fixProbeTime(true);
                    $("#bindAccountDiv").attr("style","display:block;");//显示div
                    $("select[name='bind_Value_Channel_No']").val('');
                }else if("server" == splits[0]) {//服务器
                    $("#bindServerDiv").attr("style","display:block;");//显示div
                    $("select[name='bind_Value_User_Id']").val('');
                    $("select[name='bind_Value_Channel_No']").val('');
                }else if("product" == splits[0]) {//服务器
                    $("#bindProductDiv").attr("style","display:block;");//显示div
                    $("select[name='bind_Value_User_Id']").val('');
                    $("select[name='bind_Value_Channel_No']").val('');
                }else if("sended" == splits[0]){//短信审核
                    $("#bindInputDiv").attr("style","display:block;");//显示div
                    $("select[name='bind_Value_User_Id']").val('');
                    $("select[name='bind_Value_Channel_No']").val('');
                }else if("sort" == splits[0] || "submit" == splits[0]){//队列
                    $("#bindBaseDiv").attr("style","display:none;");//隐藏div
                    $("select[name='bind_Value_Channel_No']").val('');
                }else if("user" == splits[0]){//用户未提交数据预警
                    $("#bindAccountDiv").attr("style","display:block;");//显示div
                    $("#monitorTime").attr("style","display:block;");//显示div
                    $("select[name='bind_Value_Channel_No']").val('');
                    $("#threshold_Value").attr("style","display:none;");//隐藏div
                    $('input[name="threshold_Value"]').val(0)
                }else if("audit" == splits[0]){//审核
                    $("#bindBaseDiv").attr("style","display:none;");//隐藏div
                }

                //除这几个之外的展示
                if(!("sort" == splits[0] || "submit" == splits[0] || "audit" == splits[0])){
                    $("#bindBaseDiv").attr("style","display:block;");//显示div
                }
            }
            form.render('select');//渲染
        });
    });
    function fixProbeTime(flag) {
        if(flag){
            $("#probe_Time").find("option[value='10']").prop("selected",true);
            $("#probe_Time").prop("disabled",true);   //选中不可点击
        }else{
            $("#probe_Time").prop("disabled",false);
        }
    }
</script>