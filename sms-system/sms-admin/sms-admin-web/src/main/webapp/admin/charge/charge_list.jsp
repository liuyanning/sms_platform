<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/admin/common/common.jsp" %>
<%@ include file="/admin/common/layui_head.html" %>
<body>
<div class="layui-fluid">
    <div class="layui-row layui-col-space15">
        <div class="layui-col-md12">
            <div class="layui-card">
                <form id="layuiForm" class="layui-form layui-card-header layuiadmin-card-header-auto" onsubmit="return false;">
                        <div class="layui-inline">
                            充值时间&nbsp;
                            <div class="layui-input-inline">
                                <input name="min_Input_Date" id="minInputDate" class="layui-input layui-input-sm" size="15"/>
                            </div>
                            <div class="layui-inline">--</div>
                            <div class="layui-input-inline">
                                <input name="max_Input_Date" id="maxInputDate" class="layui-input layui-input-sm" size="15"/>
                            </div>
                        </div>
                        <div class="layui-inline">
                        &nbsp;&nbsp;开通状态&nbsp;
                        <div class="layui-inline" style="width: 100px">
                            <ht:herocodeselect sortCode="017" name="open_Status_Code"/>
                        </div>
                    </div>
                    &nbsp;&nbsp;
                    <div class="layui-inline">
                        &nbsp;&nbsp;充值支付方式&nbsp;
                        <div class="layui-inline" style="width: 100px">
                            <ht:herocodeselect sortCode="018" name="pay_Type_Code"/>
                        </div>
                    </div>
                    &nbsp;&nbsp;
                    <div class="layui-inline">
                        &nbsp;&nbsp;企业名称
                        <div class="layui-inline" style="width: 200px">
                            <ht:heroenterpriseselect  name="enterprise_No" />
                        </div>
                    </div>
                    <div class="layui-inline">
                        <button class="layui-btn layui-btn-sm" type="submit" lay-submit="" lay-filter="reload">搜索
                        </button>
                    </div>
                </form>
                <div class="layui-form layui-border-box layui-table-view">
                    <div class="layui-card-body">
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
</div>

</body>
<script>
    var $;
    layui.extend({tableExt: '/layuiadmin/extends/tableExt'}).use(['tableExt'], function () {
        var table = layui.tableExt;
        $ = layui.$;
        table.render({
            url: '/admin/charge_chargeOrderList',
            cols: [[
                {checkbox: true},
                {title: '企业名称/企业编号', width:180,templet:function (d) {
                        return handleData(d.enterprise_Name)+"<br>"+handleData(d.enterprise_No);
                    }},
                {title: '企业用户', width:140,templet:function (d) {
                        return !d.enterprise_User_Id_ext?'---':handleData(d.enterprise_User_Id_ext.real_Name)
                            +"<br>"+handleData(d.enterprise_User_Id_ext.user_Name);
                    }},
                {field: 'before_Money', title: '期初金额', width:130},
                // {field: 'after_Money', title: '期末金额【￥】', width:120},
                {title: '充值  信息', minWidth: 430, templet: function (d) {
                        var payName = handleData(d.pay_Type_Code_name);
                        if(payName =='NATIVE'){
                            payName =  "微信";
                        }else if(payName =='ZFB_F2F_SWEEP_PAY'){
                            payName =  "支付宝";
                        }
                        return "金额(元)：" + handleData(d.money)
                            + "/金额大写：" + handleData(d.money_Letter)
                            + "<br>充值人：" + handleData(d.input_User)
                            + "/支付方式：" + payName
                            + "/充值时间：" + handleData(d.input_Date)
                    }
                },
                {
                    title: '开通  信息', width: 230, templet: function (d) {
                        return "状态：" + handleData(d.open_Status_Code_name)
                            + "/开通人：" + handleData(d.open_User)
                            + "<br>开通时间：" + handleData(d.open_Date)
                    }
                },
                {field: 'input_Remark', title: '充值备注', width: 100},
                {field: 'open_Remark', title: '开通备注', width: 100},
            ]]
        });
    });
    layui.use('laydate', function(){
        var laydate = layui.laydate;
        var today = new Date();
        laydate.render({     //创建时间选择框
            elem: '#minInputDate' //指定元素
            ,type:'datetime'
            ,trigger : 'click'
            ,value: new Date(today.getFullYear(),today.getMonth(),today.getDate())
        });
        laydate.render({     //创建时间选择框
            elem: '#maxInputDate' //指定元素
            ,type:'datetime'
            ,trigger : 'click'
            ,value: new Date(today.getFullYear(),today.getMonth(),today.getDate(),23,59,59)
        });
    });
    function getFormData() {
        return $("#layuiForm").serialize();
    }
</script>