package com.guilin.studycode.utils.pdTofWord;

import com.aspose.cells.Workbook;
import com.aspose.words.Document;
import com.aspose.words.License;
import com.guilin.studycode.utils.excel.test;
import com.lowagie.text.pdf.PdfCopy;
import com.lowagie.text.pdf.PdfImportedPage;
import com.lowagie.text.pdf.PdfReader;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFPictureData;
import org.apache.xmlbeans.XmlOptions;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * word转pdf excel 转pdf
 * 
 * @Title PdfUtil.java
 * @author puguil
 * @version 1.0
 * @date 2022年6月24日 下午6:46:12
 */
public class PdfWordUtil {
	protected static Logger log = LoggerFactory.getLogger(PdfWordUtil.class);  
	private static boolean getLicense() throws Exception {
		boolean result = false;
		ByteArrayInputStream is =null;
		try {
			String licenseXml = "<License><Data><Products><Product>Aspose.Total for Java</Product><Product>Aspose.Words for Java</Product></Products><EditionType>Enterprise</EditionType><SubscriptionExpiry>20991231</SubscriptionExpiry><LicenseExpiry>20991231</LicenseExpiry><SerialNumber>23dcc79f-44ec-4a23-be3a-03c1632404e9</SerialNumber></Data><Signature>sNLLKGMUdF0r8O1kKilWAGdgfs2BvJb/2Xp8p5iuDVfZXmhppo+d0Ran1P9TKdjV4ABwAgKXxJ3jcQTqE/2IRfqwnPf8itN8aFZlV3TJPYeD3yWE7IT55Gz6EijUpC7aKeoohTb4w2fpox58wWoF3SNp6sK6jDfiAUGEHYJ9pjU=</Signature></License>";
			 is = new ByteArrayInputStream(licenseXml.getBytes());
			License license = new License();
			license.setLicense(is);
			is.close();
			result = true;
		}finally {
			if(is!=null) {
				is.close();
			}
		}
		return result;
	}
	
	/**
	 * word转pdf
	 * @param xmlPath 需要被转换的word全路径带文件名
	 * @param docPath  转换之后pdf的全路径带文件名
	 * @throws Exception 
	 */
	public static void xml2Doc(String xmlPath, String docPath) throws Exception {
		if (!getLicense()) { // 验证License 若不验证则转化出的pdf文档会有水印产生
			return;
		}
		FileOutputStream os =null;
		try {
			long old = System.currentTimeMillis();
			File file = new File(docPath); // 新建一个doc文档
			os = new FileOutputStream(file);
			Document doc = new Document(xmlPath); // Address是将要被转化的word文档
			if(docPath.contains(".docx")) {
				doc.save(os, com.aspose.words.SaveFormat.DOCX);// 全面支持DOC, DOCX, OOXML, RTF HTML, OpenDocument, PDF, EPUB,
				// XPS, SWF 相互转换
			}else {
				doc.save(os, com.aspose.words.SaveFormat.DOC);// 全面支持DOC, DOCX, OOXML, RTF HTML, OpenDocument, PDF, EPUB,
				// XPS, SWF 相互转换
			}
			long now = System.currentTimeMillis();
			log.info("共耗时：" + ((now - old) / 1000.0) + "秒"); // 转化用时
		}finally {
			if(os!=null) {
				os.close();
			}
		}
	}

	/**
	 * word转pdf
	 * @param wordPath 需要被转换的word全路径带文件名
	 * @param pdfPath  转换之后pdf的全路径带文件名
	 * @throws Exception 
	 */
	public static void doc2pdf(String wordPath, String pdfPath) throws Exception {
		if (!getLicense()) { // 验证License 若不验证则转化出的pdf文档会有水印产生
			return;
		}
		FileOutputStream os = null;
		try {
			long old = System.currentTimeMillis();
			File file = new File(pdfPath); // 新建一个pdf文档
			os = new FileOutputStream(file);
			Document doc = new Document(wordPath); // Address是将要被转化的word文档
			doc.save(os, com.aspose.words.SaveFormat.PDF);// 全面支持DOC, DOCX, OOXML, RTF HTML, OpenDocument, PDF, EPUB,
															// XPS, SWF 相互转换
			long now = System.currentTimeMillis();
			log.info("共耗时：" + ((now - old) / 1000.0) + "秒"); // 转化用时
		}finally {
			if(os!=null) {
				os.close();
			}
		}
	}

	/**
	 * excel转pdf
	 * @param excelPath 需要被转换的excel全路径带文件名
	 * @param pdfPath   转换之后pdf的全路径带文件名
	 * @throws Exception 
	 */
	public static void xls2pdf(String excelPath, String pdfPath) throws Exception {
		if (!getLicense()) { // 验证License 若不验证则转化出的pdf文档会有水印产生
			return;
		}
		FileOutputStream fileOS =null;
		try {
			long old = System.currentTimeMillis();
			Workbook wb = new Workbook(excelPath);// 原始excel路径
			fileOS = new FileOutputStream(new File(pdfPath));
			wb.save(fileOS, com.aspose.cells.SaveFormat.PDF);
			long now = System.currentTimeMillis();
			log.info("共耗时：" + ((now - old) / 1000.0) + "秒"); // 转化用时
		}finally {
			if(fileOS!=null) {
				fileOS.close();
			}
		}
	}

	/*************************************************************************************************/
	
	/**
	 * 合并多个pdf为一个
	 * @param files
	 * @param newfile
	 * @return
	 * @author zhengzhenglei
	 * @throws Exception 
	 */
	public static String mergePdf(List<String> files, String newfile) throws Exception {
		com.lowagie.text.Document document = null;
		try {
			document = new com.lowagie.text.Document(new PdfReader(files.get(0)).getPageSize(1));
			PdfCopy copy = new PdfCopy(document, new FileOutputStream(newfile));
			document.open();
			for (int i = 0; i < files.size(); i++) {
				PdfReader reader = new PdfReader(files.get(i));
				int n = reader.getNumberOfPages();
				for (int j = 1; j <= n; j++) {
					document.newPage();
					PdfImportedPage page = copy.getImportedPage(reader, j);
					copy.addPage(page);
				}
			}
		} finally {
			document.close();
		}
		return newfile;
	}

	/**
	 * 合并多个doc为一个  只支持 2007  docx
	 * @param filePathList
	 * @param filePath
	 * @return
	 * @throws Exception
	 * @author zhengzhenglei
	 */
	public static String mergeDoc(List<String> filePathList, String filePath) throws Exception {
		File newFile = new File(filePath);
		List<File> srcfile = new ArrayList<>();
		for (String src : filePathList) {
			srcfile.add(new File(src));
		}
		
		OutputStream dest =null;
		try {
			dest = new FileOutputStream(newFile);
			ArrayList<XWPFDocument> documentList = new ArrayList<>();
			XWPFDocument doc = null;
			for (int i = 0; i < srcfile.size(); i++) {
				FileInputStream in = new FileInputStream(srcfile.get(i).getPath());
				OPCPackage open = OPCPackage.open(in);
				XWPFDocument document = new XWPFDocument(open);
				documentList.add(document);
			}
			for (int i = 0; i < documentList.size(); i++) {
				doc = documentList.get(0);
				if (i != documentList.size() - 1) {
					// 每一页后加上段前分页，最后一页不加
					documentList.get(i).createParagraph().setPageBreak(true);
				}
				if (i != 0) {
					appendBody(doc, documentList.get(i));
				}
			}
			// doc.createParagraph().setPageBreak(true);
			doc.write(dest);
		} finally {
			if(dest!=null) {
				dest.close();
			}
		}
		return filePath;

	}

	public static void appendBody(XWPFDocument src, XWPFDocument append) throws Exception {
		CTBody src1Body = src.getDocument().getBody();
		CTBody src2Body = append.getDocument().getBody();

		List<XWPFPictureData> allPictures = append.getAllPictures();
		// 记录图片合并前及合并后的ID
		Map<String, String> map = new HashMap<String, String>();
		for (XWPFPictureData picture : allPictures) {
			String before = append.getRelationId(picture);
			// 将原文档中的图片加入到目标文档中
			String after = src.addPictureData(picture.getData(), org.apache.poi.xwpf.usermodel.Document.PICTURE_TYPE_PNG);
			map.put(before, after);
		}

		appendBody(src1Body, src2Body, map);

	}

	private static void appendBody(CTBody src, CTBody append, Map<String, String> map) throws Exception {
		XmlOptions optionsOuter = new XmlOptions();
		optionsOuter.setSaveOuter();
		String appendString = append.xmlText(optionsOuter);

		String srcString = src.xmlText();
		String prefix = srcString.substring(0, srcString.indexOf(">") + 1);
		String mainPart = srcString.substring(srcString.indexOf(">") + 1, srcString.lastIndexOf("<"));
		String sufix = srcString.substring(srcString.lastIndexOf("<"));
		String addPart = appendString.substring(appendString.indexOf(">") + 1, appendString.lastIndexOf("<"));

		if (map != null && !map.isEmpty()) {
			// 对xml字符串中图片ID进行替换
			for (Map.Entry<String, String> set : map.entrySet()) {
				addPart = addPart.replace(set.getKey(), set.getValue());
			}
		}
		// 将两个文档的xml内容进行拼接
		CTBody makeBody = CTBody.Factory.parse(prefix + mainPart + addPart + sufix);
		src.set(makeBody);
	}

	public static void main(String[] args) throws Exception {
		// word 和excel 转为pdf
//		String excePaths = "E:\\fileUp\\test\\test.xlsx";
//		String wordPaths = "E:\\fileUp\\test\\1.doc";
//		String pdfPath = "E:\\fileUp\\test\\2.pdf";
		// doc --> PDF
//		doc2pdf(wordPaths, pdfPath);// filePaths需要转换的文件位置 pdfPath为存储位置

		// // excel --> PDF
//		xls2pdf(excePaths, pdfPath);

		// 合并pdf
//		List<String> files=new ArrayList<>();
//		files.add("E:\\fileUp\\test\\1.pdf");
//		files.add("E:\\fileUp\\test\\2.pdf");
//		mergePdf(files, "E:\\fileUp\\test\\3.pdf");


		// word合并
		List<String> files1=new ArrayList<>();
		files1.add("E:\\fileUp\\test\\1.doc");
		files1.add("E:\\fileUp\\test\\2.doc");
		mergeDoc(files1, "E:\\fileUp\\test\\12.doc");

	}
}
