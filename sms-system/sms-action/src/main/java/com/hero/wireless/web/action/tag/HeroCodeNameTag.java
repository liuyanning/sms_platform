package com.hero.wireless.web.action.tag;

import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.lang.StringEscapeUtils;

import com.hero.wireless.web.config.DatabaseCache;
import com.hero.wireless.web.entity.business.Code;


/**
 *
 * @author 张丽阳
 * @createTime 2014年2月21日 下午4:32:52
 */
public class HeroCodeNameTag extends TagSupport {

	private static final long serialVersionUID = 1L;
	private String sortCode;
	private String value;
	private boolean escape = true;
	private boolean escapeJavaScript = false;

	public String getSortCode(){return sortCode;}
	public void setSortCode(String sortCode){this.sortCode = sortCode;}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
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
		String str = "";
		List<? extends Code> codeSortList = DatabaseCache.getCodeListBySortCode(this.sortCode);
		for (Code code : codeSortList) {
			if(code.getCode().equals(this.value)){
				str = prepare(code.getName());
			}
		}
		try {
			this.pageContext.getOut().println(str);
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
