package com.hero.wireless.json;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.hero.wireless.web.config.DatabaseCache;
import com.hero.wireless.web.entity.business.Code;

/**
 * 格式化值
 * 
 * @author Lenovo
 *
 */
public class ValueFormat {
	private static final String Y_M_D_H_M_S_1 = "yyyy-MM-dd HH:mm:ss";
	private static final String Y_M_D_H_M_S_2 = "yyyyMMddHHmmss";
	private static final String Y_M_D_H_M_S_S_1 = "yyyy-MM-dd HH:mm:ss.SSS";
	private static final String Y_M_D_H_M_S_S_2 = "yyyyMMddHHmmssSSS";
	private static final String Y_M_D_1 = "yyyy-MM-dd";
	private static final String Y_M_D_2 = "yyyyMMdd";
	private static final String H_M_S_1 = "HH:mm:ss";
	private static final String H_M_S_2 = "HHmmss";

	/**
	 * 隐藏中间字符
	 * 
	 * @param value
	 * @param beginIndex
	 * @param endIndex
	 * @return
	 */
	protected String hideMiddle(String value, int beginIndex, int endIndex) {
		Code symbolCode =DatabaseCache.getSystemEnvByCode("hideSymbol");
		String symbol="☺☺☺";
		if(symbolCode!=null) {
			symbol=symbolCode.getName();
		}
		int lastIndex = value.length() - 1;
		StringBuffer result = new StringBuffer();
		if (beginIndex >= lastIndex) {
			return symbol;
		}
		result.append(value.substring(0, beginIndex + 1));
		result.append(symbol);
		if (endIndex > lastIndex || (endIndex - beginIndex) <= 1) {
			return result.toString();
		}
		result.append(value.substring(endIndex));
		return result.toString();
	}

	/**
	 * 隐藏手机号码
	 * 
	 * @param value
	 * @return
	 */
	public String hideMobileNo(Object value) {
		return hideMiddle((String) value, 2, 8);
	}

	public String hideBankCardNo(Object value) {
		String v = (String) value;
		return hideMiddle((String) value, 3, v.length() - 4);
	}

	public String defaultHide(Object value) {
		String v = (String) value;
		return hideMiddle((String) value, 0, v.length() - 1);
	}

	protected String fmtDate(Object value, String fmt) {
		return new SimpleDateFormat(fmt).format(value);
	}

	public String fmtDate1(Object value) {
		return fmtDate(value, Y_M_D_H_M_S_1);
	}
	public String fmtDate2(Object value) {
		return fmtDate(value, Y_M_D_H_M_S_2);
	}
	public String fmtDate3(Object value) {
		return fmtDate(value, Y_M_D_H_M_S_S_1);
	}
	public String fmtDate4(Object value) {
		return fmtDate(value, Y_M_D_H_M_S_S_2);
	}
	public String fmtDate5(Object value) {
		return fmtDate(value, Y_M_D_1);
	}
	public String fmtDate6(Object value) {
		return fmtDate(value, Y_M_D_2);
	}
	public String fmtDate7(Object value) {
		return fmtDate(value, H_M_S_1);
	}
	public String fmtDate8(Object value) {
		return fmtDate(value, H_M_S_2);
	}

	public static void main(String[] args) {
		System.out.println(new ValueFormat().fmtDate4(new Date()));
	}
}
