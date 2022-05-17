
package com.guilin.studycode.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.poi.ss.usermodel.*;
import org.springframework.web.servlet.view.document.AbstractXlsView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

public class ViewExcel extends AbstractXlsView {
	@SuppressWarnings("unchecked")
	@Override
	protected void buildExcelDocument(Map<String, Object> map, Workbook workbook,
		HttpServletRequest request, HttpServletResponse response) throws Exception {

		String sheetName = (String) map.get("sheetName");// excel名
		//HSSFSheet sheet = workbook.createSheet(sheetName);// 创建sheet页
		Sheet sheet = workbook.createSheet(sheetName);
		Row row = sheet.createRow(0);  // 获得第一行
		List<String> rowList = (List<String>) map.get("rowList");// 取得列名数据
		sheet.setDefaultColumnWidth(rowList.size() + 1);// 设置列数

		//HSSFFont font = workbook.createFont();// 设置字体
		Font font = workbook.createFont();
		font.setFontName("黑体");
		font.setFontHeightInPoints((short) 11);// 设置字体大小
		font.setBold(true);// 字体加粗

		//HSSFCellStyle headstyle = workbook.createCellStyle();
		CellStyle headstyle = workbook.createCellStyle();
		headstyle.setFont(font);
		headstyle.setAlignment(HorizontalAlignment.CENTER);// 左右居中 水平居中
		headstyle.setVerticalAlignment(VerticalAlignment.CENTER);// 上下居中

		//设置CELL格式为文本格式
		DataFormat format = workbook.createDataFormat();
		headstyle.setDataFormat(format.getFormat("@"));
		// 设置列名
		//Cell cell = getCell(row, 0); //获得第一行第一个格子
		//cell.setCellStyle(headstyle);
		for (int i = 0; i < rowList.size(); i++) {
			// 设置列宽
			sheet.setColumnWidth(i, 10 * 512);
			row.createCell(i).setCellStyle(headstyle);
			//setText(cell, (String) rowList.get(i));
			row.createCell(i).setCellValue((String) rowList.get(i));

			sheet.setDefaultColumnStyle(i, headstyle);//设置每一列格式都为文本格式
		}

		// 设置excel中的数据
		String flag = (String) map.get("flag");
		// flag: 1 其它  template 模板
		if ("1".equals(flag)) {
			JSONArray info = (JSONArray) map.get("rowList");// 类信息
			if (info.size() > 0) {
				for (int i = 0; i < info.size(); i++) {//取出每一条数据
					JSONObject jsonObject = info.getJSONObject(i);
					row = sheet.createRow(i + 1);
					row.createCell(0).setCellValue((String) jsonObject.get("id"));
					row.createCell(1).setCellValue((String) jsonObject.get("name"));
					row.createCell(2).setCellValue((String) jsonObject.get("typeName"));
					row.createCell(3).setCellValue((String) jsonObject.get("city"));
					row.createCell(4).setCellValue((String) jsonObject.get("time"));
				}
			}
		} else if ("template".equals(flag)) {//批量导入模板下载
			List<String> exampleList = (List<String>) map.get("exampleList");
			row = sheet.createRow(1);
			for (int i = 0; i < exampleList.size(); i++) {
				row.createCell(i).setCellValue((String) exampleList.get(i));
			}

			String fileName = (String) map.get("fileName") + ".xls";

			fileName = encodeFilename(fileName, request);

			response.setContentType("application/vnd.ms-excel");

			response.setHeader("Content-disposition", "attachment;filename=" + fileName);

			OutputStream out = response.getOutputStream();

			workbook.write(out);

			out.flush();

			out.close();
		}
	}
	protected static String encodeFilename(String fileName, HttpServletRequest request) throws Exception {
		String agent = request.getHeader("USER-AGENT");
		try {
			if ((agent != null) && (-1 != agent.indexOf("MSIE"))) {

				String newFileName = URLEncoder.encode(fileName, "UTF-8");
				newFileName = newFileName.replace("+", "%20");
				if (newFileName.length() > 150) {
					newFileName = new String(newFileName.getBytes("GB2312"), "ISO8859-1");
					newFileName = newFileName.replace(" ", "%20");
				}
				return newFileName;
			}

			if ((agent != null) && (-1 != agent.indexOf("Mozilla"))) {
				return fileName;
			}

			return fileName;
		} catch (Exception e) {
			e.printStackTrace();
			return fileName;
		}
	}

}
