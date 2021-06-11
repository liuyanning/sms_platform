package com.hero.wireless.web.util;

import java.lang.reflect.Field;
import java.net.URLDecoder;

import org.apache.commons.lang.StringUtils;

import com.drondea.wireless.util.SuperLogger;

/**
 * 
 * @author 张丽阳
 * @createTime 2014年5月19日 下午5:07:20
 */
public class BeanUtil {

	/**txt文件BOM头编码*/
	public static final String UFEFF = "\uFEFF";

	/**
	 * bean 字符属性编码转换
	 * 
	 * @param bean
	 * @param fromEncode
	 * @param toEncode
	 * @return
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 */
	public static <T> T stringPropertyEncodeConvert(Class beanClass, T bean,
			String fromEncode, String toEncode) {
		if (bean == null) {
			return bean;
		}

		try {
			if (beanClass.getSuperclass() != null) {
				stringPropertyEncodeConvert(beanClass.getSuperclass(), bean,
						fromEncode, toEncode);
			}
			Field[] fieldArray = beanClass.getDeclaredFields();
			if (fieldArray == null || fieldArray.length == 0) {
				return bean;
			}
			for (int i = 0; i < fieldArray.length; i++) {
				Field field = fieldArray[i];
				if (field.getGenericType() == String.class) {
					String propertyName = new String(new byte[] { (byte) field
							.getName().charAt(0) }).toUpperCase()
							+ field.getName().substring(1,
									field.getName().length());
					String oldValue = (String) beanClass.getMethod(
							"get" + propertyName).invoke(bean);
					if (StringUtils.isEmpty(oldValue)) {
						continue;
					}
					SuperLogger.debug("oldValue:"+oldValue);
					String newValue = URLDecoder.decode(oldValue, toEncode);
					SuperLogger.debug("newValue:"+newValue);
					beanClass.getMethod("set" + propertyName,
							new Class[] { String.class }).invoke(bean,
							new String[] { newValue });
				}
			}
		} catch (Exception e) {
			SuperLogger.error(e.getMessage(), e);
		}
		return bean;
	}

	public static void main(String[] args) throws Exception {
		System.out.println(URLDecoder.decode(
				"%E5%8D%97%E5%8D%8E%E6%99%BA%E9%97%BB", "UTF-8"));
	}
}
