package com.drondea.wireless.config;

public class Constant {

    /**\\||,|，|\n*/
    public static final String MUTL_MOBILE_SPLIT = "\\||,|，|\r\n|\n";
    /**企业状态:正常*/
    public static final String ENTERPRISE_INFO_STATUS_NORMAL = "Normal";
    /**企业状态:被锁*/
    public static final String ENTERPRISE_INFO_STATUS_LOCKED = "Locked";
    /**请求来源：Pull*/
    public static final String SOURCE_TYPE_CODE_QUERY_REPORT = "query_report";
    /**redis白名单目录名white_list*/
    public static final String REDIS_KEY_WHITE_DIR_NAME = "wh";
    /**redis黑名单目录名black_list*/
    public static final String REDIS_KEY_BLACK_DIR_NAME = "bl";
    /**redis黑名单目录名external_black_list*/
    public static final String REDIS_KEY_EXTERNAL_BLACK_DIR_NAME = "ebl";
    /**redis空号库phone_no_check_value_list*/
    public static final String REDIS_KEY_PHONE_NO_CHECK_VALUE = "pncl";
    /**report集合名称*/
    public static final String REDIS_KEY_AWAIT_PULL_REPORT_LIST_NAME = "await_pull_report_list";
    /**submited数据的id集合名称*/
    public static final String REDIS_KEY_SUBMITED_ID_LIST_NAME = "submited_id_list_name";
    /**inputLog数据的id集合名称*/
    public static final String REDIS_KEY_INPUT_LOG_ID_LIST_NAME = "input_log_id_list_name";
    /**
     * 下游在线用户信息集合名称
     */
    public static final String REDIS_ONLINE_USER_DIR_NAME = "online";

    /**新增线程目录名*/
    public static final String THREAD_TOTAL = "thread_total:";
    /**新增线程操作导入*/
    public static final String THREAD_TOTAL_IMPORT = "import";
    /**新增线程操作导出*/
    public static final String THREAD_TOTAL_EXPORT = "export";
    /**短信模板审核状态：待审*/
    public static final String SMS_TEMPLAT_CHECK_STATUS_PENDING = "0";
    /**短信模板审核状态：通过*/
    public static final String SMS_TEMPLAT_CHECK_STATUS_PASS = "1";
    /**短信模板审核状态：拒绝*/
    public static final String SMS_TEMPLAT_CHECK_STATUS_REFUSED = "2";
    /**企业认证状态：待审*/
    public static final String ENTERPRISE_CHECK_STATUS_PENDING = "00";
    /**企业认证状态：通过*/
    public static final String ENTERPRISE_CHECK_STATUS_PASS = "01";
    /**企业认证状态：拒绝*/
    public static final String ENTERPRISE_CHECK_STATUS_REFUSED = "02";
    /**默认角色code */
    public static final String DEFAULT_ROLE_CODE = "Default";
    /**新注册用户角色code */
    public static final String NEWUSER_ROLE_CODE = "NewUser";

    /**充值（审核/开通）同意状态 */
    public static final String RECHARGE_AGREE = "Agree";
    /**充值（审核/开通）拒绝状态 */
    public static final String RECHARGE_REFUSE = "Object";
    /**充值提交支付系统状态成功 */
    public static final String RECHARGE_SUBMIT_SUCCESS = "00";
    /**充值提交支付系统状态失败*/
    public static final String RECHARGE_SUBMIT_FAILED = "99";
    /**平台支付状态*/
    public static final String PAY_STATUS_WAIT = "01";
    public static final String PAY_STATUS_SUCCESS = "02";
    public static final String PAY_STATUS_FAILED = "03";
    /**状态：启用*/
    public static final String STATUS_CODE_START = "Start";
    /**状态：停用*/
    public static final String STATUS_CODE_STOP = "Stop";
    /**单次插入数据库最大条数*/
    public static final int INSERT_MAX_LENGTH = 8000;

    public static final String PLATFORM_FLAG_ADMIN = "admin";
    public static final String PLATFORM_FLAG_AGENT = "agent";
    public static final String PLATFORM_FLAG_ENTERPRISE = "enterprise";

    /**用户提交速度key*/
    public static final String USER_SUBMIT_SPEED_DIR_NAME = "userSubmitSpeed";
    /**用户回执速度key*/
    public static final String USER_REPORT_SPEED_DIR_NAME = "userReportSpeed";
    /**分拣速度**/
    public static final String SORT_SPEED_DIR_NAME = "sortSpeed";

    /**通道提交速度key*/
    public static final String CHANNEL_SUBMIT_SPEED_DIR_NAME = "channelSubmitSpeed";
    /**通道回执速度key*/
    public static final String CHANNEL_REPORT_SPEED_DIR_NAME = "channelReportSpeed";
    /**
     * 通道统计回执率key
     */
    public static final String CHANNEL_STATISTICS_DIR_NAME = "channelSta";

    /**网关发送速度峰值key*/
    public static final String GATE_SUBMIT_MAX_KEY = "gateSubmitSpeedMax";
    /**网关回执速度峰值key*/
    public static final String GATE_REPORT_MAX_KEY = "gateReportSpeedMax";
    /**发送器发送速度峰值key*/
    public static final String SENDER_SUBMIT_MAX_KEY = "senderSubmitSpeedMax";
    /**发送器回执速度峰值key*/
    public static final String SENDER_REPORT_MAX_KEY = "senderReportSpeedMax";
    /**
     * 分拣的最大速度key
     */
    public static final String SORTER_MAX_KEY = "sorterSpeedMax";
}
