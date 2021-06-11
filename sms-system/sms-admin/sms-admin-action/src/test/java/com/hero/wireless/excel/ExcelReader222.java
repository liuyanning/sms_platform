package com.hero.wireless.excel;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class ExcelReader222 {


    private static final String XLS = "xls";
    private static final String XLSX = "xlsx";

    /**
     * 
     * @param inputStream
     * @param fileType
     * @return
     * @throws IOException
     */
    public static Workbook getWorkbook(InputStream inputStream, String fileType) throws IOException {
        Workbook workbook = null;
        if (fileType.equalsIgnoreCase(XLS)) {
            workbook = new HSSFWorkbook(inputStream);
        } else if (fileType.equalsIgnoreCase(XLSX)) {
            workbook = new XSSFWorkbook(inputStream);
        }
        return workbook;
    }

    /**
     * 读取Excel文件内容
     * @param fileName 要读取的Excel文件所在路径
     * @return 读取结果列表，读取失败时返回null
     */
    public static List<MobileArea> readExcel(String fileName) {

        Workbook workbook = null;
        FileInputStream inputStream = null;
        List<MobileArea> resultDataList = null;
        try {
            // 获取Excel后缀名
            String fileType = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
            // 获取Excel文件
            File excelFile = new File(fileName);
            if (!excelFile.exists()) {
                System.out.println("指定的Excel文件不存在！");
                return null;
            }

            // 获取Excel工作簿
            inputStream = new FileInputStream(excelFile);
            workbook = getWorkbook(inputStream, fileType);

            // 读取excel中的数据
            resultDataList = parseExcel(workbook);

            return resultDataList;
        } catch (Exception e) {
        	System.out.println("解析Excel失败，文件名：" + fileName + " 错误信息：" + e.getMessage());
            return resultDataList;
        } finally {
            try {
                if (null != workbook) {
                    workbook.close();
                }
                if (null != inputStream) {
                    inputStream.close();
                }
            } catch (Exception e) {
            	System.out.println("关闭数据流出错！错误信息：" + e.getMessage());
                return null;
            }
        }
    }

    /**
     * 解析Excel数据
     * @param workbook Excel工作簿对象
     * @return 解析结果
     */
    private static List<MobileArea> parseExcel(Workbook workbook) {
    
       List<MobileArea> resultDataList = new ArrayList<>();
       int tt =0;
        // 解析sheet
        for (int sheetNum = 0; sheetNum < workbook.getNumberOfSheets(); sheetNum++) {
            Sheet sheet = workbook.getSheetAt(sheetNum);

            // 校验sheet是否合法
            if (sheet == null) {
                continue;
            }

            // 获取第一行数据
            int firstRowNum = sheet.getFirstRowNum();
            Row firstRow = sheet.getRow(firstRowNum);
            if (null == firstRow) {
            	System.out.println("在第一行没有读取到任何数据！");
            }

            // 解析每一行的数据，构造数据对象
            int rowStart = firstRowNum + 1;
            int rowEnd = sheet.getPhysicalNumberOfRows();
        	for (int rowNum = rowStart; rowNum < rowEnd; rowNum++) {
                Row row = sheet.getRow(rowNum);

                if (null == row) {
                    continue;
                }

                MobileArea resultData = convertRowToData(row);
                if (null == resultData) {
                    System.out.println("第 " + row.getRowNum() + "行数据不合法，已忽略！");
                    break;
                }
                tt++;
                resultDataList.add(resultData);
            }
            
            
        }
        System.out.println("tt==================="+tt);
        return resultDataList;
    }

    /**
     * 将单元格内容转换为字符串
     * @param cell
     * @return
     */
    private static String convertCellValueToString(Cell cell) {
        if(cell==null){
            return null;
        }
        String returnValue = null;
        switch (cell.getCellType()) {
            case NUMERIC:   //数字
                Double doubleValue = cell.getNumericCellValue();

                // 格式化科学计数法，取一位整数
                DecimalFormat df = new DecimalFormat("0");
                returnValue = df.format(doubleValue);
                break;
            case STRING:    //字符串
                returnValue = cell.getStringCellValue();
                break;
            case BOOLEAN:   //布尔
                Boolean booleanValue = cell.getBooleanCellValue();
                returnValue = booleanValue.toString();
                break;
            case BLANK:     // 空值
                break;
            case FORMULA:   // 公式
                returnValue = cell.getCellFormula();
                break;
            case ERROR:     // 故障
                break;
            default:
                break;
        }
        return returnValue;
    }

    /**
     * 提取每一行中需要的数据，构造成为一个结果数据对象
     *
     * 当该行中有单元格的数据为空或不合法时，忽略该行的数据
     *
     * @param row 行数据
     * @return 解析后的行数据对象，行数据错误时返回null
     */
    private static MobileArea convertRowToData(Row row) {
    	MobileArea resultData = new MobileArea();

        Cell cell;
        int cellNum = 0;
        //prifix
        cell = row.getCell(cellNum++);
        String prifix = convertCellValueToString(cell);
        // MobileNumber
        cell = row.getCell(cellNum++);
        String MobileNumber = convertCellValueToString(cell);
        if(StringUtils.isEmpty(MobileNumber)){
        	return null;
        }
        resultData.setMobileNumber(MobileNumber);
        // Province
        cell = row.getCell(cellNum++);
        String Province = convertCellValueToString(cell);
        
        // City
        cell = row.getCell(cellNum++);
        String City = convertCellValueToString(cell);
       //ISP
        cell = row.getCell(cellNum++);
        String ISP = convertCellValueToString(cell);
        resultData.setMobileType(ISP);
      
      //PostCode
        cell = row.getCell(cellNum++);
        String PostCode = convertCellValueToString(cell);
        resultData.setPostCode(PostCode);
      //AreaCode
        cell = row.getCell(cellNum++);
        String AreaCode = convertCellValueToString(cell);
        resultData.setAreaCode(AreaCode);
      //AdminCode
        cell = row.getCell(cellNum++);
        String AdminCode = convertCellValueToString(cell);
        resultData.setMobileArea(Province+" "+City);
        String provinceCode = getProvinceCode(Province);
        resultData.setProvince_Code(provinceCode);
        return resultData;
    }
   
    private static String getProvinceCode(String province) {
    	String provinceCode = "";
    	switch (province) {
         case "北京":   //数字
        	 provinceCode = "010";
             break;
         case "上海":
        	 provinceCode = "021";
             break;
         case "天津":
        	 provinceCode = "022";
             break;
         case "重庆":
        	 provinceCode = "023";
             break;
         case "山西":
        	 provinceCode = "0351";
             break;
         case "河北":
        	 provinceCode = "0311";
             break;
         case "辽宁":
        	 provinceCode = "024";
             break;
         case "吉林":
        	 provinceCode = "0431";
             break;
         case "黑龙江":
        	 provinceCode = "0451";
             break;
         case "江苏":
        	 provinceCode = "025";
             break;
         case "安徽":
        	 provinceCode = "0551";
             break;
         case "山东":
        	 provinceCode = "0531";
             break;
         case "浙江":
        	 provinceCode = "0571";
             break;
         case "江西":
        	 provinceCode = "0791";
             break;
         case "福建":
        	 provinceCode = "0591";
             break;
         case "湖南":
        	 provinceCode = "0731";
             break;
         case "湖北":
        	 provinceCode = "027";
             break;
         case "河南":
        	 provinceCode = "0371";
             break;
         case "广东":
        	 provinceCode = "020";
             break;
         case "广西":
        	 provinceCode = "0771";
             break;
         case "海南":
        	 provinceCode = "0898";
             break;
         case "贵州":
        	 provinceCode = "0851";
             break;
         case "云南":
        	 provinceCode = "0871";
             break;
         case "四川":
        	 provinceCode = "028";
             break;
         case "陕西":
        	 provinceCode = "029";
             break;
         case "甘肃":
        	 provinceCode = "0931";
             break;
         case "宁夏":
        	 provinceCode = "0951";
             break;
         case "青海":
        	 provinceCode = "0971";
             break;
         case "新疆":
        	 provinceCode = "0991";
             break;
         case "西藏":
        	 provinceCode = "0891";
             break;
         case "内蒙古":
        	 provinceCode = "0471";
             break;
         default:
             break;
     }
		return provinceCode;
	}

    /**
     * 导入手机号归属地用
     * @param args
     * @throws InterruptedException
     * @throws SQLException
     */
	public static void main(String[] args) throws InterruptedException, SQLException {
		System.out.println("=======================");
		List<MobileArea> list = readExcel("C:\\Users\\13401\\Desktop\\拆分\\999.xlsx");
		DBhepler db=new DBhepler();
		db.DataBase();
		ExecutorService service = Executors.newFixedThreadPool(50);
		System.out.println(service);
			for (MobileArea entity : list) {
				service.execute(() -> {
					String sql="INSERT INTO mobile_area (MobileNumber, MobileArea, MobileType, AreaCode, PostCode, Province_Code) VALUES (?, ?, ?, ?, ?, ?)";
					String[] str=new String[]{entity.getMobileNumber(),entity.getMobileArea(),entity.getMobileType(),entity.getAreaCode(),entity.getPostCode(),entity.getProvince_Code()};
					db.AddU(sql, str);
				});
	     
			}
		
		System.out.println(service);
		System.out.println("完成========================");
	}
}
