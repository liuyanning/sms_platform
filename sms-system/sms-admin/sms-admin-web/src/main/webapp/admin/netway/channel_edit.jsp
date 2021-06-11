<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/admin/common/common.jsp" %>
<%@ include file="/admin/common/layui_head.html" %>
<body>
<form id="subForm" class="layui-form" action="/admin/netway_editChannel" lay-filter="form"
      style="padding: 20px 30px 0 0;" onsubmit="return ajaxCheck();">
    <input hidden name="id" value="<c:out value="${channel.id}"/>"/>
    <input type="hidden" name="group_Code" value="<c:out value="${channel.group_Code}"/>" class="layui-input">
    <div class="layui-form-item">
        <div class="layui-form-item">
            <label class="layui-form-label">协议类型<font color="red">&nbsp;&nbsp;*</font></label>
            <div class="layui-input-inline" style="width: 300px">
                <ht:herocodeselect sortCode="protocol_type" name="protocol_Type_Code" layVerify="required"
                                   selected="${channel.protocol_Type_Code}"/>
            </div>
            <label class="layui-form-label">通道名称<font color="red">&nbsp;&nbsp;*</font></label>
            <div class="layui-input-inline" style="width: 300px">
                <input type="text" maxlength="512" width="100" name="name" lay-verify="required"
                       value="<c:out value="${channel.name}"/>"
                       autocomplete="off" class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">账号<font color="red">&nbsp;&nbsp;*</font></label>
            <div class="layui-input-inline" style="width: 300px">
                <input type="text" maxlength="64" name="account" autocomplete="off" lay-verify="required" class="layui-input"
                       value="<c:out value="${channel.account}"/>">
            </div>
            <label class="layui-form-label">密码<font color="red">&nbsp;&nbsp;*</font></label>
            <div class="layui-input-inline" style="width: 300px">
                <input type="text" maxlength="64" name="password" autocomplete="off" lay-verify="required" class="layui-input"
                       value="<c:out value="${channel.password}"/>">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">行业类型<font color="red">&nbsp;&nbsp;*</font></label>
            <div class="layui-input-inline" style="width: 300px">
                <ht:herocodeselect sortCode="trade" name="trade_Type_Code" layVerify="required"
                                   selected="${channel.trade_Type_Code}"/>
            </div>
            <label class="layui-form-label">通道来源<font color="red">&nbsp;&nbsp;*</font></label>
            <div class="layui-input-inline" style="width: 300px">
                <ht:herocodeselect sortCode="channel_source" name="channel_Source"  layVerify="required" selected="${channel.channel_Source}"/>
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">IP</label>
            <div class="layui-input-inline" style="width: 300px">
                <input type="text" maxlength="128" name="ip" autocomplete="off" class="layui-input"
                       value="<c:out value="${channel.ip}"/>">
            </div>
            <label class="layui-form-label">端口</label>
            <div class="layui-input-inline" style="width: 300px">
                <input type="text" maxlength="11" name="port" autocomplete="off" class="layui-input"
                       value="<c:out value="${channel.port}"/>">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">连接数</label>
            <div class="layui-input-inline" style="width: 300px">
                <input type="text" maxlength="11" name="max_Concurrent_Total" autocomplete="off" class="layui-input"
                       value="<c:out value="${channel.max_Concurrent_Total}"/>">
            </div>
            <label class="layui-form-label">提交速度</label>
            <div class="layui-input-inline" style="width: 300px">
                <input type="text" maxlength="11" name="submit_Speed" autocomplete="off" class="layui-input"
                       value="<c:out value="${channel.submit_Speed}"/>">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">签名方向<font color="red">&nbsp;&nbsp;*</font></label>
            <div class="layui-input-inline" style="width: 300px">
                <ht:herocodeselect sortCode="signature_type" name="signature_Direction_Code" layVerify="required"
                                   selected="${channel.signature_Direction_Code}"/>
            </div>
            <label class="layui-form-label">签名位置<font color="red">&nbsp;&nbsp;*</font></label>
            <div class="layui-input-inline" style="width: 300px">
                <ht:herocodeselect sortCode="031" name="signature_Position" layVerify="required"
                                   selected="${channel.signature_Position}"/>
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">码号</label>
            <div class="layui-input-inline" style="width: 300px">
                <input type="text" maxlength="56" name="sp_Number" autocomplete="off" class="layui-input"
                       value="<c:out value="${channel.sp_Number}"/>">
            </div>
            <label class="layui-form-label">版本号</label>
            <div class="layui-input-inline" style="width: 300px">
                <input type="text" maxlength="128" name="version" autocomplete="off" class="layui-input"
                       value="<c:out value="${channel.version}"/>">
            </div>
        </div>
        <div class="layui-form-item layui-hide">
            <input type="submit" lay-submit lay-filter="checkParam" id="layuiadmin-app-form-submit" value="确认">
        </div>
    </div>
</form>
<%@ include file="/admin/common/layui_bottom.jsp" %>
</body>
<script type="text/javascript">
    layui.use('form', function(){
        var form = layui.form;
    });

    //校验
    function ajaxCheck() {
        //连接数
        var max_Concurrent_Total = parseInt($("input[name='max_Concurrent_Total']").val());
        //提交速度
        var submit_Speed = parseInt($("input[name='submit_Speed']").val());
        if(max_Concurrent_Total > submit_Speed){
            layer.alert('连接数大于提交速度，请修改', {
                btn: ['关闭'],
                icon: 2,
                skin: 'layer-ext-moon'
            })
            return false;
        }
        var protocol_Type_Code = $("select[name='protocol_Type_Code']").val();
        if(!protocol_Type_Code){
            layer.msg("请选择协议类型")
            return false;
        }
        var name = $("input[name='name']").val();
        if(!name){
            layer.msg("通道名称不能为空");
            return false;
        }
        var account = $("input[name='account']").val();
        if(!account){
            layer.msg("账号不能为空");
            return false;
        }
        var password = $("input[name='password']").val();
        if(!password){
            layer.msg("密码不能为空");
            return false;
        }
        var trade_Type_Code = $("select[name='trade_Type_Code']").val();
        if(!trade_Type_Code){
            layer.msg("请选择行业类型");
            return false;
        }
        var channel_Source = $("select[name='channel_Source']").val();
        if(!channel_Source){
            layer.msg("请选择通道来源");
            return false;
        }
        var signature_Direction_Code = $("select[name='signature_Direction_Code']").val();
        if(!signature_Direction_Code){
            layer.msg("请选择签名方向");
            return false;
        }
        var signature_Position = $("select[name='signature_Position']").val();
        if(!signature_Position){
            layer.msg("请选择签名位置");
            return false;
        }
        //校验分组编码
        $.ajax({
            url: "/admin/netway_channelList",
            data:{
                "account":  $("input[name='account']").val(),
                "password": $("input[name='password']").val(),
                "pagination.pageIndex":1,
                "pagination.pageSize":50
            },
            dataType:'json',
            async:false,
            success: function (res) {
                if(res && res.data && res.data.length > 0){
                    var length = res.data.length;
                    var groupCode = "";
                    var channelName = "";
                    var oldId = ${channel.id};
                    if(length == 1){
                        var id = res.data[0].id;
                        if(id == oldId){
                            ajaxSubmit();//直接提交
                            return false;
                        }
                        groupCode = res.data[0].group_Code;
                        channelName = res.data[0].name;
                    }else{
                        var flag = false;
                        for (var i = 0; i < res.data.length; i++) {
                            var id = res.data[i].id;
                            if(id != oldId){
                                groupCode = res.data[i].group_Code;
                                channelName = res.data[i].name;
                                flag = true;
                                break;
                            }
                        }
                    }
                    if(${not empty channel.group_Code}){
                        var oldGroupCode = ${channel.group_Code};
                        if(groupCode == oldGroupCode){
                            ajaxSubmit();//直接提交
                            return false;
                        }
                    }
                    var msg = "已经存在相同账号密码的通道【"+channelName+"】，是否设置为同一分组编码："+groupCode+"?";
                    msg += "《是：同一上游通道,否：不同上游通道》";
                    layer.confirm(msg, {
                        btn: ['是', '否'] //按钮
                    }, function (index) {
                        $("input[name='group_Code']").val(groupCode);
                        ajaxSubmit();
                        return false;
                    }, function(){
                        ajaxSubmit();
                        return false;
                    });
                }else{
                    ajaxSubmit();
                    return false;
                }
            }
        });
        return false;
    }

    //提交
    function ajaxSubmit(){
        $.ajax({
            url: '/admin/netway_editChannel?'+$("#subForm").serialize(),
            dataType:'json',
            success: function (res) {
                if (res.code != 0) {
                    layer.alert(res.msg, {icon: 2}, function () {
                        layer.closeAll();
                    });
                    return;
                }
                layer.msg("操作成功",function (){
                    parent.layer.closeAll();
                    parent.layui.table.reload('list_table');
                })
            }
        });
        return false;
    }

</script>