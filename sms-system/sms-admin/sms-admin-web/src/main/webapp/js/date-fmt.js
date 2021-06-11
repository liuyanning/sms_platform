function getNowFormatDate() {
    var date = new Date();
    var seperator1 = "-";
    var year = date.getFullYear();
    var month = date.getMonth() + 1;
    var strDate = date.getDate();
    if (month >= 1 && month <= 9) {
        month = "0" + month;
    }
    if (strDate >= 0 && strDate <= 9) {
        strDate = "0" + strDate;
    }
    var currentdate = year + seperator1 + month + seperator1 + strDate;
    return currentdate;
}
function getFormatDateBefore(days) {
    var date = new Date(new Date()-1000*60*60*24*days);
    var seperator1 = "-";
    var year = date.getFullYear();
    var month = date.getMonth() + 1;
    var strDate = date.getDate();
    if (month >= 1 && month <= 9) {
        month = "0" + month;
    }
    if (strDate >= 0 && strDate <= 9) {
        strDate = "0" + strDate;
    }
    var currentdate = year + seperator1 + month + seperator1 + strDate;
    return currentdate;
}
function getFormatDateBeforeMonth(days) {
    var date = new Date(new Date()-1000*60*60*24*days);
    var seperator1 = "-";
    var year = date.getFullYear();
    var month = date.getMonth() + 1;
    var strDate = date.getDate();
    if (month >= 1 && month <= 9) {
        month = "0" + month;
    }
    if (strDate >= 0 && strDate <= 9) {
        strDate = "0" + strDate;
    }
    var currentdate = year + seperator1 + month ;
    return currentdate;
}

function getFormatLastYearBeforeDays(days) {
    var date = new Date(getFormatDateBeforeMonth(days));
    var seperator1 = "-";
    var year = date.getFullYear()-1;
    var strMonth = date.getMonth() + 2;
    if (strMonth >= 1 && strMonth <= 9) {
        strMonth = "0" + strMonth;
    }
    var currentdate = year + seperator1 + strMonth ;
    return currentdate;
}

//时间间隔
function timeInterval(smallDate,bigDate){
    if (!smallDate || !bigDate) {
        return "---";
    }
    var date1 = new Date(smallDate.replace(/-/g,'/'));
    var date2 = new Date(bigDate.replace(/-/g,'/'));
    var total = (date2 - date1)/1000;
    if(total < 0){
        return "---";
    }
    return total;
}
function timeIntervalFormate(smallDate,bigDate){
    var total = timeInterval(smallDate, bigDate);
    if(total < 0){
        return "---";
    }
    var dd = parseInt(total / 60 / 60 / 24, 10);//计算剩余的天数
    var hh = parseInt(total / 60 / 60 % 24, 10);//计算剩余的小时数
    var mm = parseInt(total / 60 % 60, 10);//计算剩余的分钟数
    var ss = parseInt(total % 60, 10);//计算剩余的秒数
    var result = dd+"天"+hh+"时"+mm+"分"+ ss+"秒";
    return result;
}
function getFormatDateBeforeMinutes(minutes) {
    var date = new Date(new Date()-1000*60*minutes);
    var seperator1 = "-";
    var year = date.getFullYear();
    var month = date.getMonth() + 1;
    var strDate = date.getDate();
    var hour = date.getHours();
    var minute = date.getMinutes();
    var second = date.getSeconds();
    if (month >= 1 && month <= 9) {
        month = "0" + month;
    }
    if (strDate >= 0 && strDate <= 9) {
        strDate = "0" + strDate;
    }
    if (hour >= 0 && hour <= 9) {
        hour = "0" + hour;
    }
    if (minute >= 0 && minute <= 9) {
        minute = "0" + minute;
    }
    if (second >= 0 && second <= 9) {
        second = "0" + second;
    }
    return year + seperator1 + month + seperator1 + strDate + ' ' + hour + ':'+ minute +':'+ second;
}
