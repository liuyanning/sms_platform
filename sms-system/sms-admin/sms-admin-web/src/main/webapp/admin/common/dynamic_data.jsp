<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<script>
    $(function(){
        $("select[id='enterprise_No']").attr('lay-filter','selectEnterpriseUser');
        layui.use(['form'], function () {
            var form = layui.form;
            form.render('select');//渲染
        })
    })
    //企业用户二级联动
    layui.use(['form'], function () {
        var form = layui.form;
        form.on('select(selectEnterpriseUser)', function (data) {
            var enterprise_No = $("select[id='enterprise_No']").val();
            $("#enterprise_User_Id option").remove();//移除所有
            if(!enterprise_No){
                form.render('select');//渲染
                return false;
            }
            $.ajax({
                url: "/admin/enterprise_userList",
                data: {
                    'enterprise_No': enterprise_No,
                    'pagination.pageIndex':1,
                    'pagination.pageSize':50
                },
                dataType:'json',
                success: function (res) {
                    if (res.code == '0') {
                        var dataAray = res.data;
                        var a = "<option value=''>请选择</option>";
                        for (var i = 0; i < dataAray.length; i++) {
                            a+= '<option value="'+dataAray[i].id+'" >'+handleData(dataAray[i].real_Name)+'</option>';
                        }
                        $("#enterprise_User_Id").append(a);
                    }
                    form.render('select');//渲染
                }
            });
        });
    });


</script>