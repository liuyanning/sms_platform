package com.hero.wireless.web.service;

import com.hero.wireless.web.entity.business.*;
import com.hero.wireless.web.entity.business.ext.*;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * 业务管理
 * 
 * @author Administrator
 *
 */
public interface  IBusinessManage {

	/**
	 * 查询白名单
	 * 
	 * @param condition
	 * @return
	 */
	List<WhiteList> queryWhiteList(WhiteList condition, Map<String, String> parMap);

	/**
	 * 根据调解查询白名单
	 * @param condition
	 * @return
	 */
	List<WhiteList> queryWhiteList(WhiteListExample condition);

	/**
	 * 增加白名单
	 * 
	 * @param data
	 * @return
	 */
	WhiteList addWhiteList(WhiteList data);

	/**
	 * 删除白名单
	 * 
	 * @param ids
	 * @return
	 */
	int deleteWhiteList(List<Integer> ids);

	/**
	 * 查询黑名单
	 * 
	 * @param condition
	 * @return
	 */
	List<BlackListExt> queryBlackList(BlackListExt condition);

	/**
	 * 保存黑名单
	 * 
	 * @param data
	 * @return
	 */
	BlackList addBlackList(BlackList data);

	/**
	 * 删除黑名单
	 * 
	 * @param idList
	 * @return
	 */
	int deleteBlackByIdList(List<Integer> idList);

	/**
	 * 查询代码分类
	 * 
	 * @param condition
	 * @return
	 */
	List<CodeSort> queryCodeSortList(CodeSort condition);

	/**
	 * 保存代码分类
	 * 
	 * @param data
	 * @return
	 */
	CodeSort addCodeSort(CodeSort data);

	/**
	 * 编辑代码分类
	 * 
	 * @param data
	 * @return
	 */
	Integer editCodeSort(CodeSort data);

	/**
	 * 删除代码分类
	 * 
	 * @param idList
	 * @return
	 */
	Integer deleteCodeSort(List<Integer> idList);

	/**
	 * 查询代码
	 * 
	 * @param condition
	 * @return
	 */
	List<CodeExt> queryCodeList(CodeExt condition);

	/**
	 * 保存代码
	 * 
	 * @param data
	 * @return
	 */
	Code addCode(Code data);
	
	/**
	 * 修改Code
	 * 
	 * @param data
	 * @return
	 */
	Integer editCode(CodeExt data);
	
	/**
	 * 删除Code
	 * 
	 * @param idList
	 * @return
	 */
	Integer deleteCode(List<Integer> idList);

	/**
	 * 查询敏感字
	 * 
	 * @param condition
	 * @return
	 */
	List<SensitiveWord> querySensitiveWordList(SensitiveWordExt condition);

	/**
	 * 保存敏感字
	 * 
	 * @param data
	 * @return
	 */
	SensitiveWord addSensitiveWord(SensitiveWord data);

	/**
	 * 删除敏感字
	 * 
	 * @param idList
	 * @return
	 */
	Integer deleteSensitiveWord(List<Integer> idList);
	
	/**
	 * 增加短信路由
	 * 
	 * @param data
	 * @return SmsRoute
	 * @exception @since 1.0.0
	 */
	SmsRoute addSmsRoute(SmsRoute data);

    /**
     * 编辑路由
     * @param smsRoute
     */
    void editSmsRoute(SmsRoute smsRoute);

    /**
     * 批量删除路由
     * @param ids
     */
    void deleteSmsRoute(List<Integer> ids);

	/**
	 * 归属地
	 * 
	 * @param condition
	 * @return
	 */
	List<MobileArea> queryMobileAreaList(MobileArea condition);

	/**
	 * 增加归属地
	 * 
	 * @param data
	 * @return
	 */
	MobileArea addMobileArea(MobileArea data);

	/**
	 * 修改归属地
	 * 
	 * @param data
	 * @return
	 */
	Integer editMobileArea(MobileArea data);
	
	/**
	 * 查询短信路由
	 * 
	 * @return List<SmsRoute>
	 */
	List<SmsRoute> querySmsRouteList(SmsRoute condition);

	/**
	 * 初始化白名单
	 */
	void initWhiteListCache();

	/**
	 * 初始化黑名单
	 */
	void initBlackListCache();

	/**
	 * 初始化号码路由
	 */
	void initSmsRouteCache();

	/**
	 * 根据分页获取要缓存的路由数据
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	List<SmsRouteExt> querySmsRouteListLocalCache(int pageIndex, int pageSize);

	/**
	 * 导入黑名单
	 * 
	 * @param blackList
	 * @param blackListFile
	 */
	void importBlackList(BlackList blackList, MultipartFile blackListFile);

	/**
	 * 导入白名单
	 * 
	 * @param whiteList
	 * @param whiteListFile
	 * @throws Exception
	 */
	void importWhiteList(WhiteList whiteList, MultipartFile whiteListFile) throws Exception;

	/**
	 * 导入敏感字
	 * 
	 * @param sensitiveWord
	 * @param file
	 * @throws Exception
	 */
	void importSensitiveWord(SensitiveWord sensitiveWord, MultipartFile file) throws Exception;

	/**
	 * 敏感字列表
	 * 
	 * @param interceptStrategy
	 * @return
	 */
	@Cacheable(value = "intercept_strategy_list", condition = "#p0.pagination==null")
	List<InterceptStrategy> queryInterceptStrategyList(InterceptStrategy interceptStrategy);

	/**
	 * 添加敏感字
	 * 
	 * @param interceptStrategy
	 * @return
	 */
	@CacheEvict(value = "intercept_strategy_list", allEntries = true)
	int addInterceptStrategy(InterceptStrategy interceptStrategy);

	/**
	 * 删除敏感字
	 * 
	 * @param ckIds
	 * @return
	 */
	@CacheEvict(value = "intercept_strategy_list", allEntries = true)
	int deleteInterceptStrategy(List<Integer> ckIds);

	/**
	 * 修改敏感字
	 * @param interceptStrategy
	 * @return
	 */
	@CacheEvict(value = "intercept_strategy_list", allEntries = true)
	int editInterceptStrategy(InterceptStrategy interceptStrategy);

	/**
	 * 初始化手机归属地
	 */
	void initMobileAreaCache();

	/**
	 * 查询报警列表
	 * 
	 * @param alarmExt
	 * @return
	 */
    List<Alarm> queryAlarmList(AlarmExt alarmExt);
    
    /**
     * 修改报警
     * 
     * @param alarmExt
     * @return
     */
    int editAlarm(AlarmExt alarmExt);
   
    /**
     * 添加报警
     * 
     * @param alarmExt
     * @return
     */
    int addAlarm(AlarmExt alarmExt);
   
    /**
     * 删除报警
     * 
     * @param ckIds
     * @return
     */
    int deleteAlarm(List<Integer> ckIds);

    /**
     * 停止
     * @param uIdList
     * @return
     */
    Integer lockAlarm(List<Integer> uIdList);

    /**
     * 启动
     * @param uIdList
     * @return
     */
    Integer unLockAlarm(List<Integer> uIdList);

    /**
     * 修改报警信息
     * 
     * @param alarm
     */
	void updateByPrimaryKeySelective(Alarm alarm);

	/**
	 * 查询报警
	 * @param integer
	 * @return
	 */
    AlarmExt queryAlarmById(Integer integer);

    @Cacheable(value = "properties_list", condition = "#p0.pagination==null")
	List<Properties> queryPropertiesList(Properties properties);


    /**
     * 根据countryNumber获取运营商列表
     * @param countryNumber 中国：cn
     */
    List<SmsRouteExt> queryOperatorListByCountry(String countryNumber);

	/**
	 * 获取携号转网的手机号
	 * @param adminUser
	 * @param
	 * @return
	 */
	void getTransferPhoneNos(String startDate, String endDate, AdminUser adminUser);

    /**
     * 告警记录列表
     * @param alarmLog
     * @return
     */
    List<AlarmLog> queryAlarmLogList(AlarmLog alarmLog);

	List<BlackList> queryBlackListLocalCache(int pageIndex, int pageSize);

	List<MobileArea> queryMobileAreaList(MobileAreaExample mobileAreaExample);
}