package com.hero.wireless.sort.diversion.impl;

import com.drondea.wireless.util.SuperLogger;
import com.hero.wireless.enums.ProductChannelDiversionType;
import com.hero.wireless.sort.SortContext;
import com.hero.wireless.sort.diversion.ISortDiversionService;
import com.hero.wireless.web.entity.business.ProductChannels;
import com.hero.wireless.web.entity.business.ProductChannelsDiversion;
import com.hero.wireless.web.util.ChannelUtil.IDiversionResult;
import org.apache.commons.lang3.ObjectUtils;

import java.util.List;


/**
 * 检测到敏感字返回true，否则返回false
 * @author zly
 *
 */
public class ExcludeKeywordDiversion implements ISortDiversionService {

	@Override
	public IDiversionResult diversion(SortContext sortContext, ProductChannels productChannel) {
		List<ProductChannelsDiversion> keywords = strategys(productChannel,
				ProductChannelDiversionType.EXCLUDE_KEYWORD);
		if (ObjectUtils.isEmpty(keywords)) {
			return new BooleanResult(false);
		}
		boolean sensitivesResult = keywords.stream().anyMatch((item) -> {
			boolean isContains = sortContext.getInput().getContent().contains(item.getStrategy_Rule());
			if (isContains) {
				SuperLogger.warn("智能路由📶(" + productChannel.getChannel_No() + ") 敏感字(" + item + ")");
			}
			return isContains;
		});
		return new BooleanResult(sensitivesResult);
	}

}
