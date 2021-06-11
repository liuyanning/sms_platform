<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/admin/common/common.jsp" %>
<%@ include file="/admin/common/layui_head.html" %>
<%@ include file="/admin/common/dynamic_data.jsp" %>
<%@ include file="/admin/common/country_operator.jsp" %>
<body>
<div class="layui-fluid">
        <div class="layui-col-md12">
            <div class="layui-card">
                <form id="layuiForm" class="layui-form layui-card-header layuiadmin-card-header-auto" onsubmit="return false;">
                    <div class="layui-inline">
                        &nbsp;&nbsp;企业名称&nbsp;
                        <div class="layui-inline" style="width: 200px">
                            <ht:heroenterpriseselect id="enterprise_No_Head" name="enterprise_No" />
                        </div>
                    </div>

                    <div class="layui-inline">
                        &nbsp;&nbsp;企业用户&nbsp;
                        <div class="layui-inline" style="width: 200px">
                            <ht:herocustomdataselect dataSourceType="allEnterpriseUsers" name="enterprise_User_Id" />
                        </div>
                    </div>
                       <div class="layui-inline">
                           &nbsp;&nbsp;通道&nbsp;
                        <div class="layui-inline" style="width: 200px">
                            <ht:herocustomdataselect dataSourceType="allChannels" name="channel_No" />
                        </div>
                    </div>
                    <div class="layui-inline">
                        &nbsp;&nbsp;国家&nbsp;
                        <div class="layui-input-inline">
                            <ht:herocodeselect sortCode="country" name="country_Number" id="country_Number" valueField="Value"/>
                        </div>
                    </div>&nbsp;&nbsp;
                    <div class="layui-inline">
                        &nbsp;&nbsp;运营商&nbsp;
                        <div class="layui-input-inline">
                            <ht:countryoperatorselect id="operator" name="operator" />
                        </div>
                    </div>&nbsp;&nbsp;
                    <div class="layui-inline">
                        &nbsp;&nbsp;提交状态&nbsp;
                        <div class="layui-input-inline" style="width: 100px">
                            <ht:herocodeselect sortCode="034" name="submit_Status_Code"/>
                        </div>
                    </div>&nbsp;&nbsp;
                    <div class="layui-inline">
                        &nbsp;&nbsp;提交描述&nbsp;
                        <div class="layui-input-inline" style="width: 100px">
                            <input name="submit_Description" class="layui-input"/>
                        </div>
                    </div>&nbsp;&nbsp;
                    <div class="layui-inline">
                        &nbsp;&nbsp;消息类型&nbsp;
                        <div class="layui-inline" style="width: 200px">
                            <ht:herocodeselect sortCode="message_type_code"  name="message_Type_Code"/>
                        </div>
                    </div>
                    <div class="layui-inline">
                        &nbsp;&nbsp;短信内容&nbsp;
                        <div class="layui-input-inline">
                            <input name="content" id = "content" class="layui-input"/>
                        </div>
                    </div>&nbsp;&nbsp;
                    <div class="layui-inline">
                        &nbsp;&nbsp;手机号码&nbsp;
                        <div class="layui-input-inline">
                            <input name="phone_No" id = "phone_No" class="layui-input"/>
                        </div>
                    </div>&nbsp;&nbsp;
                    <div class="layui-inline">
                        &nbsp;&nbsp;批次号&nbsp;
                        <div class="layui-input-inline">
                            <input name="msg_Batch_No" id="msg_Batch_No" class="layui-input"/>
                        </div>
                    </div>&nbsp;&nbsp;
                    <div class="layui-inline">
                        &nbsp;&nbsp;创建时间&nbsp;
                        <div class="layui-inline">
                            <input name="minSubmitDate" id="minSubmitDate" class="layui-input layui-input-sm"/>
                        </div>
                        <div class="layui-inline">--</div>
                        <div class="layui-inline">
                            <input name="maxSubmitDate" id="maxSubmitDate"  class="layui-input layui-input-sm"/>
                        </div>
                    </div>&nbsp;&nbsp;
                    <div class="layui-inline">
                        <button class="layui-btn layui-btn-sm" type="submit" lay-submit="" lay-filter="reload">搜索
                        </button>
                    </div>
                </form>
                <div class="layui-form layui-border-box layui-table-view">
                    <div class="layui-card-body">
<%--                        <blockquote class="layui-elem-quote" id="statistics" style="padding: 10px;"></blockquote>--%>
                        <table class="layui-hide" id="list_table" lay-filter="list_table"></table>
                        <script type="text/html" id="table-toolbar">
                            <div class="layui-btn-container">
                                <%@include file="/admin/common/button_action_list.jsp" %>
                            </div>
                        </script>
                    </div>
                </div>
            </div>
        </div>
</div>

<div id="dialogId" hidden>
    <form class="layui-form" lay-filter="form" id="dialogFormId" onsubmit="return false;"
          style="padding: 30px 50px 0px 100px;">
        <div class="layui-form-item">
            <label class="layui-form-label">企业<font color="red">&nbsp;&nbsp;*</font></label>
            <div class="layui-input-inline">
                <ht:heroenterpriseselect  layVerify = "required" name="enterprise_No" id="enterprise_No"/>
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">企业用户<font color="red">&nbsp;&nbsp;*</font></label>
            <div class="layui-input-inline">
                <select id="enterprise_User_Id"  name="enterprise_User_Id">

                </select>
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">扩展码&nbsp;</label>
            <div class="layui-input-inline">
                <input type="text" name="sub_Code" placeholder="扩展码" autocomplete="off"
                       class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label"></label>
            <div class="layui-input-inline"></div>
        </div>
        <div class="layui-form-item" style="text-align: left">
            <div style="float: left">
                <font color="red" id="font1"></font>
                <br/>
                <font color="red">共<font id="font2" color="blue"></font>条数据！是否确认重发？</font>
            </div>
        </div>
    </form>
</div>
</body>
<script>
    var $;
    layui.extend({tableExt: '/layuiadmin/extends/tableExt'}).use(['tableExt','laydate'], function () {
        var table = layui.tableExt,laydate = layui.laydate,$ = layui.$;
        var today = new Date();
        var minSubmitDate = '<c:out value="${minSubmitDate}"/>';
        laydate.render({
            elem: '#minSubmitDate',
            type:'datetime',
            value: minSubmitDate?minSubmitDate:new Date(today.getFullYear(),today.getMonth(),today.getDate())
        });
        laydate.render({
            elem: '#maxSubmitDate',
            type:'datetime',
            value: new Date(today.getFullYear(),today.getMonth(),today.getDate(),23,59,59)
        });
        table.render({
            url: '/admin/sended_submitList',
            height: 'full-160',
            where:{
                'minSubmitDate': $("#minSubmitDate").val()==''? minSubmitDate:$("#minSubmitDate").val(),
                'maxSubmitDate':$("#maxSubmitDate").val()
            },
            cols: [[
                {field: 'channel_No_ext',title: '发送通道',minWidth: 180,templet:function (d) {
                        return !d.channel_No_ext?'---':handleData(d.channel_No_ext.name)+"<br>"+d.channel_No;
                    }},
                {title: '企业名称/企业用户', width:180,templet:function (d) {
                    return !d.enterprise_No_ext?'-$--':handleData(d.enterprise_No_ext.name)
                        +"<br>"+handleData(d.enterprise_User_Id_ext.real_Name)
                        +"("+handleData(d.enterprise_User_Id_ext.user_Name)+")";
                    }},
                {title: '手机号/批次号',width: 170,templet:function (d) {
                    return handleData(d.phone_No)+"<br><a href='javascript:;' lay-event='{\"type\":\"dialogNoBtnCustomerArea\",\"url\":\"/admin/sended_submitDetails?" +
                        "id="+d.id+"&submit_Date_Str="+d.submit_Date+"\",\"width\":\"70%\",\"height\":\"90%\",\"title\":\"详情\"}' style='color: #01aaed;text-decoration-line: underline;'>"+handleData(d.msg_Batch_No)+"</a>";
                    }},
                { title: '提交状态',templet:function (d) {
                    var resTime = d.submitResponseTime||0;
                    var color = "#06b832";
                    if(resTime>100 && resTime<=200){
                      color="#ebc207";
                    }
                    if(resTime>200){
                      color="red";
                    }
                    var a = handleData(d.submit_Status_Code_name);
                    a += '('+ handleData(d.submit_Description)+')/<font color='+color+'>'+resTime+'ms</font><br>';
                    a += handleData(d.submit_Date);
                    return a;
                }, width: 180},
                {field: 'content', title: '内容',width: 300},
                { title: '运营商',templet:function (d) {
                    var a = handleData(d.country_Number_name)+'/'+handleData(d.operator)+'<br>';
                    a += handleData(d.area_Name)+'<br>';
                    a += handleData(d.sp_Number);
                    return a;
                }, width: 180},
                { title: '类型/字数/长消息/序列',templet:function (d) {
                        var a = handleData(d.message_Type_Code_name);
                        a += '/'+handleData(d.content_Length);
                        a += "/"+handleData(d.is_LMS_name)+"/"+handleData(d.sequence);
                        return a;
                }, width: 170},
                { title: '成本/利润',templet:function (d) {
                        var a = '发票成本:'+handleData(d.enterprise_User_Taxes);
                        a += '/发票抵消:' + handleData(d.channel_Taxes)+'<br>';
                        a += '成本:'+handleData(d.channel_Unit_Price) + '/利润:'+handleData(d.profits);
                        return a;
                    }, width: 180},
                {field: 'create_Date', title: '创建时间',minWidth: 170}
            ]],
            done:function (res) {
                layui.$("#statistics").html("");
                layui.use('element', function() {
                    var element = layui.element;
                    element.init();
                });
                // queryTotalData();
            }
        });
    });

    //批量重发功能开始 -------
    function resend() {
        var enterprise_No = $("select[name='enterprise_No']").val();// 企业名称
        var enterprise_User_Id = $("select[name='enterprise_User_Id']").val();// 企业用户
        var channel_No = $("select[name='channel_No']").val();// 通道
        var operator = $("select[name='operator']").val();// 运营商
        var submit_Status_Code = $("select[name='submit_Status_Code']").val();// 提交状态
        var contentSearch = $("#content").val();
        var msg_Batch_No = $("#msg_Batch_No").val();
        var phone_No = $("#phone_No").val();
        var content = $("#content").val();
        var minSubmitDate = $("#minSubmitDate").val();
        var maxSubmitDate = $("#maxSubmitDate").val();
        var content = "你选择了：";
        if(enterprise_No){
        	content = content +"企业No为：" +enterprise_No;
        }
        if(enterprise_User_Id){
        	content = content +"提交用户id为：" +enterprise_User_Id;
        }
        if(channel_No){
        	content = content +"通道No为：" +channel_No;
        }

        if(operator){
        	content = content +"运营商为：" +operator;
        }
        if(submit_Status_Code){
        	content = content +"提交状态为：" +submit_Status_Code;
        }
        if(contentSearch){
        	content = content +"短信内容为：" +contentSearch;
        }
        if(msg_Batch_No){
        	content = content + " 批次号为：" + msg_Batch_No;
        }
        if(phone_No){
        	content = content + " 手机号为：" + phone_No;
        }
        if(minSubmitDate){
        	content = content + " 时间：" + minSubmitDate;
        }
        if(maxSubmitDate){
        	content = content + " 到  " + maxSubmitDate;
        }
        var queryButtonUrl = '/admin/sended_queryResendSmsCount?';
        //查询要重推的数量
        $.ajax({
            url: queryButtonUrl+$("#layuiForm").serialize(),
            dataType:'json',
            success:function (res) {
                if (res.code != '0' && res.msg == 'dataTooLarge') {
                    layer.msg('共'+res.data+'条数据，超过最大重发条数！', {icon: 2, time: 2000});
                    return false;
                }
                if (res.code == '0') {
                    if(!res.data){
                        layer.msg('没相关的短信数据！', {icon: 2, time: 2000});
                        return false;
                    }
                    $("#dialogFormId")[0].reset();
                    $("#font1").html(content);
                    $("#font2").html(' '+res.data+' ');
                    var layerOpen = layer.open({
                        area: ['800px', '500px'],
                        title:'重发短信',
                        type: 1,
                        content: $("#dialogId"),
                        btn: ['确定', '取消'],
                        yes: function (index, layero) {
                        	var enterprise_No = $("#enterprise_No").val();
                            if(enterprise_No == null || enterprise_No ==''){
                                layer.msg("请选择企业");
                            	return;
                            }
                            var enterprise_User_Id = $("#enterprise_User_Id").val();
                            if(enterprise_User_Id == null || enterprise_User_Id ==''){
                                layer.msg("请选择提交账户");
                            	return;
                            }
                            var subCode = $("input[name='sub_Code']").val();
                            var resendButtonUrl = '/admin/sended_resendSms?';
                            resendButtonUrl += $("#layuiForm").serialize()+"&subCode="+subCode+"&enterpriseNo="+enterprise_No+"&enterpriseUserId="+enterprise_User_Id;
                            var loading = parent.layer.load('',{time:10*1000});
                            $("#dialogFormId")[0].reset();
                            $("#enterprise_User_Id option").remove();//移除所有
                            $.ajax({
                                url: resendButtonUrl,
                                dataType:'json',
                                success: function (res) {
                                    parent.layer.close(loading);
                                    if (res.code == '0') {
                                        layer.msg('重发成功', {icon: 1, time: 2000}, function(){});
                                    } else {
                                        layer.msg(res.msg);
                                    }
                                    layer.close(layerOpen);
                                    return false;
                                },
                                error: function (res) {
                                    parent.layer.close(loading);
                                    layer.msg('发送失败！');
                                    layer.close(layerOpen);
                                    return false;
                                },
                            });
                            return false;
                        }
                    });
                    return false;
                } else {
                    layer.msg(res.msg);
                }
            }
        });
    }
    //批量重推功能结束 -------

    function getFormData() {
        return $("#layuiForm").serialize();
    }
</script>