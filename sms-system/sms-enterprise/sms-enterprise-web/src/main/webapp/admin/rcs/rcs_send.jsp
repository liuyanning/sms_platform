<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/common/common.jsp" %>
<%@ include file="/common/layui_head.html" %>
<html>
<head>
	<meta http-equiv="Content-Type" content="multipart/form-data; charset=utf-8"/>
	<script src="/js/jquery-3.4.1.min.js"></script>
	<script src="/js/jquery-form.js"></script>
	<link rel="stylesheet" type="text/css" href="/css/send_sms.css" id="send_sms_link"
		  international_telephone_code=<ht:heropageconfigurationtext code="international_telephone_code" sortCode="custom_switch" defaultValue="off"/>>
	<script src="/js/tagsinput.js" type="text/javascript" charset="utf-8"></script>
</head>
<body>
<div class="layui-fluid" id="LAY-component-nav">
	<div class="layui-row layui-col-space15">
		<div class="layui-col-md9">
			<div class="layui-card"  style="overflow:auto;">
				<div class="layui-card-header"></div>
				<div class="layui-card-body">
					<div class="layui-fluid" layoutH="57">
						<div class="layui-form-item">
							<label class="layui-form-label">手机号码:</label>
							<div class="layui-input-inline box" style="width: 81%;">
								<div class="tagsinput-primary form-group">
									<input name="tagsinput" id="tagsinputval" class="tagsinput layui-input"
										   data-role="tagsinput" placeholder="多个号码换行或者逗号分割,最多1W个号码">
								</div>
							</div>
						</div>
						<form class="layui-form">
							<div class="layui-form-item" style="display:none;">
								<label class="layui-form-label">手机号码:</label>
								<div class="layui-input-inline" style="width: 80%;">
                                    <textarea rows="6" placeholder="(多个号码换行或者逗号分割,最多1W个号码)" class="layui-textarea"
											  name="phone_Nos" id="phone_Nos"></textarea>
								</div>
							</div>
							<div id="global_roaming" class="layui-form-item">
								<label class="layui-form-label">国际区号:</label>
								<div class="layui-input-inline">
									<input type="text" name="country_Code" id="country_Code" autocomplete="off"
										   value="86" class="layui-input">
								</div>
								<div class="layui-form-mid layui-word-aux">最长5位字符,您所提交的号码无区号时自动拼接</div>
							</div>
                            <div class="layui-row layui-form-item">
                                <div class="layui-col-md7">
                                    <label class="layui-form-label">消息类型</label>
                                    <div class="layui-input-block layui-form" lay-filter="rcsMsgType">
                                        <input hidden id="rcsMsgTypeId" value="single_card_msg">
                                        <input type="radio" name="rcsMsgType" lay-filter="radioFilter" value="single_card_msg" title="单卡片消息" checked>
                                        <input type="radio" name="rcsMsgType" lay-filter="radioFilter"  value="many_cards_msg" title="多卡片消息" >
                                        <input type="radio" name="rcsMsgType" lay-filter="radioFilter"  value="text_msg" title="纯文本消息" >
                                    </div>
                                </div>
                                <div class="layui-col-md5">
                                    <label class="layui-form-label">短信模板</label>
                                    <div class="layui-input-inline">
                                        <ht:herorcstemplatetag id="selectRcsTemplateId"  enterpriseNo="${ADMIN_USER.enterprise_No}"
                                                               enterpriseUserId="${ADMIN_USER.id}"/>
                                    </div>
                                </div>
                            </div>

                            <fieldset class="layui-elem-field layui-field-title">
                                <legend></legend>
                            </fieldset>
                            <div class="layui-form-item" id="rcsCardAddDiv">
                                <label class="layui-form-label">内容：</label>
                                <div class="layui-input-inline">
                                    <button type="button" class="layui-btn layui-btn-radius" >
                                        <i class="layui-icon">&#xe608;</i>
                                        <span id="addCard" onclick="addOneCard()">添加卡片</span>
                                    </button>
                                </div>
                            </div>
                            <div class="layui-form-item" id="rcsContentTextDiv" style="display:none;" >
                                <label class="layui-form-label">内容：</label>
                                <div class="layui-input-block" >
                                    <input type="text" id="cardTextId" autocomplete="off" class="layui-input" >
                                </div>
                            </div>
                        </form>
                        <%--轮播图不能放在 form中--%>
                        <div class="layui-card layui-col-md8 layui-col-md-offset1" id="rcsContentCardDiv" style="display:none;">
                            <div class="layui-carousel " id="carouselId" lay-filter="carouselFilter">
                                <div carousel-item id="carouselItemId">
                                </div>
                            </div>
                        </div>
                        <form class="layui-form" action="" onsubmit="return ajaxSubmitForm()">
                            <div class="layui-form-item">
                                <label class="layui-form-label">悬浮菜单：</label>
                                <div class="layui-input-block">
                                    <button type="button" class="layui-btn layui-btn-radius" >
                                        <i class="layui-icon">&#xe608;</i>
                                        <span onclick="addOneMenu()">添加</span>
                                    </button>
                                    <button type="button"class="layui-btn layui-btn-radius layui-btn-danger" >
                                        <i class="layui-icon">&#xe640;</i>
                                        <span onclick="deleteAllMenu()">清空</span>
                                    </button>
                                </div>
                            </div>
                            <div class="layui-form-item">
                                <div class="layui-input-block">
                                    <div class="layui-btn-group" id="btnGroup" ></div>
                                </div>
                            </div>
                            <div class="layui-form-item">
                                <label class="layui-form-label"></label>
                                <div class="layui-input-block">
                                    <button class="layui-btn">提交</button>
                                </div>
                            </div>
						</form>
					</div>
				</div>
			</div>
		</div>

		<div class="layui-col-md3">
			<div class="layui-card" style="overflow:auto;">
				<div class="layui-card-header">联系人</div>
				<div class="layui-card-body">
					<div>
						<button type="button" class="layui-btn layui-btn-radius" id="addPhoneNo">&nbsp;确&nbsp;&nbsp;定&nbsp;</button>
						<button type="button" class="layui-btn layui-btn-radius" id="cleanAll">&nbsp;清&nbsp;&nbsp;空&nbsp;</button>
					</div>
					<div id="treeDemo_m"></div>
				</div>
			</div>
		</div>
	</div>
</div>

</body>
<div class="layui-form-item" id="send_Time_item" style="display: none">
	<div class="layui-row" style="height: 20px"></div>
	<label class="layui-form-label">定时发送:</label>
	<div class="layui-input-inline">
		<input type="text" name="send_Time" id="send_Time_input" lay-verify="datetime"
			   placeholder="yyyy-MM-dd HH:mm:ss" autocomplete="off" class="layui-input">
	</div>
</div>


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
            $("#selectRcsTemplateId").val('');
            form.render('select');
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
    layui.use(['form'], function () {
        var form = layui.form;
        $("select[id='selectRcsTemplateId']").attr('lay-filter','onselect');
        //监听选中事件
        form.on('select(onselect)', function (data) {
            var spans =  $("#carouselItemId span");
            for (var a=0 ;a<spans.length; a++){
                spans[a].remove();
            }
            cardCarousel.reload(options);
            $("#btnGroup").empty();
            var selectContent = $("#selectRcsTemplateId").val();
            if(selectContent=='') return;
            form.render('select');//渲染
            var template_Content = JSON.parse(decodeURIComponent(selectContent));
            var radios = document.getElementsByName("rcsMsgType");
            var message_Type = template_Content.rcsMsgType;
            $("#rcsMsgTypeId").val(message_Type);
            if("text_msg" == message_Type){//纯文本
                radios[2].checked = true;
                $("#rcsContentTextDiv").attr("style","display:block;");//显示div
                $("#rcsCardAddDiv").attr("style","display:none;");//隐藏div
                $("#rcsContentCardDiv").attr("style","display:none;");//隐藏div
            }else {
                if("single_card_msg" == message_Type){
                    radios[0].checked = true;
                }else{
                    radios[1].checked = true;
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
            };
            form.render();
            if(template_Content.menuArray){
                for(var i=0;i<template_Content.menuArray.length;i++){
                    var menuType = template_Content.menuArray[i].menuType;
                    var buttonName = template_Content.menuArray[i].menuName;
                    var buttonData = template_Content.menuArray[i].menuValue;
                    creatMenuBt(buttonName,menuType,buttonData);
                }
            };
            if(template_Content.rcsContent instanceof Array){
                for(var i=0;i<template_Content.rcsContent.length;i++){
                    var ct = template_Content.rcsContent[i];
                    ct.picSrc = ct.url;
                    setOneCard(ct.title,ct.description,ct.suggestions,ct.picSrc,ct.materialNo,ct.url);
                }
            }else if(template_Content.rcsContent.title){
                var ct = template_Content.rcsContent;
                ct.picSrc = ct.url;
                setOneCard(ct.title,ct.description,ct.suggestions,ct.picSrc,ct.materialNo,ct.url);
            }else{
                $("#cardTextId").val(template_Content.rcsContent);
            }
        });
    });
    //提交
    function ajaxSubmitForm() {
        var index = layer.confirm('确认要发送消息吗？', {
            btn : [ '确定', '取消' ]
        }, function(index) {
            submitForm();
            layer.close(index);
        });
        return false;
    }

    //提交
    function submitForm() {
        var tagsinputval = $("#tagsinputval").val();//号码输入框
        if (!tagsinputval) {
            layer.msg("请输入发送号码！");
            return false;
        }
        //提取输入框手机号码
        var phoneNosArray = tagsinputval.split(",");
        if(phoneNosArray.length>10000){
            layer.msg("发送号码总数为: " + phoneNosArray.length +"个,大于最大可发送条数 ！");
            return false;
        }
        var dataArray = new Array();
        for(var i=0;i<phoneNosArray.length;i++){
            var phoneNo = phoneNosArray[i];
            if(phoneNo.indexOf(">") == phoneNo.indexOf("<") ){
                dataArray.push(phoneNo);
            }else if(phoneNo.indexOf(">") > phoneNo.indexOf("<")){
                var pat = /<(.*?)>/g;
                dataArray.push(pat.exec(phoneNo)[1]);
            }
        }
        $("#phone_Nos").val(dataArray.join(','));

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
            delete rcsContent.url;
        }else if(rcsMsgType == 'many_cards_msg'){
            $("input[name='oneCard']").each(function (i,data) {
                var cardJson = JSON.parse(data.value)
                delete cardJson.url;
                rcsCardArray.push(cardJson);
            });
            rcsContent = rcsCardArray;
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
        var loading = layer.load(''); //遮罩层
        $.ajax({
            type: 'get', // 提交方式 get/post
            url: '/admin/sended_sendRcs', // 需要提交的 url
            dataType: 'json',
            data: {
                'content':JSON.stringify(result),
                'phone_Nos':$("#phone_Nos").val(),
                'country_Code':$("#country_Code").val(),
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
                    // window.location.reload();
                    window.location.href= window.location.href;
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

<%--联系人等 js--%>
<script type="text/javascript">
    $(function () {
        var internationalSwitch = $('#send_sms_link').attr('international_telephone_code');
        if('on'!=internationalSwitch){
            $('#global_roaming').remove();
        }
    });
    var layerLoadingNum = 0;
    var layerCloseLoadingNum = 0;
    layui.use(['tree', 'util'], function () {
        var $ = layui.$;
        var tree = layui.tree
            , layer = layui.layer
            , util = layui.util
        $.ajax({
            type: "GET",
            url: "/admin/contact_manager",
            dataType: 'json',
            success: function (data) {
                var treeData = '[{"title":"全部分组","id":"group_all","spread":"true","children":[';
                if (data.length > 0) {
                    for (var i = 0; i < data.length; i++) {
                        if (i != (data.length - 1)) {
                            treeData += '{"title":"' + data[i].group_Name + '","id":"' + "group_" + data[i].id + '"},';
                        } else {
                            treeData += '{"title":"' + data[i].group_Name + '","id":"' + "group_" + data[i].id + '"}';
                        }
                    }
                }
                treeData += "]}]";
                var d = JSON.parse(treeData);
                tree.render({
                    elem: '#treeDemo_m'
                    , data: d
                    // ,spread: true
                    , showCheckbox: true  //是否显示复选框
                    ,id: 'contacts' //定义索引
                    , click: function (obj) {
                        if(obj.data.id=="group_all"){
                            return;
                        }
                        getTreeChildren(tree,obj,false);

                    },oncheck: function(obj){
                        if(obj.data.id=="group_all"){
                            if(obj.checked==true){
                                var checkedBoxVal = obj.data.children
                                $.each(checkedBoxVal, function(i, val) {
                                    if($("div[data-id='"+val.id+"']").children("div[lay-filter]").length==0){
                                        var childrenObj = {
                                            data:val,
                                            elem:$("div[data-id='"+val.id+"']")
                                        }
                                        getTreeChildren(tree,childrenObj,true);
                                    }
                                });
                            }
                        }else{
                            getTreeChildren(tree,obj,true);
                        }
                    }
                });
            }
        })
    });

    function getTreeChildren(tree,obj,checked){
        var selectNode = obj.data;  //获取当前点击的节点数据
        $.ajax({
            type: "post",
            url: '/admin/contact_listContact?pagination.pageIndex=1&pagination.pageSize=99999999&group_Id=' + selectNode.id.split("_")[1],
            dataType: 'json',
            beforeSend: function (){
                layer.load(0, {shade: false});
                layerLoadingNum++;
            },
            success: function (res) {
                var treeData = '[{"title":"' + selectNode.title + '","id":"' + selectNode.id + '","spread":"true"';
                if(checked){
                    treeData += ',"checked":"true"';
                }
                treeData += ',"children":[';
                var data = res.data;
                if (data != null && data.length > 0) {
                    for (var i = 0; i < data.length; i++) {
                        if (i != (data.length - 1)) {
                            treeData += '{"title":"' + data[i].phone_No + '-' + data[i].real_Name + '","id":"' + data[i].real_Name.replace(/\s+/g,"") + '<' + data[i].phone_No.replace(/\s+/g,"") + '>"},';
                        } else {
                            treeData += '{"title":"' + data[i].phone_No + '-' + data[i].real_Name + '","id":"' + data[i].real_Name.replace(/\s+/g,"") + '<' + data[i].phone_No.replace(/\s+/g,"") + '>"}';
                        }
                    }
                }
                treeData += "]}]";
                var d = JSON.parse(treeData);
                tree.render({
                    elem: obj.elem
                    , data: d
                    , showCheckbox: true  //是否显示复选框
                })
            },
            error: function () {
            },
            complete:function (XMLHttpRequest,status){
                if(status=='success'){
                    layerCloseLoadingNum++;
                    if(layerCloseLoadingNum==layerLoadingNum){
                        layer.closeAll('loading')
                        layerLoadingNum = 0;
                        layerCloseLoadingNum = 0;
                    }
                }else{
                    layerLoadingNum = 0;
                    layerCloseLoadingNum = 0;
                    layer.closeAll('loading')
                }
            }
        })
    }

    //联系人数据
    function setContactDatas(dataArray) {
        var phoneNosArray = new Array();
        while (dataArray.length>1000){
            phoneNosArray.push(dataArray.splice(0,1000))
        }
        phoneNosArray.push(dataArray)
        layer.load(0, {shade: false});
        $.each(phoneNosArray,function (i,val) {
            setTimeout(function(){
                if(i==phoneNosArray.length-1){
                    layer.closeAll('loading');
                }
                $("#tagsinputval").tagsinput('add',val.join(','));
            }, i*100);

        })
    }
    layui.use(['tree', 'layer'], function () {
        var $ = layui.jquery,
            layer = layui.layer,
            tree = layui.tree;
        $(document).on('click', '.layui-tree-iconClick .layui-icon-file', function () {
            $(this).parent().parent().find('.layui-tree-txt').trigger("click");
        });
        $(document).on('click', '#addPhoneNo', function () {
            var phoneNos = $(".layui-tree-lineExtend .layui-form-checked").parent().find("input");
            if (phoneNos.length == 0) {
                layer.msg("请选择联系人");
                return;
            }
            var dataArray = new Array();
            phoneNos.each(function () {
                var checkedBoxVal = $(this).val();
                if (checkedBoxVal.indexOf("group_") == -1) {
                    dataArray.push(checkedBoxVal)
                }
            });
            if (dataArray.length == 0) {
                layer.msg("请选择联系人");
                return;
            }
            setContactDatas(dataArray);
        });
        $(document).on('click', '#cleanAll', function () {
            tree.reload('contacts', {});
            $("#tagsinputval").tagsinput('removeAll');
        });
        $(document).on('click', '#send_Time_button', function () {
            var h = $(window).height()/6;
            layer.open({
                title: '选择日期',
                type: 1,
                icon: 3,
                //skin: 'layui-layer-rim',
                skin: 'layui-layer-molv',
                area: ['500px', '200px'],
                offset: h+'px',
                content: $('#send_Time_item'),
                btn:['确定'],
                success:function(layero, index) {
                    layui.use(['laydate'], function () {
                        var laydate = layui.laydate;
                        laydate.render({     //创建时间选择框
                            elem: '#send_Time_input',
                            type: 'datetime',
                            trigger : 'click'
                        });
                    });
                    layero.find("#send_Time_input").val('');
                },
                yes: function(index, layero){
                    var send_Time = layero.find("#send_Time_input").val()
                    if(send_Time == null || send_Time.trim() ==""){
                        layer.msg("请选择发送时间");
                        return;
                    }
                    $("#send_Time").val(send_Time);
                    $("#submit_button").trigger("click");
                    layer.close(index); //如果设定了yes回调，需进行手工关闭
                }
            })
        });
    });
    $('#tagsinputval').on('itemAdded', function(event) {
        $('.bootstrap-tagsinput input[type="text"]').attr("placeholder","");
    });
</script>