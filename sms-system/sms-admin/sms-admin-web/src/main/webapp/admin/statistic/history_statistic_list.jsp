<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/admin/common/common.jsp" %>
<%@ include file="/admin/common/layui_head.html" %>
<%@ include file="/admin/common/country_operator.jsp" %>
<body>

<div class="layui-fluid">
    <div class="layui-row layui-col-space15">
        <div class="layui-col-md12">
            <div class="layui-card">
                <%--                <div id="myChart" style="width: 100%;height:400px;"></div>--%>
                <form class="layui-form layui-card-header layuiadmin-card-header-auto" id="layuiForm"
                      onsubmit="return false;">
                    <div class="layui-inline">
                        &nbsp;&nbsp;企业名称&nbsp;
                        <div class="layui-inline" style="width: 200px">
                            <c:if test="${ADMIN_USER.roles.get(0).code!='Business'}">
                                <ht:heroenterpriseselect id="enterprise_No_Head" name="enterprise_No"/>
                            </c:if>
                            <c:if test="${ADMIN_USER.roles.get(0).code=='Business'}">
                                <ht:heroenterpriseselect id="enterprise_No_Head" name="enterprise_No"
                                                         adminUserId="${ADMIN_USER.id}"/>
                            </c:if>
                        </div>
                    </div>
                    <c:if test="${ADMIN_USER.roles.get(0).code!='Business'}">
                        <div class="layui-inline">
                            &nbsp;&nbsp;企业用户&nbsp;
                            <div class="layui-inline" style="width: 200px">
                                <ht:herocustomdataselect dataSourceType="allEnterpriseUsers" name="enterprise_User_Id"/>
                            </div>
                        </div>
                    </c:if>
                    <div class="layui-inline">
                        &nbsp;&nbsp;国家&nbsp;
                        <div class="layui-input-inline">
                            <ht:herocodeselect sortCode="country" name="country_Number" id="country_Number"
                                               valueField="Value"/>
                        </div>
                    </div>&nbsp;&nbsp;
                    <div class="layui-inline">
                        &nbsp;&nbsp;运营商&nbsp;
                        <div class="layui-input-inline">
                            <ht:countryoperatorselect id="operator" name="operator"/>
                        </div>
                    </div>&nbsp;&nbsp;
                    <c:if test="${ADMIN_USER.roles.get(0).code!='Business'}">
                        <div class="layui-inline">
                            &nbsp;&nbsp;通道&nbsp;
                            <div class="layui-inline" style="width: 200px">
                                <ht:herocustomdataselect dataSourceType="allChannels" name="channel_No"/>
                            </div>
                        </div>

                        <div class="layui-inline">
                            &nbsp;&nbsp;商务&nbsp;
                            <div class="layui-inline" style="width: 200px">
                                <ht:herocustomdataselect dataSourceType="allBusinessUser" name="business_User_Id"/>
                            </div>
                        </div>
                    </c:if>
                    <div class="layui-inline">
                        &nbsp;&nbsp;区域&nbsp;
                        <div class="layui-inline" style="width: 200px">
                            <ht:herocodeselect sortCode="location" name="province_Code"/>
                        </div>
                    </div>
                    <div class="layui-inline">
                        &nbsp;&nbsp;签名&nbsp;
                        <div class="layui-inline">
                            <input type="text" class="layui-input" name="signature">
                        </div>
                    </div>
                    <div class="layui-inline">
                        &nbsp;&nbsp;消息类型&nbsp;
                        <div class="layui-inline">
                            <ht:herocodeselect sortCode="message_type_code" name="message_Type_Code"/>
                        </div>
                    </div>
                    <div class="layui-inline">
                        &nbsp;&nbsp;成功率&nbsp;
                        <div class="layui-inline" style="width: 100px">
                            <input type="text" class="layui-input" name="min_Success_Rate">
                        </div>
                        %-
                        <div class="layui-inline" style="width: 100px">
                            <input type="text" class="layui-input" name="max_Success_Rate">
                        </div>
                        %
                    </div>
                    <div class="layui-inline">
                        &nbsp;&nbsp;失败率&nbsp;
                        <div class="layui-inline" style="width: 100px">
                            <input type="text" class="layui-input" name="min_Failure_Rate">
                        </div>
                        %-
                        <div class="layui-inline" style="width: 100px">
                            <input type="text" class="layui-input" name="max_Failure_Rate">
                        </div>
                        %
                    </div>
                    <div class="layui-inline">
                        <%--                        &nbsp;&nbsp;分组&nbsp;--%>
                        <div class="layui-inline" style="width: 100%">
                            <input type="text" name="group_Str" id="group_Str" value="Time_Cycle" hidden/>
                            <input type="text" id="groupStrNoChecked"
                                   value="agent_No,business_User_Id,enterprise_No,enterprise_User_Id,channel_No,message_Type_Code,country_Number,operator,province_Code,signature"
                                   hidden/>
                            <c:if test="${ADMIN_USER.roles.get(0).code!='Business'}">
                                <input type="checkbox" lay-filter="group" value="Business_User_Id" lay-skin="primary"
                                       title="商务">
                                <input type="checkbox" lay-filter="group" value="Enterprise_No" lay-skin="primary"
                                       title="企业">
                                <input type="checkbox" lay-filter="group" value="Enterprise_User_Id" lay-skin="primary"
                                       title="用户">
                                <input type="checkbox" lay-filter="group" value="Channel_No" lay-skin="primary" title="通道">
                            </c:if>
                            <c:if test="${ADMIN_USER.roles.get(0).code=='Business'}">
                                <input type="checkbox" lay-filter="group" value="Enterprise_No" lay-skin="primary"
                                       title="企业">
                            </c:if>
                            <input type="checkbox" lay-filter="group" value="Message_Type_Code" lay-skin="primary"
                                   title="消息类型">
                            <input type="checkbox" lay-filter="group" value="Country_Number" lay-skin="primary"
                                   title="国家">
                            <input type="checkbox" lay-filter="group" value="Operator" lay-skin="primary" title="运营商">
                            <input type="checkbox" lay-filter="group" value="Province_Code" lay-skin="primary"
                                   title="区域">
                            <input type="checkbox" lay-filter="group" value="Signature" lay-skin="primary" title="签名">
                        </div>
                    </div>
                    <div class="layui-inline">
                        &nbsp;&nbsp;统计时间&nbsp;
                        <div class="layui-inline" style="width: 150px">
                            <input type="text" class="layui-input" id="min" placeholder="yyyy-MM-dd"
                                   name="min_Statistics_Date_Str">
                            <input type="text" class="layui-input" id="minMonth" placeholder="yyyy-MM"
                                   style="display:none">
                        </div>
                        -
                        <div class="layui-inline" style="width: 150px">
                            <input type="text" class="layui-input" id="max" placeholder="yyyy-MM-dd"
                                   name="max_Statistics_Date_Str">
                            <input type="text" class="layui-input" id="maxMonth" placeholder="yyyy-MM"
                                   style="display:none">
                        </div>
                    </div>
                    <div class="layui-inline">
                        报表类型&nbsp;
                        <div class="layui-inline" style="width: 150px">
                            <ht:herocodeselect sortCode="statistic_time_cycle" includeHeader="0" id="time_Cycle"
                                               name="time_Cycle" layFilter="timeCycleId"
                                               selected="day"></ht:herocodeselect>
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
  layui.use(['form'], function () {
    var form = layui.form;
    var laydate = layui.laydate;
    form.on('select(timeCycleId)', function (data) {
      if (data.value == 'month') {
        $('#min').removeAttr("name");
        $('#max').removeAttr("name");
        $("#minMonth").attr("name", "min_Statistics_Date_Str");
        $("#maxMonth").attr("name", "max_Statistics_Date_Str");
        $('#minMonth').css("display", "block").siblings().css('display', 'none');
        $('#maxMonth').css("display", "block").siblings().css('display', 'none');
      } else {
        $('#minMonth').removeAttr("name");
        $('#maxMonth').removeAttr("name");
        $("#min").attr("name", "min_Statistics_Date_Str");
        $("#max").attr("name", "max_Statistics_Date_Str");
        $('#min').css("display", "block").siblings().css('display', 'none');
        $('#max').css("display", "block").siblings().css('display', 'none');
      }
    })
  })
  layui.extend({tableExt: '/layuiadmin/extends/tableExt'}).use(['tableExt', 'laydate'], function () {
    var table = layui.tableExt;
    $ = layui.$;
    var laydate = layui.laydate;
    var nowTime = new Date();
    laydate.render({
      elem: '#min'
      , format: "yyyy-MM-dd"
      , max: 'nowTime' //默认最大值为当前日期
      , value: getFormatDateBefore(34)
      , trigger: 'click'
    });
    laydate.render({
      elem: '#max'
      , format: "yyyy-MM-dd"
      , max: 'nowTime' //默认最大值为当前日期
      , value: getFormatDateBefore(4)
      , trigger: 'click'
    });
    laydate.render({
      elem: '#minMonth'
      , format: "yyyy-MM"
      , max: 'nowTime' //默认最大值为当前日期
      , value: getFormatDateBeforeMonth(4)
      , type: 'month'
      , trigger: 'click'
    });
    laydate.render({
      elem: '#maxMonth'
      , format: "yyyy-MM"
      , max: 'nowTime' //默认最大值为当前日期
      , value: getFormatDateBeforeMonth(4)
      , type: 'month'
      , trigger: 'click'
    });
    var index = layer.load(0, {
      offset: '200px'
    });
    table.render({
      url: '/admin/statistic_historyStatisticList',
      height: 'full-200',
      where: {
        'min_Statistics_Date_Str': $("#min").val() == '' ? getFormatDateBefore(34) : $("#min").val(),
        'max_Statistics_Date_Str': $("#max").val() == '' ? getFormatDateBefore(4) : $("#max").val(),
        'time_Cycle': 'day',
        'group_Str': 'time_Cycle'
      },
      totalRow: true,
      cols: [[
        {type: 'checkbox', totalRowText: '合计'},
        {
          field: 'statistics_Date', title: '时间', width: 150, templet: function (d) {
            var timeCycle = $('#time_Cycle').val();
            if (timeCycle == 'day') {
              return layui.util.toDateString(d.statistics_Date, 'yyyy年MM月dd日');
            } else {
              return layui.util.toDateString(d.statistics_Date, 'yyyy年MM月');
            }
          }
        },
        {
          field: 'business_User_Id', title: '商务', width: 220, templet: function (d) {
            return !d.business_User_Id_ext ? '---' : handleData(d.business_User_Id_ext.real_Name);
          }
        },
        {
          field: 'enterprise_No', title: '企业名称', width: 220, templet: function (d) {
            return !d.enterprise_No_ext ? '---' : handleData(d.enterprise_No_ext.name);
          }
        },
        {
          field: 'enterprise_User_Id', title: '企业用户', width: 220, templet: function (d) {
            return !d.enterprise_User_Id_ext ? '---' : handleData(d.enterprise_User_Id_ext.real_Name)
                + "(" + handleData(d.enterprise_User_Id_ext.user_Name) + ")";
          }
        },
        {
          field: 'channel_No', title: '发送通道', minWidth: 180, templet: function (d) {
            var channelName = !d.channel_No_ext ? '---' : handleData(d.channel_No_ext.name);
            var channelSource = !d.channel_No_ext ? '---' : handleData(d.channel_No_ext.channel_Source_name);
            return channelName + '</br>【' + channelSource + '】';
          }
        },
        {
          field: 'country_Number', title: '国家', minWidth: 180, templet: function (d) {
            return handleData(d.country_Number_name);
          }
        },
        {
          field: 'operator', title: '运营商', minWidth: 180, templet: function (d) {
            return handleData(d.operator);
          }
        },
        {
          field: 'message_Type_Code', title: '消息类型', minWidth: 180, templet: function (d) {
            return handleData(d.message_Type_Code_name);
          }
        },
        {field: 'signature', title: '签名', width: 120, totalRow: true},
        {
          field: 'province_Code', title: '区域', minWidth: 180, templet: function (d) {
            return handleData(d.province_Code_name);
          }
        },
        {field: 'submit_Total', title: '提交条数', width: 120, totalRow: true},
        {field: 'submit_Success_Total', title: '提交成功', width: 120, totalRow: true},
        {field: 'submit_Faild_Total', title: '提交失败', width: 120, totalRow: true},
        {field: 'send_Success_Total', title: '发送成功', width: 120, totalRow: true},


          {field: 'send_Faild_Total', title: '发送失败',width: 120, totalRow: true,templet:function (d) {
              var ahref = "<br><a href='javascript:;' lay-event='{\"type\":\"dialogNoBtnCustomerArea\",\"url\":\"/admin/statistic_sendFaildNativeStatusDetails?"
                  +"min_Statistics_Date_Str="+layui.util.toDateString(d.statistics_Date, 'yyyy-MM-dd')
                  +"&enterprise_User_Id_Ext="+d.enterprise_User_Id
                  +"&channel_No="+d.channel_No
                  +"&enterprise_No="+d.enterprise_No
                  +"&business_User_Id_Ext="+d.business_User_Id
                  +"&agent_No="+d.agent_No
                  +"&message_Type_Code="+d.message_Type_Code
                  +"&country_Number="+d.country_Number
                  +"&operator="+d.operator
                  +"&province_Code="+d.province_Code
                  +"&signature="+d.signature
                  +"&Submit_Success_Sms_Total="+d.submit_Success_Total
                  +"&databaseName=history&group_Str="+$("#group_Str").val()+"\",\"width\":\"70%\",\"height\":\"90%\",\"title\":\"错误编码\"}' style='color: #01aaed;text-decoration-line: underline;'>错误码详情</a>";
              return d.send_Faild_Total+ahref;
          }},



        {
          title: '发送未知', width: 120, templet: function (d) {
            return d.send_Unknown_Total < 0 ? 0 : d.send_Unknown_Total;
          }
        },
        {
          field: 'success_Rate', title: '成功率', templet: function (d) {
            var str = Number(d.success_Rate).toFixed(2);
            return str += "%";
          }, width: 100
        },
        {
          field: 'failure_Rate', title: '失败率', templet: function (d) {
            var str = Number(d.failure_Rate).toFixed(2);
            return str += "%";
          }, width: 100
        },
        {field: 'channel_Taxes', title: '发票抵扣', width: 120, totalRow: true},
        {field: 'enterprise_User_Taxes', title: '发票成本', width: 120, totalRow: true},
        {field: 'unit_Cost', title: '成本', width: 120, totalRow: true},
        {field: 'profits', title: '利润', width: 120, totalRow: true}
      ]]
      , done: function (re) {
        var str = $("#groupStrNoChecked").val();
        var notChecked = str.split(',');
        for (var i = 0; i < notChecked.length; i++) {
          $("[data-field=" + notChecked[i] + "]").css('display', 'none');
        }
        layer.close(index);
      }
    });
  });

  layui.use(['form', 'laydate'], function () {
    var $ = layui.$
        , form = layui.form;
    form.on('checkbox(group)', function (data) {
      var groupStr = $("input[lay-filter='group']:checked").map(function () {
        return $(this).val();
      }).get().join(',');
      var Delimiter = groupStr ? ',' : '';
      $("#group_Str").val('Time_Cycle' + Delimiter + groupStr);
      var groupStrNoChecked = $("input[lay-filter='group']:not(:checked)").map(function () {
        var str = $(this).val();
        return str.replace(str[0], str[0].toLowerCase());
      }).get().join(',');
      $("#groupStrNoChecked").val(groupStrNoChecked);
    });
    form.render();

  });

  function getFormData() {
    var str = $("#layuiForm").serialize();
    return str;
  }
</script>