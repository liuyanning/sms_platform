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
                <label class="layui-form-label">模板名称<font color="red">&nbsp;&nbsp;*</font></label>
                <div class="layui-input-inline" style="width:80%;">
                    <input type="text" maxlength="128"  id="template_Name" placeholder="请输入" autocomplete="off" class="layui-input"  lay-verify="required">
                </div>
            </div>
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
                    <button type="button" onclick="addOneCard()" class="layui-btn layui-btn-radius" >
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
                    <button type="button" onclick="addOneMenu()" class="layui-btn layui-btn-radius" >
                        <i class="layui-icon">&#xe608;</i>
                        <span>添加</span>
                    </button>
                    <button type="button" onclick="deleteAllMenu()" class="layui-btn layui-btn-radius layui-btn-danger" >
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
            <div class="layui-form-item">
                <label class="layui-form-label">描述</label>
                <div class="layui-input-block" style="width:80%;">
                    <input type="text" maxlength="128" id="description"  autocomplete="off" class="layui-input" >
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">备注</label>
                <div class="layui-input-block" style="width:80%;">
                    <textarea type="text" maxlength="128" id="remark" autocomplete="off" class="layui-textarea"></textarea>
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label"></label>
                <div class="layui-input-block">
                    <button class="layui-btn" >提交</button>
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
    var form;
    var cardIndex = 1;//当前卡片
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
    });
    //提交
    function ajaxSubmitForm() {
        var template_Name = $("#template_Name").val();//模板名称
        if(!template_Name){
            layer.msg("请输入模板名称！");
            return false;
        }
        //卡片||文本
        var rcsMsgType = $("#rcsMsgTypeId").val();
        var rcsContent;
        var rcsCardArray = new Array();
        if(rcsMsgType != 'text_msg' && $("input[name='oneCard']").length == 0){
            layer.msg("卡片消息内容不能为空！");
            return false;
        };
        if(rcsMsgType == 'text_msg' && isEmpty($("#cardTextId").val())){
            layer.msg("文本消息内容不能为空！");
            return false;
        };
        if(rcsMsgType == 'single_card_msg'){
            rcsContent = JSON.parse($("input[name='oneCard']:first").val());
        }else if(rcsMsgType == 'many_cards_msg'){
            $("input[name='oneCard']").each(function (i,data) {
                rcsCardArray.push(JSON.parse(data.value));
            });
            rcsContent =rcsCardArray;
        }else {
            rcsContent = $("#cardTextId").val();
        }
        if (!rcsContent) {
            layer.msg("消息内容不能为空！");
            return false;
        }
        //悬浮菜单
        var menuArray = new Array();
        $("#btnGroup").find("button").each(function (i,data) {
            var menu = {};
            for (var i in data.attributes){
                var att = data.attributes[i];
                if (att.specified && att.name == 'menu-type') {
                    menu['menuType'] = att.value;
                }
                if (att.specified && att.name == 'menu-value') {
                    menu['menuValue'] = att.value;
                }
            }
            menu['menuName'] = data.innerText;
            menuArray.push(menu);
        })
        var result = {};
        result.rcsMsgType = rcsMsgType;
        result.rcsContent = rcsContent;
        result.menuArray = menuArray;
        $('.layui-btn').attr('disabled', true);
        var description = $("#description").val();//描述
        var remark = $("#remark").val();//备注
        var loading = layer.load(''); //遮罩层
        $.ajax({
            type: 'post', // 提交方式 get/post
            url: '/admin/business_addRcsTemplate', // 需要提交的 url
            dataType: 'json',
            data: {
                "template_Name":template_Name,
                "description":description,
                "remark":remark,
                "message_Type":rcsMsgType,
                "template_Content":JSON.stringify(result)
            },
            success: function (d) {
                $('.layui-btn').attr('disabled', false);
                layer.close(loading);
                if (d.code != 0) {
                    layer.alert(d.msg, {icon: 2}, function () {
                        layer.closeAll();
                    });
                    return;
                }
                layer.alert(d.msg, {icon: 1}, function (index) {
                    parent.layer.closeAll();
                    parent.layui.table.reload('list_table');
                });
            },
            error: function (d) {
                $('.layui-btn').attr('disabled', false);
                layer.close(loading);
                layer.msg('提交失败', {icon: 2, time: 2000}, function () {
                    layer.closeAll();
                });
            }
        })
        return false; // 必须返回false，否则表单会自己再做一次提交操作，并且页面跳转
    }
</script>