var sms = {
    ajaxSubmitForm : function(options) {
        var loading= parent.layer.load('');
        $("#importFileForm").ajaxSubmit({
            type : options.type,
            dataType : options.dataType,
            success : function(d) {
                parent.layer.close(loading);
                if (d.code == '0') {
                    layer.alert('导入成功', function() {
                        window.parent.location.reload();
                    });
                } else {
                    layer.alert(d.msg);
                }
            },
            error : function(d) {
                parent.layer.close(loading)
                layer.alert('导入失败');
            }
        });
    }
};