package com.guilin.studycode.controller.file;

import com.guilin.studycode.entrity.Student;
import com.guilin.studycode.service.StudentService;
import com.guilin.studycode.utils.FilePathUtils;
import com.guilin.studycode.utils.FileTypeParseUtil;
import com.guilin.studycode.utils.ReadExcelUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.UUID;


@RequestMapping("/import")
@RestController
@Api(tags = "上传相关接口")
public class FileController {

    private static final Logger logger = LoggerFactory.getLogger(FileController.class);
    //中间临时文件路径位置
    @Value("${enter.tempPathUrl}")
    public String tempPathUrl;

    // 保存到服务器的临时文件夹
    @Value("${enter.relativeUrl}")
    public String relativeUrl;

    // 保存服务器文件上传的文件夹
    @Value("${enter.savePathUrl}")
    public String savePathUrl;

    @Autowired
    private StudentService studentService;


    @PostMapping("/import")
    @ApiOperation("文件上传 ")
    public String ExcelImport(
            @ApiParam (name = "file",value ="上传文件") @RequestPart("file") MultipartFile file, HttpServletRequest request) throws Exception {
        String originalFilename = file.getOriginalFilename();
        String fileType = originalFilename.substring(originalFilename.lastIndexOf(".") + 1).toLowerCase(Locale.US);
        if (!fileType.equals("xlsx")) {
            return "文件类型错误,不是excel类型";
        }
        if (fileType.equals("xlsx")) {
            File fileRest = null;
            //保存的文件路径 savePathUrl
            fileRest = new File( savePathUrl + File.separator + file.getOriginalFilename());
            InputStream ins = file.getInputStream();
            //开始复制
            FileUtils.copyInputStreamToFile(ins, fileRest);
        }
        return originalFilename +"上传成功";
    }


    /**
     * zip 文件上传-解压保存
     *
     *
     * @param file
     * @return
     * @throws Exception
     */
    @PostMapping("/importZip")
    @ApiOperation("zip文件上传解压保存 ")
    public String fileImport(
            @ApiParam (name = "file",value ="上传文件") @RequestPart("file") MultipartFile file, HttpServletRequest request) throws Exception {
        String originalFilename = file.getOriginalFilename();
        String fileType = originalFilename.substring(originalFilename.lastIndexOf(".") + 1).toLowerCase(Locale.US);
        String unzipPackage = originalFilename.substring(0, originalFilename.lastIndexOf("."));
        String uuid = UUID.randomUUID().toString();
        if (!fileType.equals("zip")) {
            return "文件类型错误,不是zip类型";
        }
        if (fileType.equals("zip")) {
            //中间临时文件
            String tempPath = tempPathUrl + File.separator + uuid.replaceAll("-", "");
            //上传文件copy到服务器的临时路径
            String savePath = relativeUrl + File.separator;
            File savePathFile = new File(savePath);
            if (!savePathFile.exists()) {
                savePathFile.mkdirs();
            }
            String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
            // 防止生成的临时文件重复
            File saveFile = File.createTempFile(UUID.randomUUID().toString(), suffix);
            // 将上传zip文件解压并复制到临时文件
            FileUtils.copyInputStreamToFile(file.getInputStream(), saveFile);
            FileTypeParseUtil.unZip(file, tempPath, savePath);
            // 入库并上传至服务器  savePathUrl
            saveExcleInfo(tempPath);
            //删除文件,中间临时文件
            FileTypeParseUtil.clearFiles(tempPath);
        }
        return originalFilename +"上传成功";
    }

    /**
     * 入库并上传至服务器
     *
     * @param tempPath
     */
    private void saveExcleInfo(String tempPath) {
        List<File> fileXlsList = new ArrayList<>();
        logger.info("临时路径{}", tempPath);
        fileXlsList = FileTypeParseUtil.getSubFiles(tempPath, fileXlsList);
        logger.info("文件数量" + fileXlsList.size());
        for (File oneFile : fileXlsList) {
            String fileNameType = oneFile.getName().toLowerCase();
            if (fileNameType.endsWith(".xls") || fileNameType.endsWith(".xlsx")) {
                try {
                    logger.info("循环迭代文件{}", oneFile.getName());
                    //入库
                    if (oneFile.getName().contains("测试")) {
                         saveExcl(oneFile);
                    }
                    //保存到服务器并删除服务器中的临时文件
                    FilePathUtils.saveFileByType(oneFile, null, savePathUrl + File.separator);
                    logger.info("{}", "文件上传且入库成功");
                } catch (Exception e) {
                    logger.error("附件清单上传异常{}", e.getMessage());
                    e.printStackTrace();
                }
            }

        }

    }

    public void saveExcl(File oneFile) throws IOException {
        boolean isE2007 = false;
        if (oneFile.getName().endsWith("xlsx")) {
            isE2007 = true;
        }
        InputStream input = new FileInputStream(oneFile); //建立输入流
        Workbook wb = null;
        //根据文件格式(2003或者2007)来初始化
        if (isE2007) {
            wb = new XSSFWorkbook(input);
        } else {
            wb = new HSSFWorkbook(input);
        }
        Sheet sheet = wb.getSheetAt(0);
        int n = 0;
        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            Student student = new Student();
            //student.setID();
            student.setSNO(ReadExcelUtil.getCellValue(sheet.getRow(i).getCell(1)));
            student.setSNAME(ReadExcelUtil.getCellValue(sheet.getRow(i).getCell(2)));
            student.setSSEX(ReadExcelUtil.getCellValue(sheet.getRow(i).getCell(3)));
            //落库
            int rest = studentService.saveStudent(student);
            if(1 ==rest){
                n++;
                logger.info("当前第:{}条数据保存成功" , i);
            }
        }
        logger.info("共有：{}条数据保存成功",n);

    }


}
