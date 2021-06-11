<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/common/common.jsp" %>
<%@ include file="/common/layui_head.html" %>
<body>
<div class="layui-fluid">
    <div class="layui-row layui-col-space15">
        <div class="layui-col-md12">
            <div class="layui-card">
                <form class="layui-form layui-card-header layuiadmin-card-header-auto" id="layuiForm" onsubmit="return false;">
                    <table class="layui-input-item">
                        <div class="layui-input-item">
                            <div class="layui-input-item">
                                <div class="layui-inline">
                                    &nbsp;&nbsp;手机号码&nbsp;&nbsp;&nbsp;
                                </div>
                                <div class="layui-inline">
                                    <input name="phone_Nos" class="layui-input"/>
                                </div>
                                <div class="layui-inline">
                                    &nbsp;&nbsp;消息类型&nbsp;
                                    <div class="layui-inline">
                                        <ht:herocodeselect sortCode="message_type_code"  name="message_Type_Code"/>
                                    </div>
                                </div>
                                <div class="layui-inline">
                                    &nbsp;&nbsp;批次号&nbsp;&nbsp;&nbsp;
                                </div>
                                <div class="layui-inline">
                                    <input name="msg_Batch_No" class="layui-input"/>
                                </div>
                                <div class="layui-inline">
                                    &nbsp;&nbsp;消息内容&nbsp;&nbsp;&nbsp;
                                </div>
                                <div class="layui-inline">
                                    <input name="content" class="layui-input"/>
                                </div>
                                <div class="layui-inline">&nbsp;&nbsp;提交时间&nbsp;</div>
                                <div class="layui-inline">
                                    <input name='minCreateDate' class="layui-input layui-input-sm" id="minCreateDate"/>
                                </div>
                                <div class="layui-inline">
                                    -
                                </div>
                                <div class="layui-inline">
                                    <input name='maxCreateDate' class="layui-input layui-input-sm" id="maxCreateDate"/>
                                </div>
                                <div class="layui-inline">
                                    <button class="layui-btn layui-btn-sm" type="submit" lay-submit="" lay-filter="reload">
                                        搜索
                                    </button>
                                </div>
                            </div>
                        </div>
                    </table>
                </form>
                <div class="layui-form layui-border-box layui-table-view">
                    <div class="layui-card-body">
<%--                        <blockquote class="layui-elem-quote" id="statistics" style="padding: 10px;"></blockquote>--%>
                        <table class="layui-hide" id="list_table" lay-filter="list_table"></table>
                        <script type="text/html" id="table-toolbar">
                            <div class="layui-btn-container">
                                <%@include file="/common/button_action_list.jsp" %>
                            </div>
                        </script>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>


<c:forEach items="${ADMIN_USER.limits}" var="i" varStatus="st">
    <c:if test="${i.code=='006012002'}">
        <input id="limitTypeInput" value="006012002" hidden />
    </c:if>
</c:forEach>
</body>
<script>
  var $;
  layui.use('laydate', function(){
    var laydate = layui.laydate;
    var today = new Date();
    var minCreateDate = '<c:out value="${minCreateDate}"/>';
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

  layui.extend({tableExt: '/js/layui-ext/tableExt'}).use(['tableExt'], function () {
    var table = layui.tableExt;
    $ = layui.$;
    var limitTypeInput = $("#limitTypeInput").val();
    table.render({
      url: '/admin/sended_inputLogList',
      height: 'full-120',
      where:{
        'minCreateDate': $("#minCreateDate").val()==''? minCreateDate:$("#minCreateDate").val(),
        'maxCreateDate':$("#maxCreateDate").val()
      }
      , cols: [[
        {field: 'id', title: 'ID', width: 50, sort: true, type: 'checkbox'},
        {title: '手机号',width: 155,templet:function (d) {
            return handleData(d.phone_Nos);
          }},
        {title: '批次号',width: 170,templet:function (d) {
            return handleData(d.msg_Batch_No);
          }},
        {field: 'content', title: '消息内容', minWidth: 300},
        { title: '类型/字数/号码/计费',templet:function (d) {
            var a = handleData(d.message_Type_Code_name);
            a += '/'+handleData(d.content_Length);
            a += '/'+handleData(d.phone_Nos_Count)+'个<br>';
            a += '计费:'+handleData(d.fee_Count)+'条='+ handleData(d.sale_Amount)+'元';
            return a;
          }, width: 200},
        {field: 'input_Date', title: '提交时间', width: 170},
        {field: 'create_Date', title: '分拣时间', width: 170},
        {title: '操作',width: 150,templet:function (d) {
            var a = '';
            if(limitTypeInput == '006012002'){
              a = "<button class=\"layui-btn layui-btn-xs\" lay-event='{\"type\":\"tagTodo\",\"url\":\"/admin/sended_reportIndex?limitCode=006012002&flag=Input_Log_Flag&minCreateDate="+d.create_Date+"&msg_Batch_No="+d.msg_Batch_No+"\",\"width\":\"750\",\"height\":\"500\",\"title\":\"详情\"}'  title=\"详情\" >查看详情</button>";
            }
            return a;
          }}
      ]], done: function (res) {
            layui.$("#statistics").html("");
            layui.use('element', function() {
                var element = layui.element;
                element.init();
            });
            // queryTotalData();
      }
    });
  });

  function getFormData() {
    return $("#layuiForm").serialize();
  }

</script>