<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/common/common.jsp" %>
<%@ include file="/common/layui_head.html" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="multipart/form-data; charset=utf-8"/>
    <script src="/js/jquery-3.4.1.min.js"></script>
    <script src="/js/jquery-form.js"></script>
</head>
<body>
<div class="layui-fluid" id="LAY-component-nav">
    <div class="layui-row layui-col-space15">
        <form class="layui-form">
            <div class="layui-form-item">
                <label class="layui-form-label">消息类型</label>
                <div class="layui-input-block">
                    <input hidden id="rcsMsgTypeId" value="single_card_msg">
                    <input type="radio" name="rcsMsgType" lay-filter="radioFilter" value="single_card_msg" title="单卡片消息" checked>
                    <input type="radio" name="rcsMsgType" lay-filter="radioFilter"  value="many_cards_msg" title="多卡片消息" >
                    <input type="radio" name="rcsMsgType" lay-filter="radioFilter"  value="text_msg" title="纯文本消息" >
                </div>
            </div>
            <fieldset class="layui-elem-field layui-field-title" style="margin-top: 10px;">
                <legend></legend>
            </fieldset>
            <div class="layui-form-item" id="rcsCardAddDiv">
                <label class="layui-form-label">内容：</label>
                <div class="layui-input-inline">
                    <button type="button" class="layui-btn layui-btn-radius layui-btn-disabled" >
                        <i class="layui-icon">&#xe608;</i>
                        <span id="addCard">添加卡片</span>
                    </button>
                </div>
            </div>
            <div class="layui-form-item" id="rcsContentTextDiv" style="display:none;" >
                <label class="layui-form-label">内容：</label>
                <div class="layui-input-block" style="width:80%;">
                    <textarea type="text" maxlength="128" id="cardTextId" autocomplete="off" class="layui-textarea"></textarea>
                </div>
            </div>
        </form>
        <%--轮播图不能放在 form中--%>
        <div class="layui-col-sm8 layui-col-sm-offset1" id="rcsContentCardDiv" style="display:none;">
            <div class="layui-carousel" id="carouselId" lay-filter="carouselFilter">
                <div carousel-item id="carouselItemId">
                </div>
            </div>
        </div>
        <form class="layui-form" action="" onsubmit="return ajaxSubmitForm()">
            <div class="layui-form-item">
                <label class="layui-form-label">悬浮菜单：</label>
                <div class="layui-input-block">
                    <button type="button" class="layui-btn layui-btn-radius layui-btn-disabled" >
                        <i class="layui-icon">&#xe608;</i>
                        <span>添加</span>
                    </button>
                    <button type="button" class="layui-btn layui-btn-radius layui-btn-disabled layui-btn-danger" >
                        <i class="layui-icon">&#xe640;</i>
                        <span>清空</span>
                    </button>
                </div>
            </div>
            <div class="layui-form-item">
                <div class="layui-input-block">
                    <div class="layui-btn-group" id="btnGroup" ></div>
                </div>
            </div>
        </form>
    </div>
</div>
</body>
<div id="dialogId" hidden>
    <form class="layui-form" lay-filter="form" id="dialogFormId" onsubmit="return false;"
          style="padding: 50px 50px 0px 50px;">
        <div class="layui-form-item">
            <div class="layui-input-block">
                <input hidden id="menuTypeId" value="replyMsg" >
                <input type="radio" name="menuType" lay-filter="menuFilter" value="replyMsg" title="消息上行" checked>
                <input type="radio" name="menuType" lay-filter="menuFilter" value="callPhone" title="电话拨打" >
                <input type="radio" name="menuType" lay-filter="menuFilter" value="openUrl" title="链接跳转" >
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">按钮名称:</label>
            <div class="layui-input-block">
                <input type="text" name="buttonName" autocomplete="off" class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">数据:</label>
            <div class="layui-input-block">
                <input type="text" name="buttonData" autocomplete="off" class="layui-input">
            </div>
        </div>
    </form>
</div>
</html>
<script src="/js/common/rcs_common.js"></script>
<script>
    var content = "<c:out value="${param.content}"/>";
    content = decodeURIComponent(content);
    content = JSON.parse(content);
    var msgType = content.rcsMsgType;
    var radios = document.getElementsByName("rcsMsgType");
    $("#rcsMsgTypeId").val(msgType)
    if("text_msg" == msgType){//纯文本
        radios[2].checked = true;
        radios[0].disabled = true;
        radios[1].disabled = true;
        $("#rcsContentTextDiv").attr("style","display:block;");//显示div
        $("#rcsCardAddDiv").attr("style","display:none;");//隐藏div
        $("#rcsContentCardDiv").attr("style","display:none;");//隐藏div
    }else {
        if("single_card_msg" == msgType){
            radios[0].checked = true;
            radios[1].disabled = true;
            radios[2].disabled = true;
        }else{
            radios[1].checked = true;
            radios[0].disabled = true;
            radios[2].disabled = true;
        }
        $("#rcsCardAddDiv").attr("style","display:block;");//显示div
        $("#rcsContentTextDiv").attr("style","display:none;");//隐藏div
        var checkCard = "false";
        $("#carouselItemId").find("span").each(function (i,data) {
            checkCard = "true";
        })
        if(checkCard == "true"){
            $("#rcsContentCardDiv").attr("style","display:block;");//显示div
        }
    }
    var cardIndex = 1;//当前卡片
    var form;
    var carousel;
    var cardCarousel;
    var options = {
        elem: '#carouselId'
        ,width: '100%' //设置容器宽度
        ,arrow: 'always' //箭头
        ,interval: 3000
    }
    layui.use(['form', 'carousel'], function () {
        var carousel = layui.carousel;
        cardCarousel = carousel.render(options);
        form = layui.form;
        form.on('radio(radioFilter)', function(data){
            $("#rcsMsgTypeId").val(data.value);
            if("text_msg" == data.value){//纯文本
                $("#rcsContentTextDiv").attr("style","display:block;");//显示div
                $("#rcsCardAddDiv").attr("style","display:none;");//隐藏div
                $("#rcsContentCardDiv").attr("style","display:none;");//隐藏div
            }else {
                $("#rcsCardAddDiv").attr("style","display:block;");//显示div
                $("#rcsContentTextDiv").attr("style","display:none;");//隐藏div
                var checkCard = "false";
                $("#carouselItemId").find("span").each(function (i,data) {
                    checkCard = "true";
                })
                if(checkCard == "true"){
                    $("#rcsContentCardDiv").attr("style","display:block;");//显示div
                }
            }
        });
        form.on('radio(menuFilter)', function(data){
            $("#menuTypeId").val(data.value);
            $("input[name='buttonName']").val('');
            $("input[name='buttonData']").val('');
        });
        form.render();

        //解析数据展示
        var template_Content = content;
        if(template_Content.menuArray){
            for(var i=0;i<template_Content.menuArray.length;i++){
                var menuType = template_Content.menuArray[i].menuType;
                var buttonName = template_Content.menuArray[i].menuName;
                var buttonData = template_Content.menuArray[i].menuValue;
                creatMenuBt(buttonName,menuType,buttonData);
            }
        }
        if(template_Content.rcsContent instanceof Array){
            for(var i=0;i<template_Content.rcsContent.length;i++){
                var ct = template_Content.rcsContent[i];
                setOneCard(ct.title,ct.description,ct.suggestions,ct.picSrc,ct.materialNo);
            }
        }else if(template_Content.rcsContent.title){
            var ct = template_Content.rcsContent;
            setOneCard(ct.title,ct.description,ct.suggestions,ct.picSrc,ct.materialNo);
        }else{
            $("#cardTextId").val(template_Content.rcsContent);
        }

    });
    function creatMenuBt(buttonName,menuType,buttonData) {
        var bt=document.createElement("button");
        bt.type = "button" ;
        bt.className = "layui-btn layui-btn-sm layui-btn-normal";
        bt.innerText = buttonName;
        bt.addEventListener("click",editMenu);
        var typeAttr = document.createAttribute('menu-type');
        typeAttr.value=menuType;
        bt.setAttributeNode(typeAttr);
        var valueAttr = document.createAttribute('menu-value');
        valueAttr.value=buttonData;
        bt.setAttributeNode(valueAttr);
        $("#btnGroup").append(bt);
    }

    function editMenu() {
        //只展示
        return false;

        var bt = this;
        var innerText = bt.innerText;
        var buttonType = bt.getAttributeNode("menu-type").value;
        var buttonValue = bt.getAttributeNode("menu-value").value;
        var editBt = layer.open({
            area: ['650px', '450px'],
            title:'修改卡片操作项',
            type: 1,
            content: $("#dialogId"),
            btn: ['确定'],
            success:function(layero, index) {
                var radios = document.getElementsByName("menuType");
                radios[0].disabled = true;
                radios[1].disabled = true;
                radios[2].disabled = true;
                if("replyMsg" == buttonType){
                    radios[0].checked = true;
                }else if("callPhone" == buttonType){
                    radios[1].checked = true;
                }else{
                    radios[2].checked = true;
                }
                $("input[name='buttonName']").val(innerText);
                $("#menuTypeId").val(buttonType);
                $("input[name='buttonData']").val(buttonValue);
                form.render();
            },
            yes: function (index, layero) {
                var buttonName = $("input[name='buttonName']").val();
                if(!buttonName){
                    layer.msg('按钮名称不能为空');
                    return false;
                }
                var buttonData = $("input[name='buttonData']").val();
                if(!buttonData){
                    layer.msg('数据不能为空');
                    return false;
                }
                var menuType = $("#menuTypeId").val();
                bt.innerText = buttonName;
                bt.setAttribute("menu-type",menuType);
                bt.setAttribute("menu-value",buttonData);
                form.render();
                layer.close(editBt);
            }
        })
    }

    //编辑卡片
    function editCard(cardJson) {
    }

    function setOneCard(title,description,buttonArray,picSrc,materialNo){
        $.ajax({
            url: "/admin/business_rcsMaterialList",
            type: 'POST',
            async: true,
            data: {
                "material_No":materialNo,
                "pagination.pageIndex":0,
                "pagination.pageSize":1
            },
            dataType:'json',
            success: function (res) {
                if (res.code == '0') {
                    picSrc = res.data[0].url;
                    if(!picSrc.IsPicture()){
                        picSrc = "/public/admin/images/videoPlayButton.png"
                    }
                    var card = assembleCard(title,description,buttonArray,picSrc,materialNo,cardIndex);
                    cardIndex++;
                    $("#rcsContentCardDiv").attr("style","display:block;");//显示div
                    $("#carouselItemId").append(card);
                    cardCarousel.reload(options);
                    layer.closeAll();
                }
            }
        });
        return false;
    }

    //组装卡片数据
    function assembleCard(title,description,buttonArray,picSrc,materialNo,currentCardIndex) {
        var cardJson = {};
        cardJson.title = title;
        cardJson.description = description;
        cardJson.materialNo = materialNo;
        cardJson.suggestions = buttonArray;
        cardJson.cardIndex = currentCardIndex;
        var card = "<span id=\"span_"+currentCardIndex+"\" onclick=\"editCard('"+encodeURI(JSON.stringify(cardJson)) +"')\">\n" +
            "<input hidden name='oneCard' value='"+JSON.stringify(cardJson)+"'>"+
            "         <div class=\"layui-card-body divclass\" align=\"center\">\n" +
            "              <div>\n" +
            "                  <img class=\"layui-upload-img\" width=\"100px\" height=\"100px\"\n" +
            "                       src=\""+picSrc+"\">\n" +
            "              </div>\n" +
            "              <div>"+title+"</div>\n" +
            "              <div>"+description+"</div>\n" +
            "              <div class=\"layui-btn-group\">\n";
        if(buttonArray && buttonArray.length >0){
            for (var i = 0; i < buttonArray.length; i++) {
                var map = buttonArray[i];
                var button = '<button type="button"' +
                    ' button-type="'+map['menuType']
                    +'" button-value="'+ map['menuValue']
                    +'" class="layui-btn layui-btn-sm layui-btn-normal">'
                    +map['menuName']+
                    '</button>';
                card += button;
            }
        }
        card += "</div></div></span>";
        return card;
    }

    //提交
    function ajaxSubmitForm() {
        return false;
    }
</script>