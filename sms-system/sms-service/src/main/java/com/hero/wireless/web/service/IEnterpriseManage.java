package com.hero.wireless.web.service;

import com.hero.wireless.web.entity.business.*;
import com.hero.wireless.web.entity.business.ext.*;
import com.hero.wireless.web.entity.ext.SqlStatisticsEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 企业信息管理
 * 
 * @author Administrator
 *
 */
public interface IEnterpriseManage {
	/**
	 * 查询企业列表
	 * 
	 * @param condition
	 * @return
	 */
	List<Enterprise> queryEnterpriseList(EnterpriseExt condition);

	/**
	 * 增加企业
	 * 
	 * @param data
	 * @return
	 */
	Enterprise addEnterprise(Enterprise data);
	
	/**
	 * 修改企业
	 * 
	 * @param enterprise
	 */
	int updateByPrimaryKeySelective(Enterprise enterprise);

	/**
	 * 根据Id查看企业信息
	 * 
	 * @param id
	 * @return
	 */
	Enterprise queryEnterpriseById(int id);
	
	/**
	 * 修改企业
	 * 	和updateByPrimaryKeySelective 这个方法是不是可以合并
	 * 
	 * @param enterpriseExt
	 * @return
	 */
	Integer editEnterprise(EnterpriseExt enterpriseExt) throws Exception;
	

	/**
	 * 原锁定企业方法
	 * 修改企业状态
	 * 
	 * @param enterpriseIdList
	 * @return
	 */
	List<Integer> updateEnterpriseStatus(List<Integer> enterpriseIdList, String status);

	/**
	 * 查询企业用户
	 * 
	 * @return
	 */
	List<EnterpriseUser> queryEnterpriseUserList(EnterpriseUserExt condition);

	/**
	 * 增加企业用户
	 * 
	 * @param userInfo
	 * @return
	 */
	EnterpriseUser addEnterpriseUser(EnterpriseUserExt userInfo);


	
	/**
	 * 修改企业用户状态
	 * 
	 * @param enterpriseUserIdList
	 * @return
	 */
	Integer updateEnterpriseUserStatus(List<Integer> enterpriseUserIdList, String status);


	/**
	 * 通过企业用户id查询企业用户
	 * 
	 * @param id
	 * @return
	 */
	EnterpriseUser queryEnterpriseUserById(Integer id);

	/**
	 * 修改企业用户
	 * 
	 * @param enterpriseUser
	 * @return
	 */
	Integer editUser(EnterpriseUserExt enterpriseUser);
	
	/**
	 * 
	 * @param userInfo
	 * @return
	 */
	EnterpriseUser editPassword(EnterpriseUser userInfo, String password);

	/**
	 * 查询企业权限
	 * 
	 * @param condition
	 * @return
	 */
	List<EnterpriseLimit> queryEnterpriseLimitList(EnterpriseLimit condition);
	
	/**
	 * 增加企业权限
	 * 
	 * @param data
	 * @return
	 */
	EnterpriseLimit addEnterpriseLimit(EnterpriseLimit data);
	
	/**
	 * 查询企业角色
	 * 
	 * @param condition
	 * @return
	 */
	List<EnterpriseRole> queryEnterpriseRoleList(EnterpriseRole condition);
	
	/**
	 * 保存企业角色
	 * 
	 * @param data
	 * @return
	 */
	EnterpriseRole addEnterpriseRole(EnterpriseRole data);
	
	/**
	 * 分配用户角色列表
	 * 
	 * @param userIdList
	 * @return
	 */
	List<EnterpriseRoleExt> queryBindUserRoleList(List<Integer> userIdList);
	
	/**
	 * 分配角色权限列表
	 * 
	 * @param roleIdList
	 * @return
	 */
	List<EnterpriseLimitExt> queryBindRoleLimitList(List<Integer> roleIdList);
	
	/**
	 * 绑定用户角色
	 * 
	 * @param userRoleList
	 * @return
	 */
	boolean bindUserRole(List<EnterpriseUserRoles> userRoleList);
	
	/**
	 * 绑定角色权限
	 * 
	 * @param roleLimitList
	 * @return
	 */
	boolean bindRoleLimit(List<EnterpriseRoleLimit> roleLimitList);

	/**
	 * 修改企业权限
	 * 
	 * @param enterpriseLimit
	 * @return
	 */
	Integer editLimit(EnterpriseLimit enterpriseLimit);

	/**
	 * 修改企业角色
	 * 
	 * @param enterpriseRole
	 * @return
	 */
	Integer editRole(EnterpriseRole enterpriseRole);

	/**
	 * 企业用户登录
	 * 
	 * @param userInfo
	 * @return
	 */
	EnterpriseUser userLogin(EnterpriseUser userInfo);

	/**
	 * 企业用户登录
	 * 
	 * @param userName
	 * @param md5Password
	 * @return
	 */
	EnterpriseUser userLogin(String userName, String md5Password);

	/**
	 * 
	 * 授权
	 * @param enterpriseExt
	 * @return
	 * @throws Exception
	 */
	Integer authenticate(EnterpriseExt enterpriseExt) throws Exception;

	/**
	 * 查询企业用户资费
	 * 
	 * @param enterpriseUserFee
	 * @return
	 * @author volcano
	 * @date 2019年9月14日上午3:32:48
	 * @version V1.0
	 */
	List<EnterpriseUserFee> queryEnterpriseUserFeeList(EnterpriseUserFee enterpriseUserFee);
	/**
	 * 添加用户资费
	 * 
	 * @param enterpriseUserFee
	 */
	void addEnterpriseUserFee(EnterpriseUserFee enterpriseUserFee);
	/**
	 * 编辑用户资费
	 * 
	 * @param enterpriseUserFee
	 */
	void editEnterpriseUserFee(EnterpriseUserFee enterpriseUserFee);
	/**
	 * 删除用户资费
	 * 
	 * @param ckIds
	 */
	void delEnterpriseUserFee(List<Integer> ckIds, EnterpriseUserExt userExt);
	
	/**
	 * 短信模板
	 * 
	 * @param smsTemplateExt
	 * @return
	 */
	List<SmsTemplate> querySmsTemplateList(SmsTemplateExt smsTemplateExt);

	/**
	 * 添加短信模板
	 * 
	 * @param smsTemplate
	 */
	void addSmsTemplate(SmsTemplate smsTemplate);

	/**
	 * 删除短信模板
	 * 
	 * @param ckIds
	 */
	void deleteSmsTemplate(List<Integer> ckIds, EnterpriseUser enterpriseUser);

	/**
	 * 导入短信模板
	 * 
	 * @param smsTemplate
	 * @param smsTemplateFile
	 * @throws Exception
	 */
	void importSmsTemplate(SmsTemplate smsTemplate, MultipartFile smsTemplateFile) throws Exception;

	/**
	 * 修改短信模板
	 * 
	 * @param smsTemplate
	 */
	void editSmsTemplate(SmsTemplate smsTemplate);

	/**
	 * 查询短信模板
	 * 
	 * @param integer
	 * @return
	 */
	SmsTemplate querySmsTemplateById(Integer integer);

	/**
	 * 修改短信模板
	 * 
	 * @param smsTemplateExt
	 */
	void updateSmsTemplateExt(SmsTemplateExt smsTemplateExt);

	/**
	 * 
	 * 通过企业编号查询企业信息
	 * 
	 * @param enterprise_no
	 * @return
	 */
	Enterprise queryEnterpriseByNo(String enterprise_no);

	/**
	 * 获取企业用户资费
	 * @param enterpriseUserFee
	 * @return
	 */
	@Deprecated
	List<EnterpriseUserFee> getEnterpriseUserFeeListAll(EnterpriseUserFee enterpriseUserFee);

	/**
	 * 
	 * 删除企业用户资费
	 * @param enterpriseUserFee
	 */
	void delEnterpriseUserFee(EnterpriseUserFee enterpriseUserFee);

	/**
	 * 添加企业用户资费
	 * 
	 * @param list
	 */
	void addEnterpriseUserFeeList(List<EnterpriseUserFee> list);

	/**
	 * 表头统计
	 * 
	 * @param enterpriseExt
	 * @return
	 */
    SqlStatisticsEntity queryEnterpriseListTotalData(EnterpriseExt enterpriseExt);

    /**
     * 此方法废弃使用
     * 	请使用queryEnterpriseUserList 代替删除所选权限以及下级权限
     * 
     * @param agentNo
     * @return
     */
    @Deprecated
    List<EnterpriseUser> getEnterpriseUserByAgentNo(String agentNo);

	/**
	 * 删除企业权限
	 * 			删除所选权限以及下级权限
	 * @param ckIds
	 * @return
	 */
	int deleteEnterpriseLimit(List<Integer> ckIds);

    /**
     * 导出企业
     * @param enterpriseExt
     * @param exportFile
     */
    void exportEnterpriseList( EnterpriseExt enterpriseExt, ExportFileExt exportFile);

    /**
     * 导出企业用户
     * @param enterpriseUserExt
     * @param exportFile
     */
    void exportEnterpriseUserList(EnterpriseUserExt enterpriseUserExt, ExportFileExt exportFile);


    /**
     * 客户平台添加用户时 初始化用户属性、产品、资费等。
     * @param oldUser
     * @param newUser
     */
    void initUserProperties(EnterpriseUser oldUser, EnterpriseUserExt newUser);

	/**
	 * 获取当天新增的企业数据
	 * @return
	 */
	List<Enterprise> queryAddEnterpriseCount();

}
