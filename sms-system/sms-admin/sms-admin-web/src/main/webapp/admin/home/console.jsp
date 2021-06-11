<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/admin/common/common.jsp" %>
<%@ include file="/admin/common/layui_head.html" %>
<head>
    <meta charset="utf-8">
    <title>首页</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <script type="text/javascript" src="/layuiadmin/lib/extend/echarts.js"></script>
    <script type="text/javascript" src="/js/index.js"></script>
    <style>
        .imgClass {
            width: 60px;
            height: 60px;
        }
        .divBorder {
            border: 1px;
            border-style: solid;
            border-color: rgba(204, 204, 204, 1);
            border-radius: 2px;
            margin-left: 2%;
        }

        .double_pie_chart {
            height: 400px;
        }

        #myChartPie {
            height: 400px;
        }

    </style>
</head>
<body>

<div class="layui-fluid">
    <div class="layui-row layui-col-space15">
        <div class="layui-col-sm4 layui-col-md4">
            <div class="layui-card" style="height:140px;font-weight: bold;">
                <div class="layui-card-body">
                    <div class="layui-col-xs12" style="line-height: 40px; text-align: right;">
                            <span class="layui-btn layui-btn-xs layui-btn-danger" style="vertical-align: top;">
                                上次登录
                            </span>
                    </div>
                    <div class="layui-col-xs5" style="text-align: center;">
                        你好：<label style="font-weight: bold;"><c:out value="${ADMIN_USER.user_Name}"/></label>
                    </div>
                    <div class="layui-col-xs1" style="line-height: 60px;"><img src="/public/images/admin/xian.png"></div>
                    <div class="layui-col-xs6">
                        时间： <fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${ADMIN_USER.last_Login_Time}"/>
                        <br>
                        IP地址：  <c:out value="${ADMIN_USER.last_Login_IP}"/>
                    </div>
                </div>
            </div>
        </div>

        <div class="layui-col-sm4 layui-col-md4">
            <div class="layui-card" style="height:140px;">
                    <div class="layui-card-body" style="text-align: center;font-weight: bold;">
                        <div class="layui-col-xs12" style="line-height: 40px; text-align: right;">
                            <span class="layui-btn layui-btn-warm layui-btn-xs" style="vertical-align: top;">今日</span>
                        </div>
                        <div class="layui-col-xs5" style="line-height: 30px;">
                            提交成功总数
                            <p class="layuiadmin-big-font" id="submitTotal"></p>

                        </div>
                        <div class="layui-col-xs2" style="line-height: 30px;">
                            <img src="/public/images/admin/xian.png">
                        </div>

                        <div class="layui-col-xs5" style="line-height: 30px;">
                            发送成功总数
                            <p class="layuiadmin-big-font" id="send_Success_Total"></p>
                        </div>
                    </div>
                </div>
            </div>

        <div class="layui-col-sm4 layui-col-md4">
            <div class="layui-card" style="height:140px;">
                    <div class="layui-card-body" style="text-align: center; font-weight: bold;">
                        <div class="layui-col-xs12" style="line-height: 40px; text-align: right;">
                            <span class="layui-btn layui-btn-green layui-btn-xs" style="vertical-align: top;">今日</span>
                        </div>
                        <div class="layui-col-xs12">
                            新增企业
                            <p class="layuiadmin-big-font" id="addNewEnterprise">
                            </p>
                        </div>
                    </div>
            </div>
        </div>
    </div>
    <div class="layui-row layui-col-space15">
        <div class="layui-col-sm8 layui-col-md8">
            <div class="layui-card">
                <div class="layui-row layui-col-space4">
                    <div id="myChart" style="width: 100%;height:400px;"></div>
                </div>
            </div>
        </div>
        <div class="layui-col-sm4 layui-col-md4">
            <div class="layui-card">
                <div class="layui-row layui-col-space4">
                    <div class="double_pie_chart">
                        <div id="myChartPie" ></div>

                        <h2 id="noSendSmsData"><img src="/public/images/admin/zanwushuju.png" style="margin: -427px 0px 0px 0%;width: 100%; height: 400px;" hidden></h2>
                    </div>
                </div>

            </div>
        </div>
    </div>


        <div class="layui-row layui-col-space15">
                <div class="layui-card">
                    <div class="layui-card-body">
                        <div class="layui-col-sm1 layui-col-md1 divBorder"
                             onclick="newTab('/admin/enterprise/list.jsp?&limitCode=003004','企业审核')">
                            <span style="display:block;margin-top:10%;text-align:center;"><img
                                    src="/public/images/admin/qyrz.svg" class="imgClass"></span>

                            <br/>
                            <span style="display:block;margin-top:10%;text-align:center;">企业审核</span>
                        </div>
                        <div class="layui-col-sm1 layui-col-md1 divBorder"
                             onclick="newTab('/admin/enterprise/list.jsp?&limitCode=003004','企业充值')">
                                    <span style="display:block;margin-top:10%;text-align:center;">
                                        <img src="/public/images/admin/qycz.svg" class="imgClass"></span>
                            <br/>
                            <span style="display:block;margin-top:10%;text-align:center;">企业充值</span>
                        </div>
                        <div class="layui-col-sm1 layui-col-md1 divBorder"
                             onclick="newTab('/admin/sended/approve_input_list.jsp?&limitCode=006023','短信审核')">
                                    <span style="display:block;margin-top:16%;text-align:center;">
                                        <img src="/public/images/admin/scsh.svg" class="imgClass">
                                    </span>
                            <br/>
                            <span style="display:block;margin-top:6%;text-align:center;">短信审核</span>
                        </div>
                        <div class="layui-col-sm1 layui-col-md1 divBorder"
                             onclick="newTab('/admin/enterprise/sms_template_list.jsp?&limitCode=004011','短信模板审核')">
                                    <span style="display:block;margin-top:12%;text-align:center;">
                                        <img src="/public/images/admin/mbsh.svg" class="imgClass">
                                    </span>
                            <br/>
                            <span style="display:block;margin-top:10%;text-align:center;">短信模板审核</span>
                        </div>

                        <div class="layui-col-sm1 layui-col-md1 divBorder"
                             onclick="newTab('/admin/business/material_List.jsp?&limitCode=010001','彩信素材审核')">
                                    <span style="display:block;margin-top:16%;text-align:center;">
                                        <img src="/public/images/admin/scsh.svg" class="imgClass">
                                    </span>
                            <br/>
                            <span style="display:block;margin-top:6%;text-align:center;">彩信素材审核</span>
                        </div>


                        <div class="layui-col-sm1 layui-col-md1 divBorder"
                             onclick="newTab('/admin/business/mms_template_list.jsp?&limitCode=010002','彩信模板审核')">
                                    <span style="display:block;margin-top:12%;text-align:center;">
                                        <img src="/public/images/admin/mbsh.svg" class="imgClass">
                                    </span>
                            <br/>
                            <span style="display:block;margin-top:10%;text-align:center;">彩信模板审核</span>
                        </div>

                        <div class="layui-col-sm1 layui-col-md1 divBorder"
                             onclick="newTab('/admin/rcs/rcs_material_list.jsp?&limitCode=009003','5G素材审核')">
                                    <span style="display:block;margin-top:16%;text-align:center;">
                                        <img src="/public/images/admin/scsh.svg" class="imgClass">
                                    </span>
                            <br/>
                            <span style="display:block;margin-top:6%;text-align:center;">5G素材审核</span>
                        </div>
                        <div class="layui-col-sm1 layui-col-md1 divBorder"
                             onclick="newTab('/admin/rcs/rcs_template_list.jsp?&limitCode=009007','5G模板审核')">
                                    <span style="display:block;margin-top:12%;text-align:center;">
                                        <img src="/public/images/admin/mbsh.svg" class="imgClass">
                                    </span>
                            <br/>
                            <span style="display:block;margin-top:10%;text-align:center;">5G模板审核</span>
                        </div>
                        <div>&nbsp;</div><div>&nbsp;</div><div>&nbsp;</div>
                        <div>&nbsp;</div><div>&nbsp;</div><div>&nbsp;</div>
                    </div>
                </div>

        </div>

</div>

<script>
    function newTab(url, title) {
        parent.layui.index.openTabsPage(url, title);
    }
</script>
</body>
</html>