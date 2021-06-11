<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/admin/common/common.jsp" %>
<%@ include file="/admin/common/layui_head.html" %>
<body>
<div class="layui-fluid">
    <div class="layui-row layui-col-space15">
        <div class="layui-col-md12">
            <div class="layui-card">
                <form class="layui-form layui-card-header layuiadmin-card-header-auto" onsubmit="return false;"
                      id="layuiForm">
                    <div class="layui-inline">
                        &nbsp;&nbsp;处理状态&nbsp;
                        <div class="layui-input-inline" style="width: 100px">
                            <ht:herocodeselect sortCode="dispose_state_code" name="dispose_State_Code"/>
                        </div>
                    </div>&nbsp;&nbsp;
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
    layui.extend({tableExt: '/layuiadmin/extends/tableExt'}).use(['tableExt'], function () {
        var table = layui.tableExt;
        table.render({
            url: '/admin/charge_invoiceList'
            , cols: [[
                // {checkbox: true},
                {field: 'id', title: 'ID', width: 50, sort: true, type: 'checkbox'}
                , {field: 'invoice_Title', title: '发票抬头', width: 180}
                , {field: 'invoice_Amount', title: '发票金额【￥】', width: 100}
                , {field: 'duty_Daragraph', title: '纳税人识别号', width: 180}
                , {title: '企业信息(电话|地址)', templet: function (d) {
                        return '电话:'+handleData(d.phone_No)
                        + '<br>地址:' + handleData(d.address);
                    }, width: 230
                }
                , {title: '开户行/银行卡号', templet: function (d) {
                        return handleData(d.opening_Bank)
                        + '<br>' + handleData(d.bank_Account) ;
                    }, width: 230
                }
                , {title: '收件信息(电话|地址)', templet: function (d) {
                        return '电话:'+handleData(d.recipient_Phone_No)
                        +'<br>地址:' + handleData(d.mailing_Address);
                    }, width: 230
                }
                , {title: '寄件信息(快递公司|单号)', templet: function (d) {
                        return '快递公司:'+handleData(d.express_Company)
                        + '<br>单号:' + handleData(d.courier_Number);
                    }, width: 230
                }
                , {field: 'dispose_State_Code_name', title: '处理状态', width: 110}
                , {field: 'remark', title: '备注', width: 150}
                , {field: 'create_Date', title: '申请时间', width: 200}
            ]]
        });
    });
</script>