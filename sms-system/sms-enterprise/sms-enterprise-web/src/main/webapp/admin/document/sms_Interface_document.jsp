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
.p1{text-align:end;hyphenate:auto;font-family:宋体;font-size:42pt;}
.p2{text-indent:-0.29166666in;margin-left:0.29166666in;text-align:center;hyphenate:auto;font-family:宋体;font-size:26pt;}
.p3{text-align:center;hyphenate:auto;font-family:宋体;font-size:26pt;}
.p4{text-indent:0.29166666in;margin-left:1.75in;text-align:justify;hyphenate:auto;font-family:Times New Roman;font-size:26pt;}
.p5{text-align:center;hyphenate:auto;font-family:Times New Roman;font-size:22pt;}
.p6{text-align:justify;hyphenate:auto;font-family:Times New Roman;font-size:24pt;}
.p7{text-align:start;hyphenate:auto;font-family:宋体;font-size:10pt;}
.p8{text-align:center;hyphenate:auto;font-family:华文楷体;font-size:22pt;}
.p9{text-align:justify;hyphenate:auto;font-family:宋体;font-size:14pt;}
.p10{text-align:justify;hyphenate:auto;font-family:Times New Roman;font-size:10pt;}
.p11{text-align:justify;hyphenate:auto;font-family:Cambria;font-size:22pt;}
.p12{text-align:justify;hyphenate:auto;font-family:宋体;font-size:16pt;}
.p13{text-align:justify;hyphenate:auto;font-family:宋体;font-size:11pt;}
.p14{text-align:justify;hyphenate:auto;font-family:宋体;font-size:10pt;}
.p15{text-align:start;hyphenate:auto;font-family:Consolas;font-size:14pt;}
.p16{text-align:justify;hyphenate:auto;font-family:Consolas;font-size:12pt;}
.s1{font-weight:bold;}
.s2{font-weight:bold;color:black;}
.s3{font-family:宋体;font-weight:bold;color:black;}
.s4{color:black;}
.s5{font-family:Calibri;color:black;}
.s6{font-style:italic;color:black;}
.s7{color:#6a3e3e;}
.td1{width:0.97430557in;padding-start:0.0in;padding-end:0.0in;border-bottom:thin solid black;border-left:thin solid black;border-right:thin solid black;border-top:thin solid black;}
.td2{width:3.6291666in;padding-start:0.0in;padding-end:0.0in;border-bottom:thin solid black;border-left:thin solid black;border-right:thin solid black;border-top:thin solid black;}
.r1{height:0.27986112in;}
.r2{height:0.24375in;keep-together:always;}
.r3{height:0.54791665in;keep-together:always;}
.r4{height:0.2875in;keep-together:always;}
.t1{table-layout:fixed;border-collapse:collapse;border-spacing:0;}
</style>
<title>电信级短信平台</title>
</head>
<body class="b1 b2">

<p class="p11">
<span class="s1">电信级短信平台</span>
</p>
<p class="p11">
<span class="s1">MC-SMS</span>
</p>
<p class="p11">
<span class="s1">接口说明书</span>
</p>
<p class="p5"></p>
<p class="p6"></p>
<p class="p7"></p>
<p class="p7"></p>
<p class="p7"></p>
<p class="p7"></p>
<p class="p8"></p>
<p class="p8"></p>
<table class="t1">
<tbody>
<tr class="r1">
<td class="td1">
<p class="p9">
<span class="s2">产品名称</span>
</p>
</td><td class="td2">
<p class="p9">
<span class="s2">电信级短信平台</span>
</p>
</td>
</tr>
<tr class="r2">
<td class="td1">
<p class="p9">
<span class="s2">版本编号</span>
</p>
</td><td class="td2">
<p class="p9">
<span class="s2">Build 2.0.0</span>
</p>
</td>
</tr>
<tr class="r3">
<td class="td1">
<p class="p9">
<span class="s2">发布者</span>
</p>
</td><td class="td2">
<p class="p9">
<span class="s2">drondea</span>
</p>
</td>
</tr>
<tr class="r4">
<td class="td1">
<p class="p9">
<span class="s2">发布日期</span>
</p>
</td><td class="td2">
<p class="p9">
<span class="s2">2019-09-20</span>
</p>
</td>
</tr>
</tbody>
</table>
<p class="p10"></p>
<br/>
<p class="p10"></p>
<p class="p11">
<span class="s2">API </span><span class="s3">说明</span>
</p>
<p class="p10"></p>
<p class="p12">
<span class="s2">1、 开发环境说明</span>
</p>
<p class="p10"></p>
<p class="p13">
<span class="s4">用户以</span><span class="s5"> POST </span><span class="s4">方式提交数据平台统一使用</span><span class="s5"> UTF-8 </span><span class="s4">编码方式</span>
</p>
<p class="p10"></p>
<p class="p14">
<span class="s4">参数名称和参数说明中规定的固定值必须与列表中完全一致（大小写敏感）</span>
</p>
<p class="p10"></p>
<p class="p12">
<span class="s2">2、 签名算法</span>
</p>
<p class="p10"></p>
<p class="p13">
<span class="s4">签名方式：</span><span class="s5">MD5</span>
</p>
<p class="p10"></p>
<p class="p13">
<span class="s4">签名返回：</span><span class="s5">32 </span><span class="s4">位大写</span>
</p>
<p class="p10"></p>
<p class="p13">
<span class="s4">按照以下示例参数顺序 加上密钥进行</span><span class="s5"> MD5 </span><span class="s4">加密</span>
</p>
<p class="p13"></p>
<p class="p13">
<span class="s4">示例：</span>
</p>
<p class="p10"></p>
<p class="p15">
<span class="s6">MD5</span><span class="s4">(企业编号（</span><span class="s7">e</span><span class="s4">nterprise_no） + 账号（account） + 时间戳（timestamp）+ 密钥（http_Sign_Key）);</span>
</p>
<p class="p16"></p>
<p class="p10"></p>
<br/><br/><br/>
</body>
</html>