package com.drondea.wireless.config;

/**
 * 自定义请求状态码
 * 
 * @author yjb
 */
public enum ResultStatus {
    
    SUCCESS("0", "成功"),
    ERROR("99", "失败,系统异常"),


    /**企业编号不能为空*/
    ENTERPRISENO_NOT_NULL("20", "企业编号不能为空"),
    /**企业不存在或者状态异常*/
    ENTERPRISE_STATUS_ERROR("21", "企业不存在或者状态异常"),

    /**余额不足*/
    NO_VAILABLE_SMS_TOTAL("10","余额不足"),
    /**账号或密码错误*/
    USER_PASSWORD_ERROR("11", "账号或密码错误"),
    /**账号锁定*/
    ACCOUT_LOCKED("12", "账号锁定"),
    
    /**短信内容不能为空*/
    CONTENT_NOT_NULL("30", "短信内容不能为空"),
    /**超出最大发送字数*/
    OUT_MAX_SHORT_MESSAGE_COUNT("31", "超出最大发送字数,最大发送字数为：{0}个"),
    /**超出最大发送字数*/
    MAX_BATCH_MOBILE_COUNT("32", "超出最大允许发送手机号个数,最多允许手机号为：{0}个"),
    /** 包含特殊字符*/
    CONTENT_CONTAINS_EMOJI("33", "包含特殊字符"),

    /**国家编码不存在*/
    COUNTRY_CODE_IS_NULL("34", "国家编码不存在"),

    /**拉取频率过于频繁!*/
    PULL_MORE_TIMES_ERROR("40", "拉取频率过于频繁!"),
    /**重推收件箱数据不能为空！*/
    REPUSH_SMS_MO__NOT_NULL("41", "重推收件箱数据不能为空！"),
    /**重推状态报告数据不能为空！*/
    REPUSH_SMS_REPORT__NOT_NULL("42", "重推状态报告数据不能为空！"),
    
    /**视频模板不存在*/
    VIDEO_TEMPLATE_NOT_EXSITS("50", "视频模板不存在"),
    /**彩信内容超出最大允许值*/
    MMS_CONTENT_BEYOND_THE_MAXIMUM("51", "彩信内容超出最大允许值"),
    /**参数为空或是数据格式错误*/
    DATA_NOT_NULL_OR_FORMAT_ERROR("52", "参数为空或是数据格式错误"),
    /**文字内容与模板不符*/
    CONTENT_DOES_NOT_MATCH_THE_TEMPLATE("53", "文字内容与模板不符"),
    /**彩信素材不存在*/
    MMS_MATERIAL_NOT_EXSITS("54", "彩信素材不存在"),
    
    /**后台管理预留6000~6999*/
    GROUP_NAME_EXSITS("6001", "组名称已存在！"),
    /**"手机号{0},已存在"*/
    CONTACT_MOBILE_EXSITS("6002", "手机号{0},已存在"),
    /**用户已存在*/
    USER_NAME_EXSITS("6003", "用户已存在"),
    /**主账户只能设置一个*/
    MASTER_ACCOUNT_EXSITS("6004", "主账户只能设置一个"),
    /**权限编码已存在*/
    LIMIT_CODE_EXSITS("6005", "权限编码已存在{0}"),
    /**角色编码已存在*/
    ROLE_CODE_EXSITS("6006", "角色编码已存在{0}"),
    /**号码前7位不能为空*/
    MOBILE_NUMBER__NOT_NULL("6007", "号码前7位不能为空"),
    /**号码区号不能为空*/
    AREA_CODE__NOT_NULL("6008", "号码区号不能为空"),
    /**号码归属地不能为空*/
    MOBILE_AREA__NOT_NULL("6009", "号码归属地不能为空"),
    /**区域号码已存在*/
    MOBILE_AREA_EXSITS("6010", "区域号码已存在"),
    /**后台正在导入黑名单，暂时请勿操作*/
    IMPORT_BLACK_ERROR("6011", "后台正在导入黑名单，暂时请勿再次操作"),
    /**未知条数返还比例必须是0-1之间的数字*/
    RETURN_UNKNOWN_RATE_ERROR("6012", "未知条数返还比例必须是0-1之间的数字"),
    /**充值账户不存在*/
    USER_NOT_FIND_ERROR("6013", "充值账户不存在"),
    /**企业用户资费重复*/
    ENTERPRISENO_USER_FEE_EXIST("6014","企业用户资费已存在"),
    /**代理资费重复*/
    AGENT_FEE_EXIST("6015","该代理资费已存在"),
    ;
    
    /**
     * 返回码
     */
    private String code;
    
    /**
     * 返回结果描述
     */
    private String message;
    
    ResultStatus(String code, String message) {
        this.code = code;
        this.message = message;
    }
    
    public String getCode() {
        return code;
    }
    
    public String getMessage() {
        return message;
    }
    
}
