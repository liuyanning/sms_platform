package com.drondea.wireless.util;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class ExcelUtil {
	private static final int LIMIT_NUM = 1000;

	/**
	 * 数据导出方法 直接输出
	 * 
	 * @param fileName
	 * @param titles
	 * @param dataList
	 *            直接输出此list的数据到Excel中
	 * @return
	 */
	@SuppressWarnings("all")
    public static String exportExcel(String path, String fileName, Object[][] titles,
            List<Map<String, Object>> dataList,boolean isShowPhoneNo) {
		if (dataList == null || dataList.isEmpty())
			return null;
		try {
			Workbook book = new SXSSFWorkbook(1000);// 超过1000条缓存到硬盘
			Sheet sheet = book.createSheet("sheet");
			Font font = book.createFont();
			CellStyle boldStyle = book.createCellStyle();
			boldStyle.setFont(font);
			long parts = 1L;
			for (int part = 0; part < parts; part++) {
				if (part == 0) {// 创建表头
					Row row = sheet.createRow(0);
					for (int index = 0; index < titles.length; index++) {
						Cell cell = row.createCell(index);
						cell.setCellValue(titles[index][1].toString());
						cell.setCellStyle(boldStyle);
						if (titles[index].length >= 3 && titles[index][2] != null) {
							sheet.setColumnWidth(index, (Integer) titles[index][2]);// 设置宽度
						}
					}
				}
				// 求取所有数据
				Map<String, Object> map;
				Row tmRow;
				Cell cell;
				for (int j = 0; j < dataList.size(); j++) {
					map = dataList.get(j);
					tmRow = sheet.createRow(part * LIMIT_NUM + j + 1);
					for (int column = 0; column < titles.length; column++) {

						Object value = getCellValue(map, titles, column,isShowPhoneNo);
						cell = tmRow.createCell(column);
						cell.setCellValue(value == null ? "" : value.toString());
					}
				}
			}
            SuperLogger.debug("创建目录:" + path);
            File dir = new File(path);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            fileName = fileName + "_" + DateTime.getString(new Date(),DateTime.Y_M_D_H_M_S_S_2) + ".xlsx";
            SuperLogger.debug("创建文件:" + fileName);
            File file = new File(path + File.separator + fileName);
            SuperLogger.debug("创建文件结果：" + file.createNewFile());
			OutputStream os = new FileOutputStream(file);
			book.write(os);
			book.close();
			os.flush();
			os.close();
			return fileName;
		} catch (Exception e) {
			SuperLogger.error(e.getMessage(), e);
		}
		return "";
	}
    private static Object getCellValue(Map<String, Object> map, Object[][] titles, int column) {
        return getCellValue(map,titles,column,false);
    }
	private static Object getCellValue(Map<String, Object> map, Object[][] titles, int column,boolean isShowPhoneNo) {
		Object[] title = titles[column];
		if (title.length >= 5 && title[4] instanceof Function) {
			Function function = (Function) title[4];
			String key = title[3].toString();
			Object result = function.apply(map.get(key));
			return result;
		}
		String key = title[0].toString();
		Object result = map.get(key);
		if (result instanceof Date) {
			Date date = (Date) result;
			return DateTime.getString(date);
		}
        result = getValue(map,key,isShowPhoneNo);
		return result;
	}
    private static Object getValue(Map<String, Object> map,String key,boolean isShowPhoneNo) {
        Object value = map.get(key);
        //手机号脱敏
        if(!isShowPhoneNo && value != null && isPhoneNo(key)){
            return BlurUtil.phoneNoBlur(value.toString());
        }
        return value;
    }

    private static boolean isPhoneNo(String str) {
        return str.equalsIgnoreCase("phone_No")
                || str.equalsIgnoreCase("phone_Nos")
                || str.equalsIgnoreCase("phoneNo")
                || str.equalsIgnoreCase("phoneNos");
    }
    /**
     * 新增or追加
     * @param path
     * @param fileName
     * @param titles
     * @param dataList
     * @return
     */
    public static String addOrEditExcel(String path, String fileName, Object[][] titles,List<Map<String, Object>> dataList) {
        if (dataList == null || dataList.isEmpty())
            return null;
        try {
            String finalFileName = path + File.separator + fileName + "_" + DateTime.getString(new Date(),DateTime.Y_M_D_2) + ".xlsx";
            File file = new File(finalFileName);
            if (!file.exists()) {
                return addExcel(path,fileName,titles,dataList);
            }
            InputStream input = new FileInputStream(file);
            XSSFWorkbook wb = (XSSFWorkbook) WorkbookFactory.create(input);
            if(wb == null){
                return null;
            }
            //获取文件的指定工作表
            XSSFSheet sheet = wb.getSheetAt(0);
            FileOutputStream output = new FileOutputStream(file.getPath());
            int index = sheet.getLastRowNum()+1;
            // 求取所有数据
            Map<String, Object> map;
            Row tmRow;
            Cell cell;
            for (int j = 0; j < dataList.size(); j++) {
                map = dataList.get(j);
                tmRow = sheet.createRow(index+j);
                for (int column = 0; column < titles.length; column++) {
                    Object value = getCellValue(map, titles, column);
                    cell = tmRow.createCell(column);
                    cell.setCellValue(value == null ? "" : value.toString());
                }
            }
            output.flush();
            wb.write(output);
            wb.close();
            output.close();
            input.close();
            return file.getName();
        } catch (Exception e) {
            SuperLogger.error(e.getMessage(), e);
        }
        return "";
    }

    @SuppressWarnings("all")
    private static String addExcel(String path, String fileName, Object[][] titles, List<Map<String, Object>> dataList) {
        if (dataList == null || dataList.isEmpty())
            return null;
        try {
            Workbook book = new SXSSFWorkbook(1000);// 超过1000条缓存到硬盘
            Sheet sheet = book.createSheet("sheet");
            Font font = book.createFont();
            CellStyle boldStyle = book.createCellStyle();
            boldStyle.setFont(font);
            long parts = 1L;
            for (int part = 0; part < parts; part++) {
                if (part == 0) {// 创建表头
                    Row row = sheet.createRow(0);
                    for (int index = 0; index < titles.length; index++) {
                        Cell cell = row.createCell(index);
                        cell.setCellValue(titles[index][1].toString());
                        cell.setCellStyle(boldStyle);
                        if (titles[index].length >= 3 && titles[index][2] != null) {
                            sheet.setColumnWidth(index, (Integer) titles[index][2]);// 设置宽度
                        }
                    }
                }
                // 求取所有数据
                Map<String, Object> map;
                Row tmRow;
                Cell cell;
                for (int j = 0; j < dataList.size(); j++) {
                    map = dataList.get(j);
                    tmRow = sheet.createRow(part * LIMIT_NUM + j + 1);
                    for (int column = 0; column < titles.length; column++) {
                        Object value = getCellValue(map, titles, column);
                        cell = tmRow.createCell(column);
                        cell.setCellValue(value == null ? "" : value.toString());
                    }
                }
            }
            SuperLogger.debug("创建目录:" + path);
            File dir = new File(path);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            fileName = fileName + "_" + DateTime.getString(new Date(),DateTime.Y_M_D_2) + ".xlsx";
            SuperLogger.debug("创建文件:" + fileName);
            File file = new File(path + File.separator + fileName);
            SuperLogger.debug("创建文件结果：" + file.createNewFile());
            OutputStream os = new FileOutputStream(file);
            book.write(os);
            book.close();
            os.flush();
            os.close();
            return fileName;
        } catch (Exception e) {
            SuperLogger.error(e.getMessage(), e);
        }
        return "";
    }
}
