package com.guilin.studycode.utils;

import java.io.*;
import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.guilin.studycode.entrity.Student;
import com.sun.media.sound.InvalidFormatException;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * exce工具类
 * 
 * @author
 * @version 2014-08-06
 */
public class ExcelTool {

    private final static Logger logger = LoggerFactory.getLogger(ExcelTool.class);

    private Workbook wb     = null;
    private SimpleDateFormat    sdf    = new SimpleDateFormat("yyyyMMdd");

    public ExcelTool(String path) {
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(path);
            wb = WorkbookFactory.create(inputStream);
        } catch (FileNotFoundException e) {
            logger.error("文件没有找到，赶快确认！" + e.getMessage(), e);
            throw new RuntimeException(e);
        } catch (InvalidFormatException e) {
            logger.error("流出现问题" + e.getMessage(), e);
            throw new RuntimeException(e);
        } catch (IOException e) {
            logger.error("流出现异常，尽快确认！" + e.getMessage(), e);
            throw new RuntimeException(e);
        } finally {
            closeInputStream(inputStream);
        }
    }


   /* public ExcelTool(FileOutputStream out) {
        try {
            wb = WorkbookFactory.create(out);
        } catch (FileNotFoundException e) {
            logger.error("文件没有找到，赶快确认！" + e.getMessage(), e);
            throw new RuntimeException(e);
        } catch (InvalidFormatException e) {
            logger.error("流出现问题" + e.getMessage(), e);
            throw new RuntimeException(e);
        } catch (IOException e) {
            logger.error("流出现异常，尽快确认！");
            throw new RuntimeException(e);
        }
    }

    public ExcelTool(InputStream in) {
        try {
            wb = WorkbookFactory.create(in);
        } catch (FileNotFoundException e) {
            logger.error("文件没有找到，赶快确认！" + e.getMessage(), e);
            throw new RuntimeException(e);
        } catch (InvalidFormatException e) {
            logger.error("流出现问题" + e.getMessage(), e);
            throw new RuntimeException(e);
        } catch (IOException e) {
            logger.error("流出现异常，尽快确认！");
            throw new RuntimeException(e);
        } finally {
            closeInputStream(in);
        }
    }*/

    /**
     * 读取文件
     * 
     * @param c
     * @param fileNames
     * @param sheetIndex 读取第几个sheetIndex
     * @param spkit 跳过多少行
     * @return
     */
    public <T> FileParseResult<T> getExcelData(Class<T> c, String[] fileNames, int sheetIndex, int spkit) {
        List<String[]> list = new ArrayList<String[]>();
        FileParseResult<T> result = new FileParseResult<T>();
        int columnNum = 0;
        Row row = null;
        String value = null;
        Sheet sheet = wb.getSheetAt(sheetIndex);
        if (sheet.getRow(0) != null) {
            columnNum = sheet.getRow(0).getLastCellNum() - sheet.getRow(0).getFirstCellNum();
        }
        int rowNum = sheet.getLastRowNum();//总行数
        if (rowNum == 0) {
            logger.warn("读取的文件为空！");
            throw new RuntimeException("要读取的文件为空，请确认一遍！解析的文件为EXCEL文件。方法为：getExcelData");
        }
        if (spkit > rowNum + 1) {
            logger.warn("读取的文件为空！");
            throw new RuntimeException("要读取的文件为空，请确认一遍！解析的文件为EXCEL文件。方法为：getExcelData");
        }
        if (fileNames.length != columnNum) {
            logger.info("要赋值的列和导入的文件列不相同请检查！解析的文件为EXCEL文件需要的长度为：" + fileNames.length + ";实际的列长度为：" + columnNum);
            throw new RuntimeException("要赋值的列和导入的文件列不相同请检查！解析的文件为EXCEL文件需要的长度为：" + fileNames.length + ";实际的列长度为："
                    + columnNum);
        }

        if (columnNum > 0) {
            for (int k = spkit; k <= rowNum; k++) {

                row = sheet.getRow(k);
                if (null == row) {
                    break;
                }
                String[] singleRow = new String[columnNum];
                int n = 0;
                for (int i = 0; i < columnNum; i++) {
                    org.apache.poi.ss.usermodel.Cell cell1 = row.getCell(i, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                    CellType cellType = cell1.getCellType();
                    switch (cellType) {
                       case BLANK: //空值
                            singleRow[n] = "";
                            break;
                        case BOOLEAN: // BOOLEAN
                            singleRow[n] = Boolean.toString(cell1.getBooleanCellValue());
                            break;
                        //数值
                        case NUMERIC: //数值
                            if (DateUtil.isCellDateFormatted(cell1)) {
                                singleRow[n] = String.valueOf(cell1.getDateCellValue());
                            } else {
                                cell1.setCellType(CellType.STRING);
                                String temp = cell1.getStringCellValue();
                                if (StringUtils.isBlank(temp)) {
                                    temp = "";
                                }
                                // 则转换为BigDecimal类型的字符串 
                                if (temp.indexOf(".") > -1) {
                                    singleRow[n] = String.valueOf(new Double(temp)).trim();
                                } else {
                                    singleRow[n] = temp.trim();
                                }
                            }
                            break;
                        case STRING: // 字符串类型
                            singleRow[n] = cell1.getStringCellValue().trim();
                            break;
                        case ERROR:  // 错误
                            singleRow[n] = "";
                            break;
                        case FORMULA: //公式型
                            cell1.setCellType(CellType.FORMULA);
                            singleRow[n] = cell1.getStringCellValue();
                            if (value != null) {
                                singleRow[n] = value.replaceAll("#N/A", "").trim();
                            }
                            break;
                        default:
                            singleRow[n] = "";
                            break;
                    }
                    n++;
                }
                if (isEmpty(singleRow)) {//检测到第一个空白行，则停止解析。
                    break;
                }
                list.add(singleRow);

            }
        }
        if (list.size() > 0) {
            try {
                return convertDataToDTO(c, list, fileNames);
            } catch (Exception e) {
                logger.error("出现异常，尽快确认！" + e.getMessage(), e);
            }
        }
        return result;
    }

    private boolean isEmpty(String[] attr) {
        if (attr == null || attr.length == 0) {
            return true;
        }

        for (String ele : attr) {
            if (StringUtils.isNotBlank(ele)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 导出excel
     * 
     * @param c
     * @param list
     * @param feilds
     */
    public static <T> FileOutputStream exportExcel(
            Class<T> c, List<T> list, String[] feilds, FileOutputStream fileOut,List<String> head ) {
        T obj;
        try {
            obj = c.newInstance();
        } catch (InstantiationException e1) {
            logger.error("转换对象失败。。。" + e1.getMessage(), e1);
            throw new RuntimeException(e1);
        } catch (IllegalAccessException e1) {
            logger.error("转换对象失败。。。" + e1.getMessage(), e1);
            throw new RuntimeException(e1);
        }
        int listLen = list.size();
        if (listLen == 0) {
            logger.warn("要导出的数据为空，请确认！");
        }

        List<List<String[]>> tempRest = new ArrayList<>();
        List<String[]> temp = null;
        int length = feilds.length;
        try {
            for (int i = 0; i < listLen; i++) {
                obj = list.get(i);
                temp = new ArrayList<>();
                for (int j = 0; j < length; j++) {
                    String[] arr = new String[length];
                    Field field = c.getDeclaredField(feilds[j]);
                    field.setAccessible(true);
                    Object val = field.get(obj);
                    if ("java.util.Date".equals(field.getType().getName())) {
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd ");
                        String date = sdf.format(val);
                        arr[j] = date;
                    } else if ("int".equals(field.getType().getName())) {
                        arr[j] =  val.toString();
                    } else {
                        arr[j] = (String) val;
                    }
                    temp.add(arr);
                }
                tempRest.add(temp);
            }
        } catch (Exception e) {
            logger.error("message:" + e.getMessage(), e);
            throw new RuntimeException(e);
        }

        HSSFWorkbook wb = new HSSFWorkbook();// 产生工作薄对象
        Sheet sheet = wb.createSheet();// 产生工作表对象
        wb.setSheetName(0,"测试"); // 设置工作表的名称.


        // POI 升级到高版本后 https://blog.csdn.net/qh870754310/article/details/85685505


/*
        Font font = wb.createFont();// 设置字体
        font.setFontName("黑体");
        font.setFontHeightInPoints((short) 18);// 设置字体大小
        font.setBold(true);// 字体加粗  font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD); // 字体加粗  font.setBold(true)

        CellStyle headstyle = wb.createCellStyle();
        headstyle.setFont(font);
        headstyle.setAlignment(HorizontalAlignment.CENTER);// 左右居中 HSSFCellStyle.ALIGN_CENTER   HorizontalAlignment.CENTER
        headstyle.setVerticalAlignment(VerticalAlignment.CENTER);// 上下居中 HSSFCellStyle.VERTICAL_CENTER   VerticalAlignment.CENTER
        //设置CELL格式为文本格式
        DataFormat format = wb.createDataFormat();
        headstyle.setDataFormat(format.getFormat("@"));
*/


        Row row1 = sheet.createRow(0);

        for(int n= 0 ;n<length;n++){
            // 设置表头列名的格式
            Cell cell = row1.createCell(n);
          /*  Font font1 = wb.createFont();// 设置字体
            font1.setFontName("黑体");
            font1.setFontHeightInPoints((short) 12);// 设置字体大小
            font1.setBold(true);// 字体加粗
            cell.getCellStyle().setFont(font1);*/
            CellStyle cellStyle = cell.getCellStyle();
            //水平居中
            cellStyle.setAlignment(HorizontalAlignment.CENTER);
            //垂直居中
            cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            cell.setCellValue(head.get(n));

        }

        for (int i = 0; i < listLen; i++) {
            //每一行的值
            List<String[]> strings = tempRest.get(i);

            //因为加了表头所以从第二行起
            Row row = sheet.createRow(i+1);

            for (int j = 0; j < length; j++) {
                String[] arr = strings.get(j);
                Cell cell  = row.createCell(j);//创建列
                if (cell == null) {
                    cell = row.createCell(j);
                }
                cell.setCellValue(arr[j]);
            }
        }
        ByteArrayOutputStream out = null;
        ByteArrayInputStream in = null;
        try {
            fileOut.flush();
            wb.write(fileOut);
            fileOut.close();
        } catch (IOException e) {
            logger.error("流出现异常，尽快确认！");
        } finally {
            if (null != fileOut) {
                try {
                    fileOut.close();
                } catch (IOException e) {
                    logger.error("关闭流异常。。。：" + e.getMessage(), e);
                }
            }
        }
        return fileOut;
    }

    /**
     * 往Excel的指定列写值
     * 
     * @param sheetIndex 第几个工作簿
     * @param spkit 跳过行数
     * @param find 根据第几列来查查找
     * @param map
     */
    public <T> InputStream writeExcel(int sheetIndex, int spkit, int find, Map<Integer, Map<Integer, String>> map) {
        Row row = null;
        int columnNum = 0;
        Sheet sheet = wb.getSheetAt(sheetIndex);
        if (sheet.getRow(0) != null) {
            columnNum = sheet.getRow(0).getLastCellNum() - sheet.getRow(0).getFirstCellNum();
        }
        int rowNum = sheet.getLastRowNum();//总行数
        if (rowNum == 0) {
            logger.error("读取的文件为空！");
            throw new RuntimeException("写入的文件不正确，请检查！要写入的文件为EXCEL文件,方法为：writeExcel");
        }
        if (columnNum > 0) {
            for (int i = spkit; i <= rowNum; i++) {
                row = sheet.getRow(i);//第几行
                if (null == row) {
                    break;
                }
                for (int j = spkit; j <= rowNum; j++) {
                    for (Map.Entry<Integer, Map<Integer, String>> entry : map.entrySet()) {
                        Cell cell = row.getCell(find, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                        String keyValue = null;
                        switch (cell.getCellType()) {
                            case BLANK:
                                keyValue = "";
                                break;
                            case BOOLEAN:
                                keyValue = Boolean.toString(cell.getBooleanCellValue());
                                break;
                            //数值  
                            case NUMERIC:
                                if (DateUtil.isCellDateFormatted(cell)) {
                                    keyValue = String.valueOf(cell.getDateCellValue());
                                } else {
                                    cell.setCellType(CellType.NUMERIC);
                                    String temp = cell.getStringCellValue();
                                    //判断是否包含小数点，如果不含小数点，则以字符串读取，如果含小数点，则转换为Double类型的字符串  
                                    if (temp.indexOf(".") > -1) {
                                        keyValue = String.valueOf(new Double(temp)).trim();
                                    } else {
                                        keyValue = temp.trim();
                                    }
                                }
                                break;
                            case STRING:
                                keyValue = cell.getStringCellValue().trim();
                                break;
                            case ERROR:
                                keyValue = "";
                                break;
                            case FORMULA:
                                cell.setCellType(CellType.FORMULA);
                                keyValue = cell.getStringCellValue();
                                if (keyValue != null) {
                                    keyValue = keyValue.replaceAll("#N/A", "").trim();
                                }
                                break;
                            default:
                                keyValue = "";
                                break;
                        }
                        if (null != keyValue && entry.getKey().toString().equals(keyValue)) {
                            for (Map.Entry<Integer, String> entry2 : entry.getValue().entrySet()) {
                                cell = row.getCell(entry2.getKey());//该行的列cell
                                if (cell == null) {
                                    cell = row.createCell(entry2.getKey());
                                }
                                try {
                                    cell.setCellValue(entry2.getValue());
                                } catch (Exception e) {
                                    logger.error("写入数据失败！" + e.getMessage(), e);
                                    throw new RuntimeException("生产保单号失败！" + e.getMessage(), e);
                                }
                            }
                        }
                    }
                }
            }
        }
        ByteArrayOutputStream out = null;
        ByteArrayInputStream in = null;
        try {
            out = new ByteArrayOutputStream();
            wb.write(out);
            out.flush();
            in = new ByteArrayInputStream(out.toByteArray());
        } catch (IOException e) {
            logger.error("流出现异常，尽快确认！message:" + e.getMessage(), e);
            throw new RuntimeException(e);
        } finally {
            if (null != out) {
                try {
                    out.close();
                } catch (IOException e) {
                    logger.error("关闭流失败..." + e.getMessage(), e);
                }
            }
        }
        return in;
    }

    /**
     * 往Excel固定列写值
     * 
     * @param list 要写入的值
     * @param sheetIndex 第几个工作簿
     * @param spkit 跳过行数
     * @param map key:写入的列数 ，value:str[0]，str[1],str[3]:需要回写的列
     */

      public <T> InputStream writeExcel(Class<T> c, List<T> list, int sheetIndex, int spkit, Map<Integer, String[]> map) {
          Row row = null;
          int columnNum = 0;
          Sheet sheet = wb.getSheetAt(sheetIndex);
          if (sheet.getRow(0) != null) {
              columnNum = sheet.getRow(0).getLastCellNum() - sheet.getRow(0).getFirstCellNum();
          }
          int rowNum = sheet.getLastRowNum();
          //总行数
          if (rowNum == 0) {
              logger.error("读取的文件为空！");
              throw new RuntimeException("写入的文件不正确，请检查！要写入的文件为EXCEL文件,方法为：writeExcel");
            }
          String[] bean = null;
          if (columnNum > 0) {
              for (int i = spkit; i <= rowNum; i++) {
                  row = sheet.getRow(i);
                  //第几行
                  if (null == row) {
                      break;
                  }
                  for (int j = spkit; j <= rowNum; j++) {
                      for (Map.Entry<Integer, String[]> entry : map.entrySet()) {
                          bean = entry.getValue();
                          String ticketNo = row.getCell(Integer.parseInt(bean[2])).getStringCellValue();
                          Cell cell = row.getCell(entry.getKey());
                          //该行的列cell
                          if (cell == null) {
                              cell = row.createCell(entry.getKey());
                          } try {
                              cell.setCellValue(findValueFromList(c, list, ticketNo, bean[0], bean[1]));
                          } catch (Exception e) {
                              logger.error("写入数据失败！" + e.getMessage(), e);
                              throw new RuntimeException("生产保单号失败！" + e.getMessage(), e);
                          }
                      }
                  }
             }
          }
          ByteArrayOutputStream out = null;
          ByteArrayInputStream in = null;
          try {
              out = new ByteArrayOutputStream();
              wb.write(out);
              out.flush();
              in = new ByteArrayInputStream(out.toByteArray());
          } catch (IOException e) {
                logger.error("流出现异常，尽快确认！message:" + e.getMessage(), e);
                throw new RuntimeException(e);
          } finally {
              if (null != out) {
                  try {
                      out.close();
                  } catch (IOException e) {
                      logger.error("关闭流失败..." + e.getMessage(), e);
                  }
              }
          }
          return in;
      }

    /**
     * 从目标list里面查找出需要的内容
     * 
     * @param c
     * @param list 要查找的目标list
     * @param find 用于查找的值
     * @param src 用于查找的字段
     * @param target 要查找的字段Id
     * @return
     * @throws IllegalAccessException
     * @throws InstantiationException
     * @throws NoSuchFieldException
     * @throws SecurityException
     */
     private <T> String findValueFromList(Class<T> c, List<T> list, String find, String src, String target) {
         String result = "";
         int length = list.size();
         T obj;
         try {
             obj = c.newInstance();
         } catch (InstantiationException e1) {
             logger.error("转换对象失败。。。" + e1.getMessage(), e1);
             throw new RuntimeException(e1);
         } catch (IllegalAccessException e1) {
             logger.error("转换对象失败。。。" + e1.getMessage(), e1);
             throw new RuntimeException(e1);
         } try {
             for (int i = 0; i < length; i++) {
                 obj = list.get(i);
                 Field field = c.getDeclaredField(src);
                 field.setAccessible(true);
                 String val = (String) field.get(obj);
                 if (find.equals(val)) {
                        field = c.getDeclaredField(target);
                        field.setAccessible(true);
                        result = (String) field.get(obj);
                        break;
                 }
             }
         } catch (Exception e) {
             logger.error("赋值失败。。。" + e.getMessage(), e);
             throw new RuntimeException(e);
         }
         return result;
     }


    private <T> FileParseResult<T> convertDataToDTO(Class<T> c, List<String[]> dataList, String[] fileNames)
            throws InstantiationException, IllegalAccessException {
        FileParseResult<T> result = new FileParseResult<T>();
        List<T> temp = new ArrayList<T>();
        int dataLength = dataList.size();
        T obj = null;

        StringBuffer errorDesc = new StringBuffer("");
        for (int i = 0; i < dataLength; i++) {
            try {
                obj = c.newInstance();
            } catch (InstantiationException e) {
                logger.error("出现异常，尽快确认！message:" + e.getMessage(), e);
            } catch (IllegalAccessException e) {
                logger.error("出现异常，尽快确认！message:" + e.getMessage(), e);
            }
            String[] data = dataList.get(i);
            boolean errorFlag = false;
            for (int j = 0; j < fileNames.length; j++) {
                try {
                    Field field = c.getDeclaredField(fileNames[j]);
                    field.setAccessible(true);
                    if ("java.util.Date".equals(field.getType().getName())) {
                        field.set(obj, data[j] == null ? null : sdf.parse(data[j]));
                    } else if ("java.lang.String".equals(field.getType().getName())) {
                        field.set(obj, data[j]);
                    }
                } catch (IllegalAccessException e) {
                    int row = i + 1;
                    int col = j + 1;
                    errorDesc.append("第" + row + "行" + col + "列数格式错误;");
                    logger.error("出现异常，尽快确认！");
                    errorFlag = true;
                    continue;
                } catch (SecurityException e) {
                    int row = i + 1;
                    int col = j + 1;
                    errorDesc.append("第" + row + "行" + col + "列数格式错误;");
                    logger.error("出现异常，尽快确认！");
                    errorFlag = true;
                    continue;
                } catch (NoSuchFieldException e) {
                    int row = i + 1;
                    int col = j + 1;
                    errorDesc.append("第" + row + "行" + col + "列数格式错误;");
                    logger.error("出现异常，尽快确认！");
                    errorFlag = true;
                    continue;
                } catch (IllegalArgumentException e) {
                    int row = i + 1;
                    int col = j + 1;
                    errorDesc.append("第" + row + "行" + col + "列数格式错误;");
                    logger.error("出现异常，尽快确认！");
                    errorFlag = true;
                    continue;
                } catch (ParseException e) {
                    int row = i + 1;
                    int col = j + 1;
                    errorDesc.append("第" + row + "行" + col + "列数格式错误;");
                    logger.error("出现异常，尽快确认！");
                    errorFlag = true;
                    continue;
                }
            }
            if (!errorFlag) {
                temp.add(obj);
            }
        }
        result.setErrorDesc(errorDesc.toString());
        result.setResultList(temp);
        result.setTotal(dataLength);
        result.setSuccess(true);
        return result;
    }


    /**
     * @param sheetIndex:
     * @return List<String>
     * @author guilin
     * @description 获取所有的列
     * @date 2022/3/11 14:23
     */
/*    public List<String[]> getAllData(int sheetIndex) {
        FileParseResult<T> result = new FileParseResult<T>();
        // T obj = c.newInstance();
        int columnNum = 0;
        Sheet sheet = wb.getSheetAt(sheetIndex);
        if (sheet.getRow(0) != null) {
            columnNum = sheet.getRow(0).getLastCellNum() - sheet.getRow(0).getFirstCellNum();
        }

        int rowNum = sheet.getLastRowNum();//总行数
        if (columnNum > 0) {
            for (int k = 1; k <= rowNum; k++) {
                Row row = sheet.getRow(k);
                String[] singleRow = new String[columnNum];
                int n = 0;
                for (int i = 0; i < columnNum; i++) {
                    Cell cell = row.getCell(i, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                    switch (cell.getCellType()) {
                        case BLANK:
                            singleRow[n] = "";
                            break;
                        case BOOLEAN:
                            singleRow[n] = Boolean.toString(cell.getBooleanCellValue());
                            break;
                        //数值
                        case NUMERIC:
                            if (DateUtil.isCellDateFormatted(cell)) {
                                singleRow[n] = String.valueOf(cell.getDateCellValue());
                            } else {
                                cell.setCellType(CellType.STRING);
                                String temp = cell.getStringCellValue();
                                //判断是否包含小数点，如果不含小数点，则以字符串读取，如果含小数点，则转换为Double类型的字符串
                                if (temp.indexOf(".") > -1) {
                                    singleRow[n] = String.valueOf(new Double(temp)).trim();
                                } else {
                                    singleRow[n] = temp.trim();
                                }
                            }
                            break;
                        case STRING:
                            singleRow[n] = cell.getStringCellValue().trim();
                            break;
                        case ERROR:
                            singleRow[n] = "";
                            break;
                        case FORMULA:
                            cell.setCellType(CellType.STRING);
                            singleRow[n] = cell.getStringCellValue();
                            if (singleRow[n] != null) {
                                singleRow[n] = singleRow[n].replaceAll("#N/A", "").trim();
                            }
                            break;
                        default:
                            singleRow[n] = "";
                            break;
                    }
                    n++;
                }
                if ("".equals(singleRow[0])) {
                    continue;
                }//如果第一行为空，跳过
                dataList.add(singleRow);
            }
        }

        return dataList;
    }*/


    /**
     * 返回Excel最大行index值，实际行数要加1
     * 
     * @return
     */
       private int getRowNum(int sheetIndex) {
           Sheet sheet = wb.getSheetAt(sheetIndex);
           return sheet.getLastRowNum();
       }

    /**
     * 返回数据的列数
     * 
     * @return
     */
      private int getColumnNum(int sheetIndex) {
          Sheet sheet = wb.getSheetAt(sheetIndex);
          Row row = sheet.getRow(0);
          if (row != null && row.getLastCellNum() > 0) {
              return row.getLastCellNum();
          }
          return 0;
      }

    /**
     * 获取某一行数据
     * 
     * @param rowIndex 计数从0开始，rowIndex为0代表header行
     * @return
     */
   /*    public String[] getRowData(int sheetIndex, int rowIndex) {
           String[] dataArray = null;
           if (rowIndex > this.getColumnNum(sheetIndex)) {
               return dataArray;
           } else {
               dataArray = new String[this.getColumnNum(sheetIndex)];
               return this.dataList.get(rowIndex);
           }
       }*/

    /**
     * 关闭输入流
     * 
     * @param in
     */
    private void closeInputStream(InputStream in) {
        close(in, null);
    }

    private void close(InputStream in, OutputStream out) {
        try {
            if (null != in) {
                in.close();
            }
            if (null != out) {
                out.close();
            }
        } catch (Exception e) {
            logger.error("关闭流失败。。" + e.getMessage(), e);
        }
    }

    /**
     * 获取某一列数据
     * 
     * @param
     * @return
     */
    /*   public String[] getColumnData(int sheetIndex, int colIndex) {
           String[] dataArray = null;
           if (colIndex > this.getColumnNum(sheetIndex)) {
               return dataArray;
           } else {
               if (this.dataList != null && this.dataList.size() > 0) {
                   dataArray = new String[this.getRowNum(sheetIndex) + 1];
                   int index = 0;
                   for (String[] rowData : dataList) {
                       if (rowData != null) {
                            dataArray[index] = rowData[colIndex]; index++;
                       }
                   }
               }
           }
           return dataArray;
       }*/





    /**
     *  把属性通过换行的方式输出出来
     * @author ztd
     * @param classPath
     */
    //TODO 有问题
    public static String[] getFiledsName(String classPath) throws IllegalAccessException {
        Class<?> clazz = null;

        try {
            clazz = Class.forName(classPath);
        } catch (ClassNotFoundException e) {

            e.printStackTrace();

        }
        Field[] fields = clazz.getDeclaredFields();
        String[] f = new String[fields.length];
        Student rt = new Student();//实例化类对象
        for (int i = 0; i <fields.length ; i++) {
            Field field = fields[i];
            String value = (String) field.get(rt);
            f[i] = value;
        }
        return f;
    }

    public static void main(String[] args) {

        //导出excel
        FileOutputStream out = null;
        try {
            out = new FileOutputStream("E:\\test\\test.xls");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        List<Student> list = new ArrayList<Student>();

        Student vo = new Student(1,"001","tom","F","测试",new Date(),new Date());
        Student vo1 = new Student(2,"002","tom02","F","测试02",new Date(),new Date());

        list.add(vo);
        list.add(vo1);

        //类的字段属性
        String[] feilds = {"ID","SNO","SNAME","SSEX","remark","createDate","updateDate"};
        // 创建 表头 单元格
        List<String> head = new ArrayList<>();
        head.add("ID");
        head.add("学号");
        head.add("姓名");
        head.add("性别");
        head.add("备注");
        head.add("修改时间");
        head.add("创建时间");
      //   ExcelTool.exportExcel(Student.class, list, feilds, out,head);
        System.out.println("----执行完毕----------");
    }

}
