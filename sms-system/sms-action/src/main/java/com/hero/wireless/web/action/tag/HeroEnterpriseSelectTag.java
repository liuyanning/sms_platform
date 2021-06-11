package com.hero.wireless.web.action.tag;

import com.hero.wireless.web.config.DatabaseCache;
import com.hero.wireless.web.entity.business.Enterprise;
import com.hero.wireless.web.entity.business.ext.EnterpriseExt;

import java.util.stream.Collectors;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;

import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

public class HeroEnterpriseSelectTag extends TagSupport{

    private static final long serialVersionUID = 1L;
    private String selected;
    private String name;
    private String id;
    private String disabled;
    private String layVerify;
    private String layFilter;
    private String agentNo;
    private String cssClass;
    private String headerKey;
    private String headerValue;
    private String adminUserId;
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

    public String getDisabled() {
        return disabled;
    }

    public void setDisabled(String disabled) {
        this.disabled = disabled;
    }

    public String getAgentNo() {
        return agentNo;
    }

    public void setAgentNo(String agentNo) {
        this.agentNo = agentNo;
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
    public String getLayFilter() { return layFilter; }
    public void setLayFilter(String layFilter) { this.layFilter = layFilter; }

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
        EnterpriseExt enterpriseExt = new EnterpriseExt();
        if (StringUtils.isNotEmpty(agentNo)) {
        	enterpriseExt.setAgent_No(agentNo);
        }
        if(StringUtils.isNotBlank(adminUserId)){
            enterpriseExt.setBusiness_User_Id(Integer.parseInt(adminUserId));
        }
        List<? extends Enterprise> enterpriseList = DatabaseCache.getEnterpriseList(enterpriseExt);
        for (Enterprise enterprise : enterpriseList) {
            htmlBuffer.append("<option value=\"");
            htmlBuffer.append(enterprise.getNo());
            htmlBuffer.append("\" ");
            if ((enterprise.getNo()+"").equals(this.selected)) {
                htmlBuffer.append("selected=\"selected\"");
            }
            htmlBuffer.append(">");
            htmlBuffer.append(prepare(enterprise.getName()));
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

    public String getAdminUserId() {
        return adminUserId;
    }

    public void setAdminUserId(String adminUserId) {
        this.adminUserId = adminUserId;
    }
}
