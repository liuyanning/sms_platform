package com.drondea.wireless.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

/**
 * @author duyujun 工具类，负责Base64编码的处理。
 */
public class Base64Utils {
	/**
	 * Byte value that maps to 'a' in Base64 encoding
	 */
	private final static int LOWER_CASE_A_VALUE = 26;

	/**
	 * Byte value that maps to '0' in Base64 encoding
	 */
	private final static int ZERO_VALUE = 52;

	/**
	 * Byte value that maps to '+' in Base64 encoding
	 */
	private final static int PLUS_VALUE = 62;

	/**
	 * Byte value that maps to '/' in Base64 encoding
	 */
	private final static int SLASH_VALUE = 63;

	/**
	 * Bit mask for one character worth of bits in Base64 encoding. Equivalent
	 * to binary value 111111b.
	 */
	private final static int SIX_BIT_MASK = 63;

	/**
	 * Bit mask for one byte worth of bits in Base64 encoding. Equivalent to
	 * binary value 11111111b.
	 */
	private static final int EIGHT_BIT_MASK = 0xFF;

	/**
	 * The input String to be decoded
	 */
	private String mString;

	/**
	 * Current position in the String(to be decoded)
	 */
	private int mIndex = 0;

	/**
	 * Encode an array of bytes using Base64
	 * 
	 * @param data[]
	 *            The bytes to be encoded
	 * @return A valid Base64 representation of the input
	 */
	public static String encode(byte data[]) {
		return new Base64Utils().internalEncode(data);
	}
	
	public static String encode(String str) {
		return encode(str.getBytes());
	}

	/**
	 * Encode an array of bytes using Base64
	 * 
	 * @param data[]
	 *            The bytes to be encoded
	 * @return A valid Base64 representation of the input
	 */
	public String internalEncode(byte data[]) {
		// Base64 encoding yields a String that is 33% longer than the byte
		// array
		int charCount = ((data.length * 4) / 3) + 4;

		// New lines will also be needed for every 76 charactesr, so allocate a
		// StringBuffer that is long enough to hold the full result without
		// having to expand later
		StringBuffer result = new StringBuffer((charCount * 77) / 76);

		int byteArrayLength = data.length;
		int byteArrayIndex = 0;
		int byteTriplet = 0;
		while (byteArrayIndex < byteArrayLength - 2) {
			// Build the 24 bit byte triplet from the input data
			byteTriplet = convertUnsignedByteToInt(data[byteArrayIndex++]);
			// Each input byte contributes 8 bits to the triplet
			byteTriplet <<= 8;
			byteTriplet |= convertUnsignedByteToInt(data[byteArrayIndex++]);
			byteTriplet <<= 8;
			byteTriplet |= convertUnsignedByteToInt(data[byteArrayIndex++]);

			// Look at the lowest order six bits and remember them
			byte b4 = (byte) (SIX_BIT_MASK & byteTriplet);
			// Move the byte triplet to get the next 6 bit value
			byteTriplet >>= 6;
			byte b3 = (byte) (SIX_BIT_MASK & byteTriplet);
			byteTriplet >>= 6;
			byte b2 = (byte) (SIX_BIT_MASK & byteTriplet);
			byteTriplet >>= 6;
			byte b1 = (byte) (SIX_BIT_MASK & byteTriplet);

			// Add the Base64 encoded character to the result String
			result.append(mapByteToChar(b1));
			result.append(mapByteToChar(b2));
			result.append(mapByteToChar(b3));
			result.append(mapByteToChar(b4));

			// There are 57 bytes for every 76 characters, so wrap the line when
			// needed
			// if ( byteArrayIndex % 57 == 0 ) {
			// result.append( "\n" );
			// }
		}

		// Check if we have one byte left over
		if (byteArrayIndex == byteArrayLength - 1) {
			// Convert our one byte to an int
			byteTriplet = convertUnsignedByteToInt(data[byteArrayIndex++]);
			// Right pad the second 6 bit value with zeros
			byteTriplet <<= 4;

			byte b2 = (byte) (SIX_BIT_MASK & byteTriplet);
			byteTriplet >>= 6;
			byte b1 = (byte) (SIX_BIT_MASK & byteTriplet);

			result.append(mapByteToChar(b1));
			result.append(mapByteToChar(b2));

			// Add "==" to the output to make it a multiple of 4 Base64
			// characters
			result.append("==");
		}

		// Check if we have two byte left over
		if (byteArrayIndex == byteArrayLength - 2) {
			// Convert our two bytes to an int
			byteTriplet = convertUnsignedByteToInt(data[byteArrayIndex++]);
			byteTriplet <<= 8;
			byteTriplet |= convertUnsignedByteToInt(data[byteArrayIndex++]);
			// Right pad the third 6 bit value with zeros
			byteTriplet <<= 2;

			byte b3 = (byte) (SIX_BIT_MASK & byteTriplet);
			byteTriplet >>= 6;
			byte b2 = (byte) (SIX_BIT_MASK & byteTriplet);
			byteTriplet >>= 6;
			byte b1 = (byte) (SIX_BIT_MASK & byteTriplet);

			result.append(mapByteToChar(b1));
			result.append(mapByteToChar(b2));
			result.append(mapByteToChar(b3));

			// Add "==" to the output to make it a multiple of 4 Base64
			// characters
			result.append("=");
		}

		return result.toString();
	}

	/**
	 * Decode an input String using Base64
	 * 
	 * @param data
	 *            The String to be decoded
	 * @return The appropriate byte array
	 */
	public static byte[] decode(String data) {
		return new Base64Utils().internalDecode(data);
	}

	/**
	 * Decode an input String using Base64
	 * 
	 * @param data
	 *            The String to be decoded
	 * @return The appropriate byte array
	 */
	public byte[] internalDecode(String data) {
		mString = data;
		mIndex = 0;

		/**
		 * Total number of Base64 characters in the input
		 */
		int mUsefulLength = 0;
		int length = mString.length();
		for (int i = 0; i < length; i++) {
			if (isUsefulChar(mString.charAt(i))) {
				mUsefulLength++;
			}
		}

		// mString = data;

		// A Base64 byte array is 75% the size of its String representation
		int byteArrayLength = mUsefulLength * 3 / 4;

		byte result[] = new byte[byteArrayLength];

		int byteTriplet = 0;
		int byteIndex = 0;

		// Continue until we have less than 4 full characters left to
		// decode in the input.
		while (byteIndex + 2 < byteArrayLength) {

			// Package a set of four characters into a byte triplet
			// Each character contributes 6 bits of useful information
			byteTriplet = mapCharToInt(getNextUsefulChar());
			byteTriplet <<= 6;
			byteTriplet |= mapCharToInt(getNextUsefulChar());
			byteTriplet <<= 6;
			byteTriplet |= mapCharToInt(getNextUsefulChar());
			byteTriplet <<= 6;
			byteTriplet |= mapCharToInt(getNextUsefulChar());

			// Grab a normal byte (eight bits) out of the byte triplet
			// and put it in the byte array
			result[byteIndex + 2] = (byte) (byteTriplet & EIGHT_BIT_MASK);
			byteTriplet >>= 8;
			result[byteIndex + 1] = (byte) (byteTriplet & EIGHT_BIT_MASK);
			byteTriplet >>= 8;
			result[byteIndex] = (byte) (byteTriplet & EIGHT_BIT_MASK);
			byteIndex += 3;
		}

		// Check if we have one byte left to decode
		if (byteIndex == byteArrayLength - 1) {
			// Take out the last two characters from the String
			byteTriplet = mapCharToInt(getNextUsefulChar());
			byteTriplet <<= 6;
			byteTriplet |= mapCharToInt(getNextUsefulChar());

			// Remove the padded zeros
			byteTriplet >>= 4;
			result[byteIndex] = (byte) (byteTriplet & EIGHT_BIT_MASK);
		}

		// Check if we have two bytes left to decode
		if (byteIndex == byteArrayLength - 2) {
			// Take out the last three characters from the String
			byteTriplet = mapCharToInt(getNextUsefulChar());
			byteTriplet <<= 6;
			byteTriplet |= mapCharToInt(getNextUsefulChar());
			byteTriplet <<= 6;
			byteTriplet |= mapCharToInt(getNextUsefulChar());

			// Remove the padded zeros
			byteTriplet >>= 2;
			result[byteIndex + 1] = (byte) (byteTriplet & EIGHT_BIT_MASK);
			byteTriplet >>= 8;
			result[byteIndex] = (byte) (byteTriplet & EIGHT_BIT_MASK);
		}

		return result;
	}

	/**
	 * Convert a Base64 character to its 6 bit value as defined by the mapping.
	 * 
	 * @param c
	 *            Base64 character to decode
	 * @return int representation of 6 bit value
	 */
	private int mapCharToInt(char c) {
		if (c >= 'A' && c <= 'Z') {
			return c - 'A';
		}

		if (c >= 'a' && c <= 'z') {
			return (c - 'a') + LOWER_CASE_A_VALUE;
		}

		if (c >= '0' && c <= '9') {
			return (c - '0') + ZERO_VALUE;
		}

		if (c == '+') {
			return PLUS_VALUE;
		}

		if (c == '/') {
			return SLASH_VALUE;
		}

		throw new IllegalArgumentException(c
				+ " is not a valid Base64 character.");
	}

	/**
	 * Convert a byte between 0 and 63 to its Base64 character equivalent
	 * 
	 * @param b
	 *            Byte value to be converted
	 * @return Base64 char value
	 */
	private char mapByteToChar(byte b) {
		if (b < LOWER_CASE_A_VALUE) {
			return (char) ('A' + b);
		}

		if (b < ZERO_VALUE) {
			return (char) ('a' + (b - LOWER_CASE_A_VALUE));
		}

		if (b < PLUS_VALUE) {
			return (char) ('0' + (b - ZERO_VALUE));
		}

		if (b == PLUS_VALUE) {
			return '+';
		}

		if (b == SLASH_VALUE) {
			return '/';
		}

		throw new IllegalArgumentException("Byte " + new Integer(b)
				+ " is not a valid Base64 value");
	}

	/**
	 * @param c
	 *            Character to be examined
	 * @return Whether or not the character is a Base64 character
	 */
	private boolean isUsefulChar(char c) {
		return (c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z')
				|| (c >= '0' && c <= '9') || (c == '+') || (c == '/');
	}

	/**
	 * Traverse the String until hitting the next Base64 character. Assumes that
	 * there is still another valid Base64 character left in the String.
	 */
	private char getNextUsefulChar() {
		char result = '_'; // Start with a non-Base64 character
		while (!isUsefulChar(result)) {
			result = mString.charAt(mIndex++);
		}

		return result;
	}

	/**
	 * Convert a byte to an integer. Needed because in Java bytes are signed,
	 * and for Base64 purposes they are not. If not done this way, when
	 * converted to an int, 0xFF will become -127
	 * 
	 * @param b
	 *            Byte value to be converted
	 * @return Value as an integer, as if byte was unsigned
	 */
	private int convertUnsignedByteToInt(byte b) {
		if (b >= 0) {
			return (int) b;
		}

		return 256 + b;
	}

	/**
	 * 压缩原始报文，之后base64
	 * 
	 * @param tMessage
	 * @return 返回压缩并且base64的报文
	 */
	public static String getEncodeMsg(String tMessage) {
		String returnStr = "";
		try {
			ByteArrayOutputStream byteStream = getCompressedStr(tMessage);

			if (byteStream != null)
				returnStr = Base64Utils.encode(byteStream.toByteArray());

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return returnStr;
	}

	/**
	 * 获得压缩后的报文(ZLib压缩)
	 * 
	 * @param tMessage
	 *            传入的报文
	 * @return byte[] 返回的byte数组
	 */
	private static ByteArrayOutputStream getCompressedStr(String tMessage) {

		ByteArrayOutputStream compressedStream = null;
		try {
			if (tMessage != null && !"".equals(tMessage)) {
				// 需要压缩的byte数组
				byte[] input = tMessage.getBytes("UTF-8");

				// 压缩数组
				Deflater compresser = new Deflater();
				compresser.setInput(input);
				compresser.finish();

				compressedStream = new ByteArrayOutputStream();
				byte[] buf = new byte[2048];

				// 读取压缩数组到字节流
				while (!compresser.finished()) {
					int got = compresser.deflate(buf);
					if (got < 1)
						break;
					compressedStream.write(buf, 0, got);
				}
				compresser.end();
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (compressedStream != null) {
				try {
					compressedStream.close();
				} catch (IOException ioex) {
					ioex.printStackTrace();
				}
			}

		}

		return compressedStream;

	}

	/**
	 * decode报文之后解压缩
	 * 
	 * @param tMessage
	 * @return
	 */
	public static String getDecodeMsg(String tMessage) {
		String outputString = "";
		byte[] inputStr = null;
		try {
			String newStr = tMessage;
			newStr = newStr.replaceAll(" ", "+");
			inputStr = Base64Utils.decode(newStr);
			outputString = getDeCompressedStr(inputStr);

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return outputString;

	}

	/**
	 * 获得解压缩后的报文
	 * 
	 * @param tMessage
	 *            base64解码后的，压缩过的报文
	 * @return 解压缩后的原始报文
	 */
	private static String getDeCompressedStr(byte[] tMessage) {
		String returnStr = "";
		ByteArrayOutputStream aDeCompressedStream = null;
		try {

			// Decompress the bytes
			Inflater decompresser = new Inflater();
			decompresser.setInput(tMessage);
			aDeCompressedStream = new ByteArrayOutputStream();

			byte[] buf = new byte[2048];
			while (!decompresser.finished()) {
				int got = decompresser.inflate(buf);
				if (got < 1)
					break;
				aDeCompressedStream.write(buf, 0, got);
			}
			decompresser.end();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try {
				if (aDeCompressedStream != null)
					aDeCompressedStream.close();
			} catch (IOException ioex) {
				ioex.printStackTrace();
			}
		}
		try {
			returnStr = aDeCompressedStream.toString("UTF-8");
		} catch (UnsupportedEncodingException encodeEx) {
			encodeEx.printStackTrace();
		}

		return returnStr;
	}
}
