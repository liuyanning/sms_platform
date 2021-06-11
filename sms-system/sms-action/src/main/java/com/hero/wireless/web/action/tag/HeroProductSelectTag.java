package com.hero.wireless.web.action.tag;

import com.hero.wireless.web.config.DatabaseCache;
import com.hero.wireless.web.entity.business.Product;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;

import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

public class HeroProductSelectTag extends TagSupport{

    private static final long serialVersionUID = 1L;
    private String selected;
    private String name;
    private String statusCode;
    private String cssClass;
    private String headerKey;
    private String headerValue;
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
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getSelected() {
        return selected;
    }
    public void setSelected(String selected) {
        this.selected = selected;
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
        htmlBuffer.append(" lay-search>");
        htmlBuffer.append("<option value=\"");
        htmlBuffer.append(StringUtils.isNotBlank(this.headerValue) ? this.headerValue : "");
        htmlBuffer.append("\">");
        htmlBuffer.append(StringUtils.isNotBlank(this.headerKey) ? this.headerKey : "请直接选择或搜索选择");
        htmlBuffer.append("</option>");
        Product condition = new Product();
        condition.setStatus_Code(this.statusCode);
        List<? extends Product> productList = DatabaseCache.getProduct(condition);
        for (Product product : productList) {
            htmlBuffer.append("<option value=\"");
            htmlBuffer.append(product.getNo());
            htmlBuffer.append("\" ");
            if ((product.getNo()).equals(this.selected)) {
                htmlBuffer.append("selected=\"selected\"");
            }
            htmlBuffer.append(">");
            htmlBuffer.append(prepare(product.getName()));
            htmlBuffer.append("(");
            htmlBuffer.append(prepare(translate(product.getStatus_Code())));
            htmlBuffer.append(")");
            htmlBuffer.append("</option>");
        }
        htmlBuffer.append("</select>");
        try {
            this.pageContext.getOut().println(htmlBuffer.toString());
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
    private String translate(String value) {
        String result = value;
        switch (value){
            case "Stop":
                result="停止";
                break;
            case "Start":
                result="启动";
                break;
        }
        return result;
    }
}
