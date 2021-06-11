package com.drondea.wireless.util;

import java.security.MessageDigest;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;


/**
 * 
 * 
 * SecretUtil
 * 
 * @author 
 * @createTime 2016年9月13日 上午11:38:35
 * @version 1.0.0
 * 
 */
public class SecretUtil {
	public static final char HEX_DIGITS[] = { '0', '1', '2', '3', '4', '5',
			'6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };    

	public final static String MD5(String s) {
		return MD5(s, "UTF-8");
	}

	public final static String MD5(String s, String encoding) {
		try {
			byte[] btInput = s.getBytes(encoding);
			// 获得MD5摘要算法的 MessageDigest 对象
			MessageDigest mdInst = MessageDigest.getInstance("MD5");
			// 使用指定的字节更新摘要
			mdInst.update(btInput);
			// 获得密文
			byte[] md = mdInst.digest();
			// 把密文转换成十六进制的字符串形式
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				str[k++] = HEX_DIGITS[byte0 >>> 4 & 0xf];
				str[k++] = HEX_DIGITS[byte0 & 0xf];
			}
			return new String(str);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	public final static String MD5AddKey(String s, String key) {
		s += key;
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'A', 'B', 'C', 'D', 'E', 'F' };
		try {
			byte[] btInput = s.getBytes();
			// 获得MD5摘要算法�?MessageDigest 对象
			MessageDigest mdInst = MessageDigest.getInstance("MD5");
			// 使用指定的字节更新摘�?
			mdInst.update(btInput);
			// 获得密文
			byte[] md = mdInst.digest();
			// 把密文转换成十六进制的字符串形式
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(str);
		} catch (Exception e) {
			SuperLogger.error(e.getMessage(), e);
			return "";
		}
	}
	

	private static String getFormattedText(byte[] bytes) {
		int len = bytes.length;
		StringBuilder buf = new StringBuilder(len * 2);
		// 把密文转换成十六进制的字符串形式
		for (int j = 0; j < len; j++) {
			buf.append(HEX_DIGITS[(bytes[j] >> 4) & 0x0f]);
			buf.append(HEX_DIGITS[bytes[j] & 0x0f]);
		}
		return buf.toString();
	}

	public static String SHA1(String str) {
		if (str == null) {
			return null;
		}
		try {
			MessageDigest messageDigest = MessageDigest.getInstance("SHA1");
			messageDigest.update(str.getBytes());
			return getFormattedText(messageDigest.digest());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public static String SHA256(String str) {
		if (str == null) {
			return null;
		}
		try {
			MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
			messageDigest.update(str.getBytes());
			return getFormattedText(messageDigest.digest());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private static final String Algorithm = "DESede"; // 定义 加密算法,可用
														// DES,DESede,Blowfish

	// / <summary>
	// / 3des解码
	// / </summary>
	// / <param name="value">待解密字符串</param>
	// / <param name="key">原始密钥字符串</param>
	// / <returns></returns>
	public static String Decrypt3DES(String value, String key) throws Exception {
		byte[] b = decryptMode(key.getBytes(), Base64.getDecoder().decode(value));
		return new String(b,"UTF-8");
	}

	// / <summary>
	// / 3des加密
	// / </summary>
	// / <param name="value">待加密字符串</param>
	// / <param name="strKey">原始密钥字符串</param>
	// / <returns></returns>
	public static String Encrypt3DES(String value, String key) throws Exception {
		String str = byte2Base64(encryptMode(key.getBytes(), value.getBytes("UTF-8")));
		return str;
	}

	/**
	 * 3des加密
	 * @param data 待加密串
	 * @param key 24位秘钥
	 * @param encode 编码
	 * @return 输出结果为16进制
	 * @throws Exception
	 */
	public static String encrypt3desToHex(String data, String key, String encode) throws Exception {
		String str = getFormattedText(encryptMode(key.getBytes(encode), data.getBytes(encode)));
		return str;
	}
	
	/**
	 * 3des解密
	 * @param value 16进制字符
	 * @param key 24位秘钥
	 * @param encode 编码
	 * @return
	 * @throws Exception
	 */
	public static String decrypt3desToHex(String value, String key, String encode) throws Exception {
		byte[] b = decryptMode(key.getBytes(encode), hexStringToBytes(value));
		return new String(b, encode);
	}
	
	/**
	 * 将十六进制字符串转换为字节 
	 * @param hexString 需转换字符串
	 * @return
	 */
    public static byte[] hexStringToBytes(String hexString) {  
        if (hexString == null || hexString.equals("")) {  
            return null;  
        }  
        hexString = hexString.toUpperCase();  
        int length = hexString.length() / 2;  
        char[] hexChars = hexString.toCharArray();  
        byte[] d = new byte[length];  
        for (int i = 0; i < length; i++) {  
            int pos = i * 2;  
            d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));  
        }  
        return d;  
    }
    
    private static byte charToByte(char c) {  
    	return (byte) "0123456789ABCDEF".indexOf(c);  
    }
    
	// keybyte为加密密钥，长度为24字节
	// src为被加密的数据缓冲区（源）
	public static byte[] encryptMode(byte[] keybyte, byte[] src) {
		try {
			// 生成密钥
			SecretKey deskey = new SecretKeySpec(keybyte, Algorithm); // 加密
			Cipher c1 = Cipher.getInstance(Algorithm);
			c1.init(Cipher.ENCRYPT_MODE, deskey);
			return c1.doFinal(src);
		} catch (java.security.NoSuchAlgorithmException e1) {
			e1.printStackTrace();
		} catch (javax.crypto.NoSuchPaddingException e2) {
			e2.printStackTrace();
		} catch (java.lang.Exception e3) {
			e3.printStackTrace();
		}
		return null;
	}

	// keybyte为加密密钥，长度为24字节
	// src为加密后的缓冲区
	private static byte[] decryptMode(byte[] keybyte, byte[] src) {
		try { // 生成密钥
			SecretKey deskey = new SecretKeySpec(keybyte, Algorithm);
			// 解密
			Cipher c1 = Cipher.getInstance(Algorithm);
			c1.init(Cipher.DECRYPT_MODE, deskey);
			return c1.doFinal(src);
		} catch (java.security.NoSuchAlgorithmException e1) {
			e1.printStackTrace();
		} catch (javax.crypto.NoSuchPaddingException e2) {
			e2.printStackTrace();
		} catch (java.lang.Exception e3) {
			e3.printStackTrace();
		}
		return null;
	}

	// 转换成base64编码
	public static String byte2Base64(byte[] b) {
		return Base64.getEncoder().encodeToString(b);
	}
	
	/**
	 * @param encryptdata
	 *            要加密的明码
	 * @param encryptkey
	 *            加密的密钥
	 * @return 加密后的暗码
	 * @throws Exception
	 */
	public static String encrypt(String encryptdata, String encryptkey)
			throws Exception {
		try{
			DesUtil desPlus = new DesUtil(encryptkey);
			return desPlus.encrypt(encryptdata);
		}catch(Exception e){
			e.printStackTrace();
		}
		return "";
	}
	
	/**
	 * @param decryptdata
	 *            要解密的暗码
	 * @param decryptkey
	 *            解密的密钥
	 * @return 解密后的明码
	 * @throws Exception
	 */
	public static String decrypt(String decryptdata, String decryptkey)
			throws Exception {
		DesUtil desPlus = new DesUtil(decryptkey);
		return desPlus.decrypt(decryptdata);
	}
	
	public static void main(String[] args) {
		try {
			System.out.println(Encrypt3DES("jdbc:mysql://bj-cdb-9smxilcn.sql.tencentcdb.com:63844/sharedevice","eFxiWKaEkjM3vDdxSop6jXI4"));
			System.out.println(Decrypt3DES("I8lcnvZroP8QInLZhHpKL8tGQer/wMhW67J2+l6Ca0t4jeNlERP2yvriNndH+r8thSIsu51aZPyvficlicVm2A==","eFxiWKaEkjM3vDdxSop6jXI4"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
