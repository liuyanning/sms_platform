package com.hero.wireless.web.entity.business.ext;

import com.hero.wireless.web.entity.business.SensitiveWord;

import java.util.Date;

/**
 * @version V3.0.0
 * @description: 敏感字
 * @author: lyh
 * @date: 2021年04月12日
 **/
public class SensitiveWordExt extends SensitiveWord {
    private Date minCreateDate;
    private Date maxCreateDate;

    public Date getMinCreateDate() {
        return minCreateDate;
    }

    public void setMinCreateDate(Date minCreateDate) {
        this.minCreateDate = minCreateDate;
    }

    public Date getMaxCreateDate() {
        return maxCreateDate;
    }

    public void setMaxCreateDate(Date maxCreateDate) {
        this.maxCreateDate = maxCreateDate;
    }
}
