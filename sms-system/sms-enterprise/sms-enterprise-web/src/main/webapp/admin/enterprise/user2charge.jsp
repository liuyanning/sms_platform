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
<form class="layui-form" action="/admin/enterprise_user2charge" id="subForm" onsubmit="return false"
      enctype="multipart/form-data" method="post" style="padding: 50px 50px 35px 35px;">
    <input hidden name="enterprise_User_Id" value="<c:out value="${enterpriseUser.id}"/>"/>
    <input hidden name="enterprise_Name" value="<c:out value="${ENTERPRISE_INFO.name}"/>"/>
    <div class="layui-form-item">
        <label class="layui-form-label" style="width: 25%">当前账户余额</label>
        <div class="layui-input-inline">
            <input type="text" value="<c:out value="${masterMoney}"/>" readonly autocomplete="off" class="layui-input">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label" style="width: 25%">当前账户名称</label>
        <div class="layui-input-inline">
            <input type="text" value="<c:out value="${enterpriseUser.real_Name}"/>" readonly="true" autocomplete="off" class="layui-input" >
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label" style="width: 25%">划拨账户余额</label>
        <div class="layui-input-inline">
            <input type="text" value="<c:out value="${enterpriseUser.available_Amount}"/>" readonly autocomplete="off" class="layui-input">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label" style="width: 25%">划拨金额</label>
        <div class="layui-input-inline">
            <input type="text" name="money" onkeypress="regInput(/^\d{0,12}(\.\d{0,2})?$/)"
                   onblur="letterValue('money_Letter','money')" alt="(单位:元)"
                   placeholder="请输入" autocomplete="off" class="layui-input">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label" style="width: 25%">金额大写</label>
        <div class="layui-input-inline">
            <input type="text" name="money_Letter" readonly id="money_Letter" placeholder="金额大写" autocomplete="off" class="layui-input" >
        </div>
    </div>
    <div class="layui-form-item layui-hide">
        <div class="layui-input-block">
            <input class="layui-btn" type="submit" lay-submit lay-filter="submit" id="layuiadmin-app-form-submit"
                   value="立即提交">
        </div>
    </div>

</form>
<%@ include file="/common/layui_bottom.jsp"%>

<script >
    var form;
    layui.use(['form'], function () {
            form = layui.form;
            form.render();
    });

    /*业务逻辑*/
    function regInput(reg)
    {
        var srcElem = event.srcElement
        var oSel = document.selection.createRange()
        oSel = oSel.duplicate()

        oSel.text = ""
        var srcRange = srcElem.createTextRange()
        oSel.setEndPoint("StartToStart", srcRange)
        var num = oSel.text + String.fromCharCode(event.keyCode) + srcRange.text.substr(oSel.text.length)
        event.returnvalue = reg.test(num)
    }
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
        // <button class=\"layui-btn layui-btn-xs\" lay-event='{\"type\":\"dialogTodo\",\"url\":\"/admin/account/google_qrcode_img.jsp?u="+d.user_Name+"&s="+d.google_ID_Key+"\",\"width\":\"320\",\"height\":\"320\",\"title\":\""+d.user_Name+"\"}'  title=\"谷歌二维码\" >谷歌二维码</button>";
        $("#subForm").ajaxSubmit({
            type: 'post', // 提交方式 get/post
            url: '/admin/enterprise_user2charge', // 需要提交的 url
            dataType: 'json',
            data: data,
            success: function (d) { // data 保存提交后返回的数据，一般为 json 数据
                if(d.code == 0){
                    layer.msg(d.msg, {icon: 1, time: 2000});
                }else{
                    layer.alert(d.msg, {icon: 2, time: 2000});
                }
            },
            error: function (d) {
                layer.msg('提交失败', {icon: 2, time: 2000});
            }
        })
        return false; // 必须返回false，否则表单会自己再做一次提交操作，并且页面跳转
    }
</script>
</body>