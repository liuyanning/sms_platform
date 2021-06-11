package com.hero.wireless.web.util;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DecimalFormat;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import com.drondea.wireless.util.SuperLogger;
import com.hero.wireless.web.exception.BaseException;

/**
 * excel读取模板
 * 
 * @author 张丽阳
 * 
 */
public abstract class ExcelReadTemplate {
	private Integer readedRowCount = 0;
	private String readSheetName;
	private MultipartFile file;
	private String fileName;
	private Integer startRowNum = 0;
	private Integer endRowNum;
	private Integer startColumnIndex = 0;
	private Integer endColumnIndex = 10;

	public String getReadSheetName() {
		return readSheetName;
	}

	public void setReadSheetName(String readSheetName) {
		this.readSheetName = readSheetName;
	}

	public Integer getReadedRowCount() {
		return readedRowCount;
	}

	public void setReadedRowCount(Integer readedRowCount) {
		this.readedRowCount = readedRowCount;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public Integer getStartRowNum() {
		return startRowNum;
	}

	public void setStartRowNum(Integer startRowNum) {
		this.startRowNum = startRowNum;
	}

	public Integer getEndRowNum() {
		return endRowNum;
	}

	public void setEndRowNum(Integer endRowNum) {
		this.endRowNum = endRowNum;
	}

	public Integer getStartColumnIndex() {
		return startColumnIndex;
	}

	public void setStartColumnIndex(Integer startColumnIndex) {
		this.startColumnIndex = startColumnIndex;
	}

	public Integer getEndColumnIndex() {
		return endColumnIndex;
	}

	public void setEndColumnIndex(Integer endColumnIndex) {
		this.endColumnIndex = endColumnIndex;
	}

	public ExcelReadTemplate(MultipartFile file) {
		super();
		this.file = file;
		this.fileName = file.getOriginalFilename();
	}

	private Workbook getWorkbook() throws FileNotFoundException, IOException {
		String extName = FileUtil.getExtensionName(this.fileName);
		if (extName.equals("xls")) {
			return new HSSFWorkbook(new ByteArrayInputStream(this.file.getBytes()));
		}
		if (extName.equals("xlsx")) {
			return new XSSFWorkbook(new ByteArrayInputStream(this.file.getBytes()));
		}
		return null;
	}

	protected String getCellValue(Cell cell) {
		if (cell.getCellType() == CellType.NUMERIC) {
			Double d = cell.getNumericCellValue();
			DecimalFormat df = new DecimalFormat("#");
			return df.format(d);
		}
		if (cell.getCellType() == CellType.STRING) {
			return cell.getStringCellValue();
		}
		if (cell.getCellType() == CellType.FORMULA) {
			SuperLogger.debug(cell.getCellFormula());
			SuperLogger.debug(cell.getStringCellValue());
			return cell.getStringCellValue();
		}
		return cell.getRichStringCellValue().getString();
	}

	/**
	 * 读取sheet事件
	 * 
	 * @param sheet
	 */
	protected abstract void readSheetEvent(Sheet sheet) throws Exception;

	/**
	 * 读取行事件之前
	 * 
	 * @param row
	 */
	protected abstract void readRowBeforeEvent(Sheet sheet, Row row) throws Exception;

	/**
	 * 读取行事件之后
	 * 
	 * @param row
	 */
	protected abstract void readRowAfterEvent(Sheet sheet, Row row) throws Exception;

	protected abstract void readCellEvent(Sheet sheet, Row row, Cell cell, String cellName) throws Exception;

	public void read() throws Exception {
		Workbook workBook = getWorkbook();
		if (workBook == null) {
//			throw new BaseException(ExceptionKey.SERVICE_EXCEPTION, "文件格式不正确!");
			throw new BaseException("文件格式不正确!");
		}
		boolean existsSheetName = false;
		// 循环Sheet
		for (int sheetNumer = 0; sheetNumer < workBook.getNumberOfSheets(); sheetNumer++) {
			Sheet sheet = workBook.getSheetAt(sheetNumer);
			if (!StringUtils.isEmpty(readSheetName)) {
				if (!readSheetName.equals(sheet.getSheetName())) {
					if (sheetNumer == workBook.getNumberOfSheets() - 1 && !existsSheetName) {
//						throw new BaseException(ExceptionKey.SERVICE_EXCEPTION, readSheetName + "不存在!");
						throw new BaseException(readSheetName + "不存在!");
					}
					continue;
				}
				existsSheetName = true;
			}
			readSheetEvent(sheet);
			if (SuperLogger.isDebugEnabled()) {
				SuperLogger.debug(sheet.getSheetName());
			}
			// 循环行
			for (int rowNumber = startRowNum; rowNumber <= sheet.getLastRowNum(); rowNumber++) {
				Row row = sheet.getRow(rowNumber);
				if (row == null) {
					continue;
				}
				SuperLogger.debug("行号:" + row.getRowNum());
				readRowBeforeEvent(sheet, row);
				// 循环列Cell
				for (int cellIndex = startColumnIndex; cellIndex < getEndColumnIndex(); cellIndex++) {
					Cell cell = row.getCell(cellIndex);
					if (cell == null) {
						continue;
					}
					String cellName = CellReference.convertNumToColString(cell.getColumnIndex());
					readCellEvent(sheet, row, cell, cellName);
				}
				readRowAfterEvent(sheet, row);
				readedRowCount++;
			}
		}
	}
}
