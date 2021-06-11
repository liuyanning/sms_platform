package com.hero.wireless.web.entity.business.ext;


import com.hero.wireless.web.entity.business.PlatformSmsStatistics;

import java.util.Date;

public class PlatformSmsStatisticsExt extends PlatformSmsStatistics {

    private String min_Statistics_Date_Str;
    private String max_Statistics_Date_Str;
    private Date min_Submit_Date;
    private Date max_Submit_Date;

    public String getMin_Statistics_Date_Str() {
        return min_Statistics_Date_Str;
    }

    public void setMin_Statistics_Date_Str(String min_Statistics_Date_Str) {
        this.min_Statistics_Date_Str = min_Statistics_Date_Str;
    }

    public String getMax_Statistics_Date_Str() {
        return max_Statistics_Date_Str;
    }

    public void setMax_Statistics_Date_Str(String max_Statistics_Date_Str) {
        this.max_Statistics_Date_Str = max_Statistics_Date_Str;
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
}