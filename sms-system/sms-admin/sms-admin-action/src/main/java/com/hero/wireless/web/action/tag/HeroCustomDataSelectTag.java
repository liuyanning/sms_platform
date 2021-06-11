package com.hero.wireless.web.action.tag;

import com.hero.wireless.enums.AccountStatus;
import com.hero.wireless.web.config.DatabaseCache;
import com.hero.wireless.web.entity.business.*;
import com.hero.wireless.web.entity.business.ext.EnterpriseUserExt;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.util.ArrayList;
import java.util.List;

public class HeroCustomDataSelectTag extends TagSupport {

	private static final long serialVersionUID = 1L;
	private String selected;
	private String name;
	private String id;
    private String disabled;
	private String layVerify;
	private String cssClass;
	private String headerKey;
	private String headerValue;
	private String dataSourceType;
	private boolean escape = true;
	private boolean escapeJavaScript = false;

    public String getDisabled() {
        return disabled;
    }

    public void setDisabled(String disabled) {
        this.disabled = disabled;
    }

    public String getSelected() {
		return selected;
	}

	public void setSelected(String selected) {
		this.selected = selected;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCssClass() {
		return cssClass;
	}

	public void setCssClass(String cssClass) {
		this.cssClass = cssClass;
	}

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

	public String getDataSourceType() {
		return dataSourceType;
	}

	public void setDataSourceType(String dataSourceType) {
		this.dataSourceType = dataSourceType;
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

	class TagOption { // 数据内部类
		private String tagValue;
		private String tagName;

		public String getTagValue() {
			return tagValue;
		}

		public TagOption setTagValue(String tagValue) {
			this.tagValue = tagValue;
			return this;
		}

		public String getTagName() {
			return tagName;
		}

		public TagOption setTagName(String tagName) {
			this.tagName = tagName;
			return this;
		}
	}

	@Override
	public int doEndTag() throws JspException {

		List<TagOption> tagOptionList = dataSource();

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

		if (this.selected == null || this.selected == "") {
			this.selected = "0";
		}
		for (TagOption tagOption : tagOptionList) {
			htmlBuffer.append("<option value=\"");
			htmlBuffer.append(tagOption.getTagValue());
			htmlBuffer.append("\" ");
			if (this.selected.equals(tagOption.getTagValue() + "")) {
				htmlBuffer.append("selected=\"selected\"");
			}
			htmlBuffer.append(">");
			htmlBuffer.append(prepare(tagOption.getTagName()));
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
	private String translate(String value) {
		String result = value;
		switch (value){
			case "Normal":
				result="正常";
				break;
			case "Locked":
				result="锁定";
				break;
		}
		return result;
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

	private List<TagOption> dataSource() {
		List<TagOption> tagOptionList = new ArrayList<TagOption>();
		switch (this.dataSourceType) {
		case "allChannels":
			List<Channel> channelList = DatabaseCache.getAllChannelList();
			channelList.stream().forEach(item -> {
				TagOption tagOption = new TagOption();
				tagOption.setTagName(item.getName());
				tagOption.setTagValue(item.getNo());
				tagOptionList.add(tagOption);
			});
			break;
		case "allEnterpriseUsers":
			List<EnterpriseUser> userList = DatabaseCache.getEnterpriseUserList(new EnterpriseUserExt());
			userList.stream().forEach(item -> {
				TagOption tagOption = new TagOption();
				tagOption.setTagName(item.getReal_Name() + " (账号" + item.getUser_Name() + ")");
				tagOption.setTagValue(String.valueOf(item.getId()));
				tagOptionList.add(tagOption);
			});
			break;
		case "allBusinessUser":
			List<AdminUser> adminUserList = DatabaseCache.getAdminUsersByRoleCode("Business");
			adminUserList.stream().forEach(item -> {
				TagOption tagOption = new TagOption();
				tagOption.setTagName(item.getReal_Name());
				tagOption.setTagValue(String.valueOf(item.getId()));
				tagOptionList.add(tagOption);
			});
			break;
		case "allProducts":
			List<Product> productList = DatabaseCache.getProduct();
			productList.stream().forEach(item -> {
				TagOption tagOption = new TagOption();
				tagOption.setTagName(item.getName());
				tagOption.setTagValue(String.valueOf(item.getNo()));
				tagOptionList.add(tagOption);
			});
			break;
		case "allAdmin":
			AdminUser adminUser = new AdminUser();
			adminUser.setStatus(AccountStatus.NORMAL.toString());
			List<AdminUser> adminUsers = DatabaseCache.getAdminUserList(adminUser);
			adminUsers.stream().forEach(item -> {
				TagOption tagOption = new TagOption();
				tagOption.setTagName(item.getUser_Name());
				tagOption.setTagValue(String.valueOf(item.getId()));
				tagOptionList.add(tagOption);
			});
			break;
		case "allMaterial":
			List<MmsMaterial> materialManagements = DatabaseCache.getMmsMaterialList();
			materialManagements.stream().forEach(item -> {
				TagOption tagOption = new TagOption();
				tagOption.setTagName(item.getMaterial_Name());
				tagOption.setTagValue(item.getUrl());
				tagOptionList.add(tagOption);
			});
			break;
		case "interceptStrategy":
			List<InterceptStrategy> interceptStrategies = DatabaseCache.getAllInterceptStrategy();
			interceptStrategies.stream().forEach(item -> {
				TagOption tagOption = new TagOption();
				tagOption.setTagName(item.getName());
				tagOption.setTagValue(String.valueOf(item.getId()));
				tagOptionList.add(tagOption);
			});
			break;
		}
		return tagOptionList;
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
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
