package com.hero.wireless.web.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.hero.wireless.json.JsonUtil;
import com.hero.wireless.web.entity.business.Channel;
import com.hero.wireless.web.entity.business.Code;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.io.Serializable;
import java.util.Map;
import java.util.TreeMap;

/**
 * 
 * 
 * @author volcano
 * @date 2019年10月2日上午7:59:38
 * @version V1.0
 */
public abstract class ChannelUtil {

	/**
	 * 空频次限制对象
	 */
	public static LimitRepeat BLANK_LIMIT_REPEAT = new LimitRepeat();

	/**
	 * 
	 * 
	 * @author volcano
	 * @date 2019年10月2日上午8:01:15
	 * @version V1.0
	 */
	public static class OtherParameter implements Serializable {
		private static final long serialVersionUID = 7963434198822325844L;
		private String name;
		private String code;
		private String value;
		private String remark;

		public OtherParameter(Code code) {
			this.name = code.getName();
			this.code = code.getCode();
			this.value = code.getValue();
			this.remark = code.getRemark();
		}

		public OtherParameter() {

		}

		public OtherParameter(String code, String value) {
			this.code = code;
			this.value = value;
		}

		public String getCode() {
			return code;
		}

		public void setCode(String code) {
			this.code = code;
		}

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}

		public String getRemark() {
			return remark;
		}

		public void setRemark(String remark) {
			this.remark = remark;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

	}

	/**
	 * 接口标致,导流策略检查结果
	 * 
	 * @author zly
	 */
	public static interface IDiversionResult {
	}

	/**
	 * 限制数据结构
	 * 
	 * @author volcano
	 * @date 2019年10月2日上午11:58:56
	 * @version V1.0
	 */
	public static class LimitRepeat implements Serializable {
		/**
		 * 
		 */
		private static final long serialVersionUID = 924017599590583565L;
		private TreeMap<Integer, Integer> phoneNos;
		private TreeMap<Integer, Integer> content;

		public LimitRepeat() {
			phoneNos = new TreeMap<Integer, Integer>();
			content = new TreeMap<Integer, Integer>();
		}

		public TreeMap<Integer, Integer> getPhoneNos() {
			return phoneNos;
		}

		public void setPhoneNos(TreeMap<Integer, Integer> phoneNos) {
			this.phoneNos = phoneNos;
		}

		public TreeMap<Integer, Integer> getContent() {
			return content;
		}

		public void setContent(TreeMap<Integer, Integer> content) {
			this.content = content;
		}
	}

	/**
	 * 
	 * 
	 * @author volcano
	 * @date 2019年10月2日下午7:00:12
	 * @version V1.0
	 */
	public static class IncludeSignature implements IDiversionResult {
		private String signature;
		private String subCode;

		public IncludeSignature() {
			super();
		}

		public IncludeSignature(String signature, String subCode) {
			super();
			this.signature = signature;
			this.subCode = subCode;
		}

		public String getSignature() {
			return signature;
		}

		public void setSignature(String signature) {
			this.signature = signature;
		}

		public String getSubCode() {
			return subCode;
		}

		public void setSubCode(String subCode) {
			this.subCode = subCode;
		}

	}

	/**
	 * 
	 * @param channel
	 * @return
	 * @author volcano
	 * @date 2019年10月2日上午8:17:25
	 * @version V1.0
	 */
	public static Map<String, OtherParameter> getParameter(Channel channel) {
		String otherParameter = channel.getOther_Parameter();
		Map<String, OtherParameter> map = JsonUtil.readValue(otherParameter,
				new TypeReference<Map<String, OtherParameter>>() {
				});
		return map;
	}

	/**
	 * 
	 * @param parameterMap
	 * @param code
	 * @param defaultValue
	 * @return
	 * @author volcano
	 * @date 2019年10月2日上午8:37:36
	 * @version V1.0
	 */
	public static String getParameterValue(Map<String, OtherParameter> parameterMap, String code, String defaultValue) {
		OtherParameter otherParameter = parameterMap.get(code);
		return otherParameter == null ? defaultValue
				: StringUtils.defaultIfEmpty(otherParameter.getValue(), defaultValue);
	}

	/**
	 * 如果一个方法，获取多次参数，不建议使用此方法
	 * 
	 * @param channel
	 * @param code
	 * @param defaultValue
	 * @return
	 * @author volcano
	 * @date 2019年10月2日上午8:56:16
	 * @version V1.0
	 */
	public static String getParameter(Channel channel, String code, String defaultValue) {
		String otherParameter = channel.getOther_Parameter();
		Map<String, OtherParameter> map = JsonUtil.readValue(otherParameter,
				new TypeReference<Map<String, OtherParameter>>() {
				});
		return getParameterValue(map, code, defaultValue);
	}

	/**
	 * 
	 * @param parameterMap
	 * @param code
	 * @param defaultValue
	 * @return
	 * @author volcano
	 * @date 2019年10月2日上午8:40:56
	 * @version V1.0
	 */
	public static byte getParameterByteValue(Map<String, OtherParameter> parameterMap, String code, byte defaultValue) {
		OtherParameter otherParameter = parameterMap.get(code);
		return otherParameter == null ? defaultValue : NumberUtils.toByte(otherParameter.getValue(), defaultValue);
	}

	/**
	 * 
	 * @param parameterMap
	 * @param code
	 * @param defaultValue
	 * @return
	 * @author volcano
	 * @date 2019年10月2日上午8:40:54
	 * @version V1.0
	 */
	public static int getParameterIntValue(Map<String, OtherParameter> parameterMap, String code, int defaultValue) {
		OtherParameter otherParameter = parameterMap.get(code);
		return otherParameter == null ? defaultValue : NumberUtils.toInt(otherParameter.getValue(), defaultValue);
	}

	/**
	 * 是否号码池策略
	 * 
	 * @param channel
	 * @return
	 */
	public static boolean isPhoneNoPoolStrategy(Channel channel) {
		String phoneNoPoolStrategyStatus = getParameter(channel, "phone_no_pool_strategy_status", "stop");
		return phoneNoPoolStrategyStatus.equals("start");
	}

	/**
	 * 
	 * @param parameterMap
	 * @param code
	 * @param defaultValue
	 * @return
	 * @author volcano
	 * @date 2019年10月2日上午8:40:51
	 * @version V1.0
	 */
	public static short getParameterShortValue(Map<String, OtherParameter> parameterMap, String code,
			short defaultValue) {
		OtherParameter otherParameter = parameterMap.get(code);
		return otherParameter == null ? defaultValue : NumberUtils.toShort(otherParameter.getValue(), defaultValue);
	}

	/**
	 * 
	 * @param parameterMap
	 * @param code
	 * @param defaultValue
	 * @return
	 * @author volcano
	 * @date 2019年10月2日上午8:49:32
	 * @version V1.0
	 */
	public static boolean getParameterBooleanValue(Map<String, OtherParameter> parameterMap, String code,
			boolean defaultValue) {
		OtherParameter otherParameter = parameterMap.get(code);
		if (otherParameter == null) {
			return defaultValue;
		}
		String value = otherParameter.getValue();
		if (StringUtils.isEmpty(value)) {
			return defaultValue;
		}
		if (NumberUtils.isDigits(value)) {
			return BooleanUtils.toBoolean(Integer.parseInt(value));
		}
		return BooleanUtils.toBoolean(value);
	}

}
