package com.hero.wireless.sort.diversion.impl;

import com.drondea.wireless.util.SuperLogger;
import com.hero.wireless.enums.MessageType;
import com.hero.wireless.enums.ProductChannelDiversionType;
import com.hero.wireless.sort.SortContext;
import com.hero.wireless.sort.diversion.ISortDiversionService;
import com.hero.wireless.web.entity.business.ProductChannels;
import com.hero.wireless.web.entity.business.ProductChannelsDiversion;
import com.hero.wireless.web.entity.send.Input;
import com.hero.wireless.web.util.ChannelUtil.IDiversionResult;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.util.List;

/**
 * 内容长度限制
 * 
 * @author zly
 *
 */
public class ContentLengthLimit implements ISortDiversionService {

	@Override
	public IDiversionResult diversion(SortContext sortContext, ProductChannels productChannel) {
		List<ProductChannelsDiversion> contentLengthRules = strategys(productChannel,
				ProductChannelDiversionType.SMS_LENGTH_LIMIT);
		if (ObjectUtils.isEmpty(contentLengthRules)) {
			return new BooleanResult(false);
		}
		Input input = sortContext.getInput();
		boolean isSMS = MessageType.SMS.toString().equalsIgnoreCase(input.getMessage_Type_Code());
		if (isSMS && input.getContent().length() > NumberUtils.toInt(contentLengthRules.get(0).getStrategy_Rule(),
				1000)) {
			SuperLogger.warn("超过通道支持的最大字数");
			return new BooleanResult(true);
		}
		return new BooleanResult(false);
	}

}
