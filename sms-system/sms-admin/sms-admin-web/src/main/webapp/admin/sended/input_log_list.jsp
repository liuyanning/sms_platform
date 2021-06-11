<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/admin/common/common.jsp" %>
<%@ include file="/admin/common/layui_head.html" %>
<%@ include file="/admin/common/dynamic_data.jsp" %>
<body>
<div class="layui-fluid">
    <div class="layui-col-md12">
        <div class="layui-card">
            <form class="layui-form layui-card-header layuiadmin-card-header-auto" id="layuiForm"
                  onsubmit="return false;">
                <div class="layui-inline">
                    &nbsp;&nbsp;企业名称&nbsp;
                    <div class="layui-inline" style="width: 200px">
                        <ht:heroenterpriseselect id="enterprise_No_Head" name="enterprise_No"/>
                    </div>
                </div>
                <div class="layui-inline">
                    &nbsp;&nbsp;企业用户&nbsp;
                    <div class="layui-inline" style="width: 200px">
                        <ht:herocustomdataselect dataSourceType="allEnterpriseUsers" name="enterprise_User_Id"/>
                    </div>
                </div>
                <div class="layui-inline">
                    <label class="layui-form-label">消息类型&nbsp;</label>
                    <div class="layui-inline" style="width: 200px">
                        <ht:herocodeselect sortCode="message_type_code" name="message_Type_Code"/>
                    </div>
                </div>
                <div class="layui-inline">
                    &nbsp;&nbsp;批次号&nbsp;
                    <div class="layui-input-inline">
                        <input name="msg_Batch_No" id="msg_Batch_No" class="layui-input"/>
                    </div>
                </div>&nbsp;&nbsp;
                <div class="layui-inline">
                    &nbsp;&nbsp;手机号码&nbsp;
                    <div class="layui-input-inline">
                        <input name="phone_Nos" id="phone_Nos" class="layui-input"/>
                    </div>
                </div>&nbsp;&nbsp;
                <div class="layui-inline">
                    &nbsp;&nbsp;消息内容&nbsp;
                    <div class="layui-input-inline">
                        <input name="content" id="content" class="layui-input"/>
                    </div>
                </div>&nbsp;
                <div class="layui-inline">
                    &nbsp;&nbsp;分拣时间&nbsp;
                    <div class="layui-input-inline">
                        <input name="minCreateDate" id="minCreateDate" class="layui-input layui-input-sm" size="15"/>
                    </div>
                    <div class="layui-inline">--</div>
                    <div class="layui-input-inline">
                        <input name="maxCreateDate" id="maxCreateDate" class="layui-input layui-input-sm" size="15"/>
                    </div>
                </div>&nbsp;&nbsp;
                <div class="layui-inline">
                    <button class="layui-btn layui-btn-sm" type="submit" lay-submit="" lay-filter="reload">搜索
                    </button>
                </div>
            </form>
            <div class="layui-form layui-border-box layui-table-view">
                <div class="layui-card-body">
                    <%--                    <blockquote class="layui-elem-quote" id="statistics" style="padding: 10px;"></blockquote>--%>
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
            <div class="layui-input-inline" style="width:300px;">
                <ht:heroenterpriseselect  layVerify = "required" name="enterprise_No" id="enterprise_No"/>
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">企业用户<font color="red">&nbsp;&nbsp;*</font></label>
            <div class="layui-input-inline" style="width:300px;">
                <select id="enterprise_User_Id"  name="enterprise_User_Id">

                </select>
            </div>
        </div>&nbsp;
        <div class="layui-form-item">
            <label class="layui-form-label">扩展码&nbsp;</label>
            <div class="layui-input-inline" style="width:300px;">
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
    layui.extend({tableExt: '/layuiadmin/extends/tableExt'}).use(['tableExt'], function () {
        var table = layui.tableExt;
        $ = layui.$;
        var minCreateDate = '<c:out value="${minCreateDate}"/>';
        var today = new Date();
        layui.use('laydate', function(){
            var laydate = layui.laydate;
            laydate.render({     //创建时间选择框
                elem: '#minCreateDate' //指定元素
                ,type:'datetime'
                ,trigger : 'click'
                ,value: minCreateDate?minCreateDate:new Date(today.getFullYear(),today.getMonth(),today.getDate())
            });
            laydate.render({     //创建时间选择框
                elem: '#maxCreateDate' //指定元素
                ,type:'datetime'
                ,trigger : 'click'
                ,value: new Date(today.getFullYear(),today.getMonth(),today.getDate(),23,59,59)
            });
        });
        table.render({
            url: '/admin/sended_inputLogList',
            height: 'full-150',
            where:{
                'minCreateDate': $("#minCreateDate").val()==''? minCreateDate:$("#minCreateDate").val(),
                'maxCreateDate':$("#maxCreateDate").val()
            },
            cols: [[
                {field: 'enterprise_No_ext', title: '企业名称/企业用户',templet:function (d) {
                        return !d.enterprise_No_ext?'---':handleData(d.enterprise_No_ext.name)
                            +"<br>"+handleData(d.enterprise_User_Id_ext.real_Name)
                            +"("+handleData(d.enterprise_User_Id_ext.user_Name)+")";
                    }, width: 180},
                {title: '批次号',width: 175,templet:function (d) {
                        return "<a href='javascript:;' lay-event='{\"type\":\"tagTodo\",\"url\":\"/admin/sended_reportIndex?limitCode=006028&flag=Input_Log_Flag&minCreateDate="+d.create_Date+"&msg_Batch_No="+d.msg_Batch_No+"\",\"title\":\"详情\"}' style='color: #01aaed;text-decoration-line: underline;'>"+handleData(d.msg_Batch_No)+"</a>";
                    }},
                {title: '手机号',width: 155,field:'phone_Nos'},
                {field: 'content', title: '内容', minWidth: 350},
                { title: '类型/字数/号码/计费',templet:function (d) {
                        var a = handleData(d.message_Type_Code_name);
                        a += '/'+handleData(d.content_Length);
                        a += '/'+handleData(d.phone_Nos_Count)+'个<br>';
                        a += '计费:'+handleData(d.fee_Count)+'条='+ handleData(d.sale_Amount)+'元';
                        return a;
                    }, width: 190},
                { title: '协议/IP',templet:function (d) {
                        var a = handleData(d.protocol_Type_Code_name)+ '<br>';
                        a += handleData(d.source_IP);
                        return a;
                    }, width: 150},
                { title: '审核',templet:function (d) {
                        var a = handleData(d.audit_Status_Code_name);
                        a += !d.audit_Admin_User_Id_ext?'':"("+handleData(d.audit_Admin_User_Id_ext.real_Name)+")"+'<br>';
                        a += !d.audit_Date?'':handleData(d.audit_Date);
                        return a;
                    }, width: 160},
                {field: 'input_Date', title: '提交时间', width: 170},
                {field: 'create_Date', title: '分拣时间', width: 170},
            ]]
            ,done: function(res){
                layui.$("#statistics").html("");
                layui.use('element', function() {
                    var element = layui.element;
                    element.init();
                });
            }
        });
    });

    //获取form表单数据
    function getFormData() {
        return $("#layuiForm").serialize();
    }

    //批量重推功能开始 -------
    function resend() {
        var msg_Batch_No = $("#msg_Batch_No").val();
        var enterpriseName = $("#enterpriseName").val();
        var phone_Nos = $("#phone_Nos").val();
        var content = $("#content").val();
        var minCreateDate = $("#minCreateDate").val();
        var maxCreateDate = $("#maxCreateDate").val();
        var contentsss = "你选择了：";
        if(enterpriseName){
            contentsss = contentsss +"企业名称为：" +enterpriseName;
        }
        if(msg_Batch_No){
            contentsss = contentsss + " 批次号为：" + msg_Batch_No;
        }
        if(phone_Nos){
            contentsss = contentsss + " 手机号为：" + phone_Nos;
        }
        if(minCreateDate){
            contentsss = contentsss + " 时间：" + minCreateDate;
        }
        if(maxCreateDate){
            contentsss = contentsss + " 到  " + maxCreateDate;
        }
        var queryButtonUrl = '/admin/sended_queryInputLogList?';
        //查询要重推的数量
        $.ajax({
            url: queryButtonUrl+$("#layuiForm").serialize(),
            dataType:'json',
            success: function (res) {
                if (res.code != '0' && res.msg == 'dataTooLarge') {
                    layer.msg('共'+res.data+'条数据，超过最大重发条数！', {icon: 2, time: 2000});
                    return false;
                }
                if (res.code == '0') {
                    if(!res.data){
                        layer.msg('没有相关的短信数据！', {icon: 2, time: 2000});
                        return false;
                    }
                    $("#dialogFormId")[0].reset();
                    $("#font1").html(contentsss);
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
                                layer.alert("请选择企业");
                                return;
                            }
                            var enterprise_User_Id = $("#enterprise_User_Id").val();
                            if(enterprise_User_Id == null || enterprise_User_Id ==''){
                                layer.alert("请选择提交账户");
                                return;
                            }
                            var subCode = $("input[name='sub_Code']").val();
                            var resendButtonUrl = '/admin/sended_resendInputLog?';
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
                                        layer.msg('重发成功', {icon: 1, time: 3000}, function(){});
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

</script>