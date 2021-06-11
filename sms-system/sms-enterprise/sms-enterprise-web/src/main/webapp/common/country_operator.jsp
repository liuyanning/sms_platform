<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<script src="/js/jquery-3.4.1.min.js"></script>
<script src="/layuiadmin/layui/layui.all.js"></script>
<script src="/js/jquery-form.js"></script>
<script>
    $(function(){
        $("select[id='country_Number']").attr('lay-filter','onselect');
        layui.use(['form'], function () {
            var form = layui.form;
            form.render('select');//渲染
        })
    })
    //运营商二级联动
    layui.use(['form'], function () {
        var form = layui.form;
        form.on('select(onselect)', function (data) {
            var countryNumber = $("select[id='country_Number']").val();
            $("#operator option").remove();//移除所有
            if(!countryNumber){
                form.render('select');//渲染
                return false;
            }
            $.ajax({
                url: "/admin/business_operatorListByCountry",
                data: {
                    'countryNumber': countryNumber,
                },
                dataType:'json',
                success: function (res) {
                    if (res.code == '0') {
                        var dataAray = res.data;
                        var a = '<option value="" >请选择</option>';
                        for (var i = 0; i < dataAray.length; i++) {
                            a+= '<option value="'+dataAray[i].route_Name_Code+'" >'+handleData(dataAray[i].route_Name_Code_name)+'</option>';
                        }
                        $("#operator").append(a);
                    }
                    form.render('select');//渲染
                }
            });
        });
    });


</script>