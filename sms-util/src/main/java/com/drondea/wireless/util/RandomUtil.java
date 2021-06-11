package com.drondea.wireless.util;

import java.util.Random;

/**
 * 
 * 
 * RandomUtil
 * 
 * @author 
 * @createTime 2016年9月13日 上午11:53:38
 * @version 1.0.0
 * 
 */
public class RandomUtil {
	static char[] randomMetaCharacterData = null;
	static char[] randomMetaStrData = null;
	static char[] randomMetaIntData = null;

	static {
		randomMetaCharacterData = new char[] { 'a', 'b', 'c', 'd', 'e', 'f', 'g',
				'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's',
				't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E',
				'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q',
				'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };
		randomMetaStrData = new char[] { 'a', 'b', 'c', 'd', 'e', 'f', 'g',
			'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's',
			't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E',
			'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q',
			'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '0', '1', '2',
			'3', '4', '5', '6', '7', '8', '9' };
		randomMetaIntData = new char[] {'0', '1', '2','3', '4', '5', '6', '7', '8', '9' };
	}

	public static String randomCharacter(int num) {
		return getRandom(randomMetaCharacterData, num);
	}
	
	public static String randomStr(int num) {
		return getRandom(randomMetaStrData, num);
	}
	
	public static String randomInt(int num) {
		return getRandom(randomMetaIntData, num);
	}

	private static String getRandom(char[] randomMetaData, int num) {
		Random random = new Random();
		StringBuilder returnVal = new StringBuilder() ;
		for (int i = 0; i < num; i++) {
			returnVal.append(randomMetaData[random
					.nextInt(randomMetaData.length - 1)]);
		}
		return returnVal.toString();
	}
	
	
	/*public static void main(String[] args) {
		System.out.println(randomInt(4));
		System.out.println(randomStr(24));
	}*/
}
