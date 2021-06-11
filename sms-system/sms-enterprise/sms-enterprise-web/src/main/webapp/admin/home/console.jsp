<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/common/common.jsp" %>
<%@ include file="/common/layui_head.html" %>
<html>
<head>
    <meta charset="utf-8">
    <title>首页</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <style type="text/css">
        ul {
            width: 90%;
        }

        li {
            white-space: nowrap;
            overflow: hidden;
            text-overflow: ellipsis;
        }

        .rectangle {
            width: 90%;
            height: 40px;
            border: 1px solid #00CCCC;
            margin-top: 10px;
            padding-top: 20px;
            padding-left: 20px;
        }

        .sengMsg {
            width: 50%;
            height: 30px;
            border: 1px solid #00CCCC;
            margin-top: 20px;
            margin-left: 25%;
            padding-top: 10px;
            text-align: center;
        }

        .sms_statistics {
            margin-left: 30%;
        }

        .double_pie_chart {
            height: 400px;
        }

        #pieChart {
            margin-top: 10px;
            height: 400px;
        }
    </style>
    <script id="indexJs" type="text/javascript"
            data='<ht:heropageconfigurationtext code="agent_webpage_css" defaultValue="/layuiadmin"/>'></script>
    <script src='<ht:heropageconfigurationtext code="agent_webpage_css" defaultValue="/layuiadmin"/>/lib/extend/echarts.js'></script>
    <script src="/js/jquery-3.4.1.min.js"></script>
    <script src="/js/index.js"></script>
</head>
<body>
<div class="layui-fluid">
    <div class="layui-row layui-col-space15">
        <div class="layui-col-md6">
            <div class="layui-card" style="height:300px;">
                <div class="layui-card-body">
                    <div class="layui-col-md12" style="margin-top: 10px">
                        <h2>${ADMIN_USER.real_Name}</h2>
                    </div>
                    <div class="layui-col-xs5" style="margin-top: 10px">
                        <c:choose>
                            <c:when test="${ENTERPRISE_INFO.authentication_State_Code == '01'}">
                                <button class="layui-btn layui-btn-radius">
                                    <c:out value="${ENTERPRISE_INFO.authentication_State_Code_Name}"/>
                                </button>
                            </c:when>
                            <c:when test="${ENTERPRISE_INFO.authentication_State_Code == '00'}">
                                <button class="layui-btn layui-btn-radius">
                                    未认证
                                </button>
                                <a href="#" onclick='authEnterprise<c:out value="(${ENTERPRISE_INFO.id})"/>'>
                                    立即认证</a>
                            </c:when>
                            <c:when test="${ENTERPRISE_INFO.authentication_State_Code == '02'}">
                                <button class="layui-btn layui-btn-radius">
                                    未认证
                                </button>
                                <a href="#" onclick='authEnterprise<c:out value="(${ENTERPRISE_INFO.id})"/>'/>'>
                                立即认证</a>
                            </c:when>
                        </c:choose>
                    </div>
                    <div class="layui-col-xs7">
                        <div class="layui-col-md4" style="margin-top:20px; font-weight: bold;text-align: right;">
                            账户余额（￥）
                        </div>
                        <div class="layui-col-md5" style="margin-top:20px;font-size: 20px;text-align: right;"><c:out
                                value="${ADMIN_USER.available_Amount}"/></div>
                        <div class="layui-col-md3" style="margin-top:15px;text-align: right;">
                            <button class="layui-btn layui-btn-primary"
                                    onclick="newTab('/admin/charge_preCharge?&limitCode=008004','我要充值')"
                                    style="border-color: #00CCCC; color: #1aa094; width:80px;">充 值
                            </button>
                        </div>
                    </div>

                    <div class="layui-col-xs12">
                        <div class="layui-card">
                            <hr/>
                            <img src="/public/admin/images/advertising.jpg" width="100%" height="160px">
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="layui-col-md3">
            <div class="layui-card" style="height:300px;">
                <div class="layui-card-header">
                    <span class="layui-badge layui-bg-orange layuiadmin-badge">今日</span>
                </div>
                <div class="layui-card-body">
                    <div class="rectangle">提交总数 <span class="layuiadmin-big-font sms_statistics"
                                                      id="submitTotal"></span></div>
                    <div class="rectangle">发送成功 <span class="layuiadmin-big-font sms_statistics"
                                                      id="sendSuccessCount"></span></div>
                    <div class="rectangle">消费金额 <span class="layuiadmin-big-font sms_statistics"
                                                      id="costTotal"></span></div>
                </div>
            </div>
        </div>
        <div class="layui-col-md3">
            <div class="layui-card" style="height:300px;">
                <div class="layui-card-header">
                    企业公告
                </div>
                <div class="layui-card-body layuiadmin-card-list">
                    <ul class="layuiadmin-card-status layuiadmin-home2-usernote">
                        <li>
                            1.庄点科技有限公司推出新活动，新用户开户送好礼啦，快来看看。
                        </li>
                        <li>
                            2.信相通短信平台V3.2版本上线啦，更新了诸多功能。
                        </li>
                        <li>
                            3.信相通短信平台V3.1版本更新啦，修改了一些问题。
                        </li>
                        <li>
                            4.庄点科技有限公司推出新活动，老用户充值送好礼啦，快来看看。
                        </li>
                        <li>
                            5.庄点科技有限公司推出新活动，老用户充值送好礼啦，快来看看。
                        </li>
                    </ul>
                </div>
            </div>
        </div>
    </div>

    <div class="layui-row layui-col-space15">
        <div class="layui-col-md9">
            <div class="layui-row layui-col-space15">
                <div class="layui-col-sm4">
                    <div class="layui-card" style="height:200px;">
                        <div class="layui-card-body">
                            <p>普通短信</p>
                            <p style="height:75px"><font size="1" face="serif">简单易用，安全稳定，验证码短信5s可达。
                                支持API快速接入，或直接手工发送</font>
                            </p>
                            <hr/>
                            <div class="sengMsg"
                                 onclick="newTab('/admin/sended/send_sms.jsp?&limitCode=006009','我要发送')">
                                我要发送
                            </div>
                        </div>
                    </div>
                </div>
                <div class="layui-col-sm4">
                    <div class="layui-card" style="height:200px;">
                        <div class="layui-card-body">
                            <p>彩信</p>
                            <p style="height:75px"><font size="1" face="serif">除基本的文字信息以外,更配有丰富的彩色图片、声音、动画、震动等多媒体的内容,图文并茂,生动直观</font>
                            </p>
                            <hr/>
                            <div class="sengMsg" onclick="newTab('/admin/sended_preSendMMS?&limitCode=007001','发送彩信')">
                                我要发送
                            </div>
                        </div>
                    </div>
                </div>
                <div class="layui-col-sm4">
                    <div class="layui-card" style="height:200px;">
                        <div class="layui-card-body">
                            <p>5G短信</p>
                            <p style="height:75px"><font size="1"
                                                         face="serif">快速传递文本、图片、音频、视频等多媒体信息，内容更丰富，展现更生动，带来更好的沟通体验</font>
                            </p>
                            <hr/>
                            <div class="sengMsg" onclick="newTab('/admin/rcs/rcs_send.jsp?&limitCode=009005','我要发送')">
                                我要发送
                            </div>
                        </div>
                    </div>
                </div>
                <div class="layui-col-sm12 layui-col-md12">
                    <div class="layui-card">
                        <div class="layui-card-body" style="height:270px;">
                            <h2 style="margin: 15px;">用户资费</h2>
                            <table class="layui-hide" id="enterpriseFeeTable"></table>
                            <div style="margin: 10px 0px 0px 50%">
                            <span id="showMorefee"
                                  onclick="newTab('/admin/enterprise/enterprise_fee_list.jsp','用户资费')">查看更多资费信息</span>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="layui-col-md3">
            <div class="layui-row" layui-col-space15>
                <div class="layui-col-sm12 layui-col-md12">
                    <div class="layui-card">
                        <div class="layui-card-body double_pie_chart">
                            <div id='pieChart'></div>
                            <h2 id="noSendSmsData" style='margin: -330px 0px 0px 30%;' hidden>暂无发送数据</h2>
                        </div>
                    </div>
                    <div class="layui-card">
                        <div class="layui-card-body">
                            <div style="margin: 0px 0px 0px 10%">
                                时间&nbsp;&nbsp; <fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss"
                                                               value="${ADMIN_USER.last_Login_Time}"/><br/>
                                IP地址&nbsp;&nbsp; <c:out value="${ADMIN_USER.last_Login_IP}"/>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">
    //认证上传
    function authEnterprise(id) {
        layui.use("layer", function () {
            layer.open({
                type: 2,
                title: "企业认证",
                content: "/admin/enterprise_preAuthEnterpriseInfo?id=" + id,
                area: ["750px", "500px"],
                maxmin: true,
            });
        });
    }

    function newTab(url, title) {
        parent.layui.index.openTabsPage(url, title);
    }

    function popUpTab(url, title) {
        layer.open({
            type: 2,
            title: title,
            content: url,
            maxmin: true,
            area: ["70%", "80%"],
            btn: ['确定', '取消'],
            yes: function (index, layero) {
                var submit = layero.find('iframe').contents().find("#layuiadmin-app-form-submit");
                submit.click();
            }
        });
    }

    layui.use('table', function () {
        var table = layui.table;
        table.render({
            elem: '#enterpriseFeeTable'
            , url: '/admin/enterprise_enterpriseUserFeeList'
            , cellMinWidth: 80 //全局定义常规单元格的最小宽度，layui 2.2.1 新增
            , cols: [[
                {
                    title: '国家', minWidth: 120, templet: function (d) {
                        return handleData(d.country_Number_name);
                    }
                },
                {
                    title: '运营商', minWidth: 120, templet: function (d) {
                        return handleData(d.operator);
                    }
                },
                {
                    title: '行业', minWidth: 120, templet: function (d) {
                        return handleData(d.trade_Type_Code_name);
                    }
                },
                {
                    title: '消息类型', minWidth: 120, templet: function (d) {
                        return handleData(d.message_Type_Code_name);
                    }
                },
                {field: 'unit_Price', title: '单价(元)', minWidth: 200}
            ]],
            done: function (res) {
                if (res.code != "0") return;
                if (res.data.length > 3) {
                    $('#showMorefee').show()
                } else {
                    $('#showMorefee').hide()
                }
            }
        });
    });
    $(function(){
        if (!'${sessionScope.ADMIN_USER.user_Name}') {
            window.parent.location.href="/";
            return;
        }
        //是否修改过密码
        if (!'${sessionScope.ADMIN_USER.last_Update_Password_Time}') {
            setTimeout(function () {
                layui.use("layer",function(){
                    parent.layer.open({
                        type: 2,
                        title: "修改密码",
                        content: "/admin/home/edit_password.jsp",
                        area:["750px","500px"],
                        maxmin: true,
                        closeBtn:0,
                    });
                });
            },500)
            return;
        }
    });
</script>
</body>
</html>
