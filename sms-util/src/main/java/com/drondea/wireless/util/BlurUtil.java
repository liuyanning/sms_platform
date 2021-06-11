package com.drondea.wireless.util;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.StringUtils;

public class BlurUtil {

    private static final String PHONE_NO = "PHONE_NO";
    private static final String ID_CARD = "ID_CARD";
    private static final String CONTENT = "CONTENT";

    public static final String BLUR_REGEX = "(?<=\\w{3})\\w(?=\\w{4})";

    public static final String REPLACE_REGEX = "*";

    public static String blur(String str, String type) {
        switch (type) {
            case PHONE_NO:
                str = bathPhoneNoBlur(str);
                break;
            case ID_CARD:
                str = idBlur(str);
                break;
            case CONTENT:
                str = contentBlur(str);
                break;
        }
        return str;
    }

    /**
     * 手机号脱敏处理, 如果手机号位数小于11位不做处理
     * @param phone
     * @return
     */
    public static String phoneNoBlur(String phone) {
        if (StringUtils.isBlank(phone) || phone.length() < 11) {
            return phone;
        }
        return phone.replaceAll(BLUR_REGEX, REPLACE_REGEX);
    }

    /**
     * 手机号批量脱敏处理, 手机号之间用逗号隔开
     * @param phones
     * @return
     */
    public static String bathPhoneNoBlur(String phones) {

        if (StringUtils.isBlank(phones)) {
            return phones;
        }
        String[] phoneArray = phones.split(",");
        List<String> phoneList = new ArrayList<>();
        for (String s : phoneArray) {
            phoneList.add(phoneNoBlur(s));
        }
        return String.join(",", phoneList);
    }

    /**
     * 身份证前三后四脱敏
     * @param id
     * @return
     */
    public static String idBlur(String id) {
        if (StringUtils.isBlank(id) || (id.length() < 8)) {
            return id;
        }
        return id.replaceAll(BLUR_REGEX, REPLACE_REGEX);
    }

    /**
     * 隐藏内容
     * @param content
     * @return
     */
    public static String contentBlur(String content) {
        if (StringUtils.isNotBlank(content)) {
            return StringUtils.left(content, 3).concat(StringUtils.removeStart(StringUtils
                .leftPad(StringUtils.right(content, content.length() - 11), StringUtils.length(content), "*"), "***"));
        }
        return content;
    }

    /**
     * 自定义脱敏规则
     * @param content
     * @param blurRegex
     * @return
     */
    public static String contentBlur(String content, String blurRegex, String replaceRegex) {
        if (StringUtils.isBlank(content)) {
            return content;
        }
        return content.replaceAll(blurRegex, replaceRegex);
    }
}
