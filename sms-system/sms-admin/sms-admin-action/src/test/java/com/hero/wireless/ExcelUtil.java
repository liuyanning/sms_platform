package com.hero.wireless;

import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import com.alibaba.fastjson.JSONObject;

/**
 * Apache POI操作Excel对象
 * HSSF：操作Excel 2007之前版本(.xls)格式,生成的EXCEL不经过压缩直接导出
 * XSSF：操作Excel 2007及之后版本(.xlsx)格式,内存占用高于HSSF
 * SXSSF:从POI3.8 beta3开始支持,基于XSSF,低内存占用,专门处理大数据量(建议)。
 *
 * 注意:
 * 		值得注意的是SXSSFWorkbook只能写(导出)不能读(导入)
 *
 * 说明:
 * 		.xls格式的excel(最大行数65536行,最大列数256列)
 * 	   	.xlsx格式的excel(最大行数1048576行,最大列数16384列)
 */
public class ExcelUtil {

	public static final String DEFAULT_DATE_PATTERN = "yyyy-MM-dd HH:mm:ss";// 默认日期格式
	public static final int DEFAULT_COLUMN_WIDTH = 17;// 默认列宽


	/**
	 * 导出Excel(.xlsx)格式
	 * @param <T>
	 * @param titleList 表格头信息集合
	 * @param dataArray 数据数组
	 * @param os 文件输出流
	 */
	public static <T> void exportExcel(ArrayList<LinkedHashMap> titleList, List<T> dataArray, OutputStream os) {
		String datePattern = DEFAULT_DATE_PATTERN;
		int minBytes = DEFAULT_COLUMN_WIDTH;

		/**
		 * 声明一个工作薄
 		 */
		SXSSFWorkbook workbook = new SXSSFWorkbook(1000);// 大于1000行时会把之前的行写入硬盘
		workbook.setCompressTempFiles(true);

		// 表头1样式
		CellStyle title1Style = workbook.createCellStyle();
		Font titleFont = workbook.createFont();// 字体
		titleFont.setFontHeightInPoints((short) 20);
		title1Style.setFont(titleFont);

		// head样式
		CellStyle headerStyle = workbook.createCellStyle();
		Font headerFont = workbook.createFont();
		headerFont.setFontHeightInPoints((short) 12);
		headerStyle.setFont(headerFont);

		// 单元格样式
		CellStyle cellStyle = workbook.createCellStyle();
		Font cellFont = workbook.createFont();
		cellStyle.setFont(cellFont);


		String title1 = (String) titleList.get(0).get("title1");
		LinkedHashMap<String, String> headMap = titleList.get(1);

		/**
		 * 生成一个(带名称)表格
		 */
		SXSSFSheet sheet = (SXSSFSheet) workbook.createSheet(title1);
		sheet.createFreezePane(0, 3, 0, 3);// (单独)冻结前三行

		/**
		 * 生成head相关信息+设置每列宽度
		 */
		int[] colWidthArr = new int[headMap.size()];// 列宽数组
		String[] headKeyArr = new String[headMap.size()];// headKey数组
		String[] headValArr = new String[headMap.size()];// headVal数组
		int i = 0;
		for (Map.Entry<String, String> entry : headMap.entrySet()) {
			headKeyArr[i] = entry.getKey();
			headValArr[i] = entry.getValue();

			int bytes = headKeyArr[i].getBytes().length;
			colWidthArr[i] = bytes < minBytes ? minBytes : bytes;
			sheet.setColumnWidth(i, colWidthArr[i] * 256);// 设置列宽
			i++;
		}

		/**
		 * 遍历数据集合，产生Excel行数据
 		 */
		int rowIndex = 0;
		int countSheet = 1;
		for (Object obj : dataArray) {
			if(rowIndex !=0 && rowIndex % 50000==0){
				++countSheet;
				sheet = (SXSSFSheet) workbook.createSheet(title1+countSheet);
				/**
				 * 生成head相关信息+设置每列宽度
				 */
				int[] colWidthArr1 = new int[headMap.size()];// 列宽数组
				String[] headKeyArr1 = new String[headMap.size()];// headKey数组
				String[] headValArr1 = new String[headMap.size()];// headVal数组
				int t = 0;
				for (Map.Entry<String, String> entry : headMap.entrySet()) {
					headKeyArr1[t] = entry.getKey();
					headValArr1[t] = entry.getValue();

					int bytes = headKeyArr1[t].getBytes().length;
					colWidthArr1[t] = bytes < minBytes ? minBytes : bytes;
					sheet.setColumnWidth(t, colWidthArr1[t] * 256);// 设置列宽
					t++;
				}
				rowIndex = 0;
			}
			// 生成title+head信息
			if (rowIndex == 0) {
				SXSSFRow title1Row = (SXSSFRow) sheet.createRow(0);// title1行
				title1Row.createCell(0).setCellValue(title1);
				title1Row.getCell(0).setCellStyle(title1Style);
				sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, headMap.size() - 1));// 合并单元格
				SXSSFRow headerRow = (SXSSFRow) sheet.createRow(2);// head行
				for (int j = 0; j < headValArr.length; j++) {
					headerRow.createCell(j).setCellValue(headValArr[j]);
					headerRow.getCell(j).setCellStyle(headerStyle);
				}
				rowIndex = 3;
			}

			JSONObject jo = (JSONObject) JSONObject.toJSON(obj);
			// 生成数据
			SXSSFRow dataRow = (SXSSFRow) sheet.createRow(rowIndex);// 创建行
			for (int k = 0; k < headKeyArr.length; k++) {
				SXSSFCell cell = (SXSSFCell) dataRow.createCell(k);// 创建单元格
				Object o = jo.get(headKeyArr[k]);
//				Object o = obj;
				String cellValue = "";

				if (o == null) {
					cellValue = "";
				} else if (o instanceof Date) {
					cellValue = new SimpleDateFormat(datePattern).format(o);
				} else if (o instanceof Float || o instanceof Double) {
					cellValue = new BigDecimal(o.toString()).setScale(2, BigDecimal.ROUND_HALF_UP).toString();
				} else {
					cellValue = o.toString();
				}
				cell.setCellValue(cellValue);
				cell.setCellStyle(cellStyle);
			}
			rowIndex++;
		}

		try {
			workbook.write(os);
			os.flush();// 刷新此输出流并强制将所有缓冲的输出字节写出
			os.close();// 关闭流
			workbook.dispose();// 释放workbook所占用的所有windows资源
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
