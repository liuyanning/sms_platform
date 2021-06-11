package com.hero.wireless.web.action.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;

public class HeroPropertyTag extends TagSupport {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private Integer subStartIndex;// 截取开始索引
    private Integer subLength;// 截取长度
    private String subFillString = "...";// 填充字符串
    private String defaultValue;
    private String value;
    private boolean escape = true;
    private boolean escapeJavaScript = false;

    @Override
    public int doEndTag() throws JspException {
        try {
            if (subLength != null) {
                int startIndex = 0;
                if (subStartIndex != null) {
                    startIndex = subStartIndex;
                }
                if (StringUtils.isNotBlank(value) && value.length() > subLength.intValue()) {
                    this.pageContext.getOut().println(prepare(value.substring(startIndex, subLength) + subFillString));
                }
            } else if (defaultValue != null) {
                this.pageContext.getOut().println(prepare(defaultValue));
            }
        } catch (Exception e) {
            throw new JspException(e);
        }
        return EVAL_PAGE;
    }

    private String prepare(String value) {
        String result = value;
        if (escape) {
            result = StringEscapeUtils.escapeHtml(result);
        }
        if (escapeJavaScript) {
            result = StringEscapeUtils.escapeJavaScript(result);
        }
        return result;
    }

    public void setEscape(boolean escape) {
        this.escape = escape;
    }

    public void setEscapeJavaScript(boolean escapeJavaScript) {
        this.escapeJavaScript = escapeJavaScript;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Integer getSubStartIndex() {
        return subStartIndex;
    }

    public void setSubStartIndex(Integer subStartIndex) {
        this.subStartIndex = subStartIndex;
    }

    public Integer getSubLength() {
        return subLength;
    }

    public void setSubLength(Integer subLength) {
        this.subLength = subLength;
    }

    public String getSubFillString() {
        return subFillString;
    }

    public void setSubFillString(String subFillString) {
        this.subFillString = subFillString;
    }

}
