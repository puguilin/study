package com.guilin.studycode.utils;


import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.*;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * EXCEL工具类
 *
 * @date:2021-10-19
 */
public class ReadExcelUtil {
    /**
     * 获取单元格的值
     *
     * @param cell
     * @return
     */
//    public static String getCellValue(Cell cell){
//        if(cell == null) return "";
//        if(cell.getCellType() == Cell.CELL_TYPE_STRING){
//            return cell.getStringCellValue();
//        }else if(cell.getCellType() == Cell.CELL_TYPE_BOOLEAN){
//            return String.valueOf(cell.getBooleanCellValue());
//        }else if(cell.getCellType() == Cell.CELL_TYPE_FORMULA){
//            return cell.getCellFormula() ;
//        }else if(cell.getCellType() == Cell.CELL_TYPE_NUMERIC){
//            return String.valueOf(cell.getNumericCellValue());
//        }
//        return "";
//    }

    /**
     * 获取单元格的值
     *
     * @param cell
     * @return
     */
    public static String getCellValue(Cell cell) {
        String cellValue = "";
        if (cell == null) {
            return cellValue;
        }
        //把数字转换成string，防止12.0这种情况
        if (cell.getCellType() == CellType.NUMERIC) {
            cell.setCellType(CellType.STRING);
        }
        //判断数据的类型
        switch (cell.getCellType()) {

            case NUMERIC: //数字0
                cellValue = String.valueOf(cell.getNumericCellValue());
                break;
            case STRING: //字符串1
                cellValue = String.valueOf(cell.getStringCellValue());
                break;
            case BOOLEAN: //Boolean
                cellValue = String.valueOf(cell.getBooleanCellValue());
                break;
            case FORMULA: //公式
                try {
                    cellValue = String.valueOf(cell.getNumericCellValue());
                } catch (IllegalStateException e) {
                    cellValue = String.valueOf(cell.getRichStringCellValue());
                }
                break;
            case BLANK: //空值
                cellValue = "";
                break;
            case ERROR: //故障
                cellValue = "非法字符";
                break;
            default:
                cellValue = "未知类型";
                break;
        }
        return cellValue;
    }


    /**
     * 合并单元格处理,获取合并行
     *
     * @param sheet
     * @return List<CellRangeAddress>
     */
    public static List<CellRangeAddress> getCombineCell(Sheet sheet) {
        List<CellRangeAddress> list = new ArrayList<CellRangeAddress>();
        //获得一个 sheet 中合并单元格的数量
        int sheetmergerCount = sheet.getNumMergedRegions();
        //遍历所有的合并单元格
        for (int i = 0; i < sheetmergerCount; i++) {
            //获得合并单元格保存进list中
            CellRangeAddress ca = sheet.getMergedRegion(i);
            list.add(ca);
        }
        return list;
    }

    /**
     * 获取行数
     *
     * @param listCombineCell
     * @param cell
     * @param sheet
     * @return
     */
    public static int getRowNum(List<CellRangeAddress> listCombineCell, Cell cell, Sheet sheet) {
        int xr = 0;
        int firstC = 0;
        int lastC = 0;
        int firstR = 0;
        int lastR = 0;
        for (CellRangeAddress ca : listCombineCell) {
            //获得合并单元格的起始行, 结束行, 起始列, 结束列
            firstC = ca.getFirstColumn();
            lastC = ca.getLastColumn();
            firstR = ca.getFirstRow();
            lastR = ca.getLastRow();
            if (cell.getRowIndex() >= firstR && cell.getRowIndex() <= lastR) {
                if (cell.getColumnIndex() >= firstC && cell.getColumnIndex() <= lastC) {
                    xr = lastR;
                }
            }
        }
        return xr;
    }

    /**
     * 判断单元格是否为合并单元格，是的话则将单元格的值返回
     *
     * @param listCombineCell 存放合并单元格的list
     * @param cell            需要判断的单元格
     * @param sheet           sheet
     * @return
     */
    public String isCombineCell(List<CellRangeAddress> listCombineCell, Cell cell, Sheet sheet)
            throws Exception {
        int firstC = 0;
        int lastC = 0;
        int firstR = 0;
        int lastR = 0;
        String cellValue = null;
        for (CellRangeAddress ca : listCombineCell) {
            //获得合并单元格的起始行, 结束行, 起始列, 结束列
            firstC = ca.getFirstColumn();
            lastC = ca.getLastColumn();
            firstR = ca.getFirstRow();
            lastR = ca.getLastRow();
            if (cell.getRowIndex() >= firstR && cell.getRowIndex() <= lastR) {
                if (cell.getColumnIndex() >= firstC && cell.getColumnIndex() <= lastC) {
                    Row fRow = sheet.getRow(firstR);
                    Cell fCell = fRow.getCell(firstC);
                    cellValue = getCellValue(fCell);
                    break;
                }
            } else {
                cellValue = "";
            }
        }
        return cellValue;
    }

    /**
     * 获取合并单元格的值
     *
     * @param sheet
     * @param row
     * @param column
     * @return
     */
    public String getMergedRegionValue(Sheet sheet, int row, int column) {
        int sheetMergeCount = sheet.getNumMergedRegions();
        for (int i = 0; i < sheetMergeCount; i++) {
            CellRangeAddress ca = sheet.getMergedRegion(i);
            int firstColumn = ca.getFirstColumn();
            int lastColumn = ca.getLastColumn();
            int firstRow = ca.getFirstRow();
            int lastRow = ca.getLastRow();
            if (row >= firstRow && row <= lastRow) {
                if (column >= firstColumn && column <= lastColumn) {
                    Row fRow = sheet.getRow(firstRow);
                    Cell fCell = fRow.getCell(firstColumn);
                    return getCellValue(fCell);
                }
            }
        }
        return null;
    }

    /**
     * 判断指定的单元格是否是合并单元格
     *
     * @param sheet
     * @param row    行下标
     * @param column 列下标
     * @return
     */
    public static boolean isMergedRegion(Sheet sheet, int row, int column) {
        int sheetMergeCount = sheet.getNumMergedRegions();
        for (int i = 0; i < sheetMergeCount; i++) {
            CellRangeAddress range = sheet.getMergedRegion(i);
            int firstColumn = range.getFirstColumn();
            int lastColumn = range.getLastColumn();
            int firstRow = range.getFirstRow();
            int lastRow = range.getLastRow();
            if (row >= firstRow && row <= lastRow) {
                if (column >= firstColumn && column <= lastColumn) {
                    return true;
                }
            }
        }
        return false;
    }


    /**
     * 导出Excel
     *
     * @param sheetName       表格 sheet 的名称
     * @param headers         标题名称
     * @param dataList        需要显示的数据集合
     * @param exportExcelName 导出excel文件的名字
     */
    public static void exportExcel(String sheetName, List<Map<String, Object>> dataList,
                                   String[] headers, String exportExcelName, String path) {

        // 声明一个工作薄
        XSSFWorkbook workbook = new XSSFWorkbook();
        // 生成一个表格
        XSSFSheet sheet = workbook.createSheet(sheetName);
        // 设置表格默认列宽度为15个字节
        sheet.setDefaultColumnWidth(15);

        // 生成表格中非标题栏的样式
        XSSFCellStyle style = workbook.createCellStyle();
        // 设置这些样式
        style.setFillForegroundColor(IndexedColors.WHITE.getIndex());//背景色
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setAlignment(HorizontalAlignment.CENTER);
        // 生成表格中非标题栏的字体
        XSSFFont font = workbook.createFont();
        font.setColor(IndexedColors.BLACK.getIndex());
        font.setFontHeightInPoints((short) 12);
        font.setBold(true);
        // 把字体应用到当前的样式
        style.setFont(font);
        // 设置表格标题栏的样式
        XSSFCellStyle titleStyle = workbook.createCellStyle();
        titleStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        titleStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        titleStyle.setBorderBottom(BorderStyle.THIN);
        titleStyle.setBorderLeft(BorderStyle.THIN);
        titleStyle.setBorderRight(BorderStyle.THIN);
        titleStyle.setBorderTop(BorderStyle.THIN);
        titleStyle.setAlignment(HorizontalAlignment.CENTER);
        titleStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        // 设置标题栏字体
        XSSFFont titleFont = workbook.createFont();
        titleFont.setColor(IndexedColors.WHITE.getIndex());
        titleFont.setFontHeightInPoints((short) 12);
        titleFont.setBold(true);
        // 把字体应用到当前的样式
        titleStyle.setFont(titleFont);
        // 产生表格标题行
        XSSFRow row = sheet.createRow(0);
        for (short i = 0; i < headers.length; i++) {
            XSSFCell cell = row.createCell(i);
            cell.setCellStyle(titleStyle);
            XSSFRichTextString text = new XSSFRichTextString(headers[i]);
            cell.setCellValue(text);
        }
        // 遍历集合数据，产生数据行
        Iterator<Map<String, Object>> it = dataList.iterator();
        int index = 0;
        while (it.hasNext()) {
            index++;
            row = sheet.createRow(index);
            Map<String, Object> data = it.next();
            int i = 0;
            for (String key : data.keySet()) {
                XSSFCell cell = row.createCell(i);
                cell.setCellStyle(style);
                XSSFRichTextString text = new XSSFRichTextString(data.get(key) + "");
                cell.setCellValue(text);
                i++;
            }
        }
        OutputStream out = null;
        try {
            String tmpPath = "G:\\excel\\" + exportExcelName + ".xlsx";
            out = new FileOutputStream(tmpPath);
            workbook.write(out);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (workbook != null) {
                try {
                    workbook.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
