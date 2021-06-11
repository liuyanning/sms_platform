package com.drondea.wireless.util;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

/**
 * 转换工具类
 * 
 * @author Administrator
 * 
 */
public class NumberConvert {

	public static int toInt(byte[] b) {
		int intValue = 0;
		for (int i = 0; i < b.length; i++) {
			// 0xFF目的是转换Int
			intValue += (b[i] & 0xFF) << (8 * (3 - i));
		}
		return intValue;
	}

	public static byte[] toByte(int intValue) {
		byte[] b = new byte[4];
		for (int i = 0; i < 4; i++) {
			int move = 8 * (3 - i);
			b[i] = (byte) (intValue >> move & 0xFF);
		}
		return b;
	}

	public static short toShort(byte[] b) {
		short value = 0;
		for (int i = 0; i < b.length; i++) {
			value += (b[i] & 0xFF) << (8 * (1 - i));
		}
		return value;
	}

	public static byte[] toByte(short value) {
		byte[] b = new byte[2];

		for (int i = 0; i < 2; i++) {
			int move = 8 * (1 - i);
			b[i] = (byte) (value >> move & 0xFF);
		}
		return b;
	}

	public static long toLong(byte[] b) {
		long value = 0;
		for (int i = 0; i < b.length; i++) {
			value += (b[i] & 0xFF) << (8 * (7 - i));
		}
		return value;
	}

	public static byte[] toByte(long value) {
		byte[] b = new byte[8];
		for (int i = 0; i < 7; i++) {
			int move = 8 * (7 - i);
			b[i] = (byte) (value >> move & 0xFF);
		}
		return b;
	}

	public static byte[] toByte(String value, int fix, String charsetName)
			throws UnsupportedEncodingException {
		byte[] data = new byte[fix];
		byte[] stringByte = value.getBytes(charsetName);
		for (int i = 0; i < stringByte.length; i++) {
			data[i] = stringByte[i];
		}
		return data;
	}

	public static byte[] toByte(String value, int fix)
			throws UnsupportedEncodingException {
		return toByte(value, fix, Charset.defaultCharset().name());
	}

	public static byte[] toByte(String value)
			throws UnsupportedEncodingException {
		return toByte(value, value.getBytes().length, Charset.defaultCharset()
				.name());
	}

	public static byte[] toByte(String value, String charsetName)
			throws UnsupportedEncodingException {
		return toByte(value, value.getBytes(charsetName).length, charsetName);
	}

	public static String toString(byte[] data, String charsetName)
			throws UnsupportedEncodingException {
		String value = new String(data, charsetName);
		return value;
	}

	public static String toString(byte[] data)
			throws UnsupportedEncodingException {
		return toString(data, Charset.defaultCharset().name());
	}

	private static final char[] hexChar = new char[] { '0', '1', '2', '3', '4',
			'5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
	private static final Map<Character, Integer> char2HexMap = new HashMap<Character, Integer>();
	static {
		char2HexMap.put('0', 0);
		char2HexMap.put('1', 1);
		char2HexMap.put('2', 2);
		char2HexMap.put('3', 3);
		char2HexMap.put('4', 4);
		char2HexMap.put('5', 5);
		char2HexMap.put('6', 6);
		char2HexMap.put('7', 7);
		char2HexMap.put('8', 8);
		char2HexMap.put('9', 9);
		char2HexMap.put('A', 10);
		char2HexMap.put('B', 11);
		char2HexMap.put('C', 12);
		char2HexMap.put('D', 13);
		char2HexMap.put('E', 14);
		char2HexMap.put('F', 15);
	}

	/**
	 * 转换16进制
	 * 
	 * @return
	 */
	public static String toHexString(byte[] b) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < b.length; i++) {
			sb.append(hexChar[b[i] >> 4 & 0xF]);
			sb.append(hexChar[b[i] & 0xF]);
		}
		return sb.toString();
	}

	public static byte[] toByteWithHexString(String hexSring, int fix) {
		byte[] byteData = new byte[fix];
		for (int i = 0; i < fix; i++) {
			int byte1 = (char2HexMap.get(hexSring.charAt(i * 2)).byteValue() << 4 & 0xFF);
			int byte2 = (char2HexMap.get(hexSring.charAt(i * 2 + 1))
					.byteValue() & 0xFF);
			byteData[i] = (byte) (byte1 + byte2);
		}
		return byteData;
	}

	public static String toHexString(int value) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < 8; i++) {
			sb.append(hexChar[value >> (4 * (7 - i)) & 0xF]);
		}
		return sb.toString();
	}

	public static void main(String[] args) throws UnsupportedEncodingException {
		System.out.println(toByte(0).length);
	}

}
