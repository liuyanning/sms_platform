package com.hero.wireless.web.action.tag;

import com.hero.wireless.web.config.DatabaseCache;
import org.apache.commons.lang.StringEscapeUtils;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

public class HeroPageConfigurationTextTag extends TagSupport {

	private static final long serialVersionUID = 1L;
	private String code;
	private String sortCode;
	private String defaultValue;
	private boolean escape = true;
	private boolean escapeJavaScript = false;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setSortCode(String sortCode) {
		this.sortCode = sortCode;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
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
		sortCode = sortCode==null?"page_configuration":sortCode;
		String text = DatabaseCache.getStringValueBySortCodeAndCode(sortCode, code, defaultValue);
		try {
			this.pageContext.getOut().print(text);
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
