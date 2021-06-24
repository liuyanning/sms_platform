package com.hero.wireless.web.config;

import com.drondea.wireless.util.SuperLogger;
import com.hero.wireless.enums.AccountStatus;
import com.hero.wireless.enums.ChannelStatus;
import com.hero.wireless.enums.ProductChannelDiversionType;
import com.hero.wireless.enums.PropertiesType;
import com.hero.wireless.web.entity.base.Pagination;
import com.hero.wireless.web.entity.business.*;
import com.hero.wireless.web.entity.business.ext.*;
import com.hero.wireless.web.service.*;
import com.hero.wireless.web.util.*;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * 数据库缓存
 *
 * @author Administrator
 */

@Component("databaseCache")
public class DatabaseCache {

    private static DatabaseCache databaseCache;
    private static List<CodeSort> codeSortList = new ArrayList<>();
    private static List<CodeExt> codeList = new ArrayList<>();
    private static String systemType;
    /**
     * 本地二级缓存
     */
    private final static LocalCache<Channel> CHANNEL_LOCAL_CACHE = new LocalCache<>(1 * 60, 500);
    private final static LocalCache<Enterprise> ENTERPRISE_LOCAL_CACHE = new LocalCache<>(1 * 60, 1000);
    private final static LocalCache<EnterpriseUser> ENTERPRISE_USER_LOCAL_CACHE = new LocalCache<>(1 * 60, 1000);
    private final static LocalCache<EnterpriseUser> TCP_ENTERPRISE_USER_LOCAL_CACHE = new LocalCache<>(1 * 60, 500);
    private final static LocalCache<EnterpriseUserFee> ENTERPRISE_USER_FEE_LOCAL_CACHE = new LocalCache<>(1 * 60, 1000);
    private final static LocalCache<List<Pattern>> SMS_TEMPLATE_LOCAL_CACHE = new LocalCache<>(1 * 60, 5000);
    private final static LocalCache<List<ProductChannels>> PRODUCT_CHANNELS_LOCAL_CACHE = new LocalCache<>(1 * 60, 500);
    private final static LocalCache<Product> PRODUCT_LOCAL_CACHE = new LocalCache<>(1 * 60, 500);
    private final static LocalCache<InterceptStrategy> INTERCEPT_STRATEGY_LOCAL_CACHE = new LocalCache<>(1 * 60, 500);
    private final static LocalCache<Map<String, String>> PROPERTIES_LOCAL_CACHE = new LocalCache<>(1 * 60, 1000);

    private final static LocalCache<ChannelFee> CHANNEL_FEE_LOCAL_CACHE = new LocalCache<>(3 * 60, 500);
    /**
     * 手机号归属地缓存一天，最多缓存100万数据
     */
    private final static LocalCache<MobileArea> MOBILE_AREA_LOCAL_CACHE = new LocalCache<>(24 * 60 * 60, 1000000);

    /**
     * 号码路由本地缓存
     */
    public static Map<String, SmsRoute> SMS_ROUTE_MAP = new HashMap<>();
    /**
     * 白名单本地缓存
     */
    public static Map<String, Boolean> SMS_WHITE_MAP = new HashMap<>();
    /**
     * 号码归属地本地缓存
     */
    public static Map<String, MobileArea> MOBILE_AREA_MAP = new HashMap<>();
    /**
     * 黑名单本地缓存
     */
    public static ConcurrentHashMap<String, List<String>> SMS_BLACK_MAP = new ConcurrentHashMap<>();
    /**
     * 导流本地缓存，KEY格式: Product_Channels_Id+Strategy_Type_Code
     */
    public static Map<String, List<ProductChannelsDiversion>> DIVERSION_MAP = new HashMap<>();
    /**
     * 敏感字比较多,直接内部缓存
     */
    public static Map<String, List<SensitiveWord>> SENSITIVE_MAP = new HashMap<>();

    @Resource(name = "businessManage")
    private IBusinessManage businessManage;
    @Resource(name = "enterpriseManage")
    private IEnterpriseManage enterpriseManage;
    @Resource(name = "platformManage")
    private IPlatformManage platformManage;
    @Resource(name = "netwayManage")
    private INetwayManage netwayManage;
    @Resource(name = "userManage")
    private IUserManage userManage;
    @Resource(name = "materialManage")
    private IMaterialManage materialManage;
    @Resource(name = "productChannelManage")
    private IProductChannelManage productChannelManage;
    @Resource(name = "propertyManage")
    private IPropertyManage propertyManage;


    public static String getSystemType() {
        return systemType;
    }

    public static void setSystemType(String systemType) {
        DatabaseCache.systemType = systemType;
    }

    /**
     * 根据代码分类获取代码
     *
     * @return
     */
    public static List<? extends Code> getCodeListBySortCode(String sortCode) {
        return codeList.stream().filter(item -> item.getSort_Code().equalsIgnoreCase(sortCode)).collect(
                Collectors.toList());
    }

    /**
     * 根据分类和上级代码
     *
     * @param sortCode
     * @param upCode
     * @return
     * @author volcano
     * @date 2019年9月15日上午10:34:29
     * @version V1.0
     */
    public static List<? extends Code> getCodeListBySortCodeAndUpCode(String sortCode, String upCode) {
        return codeList.stream().filter(
                item -> item.getSort_Code().equalsIgnoreCase(sortCode) && item.getUp_Code().equalsIgnoreCase(upCode))
                .collect(Collectors.toList());
    }

    /**
     * 根据代码分类和代码获取
     *
     * @return
     */
    public static Code getCodeBySortCodeAndCode(String sortCode, String code) {
        return codeList.stream().filter(
                item -> item.getSort_Code().equalsIgnoreCase(sortCode) && item.getCode().equalsIgnoreCase(code)).findFirst()
                .orElse(null);
    }

    /**
     * 根据代码分类和代码获取名称
     *
     * @return
     */
    public static String getCodeNameBySortCodeAndCodeNotNull(String sortCode, String code) {
        Code bean = getCodeBySortCodeAndCode(sortCode, code);
        return ObjectUtils.isEmpty(bean) ? "---" : bean.getName();
    }

    /**
     * @param sortCode
     * @param name
     * @return
     */
    public static Code getCodeBySortCodeAndName(String sortCode, String name) {
        return codeList.stream().filter(item -> item.getSort_Code().equals(sortCode) && item.getName().equals(name))
                .findFirst().orElse(null);
    }

    /**
     * @param enterpriseNo
     * @param userName
     * @return
     * @author volcano
     * @date 2019年9月19日上午9:18:08
     * @version V1.0
     */
    public static EnterpriseUser getEnterpriseUserByNoAndUserName(String enterpriseNo, String userName) {
        EnterpriseUserExt condition = new EnterpriseUserExt();
        condition.setEnterprise_No(enterpriseNo);
        condition.setUser_Name(userName);
        List<EnterpriseUser> list = databaseCache.enterpriseManage.queryEnterpriseUserList(condition);
        return ObjectUtils.isEmpty(list) ? null : list.get(0);
    }



    /**
     * @param condition
     * @return
     * @author volcano
     * @date 2019年11月16日下午7:37:57
     * @version V1.0
     */
    public static List<EnterpriseUser> getEnterpriseUserList(EnterpriseUserExt condition) {
        return databaseCache.enterpriseManage.queryEnterpriseUserList(condition);
    }

    public static String getAlarmSmsTemplate(String templateType) {
        Code code = getCodeBySortCodeAndCode("alarm_sms_template", templateType);
        return ObjectUtils.isEmpty(code) ? null : code.getValue();
    }

    public static List<AdminUser> getAdminUserList(AdminUser condition) {
        return databaseCache.userManage.queryAdminUserList(condition);
    }

    public static int getIntValueBySortCodeAndCode(String sortCode, String code, int defaultValue) {
        Code codeObject = getCodeBySortCodeAndCode(sortCode, code);
        if (ObjectUtils.isEmpty(codeObject)) {
            return defaultValue;
        }
        return NumberUtils.toInt(codeObject.getValue(), defaultValue);
    }

    public static String getStringValueBySortCodeAndCode(String sortCode, String code, String defaultValue) {
        Code codeObject = getCodeBySortCodeAndCode(sortCode, code);
        if (ObjectUtils.isEmpty(codeObject)) {
            return defaultValue;
        }
        return codeObject.getValue();
    }

    public static Code getSystemEnvByCode(String code) {
        return getCodeBySortCodeAndCode("system_env", code);
    }

    public static String getStringValueBySystemEnvAndCode(String code, String defaultValue) {
        return getStringValueBySortCodeAndCode("system_env", code, defaultValue);
    }


    public static List<MmsMaterial> getMmsMaterialList() {
        return databaseCache.materialManage.queryMmsMaterialList(new MmsMaterial());
    }

    public static List<? extends Enterprise> getEnterpriseList(EnterpriseExt enterpriseExt) {
        return databaseCache.enterpriseManage.queryEnterpriseList(enterpriseExt);
    }

    public static List<Platform> getPlatformList(Platform platform) {
        return databaseCache.platformManage.queryPlatformList(platform);
    }

    /**
     * 获取本地缓存的企业信息
     * @param enterpriseNo
     * @return
     */
    public static Enterprise getEnterpriseCachedByNo(String enterpriseNo) {
        return ENTERPRISE_LOCAL_CACHE.get(enterpriseNo, () -> getEnterpriseByNo(enterpriseNo));
    }

    public static Enterprise getEnterpriseByNo(String enterpriseNo) {
        if (StringUtils.isEmpty(enterpriseNo)) {
            return null;
        }
        EnterpriseExt condition = new EnterpriseExt();
        condition.setNo(enterpriseNo);
        List<Enterprise> list = databaseCache.enterpriseManage.queryEnterpriseList(condition);
        return ObjectUtils.isEmpty(list) ? null : list.get(0);
    }

    /**
     * 本地缓存的企业用户
     * @param id
     * @return
     */
    public static EnterpriseUser getEnterpriseUserCachedById(Integer id) {
        return ENTERPRISE_USER_LOCAL_CACHE.get(String.valueOf(id), () -> getEnterpriseUserById(id));
    }

    public static EnterpriseUser getEnterpriseUserCachedByIdNotLoad(Integer id) {
        return ENTERPRISE_USER_LOCAL_CACHE.get(String.valueOf(id));
    }

    public static void putEnterpriseUserCachedById(Integer id, EnterpriseUser user) {
        ENTERPRISE_USER_LOCAL_CACHE.putIfAbsent(String.valueOf(id), user);
    }


    public static EnterpriseUser getEnterpriseUserById(Integer id) {
        if (id == null) {
            return null;
        }
        EnterpriseUserExt condition = new EnterpriseUserExt();
        condition.setId(id);
        List<EnterpriseUser> list = databaseCache.enterpriseManage.queryEnterpriseUserList(condition);
        return ObjectUtils.isEmpty(list) ? null : list.get(0);
    }

    public static List<String> getEnterpriseByCantainsName(String name) {
        EnterpriseExt condition = new EnterpriseExt();
        condition.setName(name);
        List<Enterprise> list = databaseCache.enterpriseManage.queryEnterpriseList(condition);
        List<String> listNo = new ArrayList<String>();
        list.forEach((e) -> {
            listNo.add(e.getNo());
        });
        return listNo;
    }

    // 根据条件获取通道
    public static List<Channel> getChannelList(Channel channel) {
        return databaseCache.netwayManage.queryChannelList(channel);
    }

    // 获取所有通道
    public static List<Channel> getAllChannelList() {
        return getChannelList(new Channel());
    }

    // 获取所有开启的通道
    public static List<Channel> getAllStartedChannelList() {
        Channel channel = new Channel();
        channel.setStatus_Code(ChannelStatus.START.toString());
        return getChannelList(channel);
    }

    public static Channel getChannelByNo(String channelNo) {
        if (StringUtils.isEmpty(channelNo)) {
            return null;
        }
        Channel channel = new Channel();
        channel.setNo(channelNo);
        List<Channel> channelList = getChannelList(channel);
        return ObjectUtils.isEmpty(channelList) ? null : channelList.get(0);
    }

    /**
     * 根据channelNo，获取本地缓存的通道
     * @param channelNo
     * @return
     */
    public static Channel getChannelCachedByNo(String channelNo) {
        //没有拿到通道编号直接返回null
        if (SMSUtil.DEFAULT_NO.equals(channelNo)) {
            return null;
        }
        return CHANNEL_LOCAL_CACHE.get(channelNo,() -> getChannelByNo(channelNo));
    }

    public static MobileArea getMobileAreaByNumber(String mobileNumber, String countryCode, boolean cacheMode) {
        if ("86".equals(countryCode)) {
            return getChinaMobileAreaByNumber(mobileNumber, cacheMode);
        }

        if(!"86".equals(countryCode)){
            mobileNumber = countryCode + mobileNumber;
        }
        for (int i = mobileNumber.length(); i > 1; i--) {
            MobileArea mobileArea = getMobileAreaByNumberAndAreaCode(mobileNumber.substring(0, i), countryCode);
            if (ObjectUtils.isNotEmpty(mobileArea)) {
                return mobileArea;
            }
        }
        return SMSUtil.NULL_MOBILE_AREA;
    }

    /**
     * 获取手机号码的归属地
     * @param mobileNumber
     * @param cacheMode 是否使用本地缓存
     * @return
     */
    public static MobileArea getChinaMobileAreaByNumber(String mobileNumber, boolean cacheMode) {
        if (StringUtils.isEmpty(mobileNumber)) {
            SuperLogger.warn("空号码无地域归属!");
            return SMSUtil.NULL_MOBILE_AREA;
        }
        if (mobileNumber.length() < 7) {
            SuperLogger.warn(mobileNumber + "无地域归属!");
            return SMSUtil.NULL_MOBILE_AREA;
        }
        String key = getMobileAreaKey(mobileNumber.substring(0, 7), "86");
        MobileArea mobileArea;
        //本地二级缓存模式
        if (cacheMode) {
            mobileArea = MOBILE_AREA_MAP.get(key);
        } else {
            mobileArea = getMobileAreaByNumberAndAreaCode(mobileNumber.substring(0, 7), "86");
        }

        if (ObjectUtils.isNotEmpty(mobileArea)) {
            return mobileArea;
        }
        return SMSUtil.NULL_MOBILE_AREA;
    }


    /**
     * 加载号码路由本地缓存
     */
    public static void refreshSmsRouteLocalCache() {
        new Thread(() -> {
            SuperLogger.info("开始加载号码路由================");
            int page = 1;
            int pageSize = 10000;
            while(true){
                List<SmsRouteExt> smsRouteExts = databaseCache.businessManage.querySmsRouteListLocalCache(page, pageSize);
                if (ObjectUtils.isEmpty(smsRouteExts)) {
                    break;
                }
                smsRouteExts.forEach(entity -> {
                    String prefix_number = entity.getPrefix_Number();
                    String country_code = entity.getCountry_Code();
                    String key = DatabaseCache.getMobileRouteKey(prefix_number, country_code);
                    SMS_ROUTE_MAP.put(key, entity);
                });
                page = page + 1;
            }
            SuperLogger.info("号码路由加载了：" + SMS_ROUTE_MAP.size() + "条");
        }).start();
    }

    /**
     * 加载黑名单本地缓存
     */
    public static void refreshBlackListLocalCache() {
        new Thread(() -> {
            SuperLogger.info("开始加载黑名单================");
            int pageIndex = 1;
            int pageSize = 10000;
            while(true){
                List<BlackList> blackLists = databaseCache.businessManage.queryBlackListLocalCache(pageIndex, pageSize);
                if (ObjectUtils.isEmpty(blackLists)) {
                    break;
                }
                blackLists.forEach(entity ->
                    BlackListUtil.addBlackList(entity.getPhone_No(), entity.getPool_Code(), entity.getEnterprise_No(), entity.getEnterprise_User_Id())
                );
                pageIndex = pageIndex + 1;
            }
            SuperLogger.info("黑名单加载了：" + SMS_BLACK_MAP.size() + "条");
        }).start();
    }

    /**
     * 加载白名单本地缓存
     */
    public static void refreshWhiteListLocalCache() {
        new Thread(() -> {
            SuperLogger.info("开始加载白名单================");
            Pagination pagination = new Pagination(1, 10000);
            while(true) {
                WhiteListExample whiteListExample = new WhiteListExample();
                whiteListExample.setPagination(pagination);
                List<WhiteList> whiteLists = databaseCache.businessManage.queryWhiteList(whiteListExample);
                if (ObjectUtils.isEmpty(whiteLists)) {
                    break;
                }
                whiteLists.forEach(entity -> {
                    String key = WhiteListUtil.getWhiteListKey(entity.getPhone_No(), entity.getPool_Code(), entity.getEnterprise_No());
                    SMS_WHITE_MAP.put(key, true);
                });
                pagination = new Pagination(pagination.getPageIndex() + 1, 10000);
            }
            SuperLogger.info("白名单加载了：" + SMS_WHITE_MAP.size() + "条");
        }).start();
    }

    public static void refreshMobileAreaLocalCache() {
        new Thread(() -> {
            SuperLogger.info("开始加载号码归属地================");
            Pagination pagination = new Pagination(1, 10000);
            while(true) {
                MobileAreaExample mobileAreaExample = new MobileAreaExample();
                mobileAreaExample.setPagination(pagination);
                List<MobileArea> mobileAreaLists = databaseCache.businessManage.queryMobileAreaList(mobileAreaExample);
                if (ObjectUtils.isEmpty(mobileAreaLists)) {
                    break;
                }
                mobileAreaLists.forEach(entity -> {
                    String key = getMobileAreaKey(entity.getMobile_Number(), "86");
                    MOBILE_AREA_MAP.put(key, entity);
                });
                pagination = new Pagination(pagination.getPageIndex() + 1, 10000);
            }
            SuperLogger.info("号码归属地加载了：" + MOBILE_AREA_MAP.size() + "条");
        }).start();
    }

    /**
     * 单独更新本地路由缓存
     * @param smsRoute
     */
    public static void updateSmsRouteLocalCache(SmsRoute smsRoute) {
        String prefix_number = smsRoute.getPrefix_Number();
        String country_code = smsRoute.getCountry_Code();
        String key = DatabaseCache.getMobileRouteKey(prefix_number, country_code);
        SMS_ROUTE_MAP.put(key, smsRoute);
    }

    /**
     * 从缓存里面获取，提高性能
     * @param mobileNumber
     * @param countryCode
     * @return
     */
    public static SmsRoute getSmsRouteCachedByNumber(String mobileNumber, String countryCode) {
        // 携号转网需要整个手机号判断
        for (int i = mobileNumber.length(); i > 1; i--) {
            String prefixNumber = mobileNumber.substring(0, i);
            String cachedKey = DatabaseCache.getMobileRouteKey(prefixNumber, countryCode);
            //从缓存取
            SmsRoute smsRoute = SMS_ROUTE_MAP.get(cachedKey);
            if (ObjectUtils.isNotEmpty(smsRoute)) {
                return smsRoute;
            }
        }
        return null;
    }


    public static SmsRoute getSmsRouteByNumber(String mobileNumber, String countryCode) {
        // 携号转网需要整个手机号判断
        for (int i = mobileNumber.length(); i > 1; i--) {
            String prefixNumber = mobileNumber.substring(0, i);
            SmsRoute smsRoute = getSmsRouteByPrefixNumberAndCountryCode(prefixNumber, countryCode);
            if (ObjectUtils.isNotEmpty(smsRoute)) {
                return smsRoute;
            }
        }
        return null;
    }


    /**
     *
     * @Title: getProperties
     * @Description: 获取属性表数据
     * @author yjb
     * @param properties
     * @return
     * @throws
     * @date 2020-05-08
     */
    public static Properties getProperty(Properties properties) {
        List<Properties> list =  databaseCache.businessManage.queryPropertiesList(properties);
        return ObjectUtils.isEmpty(list) ? null : list.get(0);
    }

    public static boolean isUserBlackSwitch(String userId) {
        Map<String, String> cachedUserProperties = getCachedUserProperties(userId);
        String blackSwitch = cachedUserProperties.get(PropertiesType.Property_Name.BLACKLIST_SWITCH.toString());
        return "true".equals(blackSwitch);
    }

    public static List<Properties> getProperties(Properties properties) {
        return databaseCache.businessManage.queryPropertiesList(properties);
    }

    /**
     * 获取用户的相关属性
     * @param userId
     * @return
     */
    public static Map<String, String> getUserProperties(String userId) {
        Properties properties = new Properties();
        properties.setType_Code(PropertiesType.ENTERPRISE_USER.toString());
        properties.setType_Code_Num(userId);

        List<Properties> list =  databaseCache.businessManage.queryPropertiesList(properties);
        if (list == null) {
            return new HashMap<>();
        }
        return list.stream().collect(Collectors.toMap(Properties::getProperty_Name, Properties::getProperty_Value, (k1, k2) -> k1));
    }

    /**
     * 缓存用户属性
     * @param userId
     * @return
     */
    public static Map<String, String> getCachedUserProperties(String userId) {
        return PROPERTIES_LOCAL_CACHE.get(userId, () -> getUserProperties(userId));
    }

    /**
     * @param mobileNumber
     * @return
     * @author volcano
     * @date 2019年9月14日下午12:38:56
     * @version V1.1
     */
    public static boolean hashCacheBlackListByNumber(String mobileNumber) {
        BlackListExt condition = new BlackListExt();
        condition.setPhone_No(mobileNumber);
        List<BlackListExt> blackListExts = databaseCache.businessManage.queryBlackList(condition);
        return ObjectUtils.isNotEmpty(blackListExts);
    }

    /**
     * 从本地缓存里面判断是否是白名单
     * @param mobileNumber
     * @param enterpriseNo
     * @param poolCode
     * @return
     */
    public static boolean hashWhiteListByNumber(String mobileNumber, String enterpriseNo, String poolCode) {
        String key = WhiteListUtil.getWhiteListKey(mobileNumber, null, null);
        //全局白名单
        if (SMS_WHITE_MAP.containsKey(key)) {
            return true;
        }

        key = WhiteListUtil.getWhiteListKey(mobileNumber, null, enterpriseNo);
        //企业全局白名单
        if (SMS_WHITE_MAP.containsKey(key)) {
            return true;
        }

        //未设置分类
        if (StringUtils.isEmpty(poolCode)) {
            return false;
        }

        key = WhiteListUtil.getWhiteListKey(mobileNumber, poolCode, null);
        //白名单池全局白名单
        if (SMS_WHITE_MAP.containsKey(key)) {
            return true;
        }

        key = WhiteListUtil.getWhiteListKey(mobileNumber, poolCode, enterpriseNo);
        //白名单池的企业白名单
        if (SMS_WHITE_MAP.containsKey(key)) {
            return true;
        }
        return false;
    }

    public static String genRedisKey(String dirName, String data) {
        return dirName + ":" + data;
    }

    public static String getMobileAreaKey(String mobilePrefix, String countryCode) {
        return countryCode + ":" + mobilePrefix;
    }

    public static String getMobileRouteKey(String mobileNumber, String countryCode) {
        return countryCode + ":" + mobileNumber;
    }

    public static ChannelFee getChannelCachedFee(String channelNo, String messageType, String operator) {
        String key = channelNo + messageType + operator;
        return CHANNEL_FEE_LOCAL_CACHE.get(key,() -> {
            ChannelFee channelFee = getChannelFee(channelNo, messageType, operator);
            if (channelFee != null) {
                return channelFee;
            }
            return SMSUtil.NULL_CHANNEL_FEE;
        });
    }

    /**
     * @param channelNo
     * @param operator
     * @author volcano
     * @date 2019年9月13日上午7:45:06
     * @version V1.0
     */
    public static ChannelFee getChannelFee(String channelNo, String messageType, String operator) {
        ChannelFee channelFee = new ChannelFee();
        channelFee.setChannel_No(channelNo);
        channelFee.setOperator(operator);
        channelFee.setMessage_Type_Code(messageType);
        List<ChannelFee> list = databaseCache.netwayManage.queryChannelFeeList(channelFee);
        // TODO 如果list有两个，说明数据库有问题
        return ObjectUtils.isEmpty(list) ? null : list.get(0);
    }

    /**
     * 本地缓存的企业用户设置的短信费用
     * @param enterpriseNo
     * @param userId
     * @param messageType
     * @param operator
     * @return
     */
    public static EnterpriseUserFee getEnterpriseUserFeeCached(String enterpriseNo, int userId, String messageType,
                                                               String operator) {
        String key = enterpriseNo + "_" + userId + "_" + messageType + "_" + operator;
        return ENTERPRISE_USER_FEE_LOCAL_CACHE.get(key, () -> {
            EnterpriseUserFee enterpriseUserFee = getEnterpriseUserFee(enterpriseNo, userId, messageType, operator);
            if (enterpriseUserFee != null) {
                return enterpriseUserFee;
            }
            return SMSUtil.NULL_ENTERPRISE_USER_FEE;
        });
    }

    public static EnterpriseUserFee getEnterpriseUserFee(String enterpriseNo, int userId, String messageType,
                                                         String operator) {
        EnterpriseUserFee userFee = new EnterpriseUserFee();
        userFee.setEnterprise_No(enterpriseNo);
        userFee.setEnterprise_User_Id(userId);
        userFee.setMessage_Type_Code(messageType);
        userFee.setOperator(operator);
        List<EnterpriseUserFee> list = databaseCache.enterpriseManage.queryEnterpriseUserFeeList(userFee);
        // TODO 如果list有两个，说明数据有问题
        return ObjectUtils.isEmpty(list) ? null : list.get(0);
    }

    /**
     * @param messageType
     * @param operator
     * @return
     * @author volcano
     * @date 2019年9月14日上午11:43:48
     * @version V1.0
     */
    public static List<ProductChannels> getProductChannels(String messageType, String operator) {
        ProductChannels productChannels = new ProductChannels();
        productChannels.setOperator(operator);
        productChannels.setMessage_Type_Code(messageType);
        return databaseCache.netwayManage.queryProductChannelsList(productChannels);
    }

    /**
     * @param messageType
     * @param operator
     * @param channelNo
     * @return
     */
    public static List<ProductChannels> getProductChannels(String messageType, String operator, String channelNo) {
        ProductChannels productChannels = new ProductChannels();
        productChannels.setOperator(operator);
        productChannels.setMessage_Type_Code(messageType);
        productChannels.setChannel_No(channelNo);
        return databaseCache.netwayManage.queryProductChannelsList(productChannels);
    }

    /**
     * 从本地缓存获取通道
     * @param productNo
     * @return
     */
    public static List<ProductChannels> getProductCachedChannels(String productNo) {
        return PRODUCT_CHANNELS_LOCAL_CACHE.get(productNo, () -> getProductChannels(productNo));
    }

    /*
     *
     */
    public static List<ProductChannels> getProductChannels(String productNo) {
        ProductChannels productChannels = new ProductChannels();
        productChannels.setProduct_No(productNo);
        return databaseCache.netwayManage.queryProductChannelsList(productChannels);
    }

    /**
     * @param channelNo
     * @return
     */
    public static List<ProductChannels> getProductChannelsByChannelNo(String channelNo) {
        ProductChannels productChannels = new ProductChannels();
        productChannels.setChannel_No(channelNo);
        return databaseCache.netwayManage.queryProductChannelsList(productChannels);
    }

    public static ProductChannels getProductChannelsById(Integer id) {
        ProductChannels productChannels = new ProductChannels();
        productChannels.setId(id);
        List<ProductChannels> list = databaseCache.netwayManage.queryProductChannelsList(productChannels);
        return list.size() > 0 ? list.get(0) : null;
    }

    /**
     *
     * @param productChannelId
     * @return
     */
    public static List<ProductChannelsDiversion> queryProductChannelsDiversionListByProductChannelsId(Integer productChannelId) {
        ProductChannelsDiversion entity = new ProductChannelsDiversion();
        entity.setProduct_Channels_Id(productChannelId);
        return databaseCache.productChannelManage.queryProductChannelsDiversionList(entity);
    }

    /**
     * 本地缓存获取产品信息
     * @param no
     * @return
     */
    public static Product getProductCachedByNo(String no) {
        return PRODUCT_LOCAL_CACHE.get(no, () -> getProductByNo(no));
    }

    /**
     * @param no
     * @return
     * @author volcano
     * @date 2019年9月14日上午11:50:16
     * @version V1.0
     */
    public static Product getProductByNo(String no) {
        Product product = new Product();
        product.setNo(no);
        List<Product> list = databaseCache.netwayManage.queryProductList(product);
        // TODO 如果list有两个，说明数据有问题
        return ObjectUtils.isEmpty(list) ? null : list.get(0);
    }

    public static List<Product> getProduct() {
        Product product = new Product();
        return databaseCache.netwayManage.queryProductList(product);
    }

    public static List<Product> getProduct(Product product) {
        if (product == null) {
            product = new Product();
        }
        return databaseCache.netwayManage.queryProductList(product);
    }

    /**
     * 本地缓存短信模板提升效率
     * @param enterpriseNo
     * @param userId
     * @param auditStatus
     * @return
     */
    public static List<Pattern> getSmsTemplateListCached(String enterpriseNo, int userId, String auditStatus) {
        String key = enterpriseNo + "_" + userId + "_" + auditStatus;
        return SMS_TEMPLATE_LOCAL_CACHE.get(key, () -> {
            List<? extends SmsTemplate> userTemplates = getSmsTemplateList(enterpriseNo, userId, auditStatus);
            //缓存为正则表达式对象，提高执行效率
            return userTemplates.stream().map((smsTemplate) ->
                    SMSUtil.getTemplatePattern(smsTemplate.getTemplate_Content())).collect(Collectors.toList());
        });
    }

    /**
     * 查询短信模板
     *
     * @param enterpriseNo
     * @param userId
     * @param auditStatus
     * @return
     * @author volcano
     * @date 2019年9月17日上午6:17:23
     * @version V1.0
     */
    public static List<? extends SmsTemplate> getSmsTemplateList(String enterpriseNo, int userId, String auditStatus) {
        SmsTemplateExt smsTemplate = new SmsTemplateExt();
        smsTemplate.setEnterprise_No(enterpriseNo);
        smsTemplate.setEnterprise_User_Id(userId);
        smsTemplate.setApprove_Status(auditStatus);
        return databaseCache.enterpriseManage.querySmsTemplateList(smsTemplate);
    }

    //查询彩信模板
    public static List<? extends MmsTemplate> getMmsTemplateList(String enterpriseNo, int userId, String auditStatus) {
        MmsTemplate smsMultimediaTemplate = new MmsTemplate();
        smsMultimediaTemplate.setEnterprise_No(enterpriseNo);
        smsMultimediaTemplate.setEnterprise_User_Id(userId);
        smsMultimediaTemplate.setApprove_Status(auditStatus);
        return databaseCache.materialManage.queryMmsTemplateList(smsMultimediaTemplate);
    }

    /**
     * 本地缓存获取拦截策略
     * @param id
     * @return
     */
    public static InterceptStrategy getInterceptStrategyCachedById(Integer id) {
        return INTERCEPT_STRATEGY_LOCAL_CACHE.get(String.valueOf(id), () -> getInterceptStrategyById(id));
    }

    /**
     * @param id
     * @return
     * @author volcano
     * @date 2019年10月4日上午7:28:49
     * @version V1.0
     */
    public static InterceptStrategy getInterceptStrategyById(Integer id) {
        if (id == null) {
            return null;
        }
        InterceptStrategy interceptStrategy = new InterceptStrategy();
        interceptStrategy.setId(id);
        List<InterceptStrategy> list = getInterceptStrategyList(interceptStrategy);
        return ObjectUtils.isEmpty(list) ? null : list.get(0);
    }

    // 获取所有拦截策略
    public static List<InterceptStrategy> getAllInterceptStrategy() {
        return getInterceptStrategyList(new InterceptStrategy());
    }

    // 根据条件获取拦截策略
    public static List<InterceptStrategy> getInterceptStrategyList(InterceptStrategy interceptStrategy) {
        return databaseCache.businessManage.queryInterceptStrategyList(interceptStrategy);
    }

    public static List<AdminUser> getAdminUsersByRoleCode(String roleCode) {
        return databaseCache.getUserManage().getAdminUsersByRoleCode(roleCode);
    }

    /**
     * 字典翻译 extInfo专用，命名只能 ByNo
     */
    public static CodeSort getCodeSortByNo(String code) {
        return codeSortList.stream().filter(codeSort -> codeSort.getCode().equalsIgnoreCase(code)).findFirst().orElse(
                null);
		/*if (StringUtils.isEmpty(code)) {
			return null;
		}
		CodeSort codeSort = new CodeSort();
		codeSort.setCode(code.toLowerCase());
		List<CodeSort> codeSorts = databaseCache.businessManage.queryCodeSortList(codeSort);
		return ObjectUtils.isEmpty(codeSorts) ? null : codeSorts.get(0);*/
    }

    /**
     * 字典翻译 extInfo专用，命名只能 ByNo
     */
    public static Code getCodeByNo(String code) {// by code
        if (StringUtils.isEmpty(code)) {
            return null;
        }
        return codeList.stream().filter(codeExt -> codeExt.getCode().equalsIgnoreCase(code)).findFirst().orElse(null);
		/*CodeExt newCode = new CodeExt();
		newCode.setCode(code.toLowerCase());
		List<CodeExt> codes = databaseCache.businessManage.queryCodeList(newCode);
		return ObjectUtils.isEmpty(codes) ? null : codes.get(0);*/
    }

    /**
     * 字典翻译 extInfo专用，过滤敏感信息 只返回 name
     */
    public static AdminUser getAdminUserTranslateById(Integer id) {
        if (id == null) {
            return null;
        }
        AdminUser adminUser = new AdminUser();
        adminUser.setId(id);
        List<AdminUser> list = databaseCache.getUserManage().queryAdminUserList(adminUser);
        adminUser.setReal_Name(ObjectUtils.isEmpty(list) ? "---" : list.get(0).getReal_Name());
        adminUser.setUser_Name(ObjectUtils.isEmpty(list) ? "---" : list.get(0).getUser_Name());
        return adminUser;
    }

    /**
     * 字典翻译 extInfo专用，过滤敏感信息 只返回 name
     */
    public static Enterprise getEnterpriseTranslateByNo(String enterpriseNo) {
        Enterprise result = new Enterprise();
        Enterprise bean = getEnterpriseByNo(enterpriseNo);
        result.setName(bean == null ? "---" : bean.getName());
        return result;
    }


    /**
     * 字典翻译 extInfo专用，过滤敏感信息 只返回 name
     */
    public static EnterpriseUser getEnterpriseUserTranslateById(Integer id) {
        EnterpriseUser result = new EnterpriseUser();
        EnterpriseUser bean = getEnterpriseUserCachedById(id);
        result.setReal_Name(bean == null ? "---" : bean.getReal_Name());
        result.setUser_Name(bean == null ? "---" : bean.getUser_Name());
        return result;
    }

    /**
     * 字典翻译 extInfo专用，过滤敏感信息 只返回 name/状态/通道来源
     */
    public static Channel getChannelTranslateByNo(String no) {
        Channel result = new Channel();
        Channel bean = getChannelByNo(no);
        result.setName(bean == null ? "---" : bean.getName());
        result.setStatus_Code(bean == null ? "---" : bean.getStatus_Code());
        result.setChannel_Source(bean == null ? "---" : bean.getChannel_Source());
        return result;
    }

    /**
     * 字典翻译 extInfo专用，过滤敏感信息 只返回 name
     */
    public static Product getProductTranslateByNo(String no) {
        Product result = new Product();
        Product bean = getProductByNo(no);
        result.setName(bean == null ? "---" : bean.getName());
        return result;
    }


    public static EnterpriseUser getUserByCachedTcpUserName(String userName) {
        return TCP_ENTERPRISE_USER_LOCAL_CACHE.get(userName, () -> getUserByTcpUserName(userName));
    }

    /**
     * 根据CMPP用户名获取企业用户
     *
     * @param userName
     * @return
     */
    public static EnterpriseUser getUserByTcpUserName(String userName) {
        EnterpriseUserExt condition = new EnterpriseUserExt();
        condition.setTcp_User_Name(userName);
        condition.setStatus(AccountStatus.NORMAL.toString());
        List<EnterpriseUser> list = databaseCache.enterpriseManage.queryEnterpriseUserList(condition);
        if (ObjectUtils.isEmpty(list)) {
            return null;
        }
        return list.get(0);
    }

    public static List<CodeExt> getCodeList() {
        return codeList;
    }

    public static void setCodeList(List<CodeExt> codeList) {
        DatabaseCache.codeList = codeList;
    }

    public static List<CodeSort> getCodeSortList() {
        return codeSortList;
    }

    public static void setCodeSortList(List<CodeSort> codeSortList) {
        DatabaseCache.codeSortList = codeSortList;
    }

    public static DatabaseCache getInstance() {
        return databaseCache;
    }

    public static List<SmsRouteExt> queryOperatorListByCountry(String countryNumber) {
        return databaseCache.businessManage.queryOperatorListByCountry(countryNumber);
    }

    @PostConstruct
    public void init() {
        SuperLogger.debug("加载系统缓存");
        databaseCache = this;
        codeSortList = businessManage.queryCodeSortList(new CodeSort());
        codeList = businessManage.queryCodeList(new CodeExt());
    }

    public IBusinessManage getBusinessManage() {
        return businessManage;
    }

    public void setBusinessManage(IBusinessManage businessManage) {
        this.businessManage = businessManage;
    }

    public IEnterpriseManage getEnterpriseManage() {
        return enterpriseManage;
    }

    public void setEnterpriseManage(IEnterpriseManage enterpriseManage) {
        this.enterpriseManage = enterpriseManage;
    }

    public IUserManage getUserManage() {
        return userManage;
    }

    public void setUserManage(IUserManage userManage) {
        this.userManage = userManage;
    }

    /**
     * 根据条件查询属性列表
     *
     * @param typeCode
     * @param typeCodeNum
     * @param propertyName
     * @param extendedField
     * @return
     */
    public static List<Properties> queryPropertiesListByFields(String typeCode, String typeCodeNum, String propertyName, String extendedField) {
        Properties properties = new Properties();
        properties.setType_Code(typeCode);
        properties.setType_Code_Num(typeCodeNum);
        properties.setProperty_Name(propertyName);
        properties.setExtended_Field(extendedField);
        return databaseCache.propertyManage.queryPropertiesList(properties);
    }

    /**
     * 根据用户配置拼接上号码前缀
     * @param userId
     * @param phoneNo
     * @return
     */
    public static String getUserCountryCodePhoneNo(Integer userId, String phoneNo) {
        Map<String, String> userProperties = DatabaseCache.getCachedUserProperties(String.valueOf(userId));
        String propertyValue  = userProperties.get(PropertiesType.Property_Name.COUNTRY_CODE_VALUE.toString());
        if (StringUtils.isNotBlank(propertyValue)) {
            return propertyValue + phoneNo;
        }
        return phoneNo;
    }

    /**
     * 根据用户信息获取额外的sgip配置信息
     * @param userInfo
     * @return
     */
    public static EnterpriseUserExt getSgipUserExtInfo(EnterpriseUser userInfo) {

        //直接从redis拿，不从本地缓存拿
        Map<String, String> userProperties = DatabaseCache.getCachedUserProperties(String.valueOf(userInfo.getId()));
        EnterpriseUserExt enterpriseUserExt = new EnterpriseUserExt();
        CopyUtil.USER_USEREXT_COPIER.copy(userInfo, enterpriseUserExt, null);

        enterpriseUserExt.setSgipSpIp(userProperties.get("Sgip_Sp_Ip"));
        enterpriseUserExt.setSgipSpPort(userProperties.get("Sgip_Sp_Port"));

        String windowSize = StringUtils.defaultIfEmpty(userProperties.get("Window_Size"), "16");
        enterpriseUserExt.setWindowSize(windowSize);

        return enterpriseUserExt;
    }

    /**
     * 刷新导流策略
     */
    public static void refreshDiversionLocalCache() {
        List<ProductChannelsDiversion> all = databaseCache.productChannelManage.queryProductChannelsDiversionByCache(cri -> {
            // 不加载号码池
            cri.andStrategy_Type_CodeNotEqualTo(ProductChannelDiversionType.PHONE_NO_POLL.toString());
        });
        // 归类
        DIVERSION_MAP = all.stream().collect(Collectors
                .groupingBy(key -> diversionMapKey(key.getProduct_Channels_Id(), key.getStrategy_Type_Code())));
    }

    public static String diversionMapKey(int productChannelsId, String diversionType) {
        return productChannelsId + "," + diversionType;
    }
    /**
     * 根据 产品通道+导流类型 获取策略配置
     *
     * @param productChannels
     * @param diversionType
     * @return
     */
    public static List<ProductChannelsDiversion> getDiversions(ProductChannels productChannels,
                                                               ProductChannelDiversionType diversionType) {
        return DIVERSION_MAP.get(diversionMapKey(productChannels.getId(), diversionType.toString()));
    }

    private static MobileArea getMobileAreaByNumberAndAreaCode(String mobileNumber, String areaCode) {
        MobileArea condition = new MobileArea();
        condition.setMobile_Number(mobileNumber);
        condition.setArea_Code(areaCode);
        List<MobileArea> mobileAreas = databaseCache.businessManage.queryMobileAreaList(condition);
        return ObjectUtils.isNotEmpty(mobileAreas) ? mobileAreas.get(0) : null;
    }

    private static SmsRoute getSmsRouteByPrefixNumberAndCountryCode(String prefixNumber, String countryCode) {
        SmsRoute condition = new SmsRoute();
        condition.setPrefix_Number(prefixNumber);
        condition.setCountry_Code(countryCode);
        List<SmsRoute> smsRoutes = databaseCache.businessManage.querySmsRouteList(condition);
        return ObjectUtils.isNotEmpty(smsRoutes) ? smsRoutes.get(0) : null;
    }

    /**
     * 刷新敏感字缓存
     */
    public static void refreshSensitiveWordLocalCache() {
        new Thread(() -> {
            List<SensitiveWord> sensitives = databaseCache.businessManage.querySensitiveWordList(new SensitiveWordExt());
            SENSITIVE_MAP = sensitives.stream().collect(Collectors.groupingBy((item) -> {
                if (StringUtils.isEmpty(item.getPool_Code())) {
                    return "default";
                }
                return item.getPool_Code();
            }));
        }).start();
    }

    public static DFA getDFA(String poolCode) {
        if (StringUtils.isEmpty(poolCode)) {
            return null;
        }
        List<SensitiveWord> list = SENSITIVE_MAP.get(poolCode);
        if (ObjectUtils.isEmpty(list)) {
            return null;
        }
        List<String> dfaList = list.stream().map(SensitiveWord::getWord).collect(Collectors.toList());
        return new DFA(dfaList);
    }

}
