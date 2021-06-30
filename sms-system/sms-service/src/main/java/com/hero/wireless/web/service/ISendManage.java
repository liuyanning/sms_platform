package com.hero.wireless.web.service;

import com.hero.wireless.web.entity.base.Pagination;
import com.hero.wireless.web.entity.business.Contact;
import com.hero.wireless.web.entity.business.ContactGroup;
import com.hero.wireless.web.entity.business.EnterpriseUser;
import com.hero.wireless.web.entity.business.ExportFile;
import com.hero.wireless.web.entity.business.ext.ContactExt;
import com.hero.wireless.web.entity.business.ext.ExportFileExt;
import com.hero.wireless.web.entity.business.ext.SmsTemplateExt;
import com.hero.wireless.web.entity.ext.SqlStatisticsEntity;
import com.hero.wireless.web.entity.send.*;
import com.hero.wireless.web.entity.send.ext.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * 发送记录管理
 * 
 * @author Administrator
 * 
 */
public interface ISendManage {

	/**
	 * 批量提交短信
	 * 
	 * @param data
	 * @return
	 */
	int batchInputSms(Input data) throws Exception;

	/**
	 * 批量提交短信（文件）
	 *
	 * @param data
	 * @return
	 */
	int batchInputSms(InputExt data) throws Exception;

	/**
	 * 格式化短信发送
	 * 
	 * @param file
	 * @param data
	 * @return
	 */
	int formatSmsSend(MultipartFile file, InputExt data) throws Exception;

	/**
	 * 待分拣短信
	 * 
	 * @param condition
	 * @return
	 */
	List<InputExt> queryInputList(InputExt condition);

	/**
	 * 审核短信
	 * 		审核通过，审核拒绝
	 * 
	 *  如果是批量短信审核通过会发送mq分拣消息，删除input表中的记录<br>
	 *  如果是审核通过，并且是定时短信，批量审核通过修改审核记录为通过状态<br>
	 *  如果是批量拒绝，多线程执行：发送mq消息保存到submit表中，状态为拒绝，<br>
	 *  回执给下游，发送mq消息通知保存到inputlog表中，删除input表中的记录，
	 * 
	 */
	void approveSMS(String status,List<InputExt> inputExtList);

	/**
	 * 发件箱
	 * 
	 * @param condition
	 * @return
	 */
	List<InputLogExt> queryInputLogList(InputLogExt condition);

	/**
	 * 发件箱分表
	 *
	 * @param condition
	 * @return
	 */
	List<InputLogExt> queryInputLogListSharding(InputLogExt condition);

	/**
	 * 增加分组
	 * 
	 * @return
	 */
	ContactGroup addContactGroup(ContactGroup contactGroup);

	/**
	 * 编辑分组
	 * 
	 * @return
	 */
	Integer editContactGroup(ContactGroup contactGroup);

	/**
	 * 删除分组
	 * 
	 * @param contactGroup
	 * @return
	 */
	Integer deleteContactGroup(ContactGroup contactGroup);

	/**
	 * 分组是否存在
	 * 
	 * @param contactGroup
	 * @return
	 */
	Boolean exsitsContactGroup(ContactGroup contactGroup);

	/**
	 * 查询分组
	 * 
	 * @param condition
	 * @return
	 */
	List<ContactGroup> queryContactGroupList(ContactGroup condition);

	/**
	 * 查询组
	 * 
	 * @param id
	 * @return
	 */
	ContactGroup queryContactGroupById(Integer id);

	/**
	 * 增加联系人
	 * 
	 * @return
	 */
	Contact addContact(Contact contact);

	/**
	 * 编辑联系人
	 * 
	 * @return
	 */
	Integer editContact(Contact contact);

	/**
	 * 删除联系人
	 * 
	 * @param contact
	 * @return
	 */
	Integer deleteContact(Contact contact);

	/**
	 * 查询联系人
	 * 
	 * @param condition
	 * @return
	 */
	List<ContactExt> queryContactList(ContactExt condition);

	/**
	 * 主键查询联系人
	 *
	 * @param id
	 * @return
	 */
	ContactExt queryContactById(Integer id);

	/**
	 * 文件导入联系人
	 * 
	 * @param file
	 * @param contactExt
	 * @return 导入结果
	 */
    Map<String,Object>  importContact(MultipartFile file, ContactExt contactExt);

	/**
	 * 删除通讯录分组
	 *
	 * @param idList
	 * @param enterpriseUser
	 * @return
	 */
	Integer deleteContactGroupByIds(List<Integer> idList, EnterpriseUser enterpriseUser);

	/**
	 * 删除联系人
	 *
	 * @param idList
	 * @param enterpriseUser
	 * @return
	 */
	Integer deleteContactByIds(List<Integer> idList, EnterpriseUser enterpriseUser);

	/**
	 * 查询上行短信
	 * 
	 * @param inbox
	 * @return
	 */
	List<Inbox> queryInboxList(InboxExt inbox);

	/**
	 * 自动回复列表
	 *
	 * @param autoReplySms
	 * @return List<AutoReplySms>
	 * @exception @since 1.0.0
	 */
	List<AutoReplySms> queryAutoReplySmsList(AutoReplySms autoReplySms);

	/**
	 * 主键查询自动回复
	 * @param condition
	 * @return AutoReplySms
	 * @exception @since 1.0.0
	 */
	AutoReplySms queryAutoReplySmsByIdSelective(AutoReplySms condition);

	/**
	 * 添加自动回复
	 * @param autoReplySms
	 * @return AutoReplySms
	 * @exception @since 1.0.0
	 */
	AutoReplySms addAutoReplySms(AutoReplySms autoReplySms);

	/**
	 * 删除自动回复
	 * @param idList
	 * @param enterpriseUser
	 * @return Integer
	 * @exception @since 1.0.0
	 */
	Integer deleteAutoReplySmsByIds(List<Integer> idList, EnterpriseUser enterpriseUser);

	/**
	 * 编辑自动回复
	 * @param autoReplySms
	 * @return Integer
	 * @exception @since 1.0.0
	 */
	Integer editAutoReplySms(AutoReplySms autoReplySms);

	/**
	 * 导出发件箱
	 *
	 * @param baseExportBaseUrl
	 * @param inputLogExt       void
	 * @param ExportFile        TODO
	 * @exception @since 1.0.0
	 */
	void exportInputLog(String baseExportBaseUrl, InputLogExt inputLogExt, ExportFileExt ExportFile);

	/**
	 * 下载文件列表
	 *
	 * @param condition
	 * @exception @since 1.0.0
	 */
	List<ExportFile> queryExportFile(ExportFile condition);


	/**
	 * 更新input
	 * 
	 * @param input
	 * @return
	 */
	Integer editInput(Input input);

	/**
	 * 查询MO匹配的submit记录
	 * 
	 * @return
	 */
	List<Submit> queryMOSubmitList(SubmitExt condition);

	/**
	 * 分表查询发送记录
	 * @param condition
	 * @return
	 */
	List<SubmitExt> querySubmitListSharding(SubmitExt condition);

	/**
	 * 查询report记录
	 * 
	 * @return
	 */
	List<ReportExt> queryReportExtList(ReportExt reportExt);

	/**
	 * 查询report记录,分表
	 *
	 * @return
	 */
	List<ReportExt> queryReportListSharding(ReportExt condition);

	/**
	 * 重推回执
	 *
	 * @param reportExt
	 * @return
	 */
	void repushSmsReport(ReportExt reportExt) throws Exception;

	/**
	 * 取消分拣
	 *
	 * @param ckIds
	 * @return
	 */
	void deleteInputById(List<Long> ckIds, EnterpriseUser enterpriseUser);

	/**
	 * 发件箱数据（导出）
	 *
	 * @param inputLogExt
	 * @return
	 */
	List<Map<String,Object>> queryInputLogListForExportPage(InputLogExt inputLogExt);

	/**
	 * 发送记录数据（导出）
	 *
	 * @param submitExt
	 * @return
	 */
	List<Map<String,Object>> querySubmitListForExportPage(SubmitExt submitExt);

	/**
	 * 导出发送记录
	 *
	 * @param realPath
	 * @param submitExt
	 * @param exportFile
	 * @param comeFlag
	 * @return
	 */
	void exportSubmit(String realPath, SubmitExt submitExt, ExportFileExt exportFile, String comeFlag);

	/**
	 * 回执数据（导出）
	 *
	 * @param reportExt
	 * @return
	 */
	List<Map<String,Object>> queryReportListForExportPage(ReportExt reportExt);

	/**
	 * 导出回执
	 *
	 * @param realPath
	 * @param reportExt
	 * @param exportFile
	 * @param comeFlag
	 * @return
	 */
	void exportReport(String realPath, ReportExt reportExt, ExportFileExt exportFile,String comeFlag);

	/**
	 * 导出发件箱
	 *
	 * @param realPath
	 * @param inputExt
	 * @param exportFile
	 * @return
	 */
	void exportInput(String realPath, InputExt inputExt, ExportFileExt exportFile);

	/**
	 * 发件箱数据（导出）
	 *
	 * @param inputExt
	 * @return
	 */
	List<Map<String,Object>> queryInputListForExportPage(InputExt inputExt);

	/**
	 * 导出联系人
	 *
	 * @param realPath
	 * @param contactExt
	 * @param exportFile
	 * @return
	 */
	void exportContactList(String realPath, ContactExt contactExt, ExportFileExt exportFile);

	/**
	 * 下载文件
	 *
	 * @param response
	 * @param exportFile
	 * @return
	 */
	void downloadFile(HttpServletResponse response,ExportFile exportFile) throws Exception;

	/**
	 * 收件箱批量补推
	 *
	 * @param ckIds
	 * @return
	 */
	void repushSmsMo(List<Integer> ckIds);

	/**
	 * 批量重发发件箱
	 *
	 * @param inputLogExt
	 * @param subCode
	 * @param enterpriseNo
	 * @param enterpriseUserId
	 * @return
	 */
	void resendInputLog(InputLogExt inputLogExt, String subCode,String enterpriseNo,Integer enterpriseUserId);

	/**
	 * 发送记录重发短信
	 *
	 * @param submitExt
	 * @param subCode
	 * @param enterpriseNo
	 * @param enterpriseUserId
	 * @return
	 */
	void resendSms(SubmitExt submitExt, String subCode, String enterpriseNo, Integer enterpriseUserId);

	/**
	 * 发送短信（内部调用）
	 *
	 * @param mobiles
	 * @param msg
	 * @return
	 */
	void sendSMS(String mobiles, String msg,String countryCode);

	/**
	 * 主键查询收件箱
	 *
	 * @param integer
	 * @return
	 */
	Inbox queryInboxByPrimaryKey(Integer integer);

	/**
	 * 审核短信列表
	 *
	 * @param inputExt
	 * @return
	 */
	List<InputExt> queryAuditingInputList(InputExt inputExt);

	/**
	 * 短信审核通过
	 *
	 * @param inputExt
	 * @return
	 */
	Integer editAgreeInput(InputExt inputExt);

	/**
	 * 发件箱数据统计
	 *
	 * @param inputLogExt
	 * @return
	 */
    SqlStatisticsEntity queryInputLogListTotalData(InputLogExt inputLogExt);

	/**
	 * 发送记录数据统计
	 *
	 * @param submitExt
	 * @return
	 */
    SqlStatisticsEntity querySubmitListTotalData(SubmitExt submitExt);

	/**
	 * 回执数据统计
	 *
	 * @param reportExt
	 * @return
	 */
    SqlStatisticsEntity queryReportListTotalData(ReportExt reportExt);

	/**
	 * 插入发件箱
	 *
	 * @param inputs
	 * @return
	 * @author volcano
	 * @date 2019年10月24日下午6:16:03
	 * @version V1.0
	 */
	int insertInputs(List<Input> inputs);

	/**
	 * 定时短信列表
	 *
	 * @param inputExt
	 * @return
	 */
	List<Input> queryTimerInputList(InputExt inputExt);

    /**
     * 未知记录列表
     * @param submitExt
     * @return
     */
    List<SubmitExt> querySubmitUnknownList(SubmitExt submitExt);

    /**
     * 未知记录导出
     * @param submitExt
     * @return
     */
    void exportSubmitReportUnknownList(String value, SubmitExt submitExt, ExportFileExt adminDefaultExportFile, String comeFlag);

	/**
	 * 导出短信模板
	 *
	 * @param realPath
	 * @param smsTemplateExt
	 * @param exportFile
	 */
	void exportSmsTemplate(String realPath, SmsTemplateExt smsTemplateExt, ExportFileExt exportFile);

	/**
	 * 短信模板数据（导出）
	 *
	 * @param smsTemplateExt
	 * @return
	 */
	List<Map<String,Object>>  querySmsTemplateListForExportPage(SmsTemplateExt smsTemplateExt);

	/**
	 * Excel导入联系人
	 *
	 * @param moblieFile
	 * @param contactExt
	 * @return
	 */
    Map<String ,Object> importExcelContact(MultipartFile moblieFile, ContactExt contactExt) throws Exception;

	/**
	 * 发送短信上传号码文件
	 *
	 * @param request
	 * @return
	 */
    Map<String, String> uploadSendFile(HttpServletRequest request,Integer userId)  throws Exception;

	/**
	 * 通过Submit获取对应的Report
	 *
	 * @param submit
	 * @return
	 */
    List<Report> queryReportListBySubmit(SubmitExt submit);
	/**
	 * 通过主键获取Submit（代理商企业端加限制）
	 *
	 * @param submitExt
	 * @return
	 */
	List<Submit> querySubmitByPrimaryKey(SubmitExt submitExt);
	/**
	 * 发送记录统计
	 *
	 * @param submitedExt
	 * @return
	 */
    SqlStatisticsEntity querySubmitedListTotalData(SubmitExt submitedExt);


	int queryAuditingInputCount(InputExt inputExt);

    /**
     * 用户管理=》发送测试(立即发送)
     * @param userId
     * @param phoneNos
     * @param content
     */
    void testSend(Integer userId, String phoneNos, String content) throws Exception;

    /**
     * 用户管理=》发送测试(文件发送)
     * @param userId
     * @param file
     */
    void testSendFile(Integer userId, MultipartFile file) throws Exception;

    /**
     * 导出收件箱
     * @param path
     * @param inboxExt
     * @param exportFile
     * @param flag
     */
    void exportInboxList(String path, InboxExt inboxExt, ExportFileExt exportFile,String flag);

	/**
	 * 按照分表查询推送记录
	 * @param condition
	 * @return
	 */
	List<ReportNotify> queryReportNotifyListSharding(ReportNotifyExt condition);

	List<SubmitExt> querysendSpeed(SubmitExt submitedExt);

    /**
     * 短信校验功能（手机app上传短信）
     * @param data
     */
    void smsVerify(List<Map<String,Object>> data);

	/**
	 * 根据回执报告查询提交条数和发送成功条数
	 * @param reportExt
	 * @return
	 */
	List<ReportExt> querySendCountDetailFromReport(ReportExt reportExt);

	/**
	 * 根据提交时间查询提交总数和提交成功条数
	 * @param submitExt
	 * @return
	 */
	List<SubmitExt> querySendCountDetailFromSubmit(SubmitExt submitExt);

    /**
     * 预警记录查看详情
     */
    String queryAlarmLogDetailList(String bingValveType, String bindValue, String beginDate, String endDate, String alarmType, Pagination pagination);

	/**
	 * 手工回复MO
	 * @param reportExt
	 * @param reportIds
	 * @param msgContent
	 */
    void manualSendMO(ReportExt reportExt, List<Long> reportIds, String msgContent);

	/**
	 * 查询推送的MO
	 * @param userId
	 * @param pageSize
	 * @return
	 */
	List<Inbox> queryNotifyInboxList(Integer userId, Integer pageSize);

	/**
	 * 查询待发送列表
	 * @param submitExt
	 * @return
	 */
	List<SubmitAwait> querySubmitAwaitList(SubmitAwaitExt submitExt);

	/**
	 * 查询待推送列表
	 * @param reportNotifyAwaitExt
	 * @return
	 */
	List<ReportNotifyAwait> queryReportNotifyAwaitList(ReportNotifyAwaitExt reportNotifyAwaitExt);
}
