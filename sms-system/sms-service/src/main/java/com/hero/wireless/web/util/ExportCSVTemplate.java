package com.hero.wireless.web.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.List;

import com.drondea.wireless.util.SuperLogger;

/**
 * 
 * 
 * ExportCVSTemplate
 * 
 * @author 张丽阳
 * @createTime 2015年12月16日 上午12:59:35
 * @version 1.0.0
 * 
 */
public abstract class ExportCSVTemplate<T> extends ExportFileTemplate<T> {

	protected String newLine="\n";
	public ExportCSVTemplate(String fileName, String[] titleNames) {
		super(fileName, titleNames);
	}

	public ExportCSVTemplate(String[] titleNames) {
		super(titleNames);
	}

	@Override
	protected String getFileExtendName() {
		return ".txt";
	}

	@Override
	public void exportToFile(String fileDir, List<T> dataList) {
		FileOutputStream out = null;
		OutputStreamWriter osw = null;
		BufferedWriter bw = null;
		try {
			// 创建目录
			SuperLogger.debug("创建目录");
			File dir = new File(fileDir);
			if (!dir.exists()) {
				dir.mkdirs();
			}

			// 创建文件
			SuperLogger.debug("创建文件");
			File file = new File(fileDir + "/" + getFileName());
			boolean createFileResult = file.createNewFile();
			SuperLogger.debug("创建文件结果：" + createFileResult);
			out = new FileOutputStream(file);
			osw = new OutputStreamWriter(out,"GB2312");
			bw = new BufferedWriter(osw);

			// 创建标题
			for (int i = 0; i < getTitleNames().length; i++) {
				bw.append(getTitleNames()[i]).append("&");
			}
			bw.append(newLine);
			if (dataList != null && dataList.size() > 0) {
				// 循环数据
				for (int i = 0; i < dataList.size(); i++) {
					addDataRow(bw, dataList.get(i));
				}
			}

			bw.close();
			osw.close();
			out.close();
		} catch (Exception e) {
			SuperLogger.error(e.getMessage(), e);
		}
	}

	public abstract void addDataRow(BufferedWriter bw, T data) throws Exception;
}
