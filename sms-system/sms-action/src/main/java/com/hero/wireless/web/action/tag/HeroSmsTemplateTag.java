package com.hero.wireless.web.action.tag;

import com.hero.wireless.enums.AuditStatus;
import com.hero.wireless.web.config.DatabaseCache;
import com.hero.wireless.web.entity.business.SmsTemplate;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;

import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

public class HeroSmsTemplateTag extends TagSupport{

    private static final long serialVersionUID = 1L;
    private String selected;
    private String name;
    private String id;
    private String layVerify;
    private String enterpriseNo;
    private Integer enterpriseUserId;
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
    public String getSelected() {
        return selected;
    }
    public void setSelected(String selected) {
        this.selected = selected;
    }

    public String getEnterpriseNo() {
        return enterpriseNo;
    }

    public void setEnterpriseNo(String enterpriseNo) {
        this.enterpriseNo = enterpriseNo;
    }

    public Integer getEnterpriseUserId() {
        return enterpriseUserId;
    }

    public void setEnterpriseUserId(Integer enterpriseUserId) {
        this.enterpriseUserId = enterpriseUserId;
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
        if (!StringUtils.isEmpty(this.id)) {
            htmlBuffer.append("id=\"");
            htmlBuffer.append(this.id);
            htmlBuffer.append("\" ");
        }
        
        if (!StringUtils.isEmpty(this.layVerify)) {
            htmlBuffer.append("lay-verify=\"");
            htmlBuffer.append(this.layVerify);
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
        htmlBuffer.append(StringUtils.isNotBlank(this.headerKey) ? this.headerKey : "??????????????????????????????");
        htmlBuffer.append("</option>");
        if(StringUtils.isNotEmpty(enterpriseNo) && enterpriseUserId != null){
            List<? extends SmsTemplate> smsTemplateList = DatabaseCache.
                    getSmsTemplateList(enterpriseNo,enterpriseUserId, AuditStatus.PASS.value());
            if(smsTemplateList != null && !smsTemplateList.isEmpty()){
                smsTemplateList.forEach(item ->{
                    htmlBuffer.append("<option value=\"");
                    htmlBuffer.append(item.getTemplate_Content());
                    htmlBuffer.append("\" ");
                    if ((item.getId().toString()).equals(this.selected)) {
                        htmlBuffer.append("selected=\"selected\"");
                    }
                    htmlBuffer.append(">");
                    htmlBuffer.append(prepare(item.getTemplate_Name()));
                    htmlBuffer.append("</option>");
                });
            }
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
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getLayVerify() {
		return layVerify;
	}
	public void setLayVerify(String layVerify) {
		this.layVerify = layVerify;
	}

}
