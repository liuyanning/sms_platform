package com.hero.wireless.sort.diversion.impl;

import com.drondea.wireless.util.SuperLogger;
import com.hero.wireless.enums.ProductChannelDiversionType;
import com.hero.wireless.sort.SortContext;
import com.hero.wireless.sort.diversion.ISortDiversionService;
import com.hero.wireless.web.entity.business.MobileArea;
import com.hero.wireless.web.entity.business.ProductChannels;
import com.hero.wireless.web.entity.business.ProductChannelsDiversion;
import com.hero.wireless.web.util.ChannelUtil.IDiversionResult;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;

/**
 * 地域导流 单行英文逗号分隔==============
 * 
 * @author zly
 *
 */
public class AreaDiversion implements ISortDiversionService {

	@Override
	public IDiversionResult diversion(SortContext sortContext, ProductChannels productChannel, String phoneNo) {

		List<ProductChannelsDiversion> areaRules = strategys(productChannel, ProductChannelDiversionType.AREAS);
		if (ObjectUtils.isEmpty(areaRules)) {
			return new BooleanResult(true);
		}
		MobileArea mobileArea = sortContext.getMobileAreas().get(phoneNo);
		boolean isMatch = Arrays.asList(areaRules.get(0).getStrategy_Rule().split(",")).stream().anyMatch(item -> {
			if (item.equalsIgnoreCase("all")) {
				return true;
			}
			if (StringUtils.isEmpty(mobileArea.getProvince_Code())) {
				SuperLogger.error("号码归属地为空");
				return false;
			}
			return mobileArea.getProvince_Code().equalsIgnoreCase(item);
		});
		return new BooleanResult(isMatch);
	}

	@Override
	public IDiversionResult diversion(SortContext sortContext, ProductChannels productChannel) {
		return null;
	}

}
