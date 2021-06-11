<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/common/common.jsp" %>
<%@ include file="/common/layui_head.html" %>
<body>
<fieldset class="layui-elem-field layui-field-title" style="margin-top: 3%;">
    <legend>素材列表</legend>
</fieldset>
<div class="layui-collapse" lay-accordion="">

    <div class="layui-colla-item">
        <h2 class="layui-colla-title">图片素材</h2>
        <div class="layui-colla-content layui-show">
            <c:if test="${not empty pictureList}">
                <c:forEach items="${pictureList}" var="file">
                    <div class="layui-card" style="float: left;">
                        <div class="layui-card-header" style="height: 30px"><c:out value="${file.material_Name}"></c:out></div>
                        <div class="layui-card-body" style="width:100px;height: 110px" >
                            <a href="javascript:;" onclick="fileDetail('<c:out value="${file.url}"></c:out>','<c:out value="${file.material_Type}"></c:out>')" ><img width="100" height="100"  src="<c:out value="${file.url}"></c:out>" ></a>
                            <a href="javascript:;" onclick="delMaterial('<c:out value="${file.id}"></c:out>')"><font color="red">删除</font></a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                            <a href="javascript:;" onclick="selectMaterial('<c:out value="${file.url}"></c:out>','<c:out value="${file.material_Type}"></c:out>','<c:out value="${file.format}"></c:out>','<c:out value="${file.size}"></c:out>','<c:out value="${file.material_No}"></c:out>')" ><font color="blue">选中</font></a>
                        </div>
                    </div>
                </c:forEach>
            </c:if>
            <c:if test="${empty pictureList}">
                暂未上传图片素材！
            </c:if>
        </div>
    </div>
    <c:if test="${showType eq 'showAll'}">
        <div class="layui-colla-item" style="clear: both;">
            <h2 class="layui-colla-title">视频素材</h2>
            <div class="layui-colla-content">
                <c:if test="${not empty videoList}">
                    <c:forEach items="${videoList}" var="file">
                        <div class="layui-card" style="float: left;">
                            <div class="layui-card-header" style="height: 30px"><c:out value="${file.material_Name}"></c:out></div>
                            <div class="layui-card-body" style="width:100px;height: 110px" >
                                <a href="javascript:;" onclick="fileDetail('<c:out value="${file.url}"></c:out>','<c:out value="${file.material_Type}"></c:out>')" ><img width="100" height="100"  src="/public/admin/images/videoPlayButton.png" ></a>
                                <a href="javascript:;" onclick="delMaterial('<c:out value="${file.id}"></c:out>')"><font color="red">删除</font></a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                <a href="javascript:;" onclick="selectMaterial('<c:out value="${file.url}"></c:out>','<c:out value="${file.material_Type}"></c:out>','<c:out value="${file.format}"></c:out>','<c:out value="${file.size}"></c:out>','<c:out value="${file.material_No}"></c:out>')" ><font color="blue">选中</font></a>
                            </div>
                        </div>
                    </c:forEach>
                </c:if>
                <c:if test="${empty videoList}">
                    暂未上传视频素材！
                </c:if>
            </div>
        </div>
    </c:if>
</div>
<script>
    var $;
    layui.use(['element', 'layer'], function(){
        var element = layui.element;
        var layer = layui.layer;
        $ = layui.$;
    });
    function selectMaterial(url,fileType,format,size,materialCode){
        layer.confirm('确定要选中此文件吗？', function(index){
            parent.setMaterial(url,fileType,format.toLowerCase(),size,materialCode,'<c:out value="${showType}"></c:out>');
            parent.layer.closeAll();
        });
    }
    function delMaterial(id){
        layer.confirm('确定要删除此文件吗？', function(index){
            $.ajax({
                url: '/admin/business_delRcsMaterialUser',
                type: 'POST',
                data: {ckIds: id},
                dataType:'json',
                success: function (res) {
                    if (res.code == '0') {
                        layer.alert('文件已删除！', {icon: 1}, function (index) {
                            window.location.reload();
                        });
                    } else {
                        layer.alert(res.msg);
                    }
                }
            });
        });
    }

    //============ 查看文件 =============
    function fileDetail(url,fileType) {
        parent.fileDetail(url,fileType);
    }

</script>

</body>
</html>