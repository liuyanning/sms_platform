<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/common.jsp" %>
<%@ include file="/common/layui_head.html"%>
<%@ include file="/common/dynamic_data.jsp" %>
<body>
<form class="layui-form" action="" id="subForm"
      onsubmit="return ajaxSubmitForm()" method="post" style="padding: 30px 30px 0 0;">
    <div class="layui-form-item">
        <label class="layui-form-label">标题<font color="red">&nbsp;&nbsp;*</font></label>
        <div class="layui-input-inline" style="width:60%;">
            <input type="text" name="title"  id="title" lay-verify="required"
                   placeholder="标题" autocomplete="off" class="layui-input">
        </div>
    </div>
    <div class="layui-form-item"style="width:80%;">
        <label class="layui-form-label">描述<font color="red">&nbsp;&nbsp;*</font></label>
        <div class="layui-input-block">
            <textarea type="text" name="description" id="description"
                      placeholder="文本内容"  lay-verify="required" class="layui-textarea"></textarea>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">卡片操作</label>
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
        <label class="layui-form-label">卡片封面</label>
        <div class="layui-input-block">
            <button type="button" onclick="selectMedia()" class="layui-btn layui-btn-radius" >
                <i class="layui-icon">&#xe608;</i>
                <span>选择素材</span>
            </button>
        </div>
    </div>
    <div class="layui-form-item">
        <div class="layui-input-block">
            <div class="layui-upload-list">
                <a href="javascript:;" id="mediaShow">
                    <input hidden id="fileId" />
                    <img class="layui-upload-img" id="picHuiXian">
                </a>
            </div>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label"></label>
        <div class="layui-input-block">
            <button class="layui-btn" id="layuiadmin-app-form-submit">提交</button>
        </div>
    </div>
</form>


<%--添加按钮--%>
<div id="dialogId" hidden>
    <form class="layui-form" lay-filter="form" id="dialogFormId" onsubmit="return false;"
          style="padding: 50px 50px 0px 50px;">
        <div class="layui-form-item">
            <div class="layui-input-block">
                <input hidden id="menuTypeId" value="callPhone" >
                <input type="radio" name="menuType" lay-filter="menuFilter" value="callPhone" title="电话拨打" checked>
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

<%@ include file="/common/layui_bottom.jsp"%>

<script >
    var form;
    function creatMenuBt(buttonName,menuType,buttonData) {
        var bt=document.createElement("button");
        bt.type = "button" ;
        bt.className = "layui-btn layui-btn-sm layui-btn-normal";
        bt.innerText = buttonName;
        bt.addEventListener("click",editMenu);
        var typeAttr = document.createAttribute('button-type');
        typeAttr.value=menuType;
        bt.setAttributeNode(typeAttr);
        var valueAttr = document.createAttribute('button-value');
        valueAttr.value=buttonData;
        bt.setAttributeNode(valueAttr);
        $("#btnGroup").append(bt);
    }

    layui.use(['form'], function () {
        form = layui.form
        form.on('radio(menuFilter)', function(data){
            $("#menuTypeId").val(data.value);
            $("input[name='buttonName']").val('');
            $("input[name='buttonData']").val('');
        });
        form.render();
    });

    //清空所有悬浮按钮
    function deleteAllMenu() {
        var index = layer.confirm('确认要清空所有按钮吗？', {
            btn : [ '确定', '取消' ]//按钮
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
            if(i == 3){
                check = "false";
            }
        })
        if(check == "false"){
            layer.msg('卡片内最多只能录入四个事件');
            return false;
        }
        //默认选中新增帧
        var layerOpen = layer.open({
            area: ['650px', '450px'],
            title:'添加卡片操作项',
            type: 1,
            content: $("#dialogId"),
            btn: ['确定'],
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
                creatMenuBt(buttonName,menuType,buttonData);
                layer.close(layerOpen);
            }
        });
    }
    function editMenu() {
        var bt = this;
        var innerText = bt.innerText;
        var buttonType = bt.getAttributeNode("button-type").value;
        var buttonValue = bt.getAttributeNode("button-value").value;
        var editBt = layer.open({
            area: ['650px', '450px'],
            title:'修改卡片操作项',
            type: 1,
            content: $("#dialogId"),
            btn: ['确定'],
            success:function(layero, index) {
                var radios = document.getElementsByName("menuType");
                if("callPhone" == buttonType){
                    radios[0].checked = true;
                }else{
                    radios[1].checked = true;
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
                bt.setAttribute("button-type",menuType);
                bt.setAttribute("button-value",buttonData);
                form.render();
                layer.close(editBt);
            }
        })
    }
    //============ 选择素材 =============
    function selectMedia() {
        var params = 'approve_Status=4';//审核通过
        params += '&showType=showAll';//showImage:显示图片，showAll:显示所有
        layer.open({
            type: 2,
            title: "选择素材",
            content: "/admin/business_preSelectRcsMaterial?"+params,
            area:["750px","450px"],
            maxmin: true,
        });
    }

    //============ 子页面素材传值 | 文件域初始化 =============
    function setMaterial(url,fileType,format,size,materialNo,showType) {
        var picSrc = url;
        if(fileType == "video"){
            picSrc = "/public/admin/images/videoPlayButton.png";
        }
        $("#picHuiXian").attr('src', picSrc);
        $("#picHuiXian").attr('width', "100px");
        $("#picHuiXian").attr('height', "100px");
        $("#fileId").val(materialNo);
        $("#fileId").attr('src', url);
        $("#mediaShow").attr("onclick","mediaShow('"+url+"','"+fileType+"')");
        layer.closeAll();
        form.render();//渲染
    }
    //============展示素材 =============
    function mediaShow(url,fileType) {
        if (fileType=='picture') {
            var content = '<img src="'+url+'" style="width: 600px;height: 350px">';
            layer.open({
                type: 1,
                maxmin: true,
                title: false,
                closeBtn: 1,
                area: ['600px', '350px'],
                shadeClose: true,
                content: content
            });
        }
        if (fileType=='video') {
            layer.open({
                type: 1,
                skin: 'layui-layer-rim', //加上边框
                area: ['600px', '350px'], //宽高
                content: '<video autoplay controls width="100%" height="90%" preload="" ><source src="'+url+'"/></video>'
            })
        }
    }

    //提交
    function ajaxSubmitForm() {
        var title = $("#title").val();
        if (!title){
            layer.msg('请填写卡片标题！')
            return false;
        }
        var description = $("#description").val();
        if (!description){
            layer.msg('请填写卡片描述！')
            return false;
        }
        //按钮
        var buttonArray = new Array();
        $("#btnGroup").find("button").each(function (i,data) {
            var map = {};
            for (var i in data.attributes){
                var att = data.attributes[i];
                if (att.specified && att.name == 'button-type') {
                    map['menuType'] = att.value;
                }
                if (att.specified && att.name == 'button-value') {
                    map['menuValue'] = att.value;
                }
            }
            map['menuName'] = data.innerText;
            buttonArray.push(map);
        })
        //卡片封面
        var picSrc = $("#picHuiXian").attr('src');
        var materialNo = $("#fileId").val();
        var url = $("#fileId").attr('src');
        if(!materialNo){
            layer.msg('卡片封面不能为空！')
            return false;
        }
        parent.setOneCard(title,description,buttonArray,picSrc,materialNo,url);
        return false;
    }
</script>
</body>