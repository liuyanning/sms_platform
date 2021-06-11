<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/common/common.jsp" %>
<%@ include file="/common/layui_head.html" %>
<meta name="renderer" content="webkit">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
<style type="text/css">
    td div.layui-table-cell{
        height:100px;
        line-height: 100px;
        position: relative;
        text-overflow: ellipsis;
        white-space: nowrap;
        box-sizing: border-box;
        padding: 0px 15px;
        overflow: hidden;
        top : 50%;
    }
</style>

<body>

<div class="layui-fluid">
    <div class="layui-row layui-col-space15">
        <div class="layui-col-md12">
            <div class="layui-card">
                <form class="layui-form layui-card-header layuiadmin-card-header-auto" onsubmit="return false;">
                    <table>
                        <tr>
                            <td class="layui-inline">&nbsp;&nbsp;&nbsp;&nbsp;
                                类型：
                            <td class="layui-inline">
                                <ht:herocodeselect sortCode="material_type_code" name="material_Type_Code"/>
                            </td>
                            </td>
                            <td class="layui-inline">&nbsp;&nbsp;&nbsp;&nbsp;</td>
                            <td class="layui-inline">
                                <button class="layui-btn layui-btn-sm" type="submit" lay-submit="" lay-filter="reload">搜索
                                </button>
                            </td>
                        </tr>
                    </table>
                </form>
                <div class="layui-form layui-border-box layui-table-view">
                    <div class="layui-card-body">
                        <table class="layui-hide" id="list_table" lay-filter="list_table"></table>
                        <script type="text/html" id="table-toolbar">
                            <div class="layui-btn-container">
                                <%@include file="/common/button_action_list.jsp" %>
                            </div>
                        </script>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

</body>
<script>
    layui.extend({tableExt: '/js/layui-ext/tableExt'}).use(['tableExt'], function () {
        var table = layui.tableExt;
        $ = layui.$;
        table.render({
            elem: '#list_table',
            url: '/admin/business_materialList',
            cols: [[
                {checkbox: true},
                {field: 'material_Name', title: '素材名称'},
                {field: 'material_Code', title: '编号'},
                {field: 'material_Type_Code_name', title: '类型', templet: function (d) {
                        return handleData(d.material_Type_Code_name);
                    }
                },
                {field: 'format', title: '格式'},
                {field: 'size', title: '大小', templet: function (d) {
                        var str=Number(d.size/1024).toFixed(2);
                        return str+="KB";
                    }
                },
                {title: '素材', width: 150, event: 'setSign', style:'cursor: pointer;', templet:function(d){
                        if(d.material_Type_Code=='picture'){
                            return '<div style="text-align: center"><img style=\"height:100px;width:100px;\" src=\"'+d.url+'\"></div>';
                        }else if(d.material_Type_Code=='video'){
                            if (d.format != '3GP') {
                                return '<div style="text-align: center"><img style=\"height:100px;width:120px;\" src=\"/public/images/videoPlayButton.png\"></div>';
                            }else {
                                return d.url;
                            }
                        }else if(d.material_Type_Code=='audio'){
                            return '<div style="text-align: center"><img style=\"height:80px;width:80px;\" src=\"/public/images/audioPlayButton.png\"></div>';
                        }
                    }},
                {field: 'approve_Status_name', title: '审核状态', width: 100},
                {field: 'create_Date', title: '创建日期'}
            ]]
        });
        //监听单元格事件
        table.on('tool(list_table)', function(obj){
            var data = obj.data;
            var content = '';
            if (data.material_Type_Code=='picture') {
                content = '<img src="'+data.url+'"  style="width: 700px;height: auto">';
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
            if (data.material_Type_Code=='audio') {
                layer.open({
                    type: 1,
                    skin: 'layui-layer-rim', //加上边框
                    area: ['500px', '200px'], //宽高
                    content: '<video autoplay controls width="100%" height="90%" preload="" src="'+data.url+'"></video>'
                })
            }
            if (data.material_Type_Code=='video') {
                if (data.format != '3GP') {
                    layer.open({
                        type: 1,
                        skin: 'layui-layer-rim', //加上边框
                        area: ['700px', '500px'], //宽高
                        content: '<video autoplay controls width="100%" height="90%" preload="" ><source src="'+data.url+'"/>"</video>'
                    })
                }
            }
        });

    });

</script>