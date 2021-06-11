<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/admin/common/common.jsp" %>
<%@ include file="/admin/common/layui_head.html" %>
<%@ include file="/admin/common/country_operator.jsp" %>
<body>
<div class="layui-fluid">
    <div class="layui-col-md12">
        <div class="layui-card">
            <form class="layui-form layui-card-header layuiadmin-card-header-auto" id="layuiForm"
                  onsubmit="return false;">
                <input value="<c:out value="${limitCode}"/>" name="limitCode" hidden/>
                <input value="<c:out value="${channel_Master_Msg_No}"/>" name="channel_Master_Msg_No"
                       id="channel_Master_Msg_No" hidden/>
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
                    &nbsp;&nbsp;通道&nbsp;
                    <div class="layui-inline" style="width: 200px">
                        <ht:herocustomdataselect dataSourceType="allChannels" name="channel_No"/>
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
                    &nbsp;&nbsp;消息类型&nbsp;
                    <div class="layui-inline" style="width: 200px">
                        <ht:herocodeselect sortCode="message_type_code" name="message_Type_Code"/>
                    </div>
                </div>
                <div class="layui-inline">
                    &nbsp;&nbsp;提交状态&nbsp;
                    <div class="layui-input-inline" style="width: 100px">
                        <ht:herocodeselect sortCode="034" name="submit_Status_Code"/>
                    </div>
                </div>&nbsp;&nbsp;
                <div class="layui-inline">
                    &nbsp;&nbsp;回执状态&nbsp;
                    <div class="layui-input-inline" style="width: 100px">
                        <ht:herocodeselect sortCode="033" name="status_Code"/>
                    </div>
                </div>&nbsp;&nbsp;
                <div class="layui-inline">
                    &nbsp;&nbsp;原生状态&nbsp;
                    <div class="layui-input-inline" style="width: 100px">
                        <input name="native_Status" class="layui-input"/>
                    </div>
                </div>&nbsp;&nbsp;
                <div class="layui-inline">
                    &nbsp;&nbsp;提交描述&nbsp;
                    <div class="layui-input-inline" style="width: 100px">
                        <input name="submit_Description" class="layui-input"/>
                    </div>
                </div>&nbsp;&nbsp;
                <div class="layui-inline">
                    &nbsp;&nbsp;手机号码&nbsp;
                    <div class="layui-input-inline">
                        <input name="phone_No" class="layui-input"/>
                    </div>
                </div>&nbsp;&nbsp;
                <div class="layui-inline">
                    短信内容
                    <div class="layui-input-inline">
                        <input name="content" id = "content" class="layui-input"/>
                    </div>
                </div>
                <div class="layui-inline">
                    &nbsp;&nbsp;批次号&nbsp;
                    <div class="layui-input-inline">
                        <input name="msg_Batch_No" value='<c:out value="${msg_Batch_No}"></c:out>'
                               class="layui-input"/>
                    </div>
                </div>
                <div class="layui-inline">
                    &nbsp;&nbsp;提交时间&nbsp;
                    <div class="layui-inline">
                        <input name="minSubmitDate" id="minSubmitDate" class="layui-input layui-input-sm"/>
                    </div>
                    <div class="layui-inline">--</div>
                    <div class="layui-inline">
                        <input name="maxSubmitDate" id="maxSubmitDate" class="layui-input layui-input-sm"/>
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

<div id="dialogSendMOId" hidden>
    <form class="layui-form" lay-filter="form" id="dialogFormId" onsubmit="return false;"
          style="padding: 30px 70px 0px 70px;">
        <div class="layui-form-item">
            <label class="layui-form-label">短信内容<font color="red">&nbsp;&nbsp;*</font></label>
            <div class="layui-input-block">
                <textarea type="text" name="msg_content" id="msg_content" autocomplete="off" class="layui-textarea"></textarea>
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label"></label>
            <div class="layui-input-inline"></div>
        </div>
        <div class="layui-form-item" style="text-align: left">
            <div style="float: left">
                <font color="red">共<font id="selectedCount" color="blue"></font>条数据！是否确认批量生成上行？</font>
            </div>
        </div>
    </form>
</div>
</body>
<script>
    var $;
    var table;
    layui.extend({tableExt: '/layuiadmin/extends/tableExt'}).use(['tableExt','laydate'], function () {
        table = layui.tableExt;
         var laydate = layui.laydate,
            today = new Date();
        $ = layui.$;
        var minSubmitDate = '<c:out value="${minSubmitDate}"/>';
        var maxSubmitDate = '<c:out value="${maxSubmitDate}"/>';
        var channel_Master_Msg_No = '<c:out value="${channel_Master_Msg_No}"/>';
        var msg_Batch_No = '<c:out value="${msg_Batch_No}"/>';
        var flag = '<c:out value="${flag}"/>';
        laydate.render({     //创建时间选择框
            elem: '#minSubmitDate' //指定元素
            ,type:'datetime'
            ,trigger : 'click'
            ,value: minSubmitDate?minSubmitDate:new Date(today.getFullYear(),today.getMonth(),today.getDate())
        });
        laydate.render({     //创建时间选择框
            elem: '#maxSubmitDate' //指定元素
            ,type:'datetime'
            ,trigger : 'click'
            ,value: maxSubmitDate?maxSubmitDate:new Date(today.getFullYear(),today.getMonth(),today.getDate(),23,59,59)
        });
        table.render({
            url: '/admin/sended_reportList',
            height: 'full-160',
            where:{
                "minSubmitDate":minSubmitDate?minSubmitDate:$("#minSubmitDate").val()
                ,"maxSubmitDate":maxSubmitDate?maxSubmitDate:$("#maxSubmitDate").val()
                ,"channel_Master_Msg_No":channel_Master_Msg_No
                ,"msg_Batch_No":msg_Batch_No
                ,"flag":flag
            },
            cols: [[
                {checkbox: true},
                {field: 'channel_No_ext', title: '发送通道',minWidth: 180,templet:function (d) {
                        return !d.channel_No_ext?'---':handleData(d.channel_No_ext.name)+"<br>"+d.channel_No;
                    }},
                {field: 'enterprise_No_ext', title: '企业名称/企业用户',templet:function (d) {
                        return !d.enterprise_No_ext?'---':handleData(d.enterprise_No_ext.name)
                            +"<br>"+handleData(d.enterprise_User_Id_ext.real_Name)
                            +"("+handleData(d.enterprise_User_Id_ext.user_Name)+")";
                    }, width: 180},
                {title: '手机号/批次号',width: 170,templet:function (d) {
                        return handleData(d.phone_No)+"<br>"+handleData(d.msg_Batch_No);
                    }},
                { title: '提交状态',templet:function (d) {
                        var a = handleData(d.submit_Status_Code_name);
                        a += '('+ handleData(d.submit_Description)+')';
                        a += '<br>'+handleData(d.submit_Date);
                        return a;
                    }, width: 180},
                { title: '回执状态',templet:function (d) {
                        var a = handleData(d.status_Code_name);
                        a += '/'+handleData(d.native_Status_name);
                        a += '<br>'+handleData(d.status_Date);
                        return a;
                    }, width: 180},
                { title: '回执耗时',templet:function (d) {
                        var resTime = timeInterval(d.submit_Date,d.status_Date)||0;
                        var color = "#06b832";
                        if(resTime>5 && resTime<=60){
                            color="#ebc207";
                        }
                        if(resTime>60){
                            color="red";
                        }
                        var formatDate = timeIntervalFormate(d.submit_Date,d.status_Date);
                        return "<font color='"+color+"'>"+formatDate+"</font>";
                    }, width: 130},
                { field: 'content', title: '内容',width: 300},
                { title: '运营商',templet:function (d) {
                        var a = handleData(d.country_Number_name)+'/'+handleData(d.operator)+'<br>';
                        a += handleData(d.area_Name)+'<br>';
                        a += handleData(d.sp_Number);
                        return a;
                    }, width: 180},
                { title: '类型/字数/序列',templet:function (d) {
                        var a = handleData(d.message_Type_Code_name);
                        a += '/'+handleData(d.content_Length);
                        a += "/"+handleData(d.sequence);
                        return a;
                    }, width: 170},
                {field: 'create_Date', title: '创建时间',minWidth: 150}
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

    //表头统计
    // function queryTotalData(){
    //     var queryUrl = '/admin/sended_queryReportListTotalData?';
    //     $.ajax({
    //         type: 'post', // 提交方式 get/post
    //         url: queryUrl+$("#layuiForm").serialize(), // 需要提交的 url
    //         dataType: 'json',
    //         // data: data,
    //         success: function (res) { // data 保存提交后返回的数据，一般为 json 数据
    //             var count = res.data.count?res.data.count:0 ;
    //             var submitSuccessSmsTotal = res.data.submit_Success_Total?res.data.submit_Success_Total:0 ;
    //             var submitFaildSmsTotal = res.data.submit_Faild_Total?res.data.submit_Faild_Total:0 ;
    //             var sortFaildSmsTotal = res.data.sort_Faild_Total?res.data.sort_Faild_Total:0 ;
    //             var sendSuccessSmsTotal = res.data.send_Success_Total?res.data.send_Success_Total:0 ;
    //             var sendFaildSmsTotal = res.data.send_Faild_Total?res.data.send_Faild_Total:0 ;
    //             layui.$("#statistics").html("共:"+count+"条&nbsp;|&nbsp;提交成功:"+submitSuccessSmsTotal+"条&nbsp;|&nbsp;提交失败:"
    //                 + submitFaildSmsTotal + "条&nbsp;|&nbsp;分拣失败:" + sortFaildSmsTotal + "条&nbsp;|&nbsp;回执:"
    //                 + sendSuccessSmsTotal + "条成功," + sendFaildSmsTotal + "条失败&nbsp;|");
    //         }
    //     })
    //     return false; // 必须返回false，否则表单会自己再做一次提交操作，并且页面跳转
    // }

    function getFormData() {
        return $("#layuiForm").serialize();
    }

    //批量补推功能开始 -------
    function resend() {
        var enterprise_No = $("select[name='enterprise_No']").val();// 企业名称
        var enterprise_User_Id = $("select[name='enterprise_User_Id']").val();// 企业用户
        var channel_No = $("select[name='channel_No']").val();// 通道
        var operator = $("select[name='operator']").val();// 运营商
        var submit_Status_Code = $("select[name='submit_Status_Code']").val();// 提交状态
        var status_Code = $("select[name='status_Code']").val();// 接收状态
        //var notify_Status_Code = $("select[name='notify_Status_Code']").val();// 通知状态
        var native_Status = $("input[name='native_Status']").val();// 原生状态
        var phone_No = $("input[name='phone_No']").val();// 手机号码
        // var contentSearcn = $("input[name='content']").val();// 短信内容
        var msg_Batch_No = $("input[name='msg_Batch_No']").val();// 批次号
        //var channel_Master_Msg_No = $("#channel_Master_Msg_No").val();// 通道批次号
        var minSubmitDate = $("#minSubmitDate").val();
        var maxSubmitDate = $("#maxSubmitDate").val();
        var contentsss = " 你选择了：";
        if(enterprise_No){
            contentsss = contentsss +"企业为：" +enterprise_No;
        }
        if(enterprise_User_Id){
            contentsss = contentsss +"提交账户为：" +enterprise_User_Id;
        }
        if(channel_No){
            contentsss = contentsss +"通道为：" +channel_No;
        }
        if(operator){
            contentsss = contentsss +"运营商为：" +operator;
        }
        if(submit_Status_Code){
            contentsss = contentsss +"提交状态为：" +submit_Status_Code;
        }
        if(status_Code){
            contentsss = contentsss +"接收状态为：" +status_Code;
        }
        // if(notify_Status_Code){
        // contentsss = contentsss +"通知状态为：" +notify_Status_Code;
        // }
        if(native_Status){
            contentsss = contentsss +"原生状态为：" +native_Status;
        }
        if(phone_No){
            contentsss = contentsss + " 手机号为：" + phone_No;
        }
        // if(contentSearcn){
        //     contentsss = contentsss + " 短信内容为：" + contentSearcn;
        // }
        if(msg_Batch_No){
            contentsss = contentsss + " 批次号为：" + msg_Batch_No;
        }
        if(minSubmitDate){
            contentsss = contentsss + " 时间：" + minSubmitDate;
        }
        if(maxSubmitDate){
            contentsss = contentsss + " 到  " + maxSubmitDate;
        }
        var queryButtonUrl = '/admin/sended_queryRepushReportList?';
        //查询要重推的数量
        $.ajax({
            url: queryButtonUrl+$("#layuiForm").serialize(),
            dataType:'json',
            success: function (res) {
                if (res.code == '0') {
                    if(!res.data){
                        layer.msg('没有相关的短信数据！', {icon: 2, time: 2000});
                        return false;
                    }
                    layer.confirm(contentsss+" 共计:"+res.data+" 条,确认要补推状态吗？", {
                        btn: ['确认', '取消'] //按钮
                    }, function (index) {
                        var resendButtonUrl = '/admin/sended_repushSmsReport?';
                        $.ajax({
                            url: resendButtonUrl+$("#layuiForm").serialize(),
                            dataType:'json',
                            success: function (res) {
                                if (res.code == '0') {
                                    layer.msg('补发成功', {icon: 1, time: 2000}, function(){});
                                } else {
                                    layer.msg(res.msg);
                                }
                                layer.close(index);
                            }
                        });
                    });
                    return false;
                } else {
                    layer.msg(res.msg);
                }
            }
        });
    }
    //批量补推功能结束 -------


    //生成上行功能开始
    function manualSendMO() {
        // 获取表格已选中的数据
        var checkStatus = table.checkStatus('list_table'); //idTest 即为基础参数 id 对应的值
        // 选择中的参数
        var selectedData = checkStatus.data;
        if(selectedData.length == 0) {
            layer.msg("至少选择一条数据！");
            return;
        }
        var ids = new Array();
        for (var i in selectedData) {
            console.log('status_Code = ' + selectedData[i].status_Code);
            if(selectedData[i].status_Code != 'success') {
                layer.msg("只能选择回执状态为成功的数据，请检查！");
                return;
            }
            ids.push(selectedData[i].id);
        }

        $("#dialogFormId")[0].reset();
        $("#selectedCount").html(' ' + ids.length + ' ');
        var layerOpen = layer.open({
            area: ['800px', '320px'],
            title:'生成上行',
            type: 1,
            content: $("#dialogSendMOId"),
            btn: ['确定', '取消'],
            yes: function (index, layero) {
                var msg_content = $("#msg_content").val();
                if(!msg_content){
                    layer.msg("请填写短信内容");
                    return;
                }
                var loading = parent.layer.load('',{time:10*1000});
                $("#dialogFormId")[0].reset();
                $.ajax({
                    url: '/admin/sended_manualSendMO?' + $("#layuiForm").serialize(),
                    type: 'post',
                    dataType:'json',
                    data: {ckLongs: ids.join(","), 'ckStrings': msg_content},
                    success: function (res) {
                        parent.layer.close(loading);
                        if (res.code == '0') {
                            layer.msg('批量生成上行成功', {icon: 1, time: 2000}, function(){});
                        } else {
                            layer.msg(res.msg);
                        }
                        layer.close(layerOpen);
                        return false;
                    },
                    error: function (res) {
                        parent.layer.close(loading);
                        layer.msg('批量生成上行失败！');
                        layer.close(layerOpen);
                        return false;
                    },
                });
                return false;
            }
        });
    }
    //生成上行功能结束
</script>