
package com.guilin.studycode.utils.excel;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;


public class ExcelUtil3 {
    public static List<List<String>> readXlsx(String path) throws IOException {
        InputStream input = new FileInputStream(path);
        List<List<String>> list = readXlsx(input);
        input.close();
        return list;
    }
 
    public static List<List<String>> readXls(String path) throws IOException {
        InputStream input = new FileInputStream(path);
        List<List<String>> list = readXls(input);
        input.close();
        return list;
    }
 
    public static List<List<String>> readXlsx(InputStream input) throws IOException {
        List<List<String>> result = new ArrayList<List<String>>();
        XSSFWorkbook workbook = new XSSFWorkbook(input);
        for (Sheet xssfSheet : workbook) {
            if (xssfSheet == null) {
                continue;
            }
            for (int rowNum = 1; rowNum <= xssfSheet.getLastRowNum(); rowNum++) {
                XSSFRow row = (XSSFRow) xssfSheet.getRow(rowNum);
                int minCellNum = row.getFirstCellNum();
                int maxCellNum = row.getLastCellNum();
                List<String> rowList = new ArrayList<String>();
                for (int i = minCellNum; i < maxCellNum; i++) {
                    XSSFCell cell = row.getCell(i);
                    if (cell == null) {
                        continue;
                    }
                    
                    String cellValue = ""; 
                    switch (cell.getCellType()) {  
                    case NUMERIC: // 数字
                        DecimalFormat df = new DecimalFormat("0");  
                        cellValue = df.format(cell.getNumericCellValue());  
                        break;  
  
                    case STRING: // 字符串
                        cellValue = cell.getStringCellValue();  
                        break;  
                    }
                    
                    rowList.add(cellValue);
                }
                result.add(rowList);
            }
        }
        return result;
    }
 
    public static List<List<String>> readXls(InputStream input,int indexSheet) throws IOException {
        List<List<String>> result = new ArrayList<List<String>>();
        HSSFWorkbook workbook = new HSSFWorkbook(input);
        if ( workbook.getNumberOfSheets()>0) {
            HSSFSheet sheet = workbook.getSheetAt(indexSheet);
            if (sheet != null) {
            
	            for (int rowNum = 1; rowNum <= sheet.getLastRowNum(); rowNum++) {
	                HSSFRow row = sheet.getRow(rowNum);
	                int minCellNum = row.getFirstCellNum();
	                int maxCellNum = row.getLastCellNum();
	                List<String> rowList = new ArrayList<String>();
	                for (int i = minCellNum; i < maxCellNum; i++) {
	                    HSSFCell cell = row.getCell(i);
	                    if (cell == null) {
	                        continue;
	                    }
	                    rowList.add(getStringVal(cell));
	                }
	                result.add(rowList);
	            }
           }
        }
        return result;
    }
    public static List<List<String>> readXlsx(InputStream input,int indexSheet) throws IOException {
    	List<List<String>> result = new ArrayList<List<String>>();
    	XSSFWorkbook workbook = new XSSFWorkbook(input);
    	if (workbook.getNumberOfSheets()>0) {
    		XSSFSheet xssfSheet = workbook.getSheetAt(indexSheet);
    		for (int rowNum = 1; rowNum <= xssfSheet.getLastRowNum(); rowNum++) {
    			XSSFRow row = xssfSheet.getRow(rowNum);
    			int minCellNum = row.getFirstCellNum();
    			int maxCellNum = row.getLastCellNum();
    			List<String> rowList = new ArrayList<String>();
    			for (int i = minCellNum; i < maxCellNum; i++) {
    				XSSFCell cell = row.getCell(i);
    				if (cell == null) {
    					continue;
    				}
    				
    				String cellValue = ""; 
    				switch (cell.getCellType()) {  
    				case NUMERIC: // 数字
    					DecimalFormat df = new DecimalFormat("0");  
    					cellValue = df.format(cell.getNumericCellValue());  
    					break;  
    					
    				case STRING: // 字符串
    					cellValue = cell.getStringCellValue();  
    					break;  
    				}
    				
    				rowList.add(cellValue);
    			}
    			result.add(rowList);
    		}
    	}
    	return result;
    }
    
    public static List<List<String>> readXls(InputStream input) throws IOException {
        List<List<String>> result = new ArrayList<List<String>>();
        HSSFWorkbook workbook = new HSSFWorkbook(input);
        for (int numSheet = 0; numSheet < workbook.getNumberOfSheets(); numSheet++) {
            HSSFSheet sheet = workbook.getSheetAt(numSheet);
            if (sheet == null) {
                continue;
            }
            for (int rowNum = 1; rowNum <= sheet.getLastRowNum(); rowNum++) {
                HSSFRow row = sheet.getRow(rowNum);
                if (row != null){
                    int minCellNum = row.getFirstCellNum();
                    int maxCellNum = row.getLastCellNum();
                    List<String> rowList = new ArrayList<String>();
                    for (int i = minCellNum; i < maxCellNum; i++) {
                        HSSFCell cell = row.getCell(i);
                        if (cell == null) {
                            continue;
                        }
                        rowList.add(getStringVal(cell));
                    }
                    result.add(rowList);
                }
            }
        }
        return result;
    }
 
    private static String getStringVal(HSSFCell cell) {
        CellType cellType = cell.getCellType();
        switch (cellType) {
            case BOOLEAN:
                return cell.getBooleanCellValue() ? "TRUE" : "FALSE";
            case FORMULA:
                return cell.getCellFormula();
            case NUMERIC:
//                cell.setCellType(Cell.CELL_TYPE_STRING);
//                NumberToTextConverter.toText(cell.getNumericCellValue());
            	// 数字格式数据转文本
                return NumberToTextConverter.toText(cell.getNumericCellValue());
            case STRING:
                return cell.getStringCellValue();
            default:
                return null;
        }
    }
 
}