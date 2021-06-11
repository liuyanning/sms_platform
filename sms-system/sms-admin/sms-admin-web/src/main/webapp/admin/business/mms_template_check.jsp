<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/admin/common/common.jsp" %>
<%@ include file="/admin/common/layui_head.html" %>
<%@ include file="/admin/common/dynamic_data.jsp" %>
<form class="layui-form" lay-filter="form" onsubmit="return false;"
      style="padding: 20px 30px 0 40px;">
    <input hidden name="id" id="id" value="<c:out value="${mmsTemplate.id}"/>" />
    <div class="layui-form-item">
        <label class="layui-form-label">模板名称<font color="red">&nbsp;&nbsp;*</font></label>
        <div class="layui-input-inline" >
            <input type="text" value="<c:out value="${mmsTemplate.template_Name}"/>"
                 autocomplete="off" class="layui-input" readonly />
        </div>

        <label class="layui-form-label">行业类型<font color="red">&nbsp;&nbsp;*</font></label>
        <div class="layui-input-inline" >
            <ht:herocodeselect sortCode="trade" selected="${mmsTemplate.trade_Type_Code}"
                               disabled="disabled" id="trade_Type_Code"/>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">企业名称<font color="red">&nbsp;&nbsp;*</font></label>
        <div class="layui-input-inline" >
            <ht:heroenterpriseselect disabled="disabled" selected="${mmsTemplate.enterprise_No}"/>
        </div>
        <label class="layui-form-label">企业用户<font color="red">&nbsp;&nbsp;*</font></label>
        <div class="layui-input-inline" >
            <ht:herocustomdataselect dataSourceType="allEnterpriseUsers"
                    disabled="disabled" selected="${mmsTemplate.enterprise_User_Id}"/>
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
                    <input maxlength="128" placeholder="彩信标题" value="<c:out value="${mmsTitle}"/>"
                           readonly class="layui-input" />
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label"></label>
                <div id="div_sms_frame">
                    <div class="layui-tab" id="tabFilter" lay-filter="tabFilter">
                        <ul class="layui-tab-title">
                            <li class="layui-this" lay-id="0">第0帧</li>
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
            <textarea type="text" readonly autocomplete="off" class="layui-textarea">${mmsTemplate.description}</textarea>
        </div>
    </div>
    <div class="layui-form-item"style="width:80%;">
        <label class="layui-form-label">备注</label>
        <div class="layui-input-block">
            <textarea type="text" readonly autocomplete="off" class="layui-textarea">${mmsTemplate.remark}</textarea>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label" >通道模板编号<font color="red">&nbsp;&nbsp;*</font></label>
        <div class="layui-input-inline" >
            <input type="text" maxlength="255" id="channel_Template_Code" value="<c:out value="${mmsTemplate.channel_Template_Code}"/>"
                   placeholder="请输入通道模板编号" autocomplete="off" class="layui-input" >
        </div>
        <label class="layui-form-label" >审核<font color="red">&nbsp;&nbsp;*</font></label>
        <div class="layui-input-inline" >
            <ht:herocodeselect sortCode="templateCheckStatus" selected="${mmsTemplate.approve_Status}" name="approve_Status"/>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label"></label>
        <div class="layui-input-block">
            <button class="layui-btn" onclick="clickBtn()">提交审核</button>
        </div>
    </div>
</form>
</body>
<script type="text/javascript">
    var currentFrame = 1;//当前帧
    var form ;
    var element ;//Tab的切换功能，切换事件监听等，需要依赖element模块
    var $ ;
    layui.use(['element'], function(){
        element = layui.element;
        $ = layui.jquery;

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
    });
    //============ 文件下载 =============
    function downloadFile(url) {
        var aDom = document.createElement('a');//创建标签
        var evt = document.createEvent('HTMLEvents');//创建事件
        evt.initEvent('click', true, true);//初始化事件，绑定点击事件，不冒泡，不阻止浏览器默认行为
        var s = url.substring(url.lastIndexOf("/") + 1);
        aDom.download = s;
        aDom.href = encodeURI(url);//对地址进行编码
        aDom.dispatchEvent(evt);//触发事件
        aDom.click();
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
    function getAddContent(url,type,content) {
        var result = "";
        if(type == 'picture' || type == 'video' || type == 'audio'){
            var imgUrl = url;
            if(type == 'video'){
                imgUrl = "/public/images/videoPlayButton.png";
            }else if(type == 'audio'){
                imgUrl = "/public/images/audioPlayButton.png";
            }
            result += "<div class=\"layui-form-item\">";
            result += "<label class=\"layui-form-label\"></label>";
            result += "<div class=\"layui-input-inline\" style=\"width: 65%;\">";
            result += "<div class=\"layui-upload-list\">";
            result += "<a href=\"javascript:;\" onclick=\"fileDetail('"+url+"','"+type+"')\" ><img class=\"layui-upload-img\" width='100px' height='100px' src='"+imgUrl+"' ></a>";
            result += "<br/>";
            result += "<a href=\"javascript:;\" onclick=\"downloadFile('"+url+"')\" class=\"layui-btn layuiadmin-btn-useradmin layui-btn-sm\">下载</a>";
            result += "</div>";
            result += "</div>";
            result += "</div>";
        }else if(type == 'txt'){
            result += "<div class=\"layui-form-item\" >";
            result += "<label class=\"layui-form-label\">文字内容：</label>";
            result += "<div class=\"layui-input-inline\" style=\"width: 65%;\">";
            result += "<textarea placeholder=\"请输入内容\" readonly class=\"layui-textarea\">"+content+"</textarea>";
            result += "</div>";
            result += "</div>";
        }
        return result;
    }

    //页面加载完成
    $(function(){
        var last = 0;
        <c:forEach items="${fileList}" var="file" varStatus="status" >
            var layId = '${status.index}';
            last = layId;
            var content = getAddContent('${file.url}','${file.type}','${file.content}');
            element.tabAdd('tabFilter', {//新增一个Tab项
                title: '第'+layId +'帧'
                ,content: content
                ,id:layId
            })
        </c:forEach>
        element.tabAdd('tabFilter', {//新增一个Tab项
            title: '第'+ ++last +'帧'
            ,content: ''
            ,id:++last
        })
        element.tabDelete('tabFilter','0');//删除第0帧
        element.tabChange('tabFilter', 1);//定位第1帧
        element.render();//渲染
    })

    //=========== 提交 =================
    function clickBtn() {
        var approve_Status = $("select[name='approve_Status']").val();//审核状态
        if(!approve_Status){
            return layer.msg("请选择审核状态！");
        }
        var channel_Template_Code = $("#channel_Template_Code").val();//通道模板编号
        if(approve_Status == '1' && !channel_Template_Code){
            return layer.msg("请输入通道模板编号！");
        }
        var loading= parent.layer.load(''); //遮罩层
        $.ajax({
            type: 'post', // 提交方式 get/post
            url: '/admin/business_checkMmsTemplate', // 需要提交的 url
            dataType: 'json',
            data: {
                "id":'<c:out value="${mmsTemplate.id}"/>',
                "approve_Status":approve_Status,
                "channel_Template_Code":channel_Template_Code,
            },
            success: function (d) {
                parent.layer.close(loading);
                if(d.code!=0){
                    layer.alert(d.msg, {icon: 2}, function () {
                    });
                    return false;
                }
                layer.alert(d.msg, {icon: 1}, function (index) {
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
