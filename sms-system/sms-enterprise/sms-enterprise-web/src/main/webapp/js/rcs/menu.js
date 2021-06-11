
$(function() {
	var mId = null;
	var layer;
	layui.use('layer', function(){
		layer = layui.layer;
	});
	//显示自定义按钮组
	var obj = {
		"menu": {
			"button": [{
					"type": "reply",
					"name": "回复内容yes",
					"data": "yes",
					"sub_button": []
				},
				{
					"type": "reply",
					"name": "回复内容TD",
					"data": "TD",
					"sub_button": []
				},
				{
					"name": "菜单",
					"sub_button": [{
						"type": "openUrl",
						"name": "跳转网页1",
						"url": "http://www.xxxxxx.com/",
						"data": "set_by_chatbot_open_url1"
						},
						{
							"type": "openUrl",
							"name": "跳转网页2",
							"url": "http://com.xxxxxx.com/",
							"data": "set_by_chatbot_open_url2"
						},
						{
							"type": "reply",
							"name": "赞一下我们",
							"data": "赞赞赞"
						},
						{
							"type": "dialPhoneNumber",
							"name": "打电话",
							"phoneNumber":"139xxxxxxxx",
							"data": "set_by_chatbot_dial_menu_phone_number"
						}
					]
				}
			]
		}
	};
	//
	$.ajax({
		type: "POST",
		async:false,
		url: "/admin/rcsMenu_getMenuValue",
		success : function(data) {
			if (data.code=="0") {
				obj = reduction(JSON.parse(data.msg));
			}else if(data.code == 301){
				layer.msg(data.msg, {icon: 2, time: 2000}, function () {
					top.location.href = data.url;
					return;
				});
			}
		}
	});


	var tempObj = {}; //存储HTML对象
	var button = obj.menu.button; //一级菜单
	//显示异常
	if (obj.errcode) {
		$('#abnormalModal').modal('show');
	}
	//一级菜单对象
	function parents(param) {
		this.name = param;
		this.sub_button = [];
	}
	//二级菜单对象
	function subs(param) {
		this.name = param;
	}
	var objp = new parents();
	var objs = new subs();
	var ix = button.length; //一级菜单数量
	var menu = '<div class="custom-menu-view__menu"><div class="text-ellipsis"></div></div>';
	var customBtns = $('.custom-menu-view__footer__right');
	if (button.length > 0) {
		showMenu();
		//$('.cm-edit-before').hide();
		$('.cm-edit-after').hide();
	} else {
		addMenu();
		$('.cm-edit-before').siblings().hide();
	}
	//显示第一级菜单
	function showMenu() {
		if (button.length == 1) {
			appendMenu(button.length);
			showBtn();
			$('.custom-menu-view__menu').css({
				width: '50%',
			});
		}
		if (button.length == 2) {
			appendMenu(button.length);
			showBtn();
			$('.custom-menu-view__menu').css({
				width: '33.3333%',
			});
		}
		if (button.length == 3) {
			appendMenu(button.length);
			showBtn();
			$('.custom-menu-view__menu').css({
				width: '33.3333%',
			});
		}
		for (var b = 0; b < button.length; b++) {
			$('.custom-menu-view__menu')[b].setAttribute('alt', b);
		}
	}
	//显示子菜单
	function showBtn() {
		for (var i = 0; i < button.length; i++) {
			var text = button[i].name;
			var list = document.createElement('ul');
			list.className = "custom-menu-view__menu__sub";
			$('.custom-menu-view__menu')[i].childNodes[0].innerHTML = text;
			$('.custom-menu-view__menu')[i].appendChild(list);
			for (var j = 0; j < button[i].sub_button.length; j++) {
				var text = button[i].sub_button[j].name;
				var li = document.createElement("li");
				var tt = document.createTextNode(text);
				var div = document.createElement('div');
				li.className = 'custom-menu-view__menu__sub__add';
				li.id = 'sub_' + i + '_' + j; //设置二级菜单id
				div.className = "text-ellipsis";
				div.appendChild(tt);
				li.appendChild(div);
				$('.custom-menu-view__menu__sub')[i].appendChild(li);
			}
			var ulBtnL = button[i].sub_button.length;
			var iLi = document.createElement("li");
			var ii = document.createElement('i');
			var iDiv = document.createElement("div");
			ii.className = "glyphicon glyphicon-plus text-info";
			iDiv.className = "text-ellipsis";
			iLi.className = 'custom-menu-view__menu__sub__add';
			iDiv.appendChild(ii);
			iLi.appendChild(iDiv);
			if (ulBtnL < 5) {
				$('.custom-menu-view__menu__sub')[i].appendChild(iLi);
			}

		}
	}
	//显示添加的菜单
	function appendMenu(num) {
		var menuDiv = document.createElement('div');
		var mDiv = document.createElement('div');
		var mi = document.createElement('i');
		mi.className = 'glyphicon glyphicon-plus text-info iBtn';
		mDiv.className = 'text-ellipsis';
		menuDiv.className = 'custom-menu-view__menu';
		mDiv.appendChild(mi);
		menuDiv.appendChild(mDiv)
		switch (num) {
			case 1:
				customBtns.append(menu);
				customBtns.append(menuDiv);
				break;
			case 2:
				customBtns.append(menu);
				customBtns.append(menu);
				customBtns.append(menuDiv);
				break;
			case 3:
				customBtns.append(menu);
				customBtns.append(menu);
				customBtns.append(menu);
				break;
		}
	}
	//初始化菜单按钮
	function addMenu() {
		var menuI =
			'<div class="custom-menu-view__menu"><div class="text-ellipsis"><i class="glyphicon glyphicon-plus text-info iBtn"></i></div></div>';
		var sortIndex = true;
		customBtns.append(menuI);
		var customFirstBtns = $('.custom-menu-view__menu');
		var firstBtnsLength = customFirstBtns.length;
		if (firstBtnsLength <= 1) {
			customFirstBtns.css({
				width: '100%',
			})
		}
	}
	//添加菜单按钮
	var customEl = '<div class="custom-menu-view__menu"><div class="text-ellipsis">新建菜单</div></div>'
	var customUl =
		'<ul class="custom-menu-view__menu__sub"><li class="custom-menu-view__menu__sub__add"><div class="text-ellipsis"><i class="glyphicon glyphicon-plus text-info"></i></div></li></ul>';
	var customLi = '<li class="custom-menu-view__menu__sub__add"><div class="text-ellipsis">新建子菜单</div></li>';
	$('.iBtn').parent().on('click', function() {
		var num = $('.custom-menu-view__footer__right').find('.custom-menu-view__menu').length;
		var ulNum = $(this).parents('.custom-menu-view__menu').prev().find('.custom-menu-view__menu__sub').length;
		ix++;
		if (ix < 4) {
			$(this).parent().before(customEl);
			$(this).parent().prev().append(customUl);

			$('.custom-menu-view__footer__right').find('.subbutton__actived').removeClass('subbutton__actived');
			$(this).parent().prev().addClass('subbutton__actived');

			//一级菜单列数
			var buttonIndex = $(this).parents('.custom-menu-view__menu').index() - 1;
			$('.custom-menu-view__menu').eq(buttonIndex).on('click', (function(buttonIndex) {
				var txt = $('.custom-menu-view__menu').eq(buttonIndex).text();
				setMenuText(txt);
			})(buttonIndex));

			if (ix == 1) {
				$('.custom-menu-view__menu').css({
					width: '50%'
				});
				$('.custom-menu-view__menu')[ix - 1].setAttribute('alt', ix - 1);
			}
			if (ix == 2) {
				$('.custom-menu-view__menu').css({
					width: '33.3333%'
				});
				$('.custom-menu-view__menu')[ix - 1].setAttribute('alt', ix - 1);
			}
			var divText = $(this).parent().prev().find('.text-ellipsis').text();
			button.push(new parents(divText));
		}
		if (ix == 3) {
			$(this).parents('.custom-menu-view__menu').remove();
			$(this).parent().append(customUl);
			$('.custom-menu-view__menu')[ix - 1].setAttribute('alt', ix - 1);
		}
		$('.cm-edit-after').show().siblings().hide();
		radios[0].checked = true;
		$('input[name="data"]').val('');
		$('#editMsg').show();
		$('#editLink').hide();
		$('#editPhone').hide();

	});
	var setMenuText = function(value) {
		setInput(value);
		updateTit(value);

		radios[0].checked = true;
		$('#editMsg').show();
		$('#editLink').hide();
	}

	function setSubText() {
		var actived = $('.custom-menu-view__menu__sub__add').hasClass('subbutton__actived');
		var activedTxt = $('.subbutton__actived').text();
		if (actived) {
			setInput(activedTxt);
			updateTit(activedTxt);
			radios[0].checked = true;
			$('input[name="data"]').val('');
			$('#editMsg').show();
			$('#editLink').hide();
			$('#editPhone').hide();
		}
	}
	//添加子菜单
	var colIndex; //一级菜单列数
	customBtns.on('click', 'li>.text-ellipsis>i', function() {
		//绑定删除事件
		colIndex = $(this).parents('.custom-menu-view__menu').attr('alt');
		var liNum = $(this).parents('.custom-menu-view__menu').find('li').length;

		if (liNum <= 1) {
			$('#reminderModal').modal('show');
		} else {
			if (liNum < 6) {
				$(this).parent().parent().before(customLi);
				setLiId();
			}
			if (liNum == 5) {
				$(this).parents('li').hide();
			}
		}
		$('#radioGroup').show();
		setSubText()
	});
	//确定添加子菜单事件
	$('.reminder').click(function() {
		var ul = $('.custom-menu-view__menu')[colIndex].getElementsByTagName('ul')[0];
		var li = document.createElement('li');
		var div = document.createElement('div');
		var Text = document.createTextNode('新建子菜单');
		li.className = "custom-menu-view__menu__sub__add";
		div.className = "text-ellipsis";
		div.appendChild(Text);
		li.appendChild(div);
		ul.insertBefore(li, ul.childNodes[0]);
		setLiId();
		delete button[colIndex].type;
		delete button[colIndex].data;
		delete button[colIndex].url;
		$('#reminderModal').modal('hide');

		setSubText()
	});
	//对新添加二级菜单添加id
	function setLiId() {
		var prev = $('.custom-menu-view__menu')[colIndex].getElementsByTagName('i')[0].parentNode.parentNode.previousSibling;
		var divText = prev.childNodes[0].innerHTML;
		button[colIndex].sub_button.push(new subs(divText));
		var id = button[colIndex].sub_button.length - 1;
		prev.setAttribute('id', 'sub_' + colIndex + '_' + id);

		$('.custom-menu-view__footer__right').find('.subbutton__actived').removeClass('subbutton__actived');
		$('.custom-menu-view__menu').eq(colIndex).find('i').parent().parent().prev().addClass('subbutton__actived');
	}
	//点击菜单
	customBtns.on('click', '.text-ellipsis', function() {
		$('.cm-edit-after').show().siblings().hide();
		if ($(this).parent().attr('id') || $(this).parent().attr('alt')) {
			$(this).parents('.custom-menu-view__footer__right').find('.subbutton__actived').removeClass('subbutton__actived');
			$(this).parent().addClass('subbutton__actived');
		}
		//一级菜单列数
		var buttonIndex = $(this).parents('.custom-menu-view__menu').index();
		//点击在一级菜单上
		if ($(this).parent().attr('alt')) {

			if ($('.custom-menu-view__menu').hasClass('subbutton__actived')) {
				var current = $('.subbutton__actived');
				var alt = current.attr('alt');
				var lis = current.find('ul>li');
				setInput(button[buttonIndex].name);
				updateTit(button[buttonIndex].name);
				if (lis.length > 1) {
					$('#editMsg').hide();
					$('#editLink').hide();
					$('#editPhone').hide();
					$('#radioGroup').hide();
				} else {
					if (button[buttonIndex].type == 'reply') {
						radios[0].checked = true;
						$('input[name="data"]').val(button[buttonIndex].data);
						$('#editMsg').show();
						$('#editLink').hide();
						$('#editPhone').hide();
						$('#radioGroup').show();
					} else if (button[buttonIndex].type == 'openUrl') {
						$('input[name="urlData"]').val(button[buttonIndex].data);
						$('input[name="url"]').val(button[buttonIndex].url);
						radios[1].checked = true;
						$('#editMsg').hide();
						$('#editLink').show();
						$('#editPhone').hide();
						$('#radioGroup').show();
					}else if (button[buttonIndex].type == 'dialPhoneNumber') {
						$('input[name="phoneData"]').val(button[buttonIndex].data);
						$('input[name="phoneNumber"]').val(button[buttonIndex].phoneNumber);
						radios[2].checked = true;
						$('#editMsg').hide();
						$('#editLink').hide();
						$('#editPhone').show();
						$('#radioGroup').show();
					} else {
						radios[0].checked = true;
						$('#editMsg').show();
						$('#editLink').hide();
						$('#editPhone').hide();
						$('#radioGroup').show();
						$('input[name="data"]').val('');
					}
				}

			}

		}
		//点击在二级菜单上
		if ($(this).parent().attr('id')) {
			var substr = $(this).parent().attr('id').lastIndexOf('_') + 1;
			var subIndex = $(this).parent().attr('id').substring(substr);
			var subText = button[buttonIndex].sub_button[subIndex].name;
			var subUrl = button[buttonIndex].sub_button[subIndex].url;
			var subType = button[buttonIndex].sub_button[subIndex].type;
			var subKey = button[buttonIndex].sub_button[subIndex].key;
			var subData = button[buttonIndex].sub_button[subIndex].data;
			var phoneNumber = button[buttonIndex].sub_button[subIndex].phoneNumber;

			if ($('.custom-menu-view__menu__sub__add').hasClass('subbutton__actived')) {
				setInput(subText);
				updateTit(subText);
				$('#radioGroup').show();
				if (subType == 'reply') {
					radios[0].checked = true;
					$('input[name="data"]').val(subData);
					$('#editMsg').show();
					$('#editLink').hide();
					$('#editPhone').hide();
					$('#radioGroup').show();
				} else if (subType == 'openUrl') {
					radios[1].checked = true;
					$('#editMsg').hide();
					$('#editPhone').hide();
					$('#editLink').show();
					$('input[name="url"]').val(subUrl);
					$('input[name="urlData"]').val(subData);
				} else if (subType == 'dialPhoneNumber') {
					radios[2].checked = true;
					$('#editMsg').hide();
					$('#editLink').hide();
					$('#editPhone').show();
					$('input[name="phoneNumber"]').val(phoneNumber);
					$('input[name="phoneData"]').val(subData);
				} else {
					radios[0].checked = true;
					$('#editMsg').show();
					$('#editLink').hide();
					$('#editPhone').hide();
					$('input[name="data"]').val('');
				}
			}
		}
	});
	//设置右边input的value
	function setInput(val) {
		$('input[name="custom_input_title"]').val(val);
	}
	//实时更新右侧顶部标题
	function updateTit(text) {
		$('#cm-tit').html(text);
	}
	//保存右侧菜单名称
	$('input[name="custom_input_title"]').blur(function() {
		var val = $(this).val().replace(/^\s+|\s+$/g,"");
		if(val == ""){
			layer.msg("菜单名称不能为空！");
			val = "新建菜单"
		}
		var current = $('.subbutton__actived');
		if ($('.custom-menu-view__menu__sub__add').hasClass('subbutton__actived')) {
			var row = current.attr('id').lastIndexOf('_') - 1;
			var col = current.attr('id').lastIndexOf('_') + 1;
			var sub_row = current.attr('id').substr(row, 1);
			var sub_col = current.attr('id').substring(col);
			button[sub_row].sub_button[sub_col].name = val;
			current.find('.text-ellipsis').text(val);
			updateTit(val);
		} else if ($('.custom-menu-view__menu').hasClass('subbutton__actived')) {
			var alt = current.attr('alt');
			button[alt].name = val;
			current.children('.text-ellipsis').text(val);
			updateTit(val)
		}

	});
	//保存右侧跳转页面的url
	$('input[name="url"]').keyup(function() {
		var val = $(this).val().replace(/^\s+|\s+$/g,"");
		var current = $('.subbutton__actived');
		if ($('.custom-menu-view__menu__sub__add').hasClass('subbutton__actived')) {
			var row = current.attr('id').lastIndexOf('_') - 1;
			var col = current.attr('id').lastIndexOf('_') + 1;
			var sub_row = current.attr('id').substr(row, 1);
			var sub_col = current.attr('id').substring(col);
			button[sub_row].sub_button[sub_col].url = val;
			button[sub_row].sub_button[sub_col].type = 'openUrl';
			if (button[sub_row].sub_button[sub_col].url == '') {
				delete button[sub_row].sub_button[sub_col].url;
			}
		} else if ($('.custom-menu-view__menu').hasClass('subbutton__actived')) {
			var alt = current.attr('alt');
			button[alt].url = val;
			button[alt].type = 'openUrl';
			if (button[alt].url == '') {
				delete button[alt].url;
			}
		}

	});
	//保存右侧跳转页面的data
	$('input[name="data"]').keyup(function() {
		var val = $(this).val().replace(/^\s+|\s+$/g,"");
		var current = $('.subbutton__actived');
		if ($('.custom-menu-view__menu__sub__add').hasClass('subbutton__actived')) {
			var row = current.attr('id').lastIndexOf('_') - 1;
			var col = current.attr('id').lastIndexOf('_') + 1;
			var sub_row = current.attr('id').substr(row, 1);
			var sub_col = current.attr('id').substring(col);
			button[sub_row].sub_button[sub_col].data = val;
			button[sub_row].sub_button[sub_col].type = 'reply';
			if (button[sub_row].sub_button[sub_col].data == '') {
				delete button[sub_row].sub_button[sub_col].data;
			}
		} else if ($('.custom-menu-view__menu').hasClass('subbutton__actived')) {
			var alt = current.attr('alt');
			button[alt].data = val;
			button[alt].type = 'reply';
			if (button[alt].data == '') {
				delete button[alt].data;
			}
		}

	});
	//保存右侧跳转页面的phoneData
	$('input[name="urlData"]').keyup(function() {
		var val = $(this).val().replace(/^\s+|\s+$/g,"");
		var current = $('.subbutton__actived');
		if ($('.custom-menu-view__menu__sub__add').hasClass('subbutton__actived')) {
			var row = current.attr('id').lastIndexOf('_') - 1;
			var col = current.attr('id').lastIndexOf('_') + 1;
			var sub_row = current.attr('id').substr(row, 1);
			var sub_col = current.attr('id').substring(col);
			button[sub_row].sub_button[sub_col].data = val;
			button[sub_row].sub_button[sub_col].type = 'openUrl';
			if (button[sub_row].sub_button[sub_col].data == '') {
				delete button[sub_row].sub_button[sub_col].data;
			}
		} else if ($('.custom-menu-view__menu').hasClass('subbutton__actived')) {
			var alt = current.attr('alt');
			button[alt].data = val;
			button[alt].type = 'openUrl';
			if (button[alt].data == '') {
				delete button[alt].data;
			}
		}

	});
	//保存右侧跳转页面的phoneData
	$('input[name="phoneData"]').keyup(function() {
		var val = $(this).val().replace(/^\s+|\s+$/g,"");
		var current = $('.subbutton__actived');
		if ($('.custom-menu-view__menu__sub__add').hasClass('subbutton__actived')) {
			var row = current.attr('id').lastIndexOf('_') - 1;
			var col = current.attr('id').lastIndexOf('_') + 1;
			var sub_row = current.attr('id').substr(row, 1);
			var sub_col = current.attr('id').substring(col);
			button[sub_row].sub_button[sub_col].data = val;
			button[sub_row].sub_button[sub_col].type = 'dialPhoneNumber';
			if (button[sub_row].sub_button[sub_col].data == '') {
				delete button[sub_row].sub_button[sub_col].data;
			}
		} else if ($('.custom-menu-view__menu').hasClass('subbutton__actived')) {
			var alt = current.attr('alt');
			button[alt].data = val;
			button[alt].type = 'dialPhoneNumber';
			if (button[alt].data == '') {
				delete button[alt].data;
			}
		}

	});
	//保存右侧跳转页面的phoneData
	$('input[name="phoneNumber"]').keyup(function() {
		var val = $(this).val().replace(/^\s+|\s+$/g,"");
		var current = $('.subbutton__actived');
		if ($('.custom-menu-view__menu__sub__add').hasClass('subbutton__actived')) {
			var row = current.attr('id').lastIndexOf('_') - 1;
			var col = current.attr('id').lastIndexOf('_') + 1;
			var sub_row = current.attr('id').substr(row, 1);
			var sub_col = current.attr('id').substring(col);
			button[sub_row].sub_button[sub_col].phoneNumber = val;
			button[sub_row].sub_button[sub_col].type = 'dialPhoneNumber';
			if (button[sub_row].sub_button[sub_col].data == '') {
				delete button[sub_row].sub_button[sub_col].data;
			}
		} else if ($('.custom-menu-view__menu').hasClass('subbutton__actived')) {
			var alt = current.attr('alt');
			button[alt].phoneNumber = val;
			button[alt].type = 'dialPhoneNumber';
			if (button[alt].data == '') {
				delete button[alt].data;
			}
		}

	});
	//tab切换
	$('.msg-panel__tab>li').click(function() {
		$('.msg-panel__tab>li').eq($(this).index()).addClass('on').siblings().removeClass('on');
		$('.msg-panel__context').eq($(this).index()).removeClass('hide').siblings().addClass('hide')
	});

	//菜单内容跳转
	var radios = document.getElementsByName("radioBtn");
	for (var n = 0; n < radios.length; n++) {
		radios[n].index = n;
		radios[n].onchange = function() {
			if (radios[this.index].checked == true) {
				if (radios[this.index].value == 'openUrl') {
					$('#editMsg').hide();
					$('#editLink').show();
					$('#editPhone').hide();

				}else if (radios[this.index].value == 'dialPhoneNumber') {
					$('#editMsg').hide();
					$('#editLink').hide();
					$('#editPhone').show();
				} else {
					$('#editMsg').show();
					$('#editLink').hide();
					$('#editPhone').hide();
				}
				var current = $('.subbutton__actived');
				if ($('.custom-menu-view__menu__sub__add').hasClass('subbutton__actived')) {
					var row = current.attr('id').lastIndexOf('_') - 1;
					var col = current.attr('id').lastIndexOf('_') + 1;
					var sub_row = current.attr('id').substr(row, 1);
					var sub_col = current.attr('id').substring(col);
					if(button[sub_row].sub_button[sub_col].type!=radios[this.index].value){
						cleanNotSelectedInputData(button[sub_row].sub_button[sub_col].type);
					}
				} else if ($('.custom-menu-view__menu').hasClass('subbutton__actived')) {
					var alt = current.attr('alt');
					if(button[alt].type!=radios[this.index].value){
						cleanNotSelectedInputData(button[alt].type);
					}
				}
			}
		};
	}
	//清除未选择的输入框数据
	function cleanNotSelectedInputData(type) {
		if(type=='openUrl'){
			$('input[name="data"]').val('');
			$('input[name="phoneNumber"]').val('');
			$('input[name="phoneData"]').val('');
		}else if(type=='dialPhoneNumber'){
			$('input[name="data"]').val('');
			$('input[name="url"]').val('');
			$('input[name="urlData"]').val('');
		}else{
			$('input[name="url"]').val('');
			$('input[name="urlData"]').val('');
			$('input[name="phoneNumber"]').val('');
			$('input[name="phoneData"]').val('');
		}
	}
	//删除菜单按钮
	$('#delMenu').click(function() {
		var is_Actived = $('.custom-menu-view__menu').hasClass('subbutton__actived'); //一级菜单选择项
		var is_actived = $('.custom-menu-view__menu__sub__add').hasClass('subbutton__actived'); //二级菜单选中项
		var rowIndex = 0;
		var colIndex = 0;

		if (is_Actived) {
			rowIndex = $('.subbutton__actived').attr('alt');
			$('.subbutton__actived').remove();
			delete button[rowIndex];
		} else if (is_actived) {
			rowIndex = $('.subbutton__actived').attr('id').substr($('.subbutton__actived').attr('id').lastIndexOf('_') - 1,
				1);
			colIndex = $('.subbutton__actived').attr('id').substr($('.subbutton__actived').attr('id').lastIndexOf('_') + 1,
				1);
			$('.subbutton__actived').remove();
			delete button[rowIndex].sub_button[colIndex];
			 $('.custom-menu-view__menu__sub:eq('+rowIndex+')').find('.glyphicon').parents('li').show();
		}
		//清除右边数据
		$('.cm-edit-before').show().siblings().hide();
		updateTit('');
		setInput('');
		$('input[name="url"]').val('');
	})
	//保存自定义菜单
	$('#saveBtns').click(function() {
		var activeBtn = $('.custom-menu-view__menu').hasClass('subbutton__actived');
		var activeSubBtn = $('.custom-menu-view__menu__sub__add').hasClass('subbutton__actived');
		var rowIndex = 0;
		var colIndex = 0;
		var url = null;
		var strRegex = '(https?|ftp|file)://[-A-Za-z0-9+&@#/%?=~_|!:,.;]+[-A-Za-z0-9+&@#/%=~_|]';
		var re = new RegExp(strRegex);

		if (activeBtn) {
			rowIndex = $('.subbutton__actived').attr('alt');
		} else if (activeSubBtn) {
			rowIndex = $('.subbutton__actived').attr('id').substr($('.subbutton__actived').attr('id').lastIndexOf('_') - 1,
				1);
			colIndex = $('.subbutton__actived').attr('id').substr($('.subbutton__actived').attr('id').lastIndexOf('_') + 1,
				1);
		}

		if (activeBtn) {
			//一级菜单
			if (button[rowIndex].hasOwnProperty('url')) {
				url = button[rowIndex].url;
				if (!re.test(url)) {
					layer.msg("请输入正确的url地址！");
					return false;
				}
				saveAjax();
			} else if (button[rowIndex].name == "") {
				layer.msg("菜单名称不能为空！");
			} else if (getBytes(button[rowIndex].name)>12) {
				layer.msg("菜单名称不超过6个汉字或12个字符");

			}else if (button[rowIndex].hasOwnProperty('data') || button[rowIndex].sub_button.length > 0) {
				saveAjax();
			} else {
				layer.msg("请填写当前菜单对应数据！");
			}
		} else if (activeSubBtn) {
			//二级菜单
			if (button[rowIndex].sub_button[colIndex].hasOwnProperty('url')) {
				/*url = button[rowIndex].sub_button[colIndex].url;
				if (!re.test(url)) {
					layer.msg("请输入正确的url地址！");
					return false;
				}*/
				saveAjax();
			} else if (button[rowIndex].sub_button[colIndex].hasOwnProperty('data')) {
				//layer.msg("请选择图文信息！");
				saveAjax();
			} else {
				layer.msg("菜单内容不能为空！");
			}
		} else {
			saveAjax();
		}
	});
	function checkData(btn_arr){
		for (var i = 0; i < btn_arr.length; i++) {
			if(btn_arr[i]==undefined)
				continue;
			if(btn_arr[i].sub_button.length>0){
				for (var a = 0; a < btn_arr[i].sub_button.length; a++) {
					if(btn_arr[i].sub_button[a]==undefined)
						continue;
					if(btn_arr[i].sub_button[a].type ==undefined || (btn_arr[i].sub_button[a].type=="reply" && isEmpty(btn_arr[i].sub_button[a].data))){
						return "【"+btn_arr[i].sub_button[a].name+"】回复内容不能为空"
					}else if(btn_arr[i].sub_button[a].type=="openUrl" && isEmpty(btn_arr[i].sub_button[a].url)){
						return "【"+btn_arr[i].sub_button[a].name+"】跳转地址不能为空"
					}else if(btn_arr[i].sub_button[a].type=="dialPhoneNumber" && isEmpty(btn_arr[i].sub_button[a].phoneNumber)){
						return "【"+btn_arr[i].sub_button[a].name+"】电话号码不能为空"
					}
				}
			}else{
				if(btn_arr[i].type == undefined || (btn_arr[i].type=="reply" && isEmpty(btn_arr[i].data))){
					return "【"+btn_arr[i].name+"】回复内容不能为空"
				}else if(btn_arr[i].type=="openUrl" && isEmpty(btn_arr[i].url)){
					return "【"+btn_arr[i].name+"】跳转地址不能为空"
				}else if(btn_arr[i].type=="dialPhoneNumber" && isEmpty(btn_arr[i].phoneNumber)){
					return "【"+btn_arr[i].name+"】电话号码不能为空"
				}
			}
		}
		return "";
	}
	//保存
	function saveAjax() {
		var btn_arr = obj.menu.button;
		var result = checkData(btn_arr);
		if(result!=""){
			layer.msg(result);
			return
		}
		var menu = {
			entries: []
		}
		for (var i = 0; i < btn_arr.length; i++) {
			var btn_obj = {};
			if(btn_arr[i]==undefined)
				continue;
			if (btn_arr[i].sub_button.length == 0) {
				if(btn_arr[i].type == 'dialPhoneNumber'){
					btn_obj = {
						action: {
							dialerAction:{
								dialPhoneNumber:{
									phoneNumber:btn_arr[i].phoneNumber
								}
							},
							displayText:btn_arr[i].name,
							postback: {
								data: btn_arr[i].data
							}
						}
					}
				}else if(btn_arr[i].type == 'openUrl'){
					btn_obj = {
						action: {
							urlAction:{
								openUrl:{
									url:btn_arr[i].url
								}
							},
							displayText:btn_arr[i].name,
							postback: {
								data: btn_arr[i].data
							}
						}
					}
				}else{
					btn_obj = {
						reply: {
							displayText: btn_arr[i].name,
							postback: {
								data: btn_arr[i].data ? btn_arr[i].data : btn_arr[i].url
							}
						}
					}
				}
			} else {
				btn_obj = {
					menu: {
						displayText: btn_arr[i].name,
						entries: []
					}
				}
				for (var a = 0; a < btn_arr[i].sub_button.length; a++) {
					var tmenu = {};
					if(btn_arr[i].sub_button[a]==undefined)
						continue;
					if(btn_arr[i].sub_button[a].type == 'dialPhoneNumber'){
						tmenu = {
							action: {
								dialerAction:{
									dialPhoneNumber:{
										phoneNumber:btn_arr[i].sub_button[a].phoneNumber
									}
								},
								displayText:btn_arr[i].sub_button[a].name,
								postback: {
									data: btn_arr[i].sub_button[a].data
								}
							}
						}
					}else if(btn_arr[i].sub_button[a].type == 'openUrl'){
						tmenu = {
							action: {
								urlAction:{
									openUrl:{
										url:btn_arr[i].sub_button[a].url
									}
								},
								displayText:btn_arr[i].sub_button[a].name,
								postback: {
									data: btn_arr[i].sub_button[a].data
								}
							}
						}
					}else{
						tmenu = {
							reply: {
								displayText: btn_arr[i].sub_button[a].name,
								postback: {
									data: btn_arr[i].sub_button[a].data ? btn_arr[i].sub_button[a].data : btn_arr[i].sub_button[a].url
								}
							}
						}
					}
					btn_obj.menu.entries.push(tmenu);
				}
			}
			menu.entries.push(btn_obj);
		}
		var menuJson ={
			menu:menu
		}
		//console.log('转后')
		//console.log("newJson :"+JSON.stringify(menuJson));
		//reduction(menu);
		$.ajax({
		  		type: "POST",
			url: "/admin/rcsMenu_addOrEdit",
			data : {
				Menu_Value : JSON.stringify(menuJson) ,//先将对象转换为字符串再传给后台
			},
			dataType : "json",
			success : function(data) {
				if (data.code=="0") {
					layer.alert("菜单创建成功！", {
						//skin: 'layui-layer-molv' //样式类名
						closeBtn: 0
					}, function(){
						window.location.reload();
					});
				} else {
					layer.alert(data.msg);
				}
			}
		});
	}
	function reduction(res){
		var data = res.menu.entries;
		var obj ={
			menu:{
				button:[]
			}
		}
		for(var i=0;i<data.length;i++){
			var btn = {};
			if(data[i].reply){
				btn = {
					type: 'reply',
					name: data[i].reply.displayText,
					data: data[i].reply.postback.data,
					sub_button: []
				}
			}else if(data[i].menu){
				btn = {
					name: data[i].menu.displayText,
					sub_button: []
				}
				for(var a=0;a<data[i].menu.entries.length;a++){
					var tmenu = {}
					var entrie = data[i].menu.entries[a]
					if(entrie.reply){
						tmenu =  {
							type: 'reply',
							name: data[i].menu.entries[a].reply.displayText,
							data: data[i].menu.entries[a].reply.postback.data
						}
					}else{
						if(entrie.action.dialerAction){
							tmenu =	{
								type: "dialPhoneNumber",
								name: data[i].menu.entries[a].action.displayText,
								phoneNumber:data[i].menu.entries[a].action.dialerAction.dialPhoneNumber.phoneNumber,
								data: data[i].menu.entries[a].action.postback.data
							}
						}else if(entrie.action.urlAction){
							tmenu =	{
								type: "openUrl",
								name: data[i].menu.entries[a].action.displayText,
								url:data[i].menu.entries[a].action.urlAction.openUrl.url,
								data: data[i].menu.entries[a].action.postback.data
							}
						}

					}
					btn.sub_button.push(tmenu);
				}
			}else if(data[i].action){
				if(data[i].action.urlAction){
					btn = {
						type: "openUrl",
						name: data[i].action.displayText,
						data: data[i].action.postback.data,
						url:data[i].action.urlAction.openUrl.url,
						sub_button: []
					}
				}else if(data[i].action.dialerAction){
					btn = {
						type: "dialPhoneNumber",
						name: data[i].action.displayText,
						data: data[i].action.postback.data,
						phoneNumber:data[i].action.dialerAction.dialPhoneNumber.phoneNumber,
						sub_button: []
					}
				}
			}
			obj.menu.button.push(btn)
		}
		//console.log('转回去')
		//console.log(obj);
		//console.log("json :"+JSON.stringify(obj));
		return obj;
	}
});
function getBytes(str){
	str = str.replace(/[^\x00-\xff]/g, '**');//将非ascii码转换为2个ascii码
	return str.length;
}
function isEmpty(obj) {
	if (typeof obj == "undefined" || obj == null || obj == "") {
		return true;
	} else {
		return false;
	}
}