package com.hero.wireless.sort.diversion.impl;

import com.hero.wireless.enums.DiversionError;
import com.hero.wireless.enums.ProductChannelDiversionType;
import com.hero.wireless.sort.SortChannelMap;
import com.hero.wireless.sort.SortContext;
import com.hero.wireless.sort.diversion.ISortDiversionService;
import com.hero.wireless.web.entity.business.ProductChannels;
import com.hero.wireless.web.entity.business.ProductChannelsDiversion;
import com.hero.wireless.web.util.ChannelUtil.IDiversionResult;
import org.apache.commons.lang3.ObjectUtils;

import java.util.List;

/**
 * 包含关键字的导流策略 只有短信内容包含指定的关键字，才能命中该通道 数据库结构每行一条
 * 
 * @author zly
 *
 */
public class IncludeKeywordDiversion implements ISortDiversionService {

	@Override
	public IDiversionResult diversion(SortContext sortContext, ProductChannels productChannel) {
		return null;
	}

	@Override
	public IDiversionResult diversion(SortContext sortContext, SortChannelMap.SortChannel sortChannel) {
		List<ProductChannelsDiversion> keywords = strategys(sortChannel.getProductChannels(),
				ProductChannelDiversionType.INCLUDE_KEYWORD);
		if (ObjectUtils.isEmpty(keywords)) {
			// 没有配置关键字，配置了签名导流，但是没有找到签名导流，也返回false
			return new BooleanResult(true && validateSignature(sortChannel));
		}
		// 数据库结构每行一条
		boolean includeResult = keywords.stream()
				.anyMatch(item -> sortContext.getInput().getContent().contains(item.getStrategy_Rule()));
		return new BooleanResult(includeResult && validateSignature(sortChannel));
	}
	
	private boolean validateSignature(SortChannelMap.SortChannel sortChannel) {
		if (sortChannel.getSignatureStr() != null
				&& sortChannel.getSignatureStr().equals(DiversionError.NO_SIGNATURE.toString())) {
			return false;
		}
		return true;
	}

}
