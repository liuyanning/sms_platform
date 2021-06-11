package com.hero.wireless.sort;

import com.hero.wireless.enums.ProductChannelDiversionType;
import com.hero.wireless.json.JsonUtil;
import com.hero.wireless.web.config.DatabaseCache;
import com.hero.wireless.web.entity.business.*;
import com.hero.wireless.web.util.ChannelUtil.LimitRepeat;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.*;

/**
 * 通道映射
 * 
 * @author volcano
 * @date 2019年9月13日上午5:39:15
 * @version V1.0
 */
public class SortChannelMap extends HashMap<SortChannelMap.Key, Collection<SortChannelMap.SortChannel>> {
	private static final long serialVersionUID = 3593861269438662846L;

	/** 权重算法 */
	public class WeightRandom {
		private TreeMap<Integer, SortChannel> weightMap = new TreeMap<Integer, SortChannel>();

		public void add(SortChannel channelStruct) {
			Integer lastWeight = this.weightMap.size() == 0 ? 0 : this.weightMap.lastKey();
			this.weightMap.put(channelStruct.getProductChannels().getWeight() + lastWeight, channelStruct);// 权重累加
		}

		public SortChannel random() {
			if (weightMap.isEmpty()) {
				return null;
			}
			Double randomWeight = this.weightMap.lastKey() * Math.random();
			SortedMap<Integer, SortChannel> tailMap = this.weightMap.tailMap(randomWeight.intValue(), false);
			return this.weightMap.get(tailMap.firstKey());
		}
	}

	/**
	 * 
	 * 通道和 要用该通道发送的手机号码 数据结构
	 * 
	 * @author volcano
	 * @date 2019年9月14日上午7:26:14
	 * @version V1.0
	 */
	public class SortChannel {

		private Channel channel;
		private EnterpriseUserFee userFee;
		private ProductChannels productChannels;
		private LimitRepeat limitRepeat = new LimitRepeat();
		private String subCode = "";
		private List<String> mobileNos = new ArrayList<String>();
		private StringBuffer errorMsg = new StringBuffer();
		private String signatureStr = null;
		public SortChannel() {

		}

		public SortChannel(Channel channel, EnterpriseUserFee userFee,
				ProductChannels productChannels) {
			this.channel = channel;
			this.userFee = userFee;
			this.productChannels = productChannels;

			// 保持原有json格式，一条记录
			List<ProductChannelsDiversion> intervalLimit = DatabaseCache.getDiversions(productChannels,
					ProductChannelDiversionType.INTERVAL_LIMIT);
			if (ObjectUtils.isNotEmpty(intervalLimit)
					&& StringUtils.isNotEmpty(intervalLimit.get(0).getStrategy_Rule())) {
				limitRepeat = JsonUtil.readValue(intervalLimit.get(0).getStrategy_Rule(), LimitRepeat.class);
			}
		}

		public Channel getChannel() {
			return channel;
		}

		public void setChannel(Channel channel) {
			this.channel = channel;
		}

		public List<String> getMobileNos() {
			return mobileNos;
		}

		public void setMobileNos(List<String> mobileNos) {
			this.mobileNos = mobileNos;
		}

		public void addMobileNo(String no) {
			mobileNos.add(no);
		}

		public EnterpriseUserFee getUserFee() {
			return userFee;
		}

		public void setUserFee(EnterpriseUserFee userFee) {
			this.userFee = userFee;
		}

		public ProductChannels getProductChannels() {
			return productChannels;
		}

		public void setProductChannels(ProductChannels productChannels) {
			this.productChannels = productChannels;
		}

		public String getSubCode() {
			return subCode;
		}

		public void setSubCode(String subCode) {
			this.subCode = subCode;
		}

		public LimitRepeat getLimitRepeat() {
			return limitRepeat;
		}

		public void setLimitRepeat(LimitRepeat limitRepeat) {
			this.limitRepeat = limitRepeat;
		}

		public StringBuffer getErrorMsg() {
			return errorMsg;
		}

		public void setErrorMsg(StringBuffer errorMsg) {
			this.errorMsg = errorMsg;
		}
		
		public String getSignatureStr() {
			return signatureStr;
		}

		public void setSignatureStr(String signatureStr) {
			this.signatureStr = signatureStr;
		}

	}

	/**
	 * 分拣通道失败类型
	 * 
	 * @author zly
	 *
	 */
	public class FaildSortChannel extends SortChannel {

	}

	/**
	 * 通道类型，数据结构
	 * 
	 * @author volcano
	 * @date 2019年9月13日上午5:22:17
	 * @version V1.0
	 */
	public class Key {
		/** 消息类型 彩信，短信，语音 */
		private String messageType;
		/** 移动，联通，电信，香港 ，澳门，美国等 */
		private String operator;
		/** 行业类型 */
		private String tradeType;

		private Key(String messageType, String operator, String tradeType) {
			super();
			this.messageType = messageType;
			this.operator = operator;
			this.tradeType = tradeType;
		}

		@Override
		public int hashCode() {
			return messageType.hashCode() + operator.hashCode() + tradeType.hashCode();
		}

		@Override
		public boolean equals(Object obj) {
			if (obj == null) {
				return false;
			}
			Key target = (Key) obj;
			return messageType.equals(target.getMessageType()) && operator.equals(target.getOperator())
					&& tradeType.equals(target.getTradeType());
		}

		public String getMessageType() {
			return messageType;
		}

		public void setMessageType(String messageType) {
			this.messageType = messageType;
		}

		public String getOperator() {
			return operator;
		}

		public void setOperator(String operator) {
			this.operator = operator;
		}

		public String getTradeType() {
			return tradeType;
		}

		public void setTradeType(String tradeType) {
			this.tradeType = tradeType;
		}
	}

	public Key newKey(String messageType, String operator, String tradeType) {
		return new Key(messageType, operator, tradeType);
	}

	/**
	 * 
	 * @param messageType
	 * @param operator
	 * @return
	 * @author volcano
	 * @date 2019年9月14日上午7:41:37
	 * @version V1.0
	 */
	public Key newKey(String messageType, String operator) {
		return new Key(messageType, operator, "");
	}

	/**
	 * 添加通道
	 * 
	 * @param key
	 * @param channelStruct
	 * @author volcano
	 * @date 2019年9月14日上午9:23:56
	 * @version V1.0
	 */
	public void putChannel(Key key, SortChannel channelStruct) {
		Collection<SortChannel> channels = getOrDefault(key, new HashSet<SortChannel>());
		channels.add(channelStruct);
		this.put(key, channels);
	}

	/**
	 * 权重通道
	 * 
	 * @param key
	 * @param predicate
	 * @return
	 * @author volcano
	 * @date 2019年9月14日上午10:11:44
	 * @version V1.0
	 */
	private SortChannel random(Key key, SortChannelPredicate predicate) {
		WeightRandom weightRandom = new WeightRandom();
		Collection<SortChannel> value = get(key);
		if (value == null) {
			return null;
		}
		FaildSortChannel subSortChannel = new FaildSortChannel();
		value.forEach(item -> {
			// 小于1权重相当于关闭
			if (item.getProductChannels().getWeight() < 1) {
				return;
			}
			if (predicate.test(item)) {
				weightRandom.add(item);
			} else {
				subSortChannel.setErrorMsg(subSortChannel.getErrorMsg().append(item.getErrorMsg()));
			}
			item.setErrorMsg(new StringBuffer());
		});
		SortChannel sortChannel = weightRandom.random();
		return sortChannel == null ? subSortChannel : sortChannel;
	}

	/**
	 * 路由通道
	 * 
	 * @param key
	 * @param predicate
	 * @return
	 * @author volcano
	 * @date 2019年9月14日上午10:13:15
	 * @version V1.0
	 */
	public SortChannel routeChannel(Key key, SortChannelPredicate predicate) {
		SortChannel channelAndMobiles = random(key, predicate);
		if (channelAndMobiles == null) {
			return null;
		}
		return channelAndMobiles;
	}

	/**
	 * 
	 * @return
	 * @author volcano
	 * @date 2019年9月15日上午3:36:27
	 * @version V1.0
	 */
	public int mobileCount() {
		return this.values().stream().flatMap(values -> values.stream())
				.mapToInt(channel -> channel.getMobileNos().size()).sum();
	}

	/**
	 * 计费
	 * 
	 * @param contentSplitCount
	 * @return
	 * @author volcano
	 * @date 2019年9月15日上午3:48:56
	 * @version V1.0
	 */
	public double saleFee(int contentSplitCount) {
		return this.values().stream().flatMap(values -> values.stream()).mapToDouble(channel -> {
			return channel.getUserFee().getUnit_Price().multiply(BigDecimal.valueOf(channel.getMobileNos().size()))
					.multiply(BigDecimal.valueOf(contentSplitCount)).doubleValue();
		}).sum();
	}

}
