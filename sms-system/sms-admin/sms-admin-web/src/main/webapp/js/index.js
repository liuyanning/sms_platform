layui.config({
    base: '/layuiadmin/',//静态资源所在路径
}).extend({
    index: 'lib/index' //主入口模块
}).use('index', 'sample');

window.onload = function () {
    //一周发送量折线图
    enterpriseSendMap();
    //头部数据
    currentDataMap();
    //operatorLoopPercent();
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
            $('#send_Success_Total').html(res.sendSuccessCount);
            $('#addNewEnterprise').html(res.addEnterpriseCount);
            $('#addNewAgent').html(res.addAgentCount);
        }
    });
}

//一周发送量折线图
function loadingEcharts(){
    var myChart = echarts.init(document.getElementById('myChart'));
    myChart.showLoading({
        text: '加载中...',
        color: '#4cbbff',
        textStyle: { color: '#444' },
        effectOption: {backgroundColor: 'rgba(0, 0, 0, 0.2)'}
    });
    return false;
}
//当天发送饼图
function loadingPieEcharts(){
    var pieEchart = echarts.init(document.getElementById('myChartPie'));
    pieEchart.showLoading({
        text: '加载中...',
        color: '#4cbbff',
        textStyle: { color: '#444' },
        effectOption: {backgroundColor: 'rgba(0, 0, 0, 0.2)'}
    });
    return false;
}
function  enterpriseSendMap(){
    loadingEcharts();
    $.ajax({
        cache : false,
        url: '/admin/index_enterpriseSendMap',
        data: {},
        dataType: 'json',
        success: function (res) {
            if(res.code == '301'){
                window.parent.parent.location.href=res.url;
                return ;
            }
            var list = res.data;
            if (list && list.length > 0){
                list.sort(function(a,b){
                    return b.submitDate - a.submitDate
                })
            }
            var xArray = []; //x轴数据
            var yArray1 = [];   //y轴1数据
            var yArray2 = [];   //y轴2数据
            if (list && list.length > 0){
                for(var j=0;j<list.length;j++){
                    if(xArray.length < 10){
                        xArray[j] = handleData(list[j].submitDate);
                        yArray1[j] = handleData(list[j].countTotal);
                        yArray2[j] = handleData(list[j].sendSuccessCountTotal);
                    }
                }
            }

            var myChart = echarts.init(document.getElementById('myChart'));
            option = {
                toolbox: {
                    show : true,
                    top:10,
                    right:10,
                    feature : {
                        mark : {show: false},
                        magicType : {show: false, type: ['line']},
                        restore : {show: false},
                        saveAsImage : {show: false}
                    }
                },
                legend: {
                    data:['提交条数','发送成功条数'],
                    padding:[10,0,0,0]
                },
                grid:{
                    top:60,
                    right:70,
                    bottom:30,
                    left:60
                },

                tooltip : {
                    trigger: 'axis'
                },
                calculable : true,
                xAxis: {
                    name:"",
                    type : 'category',
                    boundaryGap: false,
                    data : xArray,
                    axisTick: {//y轴刻度线
                        show: true
                    },
                    axisLabel: {
                        interval: 0,//横轴信息全部显示
                        rotate: 0,//-15度角倾斜显示
                    },
                    splitLine: {//设置网格线颜色
                        show: true,
                        lineStyle:{
                            width: 0,
                            type: 'dashed'
                        }
                    }
                },
                yAxis : [{
                    type: 'value',
                    name:"",
                    nameLocation:"end",
                    splitNumber:10,
                    nameGap:35,
                    nameRotate:0,
                    nameTextStyle:{
                        fontSize: 12,
                    },
                    splitLine: {//设置网格线颜色
                        show: true,//展示|隐藏
                        lineStyle:{
                            width: 1,
                            type: 'dashed'
                        }
                    },
                    //默认以千分位显示，不想用的可以在这加一段
                    axisLabel : {   //调整左侧Y轴刻度， 直接按对应数据显示
                        show:true,
                        showMinLabel:true,
                        showMaxLabel:true,
                        formatter: function (value) {
                            return value;
                        }
                    }
                }],
                series: [{
                    name: '提交条数',
                    type: 'line',
                    smooth:true,
                    yAxisIndex: 0,
                    data: yArray1,
                    itemStyle: {
                        normal: {
                            color: "#DC143C"
                        }
                    },
                }, {
                    name: '发送成功条数',
                    type: 'line',
                    smooth:true,
                    yAxisIndex: 0,
                    data: yArray2,
                    itemStyle: {
                        normal: {
                            color: "#adbc31"
                        }
                    },
                }]
            };
            myChart.setOption(option);
           // setTimeout(operatorPercent,600);
            window.onresize = myChart.resize;  //自适应浏览器窗口的大小
        }
    });
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
            var china_mobile_success_count = 0;
            var china_telecom_success_count = 0;
            var china_unicom_success_count = 0;
            var china_mobile_count = 0;
            var china_telecom_count = 0;
            var china_unicom_count = 0;
            if (list && list.length > 0){
                $('#noSendSmsData').remove();
                for(var j=0;j<list.length;j++){
                    var operator = list[j]['operator'];
                    if (operator == 'china_mobile'){
                        china_mobile_success_count+= list[j]['successCount'];
                        china_mobile_count += list[j]['submitSuccessTotal'];
                    }else if(operator == 'china_telecom'){
                        china_telecom_success_count+= list[j]['successCount'];
                        china_telecom_count += list[j]['submitSuccessTotal'];
                    }else if(operator == 'china_unicom'){
                        china_unicom_success_count+= list[j]['successCount'];
                        china_unicom_count += list[j]['submitSuccessTotal'];
                    }
                }
            }else{
                $('#noSendSmsData').show();
            }
                var myChartPie = echarts.init(document.getElementById('myChartPie'));
                let option = {
                    tooltip: {
                        trigger: 'item'
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
                            name: '提交成功数量',
                            type: 'pie',
                            radius: ["65%","85%"],
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
                    ],
                };
                myChartPie.setOption(option);
                window.onresize = myChartPie.resize;  //自适应浏览器窗口的大小

        }
    });
}