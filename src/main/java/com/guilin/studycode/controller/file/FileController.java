package com.guilin.studycode.controller.file;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.guilin.studycode.entrity.Student;
import com.guilin.studycode.service.StudentService;
import com.guilin.studycode.utils.ViewExcel;
import com.guilin.studycode.utils.date.StringDateUtil;
import com.guilin.studycode.utils.excel.ExcelUtil3;
import com.guilin.studycode.utils.filewiths.FilePathUtils;
import com.guilin.studycode.utils.filewiths.FileTypeParseUtil;
import com.guilin.studycode.utils.excel.ReadExcelUtil;
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
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.*;


@RequestMapping("/import")
@RestController
@Api(tags = "上传相关接口")
public class FileController {

    ObjectMapper objectMapper = new ObjectMapper();

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
    public JSONObject ExcelImport(
            @ApiParam (name = "file",value ="上传文件") @RequestPart("file") MultipartFile file, HttpServletRequest request) throws Exception {

        /**
         *
         * 获取到的size为：long size1 = file.getSize()
         * int GB = 1024 * 1024 * 1024;//定义GB的计算常量
         * int MB = 1024 * 1024;//定义MB的计算常量
         * int KB = 1024;//定义KB的计算常量
         */

        JSONObject result = new JSONObject();
        if (file == null || file.isEmpty()) { //文件判空校验
            result.put("msg", "未选择需上传的文件");
            return result;
        }
        int size = 1024 * 1024;  //自定义文件上传大小  1MB
        long fileSize = file.getSize(); //获取文件大小
        if(fileSize >= size){
            result.put("msg", "上传的文件不能超过1MB");
            return result;
        }

        // MultipartFile file
        String originalFilename = file.getOriginalFilename(); //获取文件全名 测试上传.xlsx

        //判断文件类型
        // 方法一 originalFilename.endsWith("xlsx") true判断文件是什么类型

//     方法二   if (!originalFilename.matches("^.+\\.(?i)(xls)$") && !originalFilename.matches("^.+\\.(?i)(xlsx)$")) {
//            logger.error("上传文件格式不正确");
//        }
         // 方法三 获取文件类型 xlsx
        String fileType = originalFilename.substring(originalFilename.lastIndexOf(".") + 1).toLowerCase(Locale.US);
        if (!fileType.equals("xlsx")) {
            result.put("msg", "文件类型错误,不是excel类型");
            return result;
        }
        if (fileType.equals("xlsx")) {
            File fileRest = null;
            //保存的文件路径 savePathUrl
            fileRest = new File( savePathUrl + File.separator + file.getOriginalFilename());
            InputStream ins = file.getInputStream();
            //开始复制
            try {
                FileUtils.copyInputStreamToFile(ins, fileRest);
                logger.info("复制文件结束");
            } catch (IOException e) {
               logger.info(e.getMessage());
               logger.error("文件复制报错 ==={}",e.getMessage());
            } finally {
                //关闭流
                ins.close();
            }
        }
        result.put("msg", originalFilename +"上传成功");
        return result;
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


    /**
     * 批量导入--批量导入模板下载
     */
    @ResponseBody
    @PostMapping(value = "/downTemplate")
    public ModelAndView downTemplate(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws IOException {
        //设置列名 start
        List<String> rowList = new ArrayList<String>();
        List<String> exampleList = new ArrayList<String>();
//        if(template.equals("渠道导入模板下载.xls")){
        model.put("fileName", "coalitionInfoTemplate1");//设置文件文件名
        model.put("sheetName", "coalitionInfoTemplate1");//设置sheet页名

        rowList.add("详细地址");
        rowList.add("商业名称");
        rowList.add("地址名称");
        rowList.add("类型");
        rowList.add("渠道编码");
        rowList.add("会员名");
        rowList.add("联系电话");

        exampleList.add("360121192");
        exampleList.add("测试");
        exampleList.add("经贸广场");
        exampleList.add("电工");
        exampleList.add("788885");
        exampleList.add("jona");
        exampleList.add("1234567");

        model.put("rowList", rowList);
        model.put("exampleList", exampleList);
        model.put("flag", "template");//批量导入模板下载
        //设置列名 end
        ViewExcel viewExcel = new ViewExcel();
        ModelAndView modelAndView = new ModelAndView(viewExcel, model);
        return modelAndView;
    }


    // ************************************  ExcelUtil3 工具的使用 ****************************************************

    /**
     * 导入
     * @author:
     * @createTime: 2022年5月25日   友好的提示 成功几条  失败几条 以及失败的原因是什么
     * @history:
     * @param request
     * @param response
     * @return HashMap<String,Object>
     */
    @ResponseBody
    @PostMapping("batchImport")
    public String batchImport(HttpServletRequest request, HttpServletResponse response) throws IOException {


        Map<String, String> out = new HashMap<String, String>();
        request.setCharacterEncoding("UTF-8");
        String errInfo="";
        int successNum = 0;
        int failureNum = 0;
        try {
            MultipartRequest multipartRequest = (MultipartRequest) request;
            MultipartFile excelFile = multipartRequest.getFile("edit");

            String filename = excelFile.getOriginalFilename();
            List<List<String>> datas = null;
            // 读到的数据都在datas里面，根据实际业务逻辑做相应处理
            if (filename.endsWith("xls")) {// xls格式的文件
                datas = ExcelUtil3.readXls(excelFile.getInputStream());
            } else if (filename.endsWith("xlsx")) {// xlsx格式的文件
                datas = ExcelUtil3.readXlsx(excelFile.getInputStream());
            }

            int length = datas.size();

            Map<String, String> map = new HashMap<String, String>();
            for (int i = 0; i < length; i++) {
                int j = i+1;
                List<String> importList = datas.get(i);
                //校验表格中的数据
                String street = (String) importList.get(0);//街道
                String type = (String) importList.get(1);
                String chanl = (String) importList.get(2);
                if ("".equals(street)) {//街道
                    failureNum++;
                    errInfo = errInfo+"|第"+j+ "行街道为空";
                    break;
                }else if(!street.substring(0,2).equals("51")){//不是成都的
                    failureNum++;
                    errInfo = errInfo+"|第"+j+ "行街道非成都省内";
                    break;
                }

                if ("".equals(type)) {//类型
                    errInfo = errInfo+"|第"+j+ "支付类型为空";
                    failureNum++;
                    break;
                }

                if ("".equals(chanl)) {//渠道编号
                    errInfo = errInfo+"|第"+j+ "渠道为空";
                    failureNum++;
                    break;
                }

               // map.put("ID", "1");
                map.put("SNO", "101");
                map.put("SNAME", "测试map");
                map.put("SSEX", "");
                map.put("remark", "备注");
                SimpleDateFormat simpleDateFormat = StringDateUtil.dateFormat(4);
                String createDate = simpleDateFormat.format(new Date());
                String updateDate = simpleDateFormat.format(new Date());
                map.put("createDate", createDate);
               // map.put("updateDate", updateDate);
                Map<String, String> result = studentService.saveStudentMap(map);;

                if (result.get("code").equals("0000")) {
                    successNum++;

                } else {
                    failureNum++;
                    errInfo = errInfo+"|第"+j+ "行"+result.get("detail");
                }
            }

            if (successNum > 0) {
                out.put("code", "0000");
                out.put("msg", "操作成功"+successNum+"条,失败"+failureNum+"条");
            }else{
                out.put("code", "4444");
                out.put("msg", "操作成功"+successNum+"条,失败"+failureNum+"条，失败信息为:" +errInfo);
            }

        } catch (Exception e) {
            e.printStackTrace();
            errInfo = e.getMessage();
            out.put("code", "4444");
            out.put("msg", "操作成功"+successNum+"条,失败"+failureNum+"条，失败信息为:" +errInfo);
        }
        return objectMapper.writeValueAsString(out);

    }

}
