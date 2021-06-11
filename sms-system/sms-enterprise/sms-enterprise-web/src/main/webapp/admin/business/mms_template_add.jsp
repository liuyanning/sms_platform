<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/common/common.jsp" %>
<%@ include file="/common/layui_head.html" %>
<form class="layui-form" lay-filter="form" onsubmit="return false;" id="subForm"
      style="padding: 20px 30px 0 40px;">
    <div class="layui-form-item">
        <label class="layui-form-label">模板名称<font color="red">&nbsp;&nbsp;*</font></label>
        <div class="layui-input-inline" >
            <input type="text"  maxlength="128" id="template_Name" placeholder="请输入" autocomplete="off" class="layui-input"  lay-verify="required">
        </div>
        <label class="layui-form-label">行业类型</label>
        <div class="layui-input-inline" >
            <ht:herocodeselect sortCode="trade" id="trade_Type_Code"/>
        </div>
    </div>
    <div class="layui-form-item">
        <div id="materialDivId">
            <fieldset class="layui-elem-field layui-field-title" style="margin-top: 30px;">
                <legend><font color="blue">彩信内容</font></legend>
            </fieldset>
            <div class="layui-form-item">
                <label class="layui-form-label">彩信标题：</label>
                <div class="layui-input-inline" style="width: 65%;">
                    <input placeholder="彩信标题" id="mmsTitle" class="layui-input" />
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label"></label>
                <div class="layui-input-inline" style="width: 65%;">
                    <button type="button" class="layui-btn site-demo-active" data-type="tabAdd"><i class="layui-icon"></i>添加帧</button>
                    <button type="button" class="layui-btn layui-btn-danger site-demo-active" data-type="deleteOne"><i class="layui-icon"></i>删除当前帧</button>
                    <button type="button" class="layui-btn layui-btn-danger site-demo-active" data-type="tabDelete"><i class="layui-icon"></i>清空帧</button>
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label"></label>
                <div id="div_sms_frame">
                    <div class="layui-tab" id="tabFilter" lay-filter="tabFilter">
                        <ul class="layui-tab-title">
                            <li class="layui-this" lay-id="1">第1帧</li>
                        </ul>
                        <div class="layui-tab-content">
                            <div class="layui-tab-item layui-show">
                                <div class="layui-form-item">
                                    <label class="layui-form-label"></label>
                                    <div class="layui-input-inline" style="width: 65%;">
                                        <button type="button" class="layui-btn layui-btn-radius" id="select_material_1" onclick="selectMaterial()" ><i class="layui-icon"></i>选择素材</button>
                                        <button type="button" class="layui-btn layui-btn-radius" id="text_material_1" onclick="textMaterial()" ><i class="layui-icon"></i>输入文字</button>
                                        <input type="hidden" id="fileMap1" />
                                        <div class="layui-upload-list">
                                            <a id="fileAId1" href="javascript:;" ><img class="layui-upload-img" id="picHuiXian1"></a>
                                        </div>
                                    </div>
                                </div>
                                <div class="layui-form-item" id="textDivId1" style="display: none">
                                    <label class="layui-form-label">文字内容：</label>
                                    <div class="layui-input-inline" style="width: 65%;">
                                        <textarea maxlength="2048" placeholder="请输入内容" class="layui-textarea" id="content1" ></textarea>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <fieldset class="layui-elem-field layui-field-title" style="margin-top: 30px;">
        <legend></legend>
    </fieldset>
    <div class="layui-form-item"style="width:80%;">
        <label class="layui-form-label">描述</label>
        <div class="layui-input-block">
            <textarea type="text" maxlength="128" id="description" autocomplete="off" class="layui-textarea"></textarea>
        </div>
    </div>
    <div class="layui-form-item"style="width:80%;">
        <label class="layui-form-label">备注</label>
        <div class="layui-input-block">
            <textarea type="text" maxlength="128" id="remark" autocomplete="off" class="layui-textarea"></textarea>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label"></label>
        <div class="layui-input-block">
            <button class="layui-btn" onclick="clickSend()">提交</button>
        </div>
    </div>
</form>
</body>
<script type="text/javascript">
    var maxSize = '${maxSize}';
    var currentFrame = 1;//当前帧
    var form ;
    var upload ;//文件上传
    var element ;//Tab的切换功能，切换事件监听等，需要依赖element模块
    var $ ;

    layui.use(['element','upload','form'], function(){
        form = layui.form;
        element = layui.element;
        $ = layui.jquery;
        upload = layui.upload;
        //=============== tab标签 触发事件 ===============
        var active = {
            tabAdd: function(){
                var layId = 0;
                $("#tabFilter>ul").children("li").each(function () {
                    layId = $(this).attr("lay-id");
                });
                layId = Number(layId) + 1;
                element.tabAdd('tabFilter', {//新增一个Tab项
                    title: '第'+layId+'帧'
                    ,content: getAddContent(layId)
                    ,id: layId
                })
                //默认选中新增帧
                element.tabChange('tabFilter', layId);
            }
            ,tabDelete: function(){//清空帧
                clearFrame();//清空帧
                initFirstFrame();//初始化第1帧
            }
            ,deleteOne: function(){//删除当前帧
                element.tabDelete('tabFilter',currentFrame);
            }
        };

        //点击触发对应事件
        $('.site-demo-active').on('click', function(){
            var othis = $(this), type = othis.data('type');
            active[type] ? active[type].call(this, othis) : '';
        });

        //Hash地址的定位
        var layid = location.hash.replace(/^#tabFilter=/, '');
        element.tabChange('tabFilter', layid);

        //点击切换
        element.on('tab(tabFilter)', function(elem){
            currentFrame = $(this).attr('lay-id');//当前帧
            location.hash = 'tabFilter='+ $(this).attr('lay-id');
        });
        element.render();//渲染
        upload.render();//渲染
        form.render();//渲染
    });

    //清空帧
    function clearFrame() {
        $("#tabFilter>ul").children("li").each(function () {
            element.tabDelete('tabFilter',$(this).attr("lay-id")); //删除
        });
    }

    //初始化第一帧
    function initFirstFrame() {
        var content = getAddContent(1);
        element.tabAdd('tabFilter', {
            title: '第1帧'
            ,content: content
            ,id: '1'
        })
        //选中第1帧
        element.tabChange('tabFilter', '1');
    }

    //==============获取动态添加帧 的 彩信内容框 =================
    function getAddContent(layId) {
        var result = "";
        result += "<div class=\"layui-form-item\">";
        result += "<label class=\"layui-form-label\"></label>";
        result += "<div class=\"layui-input-inline\" style=\"width: 65%;\">";
        result += "<button type=\"button\" class=\"layui-btn layui-btn-radius\" id=\"select_material_"+layId+"\" onclick=\"selectMaterial()\" ><i class=\"layui-icon\"></i>选择素材</button>";
        result += "<button type=\"button\" class=\"layui-btn layui-btn-radius\" id=\"text_material_"+layId+"\" onclick=\"textMaterial()\" ><i class=\"layui-icon\"></i>输入文字</button>";
        result += "<input type=\"hidden\" id=\"fileMap"+layId+"\" />";
        result += "<div class=\"layui-upload-list\">";
        result += "<a id=\"fileAId"+layId+"\" href=\"javascript:;\" ><img class=\"layui-upload-img\" id=\"picHuiXian"+layId+"\"></a>";
        result += "</div>";
        result += "</div>";
        result += "</div>";
        result += "<div class=\"layui-form-item\" id=\"textDivId"+layId+"\" style=\"display: none\">";
        result += "<label class=\"layui-form-label\">文字内容：</label>";
        result += "<div class=\"layui-input-inline\" style=\"width: 65%;\">";
        result += "<textarea placeholder=\"请输入内容\" class=\"layui-textarea\" id=\"content"+layId+"\" ></textarea>";
        result += "</div>";
        result += "</div>";
        return result;
    }

    //============ 清除文件缓存信息 =============
    function cleanFileMap() {
        var picId = "#picHuiXian"+currentFrame;//当前帧
        $(picId).attr('src', '');//清空预览图
        $(picId).attr('width', 0);
        $(picId).attr('height', 0);
        $("#fileMap"+currentFrame).val('');//清空map信息
        $("#fileAId"+currentFrame).attr("onclick","");
    }

    //============ 选择素材 =============
    function selectMaterial() {
        var params = 'approve_Status=1';//审核通过
        params += '&comeType=mmsTemplate';//页面来源
        layer.open({
            type: 2,
            title: "选择素材",
            content: "/admin/business_preSelectMaterial?"+params,
            area:["800px","400px"],
            maxmin: true,
        });
    }

    //============ 输入文本素材 =============
    function textMaterial() {
        cleanFileMap();//清除当前素材
        $("#textDivId"+currentFrame).attr("style","display:block;");//显示div
    }

    //============ 子页面素材传值 | 文件域初始化 =============
    function setMaterial(url,fileType,size,materialCode) {
        cleanFileMap();
        $("#textDivId"+currentFrame).attr("style","display:none;");//隐藏div
        var picId = "#picHuiXian"+currentFrame;//当前帧
        var picSrc = url;
        if(fileType == "video"){
            picSrc = "/public/images/videoPlayButton.png";
        }else if(fileType == "audio"){
            picSrc = "/public/images/audioPlayButton.png";
        }
        $(picId).attr('src', picSrc);
        $(picId).attr('width', "100px");
        $(picId).attr('height', "100px");
        $("#fileAId"+currentFrame).attr("onclick","fileDetail('"+url+"','"+fileType+"')");
        var m = new Map();
        m['fileSize'] = size;
        m['content'] = materialCode;
        m['type'] = fileType;
        m['url'] = url;
        $("#fileMap"+currentFrame).val(JSON.stringify(m));
        form.render();//渲染
    }

    //============ 查看文件 =============
    function fileDetail(url,fileType) {
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
        if (fileType=='audio') {
            layer.open({
                type: 1,
                skin: 'layui-layer-rim', //加上边框
                area: ['600px', '200px'], //宽高
                content: '<video autoplay controls width="100%" height="90%" preload="" src="'+url+'"></video>'
            })
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

    //=========== 提交 =================
    function clickSend() {
        var template_Name = $("#template_Name").val();//模板名称
        if(!template_Name){
            return layer.msg("请输入模板名称！");
        }
        var trade_Type_Code = $("select[id='trade_Type_Code']").val();//行业类型
        var mmsTitle = $("#mmsTitle").val();//彩信标题
        if(!mmsTitle){
            return layer.msg("请输入彩信标题！");
        }
        var mmsDataList = new Array();
        var totalSize = 0;
        $("#tabFilter>ul").children("li").each(function () {
            var layId = $(this).attr("lay-id");
            var textContent = $("#content"+layId).val();//文字内容
            var fileMap = $("#fileMap"+layId).val();//文件map
            var map = new Map();
            if(fileMap){
                fileMap = eval("("+fileMap+")");
                if(fileMap['fileSize']){
                    totalSize = totalSize + Number(fileMap['fileSize']);
                }
                map['type'] = fileMap['type'];
                map['content'] = fileMap['content'];
                map['url'] = fileMap['url'];
            }else if(textContent){
                map['type'] = "txt";
                map['content'] = textContent;
            }
            if (JSON.stringify(map) != JSON.stringify({})){
                mmsDataList.push(map);
            }
        });
        if(totalSize > maxSize*1024){//实测素材上传存的单位是B
            layer.alert("单条彩信最大上传 "+maxSize+" kb!", {icon: 2});
            return false;
        }
        if(!mmsDataList || mmsDataList.length <= 0){
            layer.alert("请上传彩信素材！", {icon: 2});
            return false;
        }
        var description = $("#description").val();//描述
        var remark = $("#remark").val();//备注
        var jsonMap = new Map();
        var data = JSON.stringify(mmsDataList);
        jsonMap['title'] = mmsTitle;
        jsonMap['data'] = data;
        var loading= parent.layer.load(''); //遮罩层
        $.ajax({
            type: 'post', // 提交方式 get/post
            url: '/admin/business_addMmsTemplate', // 需要提交的 url
            dataType: 'json',
            data: {
                "template_Name":template_Name,
                "trade_Type_Code":trade_Type_Code,
                "description":description,
                "remark":remark,
                "template_Content":JSON.stringify(jsonMap)
            },
            success: function (d) {
                parent.layer.close(loading);
                if(d.code!=0){
                    layer.alert(d.msg, {icon: 2}, function () {
                    });
                    return false;
                }
                layer.alert(d.msg, {icon: 1}, function (index) {
                    clearFrame();//清空帧
                    initFirstFrame();//初始化第1帧
                    $('#subForm')[0].reset();
                    parent.layer.closeAll();
                    parent.layui.table.reload('list_table');
                });
                return false;
            },
            error: function (d) {
                parent.layer.close(loading);
                layer.alert('提交失败', {icon: 2}, function () {
                });
                return false;
            }
        })
        return false;
    }

</script>
