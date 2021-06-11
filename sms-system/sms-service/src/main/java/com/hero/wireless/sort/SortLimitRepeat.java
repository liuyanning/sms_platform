package com.hero.wireless.sort;

import com.drondea.wireless.util.SecretUtil;
import com.drondea.wireless.util.SuperLogger;
import com.hero.wireless.json.JsonUtil;
import com.hero.wireless.web.entity.send.Input;
import com.hero.wireless.web.util.ChannelUtil;
import com.hero.wireless.web.util.ChannelUtil.LimitRepeat;
import com.hero.wireless.web.util.PhoneRepeatCache;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;
import java.util.function.BiFunction;
import java.util.function.Supplier;

/**
 * 
 * 
 * @author volcano
 * @date 2019年10月2日下午12:21:26
 * @version V1.0
 */
public class SortLimitRepeat implements Serializable {
	private static final long serialVersionUID = 3828826192674078829L;

	private SortContext sortContext;
	private LimitRepeat limitRepeat;

	private String genRepeatMsgKey(int minute, String mobile) {
		Input input = sortContext.getInput();
		StringBuilder sb = new StringBuilder();
		sb.append(input.getEnterprise_User_Id()).append(minute).append(mobile).append(input.getContent());
		//repeat_content
		return SecretUtil.MD5(sb.toString());
	}

	// repeat_phone_no_list
	private String genRepeatPhoneNoKey(int minute, String mobile) {
		Input input = sortContext.getInput();
		StringBuilder sb = new StringBuilder();
		sb.append(input.getEnterprise_User_Id()).append(minute).append(mobile);
		//repeat_mobile
		return sb.toString();
	}

	private String genRepeatMsgKey(int minute, String mobile, SortChannelMap.SortChannel channel) {
		Input input = sortContext.getInput();
		StringBuilder sb = new StringBuilder();
		sb.append(channel.getProductChannels().getProduct_No()).append(channel.getChannel().getNo()).append(minute)
				.append(mobile).append(input.getContent());
		return "rpc:" + SecretUtil.MD5(sb.toString());
	}

	// repeat_phone_no_list
	private String genRepeatPhoneNoKey(int minute, String mobile, SortChannelMap.SortChannel channel) {
		StringBuilder sb = new StringBuilder();
		sb.append(channel.getProductChannels().getProduct_No()).append(channel.getChannel().getNo()).append(minute)
				.append(mobile);
		return "rpm:" + SecretUtil.MD5(sb.toString());
	}

	/**
	 * 
	 * @param sortContext
	 * @throws Exception
	 * @author volcano
	 * @date 2019年10月2日下午3:37:00
	 * @version V1.0
	 */
	public SortLimitRepeat(SortContext sortContext) {
		this.sortContext = sortContext;
		String limitJson = sortContext.getEnterpriseUser().getLimit_Repeat();
		if (StringUtils.isEmpty(limitJson)) {
			limitRepeat = ChannelUtil.BLANK_LIMIT_REPEAT;
			return;
		}
		try {
			limitRepeat = JsonUtil.NON_NULL.readValue(limitJson, LimitRepeat.class);
		} catch (Exception e) {
			SuperLogger.error(e.getMessage(), e);
		}
	}

	/**
	 * 0:验证成功
	 * 
	 * @param phoneNo
	 * @param limtSupplier
	 * @param keyFunction
	 * @return
	 * @author volcano
	 * @date 2019年10月2日下午3:26:45
	 * @version V1.0
	 */
	private int validateAndIncrementRepeat(String phoneNo, Supplier<TreeMap<Integer, Integer>> limtSupplier,
										   BiFunction<Integer, String, String> keyFunction) {
		TreeMap<Integer, Integer> limitMap = limtSupplier.get();
		if (ObjectUtils.isEmpty(limitMap)) {
			return 0;
		}
		return limitMap.keySet().stream().reduce(0, (lastMinute, minute) -> {
			String key = keyFunction.apply(minute, phoneNo);
			// increment不会重置超时时间
			int value = PhoneRepeatCache.increment(key);
			if (value == 1) {
				// 第一次进来设置超时时间
				PhoneRepeatCache.expire(key, minute, TimeUnit.MINUTES);
				return lastMinute > 0 ? lastMinute : 0;
			}
			Integer limitCount = limitMap.get(minute);
			if (limitCount == null || limitCount < 1) {
				return 0;
			}
			if (value > limitCount) {
				return minute;
			}
			return lastMinute > 0 ? lastMinute : 0;
		});
	}

	/**
	 * 0:验证成功
	 * 校验是否超过设置的频率
	 * @param phoneNo
	 * @param limitSupplier
	 * @param keyFunction
	 * @return
	 * @author volcano
	 * @date 2019年10月2日下午3:26:45
	 * @version V1.0
	 */
	private int validateRepeat(String phoneNo, Supplier<TreeMap<Integer, Integer>> limitSupplier,
							   BiFunction<Integer, String, String> keyFunction) {
		TreeMap<Integer, Integer> limitMap = limitSupplier.get();
		if (ObjectUtils.isEmpty(limitMap)) {
			return 0;
		}
		return limitMap.keySet().stream().reduce(0, (lastMinute, minute) -> {
			String key = keyFunction.apply(minute, phoneNo);
			// increment不会重置超时时间
			long value = PhoneRepeatCache.get(key);
			Integer limitCount = limitMap.get(minute);
			if (limitCount == null || limitCount < 1) {
				return 0;
			}
			if (value >= limitCount) {
				return minute;
			}
			return lastMinute > 0 ? lastMinute : 0;
		});
	}

	/**
	 * 频率值增长
	 * @param phoneNo 手机号
	 * @param limitSupplier 设置频率
	 * @param keyFunction redis中的key
	 */
	private void incrementRepeat(String phoneNo, Supplier<TreeMap<Integer, Integer>> limitSupplier, BiFunction<Integer,
			String, String> keyFunction) {
		TreeMap<Integer, Integer> limitMap = limitSupplier.get();
		if (ObjectUtils.isEmpty(limitMap)) {
			return;
		}
		limitMap.keySet().stream().reduce(0, (lastMinute, minute) -> {
			String key = keyFunction.apply(minute, phoneNo);
			// increment不会重置超时时间
			long value = PhoneRepeatCache.increment(key);
			if (value == 1) {
				// 第一次进来设置超时时间
				PhoneRepeatCache.expire(key, minute, TimeUnit.MINUTES);
			}
			return 0;
		});
	}

	/**
	 * 
	 * @param phoneNo
	 * @return 0:验证成功
	 * @author volcano
	 * @date 2019年10月2日下午3:07:10
	 * @version V1.0
	 */
	public int validateRepeatPhoneNo(String phoneNo) {
		return validateAndIncrementRepeat(phoneNo, () -> this.limitRepeat.getPhoneNos(), (minute, no) -> {
			return genRepeatPhoneNoKey(minute, no);
		});
	}

	/**
	 * 
	 * @param phoneNo
	 * @return 0:验证成功
	 * @author volcano
	 * @date 2019年10月2日下午3:07:10
	 * @version V1.0
	 */
	public int validateRepeatContent(String phoneNo) {
		return validateAndIncrementRepeat(phoneNo, () -> this.limitRepeat.getContent(), (minute, no) -> {
			return genRepeatMsgKey(minute, no);
		});
	}

	/**
	 * 
	 * @param phoneNo
	 * @return 0:验证成功
	 * @author volcano
	 * @date 2019年10月2日下午3:07:10
	 * @version V1.0
	 */
	public int validateRepeatPhoneNo(String phoneNo, SortChannelMap.SortChannel channel) {
		return validateRepeat(phoneNo, () -> channel.getLimitRepeat().getPhoneNos(), (minute, no) -> {
			return genRepeatPhoneNoKey(minute, no, channel);
		});
	}

	/**
	 * 
	 * @param phoneNo
	 * @return 0:验证成功
	 * @author volcano
	 * @date 2019年10月2日下午3:07:10
	 * @version V1.0
	 */
	public int validateRepeatContent(String phoneNo, SortChannelMap.SortChannel channel) {
		return validateRepeat(phoneNo, () -> channel.getLimitRepeat().getContent(), (minute, no) -> {
			return genRepeatMsgKey(minute, no, channel);
		});
	}

	/**
	 * 分拣完成的时候调用
	 * 设置导流策略中的通道手机号频率限制的值，每次加1。
	 * 注意：这里可能会有并发问题，如果同时发送多个一样的手机号码，产品中可用通道数量比较少，可能会分配到同一个通道，这时候有可能超过设置的频率值，
	 * 没有特别好的办法解决，只能先这样
	 * @param phoneNo 手机号
	 * @param channel 通道对象
	 * @version V2.4.0
	 */
	public void incrementRepeatPhoneNo(String phoneNo, SortChannelMap.SortChannel channel) {
		incrementRepeat(phoneNo, () -> channel.getLimitRepeat().getPhoneNos(),
				(minute, no)-> genRepeatPhoneNoKey(minute, no, channel));
	}

	/**
	 * 分拣完成的时候调用
	 * 设置导流策略中的通道内容频率限制的值，每次加1
	 * 注意：同 incrementRepeatPhoneNo
	 * @param phoneNo 手机号
	 * @param channel 通道对象
	 * @version V2.4.0
	 */
	public void incrementRepeatContent(String phoneNo, SortChannelMap.SortChannel channel) {
		incrementRepeat(phoneNo, () -> channel.getLimitRepeat().getContent(), (minute, no)-> genRepeatMsgKey(minute, no,
				channel));
	}
}
