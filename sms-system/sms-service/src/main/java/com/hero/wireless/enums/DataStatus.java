package com.hero.wireless.enums;

/**
 * 数据状态，发件箱，发送记录，发送回执记录状态
 * 显示，隐藏
 */
public enum DataStatus {

    NORMAL("0"),
    RESEND("1"),
    DELETE("2");

    private String value;

    DataStatus(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
