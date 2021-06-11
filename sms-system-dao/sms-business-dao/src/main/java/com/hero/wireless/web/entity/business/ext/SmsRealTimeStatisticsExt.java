package com.hero.wireless.web.entity.business.ext;


import com.drondea.wireless.util.DateTime;
import com.hero.wireless.web.entity.business.SmsRealTimeStatistics;

import java.util.Date;

public class SmsRealTimeStatisticsExt extends SmsRealTimeStatistics {

    private String judgeStrExt;
    private String groupStr;
    private String min_Submit_Date_Str;
    private String max_Submit_Date_Str;
    private Date min_Submit_Date;
    private Date max_Submit_Date;
    private boolean flag;

    public String getJudgeStrExt() {
        if (this.getSubmit_Date() == null) {
            return null;
        }
        return DateTime.getString(this.getSubmit_Date(), DateTime.Y_M_D_1) + this.getAgent_No() + this.getBusiness_User_Id()
                + this.getEnterprise_No() + this.getChannel_No() + this.getEnterprise_User_Id() + this.getMessage_Type_Code()
                + this.getCountry_Number() + this.getOperator() + this.getProvince_Code() + this.getSignature();
    }

    public void setJudgeStrExt(String judgeStrExt) {
        this.judgeStrExt = judgeStrExt;
    }

    public String getGroupStr() {
        return groupStr;
    }

    public void setGroupStr(String groupStr) {
        this.groupStr = groupStr;
    }

    public String getMin_Submit_Date_Str() {
        return min_Submit_Date_Str;
    }

    public void setMin_Submit_Date_Str(String min_Submit_Date_Str) {
        this.min_Submit_Date_Str = min_Submit_Date_Str;
    }

    public String getMax_Submit_Date_Str() {
        return max_Submit_Date_Str;
    }

    public void setMax_Submit_Date_Str(String max_Submit_Date_Str) {
        this.max_Submit_Date_Str = max_Submit_Date_Str;
    }

    public Date getMin_Submit_Date() {
        return min_Submit_Date;
    }

    public void setMin_Submit_Date(Date min_Submit_Date) {
        this.min_Submit_Date = min_Submit_Date;
    }

    public Date getMax_Submit_Date() {
        return max_Submit_Date;
    }

    public void setMax_Submit_Date(Date max_Submit_Date) {
        this.max_Submit_Date = max_Submit_Date;
    }

    public boolean getFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }
}