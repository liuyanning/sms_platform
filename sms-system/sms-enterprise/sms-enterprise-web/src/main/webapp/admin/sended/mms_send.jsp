<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/common.jsp"%>
<%@ include file="/common/layui_head.html"%>
<html>
<head>
<meta http-equiv="Content-Type" content="multipart/form-data; charset=utf-8" />
<script src="/js/jquery-3.4.1.min.js"></script>
<script src="/js/jquery-form.js"></script>
</head>
<body>
	<div class="layui-fluid">
		<div class="layui-col-md12">
			<div class="layui-card">
				<div class="layui-card-header"></div>
				<div class="layui-card-body" pad15>
					<form class="layui-form" id="subForm" enctype="multipart/form-data" method="post" >
						<div class="layui-fluid" layoutH="57">
                            <fieldset class="layui-elem-field layui-field-title" style="margin-top: 0px;">
                                <legend><font color="blue">发送号码</font></legend>
                            </fieldset>
							<div class="layui-form-item">
								<label class="layui-form-label">手机号码:</label>
								<div class="layui-input-inline" style="width: 50%;">
									<textarea placeholder="多个号码换行或者逗号分割,最多2W个号码" class="layui-textarea" id="phone_Nos"></textarea>
								</div>
							</div>
							<div class="layui-form-item">
                                <label class="layui-form-label">号码文件：</label>
								<div class="layui-input-inline" id="fileDivId">
                                    <input hidden id="filePath" name="filePath"/>
                                    <button type="button" class="layui-btn" id="uploadExample">
                                        <i class="layui-icon">&#xe67c;</i>
                                        <span id="fileName">上传文件</span>
                                    </button>
								</div>
                                <div class="layui-form-mid layui-word-aux">每个文件最大支持5W个号码换行或者逗号分割。支持文件格式: TXT</div>

                            </div>
<%--							<div class="layui-form-item">--%>
<%--                                <label class="layui-form-label">发送方式：</label>--%>
<%--								<div class="layui-input-inline">--%>
<%--                                    <select name="sendType">--%>
<%--                                        <option value="sendByMaterial" selected>素材发送</option>--%>
<%--                                        <option value="sendByTemplate">模板发送</option>--%>
<%--                                    </select>--%>
<%--								</div>--%>
<%--							</div>--%>
<%--                            <div id="templateDivId" style="display: none">--%>
<%--                                <fieldset class="layui-elem-field layui-field-title" style="margin-top: 30px;">--%>
<%--                                    <legend><font color="blue">请选择彩信模板</font></legend>--%>
<%--                                </fieldset>--%>
<%--                                <div class="layui-form-item">--%>
<%--                                    <label class="layui-form-label">彩信模板：</label>--%>
<%--                                    <div class="layui-input-inline">--%>
<%--                                        <ht:herommstemplatetag  enterpriseNo="${ADMIN_USER.enterprise_No}"--%>
<%--                                                                enterpriseUserId="${ADMIN_USER.id}"--%>
<%--                                                                name="mmsTemplateId" />--%>
<%--                                    </div>--%>
<%--                                </div>--%>
<%--                            </div>--%>
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
                                                            <%--                                                        <button type="button" class="layui-btn layui-btn-radius layui-btn-normal" id="upload_picture_1" ><i class="layui-icon"></i>上传图片</button>--%>
                                                            <%--                                                        <button type="button" class="layui-btn layui-btn-radius layui-btn-normal" id="upload_video_1" ><i class="layui-icon"></i>上传视频</button>--%>
                                                            <%--                                                        <button type="button" class="layui-btn layui-btn-radius layui-btn-normal" id="upload_audio_1" ><i class="layui-icon"></i>上传音频</button>--%>
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
                                                            <textarea placeholder="请输入内容" class="layui-textarea" id="content1" ></textarea>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
							<div class="layui-form-item">
								<label class="layui-form-label"></label>
								<div class="layui-form-mid layui-word-aux">签名: ${ADMIN_USER.suffix}</div>
							</div>
						</div>
					</form>
                    <div class="layui-form-item">
                        <label class="layui-form-label" style="width: 8%;"></label>
                        <div class="layui-input-block">
                            <button class="layui-btn" onclick="clickSend()">发&nbsp;&nbsp;&nbsp;&nbsp;送</button>
                        </div>
                    </div>
                </div>
			</div>
		</div>
	</div>
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
        var layerload;
        var uploadInst = upload.render({
            elem: '#uploadExample' //绑定元素
            ,size: 1024*2
            ,url: '/admin/sended_uploadSendFile' //上传接口
            ,accept: 'file'
            ,before: function(obj){//参数
                layerload= layer.load(''); //遮罩层
            }
            ,done: function(res){
                layer.close(layerload);
                if (!$("#filePath").val()){
                    $("#fileDivId").after('<br class="append"/><br class="append"/><label class="layui-form-label"></label>');
                }
                if(res.code == '0'){
                    $("#filePath").val(res.data.realPath);
                    $("#fileName").html('【'+res.data.fileName+'】（'+res.data.mobileCount+'个号码）');
                    var html = '您上传了文件'+res.data.fileName+',号码：'+res.data.mobileCount+'个';
                    layer.msg(html);
                }else{
                    layer.msg(res.msg);
                }
            }
            ,error: function(){
                layer.close(layerload);
                layer.alert('上传失败');
            }
        });
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
                //渲染上传框
                renderUpload(layId);
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
        renderUpload(1);//初始化彩信上传框
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
        //渲染上传框
        renderUpload(1);
    }

    //==============获取动态添加帧 的 彩信内容框 =================
    function getAddContent(layId) {
        var result = "";
        result += "<div class=\"layui-form-item\">";
        result += "<label class=\"layui-form-label\"></label>";
        result += "<div class=\"layui-input-inline\" style=\"width: 65%;\">";
        // result += "<button type=\"button\" class=\"layui-btn layui-btn-radius layui-btn-normal\" id=\"upload_picture_"+layId+"\" ><i class=\"layui-icon\"></i>上传图片</button>";
        // result += "<button type=\"button\" class=\"layui-btn layui-btn-radius layui-btn-normal\" id=\"upload_video_"+layId+"\" ><i class=\"layui-icon\"></i>上传视频</button>";
        // result += "<button type=\"button\" class=\"layui-btn layui-btn-radius layui-btn-normal\" id=\"upload_audio_"+layId+"\" ><i class=\"layui-icon\"></i>上传音频</button>";
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

    //=============== 渲染上传框 ============
    function renderUpload(layId) {
        var elemPicture = "#upload_picture_"+layId;
        var elemAudio = "#upload_audio_"+layId;
        var elemVideo = "#upload_video_"+layId;
        var uploadUrl = "/admin/sended_uploadFile";
        render(layId,elemPicture,uploadUrl,maxSize,'images','image/*');//图片
        render(layId,elemAudio,uploadUrl,maxSize,'audio','audio/*');//音频
        render(layId,elemVideo,uploadUrl,maxSize,'video','video/*');//视频
    }

    //======== 渲染 ==============
    function render(layId,elem,uploadUrl,maxSize,accept,acceptMime) {
        upload.render({
            elem: elem  //绑定id
            ,accept: accept  //文件类型
            ,field: 'uploadFile'  //字段名
            ,acceptMime: acceptMime  //打开文件 只展示此类型
            ,size: maxSize  //限制大小 kb。不支持ie8/9
            ,url: uploadUrl  //上传url
            ,method: 'post'
            ,done: function(res){
                uploadDone(res,layId,accept);
            }
            ,error: function(){
                layer.msg('上传失败', {icon: 2, time: 2000});
            }
        })
    }

    //============ 文件上传 回调 =============
    function uploadDone(res,layId,fileType) {
        if(res.code != 0){
            return layer.msg('上传失败');
        }
        if(res.data){
            var fileUrl = res.data.url;
            fileUrl = fileUrl.replace(/\\/g,"/");
            if(fileType == "images"){//图片
                fileType = "picture";
            }
            setMaterial(fileUrl,fileType,res.data.size,res.data.format);//文件域初始化
        }
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
        params += '&comeType=mmsSend';//页面来源
        layer.open({
            type: 2,
            title: "选择素材",
            content: "/admin/business_preSelectMaterial?"+params,
            area:["900px","500px"],
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
        $("#fileMap"+currentFrame).val(JSON.stringify(m));
        form.render();//渲染
    }

    //============ 查看文件 =============
    function fileDetail(url,fileType) {
        if (fileType=='picture') {
            var content = '<img src="'+url+'" style="width: 700px;height: auto">';
            layer.open({
                type: 1,
                maxmin: true,
                title: false,
                closeBtn: 1,
                area: ['700px', '500px'],
                shadeClose: true,
                content: content
            });
        }
        if (fileType=='audio') {
            layer.open({
                type: 1,
                skin: 'layui-layer-rim', //加上边框
                area: ['500px', '200px'], //宽高
                content: '<video autoplay controls width="100%" height="90%" preload="" src="'+url+'"></video>'
            })
        }
        if (fileType=='video') {
            layer.open({
                type: 1,
                skin: 'layui-layer-rim', //加上边框
                area: ['700px', '500px'], //宽高
                content: '<video autoplay controls width="100%" height="90%" preload="" ><source src="'+url+'"/></video>'
            })
        }
    }

    //=========== 提交 =================
    function clickSend() {
        var moblieFile = $("#filePath").val();//号码文件
        var phone_Nos = $("#phone_Nos").val();//号码输入框
        if(!moblieFile && !phone_Nos){
            return layer.msg("请输入发送号码！");
        }
        var mmsTitle = $("#mmsTitle").val();//彩信标题
        if(!mmsTitle){
            return layer.msg("彩信标题不能为空！");
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
        var jsonMap = new Map();
        var data = JSON.stringify(mmsDataList);
        jsonMap['title'] = mmsTitle;
        jsonMap['data'] = data;
        var loading= layer.load(''); //遮罩层
        $("#subForm").ajaxSubmit({
            type: 'post', // 提交方式 get/post
            url: '/admin/sended_sendMMS', // 需要提交的 url
            dataType: 'json',
            data: {
                "moblieFile":moblieFile,
                "phone_Nos":phone_Nos,
                "content":JSON.stringify(jsonMap)
            },
            contentType : "application/json; charset=utf-8",
            success: function (d) {
                layer.close(loading);
                if(d.code!=0){
                    layer.alert(d.msg, {icon: 2}, function () {
                        layer.closeAll();
                    });
                    return;
                }
                layer.alert(d.msg, {icon: 1}, function (index) {
                    clearFrame();//清空帧
                    initFirstFrame();//初始化第1帧
                    $('#subForm')[0].reset();
                    $("#fileName").html('上传文件');
                    layer.closeAll();
                });
            },
            error: function (d) {
                layer.close(loading);
                layer.msg('提交失败', {icon: 2, time: 2000}, function () {
                    layer.closeAll();
                });
            }
        })
        return false;
    }

    // $(function(){
    //     $("select[name='sendType']").attr('lay-filter','onselect');
    //     layui.use(['form'], function () {
    //         var form = layui.form;
    //         form.on('select(onselect)', function (data) {
    //             var sendType = $("select[name='sendType']").val();
    //             if("sendByMaterial" == sendType){//选择素材
    //                 $("#materialDivId").attr("style","display:block;");//显示div
    //                 $("#templateDivId").attr("style","display:none;");//隐藏div
    //             }else if("sendByTemplate" == sendType){//选择模板
    //                 $("#templateDivId").attr("style","display:block;");//显示div
    //                 $("#materialDivId").attr("style","display:none;");//隐藏div
    //             }
    //         });
    //         form.render('select');//渲染
    //     })
    // })
</script>
</html>
