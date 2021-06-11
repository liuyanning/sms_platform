package com.hero.wireless.web.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

/**
 * 
 * 
 * ExportFileTemplate
 * 
 * @author 张丽阳
 * @createTime 2015年12月16日 上午12:20:30
 * @version 1.0.0
 * 
 */
public abstract class ExportFileTemplate<T> {

	/**
	 * 导出文件名
	 */
	private String fileName;

	/**
	 * 标题名称
	 */
	private String[] titleNames;

	private HttpServletResponse response;

	public HttpServletResponse getResponse() {
		return response;
	}

	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}

	public String[] getTitleNames() {
		return titleNames;
	}

	public void setTitleNames(String[] titleNames) {
		this.titleNames = titleNames;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public ExportFileTemplate(String fileName, String[] titleNames) {
		this.fileName = fileName + "_" + new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date())
				+ getFileExtendName();
		this.titleNames = titleNames;
	}

	/**
	 * 默认时间为文件名
	 */
	public ExportFileTemplate(String[] titleNames) {
		SimpleDateFormat fileNameDateFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String fileName = fileNameDateFormat.format(Calendar.getInstance().getTime()) + getFileExtendName();
		this.setFileName(fileName);
		this.setTitleNames(titleNames);
	}

	protected abstract String getFileExtendName();

	public abstract void exportToFile(String fileDir, List<T> dataList);

	public String addValue(String value, String defaultValue) {
		if (value == null) {
			return defaultValue;
		}
		return value.replaceAll("\n", "").replaceAll("\r\n", "");
	}

	public String addValue(Integer value, String defaultValue) {
		if (value == null) {
			return defaultValue;
		}
		return value.toString();
	}

	public String addValue(Date value, String defaultValue) {
		if (value == null) {
			return defaultValue;
		}
		return new SimpleDateFormat("yyyy-MM-dd HH:mm").format(value);
	}

	protected ExportFileTemplate() {
	}
}
