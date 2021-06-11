//清空所有悬浮按钮
function deleteAllMenu() {
	var index = layer.confirm('确认要清空所有按钮吗？', {
		btn : [ '确定', '取消' ]
	}, function(index) {
		$("#btnGroup").empty();
		layer.close(index);
	});
}

//添加悬浮菜单
function addOneMenu() {
	$("#dialogFormId").resetForm();//清空所有条件
	//校验按钮数
	var check = "true"
	$("#btnGroup").find("button").each(function (i,data) {
		if(i == 4){
			check = "false";
		}
	})
	if(check == "false"){
		layer.msg('悬浮菜单最多添加5个！');
		return false;
	}
	//默认选中新增帧
	var layerOpen = layer.open({
		area: ['800px', '500px'],
		title:'添加悬浮菜单',
		type: 1,
		content: $("#dialogId"),
		btn: ['确定'],
		yes: function (index, layero) {
			// var menuType = $("input[name='menuType'][checked]").val();
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
			creatMenuBt(buttonName,menuType,buttonData);
			layer.close(layerOpen);
		}
	});
}
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
//添加一个卡片
function addOneCard() {
	//校验卡片数、消息类型
	var rcsMsgType = $("#rcsMsgTypeId").val();
	if(rcsMsgType == 'single_card_msg'){
		var checkCard = "true";
		$("#carouselItemId").find("span").each(function (i,data) {
			checkCard = "false";
		})
		if(checkCard == "false"){
			layer.msg('单卡片消息只能添加一张卡片！');
			return false;
		}
	}
	if(rcsMsgType == 'many_cards_msg'){
		var tag = "true";
		$("#carouselItemId").find("span").each(function (i,data) {
			if(i == 9){
				tag = "false";
			}
		})
		if(tag == "false"){
			layer.msg('多卡片消息最多添加十张卡片！');
			return false;
		}
	}
	layer.open({
		area: ['800px', '550px'],
		title:'添加卡片消息',
		type: 2,
		content: "/admin/rcs/rcs_send_card_add.jsp",
		maxmin: true,
	});
}
//编辑卡片
function editCard(cardJson) {
	var span = this;
	layer.open({
		area: ['800px', '550px'],
		title:'编辑卡片消息',
		type: 2,
		content: "/admin/rcs/rcs_send_card_edit.jsp?cardJson="+cardJson,
		maxmin: true,
	});
}
//删除卡片
function deleteCard(currentCardIndex) {
	var spans =  $("#carouselItemId span");
	for (var a=0 ;a<spans.length; a++){
		var span = spans[a];
		if(span.getAttribute("id")== "span_"+currentCardIndex){
			span.remove();
		}
	}
	cardCarousel.reload(options);
	layer.closeAll();
}
//重置卡片
function setCurrentCard(title,description,buttonArray,picSrc,materialNo,currentCardIndex,url){
	var card = assembleCard(title,description,buttonArray,picSrc,materialNo,currentCardIndex,url);
	var spans =  $("#carouselItemId span");
	var cardNode = $(card).get(0)
	for (var a=0 ;a<spans.length; a++){
		var span = spans[a];
		if(span.getAttribute("id")== "span_"+currentCardIndex){
			var next = span.nextSibling;
			var spanParent = span.parentNode;
			spanParent.insertBefore(cardNode,next);
			span.remove();
		}
	}
	cardCarousel.reload(options);
	layer.closeAll();
}
function setOneCard(title,description,buttonArray,picSrc,materialNo,url){
	if(!picSrc.IsPicture()){
		picSrc = "/public/admin/images/videoPlayButton.png"
	}
	var card = assembleCard(title,description,buttonArray,picSrc,materialNo,cardIndex,url);
	cardIndex++;
	$("#rcsContentCardDiv").attr("style","display:block;");//显示div
	$("#carouselItemId").append(card);
	cardCarousel.reload(options);
	layer.closeAll();
}
//组装卡片数据
function assembleCard(title,description,buttonArray,picSrc,materialNo,currentCardIndex,url) {
	var cardJson = {};
	cardJson.title = title;
	cardJson.description = description;
	cardJson.materialNo = materialNo;
	cardJson.suggestions = buttonArray;
	cardJson.cardIndex = currentCardIndex;
	cardJson.url = url;
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
//获取请求参数
function getRequestParams() {
	var url = location.search;//获取url中"?"符后的字串
	url = decodeURI(url);
	var params = new Object();
	if (url.indexOf("?") != -1) {
		var str = url.substr(1);
		strs = str.split("&");
		for (var i = 0; i < strs.length; i++) {
			params[strs[i].split("=")[0]] = unescape(strs[i].split("=")[1]);
		}
	}
	return params;
}
//判断对象是否为空
function isEmpty(obj) {
	if (typeof obj == "undefined" || obj == null || obj == "") {
		return true;
	} else {
		return false;
	}
}
//增加一个名为 IsPicture 的函数作为String构造函数的原型对象的一个方法。
String.prototype.IsPicture = function() {
	//判断是否是图片 - strFilter必须是小写列举
	var strFilter=".jpeg|.gif|.jpg|.png|.bmp|.pic|.svg|"
	if(this.indexOf(".")>-1)
	{
		var p = this.lastIndexOf(".");
		var strPostfix=this.substring(p,this.length) + '|';
		strPostfix = strPostfix.toLowerCase();
		if(strFilter.indexOf(strPostfix)>-1)
		{
			return true;
		}
	}
	return false;
}

//查看详情
function contentDetail(content) {
    layer.open({
        area: ['750px', '550px'],
        title:'详情',
        maxmin: true,
        closeBtn: 1,
        shadeClose: true,
        type: 2,
        content: "/admin/rcs/rcs_records_detail.jsp?content="+encodeURIComponent(content),
    });
}