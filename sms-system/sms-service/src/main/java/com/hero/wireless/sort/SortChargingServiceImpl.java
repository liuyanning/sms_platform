package com.hero.wireless.sort;

import com.hero.wireless.web.entity.send.InputLog;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * @author liuyanning
 */
@Service("sortChargingService")
public class SortChargingServiceImpl extends AbstractChargingService {

	@Override
	protected void updateChargeFailed(SortContext ctx) {
		InputLog inputLog = ctx.getInputLog();
		inputLog.setFee_Count(0);
		inputLog.setSale_Amount(BigDecimal.valueOf(0));
		inputLog.setRemark(String.format("%1$s个字,%2$s个号码,0条计费,0元", inputLog.getContent_Length(),
				inputLog.getPhone_Nos_Count()));
	}

	@Override
	protected void updateChargeSuccess(SortContext ctx, int feeCount, BigDecimal userSaleFee) {
		InputLog inputLog = ctx.getInputLog();
		inputLog.setFee_Count(feeCount);
		inputLog.setSale_Amount(userSaleFee);
		inputLog.setRemark(String.format("%1$s字,%2$s号码,%3$s条,%4$s元", inputLog.getContent_Length(),
				inputLog.getPhone_Nos_Count(), inputLog.getFee_Count(), inputLog.getSale_Amount().doubleValue()));
	}

}