<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">
<style type="text/css">
body {
            zoom: 1.1;
            -moz-transform: scale(1.1);
            -moz-transform-origin: 0 0;
        }
.b1{white-space-collapsing:preserve;}
.b2{margin: 1.0in 1.25in 1.0in 1.25in;}
.p1{text-align:justify;hyphenate:auto;font-family:Times New Roman;font-size:10pt;}
.p2{text-align:justify;hyphenate:auto;font-family:宋体;font-size:16pt;}
.p3{text-align:justify;hyphenate:auto;font-family:Times New Roman;font-size:12pt;}
.p4{text-align:justify;hyphenate:auto;font-family:Consolas;font-size:12pt;}
.p5{text-align:start;hyphenate:auto;font-family:宋体;font-size:16pt;}
.p6{text-align:start;hyphenate:auto;font-family:Times New Roman;font-size:12pt;}
.s1{font-weight:bold;color:black;}
.td1{width:1.9208333in;padding-start:0.0in;padding-end:0.0in;border-bottom:1.0pt solid white;border-top:1.0pt solid white;}
.td2{width:0.8208333in;padding-start:0.0in;padding-end:0.0in;border-bottom:1.0pt solid white;border-top:1.0pt solid white;}
.td3{width:1.2381945in;padding-start:0.0in;padding-end:0.0in;border-bottom:1.0pt solid white;border-top:1.0pt solid white;}
.td4{width:1.9347222in;padding-start:0.0in;padding-end:0.0in;border-bottom:1.0pt solid white;border-top:1.0pt solid white;}
.td5{width:1.0416666in;padding-start:0.0in;padding-end:0.0in;border-bottom:1.0pt solid white;border-top:1.0pt solid white;}
.td6{width:1.0652778in;padding-start:0.0in;padding-end:0.0in;border-bottom:1.0pt solid white;border-top:1.0pt solid white;}
.td7{width:1.9583334in;padding-start:0.0in;padding-end:0.0in;border-bottom:1.0pt solid white;border-top:1.0pt solid white;}
.td8{width:5.986111in;padding-start:0.0in;padding-end:0.0in;border-bottom:1.0pt solid white;border-top:1.0pt solid white;}
.td9{width:1.9208333in;padding-start:0.0in;padding-end:0.0in;border-top:1.0pt solid white;}
.td10{width:1.0416666in;padding-start:0.0in;padding-end:0.0in;border-top:1.0pt solid white;}
.td11{width:1.0652778in;padding-start:0.0in;padding-end:0.0in;border-top:1.0pt solid white;}
.td12{width:1.9583334in;padding-start:0.0in;padding-end:0.0in;border-top:1.0pt solid white;}
.r1{height:0.4097222in;keep-together:always;}
.t1{table-layout:fixed;border-collapse:collapse;border-spacing:0;}
</style>
<title>电信级短信平台</title>
</head>
<body class="b1 b2">

<p class="p2">
<span class="s1">上行MO数据查询请求报文说明</span>
</p>
<p class="p1"></p>
<p class="p3">
<span>请求地址：http://**********/APIControllerV2/v2/mo</span>
</p>
<br/>
<p class="p3">
<span>请求报文：请求时间间隔为30秒</span>
</p>
<p class="p4"></p>
<table class="t1">
<tbody>
<tr class="r1">
<td class="td1">
<p class="p5">
<span class="s1">参数</span>
</p>
</td><td class="td2">
<p class="p5">
<span class="s1">类型</span>
</p>
</td><td class="td3">
<p class="p5">
<span class="s1">是否必填</span>
</p>
</td><td class="td4">
<p class="p5">
<span class="s1">参数含义</span>
</p>
</td>
</tr>
<tr class="r1">
<td class="td1">
<p class="p6">
<span>enterprise_no</span>
</p>
</td><td class="td2">
<p class="p6">
<span>String</span>
</p>
</td><td class="td3">
<p class="p6">
<span>是</span>
</p>
</td><td class="td4">
<p class="p6">
<span>企业编号</span>
</p>
</td>
</tr>
<tr class="r1">
<td class="td1">
<p class="p6">
<span>account</span>
</p>
</td><td class="td2">
<p class="p6">
<span>String</span>
</p>
</td><td class="td3">
<p class="p6">
<span>是</span>
</p>
</td><td class="td4">
<p class="p6">
<span>账号</span>
</p>
</td>
</tr>
<tr class="r1">
<td class="td1">
<p class="p6">
<span>sign</span>
</p>
</td><td class="td2">
<p class="p6">
<span>String</span>
</p>
</td><td class="td3">
<p class="p6">
<span>是</span>
</p>
</td><td class="td4">
<p class="p6">
<span>签名：参考签名说明</span>
</p>
</td>
</tr>
<tr class="r1">
<td class="td1">
<p class="p6">
<span>timestamp</span>
</p>
</td><td class="td2">
<p class="p6">
<span>String</span>
</p>
</td><td class="td3">
<p class="p6">
<span>是</span>
</p>
</td><td class="td4">
<p class="p6">
<span>时间戳</span>
</p>
</td>
</tr>
</tbody>
</table>
<p class="p1"></p>
<p class="p1"></p>
<p class="p1"></p>
<br/>
<p class="p3">
<span>响应报文：</span>
</p>
<table class="t1">
<tbody>
<tr class="r1">
<td class="td1">
<p class="p5">
<span class="s1">参数</span>
</p>
</td><td class="td5">
<p class="p5">
<span class="s1">类型</span>
</p>
</td><td class="td6">
<p class="p5">
<span class="s1">是否必填</span>
</p>
</td><td class="td7">
<p class="p5">
<span class="s1">参数含义</span>
</p>
</td>
</tr>
<tr class="r1">
<td class="td8" colspan="4">
<p class="p6">
<span>请求失败：</span>
</p>
</td>
</tr>
<tr class="r1">
<td class="td1">
<p class="p6">
<span>errorCode</span>
</p>
</td><td class="td5">
<p class="p6">
<span>String</span>
</p>
</td><td class="td6">
<p class="p6">
<span>是</span>
</p>
</td><td class="td7">
<p class="p6">
<span>错误编码：参考编码列表</span>
</p>
</td>
</tr>
<tr class="r1">
<td class="td1">
<p class="p6">
<span>message</span>
</p>
</td><td class="td5">
<p class="p6">
<span>String</span>
</p>
</td><td class="td6">
<p class="p6">
<span>是</span>
</p>
</td><td class="td7">
<p class="p6">
<span>错误信息</span>
</p>
</td>
</tr>
<tr class="r1">
<td class="td8" colspan="4">
<p class="p6">
<span>请求成功：</span>
</p>
</td>
</tr>
<tr class="r1">
<td class="td1">
<p class="p6">
<span>result</span>
</p>
</td><td class="td5">
<p class="p6">
<span>String</span>
</p>
</td><td class="td6">
<p class="p6">
<span>是</span>
</p>
</td><td class="td7">
<p class="p6">
<span>状态值：默认是0</span>
</p>
</td>
</tr>
<tr class="r1">
<td class="td1">
<p class="p6">
<span>desc</span>
</p>
</td><td class="td5">
<p class="p6">
<span>String</span>
</p>
</td><td class="td6">
<p class="p6">
<span>是</span>
</p>
</td><td class="td7">
<p class="p6">
<span>状态描述：成功</span>
</p>
</td>
</tr>
<tr class="r1">
<td class="td1">
<p class="p6">
<span>sign</span>
</p>
</td><td class="td5">
<p class="p6">
<span>String</span>
</p>
</td><td class="td6">
<p class="p6">
<span>是</span>
</p>
</td><td class="td7">
<p class="p6">
<span>签名</span>
</p>
</td>
</tr>
<tr class="r1">
<td class="td1">
<p class="p6">
<span>report</span>
</p>
</td><td class="td5">
<p class="p6">
<span>List&lt;Map&gt;</span>
</p>
</td><td class="td6">
<p class="p6">
<span>是</span>
</p>
</td><td class="td7">
<p class="p6">
<span>MO数据集：最大返回500 条数据</span>
</p>
</td>
</tr>
<tr class="r1">
<td class="td8" colspan="4">
<p class="p6">
<span>report中的数据：</span>
</p>
</td>
</tr>
<tr class="r1">
<td class="td1">
<p class="p6">
<span>enterprise_no</span>
</p>
</td><td class="td5">
<p class="p6">
<span>String</span>
</p>
</td><td class="td6">
<p class="p6">
<span>是</span>
</p>
</td><td class="td7">
<p class="p6">
<span>SP号码</span>
</p>
</td>
</tr>
<tr class="r1">
<td class="td1">
<p class="p6">
<span>mobile</span>
</p>
</td><td class="td5">
<p class="p6">
<span>String</span>
</p>
</td><td class="td6">
<p class="p6">
<span>是</span>
</p>
</td><td class="td7">
<p class="p6">
<span>上行手机号</span>
</p>
</td>
</tr>
<tr class="r1">
<td class="td1">
<p class="p6">
<span>port</span>
</p>
</td><td class="td5">
<p class="p6">
<span>String</span>
</p>
</td><td class="td6">
<p class="p6">
<span>是</span>
</p>
</td><td class="td7">
<p class="p6">
<span>上行内容</span>
</p>
</td>
</tr>
<tr class="r1">
<td class="td9">
<p class="p6">
<span>content</span>
</p>
</td><td class="td10">
<p class="p6">
<span>String</span>
</p>
</td><td class="td11">
<p class="p6">
<span>是</span>
</p>
</td><td class="td12">
<p class="p6">
<span>通道编号</span>
</p>
</td>
</tr>
</tbody>
</table><br/><br/><br/><br/><br/>
<p class="p1"></p>
</body>
</html>


