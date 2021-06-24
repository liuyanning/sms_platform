package com.hero.wireless.web.util;

import com.drondea.sms.common.util.CommonUtil;
import com.drondea.sms.thirdparty.SmsAlphabet;
import com.drondea.wireless.config.Constant;
import com.drondea.wireless.util.IpUtil;
import com.drondea.wireless.util.ServiceException;
import com.drondea.wireless.util.SuperLogger;
import com.fasterxml.jackson.core.type.TypeReference;
import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import com.hero.wireless.enums.*;
import com.hero.wireless.json.JsonUtil;
import com.hero.wireless.okhttp.CharsetResponseBody;
import com.hero.wireless.okhttp.HttpClient;
import com.hero.wireless.web.config.DatabaseCache;
import com.hero.wireless.web.entity.base.ShardingBatchInsert;
import com.hero.wireless.web.entity.business.Properties;
import com.hero.wireless.web.entity.business.*;
import com.hero.wireless.web.entity.send.*;
import org.apache.commons.collections4.ListUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.charset.Charset;
import java.util.*;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * 
 * @author Lenovo
 *
 */
public abstract class SMSUtil {
	public final static int ASCII_MAX_BYTE_LENGTH = 159;
	public final static int UC2_MAX_BYTE_LENGTH = 140;
	// 长短信协议头格式长度
	public final static int UDHI_BYTE_LENGTH = 6;
	public final static String DEFAULT_NO = "0000000000000000";
	public final static String SP_NUMBER = "";
	public final static String SMS_RESEND_KEY = "smsResend";
	public final static String  INVALID_DATA = "-1";


	public final static PhoneNumberUtil PHONE_NUMBER_UTIL = PhoneNumberUtil.getInstance();

	/**
	 * 使用预编译可以增加匹配速度
	 */
	private static final Pattern PATTERN = Pattern.compile("【(?<title>.+?)】");

	/**
	 * null值对象，全局
	 */
	public static NullEnterpriseUserFee NULL_ENTERPRISE_USER_FEE = new NullEnterpriseUserFee();
	public static NullChannelFee NULL_CHANNEL_FEE = new NullChannelFee();
	public static MobileArea NULL_MOBILE_AREA = new MobileArea();
	static {
		NULL_MOBILE_AREA.setMobile_Area("无");
	}



	/**
	 * 
	 * 
	 * @author volcano
	 * @date 2019年9月14日上午3:58:26
	 * @version V1.0
	 */
	public static class NullEnterpriseUserFee extends EnterpriseUserFee {
		/**
		 * 
		 */
		private static final long serialVersionUID = 8873289428253762736L;

		private NullEnterpriseUserFee() {
			this(DEFAULT_NO);
		}

		public NullEnterpriseUserFee(String enterpriseNo) {
			this.setEnterprise_No(enterpriseNo);
			this.setTax_Point(BigDecimal.valueOf(0));
			this.setUnit_Price(BigDecimal.valueOf(0));
			this.setMessage_Type_Code(MessageType.UNKNOW.toString());
			this.setOperator(Operator.UNKNOW.toString());
		}
	}

	/**
	 * 
	 * 
	 * @author volcano
	 * @date 2019年9月14日上午5:09:56
	 * @version V1.0
	 */
	public static class NullChannel extends Channel {
		private static final long serialVersionUID = -7180209559232100351L;

		public NullChannel() {
			this.setNo(DEFAULT_NO);
			this.setSp_Number(SP_NUMBER);
		}
	}


	/**
	 * 默认发送短信企业，例如系统中用的告警短信等
	 * 
	 * @author volcano
	 * @date 2019年9月14日上午5:15:16
	 * @version V1.0
	 */
	public static class DefaultEnterprise extends Enterprise {
		/**
		 * 
		 */
		private static final long serialVersionUID = -1832513644749763513L;

		public DefaultEnterprise() {
			this.setNo(DatabaseCache.getStringValueBySystemEnvAndCode("platform_default_enterprise_no", DEFAULT_NO));
			this.setBusiness_User_Id(0);
		}
	}

	public static class NullProperty extends Properties {
		public NullProperty() {
		}
	}

	/**
	 * 
	 * 
	 * @author volcano
	 * @date 2019年9月14日上午5:18:29
	 * @version V1.0
	 */
	public static class DeaultEnterpriseUser extends EnterpriseUser {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1945175699205236649L;

		public DeaultEnterpriseUser() {
			this.setEnterprise_No(
					DatabaseCache.getStringValueBySystemEnvAndCode("platform_default_enterprise_no", DEFAULT_NO));
			this.setId(
					DatabaseCache.getIntValueBySortCodeAndCode("system_env", "platform_default_enterprise_user_id", 1));
			this.setSuffix(DatabaseCache.getStringValueBySystemEnvAndCode("platform_default_sign", "【信息】"));
			this.setSub_Code(DatabaseCache.getStringValueBySystemEnvAndCode("platform_default_sub_code", "8888"));
		}
	}

	/**
	 * 
	 * 
	 * @author volcano
	 * @date 2019年9月14日上午3:58:29
	 * @version V1.0
	 */
	public static class NullChannelFee extends ChannelFee {
		/**
		 * 
		 */
		private static final long serialVersionUID = -2203763490032013410L;

		private NullChannelFee() {
			this.setTax_Point(BigDecimal.valueOf(0));
			this.setUnit_Price(BigDecimal.valueOf(0));
			this.setMessage_Type_Code(MessageType.UNKNOW.toString());
			this.setOperator(Operator.UNKNOW.toString());
		}
	}

	/**
	 * 计算内容计费条数
	 * 
	 * @param smsContent
	 *            短信内容
	 * @return
	 */
	public static int getSmsCount(String smsContent) {
		return splitContent(smsContent).length;
	}

	/**
	 * 拆分内容
	 *
	 * @param smsContent
	 * @return
	 */
	public static String[] splitContent(String smsContent) {
		Charset charset = switchCharset(smsContent);
		byte[] bytes = smsContent.getBytes(charset);
		// 字节拆分长度
		int splitSize = 0;
		if (charset.equals(Charset.forName("ISO-8859-1"))) {
			splitSize = bytes.length > ASCII_MAX_BYTE_LENGTH ? ASCII_MAX_BYTE_LENGTH - UDHI_BYTE_LENGTH
					: ASCII_MAX_BYTE_LENGTH;
		} else {
			splitSize = bytes.length > UC2_MAX_BYTE_LENGTH ? UC2_MAX_BYTE_LENGTH - UDHI_BYTE_LENGTH
					: UC2_MAX_BYTE_LENGTH;
		}
		// 转成List，用工具类拆分
		List<Byte> byteList = new ArrayList<>(Arrays.asList(ArrayUtils.toObject(bytes)));
		List<List<Byte>> partitionList = ListUtils.partition(byteList, splitSize);
		return partitionList.stream().map(item -> {
			return new String(ArrayUtils.toPrimitive(item.toArray(new Byte[] {})), charset);
		}).collect(Collectors.toList()).toArray(new String[] {});
	}

	public static Report buildReport(Submit submit) {
		return buildReport(submit, submit.getPhone_No());
	}

	public static Report buildReport(Submit submit, String mobile) {
		Channel baseInfo = DatabaseCache.getChannelCachedByNo(submit.getChannel_No());
		Report report = new Report();
		//对象拷贝
		CopyUtil.SUBMIT_REPORT_COPIER.copy(submit, report, null);
		report.setId(null);
		report.setSP_Number(baseInfo == null ? new NullChannel().getSp_Number() : baseInfo.getSp_Number());
		report.setPhone_No(mobile);
		report.setSub_Code(submit.getInput_Sub_Code());
		report.setStatus_Code(ReportStatus.UNKNOWN.toString());
		report.setCreate_Date(new Date());
		return report;
	}

	/**
	 * 2019-08-20
	 * 
	 * @version 1.0.0.1 zly优化
	 * @param
	 * @param
	 * @param submit
	 */
	public static void computeProfit(Submit submit, ChannelFee channelFee, EnterpriseUserFee userFee) {
		if (channelFee == null) {
			channelFee = NULL_CHANNEL_FEE;
		}
		if (userFee == null) {
			userFee = NULL_ENTERPRISE_USER_FEE;
		}

		int scale = 6;
		// 通道成本
		BigDecimal channelUnitPrice = NumberUtils.toScaledBigDecimal(channelFee.getUnit_Price(), scale,
				RoundingMode.UP);
		// 通道税点
		BigDecimal channelTaxPoint = NumberUtils.toScaledBigDecimal(channelFee.getTax_Point(), scale, RoundingMode.UP);
		// 用户税点
		BigDecimal userTaxPoint = NumberUtils.toScaledBigDecimal(userFee.getTax_Point(), scale, RoundingMode.UP);
		// 用户单价
		BigDecimal userUnitPrice = NumberUtils.toScaledBigDecimal(userFee.getUnit_Price(), scale, RoundingMode.UP);
		// 给企业开票的税点
		BigDecimal userTaxes = userUnitPrice.multiply(userTaxPoint);
		// 上游通道开票，相当于抵消
		BigDecimal channelTaxes = channelUnitPrice.multiply(channelTaxPoint);
		// 平台成本=企业单价+企业税点
		BigDecimal cost = userUnitPrice.add(userTaxPoint);
		// 平台利润=平台成本-通道成本+通道税点
		BigDecimal profit = cost.subtract(channelUnitPrice).add(channelTaxes);



		submit.setChannel_Unit_Price(channelUnitPrice.setScale(scale));
		submit.setChannel_Taxes(channelTaxes.setScale(scale));
		submit.setAgent_Unit_Price(BigDecimal.valueOf(0));
		submit.setAgent_Taxes(BigDecimal.valueOf(0));
		submit.setEnterprise_User_Unit_Price(userUnitPrice.setScale(scale));
		submit.setEnterprise_User_Taxes(userTaxes.setScale(scale));
		submit.setAgent_Profits(BigDecimal.valueOf(0));
		submit.setProfits(profit.setScale(scale));
	}

	public static boolean isLms(String content) {
		return splitContent(content).length > 1;
	}

	/**
	 * 构建系统短信 需要改成接口提交
	 * 
	 * @param mobiles
	 * @param content
	 * @return
	 */
	@Deprecated
	public static Input buildSystemSms(String mobiles, String content, String countryCode) {
		Enterprise defaultEnterprise = new DefaultEnterprise();
		EnterpriseUser defaultUser = new DeaultEnterpriseUser();
		Input input = new Input();
		input.setMsg_Batch_No(CodeUtil.buildMsgNo());
		input.setEnterprise_No(defaultEnterprise.getNo());
		input.setEnterprise_User_Id(defaultUser.getId());
		input.setBusiness_User_Id(0);
		input.setIs_LMS(isLms(content));
		input.setCountry_Code(countryCode);
		input.setMessage_Type_Code(MessageType.SMS.toString());
		input.setContent(content + defaultUser.getSuffix());
		input.setPhone_Nos(mobiles);
		input.setPhone_Nos_Count(mobiles.split(Constant.MUTL_MOBILE_SPLIT).length);
		input.setProtocol_Type_Code(ProtocolType.SYSTEM.toString());
		input.setSource_IP(localIp());
		input.setPriority_Number(Priority.HIGH_LEVEL.value());
		input.setAudit_Status_Code(ContentAuditStatus.PASSED.toString());
		input.setSub_Code(defaultUser.getSub_Code());
		input.setUUID(UUID.randomUUID().toString());
		input.setInput_Date(new Date());
		input.setCreate_Date(new Date());
		return input;
	}


	/**
	 * 获取本地IP
	 * 
	 * @return
	 * @author volcano
	 * @date 2019年9月13日上午3:27:48
	 * @version V1.0
	 */
	public static String localIp() {
		try {
			return IpUtil.getLocalIp();
		} catch (Exception e) {
			return "0.0.0.0";
		}
	}

	/**
	 * 
	 * @param input
	 * @return
	 * @author volcano
	 * @date 2019年9月14日上午4:01:47
	 * @version V1.0
	 */
	public static InputLog buildInputLog(Input input) {
		InputLog inputLog = new InputLog();
		CopyUtil.INPUT_INPUTLOG_COPIER.copy(input, inputLog, null);
		inputLog.setId(null);
		inputLog.setFee_Count(0);
		inputLog.setFaild_Count(0);
		inputLog.setContent_Length(input.getContent().length());
		inputLog.setCreate_Date(new Date());
		return inputLog;
	}


	/**
	 * 修正获取方式
	 * 
	 * @param userSubCode
	 * @param inputSubcode
	 * @return
	 * @author volcano
	 * @date 2019年10月8日下午5:13:25
	 * @version V1.0
	 */
	public static String getSubCode(String userSubCode, String inputSubcode, Channel channel) {
		Map<String, ChannelUtil.OtherParameter> otherParameters = com.hero.wireless.web.util.ChannelUtil.getParameter(channel);
		int srcIdLength = ChannelUtil.getParameterIntValue(otherParameters, "src_id_length", 21);
		StringBuilder subCode = new StringBuilder();
		if (!StringUtils.isEmpty(userSubCode)) {
			subCode.append(userSubCode);
		}
		if (!StringUtils.isEmpty(inputSubcode)) {
			subCode.append(inputSubcode);
		}
		if (subCode.length() > srcIdLength) {
			return subCode.toString().substring(0, srcIdLength);
		}
		return subCode.toString();

	}

	/**
	 * 
	 * @param channel
	 * @param subCode
	 * @return
	 * @author volcano
	 * @date 2019年10月8日下午5:35:01
	 * @version V1.0
	 */
	public static String getSpNumber(Channel channel, String subCode) {
		subCode = StringUtils.defaultString(subCode, "");
		if (StringUtils.isEmpty(channel.getSp_Number())) {
			return subCode;
		}
		String[] channelSpNumbers = channel.getSp_Number().split(",");
		String channelSpNumber = channelSpNumbers[0];
		if (channelSpNumbers.length > 1) {
			channelSpNumber = channelSpNumbers[new Random().nextInt(channelSpNumbers.length)];
		}
		String spNumber = channelSpNumber + subCode;
		Map<String, ChannelUtil.OtherParameter> otherParameters = com.hero.wireless.web.util.ChannelUtil.getParameter(channel);
		int srcIdLength = ChannelUtil.getParameterIntValue(otherParameters, "src_id_length", 21);
		if (spNumber.length() > srcIdLength) {
			return spNumber.substring(0, srcIdLength);
		}
		return spNumber;
	}

	/**
	 * 
	 * @param channel
	 * @param input
	 * @param mobiles
	 * @param count
	 * @param subCode
	 * @return
	 * @throws Exception
	 * @author volcano
	 * @date 2019年9月14日上午4:45:40
	 * @version V1.0
	 */
	private static Submit buildSubmit(Channel channel, Input input, String mobiles, int count, String subCode) {
		Submit submit = new Submit();
		CopyUtil.INPUT_SUBMIT_COPIER.copy(input, submit, null);
		submit.setId(null);
		submit.setChannel_No(channel.getNo());
		submit.setPhone_No(mobiles);
		submit.setSub_Code(subCode);
		submit.setInput_Sub_Code(input.getSub_Code());
		submit.setContent_Length(input.getContent().length());
		return submit;
	}

	public static ReportNotify buildReportNotify(Report report) {
		ReportNotify reportNotify = new ReportNotify();
		CopyUtil.REPORT_REPORTNOTIFY_COPIER.copy(report, reportNotify, null);
		reportNotify.setId(null);
		reportNotify.setCreate_Date(new Date());
		return reportNotify;
	}

	public static ReportNotify buildReportNotify(Inbox inbox) {
		ReportNotify reportNotify = new ReportNotify();
		reportNotify.setMsg_Batch_No(inbox.getInput_Msg_No());
		reportNotify.setChannel_Msg_Id("MO");
		reportNotify.setSP_Number(inbox.getSP_Number());
		reportNotify.setCountry_Code(inbox.getCountry_Code());
		reportNotify.setPhone_No(inbox.getPhone_No());
		reportNotify.setChannel_No(inbox.getChannel_No());
		reportNotify.setSub_Code(inbox.getSub_Code());
		reportNotify.setEnterprise_No(inbox.getEnterprise_No());
		reportNotify.setAgent_No(inbox.getAgent_No());
		reportNotify.setEnterprise_User_Id(inbox.getEnterprise_User_Id());
		reportNotify.setBusiness_User_Id(inbox.getEnterprise_User_Id());
		reportNotify.setOperator("MO");
		reportNotify.setArea_Name("");
		reportNotify.setNative_Status("MO");
		reportNotify.setProvince_Code("");
		reportNotify.setMessage_Type_Code("sms");
		reportNotify.setContent(inbox.getContent());
		reportNotify.setContent_Length(0);
		reportNotify.setSequence(1);
		reportNotify.setProtocol_Type_Code(inbox.getProtocol_Type_Code());
		reportNotify.setSubmit_Description("");
		reportNotify.setSubmit_Date(inbox.getCreate_Date());
		reportNotify.setCreate_Date(new Date());
		return reportNotify;
	}

	/**
	 * 
	 * @param channel
	 * @param input
	 * @param mobileList
	 * @param subCode
	 * @return
	 * @throws Exception
	 * @author volcano
	 * @date 2019年9月14日上午4:47:24
	 * @version V1.0
	 */

	public static List<Submit> buildSubmits(Channel channel, Input input, Collection<String> mobileList,
			String subCode) {
		List<Submit> submitList = new ArrayList<Submit>();
		boolean isHttpChannel = ProtocolType.HTTP.equals(channel.getProtocol_Type_Code());
		// http通道支持多个号码发送
		if (isHttpChannel) {
			String symbol = ChannelUtil.getParameter(channel, "mobile_split", ",");
			String httpChannelMobiles = StringUtils.join(mobileList, symbol);
			submitList.add(buildSubmit(channel, input, httpChannelMobiles, mobileList.size(), subCode));
			return submitList;
		}
		mobileList.forEach(m -> {
			try {
				submitList.add(buildSubmit(channel, input, m, 1, subCode));
			} catch (Exception e) {
				SuperLogger.error(e.getMessage(), e);
			}
		});
		return submitList;
	}

	public static String getCountryCOdeByPhoneNo(String mobile) {
//		PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
		Phonenumber.PhoneNumber phoneNumber = null;
		try {
			phoneNumber = PHONE_NUMBER_UTIL.parse("+"+mobile, "");
		} catch (NumberParseException e) {
			SuperLogger.error("获取国家编码异常", e);
			return null;
		}
		return phoneNumber==null?null:String.valueOf(phoneNumber.getCountryCode());
	}

	/**
	 * 是否国际号码
	 * 
	 * @param phoneNo
	 * @return
	 * @author volcano
	 * @date 2019年9月15日上午1:37:01
	 * @version V1.0
	 */
	/*public static boolean isInternational(String phoneNo) {
		if (StringUtils.isEmpty(phoneNo)) {
			return false;
		}
		return phoneNo.startsWith("+");
	}*/

	/**
	 * 是否国际中国
	 * 
	 * @param phoneNo
	 * @return
	 * @author volcano
	 * @date 2019年11月1日下午6:07:19
	 * @version V1.0
	 */
	public static boolean isInternationalChina(String phoneNo) {
		if (StringUtils.isEmpty(phoneNo)) {
			return false;
		}
		return phoneNo.startsWith("+86");
	}

	/**
	 * 匹配短信模板 String content = "您的验证码:123123,请勿告知他人!失效时间5分钟"; String temp =
	 * "您的验证码:{1},请勿告知他人!失效时间{2}分钟";
	 * 
	 * @param user
	 * @param content
	 * @return
	 * @author volcano
	 * @date 2019年9月17日上午6:02:34
	 * @version V1.0
	 */
	public static boolean matchSmsTemplate(EnterpriseUser user, String content) {
		//改成从缓存拿,并且缓存编译提高效率
		List<Pattern> list = DatabaseCache.getSmsTemplateListCached(user.getEnterprise_No(), user.getId(),
				Constant.SMS_TEMPLAT_CHECK_STATUS_PASS);
		if (ObjectUtils.isEmpty(list)) {
			return false;
		}
		try {
			return list.stream().filter(item -> item != null).anyMatch(pattern -> {
				Matcher m = pattern.matcher(content);
				return m.matches();
			});
		} catch (Exception e) {
			SuperLogger.error("模板匹配异常："+e.getMessage());
			return false;
		}
	}
	

	/**
	 * 
	 * @Title: comparisonOfTheContent   
	 * @Description: 短信内容和模板内容校验 
	 * @author yjb  
	 * @param templateContent
	 * @param smsContent
	 * @return boolean 
	 * @date 2020-04-15
	 */
	public static boolean comparisonOfTheContent (String templateContent, String smsContent){
		Pattern templatePattern = getTemplatePattern(templateContent);
		if (templatePattern == null) {
			return false;
		}
		try {
			Matcher m = templatePattern.matcher(smsContent);
			return m.matches();
		} catch (Exception e) {
			SuperLogger.error("模板匹配异常："+e.getMessage());
			return false;
		}
	}


	/**
	 * 转义正则特殊字符 （$()*+.[]?\^{},|）
	 *
	 * @param keyword
	 * @return
	 */
	public static String escapeExprSpecialWord(String keyword) {
		if (StringUtils.isNotBlank(keyword)) {
			String[] fbsArr = { "\\", "$", "(", ")", "*", "+", ".", "[", "]", "?", "^", "|" };
			for (String key : fbsArr) {
				if (keyword.contains(key)) {
					keyword = keyword.replace(key, "\\" + key);
				}
			}
		}
		return keyword;
	}

	/**
	 * 通过模板内容生成正则表达式对象
	 * @param templateContent
	 * @return
	 */
	public static Pattern getTemplatePattern(String templateContent){
		templateContent = escapeExprSpecialWord(templateContent);
		templateContent = StringUtils.trim(templateContent).replaceAll("\\{\\d+\\}", DatabaseCache.getStringValueBySystemEnvAndCode("sms_template_regular",
				"([-%\\\\+\\\\“\\\\”\\\\*\\\\u4e00-\\\\u9fa5a-zA-Z0-9\\.:/\\?\\\\[\\\\]]{1,6})"));
		templateContent = "^" + templateContent + "$";
		try{
			return Pattern.compile(templateContent);
		}catch (Exception e) {
			SuperLogger.error("模板正则匹配错误：" + e.getMessage());
			return null;
		}
	}

	/**
	 * 设置签名位置
	 * 
	 * @param
	 * @return
	 */
	public static String setSignaturePosition(String content, SignaturePosition position) {
		String signature = getSignature(content);
		if (StringUtils.isEmpty(signature)) {
			return content;
		}
		if (position.equals(SignaturePosition.PREFIX)) {
			return signature + clearSignature(content, signature);
		}
		return clearSignature(content, signature) + signature;
	}

	/**
	 * 将签名位置按照设定的头签、尾签换位置
	 * @param content
	 * @param signature
	 * @param position
	 * @return
	 */
	public static String resetSignaturePosition(String content, String signature, SignaturePosition position) {
		if (StringUtils.isEmpty(signature)) {
			return content;
		}
		if (position.equals(SignaturePosition.PREFIX)) {
			return signature + clearSignature(content, signature);
		}
		return clearSignature(content, signature) + signature;
	}

	/**
	 * 去除签名，去头或者去尾
	 * 
	 * @param
	 * @return String
	 * @exception @since
	 *                1.0.0
	 */
	public static String clearSignature(String content) {
		String signature = getSignature(content);
		return clearSignature(content, signature);
	}

	public static String clearSignature(String content, String signature) {
		if (StringUtils.isEmpty(signature)) {
			return content;
		}
		if (content.indexOf(signature) == 0) {
			return StringUtils.replace(content, signature, "");
		}
		if (content.lastIndexOf(signature) == content.length() - signature.length()) {
			return content.substring(0, content.lastIndexOf(signature));
		}
		return content;
	}

	/**
	 * 获取签名
	 * 
	 * @param content
	 * @return
	 * @author volcano
	 * @date 2019年9月26日下午3:46:04
	 * @version V1.0
	 */
	public static String getSignature(String content) {
		if (StringUtils.isEmpty(content)) {
			return null;
		}
		Matcher matcher = PATTERN.matcher(content);
		while (matcher.find()) {
			if (matcher.start() == 0) {
				return "【" + matcher.group("title") + "】";
			}
			if (matcher.end() == content.length()) {
				return "【" + matcher.group("title") + "】";
			}
		}
		return null;
	}

	/**
	 * 
	 * @param userId
	 * @return
	 * @author volcano
	 * @date 2019年10月8日下午5:41:48
	 * @version V1.0
	 */
	public static String getCmppUser(int userId) {
		return String.valueOf(100000 + userId);
	}

	/**
	 * 已经拆分了长短信的情况
	 * @param submit
	 */
	public static void saveFaildReportNotClone(Submit submit, boolean saveSubmit) {
		List<Report> reports = new ArrayList<Report>();
		List<Submit> submites = new ArrayList<Submit>();
		String mobile = submit.getPhone_No();
		submit.setSubmit_Status_Code(SubmitStatus.FAILD.toString());
		submit = SMSUtil.buildSubmitAreaAndOperator(submit);
		Report report = buildReport(submit, mobile);
		report.setStatus_Code(SubmitStatus.FAILD.toString());
		report.setNative_Status(ReportNativeStatus.UNDELIVRD.toString());
		report.setStatus_Date(new Date());
		reports.add(report);
		submites.add(submit);
		if (saveSubmit) {
			QueueUtil.saveSubmit(submites);
		}
		QueueUtil.saveReport(reports);
	}

	/**
	 * 
	 * @param submit
	 * @author volcano
	 * @date 2019年11月13日下午10:07:18
	 * @version V1.0
	 */
	public static void saveFailedReports(Submit submit, boolean isNotifyReport) {
		List<Report> reports = new ArrayList<Report>();
		List<Submit> submits = new ArrayList<Submit>();
		String[] mobiles = submit.getPhone_No().split(Constant.MUTL_MOBILE_SPLIT);
		submit.setSubmit_Status_Code(SubmitStatus.FAILD.toString());
		String enterpriseMsgId = submit.getEnterprise_Msg_Id();
		String[] msgIds = null;
		if (StringUtils.isNotEmpty(enterpriseMsgId)) {
			msgIds = enterpriseMsgId.split(",");
		}
		String[] finalMsgIds = msgIds;
		Arrays.asList(mobiles).forEach(mobile -> {
			Submit cloneSubmit = new Submit();
			CopyUtil.SUBMIT_COPIER.copy(submit, cloneSubmit, null);
			String[] contents;
			if (MessageType.SMS.toString().equals(submit.getMessage_Type_Code())) {
				contents = SMSUtil.splitContent(submit.getContent());
			} else {
				contents = new String[1];
				contents[0] = submit.getContent();
			}
			for (int i = 0; i < contents.length; i++) {
				//下游提交是tcp协议需要拆分msgId
				if (finalMsgIds != null && i < finalMsgIds.length) {
					cloneSubmit.setEnterprise_Msg_Id(finalMsgIds[i]);
				}
				cloneSubmit.setSequence(i + 1);
				cloneSubmit.setContent(contents[i]);
				cloneSubmit.setContent_Length(contents[i].length());
				cloneSubmit.setPhone_No(mobile);
				cloneSubmit.setChannel_Msg_Id(CodeUtil.buildMsgNo());
				cloneSubmit = SMSUtil.buildSubmitAreaAndOperator(cloneSubmit);
				Report report = buildReport(cloneSubmit, mobile);
				report.setStatus_Code(SubmitStatus.FAILD.toString());
				report.setNative_Status(ReportNativeStatus.UNDELIVRD.toString());
				report.setStatus_Date(new Date());
				reports.add(report);
				submits.add(cloneSubmit);
			}
		});
		QueueUtil.saveSubmit(submits);
		QueueUtil.saveReport(reports);
	}

	/**
	 * 
	 * @param ch
	 * @return
	 * @author volcano
	 * @date 2019年11月16日下午9:09:19
	 * @version V1.0
	 */
	public static boolean isEmoji(char ch) {
		return !((ch == 0x0) || (ch == 0x9) || (ch == 0xA) || (ch == 0xD) || ((ch >= 0x20) && (ch <= 0xD7FF))
				|| ((ch >= 0xE000) && (ch <= 0xFFFD)) || ((ch >= 0x10000) && (ch <= 0x10FFFF)));
	}

	/**
	 * 
	 * @param content
	 * @return
	 * @author volcano
	 * @date 2019年11月16日下午9:09:16
	 * @version V1.0
	 */
	public static boolean containsEmoji(String content) {
		for (int i = 0; i < content.length(); i++) {
			if (isEmoji(content.charAt(i))) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 清楚emoji
	 * 
	 * @param s
	 * @return
	 * @author volcano
	 * @date 2019年11月23日上午4:49:09
	 * @version V1.0
	 */
	public static String cleanEmoji(String s) {
		StringBuilder builder = new StringBuilder(s);
		for (int i = 0; i < builder.length(); i++) {
			if (isEmoji(builder.charAt(i))) {
				builder.deleteCharAt(i).insert(i, ' ');
			}
		}
		return builder.toString().trim();
	}

	/**
	 * 2850C16E7EE2E153
	 * 
	 * @param submit
	 * @return
	 * @author volcano
	 * @date 2019年11月18日下午10:46:26
	 * @version V1.0
	 */
	public static Submit buildSubmitAreaAndOperator(Submit submit) {

		if (StringUtils.isEmpty(submit.getArea_Name())) {
			MobileArea area = DatabaseCache.getMobileAreaByNumber(submit.getPhone_No(), submit.getCountry_Code(), true);
			// 地域
			if (!ObjectUtils.isEmpty(area)) {
				submit.setArea_Name(area.getMobile_Area());
				submit.setProvince_Code(area.getProvince_Code());
			}
		}

		String operator = submit.getOperator();
		if (StringUtils.isEmpty(operator) || Operator.UNKNOW.toString().equalsIgnoreCase(operator)) {
			SmsRoute route = DatabaseCache.getSmsRouteCachedByNumber(submit.getPhone_No(), submit.getCountry_Code());
			if (!ObjectUtils.isEmpty(route)) {
				submit.setOperator(route.getRoute_Name_Code());
				submit.setCountry_Number(route.getCountry_Number());
				submit.setMCC(route.getMCC());
				submit.setMNC(route.getMNC());
			} else {
				submit.setOperator(Operator.UNKNOW.toString());
			}
		}

		ChannelFee channelFee = DatabaseCache.getChannelCachedFee(submit.getChannel_No(), submit.getMessage_Type_Code(),
				submit.getOperator());
		EnterpriseUserFee userFee = DatabaseCache.getEnterpriseUserFeeCached(submit.getEnterprise_No(),
				submit.getEnterprise_User_Id(), submit.getMessage_Type_Code(), submit.getOperator());
		computeProfit(submit, channelFee, userFee);
		return submit;
	}

	/**
	 * 分拣单独调用减少redis读取
	 * @param submit
	 * @param userFee
	 * @return
	 */
	public static Submit buildSortSubmitAreaAndOperator(Submit submit, EnterpriseUserFee userFee) {
		ChannelFee channelFee = DatabaseCache.getChannelCachedFee(submit.getChannel_No(), submit.getMessage_Type_Code(),
				submit.getOperator());
		computeProfit(submit, channelFee, userFee);
		return submit;
	}

	/**
	 * 获取企业用户扩展参数 index0=====mo sp 格式
	 * 
	 * @param user
	 * @param index
	 * @return
	 * @author volcano
	 * @date 2019年11月22日下午1:12:05
	 * @version V1.0
	 */
	public static String getEnterpriseUserExtParams(EnterpriseUser user, int index, String defaultValue) {
		if (user == null) {
			return defaultValue;
		}
		String[] params = StringUtils.defaultIfEmpty(user.getDescription(), defaultValue).split(",");
		if (params.length == 0) {
			return defaultValue;
		}
		if (index >= params.length) {
			return defaultValue;
		}
		return params[index];
	}

	/**
	 * 选择编码
	 * 
	 * @param content
	 * @return
	 */
	public static Charset switchCharset(String content) {
		byte[] bytes = content.getBytes();
		for (int i = 0; i < bytes.length; i++) {
			// 判断最高位是否为1
			if ((bytes[i] & (byte) 0x80) == (byte) 0x80) {
				return CommonUtil.switchCharset(SmsAlphabet.UCS2);
			}
		}
		return CommonUtil.switchCharset(SmsAlphabet.ASCII);
	}
	
	//发送短信方法
    public static boolean sendSms(String url, Map<String, String> params, String charSet) {
        boolean result = false;
        try {
            HttpClient httpClient = new HttpClient();
            httpClient.setCharset(charSet);
            String httpContent = JsonUtil.STANDARD.writeValueAsString(params);
            CharsetResponseBody response = httpClient.post(url, httpContent,HttpClient.MEDIA_TYPE_JSON);
            Map<String, String> resultMap = JsonUtil.STANDARD.readValue(response.string(), new TypeReference<Map<String, String>>() {});
            if(!"0".equals(resultMap.get("result"))){//0:成功，-1：失败
               throw new ServiceException("提交短信返回数据："+resultMap.toString());
            }
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


	public static SmsAlphabet getAlphabet(byte dcs) {
		if (dcs == 0) {
			return SmsAlphabet.ASCII;
		} else {
			switch(dcs & 12) {
				case 0:
					return SmsAlphabet.ASCII;
				case 4:
					return SmsAlphabet.LATIN1;
				case 8:
					return SmsAlphabet.UCS2;
				case 12:
					return SmsAlphabet.RESERVED;
				default:
					return SmsAlphabet.UCS2;
			}
		}
	}


	/**
	 * 根据byte数据获取编码字符串
	 * @param charSet
	 * @return
	 */
	public static String getSmppCharsetByByte(byte charSet){

		String dcs = "ASCII";
		switch (charSet) {
			case 0:
				dcs = "GSM";
				break;
			case 1:
				dcs = "ASCII";
				break;
			case 3:
				dcs = "LATIN1";
				break;
			case 4:
				dcs = "LATIN1";
				break;
			case 8:
				dcs = "UCS2";
				break;
			case 12:
				dcs = "RESERVED";
				break;
			case 15:
				dcs = "RESERVED";
				break;
		}
		return dcs;
	}



	/**
	 * 根据byte数据获取编码字符串
	 * @param charSet
	 * @return
	 */
	public static String getCharsetByByte(byte charSet){

			String dcs = "ASCII";
			switch (charSet) {
				case 0:
					dcs = "ASCII";
					break;
				case 4:
					dcs = "LATIN1";
					break;
				case 8:
					dcs = "UCS2";
					break;
				case 12:
					dcs = "RESERVED";
					break;
				case 15:
					dcs = "GBK";
					break;
			}
			return dcs;
	}

	/**
	 * 根据编码字符串获取byte数据
	 * @param charSet
	 * @return
	 */
	public static byte getGeneralDataCodingDcs(String charSet) {
		byte dcs = 0x08;
		if(StringUtils.isEmpty(charSet)){
			return dcs;
		}
		switch (charSet) {
			case "ASCII":
				dcs |= 0x00;
				break;
			case "LATIN1":
				dcs |= 0x04;
				break;
			case "UCS2":
				dcs |= 0x08;
				break;
			case "RESERVED":
				dcs |= 0x0C;
				break;
			case "GBK":
				dcs |= 0x0F;
				break;
		}
		return dcs;
	}

	public static byte getSmppGeneralDataCodingDcs(String charSet) {
		byte dcs = 0x08;
		if(StringUtils.isEmpty(charSet)){
			return dcs;
		}
		switch (charSet) {
			case "GSM":
				dcs |= 0x00;
				break;
			case "ASCII":
				dcs |= 0x01;
				break;
			case "LATIN1":
				dcs |= 0x03;
				break;
			case "UCS2":
				dcs |= 0x08;
				break;
			case "RESERVED":
				dcs |= 0x0F;
				break;
		}
		return dcs;
	}

	private static boolean isResend(String dataStatusCode, String statusCode, String protocolTypeCode,
									String nativeStatus, Integer enterpriseUserId) {
	    try {
            //如果是失败才重发, NORMAL状态的才重发
            if(ReportStatus.SUCCESS.toString().equals(statusCode) || !DataStatus.NORMAL.toString().equals(dataStatusCode)) {
                return false;
            }
            String errorCode = DatabaseCache.getStringValueBySortCodeAndCode("error_code","report_native_status", "");
            // 判断是否是特定的错误码   比如空号  这些码不需要重发的
            if(StringUtils.isNotEmpty(nativeStatus) && StringUtils.isNotEmpty(errorCode)
					&& Arrays.asList(errorCode.split(",")).contains(nativeStatus)){
				return false;
            }
            EnterpriseUser user = DatabaseCache.getEnterpriseUserCachedById(enterpriseUserId);
            if (user == null) {
            	return false;
			}
            // 如果用户没有配备用产品，直接返回
            if(StringUtils.defaultIfEmpty(user.getBackup_Product_No(), INVALID_DATA).equals(INVALID_DATA)){
                return false;
            }
            // 如果不是成功计费的  不支持重发
            if(!FeeType.SUCCESS.toString().equals(user.getFee_Type_Code())){
                return false;
            }

            return true;
        }catch (Exception e){
	        SuperLogger.error(e.getMessage(),e);
            return false;
        }
	}

	/**
	 * 网关校验出错回执，如余额不足等。取消定时任务也使用了
	 * @param input
	 * @param exception
	 * @param description
	 */
	public static void notifyNetWayReport(Input input, ServiceException exception, String description) {
		String[] phoneNos = input.getPhone_Nos().split(Constant.MUTL_MOBILE_SPLIT);
		String[] contents = SMSUtil.splitContent(input.getContent());
		String enterprise_msg_id = input.getEnterprise_Msg_Id();
		String[] msgIds = null;
		if (StringUtils.isNotEmpty(enterprise_msg_id)) {
			msgIds = enterprise_msg_id.split(",");
		}
		String[] finalMsgIds = msgIds;
		Arrays.stream(phoneNos).forEach((phoneNo) -> {
			for (int i = 0; i < contents.length; i++) {
				String msgId = "";
				if (finalMsgIds != null && i < finalMsgIds.length) {
					msgId = finalMsgIds[i];
				}
				justNotifyReport(input, phoneNo, exception == null ? contents[i]: exception.getMessage(),
						i + 1, msgId, description);
			}
		});
	}

	/**
	 * 网关组装超时回执使用
	 * @param input
	 * @param phoneNos
	 * @param msgIds
	 * @param errorDesc
	 */
	public static void notifyNetWayReportByMsgId(Input input, String[] phoneNos, String[] msgIds, String errorDesc) {
		Arrays.stream(phoneNos).forEach((phoneNo) -> {
			for (int i = 0; i < msgIds.length; i++) {
				justNotifyReport(input, phoneNo, errorDesc,
						i + 1, msgIds[i], errorDesc);
			}
		});
	}

	private static void justNotifyReport(Input input, String phoneNo, String content,
										 int sequence, String msgId, String description) {
		Submit submit = new Submit();
		CopyUtil.INPUT_SUBMIT_COPIER.copy(input, submit, null);
		submit.setId(null);
		submit.setEnterprise_Msg_Id(null);
		submit.setChannel_No(new SMSUtil.NullChannel().getNo());
		submit.setContent_Length(content.length());
		submit.setContent(content);
		submit.setSequence(sequence);
		submit.setPhone_No(phoneNo);
//				submit = SMSUtil.buildSubmitAreaAndOperator(submit);
		submit.setSubmit_Date(new Date());
		submit.setSubmit_Status_Code(SubmitStatus.FAILD.toString());
		submit.setSubmit_Description(description);
		submit.setInput_Sub_Code(input.getSub_Code());
		submit.setChannel_Msg_Id(CodeUtil.buildMsgNo());
		submit.setInput_Log_Date(input.getInput_Date());
		submit.setSubmit_Date(new Date());
		submit.setCreate_Date(new Date());
		//不设置签名了，没有意义
		submit.setSignature("");
		submit.setEnterprise_Msg_Id(msgId);
		// 状态报告
		Report report = SMSUtil.buildReport(submit, phoneNo);
		// report.setSP_Number(spNumber);
		report.setNative_Status(ReportStatus.REJECTD.toString().toUpperCase());
		report.setStatus_Code(ReportStatus.REJECTD.toString());
		report.setStatus_Date(new Date());
		//只推送回执
		QueueUtil.notifyReport(report);

	}

	public static List<List<String>> getPartitionMobileList(String phoneNos) {
		List<String> mobiles = new ArrayList<>();
		if (StringUtils.isNotEmpty(phoneNos)) {
			mobiles.addAll(Arrays.asList(phoneNos.split(Constant.MUTL_MOBILE_SPLIT)));
			mobiles = mobiles.stream().filter(no -> StringUtils.isNotEmpty(no)).collect(Collectors.toList());
		}
		return ListUtils.partition(mobiles, 500);
	}

	public static <T> List<ShardingBatchInsert<T>> getBatchInsert(List<T> list, Function<T, String> function) {
		Map<String, List<T>> dayGroup = list.stream().collect(Collectors.groupingBy(item -> function.apply(item)));
		List<ShardingBatchInsert<T>> insertList = new ArrayList<>();
		Set<String> allKeys = dayGroup.keySet();
		allKeys.forEach(key -> {
			insertList.add(new ShardingBatchInsert(key, dayGroup.get(key)));
		});
		return insertList;
	}

	public static void main(String[] args) throws Exception {
		EnterpriseUser user = new EnterpriseUser();
		user.setId(1);
		boolean b = matchSmsTemplate(user, "【省呗】尾号-5056用户，您的47800元額度已于7月3日到.达！日息低至2元，请24小时内领取(的），点击 tb59.cn/c 退回T");
		System.out.println(b);
	}

}
