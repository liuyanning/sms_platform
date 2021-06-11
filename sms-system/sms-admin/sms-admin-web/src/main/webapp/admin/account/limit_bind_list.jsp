<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/admin/common/common.jsp" %>
<%@ include file="/admin/common/layui_head.html" %>
<body>
<div class="layui-container">
    <div class="layui-row">
        <div class="layui-col-md6 layui-col-md-offset1">
            <fieldset class="layui-elem-field layui-field-title">
                <legend>权限树扩展</legend>
            </fieldset>
            <div class="layui-form-item">
                <div class="layui-form-label">普通操作</div>
                <div class="layui-form-block">
                    <button type="button" class="layui-btn layui-btn-primary" data-type="checkAll">全选</button>
                    <button type="button" class="layui-btn layui-btn-primary" data-type="uncheckAll">全不选</button>
                    <button type="button" class="layui-btn layui-btn-primary" data-type="showAll">全部展开</button>
                    <button type="button" class="layui-btn layui-btn-primary" data-type="closeAll">全部隐藏</button>
                </div>
            </div>

            <!-- 此扩展能递归渲染一个权限树，点击深层次节点，父级节点中没有被选中的节点会被自动选中，单独点击父节点，子节点会全部 选中/去选中 -->
            <form class="layui-form" action="/admin/account_bindRoleLimit" onsubmit="return false;">
                <input hidden name="ckRoleId" value="<%=request.getParameter("ckIds")%>">
                <div class="layui-form-item">
                    <label class="layui-form-label">选择权限</label>
                    <div class="layui-input-block">
                        <div id="LAY-auth-tree-index"></div>
                    </div>
                </div>
                <input class="layui-hide" type="submit" lay-submit lay-filter="submit" id="layuiadmin-app-form-submit"
                       value="确认">
            </form>
        </div>
    </div>
</div>
</body>
<script>
    layui.config({
        base: '/layuiadmin/extends/',
    }).extend({
        authtree: 'authtree',
        formExt: 'formExt'
    }).use(['jquery', 'authtree', 'formExt', 'layer'], function () {
        var $ = layui.jquery, authtree = layui.authtree, formExt = layui.formExt;
        var treeId = "#LAY-auth-tree-index";
        var roleId = "<%=request.getParameter("ckIds")%>";
        if (null == roleId) {
            parent.layer.msg("缺少必要参数！");
            parent.layer.close(_index);
        }
        // 一般来说，权限数据是异步传递过来的
        $.ajax({
            url: '/admin/account_bindRoleLimitList',
            data: {ckIds: roleId},
            dataType: 'json',
            success: function (res) {
                var trees = authtree.listConvert(res.data.list, {
                    primaryKey: 'id',
                    startPid: 0,
                    parentKey: 'upId',
                    nameKey: 'name',
                    valueKey: 'id',
                    checkedKey: res.data.checkedId
                });
                // 如果后台返回的不是树结构，请使用 authtree.listConvert 转换
                authtree.render(treeId, trees, {
                    inputname: 'ckLimitId[]',
                    layfilter: 'lay-check-auth',
                    autowidth: true,
                });
            }
        });
        // 初始化
        formExt.init();


        var $ = layui.$, active = {
            checkAll: function () {
                authtree.checkAll(treeId);
            },
            uncheckAll: function () {
                authtree.uncheckAll(treeId);
            },
            showAll: function () {
                authtree.showAll(treeId);
            },
            closeAll: function () {
                authtree.closeAll(treeId);
            }
        };
        $('.layui-btn').on('click', function () {
            var type = $(this).data('type');
            active[type] ? active[type].call(this) : '';
        });
    });
</script>
</html>