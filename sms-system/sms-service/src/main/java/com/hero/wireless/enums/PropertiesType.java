package com.hero.wireless.enums;

/**
 * Properties属性表枚举
 *
 * @author gengjinbiao
 * @version V1.0
 * @date 2020年04月07日
 */
public enum PropertiesType {

    // Type_Code(对应的主表)
    AGENT("Agent"),
    ENTERPRISE_USER("enterpriseUser");

    private String value;

    private PropertiesType(String value) {
        this.value = value;
    }

    public String toString() {
        return value;
    }

    public enum Property_Name {// Property_Name
        //代理商充值倍数
        AGENT_CHARGE_MULTIPLE("Agent_Charge_Multiple")
        //黑名单开关
        ,BLACKLIST_SWITCH("Blacklist_Switch")
        // 签名位置
        ,SIGNATURE_LOCATION("Signature_Location")
        // Sp_Ip
        ,SGIP_SP_IP("Sgip_Sp_Ip")
        // Sp端口
        ,SGIP_SP_PORT("Sgip_Sp_Port")
        // 需要过滤的国际区号值
        ,COUNTRY_CODE_VALUE("Country_Code_Value")

        ,FIRST_GROUP_BEGIN_TIME("First_Group_Begin_Time")

        ,FIRST_GROUP_END_TIME("First_Group_End_Time")

        ,SECOND_GROUP_BEGIN_TIME("Second_Group_Begin_Time")

        ,SECOND_GROUP_END_TIME("Second_Group_End_Time")
        ,WINDOW_SIZE("Window_Size")
        ;

        private String value;

        private Property_Name(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return value;
        }
    }

}
