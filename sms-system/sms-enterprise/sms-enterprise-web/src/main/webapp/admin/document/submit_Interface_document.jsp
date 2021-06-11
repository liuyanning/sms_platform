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
.p1{text-align:justify;hyphenate:auto;font-family:Consolas;font-size:12pt;}
.p2{text-align:justify;hyphenate:auto;font-family:宋体;font-size:16pt;}
.p3{text-align:justify;hyphenate:auto;font-family:Times New Roman;font-size:10pt;}
.p4{text-align:justify;hyphenate:auto;font-family:Times New Roman;font-size:12pt;}
.p5{text-align:start;hyphenate:auto;font-family:宋体;font-size:16pt;}
.p6{text-align:start;hyphenate:auto;font-family:Times New Roman;font-size:12pt;}
.p7{text-align:start;hyphenate:auto;font-family:Courier New;font-size:10pt;}
.s1{font-weight:bold;color:black;}
.s2{font-family:Times New Roman;}
.td1{width:1.5965278in;padding-start:0.0in;padding-end:0.0in;border-bottom:1.0pt solid white;border-top:1.0pt solid white;}
.td2{width:1.1451389in;padding-start:0.0in;padding-end:0.0in;border-bottom:1.0pt solid white;border-top:1.0pt solid white;}
.td3{width:1.2381945in;padding-start:0.0in;padding-end:0.0in;border-bottom:1.0pt solid white;border-top:1.0pt solid white;}
.td4{width:1.9347222in;padding-start:0.0in;padding-end:0.0in;border-bottom:1.0pt solid white;border-top:1.0pt solid white;}
.td5{width:1.2826389in;padding-start:0.0in;padding-end:0.0in;border-bottom:1.0pt solid white;border-top:1.0pt solid white;}
.td6{width:1.1486111in;padding-start:0.0in;padding-end:0.0in;border-bottom:1.0pt solid white;border-top:1.0pt solid white;}
.td7{width:1.9583334in;padding-start:0.0in;padding-end:0.0in;border-bottom:1.0pt solid white;border-top:1.0pt solid white;}
.td8{width:5.986111in;padding-start:0.0in;padding-end:0.0in;border-bottom:1.0pt solid white;border-top:1.0pt solid white;}
.r1{keep-together:always;}
.r2{height:0.39375in;keep-together:always;}
.t1{table-layout:fixed;border-collapse:collapse;border-spacing:0;}
</style>
<title>电信级短信平台</title>
</head>
<body class="b1 b2">

<p class="p2">
<span class="s1">短信提交请求报文说明</span>
</p>
<p class="p3"></p>
<p class="p4">
<span>请求地址：http://**********/APIControllerV2/v2/submit</span>
</p>
<br/>
<p class="p4">
<span>请求报文：</span>
</p>
<p class="p1"></p>
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
<tr class="r2">
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
<tr class="r2">
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
<tr class="r2">
<td class="td1">
<p class="p6">
<span>password</span>
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
<span>密码</span>
</p>
</td>
</tr>
<tr class="r2">
<td class="td1">
<p class="p6">
<span>phones</span>
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
<span>接收手机号码，多个手机号码用英文逗号分隔，最多500</span>
</p>
</td>
</tr>
<tr class="r2">
<td class="td1">
<p class="p6">
<span>content</span>
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
<span>短信内容，最多500个汉字</span>
</p>
</td>
</tr>
<tr class="r2">
<td class="td1">
<p class="p6">
<span>subcode</span>
</p>
</td><td class="td2">
<p class="p6">
<span>String</span>
</p>
</td><td class="td3">
<p class="p6">
<span>否</span>
</p>
</td><td class="td4">
<p class="p6">
<span>短号：扩展子号码，内容可空（验证格式和长度，不能超过20位）</span>
</p>
</td>
</tr>
<tr class="r2">
<td class="td1">
<p class="p6">
<span>sendtime</span>
</p>
</td><td class="td2">
<p class="p6">
<span>String</span>
</p>
</td><td class="td3">
<p class="p6">
<span>否</span>
</p>
</td><td class="td4">
<p class="p7">
<span>发送时间,格式yyyyMMddHHmm,可空</span><span class="s2">(12位数字，年月日时分，比如201111180929),不填时立即发送</span>
</p>
</td>
</tr>
<tr class="r2">
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
<tr class="r2">
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
<p class="p3"></p>
<p class="p3"></p>
<p class="p3"></p>
<br/>
<p class="p4">
<span>响应报文：</span>
</p>
<table class="t1">
<tbody>
<tr class="r2">
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
<tr class="r2">
<td class="td8" colspan="4">
<p class="p6">
<span>请求失败：</span>
</p>
</td>
</tr>
<tr class="r2">
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
<tr class="r2">
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
<tr class="r2">
<td class="td8" colspan="4">
<p class="p6">
<span>请求成功：</span>
</p>
</td>
</tr>
<tr class="r2">
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
<tr class="r2">
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
<tr class="r2">
<td class="td1">
<p class="p6">
<span>msgid</span>
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
<span>返回的msgid(推送状态报告时会用到，请注意保存好)</span>
</p>
</td>
</tr>
</tbody>
</table><br/><br/><br/><br/><br/>
<p class="p3"></p>
<p class="p3"></p>
</body>
</html>
