package com.hero.wireless.json;

import com.drondea.wireless.util.SuperLogger;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hero.wireless.web.exception.BaseException;

/**
 * ObjectMapper 单例，线程安全
 * 
 * @author Lenovo
 *
 */
public class JsonUtil {
	/* 标准 */
	public static final ObjectMapper STANDARD;

	/* 忽略NULL */
	public static final ObjectMapper NON_NULL;

	static {
		STANDARD = new ObjectMapper();
		STANDARD.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		NON_NULL = new ObjectMapper();
		NON_NULL.setSerializationInclusion(Include.NON_NULL);
		NON_NULL.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
	}

	/**
	 * 
	 * @param <T>
	 * @param src
	 * @param valueTypeRef
	 * @return
	 * @author volcano
	 * @date 2019年9月15日下午10:59:23
	 * @version V1.0
	 */
	public static <T> T readValue(byte[] src, TypeReference<T> valueTypeRef) {
		try {
			return STANDARD.readValue(src, valueTypeRef);
		} catch (Exception e) {
			SuperLogger.error(e.getMessage());
			throw new BaseException(e.getMessage(), e);
		}
	}

	/**
	 * 
	 * @param <T>
	 * @param src
	 * @param valueType
	 * @return
	 * @author volcano
	 * @date 2019年9月15日下午10:59:27
	 * @version V1.0
	 */
	public static <T> T readValue(byte[] src, Class<T> valueType) {
		try {
			return STANDARD.readValue(src, valueType);
		} catch (Exception e) {
			SuperLogger.error(e.getMessage());
			throw new BaseException(e.getMessage(), e);
		}
	}

	public static <T> T readValue(String content, TypeReference<T> valueTypeRef) {
		try {
			return STANDARD.readValue(content, valueTypeRef);
		} catch (Exception e) {
			SuperLogger.error(e.getMessage());
			throw new BaseException(e.getMessage(), e);
		}
	}
	
	public static <T> T readValue(String content, Class<T> valueType) {
		try {
			return STANDARD.readValue(content, valueType);
		} catch (Exception e) {
			SuperLogger.error(e.getMessage());
			throw new BaseException(e.getMessage(), e);
		}
	}

	/**
	 * 
	 * @param value
	 * @return
	 * @author volcano
	 * @date 2019年10月2日上午7:51:29
	 * @version V1.0
	 */
	public static String writeValueAsString(Object value) {
		try {
			return STANDARD.writeValueAsString(value);
		} catch (Exception e) {
			SuperLogger.error(e.getMessage());
			throw new BaseException(e.getMessage(), e);
		}
	}

	/**
	 *
	 * @param value
	 * @return
	 * @author volcano
	 * @date 2019年10月2日上午7:51:29
	 * @version V1.0
	 */
	public static String writeValueNoNull(Object value) {
		try {
			return NON_NULL.writeValueAsString(value);
		} catch (Exception e) {
			SuperLogger.error(e.getMessage());
			throw new BaseException(e.getMessage(), e);
		}
	}

	/**
	 * 尝试转json，不能转的数据返回String
	 * @param value
	 * @return
	 * @author gengjinbiao
	 * @date 2020年2月25日
	 * @version V1.0
	 */
	public static String writeJsonOrStringValueNoNull(Object value) {
		try {
			return NON_NULL.writeValueAsString(value);
		} catch (Exception e) {
			return String.valueOf(value);
		}
	}

}
