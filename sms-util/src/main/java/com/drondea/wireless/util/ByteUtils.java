package com.drondea.wireless.util;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

/**
 * 字节转换
 * 
 * @author zly
 *
 */
public class ByteUtils {
	private static final String DEFAULT_CHARSET = "UTF-8";
	private static final char[] HEX_CHAR = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C',
			'D', 'E', 'F' };
	private static final Map<Character, Integer> CHAR_2_HEX_MAP = new HashMap<Character, Integer>();
	static {
		CHAR_2_HEX_MAP.put('0', 0);
		CHAR_2_HEX_MAP.put('1', 1);
		CHAR_2_HEX_MAP.put('2', 2);
		CHAR_2_HEX_MAP.put('3', 3);
		CHAR_2_HEX_MAP.put('4', 4);
		CHAR_2_HEX_MAP.put('5', 5);
		CHAR_2_HEX_MAP.put('6', 6);
		CHAR_2_HEX_MAP.put('7', 7);
		CHAR_2_HEX_MAP.put('8', 8);
		CHAR_2_HEX_MAP.put('9', 9);
		CHAR_2_HEX_MAP.put('A', 10);
		CHAR_2_HEX_MAP.put('B', 11);
		CHAR_2_HEX_MAP.put('C', 12);
		CHAR_2_HEX_MAP.put('D', 13);
		CHAR_2_HEX_MAP.put('E', 14);
		CHAR_2_HEX_MAP.put('F', 15);
	}

	public static long toLong(byte[] b) {
		long value = 0;
		for (int i = 0; i < b.length; i++) {
			value += (b[i] & 0xFF) << (8 * (7 - i));
		}
		return value;
	}

	public static int toInt(byte[] b) {
		int intValue = 0;
		for (int i = 0; i < b.length; i++) {
			// 0xFF目的是转换Int
			intValue += (b[i] & 0xFF) << (8 * (3 - i));
		}
		return intValue;
	}

	public static short toShort(byte[] b) {
		short value = 0;
		for (int i = 0; i < b.length; i++) {
			value += (b[i] & 0xFF) << (8 * (1 - i));
		}
		return value;
	}

	public static byte[] toByte(int intValue) {
		byte[] b = new byte[4];
		for (int i = 0; i < 4; i++) {
			int move = 8 * (3 - i);
			b[i] = (byte) (intValue >> move & 0xFF);
		}
		return b;
	}

	public static byte[] toByte(short value) {
		byte[] b = new byte[2];

		for (int i = 0; i < 2; i++) {
			int move = 8 * (1 - i);
			b[i] = (byte) (value >> move & 0xFF);
		}
		return b;
	}

	public static byte[] toByte(long value) {
		byte[] b = new byte[8];
		for (int i = 0; i < 7; i++) {
			int move = 8 * (7 - i);
			b[i] = (byte) (value >> move & 0xFF);
		}
		return b;
	}

	public static byte[] toByte(String value, int fix, String charsetName) {
		try {
			byte[] data = new byte[fix];
			byte[] stringByte = value.getBytes(charsetName);
			for (int i = 0; i < stringByte.length; i++) {
				data[i] = stringByte[i];
			}
			return data;
		} catch (UnsupportedEncodingException uee) {
			return null;
		}
	}

	public static byte[] toByte(String value, int fix) {
		return toByte(value, fix, DEFAULT_CHARSET);
	}

	public static byte[] toByte(String value) {
		return toByte(value, value.getBytes().length, DEFAULT_CHARSET);
	}

	public static byte[] toByte(String value, String charsetName) {
		try {
			return toByte(value, value.getBytes(DEFAULT_CHARSET).length, charsetName);
		} catch (UnsupportedEncodingException uee) {
			return null;
		}
	}

	public static String toString(byte[] data, String charsetName) {
		try {
			String value = new String(data, charsetName);
			return value;
		} catch (UnsupportedEncodingException uee) {
			return null;
		}
	}

	public static String toString(byte[] data) {
		return toString(data, Charset.defaultCharset().name());
	}

	/**
	 * 转换16进制
	 * 
	 * @return
	 */
	public static String toHexString(byte[] b) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < b.length; i++) {
			sb.append(HEX_CHAR[b[i] >> 4 & 0xF]);
			sb.append(HEX_CHAR[b[i] & 0xF]);
		}
		return sb.toString();
	}

	public static byte[] toByteWithHexString(String hexSring, int fix) {
		byte[] byteData = new byte[fix];
		for (int i = 0; i < fix; i++) {
			int byte1 = (CHAR_2_HEX_MAP.get(hexSring.charAt(i * 2)).byteValue() << 4 & 0xFF);
			int byte2 = (CHAR_2_HEX_MAP.get(hexSring.charAt(i * 2 + 1)).byteValue() & 0xFF);
			byteData[i] = (byte) (byte1 + byte2);
		}
		return byteData;
	}

	public static String toHexString(int value) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < 8; i++) {
			sb.append(HEX_CHAR[value >> (4 * (7 - i)) & 0xF]);
		}
		return sb.toString();
	}

	public static void main(String[] args) {
		System.out.println(toHexString(new byte[] { 15 }));
	}
}
