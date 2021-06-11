package com.hero.wireless.web.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.drondea.wireless.util.SuperLogger;

/**
 * 导出Excel模板
 * 
 * @author 张丽阳
 * @createTime 2014年5月15日 下午4:08:06
 */
public abstract class ExportExcelTemplate<T> extends ExportFileTemplate<T> {

	public ExportExcelTemplate(String fileName, String[] titleNames) {
		super(fileName, titleNames);
	}

	/**
	 * 默认时间为文件名
	 */
	public ExportExcelTemplate(String[] titleNames) {
		super(titleNames);
	}

	@Override
	protected String getFileExtendName() {
		return ".xls";
	}

	/**
	 * 导入到文件
	 * 
	 * @param fileDir
	 *            void
	 * @exception
	 * @since 1.0.0
	 */
	public void exportToFile(String fileDir, List<T> dataList) {
		SuperLogger.debug("创建WorkBook");
		HSSFWorkbook wb = createWorkBook(dataList);
		OutputStream os;
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
			os = new FileOutputStream(file);
			wb.write(os);
			os.flush();
			os.close();
		} catch (Exception e) {
			SuperLogger.error(e.getMessage(), e);
		}
	}

	private HSSFWorkbook createWorkBook(List<T> dataList) {
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet(getFileName());
		HSSFRow row = sheet.createRow(0);

		// 创建标题
		for (int i = 0; i < getTitleNames().length; i++) {
			HSSFCell cell = row.createCell(i);
			cell.setCellValue(getTitleNames()[i]);
		}
		if (dataList != null && dataList.size() > 0) {
			// 循环数据
			for (int i = 0; i < dataList.size(); i++) {
				addDataRow(sheet, dataList.get(i), i);
			}
		}
		return wb;
	}

	/**
	 * @deprecated
	 * @param dataList
	 */
	public void export(List<T> dataList) {
		HSSFWorkbook wb = createWorkBook(dataList);
		try {
			getResponse().setHeader(
					"Content-disposition",
					"attachment;filename="
							+ URLEncoder.encode(getFileName(), "UTF-8"));
			wb.write(getResponse().getOutputStream());
			getResponse().getOutputStream().flush();
			getResponse().getOutputStream().close();
		} catch (Exception e) {
			SuperLogger.error(e.getMessage(), e);
		}
	}

	/**
	 * 增加数据
	 * 
	 * @param sheet
	 * @param data
	 * @param rowNumber
	 */
	public abstract void addDataRow(HSSFSheet sheet, T data, int rowNumber);
}
