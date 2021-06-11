package com.hero.wireless.sort;

import com.hero.wireless.enums.Operator;
import com.hero.wireless.enums.Priority;
import com.hero.wireless.enums.ProtocolType;
import com.hero.wireless.enums.ReportNativeStatus;
import com.hero.wireless.sms.sender.service.ISenderSmsService;
import com.hero.wireless.web.config.DatabaseCache;
import com.hero.wireless.web.dao.send.IInputLogDAO;
import com.hero.wireless.web.entity.business.Channel;
import com.hero.wireless.web.entity.business.MobileArea;
import com.hero.wireless.web.entity.business.SmsRoute;
import com.hero.wireless.web.entity.send.Input;
import com.hero.wireless.web.entity.send.InputLog;
import com.hero.wireless.web.entity.send.Submit;
import com.hero.wireless.web.util.QueueUtil;
import com.hero.wireless.web.util.SMSUtil;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * @author drondea
 */
@Service("sortSmsService")
public class SortSmsServiceImpl implements ISortSMSService {
	@Resource(name = "sortChargingService")
	private ISortChargingService sortChargingService;
	@Autowired
	private IInputLogDAO<InputLog> inputLogDAO;
	@Resource(name = "defaultSenderServiceImpl")
	private ISenderSmsService senderSmsService;


	@Override
	public void sort(Input input) throws Exception {
		SortContext ctx = new SortContext(input);
		//设置企业相关基本信息
		EnterpriseSetter.initEnterpriseInfo(ctx);
		//设置用户的产品路由
		ChannelMapSetter.initChannelMap(ctx);

		//配置和内容检测
		boolean baseResult = SortValidator.baseValidator(ctx);
		if (!baseResult) {
			QueueUtil.saveInputLogs(Arrays.asList(ctx.getInputLog()));
			// 分拣失败记录直接写入submit
			saveSortFaildSubmit(ctx, ctx.getFaildList());
			return;
		}
		//分拣操作，校验号码，选择路由
		sortMobile(ctx);
		boolean isPerformanceGeneralMode = DatabaseCache.getStringValueBySystemEnvAndCode("performance_mode", "general")
				.equals("general");
		boolean result;
		if (isPerformanceGeneralMode) {
			result = sortChargingService.charging(ctx);
		} else {
			//只能加同步块来保证余额不是负数，但在分布式部署分拣器时，依然会出现负数情况
			synchronized (sortChargingService) {
				result = sortChargingService.charging(ctx);
			}
		}
		QueueUtil.saveInputLogs(Arrays.asList(ctx.getInputLog()));
		if (result) {
			// 分拣成功，通知发送器
			notifySender(ctx);
		}
		// 分拣失败记录直接写入submit
		saveSortFaildSubmit(ctx, ctx.getFaildList());
	}

	/**
	 * 分拣异常号码，直接保存
	 * 
	 * @param ctx
	 * @param phoneNos
	 * @throws Exception
	 * @author volcano
	 * @date 2019年9月15日上午2:19:29
	 * @version V1.0
	 */
	private void saveSortFaildSubmit(SortContext ctx, List<? extends SortContext.SortPhoneNo> phoneNos) throws Exception {
		if (ObjectUtils.isEmpty(phoneNos)) {
			return;
		}
		ctx.saveSortFaildSubmit(phoneNos);
	}



	private void sortMobile(SortContext ctx) {
		Input input = ctx.getInput();
		String[] mobiles = input.getPhone_Nos().split(SortContext.MUTL_MOBILE_SPLIT);
		ctx.getInputLog().setPhone_Nos_Count(mobiles.length);
		Arrays.asList(mobiles).forEach(no -> {
			//手机号校验
			if(!SortValidator.checkPhone(ctx, input, no)) {
				return;
			}
			//获取手机号码路由
			SmsRoute route = DatabaseCache.getSmsRouteCachedByNumber(no, input.getCountry_Code());
			if (ObjectUtils.isEmpty(route)) {
				ctx.addFaild(no, ReportNativeStatus.DD0005, "路由无效");
				return;
			}
			ctx.getMobileRoutes().put(no, route);

			// 白名单校验
			String whitePoolCode = ctx.getInterceptStrategy() == null ? null : ctx.getInterceptStrategy().getWhite_Pool_Code();
			boolean isWhite = DatabaseCache.hashWhiteListByNumber(ctx.getInput().getCountry_Code() + no,
					input.getEnterprise_No(), whitePoolCode);

			//不是白名单的手机要进行黑名单校验和重复校验
			if (!isWhite && !SortValidator.checkBlackAndRepeat(ctx, input, no)) {
				return;
			}

			MobileArea mobileArea = DatabaseCache.getMobileAreaByNumber(no, ctx.getInput().getCountry_Code(), true);
			//保存号码对应区域信息
			ctx.getMobileAreas().put(no, mobileArea);

			// =========================================================================
			// 自动路由通道
			SortChannelMap channelMap = ctx.getChannelMap();
			SortChannelMap.Key mapKey = channelMap.newKey(ctx.getInput().getMessage_Type_Code(), route.getRoute_Name_Code());
//			SuperLogger.debug(mapKey.getMessageType() + mapKey.getOperator());
			//根据消息类型和运营商获取所有配置的通道
			Collection<SortChannelMap.SortChannel> channels = channelMap.get(mapKey);
			if (ObjectUtils.isEmpty(channels)) {
				ctx.addFaild(no, ReportNativeStatus.DD0009, "用户资费未配置或网关不支持");
				return;
			}
			//根据条件过滤通道，并通过权重获取通道
			SortChannelMap.SortChannel sortChannel = channelMap.routeChannel(mapKey,
					new SortChannelPredicate(ctx, no, isWhite));
			//没有获取到通道
			if (sortChannel instanceof SortChannelMap.FaildSortChannel) {
				String errorMsg = sortChannel.getErrorMsg().toString();
				errorMsg = errorMsg.length() > 100 ? errorMsg.substring(0, 100) : errorMsg;
				ctx.addFaild(no, ReportNativeStatus.DD0099, "通道未匹配,原因：" + errorMsg);
				return;
			}

			//设置选定通道频次+1
			ctx.getSortLimitRepeat().incrementRepeatPhoneNo(no, sortChannel);
			ctx.getSortLimitRepeat().incrementRepeatContent(no, sortChannel);

			//给通道设置号码
			sortChannel.addMobileNo(no);

		});
	}

	/**
	 * 通知发送器 并保存
	 * 
	 * @param ctx
	 * @throws Exception
	 * @author volcano
	 * @date 2019年9月15日上午7:21:13
	 * @version V1.0
	 */
	private void notifySender(SortContext ctx) throws Exception {
		ctx.getChannelMap().values().stream().forEach(channels -> {
			channels.forEach(sortChannel -> {
				if (ObjectUtils.isEmpty(sortChannel.getMobileNos())) {
					return;
				}
				Channel channel = sortChannel.getChannel();
				List<Submit> submits = SMSUtil.buildSubmits(channel, ctx.getInput(), sortChannel.getMobileNos(),
						sortChannel.getSubCode());
				submits.forEach(submit -> {
					String phone_nos = submit.getPhone_No();

					//区域
					MobileArea area = ctx.getMobileAreas().get(phone_nos);
					// 地域
					if (!ObjectUtils.isEmpty(area)) {
						submit.setArea_Name(area.getMobile_Area());
						submit.setProvince_Code(area.getProvince_Code());
					}

					//路由
					SmsRoute route = ctx.getMobileRoutes().get(phone_nos);
					if (!ObjectUtils.isEmpty(route)) {
						submit.setOperator(route.getRoute_Name_Code());
						submit.setCountry_Number(route.getCountry_Number());
						submit.setMCC(route.getMCC());
						submit.setMNC(route.getMNC());
					} else {
						submit.setOperator(Operator.UNKNOW.toString());
					}
					//计算利润
					SMSUtil.buildSortSubmitAreaAndOperator(submit, sortChannel.getUserFee());

					submit.setSignature(ctx.getSignature());
					submit.setProduct_Channel_Id(sortChannel.getProductChannels().getId());
					submit.setProduct_No(sortChannel.getProductChannels().getProduct_No());
					//input_log的create_date
					submit.setInput_Log_Date(ctx.getInputLog().getCreate_Date());
					int level = Priority.valueOf(ctx.getEnterpriseUser().getPriority_Level()).value();
					submit.setPriority_Level(level);
					//保存待发送表
					QueueUtil.notifySubmit(submit, "_" + level);
				});
			});
		});
	}

	private boolean isNotHttp(String protocolTypeCode){
		return !ProtocolType.WEB.toString().equalsIgnoreCase(protocolTypeCode) &&
				!ProtocolType.HTTP_JSON.toString().equalsIgnoreCase(protocolTypeCode) &&
				!ProtocolType.HTTP_XML.toString().equalsIgnoreCase(protocolTypeCode);
	}
}
