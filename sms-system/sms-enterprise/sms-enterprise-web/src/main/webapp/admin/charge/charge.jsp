<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/common.jsp" %>
<%@ include file="/common/layui_head.html"%>
<head>
    <meta http-equiv="Content-Type" content="multipart/form-data; charset=utf-8"/>
    <script src="/js/jquery-3.4.1.min.js"></script>
    <script src='<ht:heropageconfigurationtext code="enterprise_webpage_css" defaultValue="/layuiadmin"/>/layui/layui.all.js'></script>
    <script src="/js/jquery-form.js"></script>
</head>
<body>
<form class="layui-form" action="/admin/charge_preChargeQRCode" id="subForm" onsubmit="return ajaxSubmitForm()"
      enctype="multipart/form-data" method="post" style="padding: 50px 50px 350px 350px;">
    <input hidden name="enterprise_User_Id" value="<c:out value="${ADMIN_USER.id}"/>"/>
    <div class="layui-form-item">
        <label class="layui-form-label">用户名称</label>
        <div class="layui-input-inline">
            <input type="text" value="<c:out value="${ADMIN_USER.real_Name}"/>" readonly="true" autocomplete="off" class="layui-input" >
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">用户余额</label>
        <div class="layui-input-inline">
            <input type="text" value="<c:out value="${ADMIN_USER.available_Amount}"/>" readonly autocomplete="off" class="layui-input">
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label">充值金额</label>
        <div class="layui-input-inline">
            <input type="text" name="money" id="money"
                   onblur="letterValue('money_Letter','money')" alt="(单位:元)"
                   placeholder="请输入" autocomplete="off" class="layui-input">
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label">金额大写</label>
        <div class="layui-input-inline">
            <input type="text" name="money_Letter" readonly id="money_Letter" placeholder="金额大写" autocomplete="off" class="layui-input" >
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">支付方式</label>
        <div class="layui-input-inline">
            <select name="pay_Type_Code">
                <option value="NATIVE">微信</option>
                <option value="ZFB_F2F_SWEEP_PAY">支付宝</option>
            </select>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label"></label>
        <button class="layui-btn" id="layuiadmin-app-form-submit">点击充值</button>
    </div>
    </form>
<%@ include file="/common/layui_bottom.jsp"%>

<script >
    var form;
    var $;
    layui.use(['form'], function () {
            form = layui.form;
            $ = layui.$;
            form.render();
    });
    function chineseNumber(num)
    {
        if (isNaN(num) || num > Math.pow(10, 12)) return "";
        var isMinus = '';
        if(num.substring(0,1) == '-'){//如果是负数，截取掉“-”号，最后拼上
            isMinus = num.substring(0,1);
            num = num.substring(1,num.length);
        }
        var cn = "零壹贰叁肆伍陆柒捌玖"
        var unit = new Array("拾百千", "分角")
        var unit1= new Array("万亿", "")
        var numArray = num.toString().split(".")
        var start = new Array(numArray[0].length-1, 2)
        function toChinese(num, index)
        {
            var num = num.replace(/\d/g, function ($1)
            {
                return cn.charAt($1)+unit[index].charAt(start--%4 ? start%4 : -1)
            })
            return num
        }
        for (var i=0; i<numArray.length; i++)
        {
            var tmp = ""
            for (var j=0; j*4<numArray[i].length; j++)
            {
                var strIndex = numArray[i].length-(j+1)*4
                var str = numArray[i].substring(strIndex, strIndex+4)
                var start = i ? 2 : str.length-1
                var tmp1 = toChinese(str, i)
                tmp1 = tmp1.replace(/(零.)+/g, "零").replace(/零+$/, "")
                tmp1 = tmp1.replace(/^壹拾/, "拾")
                tmp = (tmp1+unit1[i].charAt(j-1)) + tmp
            }
            numArray[i] = tmp
        }
        numArray[1] = numArray[1] ? numArray[1] : ""
        numArray[0] = numArray[0] ? numArray[0]+"元" : numArray[0], numArray[1] = numArray[1].replace(/^零+/, "")
        numArray[1] = numArray[1].match(/分/) ? numArray[1] : numArray[1]+"整"
        return isMinus+numArray[0]+numArray[1]
    }
    function aNumber(num)
    {
        var numArray = new Array()
        var unit = "亿万元$"
        for (var i=0; i<unit.length; i++)
        {
            var re = eval_r("/"+ (numArray[i-1] ? unit.charAt(i-1) : "") +"(.*)"+unit.charAt(i)+"/")
            if (num.match(re))
            {
                numArray[i] = num.match(re)[1].replace(/^拾/, "壹拾")
                numArray[i] = numArray[i].replace(/[零壹贰叁肆伍陆柒捌玖]/g, function ($1)
                {
                    return "零壹贰叁肆伍陆柒捌玖".indexOf($1)
                })
                numArray[i] = numArray[i].replace(/[分角拾百千]/g, function ($1)
                {
                    return "*"+Math.pow(10, "分角 拾百千 ".indexOf($1)-2)+"+"
                }).replace(/^\*|\+$/g, "").replace(/整/, "0")
                numArray[i] = "(" + numArray[i] + ")*"+Math.ceil(Math.pow(10, (2-i)*4))
            }
            else numArray[i] = 0
        }
        return eval_r(numArray.join("+"))
    }

    function letterValue(letterName,moneyName)
    {
        $("input[name='"+letterName+"']").attr("value",chineseNumber($("input[name='"+moneyName+"']").val()));
    }

    function smsTotalValue(SMS_TotalName,moneyName,unit_PriceName)
    {
        var money=$("input[name='"+moneyName+"']").val();
        if(money=='')
        {
            money=0;
        }
        var unitPrice=$("input[name='"+unit_PriceName+"']").val();
        if(unitPrice==''||unitPrice==0)
        {
            $("input[name='"+SMS_TotalName+"']").attr("value",0);
            return;
        }
        $("input[name='"+SMS_TotalName+"']").attr("value",parseInt(money*100/unitPrice));
    }

    function ajaxSubmitForm() {
        var reg = /(^[1-9]([0-9]+)?(\.[0-9]{1,2})?$)|(^(0){1}$)|(^[0-9]\.[0-9]([0-9])?$)/;
        var data = $("#subForm").val();
        var money = $("#money").val();
        if (!money){
            layer.msg('请选择充值金额！')
            return false;
        }
        if(!reg.test(money)){
            layer.msg('充值金额错误！')
            return false;
        }
        var pay_Type_Code = $("select[name='pay_Type_Code']").val();
        if (!pay_Type_Code){
            layer.msg('支付方式不能为空')
            return false;
        }
        var loading= layer.load('', {time: 10*1000}); //遮罩层 最长10秒后自动关闭
        $("#subForm").ajaxSubmit({
            type: 'post', // 提交方式 get/post
            url: '/admin/charge_preChargeQRCode', // 需要提交的 url
            dataType: 'json',
            data: data,
            success: function (d) { // data 保存提交后返回的数据，一般为 json 数据
                layer.close(loading);
                console.log(d)
                if(d.code == 0){
                    if(d.data.status=='00'){
                        layer.open({
                            type: 2,
                            title: '扫码充值',
                            content: d.data.qrcodeUrl,
                            maxmin: true,
                            area: ["300px","350px"],
                        });
                        queryChargeResult(d.data.orderNo);
                    }else{
                        layer.alert(d.data.message, {icon: 2});
                    }
                }else{
                    layer.alert(d.msg, {icon: 2});
                }
                return false;
            },
            error: function (d) {
                console.log(d)
                layer.close(loading);
                layer.msg('提交失败', {icon: 2});
            }
        })
        return false; // 必须返回false，否则表单会自己再做一次提交操作，并且页面跳转
    }
    
    function queryChargeResult(orderNo) {
        if(!orderNo){
            return false;
        }
        var i = 0;
        var id = setInterval(function(){
            i++;
            if(i > 18){
                clearInterval(id);
            }
            $.ajax({
                url: '/admin/charge_queryChargeResult',
                type: 'POST',
                data: {
                    'order_No': orderNo
                },
                dataType:'json',
                success: function (res) {
                    if(res.code == '0'){
                        layer.closeAll();//关闭二维码
                        layer.alert('充值成功');
                        clearInterval(id);
                    }
                }
            });
        }, 5*1000);
    }
</script>
</body>