package com.drondea.wireless.util;

import org.apache.commons.lang.StringUtils;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;


/**
 * 秘钥证书工具类
 * 
 * @author Administrator
 *
 */
public class CertificateUtil {

	// 非对称密钥算法
	public static final String KEY_ALGORITHM = "RSA";

	public static final int KEY_SIZE = 1024;

	public static final String PUBLIC_KEY = "publicKey";

	public static final String PRIVATE_KEY = "privateKey";

	private CertificateUtil() {
	}

	/**
	 * 初始化生成公钥私钥的类
	 * 
	 * @return
	 * @throws NoSuchAlgorithmException
	 */
	public static KeyPairGenerator initKeyPairGenerator() throws NoSuchAlgorithmException {
		// 实例化密钥生成器
		KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(KEY_ALGORITHM);
		// 初始化密钥生成器
		keyPairGenerator.initialize(KEY_SIZE);
		return keyPairGenerator;
	}

	/**
	 * 生成秘钥对
	 * 
	 * @return Map
	 */
	public static Map<String, Object> createKey() {
		// 将生成的公钥私钥放到map中
		Map<String, Object> keyMap = new HashMap<String, Object>();
		try {
			KeyPairGenerator keyPairGenerator = initKeyPairGenerator();
			// 生成密钥对
			KeyPair keyPair = keyPairGenerator.generateKeyPair();
			// 公钥
			RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
			byte[] publicKeyBytes = publicKey.getEncoded();
		    String pub = Base64.getEncoder().encodeToString(publicKeyBytes);
			// 私钥
			RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
			byte[] privateKeyBytes = privateKey.getEncoded();
		    String pri =Base64.getEncoder().encodeToString(privateKeyBytes);
			keyMap.put(PUBLIC_KEY, pub);
			keyMap.put(PRIVATE_KEY, pri);
		} catch (NoSuchAlgorithmException e) {
			SuperLogger.error("获取加密实例出错，没有找到对应的加密方式" + e.getMessage(), e);
		}
		return keyMap;

	}

	/**
	 * 公钥加密
	 * 
	 * @param data
	 * @param key
	 * @return
	 */
	public static String encryptByPublicKey(String data, String key) {
		try {
			return Base64Utils.encode(encryptByPublicKey(data.getBytes("UTF-8"), Base64Utils.decode(key)));
		} catch (UnsupportedEncodingException e) {
			SuperLogger.error("公钥加密异常", e);
			return null;
		}
	}

	/**
	 * 公钥加密
	 * 
	 * @param data待加密数据
	 * @param key
	 *            密钥
	 * @return byte[] 加密数据
	 */
	public static byte[] encryptByPublicKey(byte[] data, byte[] key) {

		// 实例化密钥工厂
		KeyFactory keyFactory;
		try {
			keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
			// 密钥材料转换
			X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(key);
			// 产生公钥
			PublicKey pubKey = keyFactory.generatePublic(x509KeySpec);
			// 数据加密
			Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
			cipher.init(Cipher.ENCRYPT_MODE, pubKey);
			int blockSize = cipher.getOutputSize(data.length) - 11;
			return doFinal(data, cipher,blockSize);

		} catch (NoSuchAlgorithmException e) {
			SuperLogger.error("公钥加密出错：NoSuchAlgorithmException", e);
		} catch (InvalidKeySpecException e) {
			SuperLogger.error("公钥加密异常：InvalidKeySpecException", e);
		} catch (NoSuchPaddingException e) {
			SuperLogger.error("公钥加密异常：NoSuchPaddingException", e);
		} catch (InvalidKeyException e) {
			SuperLogger.error("公钥加密异常：InvalidKeyException", e);
		} catch (IllegalBlockSizeException e) {
			SuperLogger.error("公钥加密异常：IllegalBlockSizeException", e);
		} catch (BadPaddingException e) {
			SuperLogger.error("公钥加密异常：BadPaddingException", e);
		} catch (IOException e) {
			SuperLogger.error("公钥加密异常：IOException", e);
		}
		return null;
	}
	
	/**
	 * 公钥解密
	 * @param data
	 * @param key
	 * @return
	 */
	public static String decryptByPublicKey(String data,String key){
		
		try {
			return new String(decryptByPublicKey(data.getBytes("UTF-8"), Base64Utils.decode(key)),"UTF-8");
		} catch (Exception e) {
			SuperLogger.error("公钥解密异常",e);
		}
		return null;
	}
	
	  /** 
     * 用公钥解密 
     *  
     * @param data 
     * @param key 
     * @return 
     * @throws Exception 
     */  
    public static byte[] decryptByPublicKey(byte[] data, byte[] key)  
            throws Exception {  
        // 取得公钥  
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(key);  
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);  
        Key publicKey = keyFactory.generatePublic(x509KeySpec);  
  
        // 对数据解密  
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());  
        cipher.init(Cipher.DECRYPT_MODE, publicKey);  
     	int blockSize = cipher.getOutputSize(data.length) - 11;
  
        return doFinal(data,cipher,blockSize);  
    }

	/**
	 * 私钥解密
	 * 
	 * @param data
	 *            待解密数据
	 * @param key
	 *            密钥
	 * @return byte[] 解密数据
	 */
	public static byte[] decryptByPrivateKey(byte[] data, byte[] key) {
		try {
			// 取得私钥
			PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(key);
			KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
			// 生成私钥
			PrivateKey privateKey = keyFactory.generatePrivate(pkcs8KeySpec);
			// 数据解密
			Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
			cipher.init(Cipher.DECRYPT_MODE, privateKey);
			int blockSize = cipher.getOutputSize(data.length);
			return doFinal(data, cipher,blockSize);
		} catch (NoSuchAlgorithmException e) {
			SuperLogger.error("私钥解密异常：NoSuchAlgorithmException", e);
		} catch (InvalidKeySpecException e) {
			SuperLogger.error("私钥解密异常：InvalidKeySpecException", e);
		} catch (NoSuchPaddingException e) {
			SuperLogger.error("私钥解密异常：NoSuchPaddingException", e);
		} catch (InvalidKeyException e) {
			SuperLogger.error("私钥解密异常：InvalidKeyException", e);
		} catch (IllegalBlockSizeException e) {
			SuperLogger.error("私钥解密异常：IllegalBlockSizeException", e);
		} catch (BadPaddingException e) {
			SuperLogger.error("私钥解密异常：BadPaddingException", e);
		} catch (IOException e) {
			SuperLogger.error("私钥解密异常：IOException", e);
		}
		return null;
	}

	/**
	 * 私钥解密
	 * 
	 * @param data
	 *            待解密数据
	 * @param key
	 *            密钥
	 * @return String 解密数据
	 */
	public static String decryptByPrivateKey(String data, String key) {
		try {
			byte[] decryptStr = decryptByPrivateKey(Base64Utils.decode(data), Base64Utils.decode(key));
			return new String(decryptStr, "UTF-8");
		} catch (IOException e) {
			SuperLogger.error("转换出错", e);
		}
		return null;
	}
	
	/**
	 * 私钥加密
	 * @param data
	 * @param key
	 * @return
	 */
	public static String encryptByPrivateKey(String data,String key){
		try {
			byte[] decryptStr = encryptByPrivateKey(data.getBytes(), Base64Utils.decode(key));
			return Base64Utils.encode(decryptStr);
		} catch (Exception e) {
			SuperLogger.error("私钥加密异常", e);
		}
		return null;
	}
	
	/** 
     * 用私钥加密 
     *  
     * @param data 
     * @param key 
     * @return 
     * @throws Exception 
     */  
    public static byte[] encryptByPrivateKey(byte[] data, byte[] key)  
            throws Exception {  
        // 取得私钥  
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(key);  
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);  
        Key privateKey = keyFactory.generatePrivate(pkcs8KeySpec);  
  
        // 对数据加密  
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());  
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);  
  
        return cipher.doFinal(data);  
    }  

	/**
	 * 取得私钥
	 * 
	 * @param keyMap
	 *            密钥map
	 * @return byte[] 私钥
	 */
	protected static byte[] getPrivateKey(Map<String, Object> keyMap) {
		Key key = (Key) keyMap.get(PRIVATE_KEY);
		return key.getEncoded();
	}

	/**
	 * 取得私钥 String
	 * 
	 * @param key
	 * @return
	 */
	public static String getPrivateKeyStr(Map<String, Object> keyMap) {
		return Base64.getEncoder().encodeToString(getPrivateKey(keyMap));
	}

	/**
	 * 取得私钥 String
	 * 
	 * @param key
	 * @return
	 */
	@Deprecated
	public static String getPrivateKey(byte[] key) {
		return Base64.getEncoder().encodeToString(key);
	}

	/**
	 * 取得公钥
	 * 
	 * @param keyMap
	 *            密钥map
	 * @return byte[] 公钥
	 */
	protected static byte[] getPublicKey(Map<String, Object> keyMap) {
		Key key = (Key) keyMap.get(PUBLIC_KEY);
		return key.getEncoded();
	}

	/**
	 * 获得公钥
	 * 
	 * @param key
	 * @return
	 */
	public static String getPublicKeyStr(Map<String, Object> keyMap) {
		return Base64.getEncoder().encodeToString(getPublicKey(keyMap));
	}

	/**
	 * 获得公钥
	 * 
	 * @param key
	 * @return
	 */
	@Deprecated
	public static String getPublicKey(byte[] key) {

		return Base64.getEncoder().encodeToString(key);
	}

	/**
	 * 公钥加密方法 分段加密 返回加密后的字符串
	 * 
	 * @param encryptData
	 *            待加密的字符串
	 * @param cerPath
	 *            证书路径
	 * @return
	 */
	public static byte[] encryption(String encryptData, String cerPath) {
		try {
			if (StringUtils.isEmpty(encryptData)) {
				SuperLogger.error("待解密数据为空");
				return null;
			}
			if (StringUtils.isEmpty(cerPath)) {
				SuperLogger.error("证书路径为空");
				return null;
			}
			return encryption(encryptData.getBytes(), cerPath);
		} catch (UnrecoverableKeyException e) {
			SuperLogger.error("获取公钥失败：" + e.getMessage(), e);
		} catch (InvalidKeyException e) {
			SuperLogger.error("公钥无效：" + e.getMessage(), e);
		} catch (KeyStoreException e) {
			SuperLogger.error("秘钥库异常:" + e.getMessage(), e);
		} catch (NoSuchAlgorithmException e) {
			SuperLogger.error("NoSuchAlgorithmException:" + e.getMessage(), e);
		} catch (CertificateException e) {
			SuperLogger.error("证书异常：" + e.getMessage(), e);
		} catch (NoSuchPaddingException e) {
			SuperLogger.error("补位异常：" + e.getMessage(), e);
		} catch (IOException e) {
			SuperLogger.error("IO流异常：" + e.getMessage(), e);
		} catch (IllegalBlockSizeException e) {
			SuperLogger.error("BlockSize异常：" + e.getMessage(), e);
		} catch (BadPaddingException e) {
			SuperLogger.error("补位异常：" + e.getMessage(), e);
		}
		return null;
	}

	/**
	 * 解密方法 返回解密后的字符串
	 * 
	 * @param decryptData
	 *            带解密字符串
	 * @param cerPath
	 *            证书路径
	 * @param keystorePassword
	 *            证书库密码
	 * @param cerPassword
	 *            证书密码
	 * @return
	 */
	public static String cerDecryption(byte[] decryptData, String cerPath, String keystorePassword, String cerPassword,
			String storeKey) {
		try {
			if (StringUtils.isEmpty(cerPath)) {
				SuperLogger.error("证书路径为空");
				return null;
			}
			if (StringUtils.isEmpty(keystorePassword)) {
				SuperLogger.error("私钥密码为空");
				return null;
			}
			if (StringUtils.isEmpty(cerPassword)) {
				SuperLogger.error("公钥密码为空");
				return null;
			}
			if (StringUtils.isEmpty(storeKey)) {
				SuperLogger.error("秘钥库别名位空");
				return null;
			}
			byte[] res = decryption(decryptData, cerPath, keystorePassword, cerPassword, storeKey);
			return new String(res, "UTF8");
		} catch (UnrecoverableKeyException e) {
			SuperLogger.error("获取私钥失败：" + e.getMessage(), e);
		} catch (InvalidKeyException e) {
			SuperLogger.error("私钥无效：" + e.getMessage(), e);
		} catch (KeyStoreException e) {
			SuperLogger.error("秘钥库异常:" + e.getMessage(), e);
		} catch (NoSuchAlgorithmException e) {
			SuperLogger.error("NoSuchAlgorithmException:" + e.getMessage(), e);
		} catch (CertificateException e) {
			SuperLogger.error("证书异常：" + e.getMessage(), e);
		} catch (NoSuchPaddingException e) {
			SuperLogger.error("补位异常：" + e.getMessage(), e);
		} catch (IOException e) {
			SuperLogger.error("IO流异常：" + e.getMessage(), e);
		} catch (IllegalBlockSizeException e) {
			SuperLogger.error("BlockSize异常：" + e.getMessage(), e);
		} catch (BadPaddingException e) {
			SuperLogger.error("补位异常：" + e.getMessage(), e);
		}
		return null;
	}

	/**
	 * 解密方法 返回字节数组
	 * 
	 * @param decryptData
	 * @param cerPath
	 * @param keystorePassword
	 * @param cerPassword
	 * @return
	 * @throws KeyStoreException
	 * @throws NoSuchAlgorithmException
	 * @throws CertificateException
	 * @throws IOException
	 * @throws UnrecoverableKeyException
	 * @throws NoSuchPaddingException
	 * @throws InvalidKeyException
	 * @throws BadPaddingException
	 * @throws IllegalBlockSizeException
	 */
	public static byte[] decryption(byte[] decryptData, String cerPath, String keystorePassword, String cerPassword,
			String storeKey) throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException,
			UnrecoverableKeyException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException,
			BadPaddingException {
		// 用证书的私钥解密 - 该私钥存在生成该证书的密钥库中
		FileInputStream fis = new FileInputStream(cerPath);
		KeyStore ks = KeyStore.getInstance("JKS"); // 加载证书库
		char[] kspwd = keystorePassword.toCharArray(); // 证书库密码
		char[] keypwd = cerPassword.toCharArray(); // 证书密码
		ks.load(fis, kspwd); // 加载证书
		PrivateKey pk = (PrivateKey) ks.getKey(storeKey, keypwd); // 获取证书私钥
		fis.close();
		Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
		cipher.init(Cipher.DECRYPT_MODE, pk);
		int blockSize = cipher.getOutputSize(decryptData.length);
		return doFinal(decryptData, cipher,blockSize);
	}

	/**
	 * 证书加密方法
	 * 
	 * @param encryptDat
	 *            待加密字节
	 * @param cerPath
	 *            证书路径
	 * @return
	 * @throws KeyStoreException
	 * @throws NoSuchAlgorithmException
	 * @throws CertificateException
	 * @throws IOException
	 * @throws UnrecoverableKeyException
	 * @throws NoSuchPaddingException
	 * @throws InvalidKeyException
	 * @throws BadPaddingException
	 * @throws IllegalBlockSizeException
	 */
	public static byte[] encryption(byte[] encryptData, String cerPath) throws KeyStoreException,
			NoSuchAlgorithmException, CertificateException, IOException, UnrecoverableKeyException,
			NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		// 用证书的公钥加密
		CertificateFactory cff = CertificateFactory.getInstance("X.509");
		FileInputStream fis = new FileInputStream(cerPath); // 证书文件
		Certificate cf = cff.generateCertificate(fis);
		PublicKey pk = cf.getPublicKey(); // 得到证书文件携带的公钥
		Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding"); // 定义算法：RSA
		cipher.init(Cipher.ENCRYPT_MODE, pk);
		int blockSize = cipher.getOutputSize(encryptData.length) - 11;
		return doFinal(encryptData, cipher,blockSize);
	}

	/**
	 * 加密解密共用核心代码，分段加密解密
	 * 
	 * @param decryptData
	 * @param cipher
	 * @return
	 * @throws BadPaddingException
	 * @throws IllegalBlockSizeException
	 * @throws IOException
	 */
	public static byte[] doFinal(byte[] decryptData, Cipher cipher,int blockSize)
			throws IllegalBlockSizeException, BadPaddingException, IOException {
		int offSet = 0;
		byte[] cache = null;
		int i = 0;
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		while (decryptData.length - offSet > 0) {
			if (decryptData.length - offSet > blockSize) {
				cache = cipher.doFinal(decryptData, offSet, blockSize);
			} else {
				cache = cipher.doFinal(decryptData, offSet, decryptData.length - offSet);
			}
			out.write(cache, 0, cache.length);
			i++;
			offSet = i * blockSize;
		}
		byte[] encryptedData = out.toByteArray();
		out.close();
		return encryptedData;
	}

	public static void test() throws Exception {
		String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDEsVYKcT7U9NtuzjwDT+w3IL7QxXiz/MKt2YgBeZRbVNJQySq+TiT8K53km+1LdowB7eamVfwrVXnLiHXPkguXGJ9Qv2iJ9lQY5g36lU5xsdH/6q3einSUC/o14PjS/PzdZrvxYIWiRW6M6WhRxbcJIwwUJQWeSyhUTA0gq8CytwIDAQAB";
		String privateKey = "MIICeQIBADANBgkqhkiG9w0BAQEFAASCAmMwggJfAgEAAoGBAMSxVgpxPtT0227OPANP7DcgvtDFeLP8wq3ZiAF5lFtU0lDJKr5OJPwrneSb7Ut2jAHt5qZV/CtVecuIdc+SC5cYn1C/aIn2VBjmDfqVTnGx0f/qrd6KdJQL+jXg+NL8/N1mu/FghaJFbozpaFHFtwkjDBQlBZ5LKFRMDSCrwLK3AgMBAAECgYEArigG8wAKjiywDpB0+snNcZKA4gG3OkbXaW9uyT+JaMTV48n7Gr2+abmE+GJH3cV9ekfXLfh8azInwVt2VPAPaZAAq5rpiGJ37czfmnM85u8VxxnuuB2qAcldTpvTE1Hr9wATnwKXlTu16RFoaN5uzXWRmVMaUamYX+ucLwgz7AECQQDrf7IhoiBfiWMg9c+DdmRX/NAq4XRGUFQ6kdgTvzdnxBk+cazaF0AsxnJcEjATjhJBqjJT7Ciugj7mOMiGmxGBAkEA1dDPtg1EUb01i7gMxofou/HsW6DIlfo3pn65jywGCm8YobkKLO7+Jhbr2CtdHgkTzTdk0oxM60p5HnvMpYfwNwJBAJmn+wk46JiNYvGZkWl8cSJ74TNpYV2TgjGpAWx5AmShLbtxnk+6hfPxE6MBdv77XuwbabUaTLz4XKQIfsZdHYECQQCn0u42BtPJFKoisKPWcHbMjmbmxJysm2ly1aTwK63SIsFCruq9uhjh7LvRQLgmCxnvybrpSBZX2GyY8LMAemKhAkEAyVt37q4zL9o82VUVehO8ul2GAhIuOt/gdZs7D/3CxsH8PYhfgLGU+TMYfL2w4qxaluYAkqTs6Uaf/9GoqrWYhA==";
		String enStr = "be6DbLc4Dtiz9F8lJCAWegQv/WUP2Ty2LnoJJftWoxxh3AW3m0ZUzAWTsWxtbJRrfFcOBsZ30XLT\n0RmI9VQutuhGsEYEqYDlVL6f95aXbhqO76wxYhHsqjVuSFFpV+s9o/ahdekCsfWoE8IHCdMhvbVf\nQ3gd7r3rWUJQdxjWZ3ht4zGASuPzauMw42kiuHYZKLFhdcSgGpsS4i/vgdtwouwOqhUsbiHOvW/1\nIHMRE2bCR5cOGr/5LWgxdCrDh7AdRNRWVTw5CQnKewAjjSPWHoVr+UDRykIwcz00Y45aJOyA1jI6\nFA/av7SJ8xhrL1BxoLdYZjA1qGg5B4KpCRc7pQ==";
		// System.out.println(enStr);
//		String s = decryptByPrivateKey(enStr, privateKey);
//		System.out.println(s);

		//
//		for (int i = 0; i < 1; i++) {
//			String newS = "This is a test!";
//			System.out.println("原文：" + newS);
//			String e = encryptByPublicKey(newS, publicKey);
			String e = "wo2ct4SmEaBhPpxPvY43eJFg5O2a9XxESeporu98LeTCqCac8kzpEE32zzTPQYGrhVo6wwSC+FeAdoDXOdDbLLtk3dvixELlDi0A72AK2QJbD7g3PIRNzu3x9nYDaanpzon9Ntfpg+eCdRiV+Nj2XNaUGOFbEnTlIuIQKlCeock=";
			System.out.println("密文：" + e);
			String s1 = decryptByPrivateKey(e, privateKey);
			System.out.println(s1);
			if (s1 == null) {
				System.out.println("===============================");
				System.exit(0);
			}
//		}
	}

	/*public static void testSign() throws Exception {  
        String inputStr = "sign";  
        Map<String, Object> map = createKey();
		String publicKey = getPublicKeyStr(map);
		System.out.println(publicKey);
		String privateKey = getPrivateKeyStr(map);
		System.out.println(privateKey);
        String encodedData = encryptByPrivateKey(inputStr, privateKey);
        System.out.println(encodedData);
        String decodedData = decryptByPublicKey(encodedData, publicKey);  
  
        System.out.println("加密前: " + inputStr + "\n\r" + "解密后: " + decodedData);  
    }  
  */
	public static void main(String[] args) throws Exception {
		test();
		//testSign();
		/*Map<String, Object> map = createKey();
		String publicKey = getPublicKeyStr(map);
		String privateKey = getPrivateKeyStr(map);
		System.out.println("公钥："+ publicKey);
		System.out.println("公钥："+ privateKey);
		String encryptStr = encryptByPublicKey("nihao hahh", publicKey);
		String s = decryptByPrivateKey(encryptStr, privateKey);
		System.out.println("解密后的内容："+s);
		new Thread() {
			public void run() {
				try {
					test();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}.start();
		new Thread() {
			public void run() {
				try {
					test();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}.start();*/
	}
}
