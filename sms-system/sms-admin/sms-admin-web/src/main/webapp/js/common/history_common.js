function numberToIp(number) {
	if(number == '0'){//处理数字0异常显示
		return number;
	}
	if(!number){
		return '---';
	}
	if(number == 'null' || number == 'undefined'){
		return '---';
	}

	var tt = new Array();
	for (var i = 0; i < 4; i++) {
		// 每 8 位为一段，这里取当前要处理的最高位的位置
		var pos = i * 8;
		// 取当前处理的 ip 段的值
		var and = number & (255 << pos);
		// 将当前 ip 段转换为 0 ~ 255 的数字，注意这里必须使用无符号右移
		tt[i] = and >>> pos;
	}

	return String(tt[0]) + "." + String(tt[1]) + "." + String(tt[2]) + "." + String(tt[3]);
}