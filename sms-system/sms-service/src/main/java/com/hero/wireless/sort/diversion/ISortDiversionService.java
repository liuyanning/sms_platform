package com.hero.wireless.sort.diversion;

import com.hero.wireless.enums.ProductChannelDiversionType;
import com.hero.wireless.sort.SortChannelMap;
import com.hero.wireless.sort.SortContext;
import com.hero.wireless.web.config.DatabaseCache;
import com.hero.wireless.web.entity.business.ProductChannels;
import com.hero.wireless.web.entity.business.ProductChannelsDiversion;
import com.hero.wireless.web.util.ChannelUtil.IDiversionResult;

import java.util.List;

/**
 * 导流策略接口 各种策略实现该接口，实现对应的业务 ISortDiversion.java
 *
 * @author wjl
 * @date 2020年2月9日下午1:45:43
 */
public interface ISortDiversionService {

	/**
	 * 返回字符串的结果类型
	 * 
	 * @author zly
	 *
	 */
	public static class StringResult implements IDiversionResult {
		private String description;

		public StringResult(String description) {
			super();
			this.description = description;
		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}

	}

	public static class BooleanResult implements IDiversionResult {
		private boolean result;

		public BooleanResult(boolean result) {
			super();
			this.result = result;
		}

		public boolean isResult() {
			return result;
		}

		public void setResult(boolean result) {
			this.result = result;
		}

	}

	/**
	 * 
	 * 导流业务处理方法，不同的导流策略，不同的实现
	 * 
	 * @param sortContext
	 */
	IDiversionResult diversion(SortContext sortContext, ProductChannels productChannel);
	
	/**
	 * 
	 * 
	 * @param sortContext
	 * @param sortChannel
	 * @return
	 */
	default IDiversionResult diversion(SortContext sortContext, SortChannelMap.SortChannel sortChannel) {
		return diversion(sortContext, sortChannel.getProductChannels());
	}
	/**
	 * 
	 * @param sortContext
	 * @param productChannel
	 * @param phoneNo
	 * @return
	 */
	default IDiversionResult diversion(SortContext sortContext, ProductChannels productChannel, String phoneNo) {
		return null;
	}

	/**
	 * 获取策略
	 * 
	 * @param productChannel
	 * @param diversionType
	 * @return
	 */
	default List<ProductChannelsDiversion> strategys(ProductChannels productChannel,
			ProductChannelDiversionType diversionType) {
		return DatabaseCache.getDiversions(productChannel, diversionType);
	}

}
