package com.hero.wireless.sort.diversion.impl;

import com.hero.wireless.enums.DiversionError;
import com.hero.wireless.enums.ProductChannelDiversionType;
import com.hero.wireless.json.JsonUtil;
import com.hero.wireless.sort.SortChannelMap;
import com.hero.wireless.sort.SortContext;
import com.hero.wireless.sort.diversion.ISortDiversionService;
import com.hero.wireless.web.entity.business.ProductChannels;
import com.hero.wireless.web.entity.business.ProductChannelsDiversion;
import com.hero.wireless.web.util.ChannelUtil.IDiversionResult;
import com.hero.wireless.web.util.ChannelUtil.IncludeSignature;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 更正为 签名导流策略 SignatureDiversion.java by zly <br>
 * 签名导流策略增加匹配任意签名，默认扩展 关键字策略实现 KeywordDiversion.java
 *
 * @author wjl
 * @date 2020年2月9日下午2:17:34
 */
@Component
public class SignatureDiversion implements ISortDiversionService {
	private static final String ANY_SIGNATURE_TAG = "*";

	@Override
	public IDiversionResult diversion(SortContext sortContext, ProductChannels productChannel) {
		return null;
	}

	@Override
	public IDiversionResult diversion(SortContext sortContext, SortChannelMap.SortChannel sortChannel) {
		// 找出通道对应的产品策略的签名导流配置
		List<ProductChannelsDiversion> signatureList = strategys(sortChannel.getProductChannels(),
			ProductChannelDiversionType.SIGNATURE);
		if (ObjectUtils.isEmpty(signatureList)) {
			return null;
		}
		sortChannel.setSignatureStr(DiversionError.NO_SIGNATURE.toString());

		IncludeSignature signature = signatureList.stream()
			.map(item -> JsonUtil.readValue(item.getStrategy_Rule(), IncludeSignature.class)
			).filter(item -> {
				if (item.getSignature().equals(ANY_SIGNATURE_TAG)) {
					return false;
				}
				if (StringUtils.isBlank(sortContext.getSignature())) {
					return false;
				}
				return sortContext.getSignature().equals(item.getSignature());
			}).findFirst().orElse(null);
		if (signature != null) {
			sortChannel.setSignatureStr(signature.getSignature());
			return signature;
		}
		IncludeSignature anySignature = signatureList.stream().map(item ->
			JsonUtil.readValue(item.getStrategy_Rule(), IncludeSignature.class)
		).filter(item -> item.getSignature().equals(ANY_SIGNATURE_TAG)).findFirst().orElse(null);
		if (anySignature != null) {
			sortChannel.setSignatureStr(ANY_SIGNATURE_TAG);
			return anySignature;
		}
		return null;
	}

}
