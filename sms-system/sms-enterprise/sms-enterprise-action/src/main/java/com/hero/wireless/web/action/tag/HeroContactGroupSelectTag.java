package com.hero.wireless.web.action.tag;

import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.hero.wireless.web.config.SystemKey;
import com.hero.wireless.web.entity.business.ContactGroup;
import com.hero.wireless.web.entity.business.EnterpriseUser;
import com.hero.wireless.web.service.ISendManage;

public class HeroContactGroupSelectTag extends TagSupport {

    private static final long serialVersionUID = 1L;
    private String selected;
    private String name;
    private String layVerify;
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
        if (!StringUtils.isEmpty(this.layVerify)) {
            htmlBuffer.append("lay-verify=\"");
            htmlBuffer.append(this.layVerify);
            htmlBuffer.append("\" ");
        }
        htmlBuffer.append(">");
        htmlBuffer.append("<option value=\"");
        htmlBuffer.append(StringUtils.isNotBlank(this.headerValue) ? this.headerValue : "");
        htmlBuffer.append("\">");
        htmlBuffer.append(StringUtils.isNotBlank(this.headerKey) ? this.headerKey : "请选择");
        htmlBuffer.append("</option>");
        EnterpriseUser loginUser = (EnterpriseUser)pageContext.getSession().getAttribute(SystemKey.ADMIN_USER.toString());

        ContactGroup condition = new ContactGroup();
        condition.setEnterprise_No(loginUser.getEnterprise_No());

        WebApplicationContext wac = ContextLoader.getCurrentWebApplicationContext();
        ISendManage sendManage = (ISendManage)wac.getBean("sendManage");
        List<ContactGroup> contactGroupList = sendManage
                .queryContactGroupList(condition);
        if (this.selected == null || this.selected == ""){
            this.selected = "0";
        }
        for (ContactGroup contact : contactGroupList) {
            htmlBuffer.append("<option value=\"");
            htmlBuffer.append(contact.getId());
            htmlBuffer.append("\" ");
            if (this.selected.equals(contact.getId()+"")) {
                htmlBuffer.append("selected=\"selected\"");
            }
            htmlBuffer.append(">");
            htmlBuffer.append(prepare(contact.getGroup_Name()));
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

}
