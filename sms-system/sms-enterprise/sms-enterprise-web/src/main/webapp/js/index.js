layui.config({
    base: '/layuiadmin/',//静态资源所在路径
}).extend({
    index: 'lib/index' //主入口模块
}).use('index', 'sample');

window.onload = function () {
    currentDataMap();
    operatorPiePercent();
}
//头部数据
function currentDataMap(){
    $.ajax({
        cache : false,
        url: '/admin/index_currentDataMap',
        data: {},
        dataType: 'json',
        success: function (res) {
            if(res.code == '301'){
                window.parent.parent.location.href=res.url;
                return ;
            }
            $('#submitTotal').html(res.submitTotal);
            $('#sendSuccessCount').html(res.sendSuccessCount);
            $('#costTotal').html(res.costTotal);
        }
    });
}

//当天发送饼图
function loadingPieEcharts(){
    var myChart = echarts.init(document.getElementById('pieChart'));
    myChart.showLoading({
        text: '加载中...',
        color: '#4cbbff',
        textStyle: { color: '#444' },
        effectOption: {backgroundColor: 'rgba(0, 0, 0, 0.2)'}
    });
    return false;
}

function operatorPiePercent() {
    loadingPieEcharts();
    $.ajax({
        cache : false,
        url: '/admin/index_currentOperatorSendMapData',
        data: {},
        dataType: 'json',
        success: function (res) {
            var list = res.data;
            var china_mobile_count = 0;
            var china_telecom_count = 0;
            var china_unicom_count = 0;
            var china_mobile_success_count = 0;
            var china_telecom_success_count = 0;
            var china_unicom_success_count = 0;

            if (list && list.length > 0){
                $('#noSendSmsData').remove();
                for(var j=0;j<list.length;j++){
                    var operator = list[j]['operator'];
                    if (operator == 'china_mobile'){
                        china_mobile_count += list[j]['count'];
                        china_mobile_success_count+= list[j]['successCount'];
                    }else if(operator == 'china_telecom'){
                        china_telecom_count += list[j]['count'];
                        china_telecom_success_count+= list[j]['successCount'];
                    }else if(operator == 'china_unicom'){
                        china_unicom_count += list[j]['count'];
                        china_unicom_success_count+= list[j]['successCount'];
                    }
                }
            }else{
                $('#noSendSmsData').show();
            }
            //饼图数据渲染
            var pieChart = echarts.init(document.getElementById('pieChart'));
            option = {
                tooltip: {
                    trigger: 'item',
                },
                legend: {
                    data:['移动','联通','电信']
                },
                series: [
                    {
                        name: '发送成功数量',
                        type: 'pie',
                        selectedMode: 'single',
                        radius: [0, '40%'],
                        label: {
                            position: 'inner',
                            fontSize: 14,
                        },
                        labelLine: {
                            show: false
                        },
                        itemStyle: {
                            normal:{
                                label:{
                                    show:false,
                                },
                                labelLine:{
                                    show:false,
                                }
                            }
                        },
                        data: [
                            { value: china_mobile_success_count, name: '移动'},
                            { value: china_unicom_success_count, name: '联通'},
                            { value: china_telecom_success_count, name: '电信'}
                        ]
                    },
                    {
                        name: '发送总量',
                        type: 'pie',
                        radius: ["60%","85%"],
                        label: {
                            position: 'inner',
                            fontSize: 14,
                        },
                        labelLine: {
                            show: false
                        },
                        itemStyle: {
                            normal:{
                                label:{
                                    show:false,
                                },
                                labelLine:{
                                    show:false,
                                }
                            }
                        },
                        data: [
                            {value:china_mobile_count, name:'移动'},
                            {value:china_unicom_count, name:'联通'},
                            {value:china_telecom_count, name:'电信'}
                        ]
                    }
                ]
            };
            pieChart.setOption(option);
            window.onresize = pieChart.resize;  //自适应浏览器窗口的大小
        }
    });
}