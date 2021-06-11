<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/admin/common/common.jsp" %>
<%@ include file="/admin/common/layui_head.html" %>
<%@ include file="/admin/common/country_operator.jsp" %>
<head>
    <meta http-equiv="Content-Type" content="multipart/form-data; charset=utf-8"/>
</head>
<body>
<div class="layui-fluid" style="padding: 18px;">
    <div class="layui-row layui-col-space15">
        <div class="layui-col-md12">
            <div class="layui-card">
                <form class="layui-form layui-card-header layuiadmin-card-header-auto" id="layuiForm"
                      onsubmit="return false;">
                    <input type="text" name="groupStr" id="groupStr" value="Submit_Date" hidden/>
                    <input type="text" id="groupStrNoChecked"
                           value="agent_No,business_User_Id,enterprise_No,enterprise_User_Id,message_Type_Code,operator,province_Code,signature"
                           hidden/>
                    <input type="hidden" class="layui-input" id="min_Submit_Date"
                           placeholder="yyyy-MM-dd"
                           name="min_Submit_Date_Str">
                    <input type="hidden" class="layui-input" id="max_Submit_Date" placeholder="yyyy-MM-dd"
                           name="max_Submit_Date_Str">
                </form>
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
<script>
  var $;
  layui.extend({tableExt: '/layuiadmin/extends/tableExt'}).use(['tableExt', 'laydate'], function () {
    getGroupStr();
    var table = layui.tableExt;
    $ = layui.$;
    table.render({
      url: '/admin/statistic_threeDaysStatisticList',
      height: 'full-80',
      where: {
        'groupStr':  $("#groupStr").val(),
        'min_Submit_Date_Str': getFormatDateBefore(1),
        'max_Submit_Date_Str': getFormatDateBefore(1),
      },
      totalRow: true,
      cols: [[
        {checkbox: true, totalRowText: '合计', width: 60},
        {field: 'submit_Date', title: '日期', width:120,templet:function (d) {
            return !d.submit_Date?'---':d.submit_Date.substring(0,10);
          }},
        {field: 'submit_Total', title: '提交条数', width: 120, totalRow: true},
        {field: 'submit_Success_Total', title: '提交成功', width: 120, totalRow: true},
        {field: 'submit_Faild_Total', title: '提交失败', width: 120, totalRow: true},
        {field: 'sort_Faild_Total', title: '分拣失败', width: 120, totalRow: true},
        {field: 'send_Success_Total', title: '发送成功', width: 120, sort: true, totalRow: true},
        {field: 'send_Faild_Total', title: '发送失败',width: 120, totalRow: true,templet:function (d) {
            return d.send_Faild_Total;
          }},

        { title: '发送未知', width: 120, templet:function (d) {
            var send_Unknown_Total = d.submit_Success_Total-(d.send_Success_Total+d.send_Faild_Total);
            return send_Unknown_Total<0?0:send_Unknown_Total;
          }},
        {field: 'success_Rate', title: '成功率',templet:function (d) {
            if (d.submit_Success_Total== 0){
              return "0.00%";
            }
            var str=Number(d.send_Success_Total*100/d.submit_Success_Total).toFixed(2);
            return str+="%";
          },width: 100},
        {field: 'failure_Rate', title: '失败率',templet:function (d) {
            if (d.submit_Success_Total== 0){
              return "0.00%";
            }
            var str=Number(d.send_Faild_Total*100/d.submit_Success_Total).toFixed(2);
            return str+="%";
          },width: 100}

      ]]
      ,done: function(res, curr, count){
        var str = $("#groupStrNoChecked").val();
        var notChecked = str.split(',');
        console.log(notChecked)
        for(var i = 0; i < notChecked.length; i++) {
          $("[data-field="+notChecked[i]+"]").css('display','none');
        }
      }
    });
  });

  layui.use(['form', 'laydate'], function () {
    var $ = layui.$
        , form = layui.form;
    //监听复选框
    form.on('checkbox(group)', function (data) {
      getGroupStr();
    });
    form.render();

  });

  function getGroupStr() {
    var groupStr = $("input[lay-filter='group']:checked").map(function () {
      return $(this).val();
    }).get().join(',');
    var Delimiter = groupStr?',':'';
    $("#groupStr").val('Submit_Date'+Delimiter+groupStr);
    var groupStrNoChecked = $("input[lay-filter='group']:not(:checked)").map(function () {
      var str = $(this).val();
      return str.replace(str[0],str[0].toLowerCase());
    }).get().join(',');
    $("#groupStrNoChecked").val(groupStrNoChecked);
  }

  //获取form表单数据
  function getFormData() {
    return $("#layuiForm").serialize();
  }
</script>
</body>
