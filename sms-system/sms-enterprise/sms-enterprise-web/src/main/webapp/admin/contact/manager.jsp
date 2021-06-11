<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/common/common.jsp" %>
<%@ include file="/common/layui_head.html" %>
<style>
    .layui-table-cell .layui-form-checkbox[lay-skin="primary"]{
        top: 50%;
        transform: translateY(-50%);
    }
</style>
<body class="layui-layout-body">
<div class="layui-fluid">
    <div class="layui-row layui-col-space10">
        <div class="layui-col-sm2">
            <div class="layui-card">
                <div class="layui-card-header" style=" overflow:auto; height:95%;">
                    <div class="layui-btn-group">
                        <button type="button" class="layui-btn layui-btn-sm" id="addGroup">增加</button>
                        <button type="button" class="layui-btn layui-btn-sm" id="editGroup">编辑</button>
                        <button type="button" class="layui-btn layui-btn-sm" id="removeGroup" onclick="return false;">
                            删除
                        </button>
                    </div>
                    <div id="treeDemo_m" style="margin-left: -20px"></div>
                </div>

            </div>
        </div>
        <div class="layui-col-sm10">
            <div class="layui-card">
                <div class="layui-card-body">

                    <div class="layui-fluid">
                        <div class="layui-row layui-col-space15">
                            <div class="layui-col-md12">
                                <div class="layui-card">
                                    <form class="layui-form layui-card-header layuiadmin-card-header-auto"
                                          onsubmit="return false;">
                                        <div class="layui-inline">
                                            手机号码&nbsp;
                                            <div class="layui-inline">
                                                <input name="phone_No" class="layui-input layui-input-sm"/>
                                            </div>
                                        </div>
                                        &nbsp;&nbsp;
                                        <div class="layui-inline">
                                            姓名&nbsp;
                                            <div class="layui-inline">
                                                <input name="real_Name" class="layui-input layui-input-sm"/>
                                            </div>
                                        </div>
                                        &nbsp;&nbsp;
                                        <div class="layui-inline">
                                            <button class="layui-btn layui-btn-sm" type="submit" lay-submit=""
                                                    lay-filter="reload">搜索
                                            </button>
                                        </div>
                                    </form>
                                    <div class="layui-form layui-border-box layui-table-view">
                                        <div class="layui-card-body "  style=" overflow:auto; height:80%;">
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
                </div>
            </div>
        </div>
    </div>
</div>
</body>
<script>
    layui.extend({tableExt: '/js/layui-ext/tableExt'}).use(['tableExt'], function () {
        var table = layui.tableExt;
        table.render({
            elem: '#list_table',
            url: '/admin/contact_listContact',
            cols: [[
                {checkbox: true, width: 50},
                {
                    field: 'contactGroup', title: '分组名称',
                    templet: function (d) {
                        return !d.contactGroup?'---':handleData(d.contactGroup.group_Name);
                    }
                },
                {field: 'phone_No', title: '手机号码'},
                {field: 'real_Name', title: '姓名'},
                {field: 'company', title: '单位'},
                {field: 'position', title: '职务'},
                {field: 'gender', title: '性别'},
                {field: 'birthday', title: '生日'},
                {field: 'address', title: '地址'}
            ]]
        });


    });
</script>

</div>
</div>
</div>
</div>
</div>

<div style="display: none;" id="addGroupName" class="change-pas-box">
    <form class="layui-form" enctype="multipart/form-data" method="post">
        <div class="layui-form-item">
            <label class="layui-form-label">通讯录名称:<font color="red">&nbsp;&nbsp;*</font></label>
            <div class="layui-input-inline">
                <input type="tel" id="newName" lay-verify="required" autocomplete="off" class="layui-input">
            </div>
        </div>
    </form>
</div>
<div id="phoneNoResultId" hidden >
    <div  class="layui-form-item">
        <form class="layui-form" onsubmit="return false;" >
            <div class="layui-form-item">
                <label class="layui-form-label"></label>
                <div class="layui-input-inline" >
                </div>
            </div>
            <h2 align="center"><font color="red" id="title" ></font></h2>
            <div class="layui-form-item">
                <label class="layui-form-label"></label>
                <div class="layui-input-inline" >
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">失败号码</label>
                <div class="layui-input-block" style="width: 50%">
                    <textarea type="text" id="areaId" class="layui-textarea"></textarea>
                </div>
            </div>
        </form>
    </div>
</div>

<script>

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
                var treeData = '[{"title":"全部分组","id":"","spread":"true","children":[';
                if (data.length > 0) {
                    for (var i = 0; i < data.length; i++) {
                        if (i != (data.length - 1)) {
                            treeData += '{"title":"' + data[i].group_Name + '","id":"' + data[i].id + '"},';
                        } else {
                            treeData += '{"title":"' + data[i].group_Name + '","id":"' + data[i].id + '"}';
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
                    , click: function (obj) {
                        var data = obj.data;  //获取当前点击的节点数据
                        var table = layui.tableExt;
                        table.render({
                            url: '/admin/contact_listContact?group_Id=' + data.id,
                            cols: [[
                                {checkbox: true},
                                {field: 'contactGroup', title: '分组名称',
                                    templet: function (d) {
                                        return !d.contactGroup?'---':handleData(d.contactGroup.group_Name);
                                    }
                                },
                                {field: 'phone_No', title: '手机号码'},
                                {field: 'real_Name', title: '姓名'},
                                {field: 'company', title: '单位'},
                                {field: 'position', title: '职务'},
                                {field: 'gender', title: '性别'},
                                {field: 'birthday', title: '生日'},
                                {field: 'address', title: '地址'}
                            ]]
                            , done: function () {
                                var groupId = data.id;
                                var addUrl = "/admin/contact/contact_add.jsp?group_Id=" + groupId;
                                var addValue = '{"type":"dialog","url":"' + addUrl + '","width":"550","height":"500"}';
                                $("#addButton").attr("lay-event", addValue);

                                var editUrl = "/admin/contact_preEditContact?group_Id=" + groupId;
                                var editvalue = '{"type":"dialogTodo","url":"' + editUrl + '","width":"550","height":"500"}';
                                $("#editButton").attr("lay-event", editvalue);

                                var exportUrl = "/admin/contact_exportContactList?group_Id=" + groupId;
                                var exportValue = '{"type":"ajaxTodo","url":"' + exportUrl + '"}';
                                $("#exportButton").attr("lay-event", exportValue);

                                var imporeUrl = "/admin/contact/contact_import.jsp?group_Id=" + groupId;
                                var imporeValue = '{"type":"dialog","url":"' + imporeUrl + '","width":"550","height":"400"}';
                                $("#imporeButton").attr("lay-event", imporeValue);
                            }
                        })
                    }
                });
            }
        })
    });
    layui.use('layer', function () {
        var $ = layui.jquery,
            layer = layui.layer,
            tree = layui.tree;
        $(document).on('click', '#addGroup', function () {
            layer.open({
                type: 1,
                btnAlign: 'c',
                area: ['400px', '150px'],
                title: '新增',
                content: $("#addGroupName"),
                btn: ['提交', '取消'],
                btn1: function () {
                    var newName = $("#newName").val();
                    if (!beforeRename(newName)) {
                        addContactGroup(newName)
                    }
                },
                btn2: function () {
                }
            });
        });
        $(document).on('click', '#editGroup', function () {
            var id = $(".layui-tree-lineExtend .layui-form-checked").parent().find("input").attr("value");
            var oldName = $(".layui-tree-lineExtend .layui-form-checked").parent().find(".layui-tree-txt").html();
            $("#newName").val(oldName);
            layer.open({
                type: 1,
                btnAlign: 'c',
                area: ['400px', '150px'],
                title: '编辑',
                content: $("#addGroupName"),
                btn: ['提交', '取消'],
                btn1: function () {
                    var newName = $("#newName").val();
                    if (!beforeRename(newName)) {
                        editContactGroup(id, newName)
                    }

                },
                btn2: function () {
                }
            });
        });
        $(document).on('click', '#removeGroup', function () {
            var id = $(".layui-tree-lineExtend .layui-form-checked").parent().find("input").attr("value");
            var oldName = $(".layui-tree-lineExtend .layui-form-checked").parent().find(".layui-tree-txt").html();
            $("#newName").val(oldName);
            layer.open({
                type: 1,
                btnAlign: 'c',
                area: ['400px', '150px'],
                title: '删除',
                content: $("#addGroupName"),
                btn: ['删除', '取消'],
                btn1: function () {
                    layer.confirm('确定删除该通讯录及下属的联系人吗？', function (index) {
                        removeContactGroup(id)
                    });
                },
                btn2: function () {
                }
            });
        });
    });


    function beforeRename(newName) {
        var $ = layui.$;
        if (newName.length == 0) {
            layer.alert("通讯录名称不能为空.");
            return false;
        }
        $.ajax({
            type: "POST",
            async: false,
            url: "/admin/contact_exsitsContactGroup",
            dataType: "json",
            data: {
                "group_Name": newName
            },
            success: function (data) {
                return data;
            }
        });

    };

    function addContactGroup(newName) {
        var $ = layui.$;
        $.ajax({
            type: "POST",
            url: "/admin/contact_addContactGroup",
            dataType: "json",
            data: {
                "group_Name": newName
            },
            success: function (data) {
                layer.alert('添加成功', {icon: 1}, function (index) {
                    $("#addGroupName").val('');
                    window.location.reload();
                });
            }
        });
    }

    function editContactGroup(id, newName) {
        var $ = layui.$;
        $.ajax({
            type: "POST",
            url: "/admin/contact_editContactGroup",
            dataType: "json",
            data: {
                "id": id,
                "group_Name": newName
            },
            success: function (data) {
                layer.alert('修改成功', {icon: 1}, function (index) {
                    $("#addGroupName").val('');
                    window.location.reload();
                });
            }
        });
    }

    function removeContactGroup(id) {
        var $ = layui.$;
        $.ajax({
            type: "POST",
            url: "/admin/contact_batchDeleteContactGroup",
            dataType: "json",
            data: {
                "ckIds": id
            },
            success: function (data) {
                layer.alert('删除成功', {icon: 1}, function (index) {
                    window.location.reload();
                });
            }
        });
    }

    function contactAddClick(obj) {
        obj.href = obj.href + '?group_Id=' + '<c:out value="${contactExt.group_Id}" />';
    }

    //联系人导入结果
    function openUploadReslut(successTotal,failTotal,failList) {
        $("#title").html('导入成功：'+ successTotal +' 条，导入失败：'+ failTotal +' 条');
        if(failList.length > 0){
            var area = $("#areaId");//初始值
            for (var i = 0; i < failList.length; i++) {
                area.append(failList[i]);
                area.append("\n");
            }
        }
        layer.open({
            area: ['550px', '450px'],
            title:'号码信息',
            type: 1,
            content: $("#phoneNoResultId"),
            btn: ['确定'],
            cancel: function(index, layero){
                $("#areaId").empty();//清空
                layer.close(index)
                layer.closeAll();
                layui.table.reload('list_table', {page: {curr: 1}});
                return false;
            },
            yes: function(index, layero){
                $("#areaId").empty();//清空
                layer.close(index)
                layer.closeAll();
                layui.table.reload('list_table', {page: {curr: 1}});
                return false;
            }
        })
    }
</script>
</body>



