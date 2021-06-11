//遮罩层
function loadingMask() {
    var loading = layer.load('2', {
        shade: [0.2, '#fff'],
        content: '加载图形监控...',
        success: function (layerContentStyle) {
            layerContentStyle.find('.layui-layer-content').css({
                'padding-top': '35px',
                'text-align': 'left',
                'width': '120px'
            });
        }
    });
    return loading;
}

//获取Series
function getSeriesByType(data, seriesType) {
    var series;
    if (seriesType == 'sort') {
        series = [{
            name: '分拣速度（条/秒）',
            type: 'line',
            smooth: true,
            data: data.speedData
        }];
    } else {
        series = [{
            name: '提交速度（条/秒）',
            type: 'line',
            smooth: true,
            data: data.submitData
        }, {
            name: '回执速度（条/秒）',
            type: 'line',
            smooth: true,
            data: data.reportData
        }];
    }
    return series;
}

//获取更新Series
function getUpdateSeriesByType(data, seriesType) {
    var series;
    if (seriesType == 'sort') {
        series = [{
            data: data.speedData
        }];
    } else {
        series = [{
            data: data.submitData
        }, {
            data: data.reportData
        }];
    }
    return series;
}

//====================== 无ID js===========================================
//====================== 无ID js===========================================
//====================== 无ID js===========================================
//====================== 无ID js===========================================
//第一次加载
function initData(url, loading, title, legend, seriesType) {
    var $ = layui.jquery;
    $.ajax({
        url: url,
        async: true,
        dataType: 'json',
        success: function (res) {
            initECharts(res.data, title, legend, seriesType);
            layer.close(loading);
        }
    });
}

//ajax后台加载数据
function ajaxData(url, seriesType) {
    var $ = layui.jquery;
    $.ajax({
        url: url,
        async: true,
        dataType: 'json',
        success: function (res) {
            myChart.setOption({
                xAxis: {
                    data: res.data.dateList
                },
                series: getUpdateSeriesByType(res.data, seriesType)
            });
        }
    });
}

//初始化myChart
function initECharts(data, title, legend, seriesType) {
    var option = {
        title: {
            text: title
        },
        grid: {
            top: 60,
            right: 40,
            bottom: 60,
            left: 40
        },
        legend: {
            data: legend
        },
        tooltip: {
            trigger: 'axis'
        },
        calculable: true,
        xAxis: {
            name: "时间",
            type: 'category',
            boundaryGap: false,
            data: data.dateList,
            axisTick: {//y轴刻度线
                show: true
            },
            axisLabel: {
                interval: 0,//横轴信息全部显示
                rotate: -60,//-15度角倾斜显示
            },
            splitLine: {//设置网格线颜色
                show: true,
                lineStyle: {
                    width: 0,
                    type: 'dashed'
                }
            }
        },
        yAxis: [
            {
                splitNumber: 10,
                type: 'value',
                name: "条数",
                nameLocation: "end",
                nameGap: 10,
                nameRotate: 0,
                nameTextStyle: {
                    fontSize: 12,
                },
                splitLine: {//设置网格线颜色
                    show: true,//展示|隐藏
                    lineStyle: {
                        width: 1,
                        type: 'dashed'
                    }
                },
                axisLabel: {   //调整左侧Y轴刻度， 直接按对应数据显示
                    show: true,
                    showMinLabel: true,
                    showMaxLabel: true,
                    formatter: function (value) {
                        return value;
                    }
                },
            },
        ],
        series: getSeriesByType(data, seriesType)
    };
    myChart = echarts.init(document.getElementById('myChart'));
    myChart.setOption(option);
}

//====================== 无ID js===========================================
//====================== 无ID js===========================================
//====================== 无ID js===========================================
//====================== 无ID js===========================================


// >>>>>>>>>>>>>>>>>>>>> 根据 ID加载多个 <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
// >>>>>>>>>>>>>>>>>>>>> 根据 ID加载多个 <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
// >>>>>>>>>>>>>>>>>>>>> 根据 ID加载多个 <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
// >>>>>>>>>>>>>>>>>>>>> 根据 ID加载多个 <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
function loadMultipleCharts(url, loading, title, legend, seriesType, idType) {
    var $ = layui.jquery;
    $('div[name=myChart]').each(function () {
        initDataById($(this).attr('tag'), url, loading, title, legend, seriesType, idType);
    });
}


//根据ID第一次加载
function initDataById(id, url, loading, title, legend, seriesType, idType) {
    var $ = layui.jquery;
    $.ajax({
        url: url,
        async: true,
        data: getParamDataByIdType(idType, id),
        dataType: 'json',
        success: function (res) {
            initEChartsById(res.data, id, title, legend, seriesType, idType);
            layer.close(loading);
        }
    });
}

//ajax根据Id加载数据
function ajaxDataById(id, url, seriesType, idType) {
    var $ = layui.jquery;
    $.ajax({
        url: url,
        async: true,
        data: getParamDataByIdType(idType, id),
        dataType: 'json',
        success: function (res) {
            var editChart = echarts.getInstanceByDom(document.getElementById('myChart_' + id));
            editChart.setOption({
                xAxis: {
                    data: res.data.dateList
                },
                series: getUpdateSeriesByType(res.data, seriesType)
            });
        }
    });
}

//根据Id初始化ECharts
function initEChartsById(data, id, title, legend, seriesType, idType) {
    var myChartById = echarts.init(document.getElementById('myChart_' + id));
    var option = {
        title: {
            text: getTitleByIdType(data, idType, id, title)
        },
        grid: {
            top: 60,
            right: 40,
            bottom: 60,
            left: 40
        },
        legend: {
            top: 32,
            data: legend
        },
        tooltip: {
            trigger: 'axis'
        },
        calculable: true,
        xAxis: {
            name: "时间",
            type: 'category',
            boundaryGap: false,
            data: data.dateList,
            axisTick: {//y轴刻度线
                show: true
            },
            axisLabel: {
                interval: 0,//横轴信息全部显示
                rotate: -60,//-15度角倾斜显示
            },
            splitLine: {//设置网格线颜色
                show: true,
                lineStyle: {
                    width: 0,
                    type: 'dashed'
                }
            }
        },
        yAxis: [{
            type: 'value',
            name: "条数",
            nameLocation: "end",
            splitNumber: 10,
            nameGap: 10,
            nameRotate: 0,
            nameTextStyle: {
                fontSize: 12,
            },
            splitLine: {//设置网格线颜色
                show: true,//展示|隐藏
                lineStyle: {
                    width: 1,
                    type: 'dashed'
                }
            },
            //默认以千分位显示，不想用的可以在这加一段
            axisLabel: {   //调整左侧Y轴刻度， 直接按对应数据显示
                show: true,
                showMinLabel: true,
                showMaxLabel: true,
                formatter: function (value) {
                    return value;
                }
            }
        }],
        series: getSeriesByType(data,seriesType)
    };
    myChartById.setOption(option);
}

//获取传参参数
function getParamDataByIdType(idType, id) {
    var data;
    if (idType == 'channelId') {
        data = {"channelId": id}
    } else if (idType == 'userId') {
        data = {"userId": id}
    } else if (idType == 'sortIp') {
        data = {"ip": id}
    } else if (idType == 'senderIp') {
        data = {"ip": id}
    } else if (idType == 'netwayIp') {
        data = {"ip": id}
    }
    return data;
}

//获取title
function getTitleByIdType(data, idType, id, title) {
    if (idType == 'channelId') {
        title = '(' + data.channel.name + ')并发图';
    } else if (idType == 'userId') {
        title = '(' + data.user.real_Name + ')并发图';
    } else if (idType == 'sortIp') {
        title = '分拣(' + id + ')并发图'
    } else if (idType == 'senderIp') {
        title = '发送器(' + id + ')并发图'
    } else if (idType == 'netwayIp') {
        title = '网关(' + id + ')并发图'
    }
    return title;
}


// >>>>>>>>>>>>>>>>>>>>> 根据 ID加载多个 <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
// >>>>>>>>>>>>>>>>>>>>> 根据 ID加载多个 <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
// >>>>>>>>>>>>>>>>>>>>> 根据 ID加载多个 <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
// >>>>>>>>>>>>>>>>>>>>> 根据 ID加载多个 <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

