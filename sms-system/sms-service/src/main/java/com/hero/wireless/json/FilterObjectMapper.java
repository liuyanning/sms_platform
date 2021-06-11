package com.hero.wireless.json;

import com.hero.wireless.web.config.DatabaseCache;
import com.hero.wireless.web.entity.business.*;
import com.hero.wireless.web.entity.business.ext.*;
import com.hero.wireless.web.entity.send.*;
import com.hero.wireless.web.entity.send.ext.*;
import io.netty.channel.group.ChannelGroup;

import java.util.Arrays;
import java.util.HashSet;

/**
 * 过滤器集合
 *
 * @author Lenovo
 */
public class FilterObjectMapper extends EscapeObjectMapper {
	/**
	 *
	 */
	private static final long serialVersionUID = 552854976734935388L;
	private DrondeaJsonFilterProvider filterProvider;

	public FilterObjectMapper() {
		super();
		filterProvider = new DrondeaJsonFilterProvider(this);
		addCodeTranslate(AdminUser.class, new String[] { "003,status" });
		addCodeTranslate(AdminLimit.class, new String[] { "007,type_Code" });
		addCodeTranslate(Platform.class, new String[] { "006,statistics_Status"});
		addCodeTranslate(EnterpriseLimit.class, new String[] { "007,type_Code" });
		addCodeTranslate(Channel.class, new String[] { "024,status_Code", "trade,trade_Type_Code", "Location,area_Code",
				"signature_type,signature_Direction_Code", "031,signature_Position"
                , "channel_source,channel_Source" });
		addCodeTranslate(ChannelGroup.class,
				new String[] { "024,status_Code", "trade,trade_Type_Code", "Location,area_Code" });
		addCodeTranslate(ChargeOrder.class,
				new String[] { "018,pay_Type_Code", "017,open_Status_Code", "016,approve_Status_Code" });
		addCodeTranslate(EnterpriseExt.class, new String[] { "006,is_LMS", "006,is_Need_Report", "005,fee_Type_Code",
				"003,status", "authentication_State,authentication_State_Code" });
		addCodeTranslate(EnterpriseUser.class, new String[] { "003,status", "EnterpriseUserType,user_Type","currency_type,settlement_Currency_Type_Code",
				"gate_ip_list,gate_Ip", "approvetype,audit_Type_Code", "009,priority_Level",});
		addCodeTranslate(BlackListExt.class, new String[] { "006,is_Share", "001,route_Name_Code",
				"trade,trade_Type_Code", "black_pool,pool_Code" });
		addCodeTranslate(SmsRoute.class, new String[] { "001,route_Name_Code" });
		addCodeTranslate(SmsRouteExt.class, new String[] { "operator_name,route_Name_Code" });
		addCodeTranslate(ChargeOrderExt.class,
				new String[] { "018,pay_Type_Code", "016,approve_Status_Code", "017,open_Status_Code" });
		addCodeTranslate(InputExt.class,
				new String[] { "006,is_LMS", "020,protocol_Type_Code", "019,audit_Status_Code",
                        "message_type_code,message_Type_Code" });
		addCodeTranslate(InputLogExt.class,
				new String[] { "006,is_LMS", "020,protocol_Type_Code", "019,audit_Status_Code",
                        "message_type_code,message_Type_Code" });
        addCodeTranslate(Submit.class, new String[] { "034,submit_Status_Code", "027,stat", "006,is_LMS",
                "location,province_Code", "country,country_Number" ,"message_type_code,message_Type_Code"});
        addCodeTranslate(SubmitExt.class, new String[] { "034,submit_Status_Code", "027,stat", "006,is_LMS",
				"location,province_Code", "country,country_Number","message_type_code,message_Type_Code" });
		addCodeTranslate(Inbox.class, new String[] { "032,notify_Status_Code" });
		addCodeTranslate(Report.class, new String[] { "034,submit_Status_Code", "032,notify_Status_Code",
				"country,country_Number", "027,native_Status", "006,is_LMS", "033,status_Code", "location,province_Code",
				"message_type_code,message_Type_Code" });
		addCodeTranslate(ReportExt.class, new String[] { "034,submit_Status_Code", "032,notify_Status_Code",
				"country,country_Number", "027,native_Status", "006,is_LMS", "033,status_Code", "location,province_Code",
                "message_type_code,message_Type_Code" });
		addCodeTranslate(ReportNotify.class, new String[] { "034,submit_Status_Code", "032,notify_Status_Code",
				"country,country_Number", "027,native_Status", "006,is_LMS", "033,status_Code",
                "location,province_Code","message_type_code,message_Type_Code" });
		addCodeTranslate(ReportNotifyExt.class, new String[] { "034,submit_Status_Code", "032,notify_Status_Code",
				"country,country_Number", "027,native_Status", "006,is_LMS", "033,status_Code",
				"location,province_Code","message_type_code,message_Type_Code" });
		addCodeTranslate(WhiteList.class, new String[] { "001,route_Name_Code", "white_pool,pool_Code" });
		addCodeTranslate(SmsStatistics.class, new String[] { "business_user,business_User_Id" });
		addCodeTranslate(SmsStatisticsExt.class, new String[] { "message_type_code,message_Type_Code","location,province_Code", "country,country_Number" });
		addCodeTranslate(SmsTemplate.class,
				new String[] { "trade,template_Type", "templateCheckStatus,approve_Status" });
		addCodeTranslate(Product.class, new String[] { "state,status_Code", "trade,trade_Type_Code" });
		addCodeTranslate(SensitiveWord.class,
				new String[] { "trade,trade_Type_Code", "sensitive_word_pool,pool_Code" });
		addCodeTranslate(ProductChannels.class, new String[] { "message_type_code,message_Type_Code", "country,country_Number" });
		addCodeTranslate(Invoice.class, new String[] { "dispose_state_code,dispose_State_Code" });
		addCodeTranslate(InterceptStrategy.class,
				new String[] { "faild_type_code,faild_Type_Code", "black_pool,black_Pool_Code",
						"white_pool,white_Pool_Code","external_Black_Pool,external_Black_Pool",
							"sensitive_word_pool,sensitive_Word_Pool_Code" });
		addCodeTranslate(Alarm.class, new String[] { "alarm_type,type_Code", "alarm_status,status","alarm_total,alarm_Total"
				, "alarm_probe_time,probe_Time", "probe_result,probe_Result", "wechat_party,wechat", "ding_talk_party,ding_Talk" });
		addCodeTranslate(AlarmLog.class, new String[] { "alarm_type,type_Code","probe_result,probe_Result" });
		addCodeTranslate(Input.class, new String[] { "019,audit_Status_Code","message_type_code,message_Type_Code", "is_lms,is_LMS" });
		addCodeTranslate(MmsTemplate.class, new String[] { "trade,trade_Type_Code", "templateCheckStatus,approve_Status" });
		addCodeTranslate(MmsMaterial.class,
				new String[]{"material_type_code,material_Type_Code", "audit_status,approve_Status"});
		addCodeTranslate(EnterpriseUserFee.class,
				new String[]{"country,country_Number","trade,trade_Type_Code","message_type_code,message_Type_Code",
						"fee_type_code,fee_Type_Code"});
		addCodeTranslate(ChannelFee.class,
				new String[]{"trade,trade_Type_Code","message_type_code,message_Type_Code","country,country_Number" });
		addCodeTranslate(ProductChannelsDiversion.class, new String[]{"024,status_Code"});
        addCodeTranslate(Enterprise.class, new String[] {"003,status",
                "authentication_State,authentication_State_Code" });
		addCodeTranslate(SmsRealTimeStatisticsExt.class,
				new String[]{"country,country_Number","location,province_Code","business_user,business_User_Id","message_type_code,message_Type_Code"});
		addCodeTranslate(Properties.class, new String[] { "006,property_Value"});

		addExtInfo(Submit.class,
				new KV[] { new KV("enterprise_No,getEnterpriseTranslateByNo", DatabaseCache.getInstance()),
						new KV("channel_No,getChannelTranslateByNo", DatabaseCache.getInstance()),
						new KV("enterprise_User_Id,getEnterpriseUserTranslateById", DatabaseCache.getInstance()) });
		addExtInfo(SubmitExt.class,
				new KV[] { new KV("enterprise_No,getEnterpriseTranslateByNo", DatabaseCache.getInstance()),
						new KV("channel_No,getChannelTranslateByNo", DatabaseCache.getInstance()),
						new KV("enterprise_User_Id,getEnterpriseUserTranslateById", DatabaseCache.getInstance()) });
		addExtInfo(Report.class,
				new KV[] { new KV("enterprise_No,getEnterpriseTranslateByNo", DatabaseCache.getInstance()),
						new KV("enterprise_User_Id,getEnterpriseUserTranslateById", DatabaseCache.getInstance()),
						new KV("channel_No,getChannelTranslateByNo", DatabaseCache.getInstance()) });
		addExtInfo(ReportExt.class,
				new KV[] { new KV("enterprise_No,getEnterpriseTranslateByNo", DatabaseCache.getInstance()),
						new KV("enterprise_User_Id,getEnterpriseUserTranslateById", DatabaseCache.getInstance()),
						new KV("channel_No,getChannelTranslateByNo", DatabaseCache.getInstance()) });
		addExtInfo(ReportNotify.class,
				new KV[] { new KV("enterprise_No,getEnterpriseTranslateByNo", DatabaseCache.getInstance()),
						new KV("channel_No,getChannelTranslateByNo", DatabaseCache.getInstance()),
						new KV("enterprise_User_Id,getEnterpriseUserById",DatabaseCache.getInstance())});
		addExtInfo(ReportNotifyExt.class,
				new KV[] { new KV("enterprise_No,getEnterpriseTranslateByNo", DatabaseCache.getInstance()),
						new KV("channel_No,getChannelTranslateByNo", DatabaseCache.getInstance()),
						new KV("enterprise_User_Id,getEnterpriseUserById",DatabaseCache.getInstance())});
		addExtInfo(BlackListExt.class,
				new KV[] { new KV("channel_No,getChannelTranslateByNo", DatabaseCache.getInstance()),
						new KV("enterprise_No,getEnterpriseTranslateByNo", DatabaseCache.getInstance()),
						new KV("enterprise_User_Id,getEnterpriseUserTranslateById", DatabaseCache.getInstance()) });
		addExtInfo(Input.class,
				new KV[] { new KV("enterprise_No,getEnterpriseTranslateByNo", DatabaseCache.getInstance()),
						new KV("enterprise_User_Id,getEnterpriseUserTranslateById", DatabaseCache.getInstance()),
						 });
		addExtInfo(InputExt.class,
				new KV[] { new KV("enterprise_No,getEnterpriseTranslateByNo", DatabaseCache.getInstance()),
						new KV("enterprise_User_Id,getEnterpriseUserTranslateById", DatabaseCache.getInstance()) });
		addExtInfo(InputLogExt.class,
				new KV[] { new KV("enterprise_No,getEnterpriseTranslateByNo", DatabaseCache.getInstance()),
						new KV("enterprise_User_Id,getEnterpriseUserTranslateById", DatabaseCache.getInstance()),
						new KV("audit_Admin_User_Id,getAdminUserTranslateById", DatabaseCache.getInstance())});
		addExtInfo(Inbox.class,
				new KV[] { new KV("enterprise_No,getEnterpriseTranslateByNo", DatabaseCache.getInstance()),
						new KV("enterprise_User_Id,getEnterpriseUserTranslateById", DatabaseCache.getInstance()),
						new KV("channel_No,getChannelTranslateByNo", DatabaseCache.getInstance()) });
		addExtInfo(WhiteList.class,
				new KV[] { new KV("enterprise_No,getEnterpriseTranslateByNo", DatabaseCache.getInstance()),
						new KV("enterprise_User_Id,getEnterpriseUserTranslateById", DatabaseCache.getInstance()),
						new KV("channel_No,getChannelTranslateByNo", DatabaseCache.getInstance()) });
		addExtInfo(SmsStatistics.class,
				new KV[] { new KV("enterprise_No,getEnterpriseTranslateByNo", DatabaseCache.getInstance()),
						new KV("channel_No,getChannelTranslateByNo", DatabaseCache.getInstance()) });
		addExtInfo(SmsStatisticsExt.class,
				new KV[] { new KV("business_User_Id,getAdminUserTranslateById", DatabaseCache.getInstance()),
						new KV("enterprise_No,getEnterpriseTranslateByNo", DatabaseCache.getInstance()),
						new KV("enterprise_User_Id,getEnterpriseUserTranslateById", DatabaseCache.getInstance()),
						new KV("channel_No,getChannelTranslateByNo", DatabaseCache.getInstance()) });
		addExtInfo(SmsTemplate.class,
				new KV[] { new KV("enterprise_No,getEnterpriseTranslateByNo", DatabaseCache.getInstance()),
						new KV("enterprise_User_Id,getEnterpriseUserTranslateById", DatabaseCache.getInstance()) });
		addExtInfo(ChargeOrderExt.class,
				new KV[] { new KV("enterprise_User_Id,getEnterpriseUserTranslateById", DatabaseCache.getInstance()) });
		addExtInfo(ChargeOrder.class,
				new KV[] { new KV("enterprise_User_Id,getEnterpriseUserTranslateById", DatabaseCache.getInstance()) });
		addExtInfo(Product.class,
				new KV[] { new KV("intercept_Strategy_Id,getInterceptStrategyById", DatabaseCache.getInstance()) });
		addExtInfo(CodeExt.class, new KV[] { new KV("sort_Code,getCodeSortByNo", DatabaseCache.getInstance()),
				new KV("up_Code,getCodeByNo", DatabaseCache.getInstance()) });
		addExtInfo(EnterpriseUser.class,
				new KV[] { new KV("enterprise_No,getEnterpriseTranslateByNo", DatabaseCache.getInstance())
                       , new KV("product_No,getProductTranslateByNo", DatabaseCache.getInstance()) });
		addExtInfo(MmsTemplate.class,
				new KV[] { new KV("enterprise_No,getEnterpriseTranslateByNo", DatabaseCache.getInstance()),
                        new KV("enterprise_User_Id,getEnterpriseUserTranslateById", DatabaseCache.getInstance()) });
		addExtInfo(MmsMaterial.class, new KV[]{
				new KV("enterprise_No,getEnterpriseTranslateByNo", DatabaseCache.getInstance())
				, new KV("enterprise_User_Id,getEnterpriseUserTranslateById", DatabaseCache.getInstance())
		});
		addExtInfo(ProductChannels.class, new KV[]{
				new KV("channel_No,getChannelTranslateByNo", DatabaseCache.getInstance())
		});
		addExtInfo(SmsRealTimeStatisticsExt.class, new KV[]{
                new KV("business_User_Id,getAdminUserTranslateById", DatabaseCache.getInstance()),
				new KV("enterprise_No,getEnterpriseTranslateByNo", DatabaseCache.getInstance()),
				new KV("enterprise_User_Id,getEnterpriseUserTranslateById", DatabaseCache.getInstance()),
				new KV("channel_No,getChannelTranslateByNo", DatabaseCache.getInstance()),
		});
		addExtInfo(SubmitAwaitExt.class,
				new KV[] { new KV("enterprise_No,getEnterpriseTranslateByNo", DatabaseCache.getInstance()),
						new KV("channel_No,getChannelTranslateByNo", DatabaseCache.getInstance()),
						new KV("enterprise_User_Id,getEnterpriseUserTranslateById", DatabaseCache.getInstance()) });
		addExtInfo(ReportNotifyAwaitExt.class,
				new KV[] { new KV("enterprise_No,getEnterpriseTranslateByNo", DatabaseCache.getInstance()),
						new KV("channel_No,getChannelTranslateByNo", DatabaseCache.getInstance()),
						new KV("enterprise_User_Id,getEnterpriseUserById",DatabaseCache.getInstance())});
		addCodeTranslate(SubmitAwait.class, new String[] { "034,submit_Status_Code", "027,stat", "006,is_LMS",
				"location,province_Code", "country,country_Number" ,"message_type_code,message_Type_Code"});
		addCodeTranslate(ReportNotifyAwait.class, new String[] { "034,submit_Status_Code", "032,notify_Status_Code",
				"country,country_Number", "027,native_Status", "006,is_LMS", "033,status_Code", "location,province_Code",
				"message_type_code,message_Type_Code" });
		addExtInfo(SubmitAwait.class,
				new KV[] { new KV("enterprise_No,getEnterpriseTranslateByNo", DatabaseCache.getInstance()),
						new KV("channel_No,getChannelTranslateByNo", DatabaseCache.getInstance()),
						new KV("enterprise_User_Id,getEnterpriseUserTranslateById", DatabaseCache.getInstance()) });
		addExtInfo(ReportNotifyAwait.class,
				new KV[] { new KV("enterprise_No,getEnterpriseTranslateByNo", DatabaseCache.getInstance()),
						new KV("enterprise_User_Id,getEnterpriseUserTranslateById", DatabaseCache.getInstance()),
						new KV("channel_No,getChannelTranslateByNo", DatabaseCache.getInstance()) });


		// 排除属性
		addExcludeProperty(ExportFile.class, new String[] { "file_Url" });
	}

	public FilterObjectMapper addBlurProperty(Class<?> type, String[] fields){
		filterProvider.addBlur(type, fields);
		return this;
	}

	public FilterObjectMapper addExcludeProperty(Class<?> type, String[] fields) {
		filterProvider.addExclude(type, fields);
		return this;
	}

	public FilterObjectMapper addIncludeProperty(Class<?> type, String[] fields) {
		filterProvider.addInclude(type, fields);
		return this;
	}

	@Deprecated
	public FilterObjectMapper addCodeTranslate(Class<?> clazz, String codeSort, String codeFiled) {
		return addCodeTranslate(clazz, new String[] { codeSort + "," + codeFiled });
	}

	public FilterObjectMapper addCodeTranslate(Class<?> clazz, String[] codes) {
		filterProvider.addTranslation(clazz, codes);
		return this;
	}

	public FilterObjectMapper addExtInfo(Class<?> type, KV[] kvs) {
		filterProvider.addExtInfo(type, new HashSet<KV>(Arrays.asList(kvs)));
		return this;
	}

	@Override
	public String asString(Object value) {
		try {
			this.setFilterProvider(filterProvider);
			return this.writeValueAsString(value);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
