package com.hero.wireless.sort;

import com.google.common.util.concurrent.AtomicDouble;
import com.hero.wireless.enums.ReportNativeStatus;
import com.hero.wireless.enums.SettlementType;
import com.hero.wireless.web.entity.business.EnterpriseUser;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author liuyanning
 */
public abstract class AbstractChargingService implements ISortChargingService {

	@Resource
	private IUpdateBalanceService updateBalanceService;

	/**
	 * 余额不足更新
	 * @param ctx
	 */
	protected abstract void updateChargeFailed(SortContext ctx);

	/**
	 * 计费成功更新字段
	 * @param ctx
	 */
	protected abstract void updateChargeSuccess(SortContext ctx, int feeCount, BigDecimal userSaleFee);

	@Override
	public boolean charging(SortContext ctx) {
		//需要计费的手机号码数量
		int mobileCount = ctx.getChannelMap().mobileCount();
		if (mobileCount == 0) {
			return false;
		}
		final int contentSplitCount = ctx.getContentSplitCount();
		// 分拣成功计费条数
		int feeCount = mobileCount * contentSplitCount;

		// 企业用户消费金额
		BigDecimal userSaleFee = BigDecimal.valueOf(ctx.getChannelMap().saleFee(contentSplitCount));

		String userId = String.valueOf(ctx.getEnterpriseUser().getId());
		// 余额不足,并且等于预付费
		String settlementType = StringUtils.defaultIfBlank(ctx.getEnterpriseUser().getSettlement_Type_Code(),
				SettlementType.PREPAID.toString());
		//预付才验证余额
		if (settlementType.equalsIgnoreCase(SettlementType.PREPAID.toString())) {
			BigDecimal userSaleFeeInCache = BigDecimal.valueOf(updateBalanceService.getCachedUserFee(userId).doubleValue());
			if (!validBalance(ctx.getEnterpriseUser(), userSaleFeeInCache.add(userSaleFee))) {
				String error = "余额不足";
				ctx.getChannelMap().values().forEach(channels -> {
					channels.forEach(channel -> {
						ctx.addFaild(channel.getMobileNos(), ReportNativeStatus.DD0010, error);
					});
				});
				updateChargeFailed(ctx);
				return false;
			}
		}

		updateChargeSuccess(ctx, feeCount, userSaleFee);

		// TODO sort_fee_batch_update 目前必须使用本地缓存，该配置项是否应废弃掉
		// 判断是否批量更新
		// String isBatchUpdate = DatabaseCache.getStringValueBySortCodeAndCode("system_env","sort_fee_batch_update","false");

		// 缓存中保存用户消费金额
		AtomicDouble singleUserFee = updateBalanceService.getCachedUserFee(userId);
		singleUserFee.getAndAdd(userSaleFee.doubleValue());
		// 缓存中保存成功计费条数
		AtomicInteger singleUserFeeCount = updateBalanceService.getCachedCounter(userId);
		singleUserFeeCount.getAndAdd(feeCount);

		return true;
	}

	/**
	 * 检查余额
	 *
	 * @param user
	 * @param userSaleFee
	 * @return
	 * @author volcano
	 * @date 2019年9月17日上午5:25:13
	 * @version V1.0
	 */
	private boolean validBalance(EnterpriseUser user, BigDecimal userSaleFee) {
		String settlementType = StringUtils.defaultIfBlank(user.getSettlement_Type_Code(),
				SettlementType.PREPAID.toString());
		if (userSaleFee.compareTo(user.getAvailable_Amount()) > 0
				&& settlementType.equalsIgnoreCase(SettlementType.PREPAID.toString())) {
			return false;
		}
		return true;
	}

}
