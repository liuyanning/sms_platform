package com.hero.wireless.web.action.tag;

import com.hero.wireless.web.config.DatabaseCache;
import com.hero.wireless.web.entity.business.Code;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.util.List;

public class HeroCodeSelectTag extends TagSupport{

    private static final long serialVersionUID = 1L;
    private String sortCode;//代码分类
    private String selected;
    private String id;
    private String name;
    private String upCode;
    private String cssClass;
    private String headerKey;
    private String headerValue;
    private String disabled;
    private String layVerify;
    private String layFilter;
    private String valueField;
    private String includeHeader;
    private boolean escape = true;
    private boolean escapeJavaScript = false;
    public String getHeaderKey() {
        return headerKey;
    }
    public void setHeaderKey(String headerKey) {
        this.headerKey = headerKey;
    }
    public String getHeaderValue() {
        return headerValue;
    }
    public void setHeaderValue(String headerValue) {
        this.headerValue = headerValue;
    }
    public String getCssClass() {
        return cssClass;
    }
    public void setCssClass(String cssClass) {
        this.cssClass = cssClass;
    }
    public String getValueField() { return valueField; }
    public void setValueField(String valueField) { this.valueField = valueField; }
    public String getLayFilter() { return layFilter; }
    public void setLayFilter(String layFilter) { this.layFilter = layFilter; }
    public String getIncludeHeader() {
        return includeHeader;
    }
    public void setIncludeHeader(String includeHeader) {
        this.includeHeader = includeHeader;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getUpCode() {
        return upCode;
    }
    public void setUpCode(String upCode) {
        this.upCode = upCode;
    }
    public String getSelected() {
        return selected;
    }
    public void setSelected(String selected) {
        this.selected = selected;
    }
    public String getSortCode() {
        return sortCode;
    }
    public void setSortCode(String sortCode) {
        this.sortCode = sortCode;
    }

    public String getDisabled() {
        return disabled;
    }

    public void setDisabled(String disabled) {
        this.disabled = disabled;
    }

    public String getLayVerify() {
        return layVerify;
    }

    public void setLayVerify(String layVerify) {
        this.layVerify = layVerify;
    }

    public boolean isEscape() {
        return escape;
    }

    public void setEscape(boolean escape) {
        this.escape = escape;
    }

    public boolean isEscapeJavaScript() {
        return escapeJavaScript;
    }

    public void setEscapeJavaScript(boolean escapeJavaScript) {
        this.escapeJavaScript = escapeJavaScript;
    }

    @Override
    public int doEndTag() throws JspException {
        StringBuffer htmlBuffer = new StringBuffer();
        htmlBuffer.append("<select ");
        if (!StringUtils.isEmpty(this.id)) {
            htmlBuffer.append("id=\"");
            htmlBuffer.append(this.id);
            htmlBuffer.append("\" ");
        }
        if (!StringUtils.isEmpty(this.name)) {
            htmlBuffer.append("name=\"");
            htmlBuffer.append(this.name);
            htmlBuffer.append("\" ");
        }
        if (!StringUtils.isEmpty(cssClass)) {
            htmlBuffer.append("class=\"");
            htmlBuffer.append(this.cssClass);
            htmlBuffer.append("\" ");
        }
        if (!StringUtils.isEmpty(disabled)) {
            htmlBuffer.append("disabled=\"");
            htmlBuffer.append(this.disabled);
            htmlBuffer.append("\" ");
        }
        if (!StringUtils.isEmpty(this.layVerify)) {
            htmlBuffer.append("lay-verify=\"");
            htmlBuffer.append(this.layVerify);
            htmlBuffer.append("\" ");
        }
        if (!StringUtils.isEmpty(this.layFilter)) {
            htmlBuffer.append("lay-filter=\"");
            htmlBuffer.append(this.layFilter);
            htmlBuffer.append("\" ");
        }
        htmlBuffer.append(" lay-search>");
        if (StringUtils.isEmpty(this.includeHeader) || "1".equals(this.includeHeader)){
            htmlBuffer.append("<option value=\"");
            htmlBuffer.append(StringUtils.isNotBlank(this.headerValue) ? this.headerValue : "");
            htmlBuffer.append("\">");
            htmlBuffer.append(StringUtils.isNotBlank(this.headerKey) ? this.headerKey : "请选择");
            htmlBuffer.append("</option>");
        }
        List<? extends Code> codeSortList ;
        if (StringUtils.isEmpty(this.upCode)){
            codeSortList = DatabaseCache.getCodeListBySortCode(this.sortCode);
        } else {
            codeSortList = DatabaseCache.getCodeListBySortCodeAndUpCode(this.sortCode,this.upCode);
        }

        for (Code code : codeSortList) {
            htmlBuffer.append("<option value=\"");
            String value = code.getCode();
            if ("Name".equalsIgnoreCase(valueField)) {
                value = code.getName();
            } else if("Value".equalsIgnoreCase(valueField)) {
                value = code.getValue();
            }
            htmlBuffer.append(value);
            htmlBuffer.append("\" ");
            if (value.equals(this.selected)) {
                htmlBuffer.append("selected=\"selected\"");
            }
            htmlBuffer.append(">");
            htmlBuffer.append(prepare(code.getName()));
            htmlBuffer.append("</option>");
        }
        htmlBuffer.append("</select>");
        try {
            this.pageContext.getOut().print(htmlBuffer.toString());
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

}
