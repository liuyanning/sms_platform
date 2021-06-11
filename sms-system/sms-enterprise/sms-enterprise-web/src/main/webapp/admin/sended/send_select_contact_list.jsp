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
    <div class="layui-row layui-col-space30">
        <div class="layui-col-sm2">
            <div>
                <div style=" overflow:auto; height:95%;">
                    <div id="treeDemo_m"></div>
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
                                                <input name="phone_No"
                                                       class="layui-input layui-input-sm"/>
                                            </div>
                                        </div>
                                        <div class="layui-inline">
                                            姓名&nbsp;
                                            <div class="layui-inline">
                                                <input name="real_Name"
                                                       class="layui-input layui-input-sm"/>
                                            </div>
                                        </div>
                                        <div class="layui-inline">
                                            <button class="layui-btn layui-btn-sm" type="submit"
                                                    lay-submit="" lay-filter="reload">搜索
                                            </button>
                                        </div>
                                    </form>
                                    <div class="layui-form layui-border-box layui-table-view">
                                        <div class="layui-card-body " style=" overflow:auto; height:80%;">
                                            <table class="layui-hide" id="list_table"
                                                   lay-filter="list_table"></table>
                                            <script type="text/html" id="table-toolbar">
                                                <div class="layui-btn-container">
                                                    <button class="layui-btn
                                                    layuiadmin-btn-useradmin layui-btn-sm"
                                                            onclick="selectContact()" >选择联系人</button>
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
    var table;
    var layer;
    layui.extend({tableExt: '/js/layui-ext/tableExt'}).use(['tableExt'], function () {
        table = layui.tableExt;
        table.render({
            elem: '#list_table',
            url: '/admin/contact_listContact',
            limits:[20,50,100,200,500,1000],
            cols: [[
                {checkbox: true,fixed:'center'},
                {
                    field: 'contactGroup', title: '分组名称',
                    templet: function (d) {
                        return !d.contactGroup ? '---' : handleData(d.contactGroup.group_Name);
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

    layui.use(['tree', 'util'], function () {
        var $ = layui.$;
        layer = layui.layer;
        var tree = layui.tree
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
                    // , showCheckbox: true  //是否显示复选框
                    , click: function (obj) {
                        var data = obj.data;  //获取当前点击的节点数据
                        // var table = layui.tableExt;
                        table.render({
                            url: '/admin/contact_listContact?group_Id=' + data.id,
                            limits:[20,50,100,200,500,1000],
                            cols: [[
                                {checkbox: true},
                                {
                                    field: 'contactGroup', title: '分组名称',
                                    templet: function (d) {
                                        return !d.contactGroup ? '---' : handleData(d.contactGroup.group_Name);
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
                        })
                    }
                });
            }
        })
    });

    //选择联系人
    function selectContact() {
        var checkStatus = table.checkStatus('list_table'); //idTest 即为基础参数 id 对应的值
        // 选择中的参数
        var data = checkStatus.data;
        var dataArray = new Array();
        for (var i in data) {
            dataArray.push(data[i].phone_No)
        }
        layer.confirm('确定选择共'+dataArray.length+'个联系人吗？', function (index) {
            parent.setContactDatas(dataArray.join(','));
            parent.layer.closeAll();
        });
    }
</script>
</body>



