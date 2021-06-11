package com.hero.wireless.sort;

import com.drondea.wireless.config.Constant;
import com.hero.wireless.enums.MessageType;
import com.hero.wireless.enums.ReportNativeStatus;
import com.hero.wireless.enums.ReportStatus;
import com.hero.wireless.enums.SubmitStatus;
import com.hero.wireless.web.entity.business.*;
import com.hero.wireless.web.entity.send.Input;
import com.hero.wireless.web.entity.send.InputLog;
import com.hero.wireless.web.entity.send.Report;
import com.hero.wireless.web.entity.send.Submit;
import com.hero.wireless.web.util.CodeUtil;
import com.hero.wireless.web.util.CopyUtil;
import com.hero.wireless.web.util.QueueUtil;
import com.hero.wireless.web.util.SMSUtil;
import com.hero.wireless.web.util.SMSUtil.NullChannel;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 短信分拣上下文
 * 
 * @author volcano
 * @date 2019年9月13日上午5:46:52
 * @version V1.0
 */
public class SortContext {


	/**
	 * 号码数据结构
	 * 
	 * @author volcano
	 * @date 2019年9月15日上午4:34:26
	 * @version V1.0
	 */
	public class SortPhoneNo {
		private SubmitStatus submitStatus;
		private ReportNativeStatus reportNativeStatus;
		private ReportStatus reportStatus;
		private EnterpriseUserFee userFee;
		private String mobile;
		private String msg;

		public SortPhoneNo(String mobile, String msg, EnterpriseUserFee userFee) {
			super();
			this.mobile = mobile;
			this.msg = msg;
			this.userFee = userFee;
		}

		public String getMobile() {
			return mobile;
		}

		public String getMsg() {
			return msg;
		}

		public EnterpriseUserFee getUserFee() {
			return userFee;
		}

		public void setUserFee(EnterpriseUserFee userFee) {
			this.userFee = userFee;
		}

		public void setSubmitStatus(SubmitStatus submitStatus) {
			this.submitStatus = submitStatus;
		}

		public SubmitStatus getSubmitStatus() {
			return submitStatus;
		}

		public ReportNativeStatus getReportNativeStatus() {
			return reportNativeStatus;
		}

		public void setReportNativeStatus(ReportNativeStatus reportNativeStatus) {
			this.reportNativeStatus = reportNativeStatus;
		}

		public ReportStatus getReportStatus() {
			return reportStatus;
		}

		public void setReportStatus(ReportStatus reportStatus) {
			this.reportStatus = reportStatus;
		}


		@Override
		public boolean equals(Object obj) {
			if (obj == null) {
				return false;
			}
			SortPhoneNo target = (SortPhoneNo) obj;
			return this.mobile.equals(target.getMobile());
		}

		@Override
		public int hashCode() {
			return mobile.hashCode();
		}
	};

	/**
	 * 
	 * 
	 * @author volcano
	 * @date 2019年9月15日上午4:38:09
	 * @version V1.0
	 */
	public class FaildPhoneNo extends SortPhoneNo {
		public FaildPhoneNo(String mobile, ReportNativeStatus reportNativeStatus, String msg, EnterpriseUserFee userFee) {
			super(mobile, msg, userFee);
			this.setSubmitStatus(SubmitStatus.SORT_FAILD);
			this.setReportStatus(ReportStatus.FAILD);
			this.setReportNativeStatus(reportNativeStatus);
		}
	}

	// 短信平台系统多个手机号分隔符
	public final static String MUTL_MOBILE_SPLIT = Constant.MUTL_MOBILE_SPLIT;
	private Enterprise enterprise;
	private EnterpriseUser enterpriseUser;
	private Input input;
	private InputLog inputLog = new InputLog();
	private SortChannelMap channelMap;
	private Product product;
	private String content;
	private String signature;
	private boolean isBlackSwitch;

	// 拦截策略
	private InterceptStrategy interceptStrategy;

	private List<FaildPhoneNo> faildList = new ArrayList<>();// 失败列表

	private SortLimitRepeat sortLimitRepeat;
	/* 短信内容拆分条数 */
	private int contentSplitCount = 1;
	/* 包含的敏感字 */
	private String containsSensitives = null;
	/**
	 * 保存号码的区域
	 */
	private ConcurrentHashMap<String, MobileArea> mobileAreas = new ConcurrentHashMap<>();
	/**
	 * 保存号码的路由
	 */
	private ConcurrentHashMap<String, SmsRoute> mobileRoutes = new ConcurrentHashMap<>();

	public SortContext(Input input) {
		super();
		this.input = input;
		// 去除特殊字符 空格 tab
		// this.input.setContent(this.input.getContent().replaceAll(" | | | ", ""));
		if (MessageType.SMS.toString().equalsIgnoreCase(input.getMessage_Type_Code())) {
			contentSplitCount = SMSUtil.getSmsCount(input.getContent());
		}
		// 去掉手机号的空格
		this.input.setPhone_Nos(StringUtils.deleteWhitespace(this.input.getPhone_Nos()));
		inputLog = SMSUtil.buildInputLog(input);
		inputLog.setInput_Sub_Code(input.getSub_Code());
	}

	/**
	 * 一般分拣失败会用到该方法 后者比率发送
	 * 
	 * @param mobileList
	 * @return
	 * @throws Exception
	 * @author volcano
	 * @date 2019年9月14日上午5:43:28
	 * @version V1.0
	 */
	public void saveSortFaildSubmit(List<? extends SortPhoneNo> mobileList) throws Exception {
		mobileList.forEach(item -> {
			SortPhoneNo sortPhoneNo = item;
			String[] contents = null;
			if (MessageType.SMS.toString().equals(input.getMessage_Type_Code())) {
				contents = SMSUtil.splitContent(input.getContent());
			} else {
				contents = new String[1];
				contents[0] = input.getContent();
			}
			String enterpriseMsgId = input.getEnterprise_Msg_Id();
			String[] msgIds = null;
			if (StringUtils.isNotEmpty(enterpriseMsgId)) {
				msgIds = enterpriseMsgId.split(",");
			}

			for (int i = 0; i < contents.length; i++) {
				String fragment = contents[i];

				Submit submit = new Submit();
				CopyUtil.INPUT_SUBMIT_COPIER.copy(input, submit, null);
				submit.setId(null);
				submit.setEnterprise_Msg_Id(null);
				submit.setChannel_No(new NullChannel().getNo());
				submit.setContent_Length(fragment.length());
				submit.setContent(fragment);
				submit.setSequence(i + 1);
				//input_log的create_date
				submit.setInput_Log_Date(input.getCreate_Date());
				submit.setPhone_No(sortPhoneNo.getMobile());
				submit = SMSUtil.buildSubmitAreaAndOperator(submit);
				submit.setSubmit_Date(new Date());
				submit.setSubmit_Status_Code(sortPhoneNo.getSubmitStatus().toString());
				submit.setSubmit_Description(StringUtils.defaultIfBlank(sortPhoneNo.getMsg(), "0"));
				submit.setInput_Sub_Code(input.getSub_Code());
				submit.setChannel_Msg_Id(CodeUtil.buildMsgNo());
				submit.setSubmit_Date(new Date());
				submit.setCreate_Date(new Date());
				submit.setSignature(signature);
				if (msgIds != null && i < msgIds.length) {
					submit.setEnterprise_Msg_Id(msgIds[i]);
				}

				// 状态报告
				Report report = SMSUtil.buildReport(submit, sortPhoneNo.getMobile());
				// report.setSP_Number(spNumber);
				report.setNative_Status(sortPhoneNo.getReportNativeStatus().toString());
				report.setStatus_Code(sortPhoneNo.getReportStatus().toString());
				report.setStatus_Date(new Date());
				//input_log的create_date
				report.setInput_Log_Date(input.getCreate_Date());
				QueueUtil.saveSubmit(Arrays.asList(submit));
				QueueUtil.saveReport(report);
			}
		});
	}


	public int getContentSplitCount() {
		return contentSplitCount;
	}

	public List<FaildPhoneNo> getFaildList() {
		return faildList;
	}

	public void addFaild(String mobile, ReportNativeStatus reportNativeStatus, String faildMsg) {
		inputLog.setFaild_Count((inputLog.getFaild_Count()==null?0:inputLog.getFaild_Count()) + 1);
		this.faildList.add(new FaildPhoneNo(mobile, reportNativeStatus, faildMsg, SMSUtil.NULL_ENTERPRISE_USER_FEE
				));
	}

	/**
	 * 
	 * @param mobile
	 * @return
	 * @author volcano
	 * @date 2019年10月10日上午5:03:59
	 * @version V1.0
	 */
	public boolean exsitsFaild(String mobile) {
		return this.faildList.stream().anyMatch(item -> item.getMobile().equals(mobile));
	}

	public void addFaild(List<String> mobiles, ReportNativeStatus reportNativeStatus, String faildMsg) {
		mobiles.forEach(no -> {
			addFaild(no, reportNativeStatus, faildMsg);
		});
	}

	public InputLog getInputLog() {
		return inputLog;
	}

	public String getContainsSensitives() {
		return containsSensitives;
	}

	public void setContainsSensitives(String containsSensitives) {
		this.containsSensitives = containsSensitives;
	}

	public Input getInput() {
		return input;
	}

	public void setInput(Input input) {
		this.input = input;
	}

	public Enterprise getEnterprise() {
		return enterprise;
	}

	public void setEnterprise(Enterprise enterprise) {
		this.enterprise = enterprise;
	}

	public SortChannelMap getChannelMap() {
		return channelMap;
	}

	public void setChannelMap(SortChannelMap channelMap) {
		this.channelMap = channelMap;
	}

	public EnterpriseUser getEnterpriseUser() {
		return enterpriseUser;
	}

	public void setEnterpriseUser(EnterpriseUser enterpriseUser) {
		this.enterpriseUser = enterpriseUser;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public SortLimitRepeat getSortLimitRepeat() {
		return sortLimitRepeat;
	}

	public void setSortLimitRepeat(SortLimitRepeat sortLimitRepeat) {
		this.sortLimitRepeat = sortLimitRepeat;
	}

	public InterceptStrategy getInterceptStrategy() {
		return interceptStrategy;
	}

	public void setInterceptStrategy(InterceptStrategy interceptStrategy) {
		this.interceptStrategy = interceptStrategy;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public boolean isBlackSwitch() {
		return isBlackSwitch;
	}

	public void setBlackSwitch(boolean blackSwitch) {
		isBlackSwitch = blackSwitch;
	}

	public ConcurrentHashMap<String, MobileArea> getMobileAreas() {
		return mobileAreas;
	}

	public void setMobileAreas(ConcurrentHashMap<String, MobileArea> mobileAreas) {
		this.mobileAreas = mobileAreas;
	}

	public ConcurrentHashMap<String, SmsRoute> getMobileRoutes() {
		return mobileRoutes;
	}

	public void setMobileRoutes(ConcurrentHashMap<String, SmsRoute> mobileRoutes) {
		this.mobileRoutes = mobileRoutes;
	}
}
