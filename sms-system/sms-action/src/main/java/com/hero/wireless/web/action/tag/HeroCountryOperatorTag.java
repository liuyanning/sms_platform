package com.hero.wireless.web.action.tag;

import com.hero.wireless.web.config.DatabaseCache;
import com.hero.wireless.web.entity.business.ext.SmsRouteExt;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.ObjectUtils;

import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

public class HeroCountryOperatorTag extends TagSupport{

    private static final long serialVersionUID = 1L;
    private String countryNumber;
    private String selected;
    private String id;
    private String name;
    private String cssClass;
    private String headerKey;
    private String headerValue;
    private String disabled;
    private String layVerify;
    private String layFilter;
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
    public String getLayFilter() { return layFilter; }
    public void setLayFilter(String layFilter) { this.layFilter = layFilter; }

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
    public String getSelected() {
        return selected;
    }
    public void setSelected(String selected) {
        this.selected = selected;
    }

    public String getDisabled() {
        return disabled;
    }

    public void setDisabled(String disabled) {
        this.disabled = disabled;
    }

    public String getCountryNumber() {
        return countryNumber;
    }

    public void setCountryNumber(String countryNumber) {
        this.countryNumber = countryNumber;
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
        htmlBuffer.append("<option value=\"");
        htmlBuffer.append(StringUtils.isNotBlank(this.headerValue) ? this.headerValue : "");
        htmlBuffer.append("\">");
        htmlBuffer.append(StringUtils.isNotBlank(this.headerKey) ? this.headerKey : "请选择");
        htmlBuffer.append("</option>");
        List<SmsRouteExt> smsRouteList = DatabaseCache.queryOperatorListByCountry(this.countryNumber);
        if (ObjectUtils.isNotEmpty(smsRouteList)) {
            smsRouteList.forEach(item->{
                htmlBuffer.append("<option value=\"");
                String value = item.getRoute_Name_Code();
                htmlBuffer.append(value);
                htmlBuffer.append("\" ");
                if (value.equals(this.selected)) {
                    htmlBuffer.append("selected=\"selected\"");
                }
                htmlBuffer.append(">");
                htmlBuffer.append(prepare(value));
                htmlBuffer.append("</option>");
            });
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
